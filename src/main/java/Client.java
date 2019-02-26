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
            java.util.Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(8));

            //Send the message to the server
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(bytes);
            long time = System.nanoTime();

            System.out.println("Message sent to the server : " + size);

            //Get the return message from the server
            DataInputStream is = new DataInputStream(socket.getInputStream());
            InputStreamReader isr = new InputStreamReader(is);
            time = System.nanoTime() - time;
            System.out.println("Message received from the server : " + isr.read());
            System.out.println(time);
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
