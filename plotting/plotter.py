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
            else:
                print(f"No data to plot for Server {server_id}")

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
                        s.settimeout(30) 
                        s.connect(('localhost', port))
                        s.sendall(b'CPCTY')  
                        data = s.recv(1024)  

                        capacity = Capacity()
                        capacity.ParseFromString(data)

                        self.server_data[server_id]['times'].append(time.time())
                        self.server_data[server_id]['counts'].append(capacity.server_status)

                        print(f"Data from Server {server_id}: {capacity.server_status}, Timestamp: {capacity.timestamp}")

                        self.update_plot()
                except socket.timeout:
                    print(f"Timeout while connecting to Server {server_id} on port {port}")
                except ConnectionError:
                    print(f"Connection error with Server {server_id} on port {port}")
                except DecodeError:
                    print(f"Error decoding data from Server {server_id}")
                except Exception as e:
                    print(f"Unexpected error: {e}")

            print("Current server data:", self.server_data)
            time.sleep(1)  

if _name_ == "_main_":
    plotter = SimplePlotter()
    try:
        plotter.collect_data()
    except KeyboardInterrupt:
        print("Exiting...")
        plt.show()  
