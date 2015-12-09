package reseaux;

import ihm.components.Shared_component;
import java.net.Socket;
/**
 * Created by Ari� on 25/11/2015.
 */

public abstract class AbstractUser implements User {

    public static Thread           _th;
    public        DataExchange     _de;
    public        Shared_component _shared;
    public static Socket _socket = null;

    public AbstractUser(Shared_component shr) {
        _socket = null;
        this._shared = shr;
//        System.out.println("Initialisation de la connexion");
//        try {
//            _socket = new Socket(Server.url, Server.port);
//            System.out.println("Connexion �tablie avec le serveur");
//            _de = new DataExchange(_socket);
//            _th = new Thread(_de);
//            _th.start();
//        } catch (IOException e) {
//            System.err.println("Aucun serveur � l'�coute du port " + _socket.getLocalPort());
//        }
    }

    public DataExchange getDataExchange() {
        return this._de;
    }

    public boolean newMessage() {
        while (!this._shared._is_message) {
            WAIT(0.10);
        }
        if (this._shared._datatype == DataType.ERROR) {
            return false;
        }
        this._shared._is_message = false;
        return true;
    }

    @Override
    public void sendObject(DataType type, Object obj) {
        this._de._emit(type, obj);
    }

    @Override
    public void initData(String nickname, String mot) {
        sendObject(DataType.NICKNAME, nickname);
        sendObject(DataType.PROPOSED_KEYWORD, mot);
    }

    @Override
    public void updateStatus(int nbWordFound, int score) {
        sendObject(DataType.WORD_FOUND, nbWordFound);
        sendObject(DataType.SCORE, score);
    }

    public static boolean WAIT(double sec) {
        try {
            Thread.sleep((int) sec * 10);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void finalize() {
        System.out.println("Object Abstract User been destroyed");
    }
}
