package reseaux;

import java.io.IOException;
import java.io.ObjectInputStream;



public class Reception implements Runnable {

    private ObjectInputStream in;
    private String message = null;


    public Reception(ObjectInputStream in){
        this.in = in;
    }


    public void run() {
        while(true){
            try {
	            message = (String) in.readObject();
	            System.out.println("message reçu: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}