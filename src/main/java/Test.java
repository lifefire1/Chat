// version 0.1

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        server.launch();
        System.out.println("начинаем слушать");
        server.listenAllUsers();
    }
}
