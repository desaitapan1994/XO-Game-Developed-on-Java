package client;

import java.io.IOException;
import java.net.Socket;
import utils.SocketAction;

/**
 * This class is responsible for creating the socket for the client.
 *
 * @author Antou
 */
public class ClientConnection extends SocketAction {

    // local ip
    private static final String IP = "127.0.0.1";
    // Port used in the game
    private static final Integer port = 8080;
    // instance of this class
    private static ClientConnection _instance = null;

    /**
     * The constructor here takes a socket to initiate the instance with.
     *
     * @param sock the socket used in the connection.
     */
    private ClientConnection(Socket sock) {
        super(sock, false);
    }

    /**
     * Get instance if initialized or else initiate and return.
     *
     * @return instance of client connection class.
     */
    public static ClientConnection getInstance() {
        if (_instance != null) {
            return _instance;
        }
        Socket sock = null;
        try {
            sock = new Socket(IP, port);
        } catch (IOException ex) {
            System.out.println("Couldn't create client socket");
            System.exit(-1);
        }
        return (_instance = new ClientConnection(sock));

    }
}
