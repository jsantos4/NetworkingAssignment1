import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main (String [] args){
        System.out.println("Side? (0 == client)");
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Client Client = new Client();
        Server Server = new Server();
        double[] throughputSpeeds = new double[5];

        if (side == 0) {
            System.out.println("Destination?");
            String dest = scanner.next();
            System.out.println("TCP Port?");
            int tcpPort = scanner.nextInt();
            System.out.println("UDP Port?");
            int udpPort = scanner.nextInt();


            try {
                //RTTs
                System.out.println("TCPs 1, 64, 1024:");
                System.out.println("RTT in nanoseconds: " + Client.sendTCPMessage(1, InetAddress.getByName(dest), tcpPort));
                System.out.println("RTT in nanoseconds: " + Client.sendTCPMessage(64, InetAddress.getByName(dest), tcpPort));
                System.out.println("RTT in nanoseconds: " + Client.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort));

                System.out.println("UDPs 1, 64, 1024:");
                System.out.println("RTT in nanoseconds: " + Client.sendUDPMessage(1, InetAddress.getByName(dest), udpPort));
                System.out.println("RTT in nanoseconds: " + Client.sendUDPMessage(64, InetAddress.getByName(dest), udpPort));
                System.out.println("RTT in nanoseconds: " + Client.sendUDPMessage(1024, InetAddress.getByName(dest), udpPort));

                System.out.println("\nThroughput speeds");


                //Throughput speeds
                throughputSpeeds[0] = calcThroughput(Client.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort), 1024);
                throughputSpeeds[1] = calcThroughput(Client.sendTCPMessage(1024 * 16, InetAddress.getByName(dest), tcpPort), 1024 * 16);
                throughputSpeeds[2] = calcThroughput(Client.sendTCPMessage(1024 * 64, InetAddress.getByName(dest), tcpPort), 1024 * 64);
                throughputSpeeds[3] = calcThroughput(Client.sendTCPMessage(1024 * 256, InetAddress.getByName(dest), tcpPort), 1024 * 256);
                throughputSpeeds[4] = calcThroughput(Client.sendTCPMessage(1024 * 1000, InetAddress.getByName(dest), tcpPort), 1024 * 1000);

                System.out.println("Speed of 1K: " + throughputSpeeds[0] + " bits/S");
                System.out.println("Speed of 16K: " + throughputSpeeds[1] + " bits/S");
                System.out.println("Speed of 64K: " + throughputSpeeds[2] + " bits/S");
                System.out.println("Speed of 256K: " + throughputSpeeds[3] + " bits/S");
                System.out.println("Speed of 1M: " + throughputSpeeds[4] + " bits/S");


            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else if (side == 1) {
            getAddress();
            Server.getPort();

            Server.receiveTCPMessage(1);
            Server.receiveTCPMessage(64);
            Server.receiveTCPMessage(1024);

            Server.receiveUDPMessage(1);
            Server.receiveUDPMessage(64);
            Server.receiveUDPMessage(1024);

            Server.receiveTCPMessage(1024);
            Server.receiveTCPMessage(1024 * 16);
            Server.receiveTCPMessage(1024 * 64);
            Server.receiveTCPMessage(1024 * 256);
            Server.receiveTCPMessage(1024 * 1000);


        }
    }

    private static double calcThroughput(long time, int size) {
        double bits = (double)size * 8.0;
        return bits/nanoToSec(time);
    }

    private static void getAddress() {

            // Find public IP address
            String systemipaddress = "";
            try {
                URL url_name = new URL("http://bot.whatismyipaddress.com");

                BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

                // reads system IPAddress
                systemipaddress = sc.readLine().trim();
            }
            catch (Exception e) {
                systemipaddress = "Cannot Execute Properly";
            }
            System.out.println("Public IP Address: " + systemipaddress);
    }

    private static long nanoToSec(long time) {
        return TimeUnit.NANOSECONDS.convert(time, TimeUnit.SECONDS);
    }
}
