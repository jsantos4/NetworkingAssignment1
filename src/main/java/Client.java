import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    private static Socket tcpSocket;
    private static DatagramSocket udpSocket;
    private String address;
    private int port;


    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public long sendTCPMessage(int size) {
        long time = 0;
        try {
            tcpSocket = new Socket(InetAddress.getByName(address), port);
            tcpSocket.setSoTimeout(2000);
            DataOutputStream os;
            DataInputStream is = null;
            byte[] bytes = new byte[size];
            Arrays.fill(bytes, (byte)8 );

            long startTime = System.nanoTime();
            os = new DataOutputStream(tcpSocket.getOutputStream());
            os.write(bytes);

            while (is == null) {
                try {
                    is = new DataInputStream(tcpSocket.getInputStream());
                } catch (SocketTimeoutException e) {
                    System.out.println("Waiting for response");
                }
            }
            is.readByte();
            time = System.nanoTime() - startTime;

            tcpSocket.close();
            is.close();
            os.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return time;
    }

    public long[] sendTCPCombos() throws IOException{

        long[] tcpTimes = new long[3];
        tcpSocket = new Socket(InetAddress.getByName(address), port);
        DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
        DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
        byte[] oneKB = new byte[1024];
        byte[] halfKB = new byte[512];
        byte[] quarterKB = new byte[256];

        Arrays.fill(oneKB, (byte)8 );
        Arrays.fill(halfKB, (byte)8 );
        Arrays.fill(quarterKB, (byte)8 );


        long start0 = System.nanoTime();
        for (int i = 0; i < 1024; ++i) {
            os.write(oneKB);
            is.readByte();
            tcpTimes[0] += System.nanoTime() - start0;
        }
        long start1 = System.nanoTime();
        for (int j = 0; j < 2048; ++j){
            os.write(halfKB);
            is.readByte();
            tcpTimes[1] += System.nanoTime() - start1;
        }
        long start2 = System.nanoTime();
        for (int k = 0; k < 4096; ++k) {
            os.write(quarterKB);
            is.readByte();
            tcpTimes[2] += System.nanoTime() - start2;
        }

        tcpSocket.close();
        is.close();
        os.close();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            e.printStackTrace();
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
        }

        udpSocket.close();
        return time;
    }

    public long[] sendUDPCombos() throws IOException{
        long[] udpTimes = new long[3];
        udpSocket = new DatagramSocket(port, InetAddress.getByName(address));
        byte[] oneKB = new byte[1024];
        byte[] halfKB = new byte[512];
        byte[] quarterKB = new byte[256];
        byte[] echoArr = new byte[1];

        Arrays.fill(oneKB, (byte)8 );
        Arrays.fill(halfKB, (byte)8 );
        Arrays.fill(quarterKB, (byte)8 );

        DatagramPacket oneKBpacket = new DatagramPacket(oneKB, 1024, InetAddress.getByName(address), port);
        DatagramPacket halfKBpacket = new DatagramPacket(halfKB, 512, InetAddress.getByName(address), port);
        DatagramPacket quarterKBpacket = new DatagramPacket(quarterKB, 256, InetAddress.getByName(address), port);
        DatagramPacket echo = new DatagramPacket(echoArr, 1);

        long start0 = System.nanoTime();
        for (int i = 0; i < 1024; ++i) {
            udpSocket.send(oneKBpacket);
            udpSocket.receive(echo);
            udpTimes[0] = System.nanoTime() - start0;
        }
        long start1 = System.nanoTime();
        for (int j = 0; j < 2048; ++j){
            udpSocket.send(halfKBpacket);
            udpSocket.receive(echo);
            udpTimes[1] = System.nanoTime() - start1;
        }
        long start2 = System.nanoTime();
        for (int k = 0; k < 4096; ++k) {
            udpSocket.send(quarterKBpacket);
            udpSocket.receive(echo);
            udpTimes[2] = System.nanoTime() - start2;

        }

        udpSocket.close();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            e.printStackTrace();
        }

        return udpTimes;
    }
}
