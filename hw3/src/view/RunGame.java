package view;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


import model.GameMaster;


/**
 * 
 * @author Jimmy, Joshua, Vikrant, Kyle
 * This class runs the game in a set sized frame utilizing the
 * console view and gui view.  
 *
 */
@SuppressWarnings("serial")
public class RunGame extends JFrame {

	private GuiPanelView guiPanel;
	private ConsolePanelView consolePanel;
	private GameMaster model;
	private Container cp;

	//main
	public static void main(String[] args) {
		RunGame window = new RunGame();
		window.setVisible(true);
	}

	public RunGame() {

		//initialize new gamemodel
		model = new GameMaster();
		this.setTitle("Hunt the Wumpus");
		this.setSize(700, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		cp = this.getContentPane();
		cp.setLayout(null);

		//set new gamePanelView
		guiPanel = new GuiPanelView(model);
		guiPanel.setVisible(false);
		//set new consolePanelView
		consolePanel = new ConsolePanelView(model);
		consolePanel.setVisible(true);

		//setting the frame menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenu changeViewMenu = new JMenu("Change View");
		menuBar.add(fileMenu);
		menuBar.add(changeViewMenu);
		JMenuItem guiMenuItem = new JMenuItem("Gui");
		JMenuItem consoleMenuItem = new JMenuItem("Console");
		JMenuItem newGameItem = new JMenuItem("New Game");
		JMenuItem exit = new JMenuItem("Exit");
		changeViewMenu.add(guiMenuItem);
		changeViewMenu.add(consoleMenuItem);
		fileMenu.add(newGameItem);
		fileMenu.add(exit);
		//adding listeners to menu items
		newGameItem.addActionListener(new NewGameListener());
		exit.addActionListener(new ExitListener());
		guiMenuItem.addActionListener(new ChangeToGuiView());
		consoleMenuItem.addActionListener(new ChangeToConsoleView());

		//adding console and gui views to frame
		cp.add(guiPanel);
		cp.add(consolePanel);

		//adding observers to gameModel
		model.addObserver(guiPanel);
		model.addObserver(consolePanel);

	}

/**
 * listener that changes the guiview to visable and consoleview 
 * to not visable
 *
 */
	private class ChangeToGuiView implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			guiPanel.setVisible(true);
			consolePanel.setVisible(false);
		}
	}

/**
 * listener that changes the consoleview to visable and guiview 
 * to not visable
 *
 */
	private class ChangeToConsoleView implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			guiPanel.setVisible(false);
			consolePanel.setVisible(true);
		}
	}

/**
 * listener that exits the program
 *
 */
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	
/**
 *listener that starts a new game by creating a new game model, then
 *removing the current panels and initializing new panels to the frame
 *
 */
	private class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model = new GameMaster();
			guiPanel = new GuiPanelView(model);
			guiPanel.setVisible(true);
			consolePanel = new ConsolePanelView(model);
			consolePanel.setVisible(false);
			cp.removeAll();
			cp.add(guiPanel);
			cp.add(consolePanel);
			model.addObserver(guiPanel);
			model.addObserver(consolePanel);
		}
	}

}
