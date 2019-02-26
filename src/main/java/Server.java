import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private static Socket socket;
    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(0);
    }

    public void receiveMessage() {
        try {
            System.out.println(serverSocket.getLocalPort());
            System.out.println("Server listening");

            socket = serverSocket.accept();
            DataInputStream is = new DataInputStream(socket.getInputStream());
            int size = is.available();

            byte[] bytes = new byte[size];
            java.util.Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(256));
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(bytes);
            System.out.println("Message sent to the client is " + size);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPort() {
        System.out.println(serverSocket.getLocalPort());
    }
}