package reseaux;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Reception implements Runnable {

    private ObjectInputStream in;

    public Reception(ObjectInputStream in) {
        this.in = in;
    }

    public void run() {
        while (true) {
            try {
                // Action a effectuer sur reception d'un message
                Data data = (Data) in.readObject();
                switch (data.get_type()) {
                    case NICKNAME:
                        System.out.println("Message NICKNAME recu : " + data.get_content());
                        break;
                    case PROPOSED_KEYWORD:
                        System.out.println("Message PROPOSED_KEYWORD recu : " + data.get_content());
                        break;
                    case KEYWORD:
                        System.out.println("Message KEYWORD recu : " + data.get_content());
                        break;
                    case SCORE:
                        System.out.println("Message SCORE recu : " + data.get_content());
                        break;
                    case WORD_FOUND:
                        System.out.println("Message WORD_FOUND recu : " + data.get_content());
                        break;
                    case START:
                        System.out.println("Message START recu : " + data.get_content());
                    default:
                        System.out.println("Unknown message recu : " + data.get_content());
                        break;
                }

            } catch (IOException e) {
                System.out.println("Partner probably deconnected. Leaving");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Unable a understand the message");
            }
        }
    }
}