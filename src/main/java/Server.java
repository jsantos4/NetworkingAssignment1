import java.io.*;
import java.net.*;

public class Server {

    private static Socket socket;
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
            socket = serverSocket.accept();
            socket.setSoTimeout(10000);
            DataInputStream is = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[size];
            is.readFully(bytes);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(bytes);
            System.out.println("Size sent to the client: " + size);
            is.close();
            os.close();
            socket.close();
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
            System.out.println("\nTCP combos");
            System.out.println("Receiving 1024s");
            for (int i = 0; i < 1024; ++i) {
                System.out.println(i);
                Socket socket1 = serverSocket.accept();
                DataInputStream is = new DataInputStream(socket1.getInputStream());
                byte[] bytes = new byte[1024];
                is.readFully(bytes);
                DataOutputStream os = new DataOutputStream(socket1.getOutputStream());
                byte[] echo = {(byte)0};
                os.write(echo);
                is.close();
                os.close();
                socket1.close();
            }
            System.out.println("Receiving 512s");
            for (int j = 0; j < 2048; ++j) {
                socket = serverSocket.accept();
                DataInputStream is = new DataInputStream(socket.getInputStream());
                byte[] bytes = new byte[512];
                is.readFully(bytes);
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.write(new byte[1]);
                is.close();
                os.close();
                socket.close();
            }
            System.out.println("Receiving 256s");
            for (int k = 0; k < 4096; ++k) {
                socket = serverSocket.accept();
                DataInputStream is = new DataInputStream(socket.getInputStream());
                byte[] bytes = new byte[256];
                is.readFully(bytes);
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.write(new byte[1]);
                is.close();
                os.close();
                socket.close();
            }

            System.out.println("\nUDP combos");
            System.out.println("Receiving 1024s");
            for (int l = 0; l < 1024; ++l) {

                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes, 1024);
                udpSocket.receive(packet);
                packet = new DatagramPacket(bytes, 1, packet.getAddress(), packet.getPort());
                udpSocket.send(packet);
                udpSocket.close();
            }

            System.out.println("Receiving 512s");
            for (int m = 0; m < 2048; ++m) {

                byte[] bytes = new byte[512];
                DatagramPacket packet = new DatagramPacket(bytes, 512);
                udpSocket.receive(packet);
                packet = new DatagramPacket(bytes, 1, packet.getAddress(), packet.getPort());
                udpSocket.send(packet);
                udpSocket.close();
            }

            System.out.println("Receiving 256s");
            for (int n = 0; n < 4096; ++n) {
                byte[] bytes = new byte[256];
                DatagramPacket packet = new DatagramPacket(bytes, 256);
                udpSocket.receive(packet);
                packet = new DatagramPacket(bytes, 1, packet.getAddress(), packet.getPort());
                udpSocket.send(packet);
                udpSocket.close();
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