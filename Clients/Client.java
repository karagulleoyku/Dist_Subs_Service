import java.net.*;
import java.io.*;
import com.example.protobuf.SubscriberOuterClass.Subscriber;


public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int[] SERVER_PORTS = {5001, 5002, 5003};
    
    public Subscriber subscribe(String nameSurname) {
 
        for (int port : SERVER_PORTS) {
            try (Socket socket = new Socket(SERVER_HOST, port)) {
 
                Subscriber request = Subscriber.newBuilder()
                    .setStatus(Subscriber.Status.SUBS)
                    .setNameSurname(nameSurname)
                    .build();
                
       
                request.writeDelimitedTo(socket.getOutputStream());
                
                Subscriber response = Subscriber.parseDelimitedFrom(socket.getInputStream());
                return response;
            } catch (IOException e) {
                System.out.println("Failed to connect to server on port " + port);
          
            }
        }
        throw new RuntimeException("All servers are unavailable");
    }
    
    public static void main(String[] args) {
        Client client = new Client();
        try {
            Subscriber response = client.subscribe("Oyku Karag");
            System.out.println("Subscribed with ID: " + response.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
