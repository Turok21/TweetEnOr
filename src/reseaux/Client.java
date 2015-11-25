package reseaux;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Created by Arié on 23/11/2015.
 */

public class Client extends AbstractUser {

    public Client() {
        super();
        System.out.println("Initialisation de la connexion");
        try {
            _socket = new Socket(Server.url, Server.port);
            System.out.println("Connexion établie avec le serveur");
            _de = new DataExchange(_socket);
            _th = new Thread(_de);
            _th.start();
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + _socket.getLocalPort());
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Demande de connexion");

            _socket = new Socket(Server.url, Server.port);

            System.out.println("Connexion établie avec le serveur"); // Si le message s'affiche c'est que je suis connecté
            DataExchange de = new DataExchange(_socket);
            _th = new Thread(de);
            _th.start();

        } catch (UnknownHostException e) {
            System.err.println("Impossible de se connecter à l'adresse " + _socket.getLocalAddress());
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + _socket.getLocalPort());
        }
    }
}
