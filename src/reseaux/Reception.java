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

    public static boolean WAIT(double sec) {
        try {
            Thread.sleep((int) sec * 10);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        while (true) {
            try {
                // Action a effectuer sur reception d'un message
                Data data = (Data) in.readObject();
                while (this._shared._is_message) {
                    WAIT(0.10);
                }

                HashMap<DataType, Object> editMap = new HashMap<>();
                editMap.put(data.get_type(), data.get_content());
                this._shared._datatype = data.get_type();
                this._shared._data_hash = editMap;
                System.out.println("In reception " + data.get_type() + " <-> " + data.get_content());
                this._shared._is_message = true;

            } catch (IOException e) {
                System.out.println("Partner probably deconnected. Leaving");
                this._shared._datatype = DataType.ERROR;
                this._shared._is_message = true;
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Unable a understand the message");
            }
        }
    }
}