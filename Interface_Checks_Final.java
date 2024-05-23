package Interface;

/*
 * This file is part of the Bachelor Thesis project called "Analysis and Implementation of Cypriot game Andrin",
 * under the Computer Science department of the  University of Cyprus.
 * 
 * 
 * Author: Styliana Vaki
 * 
 * Created on: Spring Semester 2024
 * 
 * Credits: Please credit the original author if this code is used or edited.
 */
import java.awt.*;
import java.awt.event.*;

public class Interface_Checks_Final extends Frame {

	// Rule components
	private Button rulesB;
	private Dialog rules;

	// Interface components
	private TextField textField;
	private TextField textField2;
	private TextField textField3;
	private Label intsruction;
	private Label arrowLabel;
	Button submitButton;
	Button submitButton2;

	// Board components
	private static int[] board = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	int currentplayer;
	private boolean gameEnded = false;
	int step;

	// User inputs
	String input = " ";
	int chosenposition;
	String inputfrom = " ";
	String inputto = " ";

	private boolean shouldStartPhase2 = false;

	// 9 spots coordinates (intended to use them but eventually not necessary)
	private int a1X;
	private int a1Y;
	private Color a1color = Color.WHITE;

	private int a2X;
	private int a2Y;
	private Color a2color = Color.WHITE;

	private int a3X;
	private int a3Y;
	private Color a3color = Color.WHITE;

	private int b1X;
	private int b1Y;
	private Color b1color = Color.WHITE;

	private int b2X;
	private int b2Y;
	private Color b2color = Color.WHITE;

	private int b3X;
	private int b3Y;
	private Color b3color = Color.WHITE;

	private int c1X;
	private int c1Y;
	private Color c1color = Color.WHITE;

	private int c2X;
	private int c2Y;
	private Color c2color = Color.WHITE;

	private int c3X;
	private int c3Y;
	private Color c3color = Color.WHITE;

	private int circle = 50;

