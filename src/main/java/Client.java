import java.io.*;
import java.net.*;

public class Client {

    private static Socket socket;

    public Client() {

    }

    public void sendMessage(int size) {
        try {
            String host = "localhost";
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            System.out.println(address);
            socket = new Socket(address, port);

            //Send the message to the server
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(new byte[size]);
            long time = System.nanoTime();

            System.out.println("Message sent to the server : " + size);

            //Get the return message from the server
            DataInputStream is = new DataInputStream(socket.getInputStream());
            InputStreamReader isr = new InputStreamReader(is);
            time = System.nanoTime() - time;
            System.out.println("Message received from the server : " + size);
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
