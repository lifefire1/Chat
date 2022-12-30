public class TestClient2 {
    public static void main(String[] args) throws InterruptedException {
//        Client client = new Client("jenya");
//        while (true){
//            client.sendMessage("hello");
//            client.sendMessage("\njskjdskfjdfjsldfjk");
//            Thread.sleep(90);
//        }

        for (int i = 0; i < 10000; i++) {
            System.out.println("i = " + i + " --- " + (char) i);
            Thread.sleep(30);
        }
    }
}
