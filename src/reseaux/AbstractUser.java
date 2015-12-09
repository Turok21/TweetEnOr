/**
 * Created by Arié on 25/11/2015.
 */
package reseaux;

import ihm.components.Shared_component;
import java.io.IOException;
import java.net.Socket;

/**
 * Class Abstrait sur User    (User pouvant etre un Client ou un Server)
 */
public abstract class AbstractUser implements User {

    public Thread           _th;
    public DataExchange     _de;
    public Shared_component _shared;
    public Socket _socket = null;

    /**
     * @param shr {Shared_component} Utilisé pour le partage des données entre IHM et la partie Reseaux
     */
    public AbstractUser(Shared_component shr) {
        _socket = null;
        this._shared = shr;
    }

    /**
     * @return {DataExchange}
     */
    public DataExchange getDataExchange() {
        return this._de;
    }

    /**
     * Fonction bloquante appelé par l'ihm et permet de savoir quand un message est recus
     *
     * @return {boolean} true if new message  // false if disconnection
     */
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

    /**
     * Destructeur de AbstractUser
     * (appel aussi le destructeur de DataExchange)
     */
    public void finalize() {
        try {
            this._socket.close();
            this._de.finalize();
            this._th.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Object Abstract User been destroyed");
    }
}
