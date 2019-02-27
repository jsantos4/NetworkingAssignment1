import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {

    public static void main (String [] args){
        System.out.println("Side? (0 == client)");
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Client client = new Client();
        Server server = new Server();

        if (side == 0) {
            System.out.println("Destination?");
            String dest = scanner.next();
            System.out.println("TCP Port?");
            int tcpPort = scanner.nextInt();
            System.out.println("UDP Port?");
            int udpPort = scanner.nextInt();

            try {
                client.sendTCPMessage(1, InetAddress.getByName(dest), tcpPort);
                client.sendTCPMessage(64, InetAddress.getByName(dest), tcpPort);
                client.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort);

                client.sendUDPMessage(1, InetAddress.getByName(dest), udpPort);
                client.sendUDPMessage(64, InetAddress.getByName(dest), udpPort);
                client.sendUDPMessage(1024, InetAddress.getByName(dest), udpPort);


            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else if (side == 1) {
            getAddress();
            server.getPort();

            server.receiveTCPMessage();
            server.receiveTCPMessage();
            server.receiveTCPMessage();

            server.receiveUDPMessage(1);
            server.receiveUDPMessage(64);
            server.receiveUDPMessage(1024);

        }
    }

    private static void getAddress() {

            // Find public IP address
            String systemipaddress = "";
            try {
                URL url_name = new URL("http://bot.whatismyipaddress.com");

                BufferedReader sc =
                        new BufferedReader(new InputStreamReader(url_name.openStream()));

                // reads system IPAddress
                systemipaddress = sc.readLine().trim();
            }
            catch (Exception e) {
                systemipaddress = "Cannot Execute Properly";
            }
            System.out.println("Public IP Address: " + systemipaddress);
    }
}
