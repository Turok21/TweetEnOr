package reseaux;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Created by Arié on 23/11/2015.
 */

public class Client extends AbstractUser {

    public Client(String ip, int port) {
        super();
        System.out.println("Initialisation de la connexion");
        try {
            _socket = new Socket(ip, port);
            System.out.println("Connexion établie avec le serveur");
            _de = new DataExchange(_socket);
            _th = new Thread(_de);
            _th.start();
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + _socket.getLocalPort());
        }
    }

    public static void main(String[] args) {
        System.out.println("Creation Client");
        Client clt = new Client("127.0.0.1", 14500);

        WAIT(5);
        clt.initData("CLIENTPSEUDO", "COP21");
        WAIT(20);
        clt.updateStatus(1, 15);
        WAIT(10);
        clt.updateStatus(8, 91);
    }
}
