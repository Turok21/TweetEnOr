package reseaux;

/**
 * Created by Arié on 23/11/2015.
 */
import ihm.components.Shared_component;
import utils.KeyWord;
import utils.TweetParser;
import utils.TweetWord;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends AbstractUser {

    //    public static int          port = 14429;
    public static String       url = "127.0.0.1";
    public static ServerSocket ss  = null;

    private int _port;

    public Server(int port, Shared_component shr) {
        super(shr);
        _port = port;
    }

    public void server_sendKeyword(KeyWord _keyWord) {
        sendObject(DataType.KEYWORD, _keyWord);
    }

    public boolean connect() {
        try {
            ss = new ServerSocket(_port);
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());
            _socket = ss.accept();
            System.out.println("Un client s'est connecté");
            DataExchange de = new DataExchange(_socket, this._shared);
            _th = new Thread(de);
            _th.start();
            return true;
        } catch (IOException e) {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
            return false;
        }
    }

    public boolean create_server() {
        try {
            ss = new ServerSocket(_port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean wait_client() {
        try {
            _socket = ss.accept();
            System.out.println("Un client s'est connecté");
            DataExchange de = new DataExchange(_socket);
            _th = new Thread(de);
            _th.start();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("Creation Server");
        Server srv = new Server(14500);
        srv.connect();

        WAIT(5);
        srv.initData("SRVBOSS", "bière");

        /** CALCUL DU SERVEUR  **/
        List<TweetWord> ll = new ArrayList<>();
        ll.add(new TweetWord("blonde", 10));
        ll.add(new TweetWord("blanche", 10));
        ll.add(new TweetWord("brune", 10));
        ll.add(new TweetWord("ambré", 10));
        ll.add(new TweetWord("ninkasi", 10));
        ll.add(new TweetWord("doua", 10));
        ll.add(new TweetWord("mousse", 10));
        ll.add(new TweetWord("jaune", 10));
        ll.add(new TweetWord("alcool", 10));
        ll.add(new TweetWord("jagu", 10));
        /**   FIN DES CALCULS  **/

        WAIT(2);
        KeyWord key = new KeyWord("bière", ll);
        srv.server_sendKeyword(key);

        WAIT(10);
        srv.updateStatus(2, 50);
        WAIT(7);
        srv.updateStatus(2, 50);
    }

}