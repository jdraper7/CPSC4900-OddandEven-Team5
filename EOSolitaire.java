import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class EOSolitaire {
	// CONSTANTS
	private static final String title = "Even & Odd";
	public static final int NUM_FINAL_DECKS = 4;
	public static final int NUM_TABLEAU_DECKS = 9;
	public static final int NUM_RESERVE_DECKS = 3;
	public static final Point DECK_POS = new Point(5, 5);

	public static final Point SHOW_POS = new Point(DECK_POS.x + EOCard.CARD_WIDTH + 5, DECK_POS.y);
	public static final Point FINAL_POS = new Point(SHOW_POS.x + EOCard.CARD_WIDTH + 265, DECK_POS.y);
	public static final Point PLAY_POS = new Point(DECK_POS.x, FINAL_POS.y + EOCard.CARD_HEIGHT + 260);

	private static int TABLE_WIDTH;
	private static int TABLE_HEIGHT;

	// GAMEPLAY STRUCTURES
	private static EOCardStack deck; // populated with standard 52 card deck
	private static EOCardStack[] tableau; // Tableau stacks
	private static EOCardStack[] reserve; // Reserve stacks
	private static EOFinalStack[] foundationA;// FoundationA Stacks
	private static EOFinalStack[] foundationB;// FoundationB Stacks
	private static EOWasteStack waste;// waste card spot

	// GUI COMPONENTS (top level)
	private static final JFrame frame = new JFrame("Even and Odd Solitaire");
	private static JFrame gpFrame;
	protected static final ImagePanel table = new ImagePanel(new ImageIcon("assets/images/backgrounds/even_and_odd.jpg").getImage());
	// other components
	private static final ToggleTimerButton button_toggle_timer = new ToggleTimerButton();
	private static final JTextField timeBox = new JTextField();// displays the time
	private static final JTextField statusBox = new JTextField();// status messages
	private static final JTextField text_field_score = new JTextField();
	private static final EOCard newCardButton = new EOCard();// reveal waste card
	private static boolean is_win = false;

	// TIMER UTILITIES
	private static Timer timer = new Timer();
	private static ScoreClock scoreClock = new ScoreClock();

	// MISC TRACKING VARIABLES
	public static boolean timeRunning = false;// timer running?
	public static int score = 0;// keep track of the score
	public static int time = 0;// keep track of seconds elapsed
	public static boolean validMoveMade = false;

	public EOSolitaire(int tw, int th, JFrame gp) {
		TABLE_WIDTH = tw;
		TABLE_HEIGHT = th;
		gpFrame = gp;

		gpFrame.setVisible(false);

		frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GamePlatform.saveScore(time, score, is_win, title);
			}
		});

		table.setLayout(null);

		Container contentPane = frame.getContentPane();
		contentPane.add(table);
		
		playNewGame();
	}

	private static void playNewGame() {
		clearTable();
		newFoundation();
		newDeck();
		newReserve();
		newTableau();
		newWaste();
		dealCards();

		NewGameButton button_new_game = new NewGameButton();
		button_new_game.setBounds(0, TABLE_HEIGHT - 70, 120, 30);
		table.add(button_new_game);

		ShowRulesButton button_show_rules = new ShowRulesButton();
		button_show_rules.setBounds(120, TABLE_HEIGHT - 70, 120, 30);
		table.add(button_show_rules);

		text_field_score.setBounds(240, TABLE_HEIGHT - 70, 120, 30);
		text_field_score.setForeground(Color.white);
		text_field_score.setText("Score: 0");
		text_field_score.setFont(new Font("Dialog", Font.BOLD, 14));
		text_field_score.setBackground(Color.black);
		text_field_score.setBorder(new LineBorder(Color.gray, 3));

		text_field_score.setEditable(false);
		text_field_score.setOpaque(true);
		table.add(text_field_score);

		timeBox.setBounds(360, TABLE_HEIGHT - 70, 120, 30);
		timeBox.setForeground(Color.white);
		timeBox.setText("Seconds: 0");
		timeBox.setFont(new Font("Dialog", Font.BOLD, 14));
		timeBox.setBackground(Color.black);
		timeBox.setBorder(new LineBorder(Color.gray, 3));
		timeBox.setEditable(false);
		timeBox.setOpaque(true);
		table.add(timeBox);

		startTimer();

		button_toggle_timer.setBounds(480, TABLE_HEIGHT - 70, 125, 30);
		table.add(button_toggle_timer);


		statusBox.setBounds(605, TABLE_HEIGHT - 70, 180, 30);
		statusBox.setForeground(Color.white);
		statusBox.setBackground(Color.black);
		statusBox.setEditable(false);
		statusBox.setBorder(new LineBorder(Color.gray, 3));

		statusBox.setFont(new Font("Dialog", Font.BOLD, 14));
		statusBox.setOpaque(true);
		table.add(statusBox);


		ExitGameButton button_exit_game = new ExitGameButton();
		button_exit_game.setBounds(785, TABLE_HEIGHT - 70, 120, 30);
		table.add(button_exit_game);

		table.repaint();
	}

	protected static void clearTable() {
		score = 0;
		time = 0;
		is_win = false;
		table.removeAll();
		if (tableau != null && reserve != null && foundationA != null && foundationB != null) {
			for (int x = 0; x < NUM_TABLEAU_DECKS; x++) tableau[x].makeEmpty();
			for (int x = 0; x < NUM_RESERVE_DECKS; x++) reserve[x].makeEmpty();
			for (int x = 0; x < NUM_FINAL_DECKS; x++) foundationA[x].makeEmpty();
			for (int x = 0; x < NUM_FINAL_DECKS; x++) foundationB[x].makeEmpty();
		}
	}

	protected static void newFoundation() {
		foundationA = new EOFinalStack[NUM_FINAL_DECKS];
		foundationB = new EOFinalStack[NUM_FINAL_DECKS];

		for (int x = 0; x < NUM_FINAL_DECKS; x++) {
			foundationA[x] = new EOFinalStack();
			foundationA[x].setXY((FINAL_POS.x + (x * EOCard.CARD_WIDTH)) + 10, FINAL_POS.y);
			table.add(foundationA[x]);
			foundationB[x] = new EOFinalStack();
			foundationB[x].setXY((FINAL_POS.x + ((x + 4) * EOCard.CARD_WIDTH)) + 40, FINAL_POS.y);
			table.add(foundationB[x]);
		}
	}

	protected static void newDeck() {
		table.add(moveCard(newCardButton, DECK_POS.x, DECK_POS.y));
	}

	protected static void newReserve() {
		reserve = new EOCardStack[NUM_RESERVE_DECKS];
		for (int x = 0; x < NUM_RESERVE_DECKS; x++) {
			reserve[x] = new EOCardStack(false);
			reserve[x].setXY((DECK_POS.x + (x * (EOCard.CARD_WIDTH + 10))), PLAY_POS.y);
			table.add(reserve[x]);
		}
	}

	protected static void newTableau() {
		tableau = new EOCardStack[NUM_TABLEAU_DECKS];
		for (int x = 0; x < NUM_TABLEAU_DECKS; x++) {
			tableau[x] = new EOCardStack(false);
			tableau[x].setXY((DECK_POS.x + ((x + 3) * (EOCard.CARD_WIDTH + 10))), PLAY_POS.y);
			table.add(tableau[x]);
		}
	}

	protected static void newWaste() {
		waste = new EOWasteStack();
		waste.setXY(SHOW_POS.x, SHOW_POS.y);
		table.add(waste);
	}

	protected static void dealCards() {
		deck = new EOCardStack(true); // deal 52 cards
		deck.shuffle();
		for (int x = 0; x < NUM_RESERVE_DECKS; x++) {
			for (int y = 0; y < 5; y++) reserve[x].putFirst(deck.pop());
			reserve[x].putFirst(deck.pop().setFaceup());
		}
		for (int x = 0; x < NUM_TABLEAU_DECKS; x++) {
			EOCard c = deck.pop().setFaceup();
			tableau[x].putFirst(c);
		}
		waste.push(deck.pop().setFaceup());
	}

	// moves a card to abs location within a component
	protected static EOCard moveCard(EOCard c, int x, int y) {
		c.setBounds(new Rectangle(new Point(x, y), new Dimension(EOCard.CARD_WIDTH + 10, EOCard.CARD_HEIGHT + 10)));
		c.setXY(new Point(x, y));
		return c;
	}

	// add/subtract points based on gameplay actions
	protected static void setScore(int deltaScore) {
		EOSolitaire.score += deltaScore;
		String newScore = "Score: " + EOSolitaire.score;
		text_field_score.setText(newScore);
//		text_field_score.setForeground(Color.white);
		text_field_score.repaint();
	}

	// GAME TIMER UTILITIES
	protected static void updateTimer() {
		EOSolitaire.time += 1;
		// every 10 seconds elapsed we take away 2 points
		if(EOSolitaire.time % 10 == 0) setScore(-2);
		String time = "Seconds: " + EOSolitaire.time;
		timeBox.setText(time);
		timeBox.repaint();
	}

	protected static void startTimer() {
		if(timeRunning) {
			scoreClock.cancel();
			scoreClock = new ScoreClock();
				timer = new Timer();
				timeRunning = true;
				timer.scheduleAtFixedRate(scoreClock, 1000, 1000);
		} else {
			scoreClock = new ScoreClock();
			// set the timer to update every second
			timer = new Timer();
			timeRunning = true;
			timer.scheduleAtFixedRate(scoreClock, 1000, 1000);
		}
		button_toggle_timer.setText("Pause Timer");
	}

	private static class ScoreClock extends TimerTask {
		@Override
		public void run() {
			updateTimer();
		}
	}

	static class NewGameButton extends JButton implements MouseListener{

		public NewGameButton() {
			setText("New Game");
			setFont(new Font("Dialog", Font.BOLD, 12));
			setForeground(Color.green);
			setBackground(Color.black);
			setContentAreaFilled(true);
			setBorder(new LineBorder(Color.white, 3));
			setBorderPainted(true);
			setVisible(true);
			setFocusable(true);
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			GamePlatform.saveScore(time, score, is_win, title);
			timer.cancel();
			playNewGame();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setBorder(new LineBorder(Color.green, 3));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBorder(new LineBorder(Color.white, 3));
		}
	}

	static class ShowRulesButton extends JButton implements MouseListener{
		private static int dialog_rules_width = 800;
		private static int dialog_rules_height = 600;
		private static String rules_text = "<b>Even and Odd Solitaire - Rules</b>"
				+ "<br><br><b>Cards in play:</b> 1 deck (52 cards)."
				+ "<br><br><b>Difficulty:</b> EASY"
				+ "<br><br><b>Even and Odd Solitaire </b>uses one deck (52 cards). You have 9 tableau piles with one card in each pile and 3 reserve piles (with 6 cards in each pile). You also have 8 foundation piles."
				+ "<br><br><b>Objective</b>"
				+ "<br><ul><li>Build up the left four foundations in ascending sequence regardless of suit by twos click_positioning with Ace (Ace,3,5,7,9,J,K) and</li>"
				+ "<li>Build up the right four foundations in ascending sequence regardless of suit by twos click_positioning with 2 (2,4,6,8,10,Q)</li></ul>"
				+ "<b>Rules</b>"
				+ "<br>Each tableau pile may contain only one card. All cards in tableaus, top cards of reserve, stock and waste piles are available to play. Spaces in tableaus are filled from waste or stock piles. Empty reserve piles cannot be filled."
				+ "<br><br>When you have made all the moves initially available, begin turning over cards from the stock pile."
				+ "<br><br>There is no redeal.";

		public ShowRulesButton() {
			setText("Show Rules");
			setFont(new Font("Dialog", Font.PLAIN, 12));
			setForeground(Color.green);
			setBackground(Color.black);
			setContentAreaFilled(true);
			setBorder(new LineBorder(Color.white, 3));
			setBorderPainted(true);
			setVisible(true);
			setFocusable(true);
			addMouseListener(this);
		}

		public void showRulesDialog() {
			JDialog dialog_rules = new JDialog(frame, true);
			dialog_rules.setSize(dialog_rules_width, dialog_rules_height);
			dialog_rules.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			JEditorPane pane_rules_text = new JEditorPane("text/html", "");
			pane_rules_text.setEditable(false);
			pane_rules_text.setText(this.rules_text);
			dialog_rules.add(new JScrollPane(pane_rules_text));
			dialog_rules.setVisible(true);
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			showRulesDialog();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setBorder(new LineBorder(Color.green, 3));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBorder(new LineBorder(Color.white, 3));
		}
	}

	static class ToggleTimerButton extends JButton implements MouseListener{

		public ToggleTimerButton() {
			setText("Pause Timer");
			setFont(new Font("Dialog", Font.PLAIN, 12));
			setForeground(Color.green);
			setBackground(Color.black);
			setContentAreaFilled(true);
			setBorder(new LineBorder(Color.white, 3));
			setBorderPainted(true);
			setVisible(true);
			setFocusable(true);
			addMouseListener(this);
		}

		protected void toggleTimer() {
			if (timeRunning) {
				pauseTimer();
			} else {
				resumeTimer();
			}
		}

		protected void pauseTimer() {
			setText("Resume Timer");
			scoreClock.cancel();
			timeRunning = false;
		}

		protected void resumeTimer() {
			setText("Pause Timer");
			scoreClock = new ScoreClock();
			// set the timer to update every second
			timer = new Timer();
			timer.scheduleAtFixedRate(scoreClock, 1000, 1000);
			timeRunning = true;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			toggleTimer();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setBorder(new LineBorder(Color.yellow, 3));

		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBorder(new LineBorder(Color.white, 3));

		}
	}

	static class ExitGameButton extends JButton implements MouseListener{

		public ExitGameButton() {
			setText("Return to Menu");
			setFont(new Font("Dialog", Font.PLAIN, 14));
			setForeground(Color.red);
			setBackground(Color.black);
			setContentAreaFilled(true);
			setBorder(new LineBorder(Color.white, 3));
			setBorderPainted(true);
			setVisible(true);
			setFocusable(true);
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			GamePlatform.saveScore(time, score, is_win, title);
			frame.setVisible(false);
			frame.dispose();
			gpFrame.setVisible(true);
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setBorder(new LineBorder(Color.red, 3));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBorder(new LineBorder(Color.white, 3));
		}
	}

	static class ImagePanel extends JPanel  {
		private Image img;

		public ImagePanel(Image img) {
			this.img = img;
			Dimension size = new Dimension(1080, 5500);
			setPreferredSize(size);
			setSize(size);
			setLayout(null);
			addMouseListener(new CardMovementManager());
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	/*
	 * This class handles all of the logic of moving the Card components as well
	 * as the game logic. This determines where Cards can be moved according to
	 * the rules of Even and Odd Solitaire
	 */
	static class CardMovementManager implements MouseListener {
		private boolean is_the_game_won = false;// should we check if game is over?
		private boolean is_game_over = true;// easier to negate this than affirm it
		private EOCard card = null; // card to be moved
		private EOCardStack source = null;
		private boolean is_source_in_tableau = false;
		private EOCardStack dest = null;

		private boolean validFinalStackMove(EOCard clicked_card, EOCard dest) {
			int s_val = clicked_card.getValue().ordinal();
			int d_val = dest.getValue().ordinal();
			if (s_val == (d_val + 2)) // destination must be two lower
				return true;
			else
				return false;
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Point start = e.getPoint();
			boolean stopSearch = false;
			statusBox.setText("");

			for (int x = 0; x < (NUM_RESERVE_DECKS); x++) {
				if (stopSearch)
					break;
				source = reserve[x];
				// pinpointing exact card pressed
				for (Component ca : source.getComponents()) {
					EOCard c = (EOCard) ca;
					if (c.contains(start) && source.contains(start) && c.getFaceStatus()) {
						card = c;
						stopSearch = true;
						break;
					}
				}
			}
			if (card == null) {
				for (int x = 0; x < (NUM_TABLEAU_DECKS); x++) {
					if (stopSearch)
						break;
					source = tableau[x];
					// pinpointing exact card pressed
					for (Component ca : source.getComponents()) {
						EOCard c = (EOCard) ca;
						if (c.contains(start) && source.contains(start) && c.getFaceStatus()) {
							card = c;
							stopSearch = true;
							is_source_in_tableau = true;
							break;
						}
					}
				}
			}
			if (card == null && waste.contains(start)) {
				card = waste.pop();
			}

			// SHOW (WASTE) CARD OPERATIONS
			// display new show card
			if (newCardButton.contains(start) && deck.showSize() > 0) {
				System.out.println("new card button");
				EOCard c = deck.pop().setFaceup();
				waste.push(c);
				c.repaint();
				table.repaint();
			}

			// used for status bar updates
			validMoveMade = false;

			// SHOW CARD MOVEMENTS
			if (waste.contains(start) && !newCardButton.contains(start)) {
				System.out.println("1");
				// Moving from SHOW TO FINAL
				if (card.getValue().ordinal() % 2 == 0) {
					System.out.println("2");
					for (int x = 0; x < NUM_FINAL_DECKS; x++) {
						System.out.println("3");
						dest = foundationA[x];
						// only aces can go first
						if (dest.empty()) {
							if (card.getValue() == EOCard.Value.ACE) {
								dest.push(card);
								dest.repaint();
								table.repaint();
								card = null;
								setScore(10);
								validMoveMade = true;
								break;
							}
						} else if (validFinalStackMove(card, dest.getLast())) {
							dest.push(card);
							dest.repaint();
							table.repaint();
							card = null;
							is_the_game_won = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				} else {
					for (int x = 0; x < NUM_FINAL_DECKS; x++) {
						dest = foundationB[x];
						// only twos can go first
						if (dest.empty()) {
							if (card.getValue() == EOCard.Value.TWO) {
								dest.push(card);
								dest.repaint();
								table.repaint();
								card = null;
								setScore(10);
								validMoveMade = true;
								break;
							}
						} else if (validFinalStackMove(card, dest.getLast())) {
							dest.push(card);
							dest.repaint();
							table.repaint();
							card = null;
							is_the_game_won = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				}
			}

			if (card != null && source != null && card.getFaceStatus()) {
				if (card.getValue().ordinal() % 2 == 0) {
					for (int x = 0; x < NUM_FINAL_DECKS; x++) {
						dest = foundationA[x];

						// TO EMPTY STACK
						if (dest.empty())// empty final should only take an ACE
						{
							if (card.getValue() == EOCard.Value.ACE) {
								EOCard c = source.popFirst();
								c.repaint();
								if (source.getFirst() != null) {
									EOCard temp = source.getFirst().setFaceup();
									temp.repaint();
									source.repaint();
								}
								dest.setXY(dest.getXY().x, dest.getXY().y);
								dest.push(c);
								dest.repaint();
								table.repaint();
								dest.showSize();
								card = null;
								setScore(10);
								validMoveMade = true;
								break;
							}// TO POPULATED STACK
						} else if (validFinalStackMove(card, dest.getLast())) {
							EOCard c = source.popFirst();
							c.repaint();
							if (source.getFirst() != null) {
								EOCard temp = source.getFirst().setFaceup();
								temp.repaint();
								source.repaint();
							}
							dest.setXY(dest.getXY().x, dest.getXY().y);
							dest.push(c);
							dest.repaint();
							table.repaint();
							dest.showSize();
							card = null;
							is_the_game_won = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				} else {
					for (int x = 0; x < NUM_FINAL_DECKS; x++) {
						dest = foundationB[x];

						// TO EMPTY STACK
						if (dest.empty())// empty final should only take an ACE
						{
							if (card.getValue() == EOCard.Value.TWO) {
								EOCard c = source.popFirst();
								c.repaint();
								if (source.getFirst() != null) {
									EOCard temp = source.getFirst().setFaceup();
									temp.repaint();
									source.repaint();
								}
								dest.setXY(dest.getXY().x, dest.getXY().y);
								dest.push(c);
								dest.repaint();
								table.repaint();
								dest.showSize();
								card = null;
								setScore(10);
								validMoveMade = true;
								break;
							}// TO POPULATED STACK
						} else if (validFinalStackMove(card, dest.getLast())) {
							EOCard c = source.popFirst();
							c.repaint();
							if (source.getFirst() != null) {
								EOCard temp = source.getFirst().setFaceup();
								temp.repaint();
								source.repaint();
							}
							dest.setXY(dest.getXY().x, dest.getXY().y);
							dest.push(c);
							dest.repaint();
							table.repaint();
							dest.showSize();
							card = null;
							is_the_game_won = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				}
				if (is_source_in_tableau && validMoveMade) {
					if (!waste.empty()) {
						source.push(waste.pop().setFaceup());
					} else if (!deck.empty()) {
						source.push(deck.pop().setFaceup());
					}
				}
			}

			// SHOWING STATUS MESSAGE IF MOVE INVALID
			if (!validMoveMade && dest != null && card != null) {
				statusBox.setText("That Is Not A Valid Move");
			}

			// CHECKING FOR WIN
			if (is_the_game_won) {
				boolean gameNotOver = false;
				// cycle through final decks, if they're all full then game over
				for (int x = 0; x < NUM_FINAL_DECKS; x++) {
					dest = foundationA[x];
					if (dest.showSize() != 7) {
						// one deck is not full, so game is not over
						gameNotOver = true;
						break;
					}
				}
				for (int x = 0; x < NUM_FINAL_DECKS; x++) {
					dest = foundationB[x];
					if (dest.showSize() != 6) {
						// one deck is not full, so game is not over
						gameNotOver = true;
						break;
					}
				}
				if (!gameNotOver)
					is_game_over = true;
			}

			if (is_the_game_won && is_game_over) {
				scoreClock.cancel();
				timeRunning = false;
				JOptionPane.showMessageDialog(table, "Congratulations! You've Won!");
				statusBox.setText("Game Over!");
				is_win = true;
			}

			if (waste.empty() && !deck.empty()) {
				waste.push(deck.pop().setFaceup());
			}

			// RESET VARIABLES FOR NEXT EVENT
			source = null;
			dest = null;
			card = null;
			is_the_game_won = false;
			is_game_over = false;
			is_source_in_tableau = false;
		}

		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent evt) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
}

