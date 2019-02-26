import java.io.*;
import java.net.*;

public class Server {

    private static Socket socket;

    public Server(){

    }

    public void receiveMessage() {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
            System.out.println("Server listening");

            socket = serverSocket.accept();
            DataInputStream is = new DataInputStream(socket.getInputStream());
            int size = is.available();

            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(new byte[size]);
            System.out.println("Message sent to the client is " + size);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}