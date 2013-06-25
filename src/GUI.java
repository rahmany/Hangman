import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, MouseListener {
	public static final String FILE = "input/dictionary.txt";
	// Jframe height, weight
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	// File menu
	private static final String FILE_START = "Play";
	private static final String FILE_STOP = "Exit";
	public static final String SHOW_REPLAY = "Play Again?";
	// changes the state of the file
	private int state = 0;
	// Random genator for word array
	public Random rGen = new Random();
	// Char array that houses the random phrases that was converted from the
	// word array
	private static char[] randPhrase;
	// word aray that houses the 20 words from the file
	private static String[] words;
	// Char array that houses the user guesses
	private static char[] guesses;
	// counts the number of hangman body parts
	public static int numBodyParts = 0;
	// holds the letters that the user guesses
	private static String numGuesses = "";
	// holds the random word that the word array generates to prevent multiple
	// calls
	public static String phrase;
	// all my panels - mainpanel holds left/right/bottom(keyboard)
	public static JPanel mainPanel, leftPanel, rightPanel, bottomPanel,
			belowPanel;

	
	public GUI() {
		// pass title to super class
		super("Hang Man");
		// set size of the jframe
		setSize(WIDTH, HEIGHT);
		// populate word array
		words = textFile();
		// close Jframe on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// main panel houses three panels - left, right and bottom(keyboard).
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 0));
		mainPanel.setBackground(Color.WHITE);

		rightPanel = new JPanel();
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		rightPanel.setBackground(Color.WHITE);
		// add the left/right panel
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(4, 4));
		bottomPanel.setBackground(Color.GRAY);
		// add the bottom panel which contains Jbuttons
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		belowPanel = new JPanel();
		belowPanel.setBackground(Color.GREEN);
		// add last panel which houses replay/exit button
		add(mainPanel);
		add(belowPanel, BorderLayout.AFTER_LAST_LINE);

		// set visibility to false until game is over
		belowPanel.setVisible(false);

		// create menu bar
		createMenuBar();
		// create keyboard buttons
		createButtons(bottomPanel);
		// create replay/exit buttons
		replayButtons(belowPanel);
		// add mouselistener
		addMouseListener(this);
	}

	// method creates two jbutton for replay/exit and adds actionlisteners
	public void replayButtons(JPanel belowPanel) {
		JButton playAgain = new JButton(SHOW_REPLAY);
		playAgain.setSize(80, 80);
		playAgain.setActionCommand(SHOW_REPLAY);
		playAgain.addActionListener(this);
		JButton exit = new JButton(FILE_STOP);
		exit.setActionCommand(FILE_STOP);
		exit.addActionListener(this);
		exit.setSize(80, 80);
		belowPanel.add(playAgain);
		belowPanel.add(exit);
	}

	// method creates an array of jbuttons with actionlisteners to use as a
	// keyboard
	public void createButtons(JPanel bottomPanel) {

		JButton[] buttons = new JButton[26];
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(letters[i]);
			buttons[i].setSize(40, 40);
			buttons[i].setActionCommand(letters[i]);
			buttons[i].addActionListener(this);

			bottomPanel.add(buttons[i]);
		}

	}

	// method creates menu and menuitems
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// create file menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// add menu items
		createMenuItem(fileMenu, FILE_START);
		createMenuItem(fileMenu, FILE_STOP);

	}
	//method creates menu items with action listeners
	public void createMenuItem(JMenu menu, String itemName) {
		JMenuItem menuItem = new JMenuItem(itemName);
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	public void paint(Graphics g) {
		super.paint(g);

		// set the font
		Font font = new Font("Serif", Font.BOLD | Font.ITALIC, 24);
		g.setFont(font);
		g.setColor(Color.RED);
	
		// if user has selected play from menu - start game
		if (state == 1) {
			
			
			gameMessages(g);
			String result = "";
			for (int i = 0; i < guesses.length; i++) {

				result += guesses[i] + " ";
			
			
			}
			g.drawString(result, 300, 175);
			g.drawString("GUESSES", 300, 300);
			g.drawString(numGuesses, 300, 350);
			System.out.println(randPhrase);
			// if user misses a letter - display body parts
				
			
			hangman(g);

			}
		}

	

	private void hangman(Graphics g) {
		if (numBodyParts >= 1) {

			// draw face
			g.setColor(Color.YELLOW);
			g.fillOval(35, 120, 70, 60);
			// hat
			g.setColor(Color.RED);

			g.fillRect(48, 90, 48, 30);
			g.fillRect(30, 120, 80, 15);

			// draw eyes
			g.setColor(Color.GREEN);
			g.fillOval(55, 140, 10, 10);
			g.fillOval(75, 140, 10, 10);

			// smile
			g.setColor(Color.RED);
			g.drawArc(50, 155, 40, 10, -10, -180);
			if (numBodyParts >= 2) {
				// body
				g.setColor(Color.GREEN);
				g.fillRect(60, 180, 20, 80);
			}

			if (numBodyParts >= 3) {
				// left arm
				g.setColor(Color.GREEN);
				g.fillRect(25, 200, 45, 15);
				g.setColor(Color.YELLOW);
				g.fillRect(15, 200, 10, 15);
			}
			if (numBodyParts >= 4) {
				// right arm
				g.setColor(Color.GREEN);
				g.fillRect(80, 200, 45, 15);
				g.setColor(Color.YELLOW);
				g.fillRect(120, 200, 10, 15);
			}
			if (numBodyParts >= 5) {
				// left foot
				g.setColor(Color.BLACK);
				g.fillRect(35, 260, 30, 15);
			}
			if (numBodyParts >= 6) {
				// right foot
				g.setColor(Color.BLACK);
				g.fillRect(70, 260, 30, 15);
			}
		}
		
	}

	private void gameMessages(Graphics g) {

		if (!winner()) {
			// draw welcome message
			g.drawString("Let's Play Hang Man!!!", 25, 80);
			//draw winner message and enable belowpanel
		} else if (winner() && numBodyParts < 6) {
			System.out.println("i hit here");
			g.drawString("You Won!!", 50, 80);
			bottomPanel.setVisible(false);
			belowPanel.setVisible(true);
			
			//draw lost message and enable belowpanel
		} else if (numBodyParts == 6) {

			g.drawString("You Lost!!", 25, 80);
			bottomPanel.setVisible(false);
			belowPanel.setVisible(true);

		}

	}

	// generate a random word and return via char array
	public String getword() {
		words = textFile();

		int n = words.length;
		int r = rGen.nextInt(n);
		String word = words[r];

		return word;
	}

	// method determines whether guesses array match the randphrase array 
	public boolean winner() {
		if (Arrays.equals(guesses, randPhrase)) {
			return true;
		} else {
			return false;
		}

	}

	// method reads from a file and writes to arraylist which is converted back
	// to an array
	public String[] textFile() {
		// create a bufferedReader
		BufferedReader reader = null;
		// create a list array and store the values from the text file
		List<String> wordList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(FILE));
			String s = null;

			while ((s = reader.readLine()) != null) {

				wordList.add(s);

			}

		} catch (IOException e) {

			System.out.println(e.getMessage());
			System.exit(-1);
		} finally {
			try {
				// close the file
				reader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}
		}
		// convert from arraylist to array and return
		return wordList.toArray(new String[wordList.size()]);

	}
	
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		
	 if (command.equals(FILE_START)) {
			// once the user has pressed play, change state and call to play
			// method
			state = 1;
			play();

			repaint();

		}
		
	 else if (command.length() == 1 && state == 1) {
			// pass action event to letters if the user has pressed play and the
			// event is generated
			// by the Jbutton array (length of the string is one)
			letters(command);
		}
		//reset status and replay game
		else if (command.equals(SHOW_REPLAY)) {

			numBodyParts = 0;
			numGuesses = "";
			bottomPanel.setVisible(true);
			state = 1;
			play();
			repaint();		

		} else if (command.equals(FILE_STOP)) {
			state = 2;
			System.exit(-1);
		}

		// repaint();
	}

	// method receives actionevent from JButtons and compares it to randphrase
	// array
	public void letters(String command) {

		System.out.println(command);

		if (phrase.contains(command.toLowerCase())) {
			for (int i = 0; i < randPhrase.length; i++) {
				if (command.toLowerCase().charAt(0) == randPhrase[i]) {
					guesses[i] = command.toLowerCase().charAt(0);

				}

			}
			// if letter does not match - bodycounter increases
		} else if (!phrase.contains(command.toLowerCase())) {
			JOptionPane.showMessageDialog(null, "Sorry " + command
					+ " is not part of the word");
			numBodyParts++;
		}

		// concatenation user guesses
		numGuesses += command;
		if (numBodyParts < 6 && !winner()) {
			numGuesses += ",";
		}
		repaint();
	}

	// method generates the '_' on the guesses array so it's display to the user
	private void play() {

		// store random word
		phrase = getword();
		// convert random word to char array
		randPhrase = phrase.toCharArray();
		//create an array to hold and display user input
		guesses = new char[randPhrase.length];
		//populate the array with dashes first
		for (int i = 0; i < guesses.length; i++) {
			guesses[i] = '_';
			}

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public static void main(String[] args) {
		GUI hangman = new GUI();
		hangman.setVisible(true);

	}
}
