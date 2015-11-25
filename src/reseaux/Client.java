package reseaux;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Created by Arié on 23/11/2015.
 */

public class Client {

    public static Socket socket = null;
    public static Thread th1;
    public DataExchange de;

    public Client(String url, int port) {
        System.out.println("Initialisation de la connexion");
        try {
            socket = new Socket(Server.url, Server.port);
            System.out.println("Connexion établie avec le serveur");
            de = new DataExchange(socket);
            th1 = new Thread(de);
            th1.start();
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + socket.getLocalPort());
        }
    }

    public DataExchange getDataExchange() {
        return this.de;
    }

    public void sendObject(DataType type, Object obj) {
        this.de._emit(type, obj);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Demande de connexion");

            socket = new Socket(Server.url, Server.port);

            System.out.println("Connexion établie avec le serveur"); // Si le message s'affiche c'est que je suis connecté
            DataExchange de = new DataExchange(socket);
            th1 = new Thread(de);
            th1.start();

        } catch (UnknownHostException e) {
            System.err.println("Impossible de se connecter à l'adresse " + socket.getLocalAddress());
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + socket.getLocalPort());
        }
    }
}
