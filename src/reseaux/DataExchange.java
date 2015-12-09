package reseaux;

import ihm.components.Shared_component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DataExchange implements Runnable {
    private Socket             socket = null;
    private ObjectInputStream  in     = null;
    private ObjectOutputStream out    = null;
    private Shared_component _shared;

    private Thread th3;
    private Thread th4;

    /**
     * @param s   {Socket}
     * @param shr {Shared_component} utlisé pour indiqué les échanges de données et les transmettre
     */
    public DataExchange(Socket s, Shared_component shr) {
        socket = s;
        this._shared = shr;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Demarre les 2 thread utilisé pour les échanges de données
     *
     * @callback Reception() pour receptionner les messages recus
     * @callback Emission() pour envoyer de nouvelles donnée et des mises à jour
     */
    public void run() {
        System.out.println("Demarrage de l'échange");
        th3 = new Thread(new Reception(in, this._shared));
        th3.start();
        th4 = new Thread(new Emission(out));
        th4.start();
    }

    /**
     * Permet d'envoyer un message
     *
     * @param type {DataType} type de la donnée à envoyer
     * @param obj  {Object}  les données à envoyer
     */
    public void _emit(DataType type, Object obj) {
        try {
            out.writeObject(new Data(type, obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Destructeur de DataExchange,
     * Stop les thread,
     * ferme les connections et les sockets
     */
    public void finalize() {
        try {
            this.th3.interrupt();
            this.th4.interrupt();
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
