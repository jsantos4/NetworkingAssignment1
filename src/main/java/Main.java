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
                //RTTs
                client.sendTCPMessage(1, InetAddress.getByName(dest), tcpPort);
                client.sendTCPMessage(64, InetAddress.getByName(dest), tcpPort);
                client.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort);

                client.sendUDPMessage(1, InetAddress.getByName(dest), udpPort);
                client.sendUDPMessage(64, InetAddress.getByName(dest), udpPort);
                client.sendUDPMessage(1024, InetAddress.getByName(dest), udpPort);

                System.out.println();

                //Throughput speeds
                System.out.println("Speed of 1K: " + calcThroughput(client.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort), 1024));
                System.out.println("Speed of 16K: " + calcThroughput(client.sendTCPMessage(1024 * 16, InetAddress.getByName(dest), tcpPort), 1024 * 16));
                System.out.println("Speed of 64K: " + calcThroughput(client.sendTCPMessage(1024 * 64, InetAddress.getByName(dest), tcpPort), 1024 * 64));
                System.out.println("Speed of 256K: " + calcThroughput(client.sendTCPMessage(1024 * 256, InetAddress.getByName(dest), tcpPort), 1024 * 256));
                System.out.println("Speed of 1M: " + calcThroughput(client.sendTCPMessage(1024 * 1000, InetAddress.getByName(dest), tcpPort), 1024 * 1000));


            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else if (side == 1) {
            getAddress();
            server.getPort();

            server.receiveTCPMessage(1);
            server.receiveTCPMessage(64);
            server.receiveTCPMessage(1024);

            server.receiveUDPMessage(1);
            server.receiveUDPMessage(64);
            server.receiveUDPMessage(1024);

            server.receiveTCPMessage(1024);
            server.receiveTCPMessage(1024 * 16);
            server.receiveTCPMessage(1024 * 64);
            server.receiveTCPMessage(1024 * 256);
            server.receiveTCPMessage(1024 * 1000);


        }
    }

    private static double calcThroughput(long time, int size) {
        double bits = (double)size/8.0;
        return bits/((double)time/1000000000);
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
