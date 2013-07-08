/**
 * Acts as the liasion between the user and the server.
 * Recieves input from the user and displays output from the server.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	// active indicates if user is still on server
	private boolean active = true;
	public static final String HOST_NAME = "localhost";
	public static final int PORT_NUMBER = 4030;
	private ObjectOutputStream outputToLiasonLoop;
	private ObjectInputStream inputFromLiasonLoop;

	static JTextField textField = new JTextField(20);
	static JTextArea textArea = new JTextArea();
	static JTextArea userArea = new JTextArea();
	static JTextArea chatArea = new JTextArea();

	public static void main(String args[]) {
		Client frame = new Client();
		frame.setVisible(true);
		frame.startTheReadThread();
	}

	private void startTheReadThread() {
		connectToServer();
		IncomingReader ir = new IncomingReader();
		Thread thread = new Thread(ir);
		thread.start();
	}

	public Client() {
        Font font = new Font("Monospaced", Font.PLAIN, 12);

        setSize(800, 800);
		this.addWindowListener(new SomeWindowListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		InputListener listener = new InputListener();
		textField.addActionListener(listener);
		textField.setText("");
		panel.add(textField, BorderLayout.SOUTH);

		JPanel userPanel = new JPanel();
		userPanel.setLayout(new GridLayout(2, 1));
		// extra textareas for the user
		userArea.setEditable(false);
		userArea.setText("Attributes and Game Info\n\n     ");
        userArea.setFont(font);
		userArea.setLineWrap(true);
		userArea.setWrapStyleWord(true);

		chatArea.setEditable(false);
		chatArea.setFont(font);
		chatArea.setText("iMUD CHAT\n");
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		userPanel.add(userArea);
		userPanel.add(new JScrollPane(chatArea));
		panel.add(userPanel, BorderLayout.EAST);

        textArea.setFont(font);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setText("\n\nAlphaSuperAwesomeCoolDynamiteWolfSquadron\n\n     "
				+ "For thousands of years, the mighty village of Camelot dominated the "
				+ "known world. The village was small, but no one ever felt the need to "
				+ "leave. After several disputes over potato irrigation techniques, Camelot "
				+ "split into four separate kingdoms, one to the North, to the East, to the "
				+ "South, and to the West. Each kingdom was named after its greatest leaders, "
				+ "General North, Captian East, Senor South, and William West, respectively. "
				+ "The North was known for their polar bears and lack of potato produce. "
				+ "The East was known for the beasts and a very good place to get a beer. "
				+ "The South has no real significant importance, however, many strange things "
				+ "occur there pretty regularly. The West is perhaps the most important kingdom "
				+ "of all, known by those who travel as 'The land of poor low level character "
				+ "survival rate...' These kingdoms vow to remain separate from each other, "
				+ "only to bend for those with enough spirit and courage to try to bring these "
				+ "lands back together.\n\n");
		panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
		this.add(panel, BorderLayout.CENTER);
		this.setTitle("Haywire Dungeons");
	}

	public void connectToServer() {
		Socket sock = null;
		try {
			sock = new Socket(HOST_NAME, PORT_NUMBER);
		} catch (Exception e) {
			System.out.println("Client was unable to connect to server");
			e.printStackTrace();
		}
		try {
			outputToLiasonLoop = new ObjectOutputStream(sock.getOutputStream());
			inputFromLiasonLoop = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			System.out
					.println("Unable to obtain Input/Output streams from Socket");
			e.printStackTrace();
		}
	}

	private class InputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				outputToLiasonLoop.writeObject(textField.getText());
			} catch (IOException e) {
				// error
			}
			textField.setText("");
		}
	}

	class IncomingReader implements Runnable {

		public void run() {
			// if active is set to false, loop ends
			while (true && active) {
				String input = null;

				try {
					input = (String) inputFromLiasonLoop.readObject();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

				if (input.length() >= 3
						&& input.substring(0, 3).equalsIgnoreCase("!!!")){
					active = false;
					break;
				}

				// grab incoming text designated for the user game info
				if (input.length() >= 4
						&& input.substring(0, 4).equalsIgnoreCase("*** ")) {
					userArea.setText(userArea.getText().substring(0, 26)
							+ input.substring(4));
				}

				// grab incoming text going to the chat area
				else if (input.length() >= 4
						&& input.substring(0, 4).equalsIgnoreCase("### ")) {
					chatArea.append("\n" + input.substring(4));
				}

				// everything else
				else {
					textArea.append(input + "\n");
					textArea.setCaretPosition(textArea.getDocument()
							.getLength());
				}
			}
			//System.exit(0);
		}
	}

	private class SomeWindowListener implements WindowListener {

		public void windowActivated(WindowEvent arg0) {
			// do nothing
		}

		public void windowClosed(WindowEvent arg0) {
			// do nothing
		}

		public void windowClosing(WindowEvent arg0) {
			active = false;
	          try {
				outputToLiasonLoop.writeObject("quit");
				//System.exit(0);
	          }
			 catch (IOException e) {
				// TODO Auto-generated catch block
				System.exit(0);
			}

		}

		public void windowDeactivated(WindowEvent arg0) {
			// do nothing
		}

		public void windowDeiconified(WindowEvent arg0) {
			// do nothing
		}

		public void windowIconified(WindowEvent arg0) {
			// do nothing
		}

		public void windowOpened(WindowEvent arg0) {
			// do nothing
		}
	}


}
