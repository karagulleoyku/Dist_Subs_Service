import java.net.*;
import java.io.*;
import com.example.protobuf.SubscriberOuterClass.Subscriber;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int[] SERVER_PORTS = {5001};

    public void sendSubscriber(Subscriber subscriber) {
        for (int port : SERVER_PORTS) {
            try (Socket socket = new Socket(SERVER_HOST, port)) {
                // Subscriber nesnesini gönder
                subscriber.writeDelimitedTo(socket.getOutputStream());
                System.out.println("Subscriber sent to server on port " + port);

                // Sunucudan yanıt al
                Subscriber response = Subscriber.parseDelimitedFrom(socket.getInputStream());
                if (response != null) {
                    System.out.println("Response from server: ID = " + response.getId());
                }
                return; // Başarılı ise diğer sunuculara deneme yapmaz
            } catch (IOException e) {
                System.out.println("Failed to connect to server on port " + port);
            }
        }
        System.err.println("All servers are unavailable");
    }

    public static void main(String[] args) {
        Client client = new Client();
        Subscriber subscriber = Subscriber.newBuilder()
            .setId(1)
            .setNameSurname("Odev Deneme")
            .setStatus(Subscriber.Status.SUBS)
            .setLastAccessed(System.currentTimeMillis() / 1000)
            .build();

        client.sendSubscriber(subscriber);
    }
}
