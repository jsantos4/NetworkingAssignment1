import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main (String [] args){
        System.out.println("Side?");
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Client client = new Client();
        Server server = new Server();

        if (side == 0) {
            System.out.println("Destination?");
            String dest = scanner.next();
            client.sendMessage(1, dest);
        } else if (side == 1) {
            getAddress();
            server.receiveMessage(1);
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
