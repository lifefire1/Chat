import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Server {
    private ArrayList <Socket> users;
    private ArrayList<InputStream> usersInputStream;
    private ArrayList<OutputStream> usersOutputStream;
    private ServerSocket serverSocket;


    public Server() {
    }

    public void launch() {
        try {
            serverSocket = new ServerSocket(5000);
        }   catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        users = new ArrayList<>();
        usersInputStream = new ArrayList<>();
        usersOutputStream = new ArrayList<>();

        new Thread(() -> {
           while(true){
               try {
                   System.out.println("wait client");
                   users.add(serverSocket.accept());
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               try {
                   usersInputStream.add(users.get(users.size() - 1).getInputStream());
                   System.out.println("add in stream");
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               try {
                   usersOutputStream.add(users.get(users.size() - 1).getOutputStream());
                   System.out.println("add out stream");
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       }).start();
    }

    public void sendMessageAllUsers(String string){
        for (OutputStream out:
             usersOutputStream) {
            try {
                out.write((string + ';').getBytes());
            }   catch (IOException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    public void listenAllUsers() throws InterruptedException {
        while (true){
            for (InputStream ins:
                    usersInputStream) {
                new Thread(() -> {
                    synchronized (this){
                        try {
                            StringBuilder res = new StringBuilder();
                            int b = 0;
                            while ((b = ins.read()) != ';'){
                                res.append((char) b);
                            }
//                            String s = Arrays.toString();
//                            if (!s.equals("[]")){
//                            System.out.println(res);
                            System.out.println("отправка");
                            sendMessageAllUsers(res.toString());
//                            }
                        }   catch (IOException exception){
                            System.out.println(exception.getMessage());
                        }
                    }
                }).start();

            }
            try {
                Thread.sleep(10);
            }   catch (InterruptedException exception){
                System.out.println(exception.getMessage());
            }
            Thread.sleep(5000);
        }
    }
}

/*
пример протокола сообщения
message from text

пример протокола авторизации
auth username passwordEncode
 */
