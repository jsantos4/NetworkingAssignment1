import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main (String [] args){
        System.out.println("Side? (0 == client)");
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Client client = new Client();
        Server server = new Server();

        if (side == 0) {
            System.out.println("Destination? (Address <ENTER> Port)");
            String dest = scanner.next();
            int port = scanner.nextInt();
            try {
                client.sendMessage(1, InetAddress.getByName(dest), port);
                client.sendMessage(64, InetAddress.getByName(dest), port);
                client.sendMessage(1024, InetAddress.getByName(dest), port);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else if (side == 1) {
            getAddress();
            server.getPort();

            server.receiveMessage();
            server.receiveMessage();
            server.receiveMessage();

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
            System.out.println("Public IP Address: " + systemipaddress +"\n");
    }
}
