package reseaux;

import ihm.components.Shared_component;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Created by Arié on 23/11/2015.
 */

public class Client extends AbstractUser {

    private String _ip;
    private int    _port;

    public Client(String ip, int port, Shared_component shr) {
        super(shr);
        _ip = ip;
        _port = port;

    }

	public boolean connect() {
    	
    	System.out.println("Initialisation de la connexion");
        try {
            _socket = new Socket(_ip, _port);
            System.out.println("Connexion établie avec le serveur");
            _de = new DataExchange(_socket, _shared);
            _th = new Thread(_de);
            _th.start();
            return true;
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + _port);
        	return false;
        }

    }

    public static void main(String[] args) {
        System.out.println("Creation Client");
        Shared_component shr = new Shared_component();
        Client clt = new Client("127.0.0.1", 14500, shr);
        clt.connect();
        WAIT(5);
        clt.initData("CLIENTPSEUDO", "COP21");
        WAIT(20);
        clt.updateStatus(1, 15);
        WAIT(10);
        clt.updateStatus(8, 91);
    }
}
