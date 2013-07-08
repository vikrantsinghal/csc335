package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.GameMaster;

/**
 * The ConsolePanelView is a JPanel that displays a textual representation of
 * the GameMaster The console will allow a user to input a desired room to
 * enter, as well as shoot an arrow by giving a path for the arrow to follow.
 * This will update alongside our GuiPanelView
 * 
 * @author Josh, Jimmy, Kyle, and Vikrant
 * 
 */
@SuppressWarnings("serial")
public class ConsolePanelView extends JPanel implements IGameObserver {

	// our model
	private GameMaster gm;

	// visual components
	private JScrollPane gameText;
	private JTextArea gameOutput;
	private JTextField entryBox;
	private JTextField arrowBox;
	private JPanel consoleView;

	public ConsolePanelView(GameMaster model) {
		this.setSize(600, 500);
		this.setLayout(new BorderLayout());

		// store instance variables
		gm = model;

		// set up console JPanel
		consoleView = new JPanel();
		consoleView.setVisible(true);
		consoleView.setLayout(new GridLayout());

		// gameOutput displays the current action
		gameOutput = new JTextArea();
		gameOutput
				.setText("Welcome to Wumpus. This needs a little bit of filler text, so lets do this. The wumpus is this nasty mammoth living in the caves. The hunter is on a quest to shoot this wumpus with a magic arrow that can travel up to 5 rooms in the cave. Watch out for the bats and pits, but we'll give you hints so you are not lost along the way. have fun");
		gameOutput.setEditable(false);
		gameOutput.setLineWrap(true);
		gameOutput.setSize(550, 400);
		gameOutput.setWrapStyleWord(true);
		gameText = new JScrollPane(gameOutput);
		consoleView.add(gameText);

		// JPanel for the two input boxes
		JPanel inputPanel = new JPanel();
		inputPanel.setVisible(true);
		inputPanel.setSize(400, 100);
		inputPanel.setLayout(new GridLayout());
		JLabel lab1 = new JLabel("Enter room number");
		JLabel lab2 = new JLabel("Enter arrow path");

		// entryInput gets the current user selection
		entryBox = new JTextField();
		entryBox.addActionListener(new EntryInputListener());
		inputPanel.add(lab1);
		inputPanel.add(entryBox);

		// arrowInput gets the arrow path selection
		arrowBox = new JTextField();
		arrowBox.addActionListener(new ArrowInputListener());
		inputPanel.add(lab2);
		inputPanel.add(arrowBox);

		// add the JPanels
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout());
		this.add(consoleView, BorderLayout.CENTER);
		this.add(inputPanel, BorderLayout.NORTH);
		makeMove();
	}

	// a move for the console will let you know what room you currently are in,
	// as well as helpful hints and directions for the next move
	public void makeMove() {
		if (gm.isAlive() != false) {
			gameOutput.append("\n----------------");
			gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
			// display game output
			gameOutput.append(gm.display());
		} else {
			gameOutput.append("\nGame over");
		}
	}

	/**
	 * Listens for an entry to the box labeled Enter room number
	 * 
	 */
	private class EntryInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// either no entry or game is over, stop listening
			if (entryBox.getText().isEmpty() || gm.isAlive() == false
					|| gm.isWumpusAlive() == false) {
				// do nothing
			} else {
				// grab the room entry
				String result = "";
				String temp = entryBox.getText();
				int entry = 0;
				try {
					entry = Integer.parseInt(temp);
					if (entry != 0) {
						entryBox.setText("");
						result = gm.moveToRoom(entry);
						gameOutput.append(result);
						gm.notifyObservers();
					}
				} catch (NumberFormatException nfe) {
					gameOutput.append("\nInvalid Entry. Try Again\n");
				}
			}
		}
	}

	/**
	 * Listen to the arrow path in the box labeled Enter arrow path
	 * 
	 */
	private class ArrowInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// either no entry or game is over, stop listening
			if (arrowBox.getText().isEmpty() || gm.isAlive() == false
					|| gm.isWumpusAlive() == false) {
				// do nothing
			} else {

				// get a list of 5 rooms
				// grab the arrow path entry
				char[] entryChars;
				int[] rooms = new int[5];
				String temp = arrowBox.getText() + " ";
				entryChars = temp.toCharArray();
				// follow the arrow path
				int count = 0;
				for (int index = 0; index < entryChars.length; index++) {
					// only add 5 room locations
					if (count == 5) {
						break;
					} else {
						// room to shoot into (1 . . . 5)
						try {
							// look at chars, exclude spaces
							if (entryChars[index] != 32) {
								// double digit input
								if (entryChars[index + 1] != 32) {
									rooms[count] = Integer.parseInt(""
											+ entryChars[index]
											+ entryChars[index + 1]);
									index++;
									count++;
								}
								// single digit input
								else {
									rooms[count] = Integer.parseInt(""
											+ entryChars[index]);
									count++;
								}
							}
						} catch (NumberFormatException nfe) {
							// just add 0, won't hit anything
						}
					}
				}
				arrowBox.setText("");
				gameOutput.append(gm.shoot(rooms));
				gm.notifyObservers();
			}
		}
	}

	@Override
	public void update(GameMaster o) {
		gm = (GameMaster) o;
		makeMove();
	}
}
