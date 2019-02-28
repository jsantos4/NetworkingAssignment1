import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    private static Socket tcpSocket;
    private static DatagramSocket udpSocket;
    private DataOutputStream os;
    private DataInputStream is;

    public Client() {

    }

    public long sendTCPMessage(int size, InetAddress address, int port) {
        long time = 0;
        try {
            tcpSocket = new Socket(address, port);
            tcpSocket.setSoTimeout(2000);
            byte[] bytes = new byte[size];
            Arrays.fill(bytes, (byte)8 );

            long startTime = System.nanoTime();
            os = new DataOutputStream(tcpSocket.getOutputStream());
            os.write(bytes);

            is = new DataInputStream(tcpSocket.getInputStream());
            is.readByte();
            time = System.nanoTime() - startTime;

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                tcpSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public long[] sendTCPCombos(InetAddress address, int port) {

        long[] tcpTimes = new long[3];
        for (int i = 0; i < 1024; ++i) {
            tcpTimes[0] += sendTCPMessage(1024, address, port);
            if (i == 1024 / 2) {
                System.out.println("50%");
            }
        }
        for (int j = 0; j < 2048; ++j){
            tcpTimes[1] += sendTCPMessage(512, address, port);
            if (j == 2048 / 2)
                System.out.println("50%");
        }
        for (int k = 0; k < 4096; ++k) {
            tcpTimes[2] += sendTCPMessage(256, address, port);
            if (k == 4096 /2)
                System.out.println("50%");
        }
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
