import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * Each instance of this class allows a client to interact with the database.
 * Currently the functionality is to allow a client to get added to the
 * collection of concurrently connected clients, to quit which removes them from
 * the database, and to also get a list of all people's names who are currently
 * connected. The client can enter these three requests for services:
 * 
 * put <name>, quit, get
 * 
 * @author Kyle Almryde
 * @author Vikrant Singhal
 */
class ConnectionThread implements Runnable {
    private Vector<String> sharedCollectionReference;
    private Vector<String> myMessages;
    private Vector<String> myCollection;
    private Socket socketFromServer;

    private ObjectInputStream readFromClient;
    private ObjectOutputStream writeToClient;

    private int startingIndex;

    /**
     * Construct an object that will run in it's own Thread so this object can
     * communicate with that one connection. When this client quits, all message
     * that client entered are removed from the sharedCollectionReference.
     */
    public ConnectionThread(Socket socketFromServer,
            Vector<String> collectionReference, int whereToStart) {
        this.socketFromServer = socketFromServer;
        this.sharedCollectionReference = collectionReference;
        myMessages = new Vector<String>();
        myCollection = new Vector<String>();
        this.startingIndex = whereToStart;
    }

    /**
     * The void run() method in class Thread must be overridden in every class
     * that extends Thread. The Server created a new instance of this class and
     * sends it a start method, which is in Thread. The start method creates a
     * new Thread which then calls the following run method where our domain
     * specific logic goes.
     */
    @Override
    public void run() {
        // Open the input and output streams so the
        // client can interact with the collection
        try {
            readFromClient = new ObjectInputStream(
                    socketFromServer.getInputStream());
            writeToClient = new ObjectOutputStream(
                    socketFromServer.getOutputStream());
        } catch (IOException e) {
            System.out
                    .println("Exception thrown while obtaining input & output streams");
            e.printStackTrace();
            return;
        }

        String messageFromClient = null;
        boolean wantToAStayConnected = true;

        // Loop as long as we are connected using a boolean variable
        while (wantToAStayConnected) {
            try {
                messageFromClient = (String) readFromClient.readObject();
            } catch (IOException e1) {
                System.out
                        .println("IOException in ServerThread.run when reading from client");
                return;
            } catch (ClassNotFoundException e1) {
                System.out
                        .println("ClassCastException in ServerThread.run casting object from client to String");
                e1.printStackTrace();
            }

            // Now that we have read a valid string from Client, process it:
            try { // Process a "put Name"
                if (messageFromClient.length() >= 1
                        && messageFromClient.substring(0, 3).equalsIgnoreCase(
                                "put")) {
                    String clientMessage = messageFromClient.substring(3)
                            .trim();
                    sharedCollectionReference.add(clientMessage);
                    myMessages.add(clientMessage);
                    List<String> list = sharedCollectionReference.subList(
                            startingIndex, sharedCollectionReference.size());
                    myCollection.clear();
                    for (String a : list) {
                        myCollection.add(a);
                    }
                }

                if (messageFromClient.equalsIgnoreCase("get")) { // Process a
                                                                 // "get"
                    myCollection.clear();
                    List<String> list = sharedCollectionReference.subList(
                            startingIndex, sharedCollectionReference.size());
                    myCollection.clear();
                    for (String a : list) {
                        myCollection.add(a);
                    }
                    writeToClient.reset(); //
                    writeToClient.writeObject(myCollection);
                }

                if (messageFromClient.equalsIgnoreCase("quit")) { // Process a
                                                                  // "quit"
                    // Clean up and avoid exceptions
                    sharedCollectionReference.removeAll(myMessages);
                    socketFromServer.close();
                    readFromClient.close();
                    writeToClient.close();
                    wantToAStayConnected = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                wantToAStayConnected = false;
            }
        }
    }
}