package reseaux;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Emission implements Runnable {

    private ObjectOutputStream out;
    private String  message = null;
    private Scanner sc      = null;

    public Emission(ObjectOutputStream out) {
        this.out = out;
    }

    public void run() {
        sc = new Scanner(System.in);

        while (true) {
            System.out.println("Votre message :");
            message = sc.nextLine();
            System.out.println(message.split(":")[0]);
            System.out.println(message.split(":")[1]);
            try {
                out.writeObject(new Data(DataType.TEST, message));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void sendData(DataType dataType, int newScore) {
        try {
            out.writeObject(new Data(dataType, newScore));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}