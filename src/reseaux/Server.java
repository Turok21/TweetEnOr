package reseaux;

/**
 * Created by Arié on 23/11/2015.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

 public static ServerSocket ss = null;
 private static Socket socket = null;

 public static Thread t;

    public static void main(String[] args) {
        try {
            ss = new ServerSocket(14429);
            System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			socket = ss.accept();
			System.out.println("Un client s'est connecté");
			DataExchange de = new DataExchange(socket);
			t = new Thread(de);
			t.start();

        } catch (IOException e) {
            System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
        }
    }
}