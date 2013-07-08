package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import model.GameMaster;


/**
 *  This class controls the main GUI functionality of the 'Hunt The Wumpus' game.
 *  It produces both a traditional console view as well as the graphical protion
 *  of the game. In addition to extending JFrame, this class also implements
 *  the IGameObserver, which enables the use of a MVC pattern.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Joshua Fry
 * @author Vikrant Singhal
 */
@SuppressWarnings("serial")
public class GuiPanelView extends JPanel implements IGameObserver {

	private BufferedImage spriteSheet, background, gameoverMan;
	private GameMaster gameModel;
	private Button leftRoomButton = new Button();
	private Button centerRoomButton = new Button();
	private Button rightRoomButton = new Button();
	private Button shootArrowButton = new Button("Shoot Arrow");
	private JTextArea output = new JTextArea();
	private List<Integer> roomList = new ArrayList<Integer>();
	private JLabel currentRoomLabel = new JLabel();
	private JLabel arrowsLeftLabel = new JLabel();
	private JScrollPane scrollpane = new JScrollPane();
    private Timer timer;

    private int arrowY, goX;
    private int velY, goY;

    private boolean arrowFlying;


    /**
     *  Constructor method for the GuiPanelView.
     *
     * @param GameMaster model
     *          A GameMaster object which controls the main functionality of the game
     */
	public GuiPanelView(GameMaster model) {
        // Initialize vectors for the game graphics
        arrowY = 500;
        velY = 20;
        goX = 700;
        goY = 600;

        // Prepare the arrow animation.
        arrowFlying = false;
        timer = new Timer(50, new shootAnimationListener());

		gameModel = model;
		roomList = gameModel.getHunterAdjacentRooms();

        // Load the prepared images
		loadImages();

        // Setup the GUI
        this.setSize(700, 600);
		this.setLayout(null);

		this.add(currentRoomLabel);
		currentRoomLabel.setSize(400, 30);
		currentRoomLabel.setLocation(new Point(190, 90));
		currentRoomLabel.setFont(new Font("FangSong", Font.BOLD, 30));
		currentRoomLabel.setForeground(Color.white);

		arrowsLeftLabel.setSize(110, 30);
		arrowsLeftLabel.setLocation(new Point(520, 465));
		arrowsLeftLabel.setFont(new Font("FangSong", Font.BOLD, 15));
		arrowsLeftLabel.setForeground(Color.white);
		this.add(arrowsLeftLabel);

		leftRoomButton.setSize(100, 40);
		leftRoomButton.setLocation(new Point(65, 100));
		leftRoomButton.addActionListener(new ButtonMoveLeftListener());
		this.add(leftRoomButton);

		centerRoomButton.setSize(100, 40);
		centerRoomButton.addActionListener(new ButtonMoveCenterListener());
		centerRoomButton.setLocation(new Point(280, 100));
		this.add(centerRoomButton);

		rightRoomButton.setSize(100, 40);
		rightRoomButton.addActionListener(new ButtonMoveRightListener());
		rightRoomButton.setLocation(new Point(515, 100));
		this.add(rightRoomButton);
		shootArrowButton.setSize(100, 40);
		shootArrowButton.addActionListener(new ButtonShootArrowListener());
		shootArrowButton.setLocation(new Point(525, 425));
		this.add(shootArrowButton);

		output.setLineWrap(true);
		output.setLocation(new Point(10, 420));
		output.setSize(500, 140);
		output.setEditable(false);
		output.add(scrollpane);
		this.add(output);

		setView();

        shootArrowButton.addActionListener(new shootAnimationListener());

	}


    /**
     *  This method sets up the components of the GUI. It adds labels to the buttons,
     *  positions the titles and labels of the game, and begins keeping track of which
     *  actors are where within the game.
     */
	private void setView() {
        // Check to see if the Hunter is alive.
		if (!gameModel.isAlive()) {
			output.setText(output.getText() + "Game Over");
            gameOverMan();
		} else {
			roomList = gameModel.getHunterAdjacentRooms();
			leftRoomButton.setLabel("" + roomList.get(0));
			centerRoomButton.setLabel("" + roomList.get(1));
			rightRoomButton.setLabel("" + roomList.get(2));
			int currentRoom = gameModel.hunterLocation();
			int arrowsLeft = gameModel.getHunter().getRemainingArrows();
			arrowsLeftLabel.setText("Arrows: " + arrowsLeft + "/3");
			currentRoomLabel.setText("You are in room " + currentRoom);
			currentRoomLabel.setLocation(new Point(190, 60));
			output.append(gameModel.displayHints());
			leftRoomButton.repaint();
			centerRoomButton.repaint();
			rightRoomButton.repaint();
		}

	}


    /**
     *  This method loads the external images used in the game. These images
     *  include the background images of the Cave, the sprite sheet for the
     *  animations, and the Game Over image if the player looses.
    */
    private void loadImages() {

        // Try to load the Cave background image
        try {
            background = ImageIO.read(new File("images" + File.separator
                    + "cave.jpg"));
        } catch (IOException e) {
            System.out.println("Could not find or load cave.jpg");
        }

        // Try to load the sprite sheet
        try {
            spriteSheet = ImageIO.read(new File("images" + File.separator
                    + "arrow_spriteSheet.png"));
        } catch (IOException e) {
            System.out.println("Could not find or load arrow_spriteSheet.png");
        }

        // Try to load the Game Over background image
        try {
            gameoverMan = ImageIO.read(new File("images" + File.separator
                    + "gameover.png"));
        } catch (IOException e) {
            System.out.println("Could not find or load gameover.png");
        }

    }

