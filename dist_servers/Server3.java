
import java.io.*;
import java.net.*;

public class Server3 {
    public static void main(String[] args) {
        int port = 5003; // Server3 dinleme portu

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server3 is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Yeni istemci bağlantısını kabul et
                System.out.println("New client connected to Server3");

                // İstemciyle iletişim kur
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                String clientMessage = in.readLine(); // İstemciden mesaj oku
                System.out.println("Server3 received: " + clientMessage);

                String response = "Processed by Server3: " + clientMessage; // Mesajı işlem yap
                out.println(response); // İstemciye cevap gönder

                clientSocket.close(); // Bağlantıyı kapat
            }
        } catch (Exception e) {
            System.err.println("Error in Server3: " + e.getMessage());
        }
    }
}
