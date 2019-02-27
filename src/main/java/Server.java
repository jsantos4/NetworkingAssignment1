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
            DataInputStream is = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[size];
            is.readFully(bytes);

            if (size > 1024) {
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.write(new byte[1]);
                System.out.println("Size sent to the client: 1");

            } else {
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.write(bytes);
                System.out.println("Size sent to the client: " + size);
            }
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

    public void getPort() {
        System.out.println("TCP Port: " + serverSocket.getLocalPort());
        System.out.println("UDP Port: " + udpSocket.getLocalPort());
    }
}