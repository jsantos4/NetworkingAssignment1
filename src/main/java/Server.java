import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private static Socket socket;
    private ServerSocket serverSocket;

    public Server(){
    }

    public void receiveMessage() {
        try {
            serverSocket = new ServerSocket(0);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPort() {
        System.out.println(serverSocket.getLocalPort());
    }
}