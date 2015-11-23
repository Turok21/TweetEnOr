package reseaux;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Created by Arié on 23/11/2015.
 */

public class Client {

    public static Socket socket = null;
    public static Thread t1;

    public static void main(String[] args) {
    	try {
	        System.out.println("Demande de connexion");

	        socket = new Socket("127.0.0.1", 14429);

	        System.out.println("Connexion établie avec le serveur"); // Si le message s'affiche c'est que je suis connecté
	        t1 = new Thread(new DataExchange(socket));
	        t1.start();

	    } catch (UnknownHostException e) {
	      System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
	    } catch (IOException e) {
	      System.err.println("Aucun serveur à l'écoute du port "+socket.getLocalPort());
	    }
    }
}
