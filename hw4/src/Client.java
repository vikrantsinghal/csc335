import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This client program connect one computer to a server and read commands from
 * the standard input which consists of the following three commands:
 * 
 * 1. "put <name>" Puts name into a collection with a the InetAddress as the key
 * 2. "get" retrieves and prints a list of all names of those currently active
 * 3. "quit" remove the person from the collection of this logged in
 * 
 * @author Kyle Almryde
 * @author Vikrant Singhal
 */
@SuppressWarnings("serial")
public class Client extends JFrame {

    private String clientName;
    private TextFieldListener textListener;

    // This client needs a server running at localhost and listening at port
    // 4009
    public static final String HOST_NAME = "localhost";

    // For this HOST_NAME, start the server on Lectura
    // public static final String HOST_NAME = "lec.cs.arizona.edu";
    public static final int PORT_NUMBER = 4009;
    private static ObjectOutputStream outputToServer; // stream to server
    private ObjectInputStream inputFromServer; // stream from server

    static JTextField textField = new JTextField(20);
    static JTextArea textArea = new JTextArea();

    /**
     * Main method which starts GUI and begins listening for messages from the
     * server.
     * 
     */
    public static void main(String args[]) {
        Client frame = new Client();
        frame.setVisible(true);
        frame.startTheReadThread();
        try {
            outputToServer.writeObject("get");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a thread which connects to the server
     * 
     */
    private void startTheReadThread() {
        connectToServer();
        IncomingReader ir = new IncomingReader();
        Thread thread = new Thread(ir);

        while (getName().equals("")) {
            System.out.println("");
        }

        try {
            outputToServer.writeObject("put " + clientName
                    + " has just joined the chat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread.start();
    }

    /**
     * The Client constructor
     * 
     * Builds the GUI, which displays and sends messages to from the server.
     */
    public Client() {
        clientName = "";
        setSize(500, 400);
        JPanel panel = new JPanel();
        this.setTitle("Chat Client");

        textListener = new TextFieldListener();
        NameFieldListener nameListener = new NameFieldListener();
        textField.addActionListener(nameListener);

        panel.add(textField);
        textField.setText("Replace me with your name");

        WindowListener exitListener = new ExitListener();
        this.addWindowListener(exitListener);

        textArea.setPreferredSize(new Dimension(480, 330));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        panel.add(textArea);
        this.add(panel);
    }

    /**
     * This method handles connecting the client to the server controlled
     * socket. Should the connection fail for whatever reason, a message will be
     * displayed
     * 
     */
    public void connectToServer() {
        Socket sock = null;
        try {
            // connect to the server
            sock = new Socket(HOST_NAME, PORT_NUMBER);
        } catch (Exception e) {
            System.out.println("Client was unable to connect to server");
            e.printStackTrace();
        }
        try {
            outputToServer = new ObjectOutputStream(sock.getOutputStream());
            inputFromServer = new ObjectInputStream(sock.getInputStream());
        } catch (Exception e) {
            System.out
                    .println("Unable to obtain Input/Output streams from Socket");
            e.printStackTrace();
        }
    }

    /**
     * This method sets the name of the client to be used when they log into the
     * chat server.
     */
    public void setName(String name) {
        clientName = name;
    }

    /**
     * A getter method which gets the client name.
     */
    public String getName() {
        return clientName;
    }

    /**
     * This WindowListener enables the user to quit the chat client by pressing
     * the 'close' button on the window. This ensures that the client with
     * properly disconnect from the server without raising an exception.
     */
    private class ExitListener implements WindowListener {
        @Override
        public void windowClosing(WindowEvent e) {
            try {
                outputToServer.writeObject("quit");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.exit(0);
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }

    /**
     * This ActionListener handles the name assignment aspect of the chat
     * client. When the user first activates the client they are prompted to
     * enter a User name. This user name is is displayed next to ever message
     * the user submits to the server and displayed on all other clients.
     * 
     * Because there is only one source of input and the first action required
     * of the user is to enter a name, this listener will assign the name, then
     * add the TextFieldListener, and then remove itself. That way the user can
     * continue to use the same text field for sending messages and assigning
     * their name.
     */
    private class NameFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String name = textField.getText();
            if (name.equals(""))
                return;
            setName(name);
            textField.setText("");
            textField.addActionListener(textListener);
            textField.removeActionListener(this);

        }
    }

    /**
     * This ActionListener handles sending user supplied messages to the server
     * and request messages currently pending on the server.
     */
    private class TextFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Object eventSource = event.getSource();
            try {
                if (eventSource == textField) {
                    if (textField.getText().length() > 0) {
                        outputToServer.writeObject("put " + getName() + ": "
                                + textField.getText());
                    }
                    textField.setText("");
                    outputToServer.writeObject("get");
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Is responsible for listening for messages from the server and passes them
     * along to the client.
     */
    class IncomingReader implements Runnable {
        @SuppressWarnings("unchecked")
        public void run() {
            while (true) {
                Vector<String> clientsConnected = null;
                try {
                    clientsConnected = (Vector<String>) inputFromServer
                            .readObject();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                textArea.setText("");
                for (int i = 0; i < clientsConnected.size(); i++)
                    textArea.append(clientsConnected.get(i) + "\n");
            }
        }
    }

}
