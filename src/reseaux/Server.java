/**
 * Created by Arié on 23/11/2015.
 */
package reseaux;

import ihm.components.Shared_component;
import utils.KeyWord;
import java.io.IOException;
import java.net.ServerSocket;

public class Server extends AbstractUser {

    public ServerSocket ss = null;
    private int _port;

    /**
     * Constructeur du server (appel aussi le constructeur parent)
     * ( != create_server & wait_client)
     *
     * @param port {int] Le port sur lequel ouvrir la connection
     * @param shr  {Shared_component}
     */
    public Server(int port, Shared_component shr) {
        super(shr);
        _port = port;
    }

    /**
     * Permet de créer le serveur en initialisant ServerSocket
     *
     * @return {boolean} Réussite ou echec de a connection
     */
    public boolean create_server() {
        try {
            ss = new ServerSocket(_port);
            return true;
        } catch (IOException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Permet d'envoyer l'object Keyword au client une fois la génération du keyword terminée
     *
     * @param _keyWord {KeyWord} le key word à envoyer
     */
    public void server_sendKeyword(KeyWord _keyWord) {
        sendObject(DataType.KEYWORD, _keyWord);
    }

    /**
     * Start le thread et attend la connection d'un client et return true lors de sa connection
     *
     * @return {boolean} Connection d'un client ou echec lors du start
     */
    public boolean wait_client() {
        try {
            _socket = ss.accept();
            _de = new DataExchange(_socket, this._shared);
            _th = new Thread(_de);
            _th.start();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * destructeur du serveur pour fermer la connection
     */
    public void finalize() {
        try {
            this.ss.close();
            super.finalize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}