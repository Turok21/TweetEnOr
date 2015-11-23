package reseaux;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;



public class Emission implements Runnable {

    private ObjectOutputStream out;
    private String message = null;
    private Scanner sc = null;

    public Emission(ObjectOutputStream out) {
        this.out = out;
    }

    public void run() {
          sc = new Scanner(System.in);

          while(true){
                System.out.println("Votre message :");
                message = sc.nextLine();
                try {
					out.writeObject(new Data("message", message));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          }
    }

}