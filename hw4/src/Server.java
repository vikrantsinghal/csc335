import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * This server runs in its own thread. It accepts requests to connect from
 * clients. Each connection accepted causes a new Thread to handle that client,
 * or in other words there is one thread per client on the server side.
 * 
 * Each client running in it's own thread shares the same database, actually a
 * Vector<E>. This collection class has synchronized methods that allows one
 * request to complete before another thread can jump into the middle thereby
 * avoiding a race condition.
 * 
 * @author Kyle Reese Almryde
 * @author Vikrant Singhal
 */
public class Server implements Runnable {

    public final static int PORT_NUMBER = 4009;
    private ServerSocket myServerSocket; // client request source
    private Vector<String> connectedClients;

    public static void main(String args[]) {
        Server server = new Server(PORT_NUMBER);
        // always call the start() method on a Thread. Don't call the run
        // method.
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    public Server(int port) {
        connectedClients = new Vector<String>();
        try {
            myServerSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This run() method is now in a separate Thread and will listen for
     * connections at PORT_NUMBER at the machine in which it is running
     */
    @Override
    public void run() {
        try {
            while (true) {
                // accept blocks until request comes over socket
                Socket intoServer = myServerSocket.accept();

                // For every new connection, start a new Thread that will
                // communicate with that client. Each one will have access
                // to the common collection of all users who are connected.
                ConnectionThread aThreadForOneClient = new ConnectionThread(
                        intoServer, connectedClients, connectedClients.size());
                Thread thread = new Thread(aThreadForOneClient);
                // always call the start() method on a Thread.
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}