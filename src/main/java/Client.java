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
            Arrays.fill(bytes, (byte) ThreadLocalRandom.current().nextInt(2,256));

            long startTime = System.nanoTime();
            DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
            os.write(bytes);

            DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
            System.out.println(is.available());
            if (is.readByte() == 0) {
                time = System.nanoTime() - startTime;
            } else {
                is.readFully(bytes);
                time = System.nanoTime() - startTime;
            }
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

    public long[] sendTCPcombos(InetAddress address, int port) {
        long[] tcpTimes = new long[3];
        for (int i = 0; i < 1024; ++i)
            tcpTimes[0] += sendTCPMessage(1024, address, port);
        for (int j = 0; j < 2048; ++j)
            tcpTimes[1] += sendTCPMessage(512, address, port);
        for (int k = 0; k < 4096; ++k)
            tcpTimes[2] += sendTCPMessage(256, address, port);

        return tcpTimes;
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

    public long[] sendUDPcombos(InetAddress address, int port) {
        long[] udpTimes = new long[3];
        for (int i = 0; i < 1024; ++i)
            udpTimes[0] += sendUDPMessage(1024, address, port);
        for (int j = 0; j < 2048; ++j)
            udpTimes[1] += sendUDPMessage(512, address, port);
        for (int k = 0; k < 4096; ++k)
            udpTimes[2] += sendUDPMessage(256, address, port);

        return udpTimes;
    }
}
