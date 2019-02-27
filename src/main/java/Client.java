import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    private static Socket tcpSocket;
    private static DatagramSocket udpSocket;

    public Client() {

    }

    public void sendTCPMessage(int size, InetAddress address, int port) {
        try {

            tcpSocket = new Socket(address, port);
            byte[] bytes = new byte[size];
            Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(256));

            long startTime = System.nanoTime();
            DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
            os.write(bytes);

            System.out.println("Size sent to the server : " + size);

            DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
            InputStreamReader isr = new InputStreamReader(is);
            long time = System.nanoTime() - startTime;
            System.out.println("RTT in nanoseconds: " + time);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                tcpSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void sendUDPMessage(int size, InetAddress address, int port) {
        try {

            udpSocket = new DatagramSocket(port, address);
            byte[] bytes = new byte[size];
            Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(256));
            DatagramPacket packet = new DatagramPacket(bytes, size);

            long startTime = System.nanoTime();
            udpSocket.send(packet);

            System.out.println("Size sent to the server : " + size);

            packet = new DatagramPacket(bytes, size);
            udpSocket.receive(packet);
            long time = System.nanoTime() - startTime;

            System.out.println("RTT in nanoseconds: " + time);

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                udpSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
