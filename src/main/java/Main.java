import java.util.Scanner;

public class Main {
    public static void main (String [] args){
        Scanner scanner = new Scanner(System.in);
        int side = scanner.nextInt();
        Client client = new Client();
        Server server = new Server();

        if (side == 0)
            client.sendMessage(1);
        else if (side == 1)
            server.receiveMessage(1);
    }
}
