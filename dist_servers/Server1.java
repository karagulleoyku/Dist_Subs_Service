import java.io.*;
import java.net.*;

public class Server1 {
    public static void main(String[] args) {
        int port = 5001; // Server1 dinleme portu

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server1 is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("New client connected to Server1");

          
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                String clientMessage = in.readLine(); 
                System.out.println("Server1 received: " + clientMessage);

                String response = "Processed by Server1: " + clientMessage; 
                out.println(response); 

                clientSocket.close(); 
            }
        } catch (Exception e) {
            System.err.println("Error in Server1: " + e.getMessage());
        }
    }
}
