/**
 * Created by Arié on 23/11/2015.
 */
package reseaux;

import ihm.components.Shared_component;
import java.io.IOException;
import java.net.Socket;

public class Client extends AbstractUser {

    private String _ip;
    private int    _port;

    /**
     * Constructeur de client (appel aussi le constructeur parent)
     * ( != connect)
     *
     * @param ip   {String}
     * @param port {int}
     * @param shr  {Shared_component}
     */
    public Client(String ip, int port, Shared_component shr) {
        super(shr);
        _ip = ip;
        _port = port;

    }

    /**
     * Lance la recherche de connection avec le serveur selon le port et l'ip configuré dans le constructeur
     *
     * @return {boolean} etat de la connection
     */
    public boolean connect() {
        System.out.println("Initialisation de la connexion");
        try {
            _socket = new Socket(_ip, _port);
            System.out.println("Connexion établie avec le serveur");
            _de = new DataExchange(_socket, _shared);
            _th = new Thread(_de);
            _th.start();
            return true;
        } catch (IOException e) {
            System.err.println("Aucun serveur à l'écoute du port " + _port);
            return false;
        }
    }
}
