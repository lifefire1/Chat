import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Client extends Thread{
    private String username;
    private Socket socket;
    private OutputStream writer;
    private InputStream reader;

    public Client(String username) {
        this.username = username;
        try {
            socket = new Socket("127.0.0.1", 5000);
            writer = socket.getOutputStream();
            reader = socket.getInputStream();
        }   catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void sendMessage(String message){
//        synchronized (this){
            new Thread(() -> {
                try {
                    writer.write(("message " + username + " "  + message  + ';').getBytes());
                    writer.flush();
                }   catch (IOException exception){
                    System.out.println(exception.getMessage());
                }
            }).start();
//        }
    }

    public void receiveMessageFromServer(){
//       synchronized (this){
           for (;;){
               try {
                   synchronized (this){
                       StringBuilder res = new StringBuilder();
                       int b = 0;
                       while ((b = reader.read()) != ';'){
                           res.append((char) b);
                       }
                       System.out.println(res);
                   }
               }   catch (IOException exception){
                   System.out.println(exception.getMessage());
               }
       }
    }
}
