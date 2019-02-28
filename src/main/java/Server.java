import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket serverSocket;
    private DatagramSocket udpSocket;

    public Server(){
        try {
            serverSocket = new ServerSocket(0);
            udpSocket = new DatagramSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveTCPMessage(int size) {
        try {
            System.out.println("Server listening");
            Socket tcpSocket = serverSocket.accept();
            DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
            byte[] bytes = new byte[size];
            is.readFully(bytes);
            DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
            os.write(bytes);
            System.out.println("Size sent to the client: " + size);
            is.close();
            os.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveUDPMessage(int size) {

        try {
            System.out.println("Server listening");

            byte[] bytes = new byte[size];
            DatagramPacket packet = new DatagramPacket(bytes, size);
            udpSocket.receive(packet);

            packet = new DatagramPacket(bytes, size, packet.getAddress(), packet.getPort());
            udpSocket.send(packet);
            System.out.println("Size sent to the client: " + size);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void receiveCombos() {
        try {
            Socket tcpSocket = null;
            System.out.println("\nTCP combos");
            System.out.println("Receiving 1024s");
            while (tcpSocket == null) {
                try {
                    tcpSocket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    System.out.println("Accept timed out: trying again");
                }
            }
            for (int i = 0; i < 1024; ++i) {
                System.out.println(i);
                if (i == 1024 / 2) {
                    System.out.println("50%");
                }
                DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
                byte[] bytes = new byte[1024];
                is.readFully(bytes);
                DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
                byte[] echo = {(byte)0};
                os.write(echo);
            }
            System.out.println("Receiving 512s");
            for (int j = 0; j < 2048; ++j) {
                if (j == 2048 / 2) {
                    System.out.println("50%");
                }
                tcpSocket = serverSocket.accept();
                DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
                byte[] bytes = new byte[512];
                is.readFully(bytes);
                DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
                os.write(new byte[1]);
            }
            System.out.println("Receiving 256s");
            for (int k = 0; k < 4096; ++k) {
                if (k == 4096 / 2) {
                    System.out.println("50%");
                }
                tcpSocket = serverSocket.accept();
                DataInputStream is = new DataInputStream(tcpSocket.getInputStream());
                byte[] bytes = new byte[256];
                is.readFully(bytes);
                DataOutputStream os = new DataOutputStream(tcpSocket.getOutputStream());
                os.write(new byte[1]);
            }

            tcpSocket.close();

            System.out.println("\nUDP combos");
            System.out.println("Receiving 1024s");
            for (int l = 0; l < 1024; ++l) {
                if (l == 1024 / 2) {
                    System.out.println("50%");
                }
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes, 1024);
                udpSocket.receive(packet);
                packet = new DatagramPacket(bytes, 1, packet.getAddress(), packet.getPort());
                udpSocket.send(packet);
            }

            System.out.println("Receiving 512s");
            for (int m = 0; m < 2048; ++m) {
                if (m == 2048 / 2) {
                    System.out.println("50%");
                }
                byte[] bytes = new byte[512];
                DatagramPacket packet = new DatagramPacket(bytes, 512);
                udpSocket.receive(packet);
                packet = new DatagramPacket(bytes, 1, packet.getAddress(), packet.getPort());
                udpSocket.send(packet);
            }

            System.out.println("Receiving 256s");
            for (int n = 0; n < 4096; ++n) {
                if (n == 4096 / 2) {
                    System.out.println("50%");
                }
                byte[] bytes = new byte[256];
                DatagramPacket packet = new DatagramPacket(bytes, 256);
                udpSocket.receive(packet);
                packet = new DatagramPacket(bytes, 1, packet.getAddress(), packet.getPort());
                udpSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPort() {
        System.out.println("TCP Port: " + serverSocket.getLocalPort());
        System.out.println("UDP Port: " + udpSocket.getLocalPort());
    }
}