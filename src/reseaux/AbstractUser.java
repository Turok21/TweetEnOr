package reseaux;

import java.io.IOException;
import java.net.Socket;
/**
 * Created by Arié on 25/11/2015.
 */

public abstract class AbstractUser implements User {

    public static Thread _th;
    public static Socket _socket = null;
    public DataExchange _de;

    public AbstractUser() {
        _socket = null;
//        System.out.println("Initialisation de la connexion");
//        try {
//            _socket = new Socket(Server.url, Server.port);
//            System.out.println("Connexion établie avec le serveur");
//            _de = new DataExchange(_socket);
//            _th = new Thread(_de);
//            _th.start();
//        } catch (IOException e) {
//            System.err.println("Aucun serveur à l'écoute du port " + _socket.getLocalPort());
//        }
    }

    public DataExchange getDataExchange() {
        return this._de;
    }

    @Override
    public void sendObject(DataType type, Object obj) {
        this._de._emit(type, obj);
    }

    @Override
    public void initData(String nickname, String mot) {
        sendObject(DataType.NICKNAME, mot);
        sendObject(DataType.PROPOSED_KEYWORD, mot);
    }

    @Override
    public void updateStatus(int nbWordFound, int score) {
        sendObject(DataType.WORD_FOUND, nbWordFound);
        sendObject(DataType.SCORE, score);
    }
}
