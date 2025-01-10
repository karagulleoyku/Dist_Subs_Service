package dist_servers;
import java.io.*;
import java.net.*;
import dist_servers.CapacityOuterClass.Capacity; // Capacity sınıfını içe aktar

public class Server3 {
    public static void main(String[] args) {
        int port = 5003; 
        int subscriberCount = 10; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server3 is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected to Server3");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String clientMessage = in.readLine();
                System.out.println("Server3 received: " + clientMessage);

                if ("CPCTY".equals(clientMessage)) {
                    Capacity capacity = Capacity.newBuilder()
                        .setServerId(3) 
                        .setServerStatus(subscriberCount) 
                        .setTimestamp(System.currentTimeMillis() / 1000) 
                        .build();

                    out.write(capacity.toByteArray());
                    out.flush();
                    System.out.println("Capacity sent: " + capacity);
                } else {
                    // Farklı mesajlar için durum kontrolü
                    System.out.println("Unknown message received.");
                }

                clientSocket.close();
            }
        } catch (Exception e) {
            System.err.println("Error in Server3: " + e.getMessage());
        }
    }
}
