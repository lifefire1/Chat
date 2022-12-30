import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Client client = new Client("lexa");
        new Thread(() -> {
            for (int i = 0; i < 10; i++){
                try {
                    client.sendMessage(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("стоп");
            }
        }).start();

        new Thread(client::receiveMessageFromServer).start();
    }
}