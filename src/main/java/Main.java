import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main (String [] args){
        System.out.println("Side? (0 == client)");
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Client rttClient = new Client();
        Server rttServer = new Server();

        if (side == 0) {
            System.out.println("Destination?");
            String dest = scanner.next();
            System.out.println("TCP Port?");
            int tcpPort = scanner.nextInt();
            System.out.println("UDP Port?");
            int udpPort = scanner.nextInt();

            try {
                //RTTs
                rttClient.sendTCPMessage(1, InetAddress.getByName(dest), tcpPort);
                rttClient.sendTCPMessage(64, InetAddress.getByName(dest), tcpPort);
                rttClient.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort);

                rttClient.sendUDPMessage(1, InetAddress.getByName(dest), udpPort);
                rttClient.sendUDPMessage(64, InetAddress.getByName(dest), udpPort);
                rttClient.sendUDPMessage(1024, InetAddress.getByName(dest), udpPort);

                System.out.println();

                //Throughput speeds
                System.out.println("Speed of 1K: " + calcThroughput(rttClient.sendTCPMessage(1024, InetAddress.getByName(dest), tcpPort), 1024) + " bits/S");
                System.out.println("Speed of 16K: " + calcThroughput(rttClient.sendTCPMessage(1024 * 16, InetAddress.getByName(dest), tcpPort), 1024 * 16) + " bits/S");
                System.out.println("Speed of 64K: " + calcThroughput(rttClient.sendTCPMessage(1024 * 64, InetAddress.getByName(dest), tcpPort), 1024 * 64) + " bits/S");
                System.out.println("Speed of 256K: " + calcThroughput(rttClient.sendTCPMessage(1024 * 256, InetAddress.getByName(dest), tcpPort), 1024 * 256) + " bits/S");
                System.out.println("Speed of 1M: " + calcThroughput(rttClient.sendTCPMessage(1024 * 1000, InetAddress.getByName(dest), tcpPort), 1024 * 1000) + " bits/S");


            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else if (side == 1) {
            getAddress();
            rttServer.getPort();

            rttServer.receiveTCPMessage(1);
            rttServer.receiveTCPMessage(64);
            rttServer.receiveTCPMessage(1024);

            rttServer.receiveUDPMessage(1);
            rttServer.receiveUDPMessage(64);
            rttServer.receiveUDPMessage(1024);

            rttServer.receiveTCPMessage(1024);
            rttServer.receiveTCPMessage(1024 * 16);
            rttServer.receiveTCPMessage(1024 * 64);
            rttServer.receiveTCPMessage(1024 * 256);
            rttServer.receiveTCPMessage(1024 * 1000);


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

    private static long nanoToSec(long time) {
        return TimeUnit.NANOSECONDS.convert(time, TimeUnit.SECONDS);
    }
}
