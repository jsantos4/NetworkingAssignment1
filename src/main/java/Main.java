import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {

    public static void main (String [] args){
        System.out.println("Side? (0 == client)");
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Server server = new Server();
        long[] rttTimes = new long[6];
        double[] throughputSpeeds = new double[5];
        long[] tcpVariations;
        long[] udpVariations;

        if (side == 0) {
            System.out.println("Destination?");
            String dest = scanner.next();
            System.out.println("TCP Port?");
            int tcpPort = scanner.nextInt();
            System.out.println("UDP Port?");
            int udpPort = scanner.nextInt();
            Client tcpClient = new Client(dest, tcpPort);
            Client udpClient = new Client(dest, udpPort);


            try {

                //RTTs----------------------------------------------------------------------------------------------------------------------------------
                System.out.println("\nRTTs");
                rttTimes[0] = tcpClient.sendTCPMessage(1);
                rttTimes[1] = tcpClient.sendTCPMessage(64);
                rttTimes[2] = tcpClient.sendTCPMessage(1024);
                rttTimes[3] = udpClient.sendUDPMessage(1, InetAddress.getByName(dest), udpPort);
                rttTimes[4] = udpClient.sendUDPMessage(64, InetAddress.getByName(dest), udpPort);
                rttTimes[5] = udpClient.sendUDPMessage(1024, InetAddress.getByName(dest), udpPort);

                System.out.println("TCPs 1, 64, 1024:");
                System.out.println("RTT in nanoseconds: " + rttTimes[0]);
                System.out.println("RTT in nanoseconds: " + rttTimes[1]);
                System.out.println("RTT in nanoseconds: " + rttTimes[2]);
                System.out.println("UDPs 1, 64, 1024:");
                System.out.println("RTT in nanoseconds: " + rttTimes[3]);
                System.out.println("RTT in nanoseconds: " + rttTimes[4]);
                System.out.println("RTT in nanoseconds: " + rttTimes[5]);

                //Throughput speeds-------------------------------------------------------------------------------------------------------------------
                System.out.println("\nThroughput speeds");
                throughputSpeeds[0] = calcThroughput(tcpClient.sendTCPMessage(1024), 1024);
                throughputSpeeds[1] = calcThroughput(tcpClient.sendTCPMessage(1024 * 16), 1024 * 16);
                throughputSpeeds[2] = calcThroughput(tcpClient.sendTCPMessage(1024 * 64), 1024 * 64);
                throughputSpeeds[3] = calcThroughput(tcpClient.sendTCPMessage(1024 * 256), 1024 * 256);
                throughputSpeeds[4] = calcThroughput(tcpClient.sendTCPMessage(1024 * 1000), 1024 * 1000);

                System.out.println("Speed of 1K: " + throughputSpeeds[0] + " Mb/s");
                System.out.println("Speed of 16K: " + throughputSpeeds[1] + " Mb/s");
                System.out.println("Speed of 64K: " + throughputSpeeds[2] + " Mb/s");
                System.out.println("Speed of 256K: " + throughputSpeeds[3] + " Mb/s");
                System.out.println("Speed of 1M: " + throughputSpeeds[4] + " Mb/s");

                //Combinations of 1MB total messages in different sizes--------------------------------------------------------------------------------
                System.out.println("\n1MB variations");
                tcpVariations = tcpClient.sendTCPCombos();
                udpVariations = udpClient.sendUDPCombos();

                System.out.println("\nTCPs 1024 x 1024MB, 2048 x 512MB, 4096 x 256MB:");
                System.out.println("Total of all RTTs: " + nanoToSec(tcpVariations[0]));
                System.out.println("Total of all RTTs: " + nanoToSec(tcpVariations[1]));
                System.out.println("Total of all RTTs: " + nanoToSec(tcpVariations[2]));
                System.out.println("UDPs 1024 x 1024MB, 2048 x 512MB, 4096 x 256MB:");
                System.out.println("Total of all RTTs: " + nanoToSec(udpVariations[0]));
                System.out.println("Total of all RTTs: " + nanoToSec(udpVariations[1]));
                System.out.println("Total of all RTTs: " + nanoToSec(udpVariations[2]));


            } catch (IOException e) {
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

            server.receiveCombos();


        }
    }

    private static double calcThroughput(long time, int size) {
        return (((double) size * 8.0)/nanoToSec(time)) / 1000000.0;
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

    private static double nanoToSec(long time) {
        long millis = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
        return (double) millis / 1000.0;
    }
}
