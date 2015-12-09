package reseaux;

import ihm.components.Shared_component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Reception implements Runnable {

    private ObjectInputStream in;
    private Shared_component  _shared;

    public Reception(ObjectInputStream in, Shared_component shr) {
        this.in = in;
        this._shared = shr;
    }

    /**
     * Detecte la reception d'un message
     * indique la reception si le message précédent à déja été pris en compte
     *
     * @callback newData(DataType, Object)
     */
    public void run() {
        while (true) {
            try {
                // Action a effectuer sur reception d'un message
                Data data = (Data) in.readObject();
                while (this._shared._is_message) {
                    WAIT(0.10);
                }
                newData(data.get_type(), data.get_content());

            } catch (IOException e) {
                System.out.println("Partner probably deconnected. Leaving");
                newData(DataType.ERROR, "disconnection");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Unable a understand the message");
            }
        }
    }

    /**
     * Permet d'instancier les variables pour indiquer une reception d'un message
     *
     * @param type {DataType} type de donnée recu
     * @param obj  {Object} donnée en question
     *             Change le _is_message de Shared_component lors de la reception du message
     */
    private void newData(DataType type, Object obj) {
        HashMap<DataType, Object> editMap = new HashMap<>();
        editMap.put(type, obj);
        this._shared._datatype = type;
        this._shared._data_hash = editMap;
        System.out.println("In reception " + type + " <-> " + obj);
        this._shared._is_message = true;
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
}