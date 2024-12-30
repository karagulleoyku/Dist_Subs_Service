import java.net.*;
import java.io.*;
import com.example.protobuf.SubscriberOuterClass.Subscriber;


public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int[] SERVER_PORTS = {5001, 5002, 5003};
    
    public Subscriber subscribe(String nameSurname) {
        // Try each server until successful
        for (int port : SERVER_PORTS) {
            try (Socket socket = new Socket(SERVER_HOST, port)) {
                // Create subscription request
                Subscriber request = Subscriber.newBuilder()
                    .setStatus(Subscriber.Status.SUBS)
                    .setNameSurname(nameSurname)
                    .build();
                
                // Send request
                request.writeDelimitedTo(socket.getOutputStream());
                
                // Read response
                Subscriber response = Subscriber.parseDelimitedFrom(socket.getInputStream());
                return response;
            } catch (IOException e) {
                System.out.println("Failed to connect to server on port " + port);
                // Try next server
            }
        }
        throw new RuntimeException("All servers are unavailable");
    }
    
    public static void main(String[] args) {
        Client client = new Client();
        try {
            Subscriber response = client.subscribe("Jane DOE");
            System.out.println("Subscribed with ID: " + response.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
