import java.io.*;
import java.net.*;

public class Server {

    private static Socket socket;

    public Server(){

    }

    public void receiveMessage(int size) {
        try {

            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");

            //Server is running always. This is done using this while(true) loop
            while(true) {
                //Reading the message from the client
                socket = serverSocket.accept();
                DataInputStream is = new DataInputStream(socket.getInputStream());

                //Sending the response back to the client.
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.write(new byte[size]);
                System.out.println("Message sent to the client is " + size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}