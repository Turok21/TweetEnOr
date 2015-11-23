package reseaux;

import java.io.IOException;
import java.io.ObjectInputStream;



public class Reception implements Runnable {

    private ObjectInputStream in;


    public Reception(ObjectInputStream in){
        this.in = in;
    }


    public void run() {
        while(true){
            try {
            	// Action a effectuer sur reception d'un message
	            Data data = (Data) in.readObject();
	            if(data.getType().equals("message")) {
	            	 System.out.println("message reçu: " + (String)data.getContent());
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