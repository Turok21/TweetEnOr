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
                    case "Nickname":
                        System.out.println("Message Nickname recu : " + data.get_content());
                        break;
                    case "Word":
                        System.out.println("Message Word recu : " + data.get_content());
                        break;
                    case "KeyWord":
                        System.out.println("Message KeyWord recu : " + data.get_content());
                        break;
                    case "Score":
                        System.out.println("Message Score recu : " + data.get_content());
                        break;
                    case "NbWordFounded":
                        System.out.println("Message NbWordFounded recu : " + data.get_content());
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