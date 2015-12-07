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

    public void run() {
        while (true) {
            try {
                // Action a effectuer sur reception d'un message
                Data data = (Data) in.readObject();
                while (this._shared._is_message) {

//                    WAIT(1);
                }
                System.out.println("RECEPTION D'UN MESSAGE = " + data.get_type());
                this._shared._is_message = true;
                HashMap<DataType, Object> editMap = new HashMap<>();
                editMap.put(data.get_type(), data.get_content());
                this._shared._data_hash = editMap;

//                switch (data.get_type()) {
//                    case NICKNAME:
//                        System.out.println("Message NICKNAME recu : " + data.get_content());
//                        if(this._shared._is_message = true;
//                        break;
//                    case PROPOSED_KEYWORD:
//                        System.out.println("Message PROPOSED_KEYWORD recu : " + data.get_content());
//                        if(this._shared._is_message = true;
//                        break;
//                    case KEYWORD:
//                        System.out.println("Message KEYWORD recu : " + data.get_content());
//                        if(this._shared._is_message = true;
//                        break;
//                    case SCORE:
//                        System.out.println("Message SCORE recu : " + data.get_content());
//                        if(this._shared._is_message = true;
//                        break;
//                    case WORD_FOUND:
//                        System.out.println("Message WORD_FOUND recu : " + data.get_content());
//                        if(this._shared._is_message = true;
//                        break;
//                    case START:
//                        System.out.println("Message START recu : " + data.get_content());
//                    default:
//                        System.out.println("Unknown = " + data.get_type() + " message recu : " + data.get_content());
//                        break;
//                }
            } catch (IOException e) {
                System.out.println("Partner probably deconnected. Leaving");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Unable a understand the message");
            }
        }
    }
}