    /**
     * The getArrowSprite image extracts the subimage of the arrow, which will be animated
     * later in the game.
     *
     * @return BufferedImage object containing the subimage of an arrow
     */
    public BufferedImage getArrowSprite() {
        if (arrowFlying)
            return spriteSheet.getSubimage(456, 32, 20, 100);
        return null;

    } // End of getArrowSprite



    /**
     *  This private method provides the heavy lifting of the arrow animation.
     *  In addition to progressing the image forward, it checks to see if the arrow
     *  has reached the mouth of the cave, at which point it resets its location back
     *  its starting position.
     */
    private void makeArrowFly() {
        if (arrowY > 240)
            arrowY = arrowY - velY;
        else {
            arrowY = 500;
            arrowFlying = false;
        }
    }


    /**
     *  Sets the position of the Game Over background so that it is visible to the
     *  player.
     */
    private void gameOverMan() {
        goX = 0;
        goY = 0;
    }


    /**
     *  Draws the loaded images so that we can see them. It also sets their postion, and in
     *  the case of the arrow, begins the timer which will determing the speed at which the
     *  animation runs.
     *
     * @param Graphics g
     *          A Graphics object which produces a 2D graphic image, which can be overlaid
     *          with multiple images to create something pretty, or monsterous.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gr = (Graphics2D) g;
        gr.drawImage(background, 0, 0, null);
        gr.drawImage(gameoverMan, goX, goY, null);
        gr.drawImage(getArrowSprite(), 315, arrowY, null);
        timer.start();
    }

    /**
     * This method updates the game model with the latest information from the
     * observer.
     *
     * @param GameMaster o
     *        The modified GameMaster object.
     */
	@Override
	public void update(GameMaster o) {
		gameModel = (GameMaster) o;
		setView();

	}


    /**
     *  This private class handles the ActionListener for the Left Move Button,
     *  which notifies the observers when it has been clicked.
     */
	private class ButtonMoveLeftListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!gameModel.isAlive()) {
				output.setText("Game Over");
                gameOverMan();
			} else {
				output.setText(gameModel.moveToRoom(roomList.get(0)));
				gameModel.notifyObservers();
			}
		}
	}



    /**
     *  This private class handles the ActionListener for the Center Move Button,
     *  which notifies the observers when it has been clicked.
     */
	private class ButtonMoveCenterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!gameModel.isAlive()) {
				output.setText("Game Over");
                gameOverMan();
			} else {
				output.setText(gameModel.moveToRoom(roomList.get(1)));
				gameModel.notifyObservers();
			}
		}
	}


    /**
     *  This private class handles the ActionListener for the Right Move Button,
     *  which notifies the observers when it has been clicked.
     */
	private class ButtonMoveRightListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!gameModel.isAlive()) {
				output.setText("Game Over");
                gameOverMan();
			} else {
				output.setText(gameModel.moveToRoom(roomList.get(2)));
				gameModel.notifyObservers();
			}
		}
	}


    /**
     *  This private class handles the ActionListener for the Shoot Arrow Button.
     *  It handles the user input for the rooms which the arrow can travel to and
     *  when the arrow animation occurs
     */
	private class ButtonShootArrowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int[] rooms = new int[5];
            velY = 20;

            // If the hunter is all out of arrows: GAME OVER MAN!!
            if (gameModel.getHunter().getRemainingArrows() == 0) {
                output.setText("Game Over");
                gameOverMan();

            // Otherwise Enter a path for the arrow to follow.
            } else {
                String arr = JOptionPane.showInputDialog("Enter Arrow Path: ");
                if (arr.isEmpty() || gameModel.isAlive() == false || gameModel.isWumpusAlive() == false) {
                    // do nothing
                } else {
                    // get a list of 5 rooms
                    // grab the arrow path entry
                    char[] entryChars;
					String temp = arr + " ";
					entryChars = temp.toCharArray();
					// follow the arrow path
					int count = 0;
					for (int index = 0; index < entryChars.length; index++) {
						// only add 5 room locations
						if (count == 5) {
							break;
						} else {
							// Try to extract the rooms to shoot into (1 . . . 5)
							try {
                                // Weve made it this far, set arrowFlying to true so we can see
                                //  it animate!
                                arrowFlying = true;

								// look at chars, exclude spaces
                                if (entryChars[index] != 32) {
									// double digit input
									if (entryChars[index + 1] != 32) {
										rooms[count] = Integer.parseInt("" + entryChars[index] + entryChars[index + 1]);
										index++;
										count++;
									}
									// single digit input
									else {
										rooms[count] = Integer.parseInt("" + entryChars[index]);
										count++;
									}
								}
							} catch (NumberFormatException nfe) {
                                // just add 0, won't hit anything
                            }
                        }
                    }
                    arr = "";
                    output.setText(gameModel.shoot(rooms));
                    gameModel.notifyObservers();

                }
            }
		}
	}


    /**
     *  This handles the actual animation of the arrow. It is supplied as an
     *  input to the Timer variable. It calls the makeArrowFly() method as well
     *  as the repaint() method from the Image class.
     */
    private class shootAnimationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            makeArrowFly();
            repaint();
        }
    }

}
