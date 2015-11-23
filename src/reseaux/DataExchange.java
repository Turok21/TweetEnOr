package reseaux;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DataExchange implements Runnable {
	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;

	public DataExchange(Socket s){
		socket = s;
	}

	public void run() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());


			System.out.println("Demarrage de l'échange");

			Thread t3 = new Thread(new Reception(in));
			t3.start();
			Thread t4 = new Thread(new Emission(out));
			t4.start();

		} catch (IOException e) {
			System.err.println("l'autre s'est déconnecté ");
		}
	}
}