	public Interface_Checks_Final() {

		super("Andrin Game");
		setSize(900, 500);

		// Open window in center of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (screenSize.width - getWidth()) / 2;
		int centerY = (screenSize.height - getHeight()) / 2;
		setLocation(centerX, centerY);

		// Create rule button
		rulesB = new Button("?");
		rulesB.setBounds(850, 35, 30, 30);
		rulesB.setBackground(Color.WHITE);
		add(rulesB);

		// Create label for phase 1
		intsruction = new Label("welcome");
		intsruction.setFont(new Font("Arial", Font.PLAIN, 18));
		add(intsruction);
		intsruction.setBounds(500, 290, 300, 30);

		// Create text field for phase 1
		textField = new TextField();
		textField.setPreferredSize(new Dimension(100, 20));
		textField.setFont(new Font("Arial", Font.PLAIN, 22));
		add(textField);
		textField.setBounds(610, 350, 60, 35);

		// Create submit button for phase 1
		submitButton = new Button("Submit");
		submitButton.setBounds(700, 350, 100, 30);
		submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
		add(submitButton);

		// Create text field for phase 2
		textField2 = new TextField();
		textField2.setPreferredSize(new Dimension(100, 20));
		textField2.setFont(new Font("Arial", Font.PLAIN, 22));
		textField2.setBounds(650, 350, 60, 35);
		textField2.setVisible(false);
		add(textField2);

		// Create text field for phase 2
		textField3 = new TextField();
		textField3.setPreferredSize(new Dimension(100, 20));
		textField3.setFont(new Font("Arial", Font.PLAIN, 22));
		textField3.setBounds(550, 350, 60, 35);
		textField3.setVisible(false);
		add(textField3);

		// Create submit button for phase 2
		submitButton2 = new Button("Submit");
		submitButton2.setBounds(720, 350, 100, 30);
		submitButton2.setFont(new Font("Arial", Font.PLAIN, 18));
		submitButton2.setVisible(false);
		add(submitButton2);

		setLayout(null);
		setVisible(true);

		rulesB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				showRules();
			}
		});

		// Close window when pressing x, top right
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		startPhase1();
		// startPhase2();

	}

	// Method to display rules when the ? button is clicked
	public void showRules() {

		rules = new Dialog(this, "Κανόνες", true);
		rules.setSize(850, 130);
		rules.setLayout(new GridLayout(5, 1));
		Font biggerFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

		Color customColor = new Color(204, 230, 255);
		rules.setBackground(customColor);

		Label rule1 = new Label("Στόχος: ένας παίκτης να έχει τρία πιόνια σε μια ευθεία (κάθετα ή οριζόντια)");
		rule1.setFont(biggerFont);
		Label rule2 = new Label("1. Οι δύο παίκτες επιλέγουν εναλλάξ θέσεις για τα 3 πιόνια που έχει ο κάθε ένας.");
		rule2.setFont(biggerFont);
		Label rule3 = new Label("2. Οι δύο παίκτες επιλέγουν εναλλάξ ένα πιόνι και μια θέση για να το μετακινήοσυν.");
		rule3.setFont(biggerFont);
		Label rule4 = new Label(
				" * Μία μετακίνηση είναι έγκυρη αν είναι άδεια και δίπλα από το πιόνι που επιλέγεται για να μετακινηθεί.");
		rule4.setFont(biggerFont);

		rules.add(rule1);
		rules.add(rule2);
		rules.add(rule3);
		rules.add(rule4);
		rules.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				rules.dispose();
			}
		});
		rules.setLocationRelativeTo(null);
		rules.setVisible(true);
	}

	// Begin the first phase
	public void startPhase1() {
		step = 1;
		takeTurnPhase1();
		if (shouldStartPhase2) {
			startPhase2();
		}
	}

	// Begin the second phase
	public void startPhase2() {
		currentplayer = 1;
		preparePhase2();
		takeTurnPhase2();
	}

	// Every time a turn of a player in phase one is taken this method is called
	// recursively
	private void takeTurnPhase1() {
		// player 1 plays in steps:1,3,5
		// player 2 plays in steps:2,4,6
		if (step % 2 == 1) {
			currentplayer = 1;
		} else {
			currentplayer = 2;
		}

		displayMessage("Player " + currentplayer + " enter your move:");

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input = textField.getText().trim();
				textField.setText("");

				if (!input.isEmpty()) {

					// Check if input is valid
					if (isValidInput(input)) {
						chosenposition = returnIndex(input);

						// Check if position is empty
						if (isPositionEmpty(chosenposition)) {

							board[chosenposition] = currentplayer;
							putToken(chosenposition, currentplayer);
							step++;

							// Check if position is win
							if (isWin(currentplayer)) {
								displayMessage("Game Over - Player " + currentplayer + " wins");
								gameEnded = true;
								textField.setVisible(false);
								submitButton.setVisible(false);
							} else if (step > 6) {
								shouldStartPhase2 = true;
								startPhase1();
							} else {

								takeTurnPhase1();
							}
						} else {
							displayMessage("Position taken - Enter a valid move.");
						}
					} else {
						displayMessage("Invalid input - Enter a valid move.");
					}
				}
			}
		});
	}

	// Every time a turn of a player in phase two is taken this method is called
	// recursively
	public void takeTurnPhase2() {

		displayMessage("Player " + currentplayer + " enter your move (from -> to):");

		submitButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				inputfrom = textField3.getText().trim();
				inputto = textField2.getText().trim();
				textField3.setText("");
				textField2.setText("");

				System.out.println("Input from: " + inputfrom);
				System.out.println("Input to: " + inputto);

				if (!inputfrom.isEmpty() && !inputto.isEmpty()) {

					// Check if inputs are valid
					if (isValidInput(inputfrom) && isValidInput(inputto)) {
						int fromPosition = returnIndex(inputfrom);
						int toPosition = returnIndex(inputto);
						System.out.println("From position: " + fromPosition);
						System.out.println("To position: " + toPosition);

						// Check if move is valid
						if (isValidMove(fromPosition, toPosition, currentplayer)) {
							removeToken(fromPosition);
							board[fromPosition] = 0;
							putToken(toPosition, currentplayer);
							board[toPosition] = currentplayer;

							// Check for win condition
							if (isWin(currentplayer)) {
								displayMessage("Game Over - Player " + currentplayer + " wins");
								gameEnded = true;
								textField3.setVisible(false);
								textField2.setVisible(false);
								submitButton2.setVisible(false);
								arrowLabel.setVisible(false);
							} else {
								// Switch player and continue
								currentplayer = (currentplayer == 1) ? 2 : 1;
								takeTurnPhase2();
							}
						} else {
							displayMessage("Invalid move. Enter a valid move.");
						}
					} else {
						displayMessage("Invalid input. Enter a valid input.");
					}
				}
			}
		});

	}

	// Make the necessary changes to the interface in order to match phase's two
	// features
	public void preparePhase2() {

		textField.setVisible(false);
		submitButton.setVisible(false);

		textField2.setVisible(true);
		textField3.setVisible(true);
		submitButton2.setVisible(true);

		arrowLabel = new Label("->");
		arrowLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		arrowLabel.setBounds(620, 350, 20, 30);
		add(arrowLabel);

		validate();

	}

	// Display on the label the corresponding instruction given as parameter
	public void displayMessage(String message) {
		remove(intsruction);
		intsruction = new Label(message);
		intsruction.setFont(new Font("Arial", Font.PLAIN, 18));
		intsruction.setBounds(500, 290, 300, 30);
		add(intsruction);
		validate();

	}

	// Make it visible on the circles of the interface that the spot is taken by a
	// certain player
	public void putToken(int position, int player) {
		if (position == 0) {
			// a1
			if (player == 1) {
				a1color = Color.red;
			} else {
				a1color = Color.blue;
			}
		} else if (position == 1) {
			// b1
			if (player == 1) {
				b1color = Color.red;
			} else {
				b1color = Color.blue;
			}

		} else if (position == 2) {
			// c1
			if (player == 1) {
				c1color = Color.red;
			} else {
				c1color = Color.blue;
			}

		} else if (position == 3) {
			// a2
			if (player == 1) {
				a2color = Color.red;
			} else {
				a2color = Color.blue;
			}

		} else if (position == 4) {
			// b2
			if (player == 1) {
				b2color = Color.red;
			} else {
				b2color = Color.blue;
			}

		} else if (position == 5) {
			// c2
			if (player == 1) {
				c2color = Color.red;
			} else {
				c2color = Color.blue;
			}

		} else if (position == 6) {
			// a3
			if (player == 1) {
				a3color = Color.red;
			} else {
				a3color = Color.blue;
			}

		} else if (position == 7) {
			// b3
			if (player == 1) {
				b3color = Color.red;
			} else {
				b3color = Color.blue;
			}

		} else if (position == 8) {
			// c3
			if (player == 1) {
				c3color = Color.red;
			} else {
				c3color = Color.blue;
			}

		}
		repaint();
	}

	// Remove color of corresponding circle on the interface to show that spot is
	// not taken
	public void removeToken(int position) {
		if (position == 0) {
			// a1
			a1color = Color.WHITE;
		} else if (position == 1) {
			// b1
			b1color = Color.WHITE;
		} else if (position == 2) {
			// c1
			c1color = Color.WHITE;
		} else if (position == 3) {
			// a2
			a2color = Color.WHITE;
		} else if (position == 4) {
			// b2
			b2color = Color.WHITE;
		} else if (position == 5) {
			// c2
			c2color = Color.WHITE;
		} else if (position == 6) {
			// a3
			a3color = Color.WHITE;
		} else if (position == 7) {
			// b3
			b3color = Color.WHITE;
		} else if (position == 8) {
			// c3
			c3color = Color.WHITE;
		}
		repaint();
	}

	// Method that builts the whole interface
	public void paint(Graphics g) {
		super.paint(g);
		int width = getSize().width;
		int height = getSize().height;

		// Draw the title
		Font titleFont = new Font("Arial", Font.BOLD, 24);
		g.setFont(titleFont);
		FontMetrics fm = g.getFontMetrics();
		int titleWidth = fm.stringWidth("Αντρίν");
		g.drawString("Αντρίν", (width - titleWidth) / 2, 50);

		// Draw the line in the middle
		Graphics2D g2d = (Graphics2D) g;
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));

		g.drawLine(width / 2, 70, width / 2, height - 50);

		// Draw the board on the left side
		int leftCenterX = width / 4;
		int leftCenterY = height / 2;
		int scale = 2;
		int squareSize = Math.min(width, height) / 3 * scale;
		g.drawRect(leftCenterX - squareSize / 2, leftCenterY - squareSize / 2, squareSize, squareSize);
		g.drawLine(leftCenterX - squareSize / 2, leftCenterY, leftCenterX + squareSize / 2, leftCenterY);
		g.drawLine(leftCenterX, leftCenterY - squareSize / 2, leftCenterX, leftCenterY + squareSize / 2);

		// START: Draw the circles on the left board
		int boardX_left = leftCenterX - squareSize / 2;
		int boardX_right = leftCenterX + squareSize / 2;
		int boardY_top = leftCenterY - squareSize / 2;
		int boardY_bottom = leftCenterY + squareSize / 2;

		int circleSize = 50;

		// g.setColor(Color.WHITE);
		// corners

		g.setColor(a1color);
		g.fillOval(boardX_left - circleSize / 2, boardY_top - 18, circleSize, circleSize);
		a1X = boardX_left - circleSize / 2;
		a1Y = boardY_top - 18;

		g.setColor(c1color);
		g.fillOval(boardX_right - circleSize / 2, boardY_top - 18, circleSize, circleSize);
		c1X = boardX_right - circleSize / 2;
		c1Y = boardY_top - 18;

		g.setColor(a3color);
		g.fillOval(boardX_left - circleSize / 2, boardY_bottom - 20, circleSize, circleSize);
		a3X = boardX_left - circleSize / 2;
		a3Y = boardY_bottom - 20;

		g.setColor(c3color);
		g.fillOval(boardX_right - circleSize / 2, boardY_bottom - 20, circleSize, circleSize);
		c3X = boardX_right - circleSize / 2;
		c3Y = boardY_bottom - 20;

		// middles

		g.setColor(a2color);
		g.fillOval(boardX_left - circleSize / 2, leftCenterY - 20, circleSize, circleSize);
		a2X = boardX_left - circleSize / 2;
		a2Y = leftCenterY - 20;

		g.setColor(c2color);
		g.fillOval(boardX_right - circleSize / 2, leftCenterY - 20, circleSize, circleSize);
		c2X = boardX_right - circleSize / 2;
		c2Y = leftCenterY - 20;

		g.setColor(b1color);
		g.fillOval(leftCenterX - 24, boardY_top - 22, circleSize, circleSize);
		b1X = leftCenterX - 24;
		b1Y = boardY_top - 22;

		g.setColor(b3color);
		g.fillOval(leftCenterX - 24, boardY_bottom - 22, circleSize, circleSize);
		b3X = leftCenterX - 24;
		b3Y = boardY_bottom - 22;

		// center
		g.setColor(b2color);
		g.fillOval(leftCenterX - circleSize / 2, leftCenterY - 22, circleSize, circleSize);
		b2X = leftCenterX - circleSize / 2;
		b2Y = leftCenterY - 22;

		g.setColor(Color.BLACK);
		// corners
		g.drawOval(boardX_left - circleSize / 2, boardY_top - 18, circleSize, circleSize);
		g.drawOval(boardX_right - circleSize / 2, boardY_top - 18, circleSize, circleSize);
		g.drawOval(boardX_left - circleSize / 2, boardY_bottom - 20, circleSize, circleSize);
		g.drawOval(boardX_right - circleSize / 2, boardY_bottom - 20, circleSize, circleSize);

		// middles
		g.drawOval(boardX_left - circleSize / 2, leftCenterY - 20, circleSize, circleSize);
		g.drawOval(boardX_right - circleSize / 2, leftCenterY - 20, circleSize, circleSize);
		g.drawOval(leftCenterX - 24, boardY_top - 22, circleSize, circleSize);
		g.drawOval(leftCenterX - 24, boardY_bottom - 22, circleSize, circleSize);

		// center
		g.drawOval(leftCenterX - circleSize / 2, leftCenterY - 22, circleSize, circleSize);

		g2d.setStroke(oldStroke);

		// Draw the board on the right side
		int rightCenterX = width * 3 / 4;
		int rightCenterY = height / 4 + 40;
		double scaleright = 2;

		int rightsquareSize = (int) (Math.min(width, height) / 6 * scaleright);
		g.drawRect(rightCenterX - rightsquareSize / 2, rightCenterY - rightsquareSize / 2, rightsquareSize,
				rightsquareSize);
		g.drawLine(rightCenterX - rightsquareSize / 2, rightCenterY, rightCenterX + rightsquareSize / 2, rightCenterY);
		g.drawLine(rightCenterX, rightCenterY - rightsquareSize / 2, rightCenterX, rightCenterY + rightsquareSize / 2);

		// START: Draw the codes on the board on the right side
		FontMetrics fm1 = g.getFontMetrics();
		int labelWidth = 28;
		int labelHeight = fm1.getHeight();

		int labelX_left = rightCenterX - rightsquareSize / 2 - 10;
		int labelY_top = rightCenterY - rightsquareSize / 2 - labelHeight + fm1.getAscent();
		int labelX_right = rightCenterX + rightsquareSize / 2 - 20;
		int labelY_bottom = rightCenterY + rightsquareSize / 2 - labelHeight + fm1.getAscent() - 10;

		g.setColor(Color.WHITE);
		// corners
		g.fillRect(labelX_left, labelY_top, labelWidth + 10, labelHeight);
		g.fillRect(labelX_right, labelY_top, labelWidth + 10, labelHeight);
		g.fillRect(labelX_left, labelY_bottom, labelWidth + 10, labelHeight);
		g.fillRect(labelX_right, labelY_bottom, labelWidth + 10, labelHeight);

		// center
		g.fillRect(rightCenterX - 17, rightCenterY - 10, labelWidth + 10, labelHeight);

		// middles
		g.fillRect(labelX_left, rightCenterY - 10, labelWidth + 10, labelHeight);
		g.fillRect(labelX_right, rightCenterY - 10, labelWidth + 10, labelHeight);
		g.fillRect(rightCenterX - 17, labelY_top, labelWidth + 10, labelHeight);
		g.fillRect(rightCenterX - 17, labelY_bottom, labelWidth + 10, labelHeight);

		g.setColor(Color.BLACK);
		// corners
		g.drawString("a1", labelX_left + 5, labelY_top + fm1.getAscent());
		g.drawString("c1", labelX_right + 5, labelY_top + fm1.getAscent());
		g.drawString("a3", labelX_left + 5, labelY_bottom + fm1.getAscent());
		g.drawString("c3", labelX_right + 5, labelY_bottom + fm1.getAscent());

		// center
		g.drawString("b2", rightCenterX - 12, rightCenterY + fm1.getAscent() - 12);

		// middles
		g.drawString("a2", labelX_left + 5, rightCenterY + fm1.getAscent() - 12);
		g.drawString("c2", labelX_right + 5, rightCenterY + fm1.getAscent() - 12);
		g.drawString("b1", rightCenterX - 12, labelY_top + fm1.getAscent());
		g.drawString("b3", rightCenterX - 12, labelY_bottom + fm1.getAscent());
		// END: Draw the codes on the board on the right side

	}

	// Check if the string input is one of the nine acceptable choices
	public static boolean isValidInput(String temp) {
		return temp.equals("a1") || temp.equals("b1") || temp.equals("c1") || temp.equals("a2") || temp.equals("b2")
				|| temp.equals("c2") || temp.equals("a3") || temp.equals("b3") || temp.equals("c3");

	}

	// Check if chosen position is empty
	public static boolean isPositionEmpty(int temp) {
		if (board[temp] == 0) {
			return true;
		}
		return false;
	}

	// Check for if: position is empty, position is adjacent, player owns the chosen
	// token
	public static boolean isValidMove(int curr, int next, int player) {

		if ((curr == 0 && (next == 1 || next == 3 || next == 4)) || (curr == 1 && (next == 0 || next == 2 || next == 4))
				|| (curr == 2 && (next == 1 || next == 4 || next == 5))
				|| (curr == 3 && (next == 0 || next == 4 || next == 6))
				|| (curr == 4 && (next == 1 || next == 3 || next == 5 || next == 7))
				|| (curr == 5 && (next == 2 || next == 4 || next == 8))
				|| (curr == 6 && (next == 3 || next == 7 || next == 4))
				|| (curr == 7 && (next == 4 || next == 6 || next == 8))
				|| (curr == 8 && (next == 5 || next == 7 || next == 4))) {

			if (board[curr] == player && board[next] == 0) {

				return true;
			}
		}
		return false;
	}

	// Check if the current board is a win given the player number
	public static boolean isWin(int player) {
		if (board[0] == player && board[1] == player && board[2] == player) {
			return true;
		} else if (board[3] == player && board[4] == player && board[5] == player) {
			return true;
		} else if (board[6] == player && board[7] == player && board[8] == player) {
			return true;
		} else if (board[0] == player && board[3] == player && board[6] == player) {
			return true;
		} else if (board[1] == player && board[4] == player && board[7] == player) {
			return true;
		} else if (board[2] == player && board[5] == player && board[8] == player) {
			return true;
		}

		return false;
	}

	// Given string input of choice return the corresponding index on the array
	public static int returnIndex(String temp) {
		int index = 0;
		if (temp.equals("a1")) {
			index = 0;
		} else if (temp.equals("b1")) {
			index = 1;
		} else if (temp.equals("c1")) {
			index = 2;
		} else if (temp.equals("a2")) {
			index = 3;
		} else if (temp.equals("b2")) {
			index = 4;
		} else if (temp.equals("c2")) {
			index = 5;
		} else if (temp.equals("a3")) {
			index = 6;
		} else if (temp.equals("b3")) {
			index = 7;
		} else if (temp.equals("c3")) {
			index = 8;
		}
		return index;
	}

	public static void main(String[] args) {
		new Interface_Checks_Final();

	}
}
