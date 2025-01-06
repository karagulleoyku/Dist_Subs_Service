import matplotlib.pyplot as plt
import socket
import time
from google.protobuf.message import DecodeError
from capacity_pb2 import Capacity

class SimplePlotter:
    def _init_(self):
      
        self.server_data = {
            1: {'times': [], 'counts': []},
            2: {'times': [], 'counts': []},
            3: {'times': [], 'counts': []}
        }

        
        self.fig, self.ax = plt.subplots()
        plt.ion() 

    def update_plot(self):
        """Grafiği günceller."""
        self.ax.clear() 
        for server_id, data in self.server_data.items():
            if data['times'] and data['counts']:
                self.ax.plot(data['times'], data['counts'], label=f"Server {server_id}")

        self.ax.set_xlabel('Time')
        self.ax.set_ylabel('Number of Subscribers')
        self.ax.set_title('Server Subscription Status')
        self.ax.legend()
        plt.draw()
        plt.pause(0.1)

    def collect_data(self):
        """Sunuculardan veri toplar ve grafiği günceller."""
        
        server_ports = {1: 5001, 2: 5002, 3: 5003}

        while True:
            for server_id, port in server_ports.items():
                try:
                    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                        s.connect(('localhost', port))
                        s.sendall(b'CAPACITY_REQUEST')  
                        data = s.recv(1024)  

                      
                        capacity = Capacity()
                        capacity.ParseFromString(data)

                        
                        self.server_data[server_id]['times'].append(time.time())
                        self.server_data[server_id]['counts'].append(capacity.server_status)

                        self.update_plot()
                except (ConnectionError, DecodeError) as e:
                    print(f"Error collecting data from Server {server_id}: {e}")

            time.sleep(1)  

if _name_ == "_main_":
    plotter = SimplePlotter()
    plotter.collect_data()
