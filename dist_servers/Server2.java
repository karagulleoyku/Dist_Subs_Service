package dist_servers;
import java.io.*;
import java.net.*;
import dist_servers.CapacityOuterClass.Capacity; // Capacity sınıfını içe aktar

public class Server2 {
    public static void main(String[] args) {
        int port = 5002; 
        int subscriberCount = 10;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server2 is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected to Server1");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String clientMessage = in.readLine();
                System.out.println("Server2 received: " + clientMessage);

                if ("CPCTY".equals(clientMessage)) {
                    Capacity capacity = Capacity.newBuilder()
                        .setServerId(2) 
                        .setServerStatus(subscriberCount) 
                        .setTimestamp(System.currentTimeMillis() / 1000) 
                        .build();

                    out.write(capacity.toByteArray());
                    out.flush();
                    System.out.println("Capacity sent: " + capacity);
                } else {
                    System.out.println("Unknown message received.");
                }

                clientSocket.close();
            }
        } catch (Exception e) {
            System.err.println("Error in Server1: " + e.getMessage());
        }
    }
}
