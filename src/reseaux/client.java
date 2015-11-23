package reseaux;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Created by Arié on 23/11/2015.
 */
public class Client {
    private Socket         _socket;
    private BufferedReader _in;
    private PrintWriter    _out;

    public Client(Socket _socket, BufferedReader _in, PrintWriter _out) {
        this._socket = _socket;
        this._in = _in;
        this._out = _out;
    }

    public static void main(String[] args) {
        new Accueil_IHM();
    }
}
