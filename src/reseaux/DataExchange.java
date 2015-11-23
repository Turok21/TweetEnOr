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
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush(); // Flush to avoid blocking until first data
			in = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
			System.out.println("Demarrage de l'échange");

			Thread t3 = new Thread(new Reception(in));
			t3.start();
			Thread t4 = new Thread(new Emission(out));
			t4.start();
	}

	public void emit(String type, Object obj) {
		try {
			out.writeObject(new Data(type, obj));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
