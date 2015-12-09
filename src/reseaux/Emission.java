package reseaux;

import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Emission implements Runnable {

    private ObjectOutputStream out;
    private Scanner sc = null;

    public Emission(ObjectOutputStream out) {
        this.out = out;
    }

    public void run() {
        sc = new Scanner(System.in);
    }
}