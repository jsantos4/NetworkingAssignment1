import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    private static Socket tcpSocket;
    private static DatagramSocket udpSocket;

    public Client() {

    }

    public long sendTCPMessage(int size, InetAddress address, int port) {
        long time = 0;
        try {

            tcpSocket = new Socket(address, port);
            byte[] bytes = new byte[size];
            Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(256));

            long startTime = System.nanoTime();
            DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
            os.write(bytes);


            DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
            is.readFully(bytes);
            time = System.nanoTime() - startTime;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                tcpSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time;

    }

    public long sendUDPMessage(int size, InetAddress address, int port) {
        long time = 0;
        try {

            udpSocket = new DatagramSocket();
            byte[] bytes = new byte[size];
            Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(256));
            DatagramPacket packet = new DatagramPacket(bytes, size, address, port);

            long startTime = System.nanoTime();
            udpSocket.send(packet);

            packet = new DatagramPacket(bytes, size);
            udpSocket.receive(packet);
            time = System.nanoTime() - startTime;

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                udpSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time;
    }
}
