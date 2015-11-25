package reseaux;

/**
 * Created by Arié on 23/11/2015.
 */
import utils.KeyWord;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends AbstractUser {

    public static int          port = 14429;
    public static String       url  = "127.0.0.1";
    public static ServerSocket ss   = null;

    public Server() {
        super();
        System.out.println("Initialisation de la connexion");
        try {
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());
            _socket = ss.accept();
            System.out.println("Un client s'est connecté");
            _de = new DataExchange(_socket);
            _th = new Thread(_de);
            _th.start();
        } catch (IOException e) {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
        }
    }

    public void server_sendKeyword(KeyWord _keyWord) {
        sendObject(DataType.KEYWORD, _keyWord);
    }

    public static void main(String[] args) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());
            _socket = ss.accept();
            System.out.println("Un client s'est connecté");
            DataExchange de = new DataExchange(_socket);
            _th = new Thread(de);
            _th.start();
            de._emit(DataType.TEST, "Ceci est un envoie de données");
        } catch (IOException e) {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
        }
    }
}