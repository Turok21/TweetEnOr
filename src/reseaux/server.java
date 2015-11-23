package reseaux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by Arié on 23/11/2015.
 */
public class Server {
    private ServerSocket   _serveurSocket;
    private Socket         _socketOfServeur;
    private BufferedReader _in;
    private PrintWriter    _out;

    private int port = 14500;

    public Server() {
        try {

            this._serveurSocket = new ServerSocket(port);
            this._socketOfServeur = new Socket(InetAddress.getLocalHost(), port);
            this._in = _in;
            this._out = new PrintWriter(_socketOfServeur.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        srvSocket = new ServerSocket()
//        Server srv = new Server();
    }
}
