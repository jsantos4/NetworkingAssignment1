import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    private static Socket socket;

    public Client() {

    }

    public void sendMessage(int size, InetAddress address, int port) {
        try {

            socket = new Socket(address, port);
            byte[] bytes = new byte[size];
            java.util.Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(256));

            //Send the message to the server
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(bytes);
            long startTime = System.nanoTime();

            System.out.println("Size sent to the server : " + size);

            //Get the return message from the server
            DataInputStream is = new DataInputStream(socket.getInputStream());
            InputStreamReader isr = new InputStreamReader(is);
            long time = System.nanoTime() - startTime;
            System.out.println("RTT in nanoseconds: " + time);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            //Closing the socket
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
