package reseaux;

/**
 * Created by Arié on 23/11/2015.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static  int          port   = 14429;
    public static  String       url    = "127.0.0.1";
    public static  ServerSocket ss     = null;
    private static Socket       socket = null;

    public static Thread th;
    public static DataExchange de;

    public Server(int port) {
        System.out.println("Initialisation de la connexion");
        try {
            ss = new ServerSocket(port);
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());
            socket = ss.accept();
            System.out.println("Un client s'est connecté");
            de = new DataExchange(socket);
            th = new Thread(de);
            th.start();
        } catch (IOException e) {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
        }
    }

    public DataExchange getDataExchange() {
        return Client.de;
    }

    public static void main(String[] args) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());
            socket = ss.accept();
            System.out.println("Un client s'est connecté");
            de = new DataExchange(socket);
            th = new Thread(de);
            th.start();
            de.emit(DataType.TEST, "Ceci est un envoie de données");
        } catch (IOException e) {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
        }
    }
}