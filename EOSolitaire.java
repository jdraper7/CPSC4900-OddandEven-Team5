import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EOSolitaire
{
	// CONSTANTS
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
	protected static final JPanel table = new JPanel();
	// other components
	private static JButton showRulesButton = new JButton("Show Rules");
	private static JButton menuReturnButton = new JButton("Return to Menu");
	private static JButton newGameButton = new JButton("New Game");
	private static JTextField scoreBox = new JTextField();// displays the score
	private static JTextField timeBox = new JTextField();// displays the time
	private static JButton toggleTimerButton = new JButton("Pause Timer");
	private static JTextField statusBox = new JTextField();// status messages
	private static final EOCard newCardButton = new EOCard();// reveal waste card
	private static NewGameListener ngl = new NewGameListener();
	private static MenuReturnListener mrl = new MenuReturnListener();
	private static ToggleTimerListener ttl = new ToggleTimerListener();
	private static ShowRulesListener srl = new ShowRulesListener();
	private static CardMovementManager cmm = new CardMovementManager();
	private static boolean win = false;

	// TIMER UTILITIES
	private static Timer timer;
	private static ScoreClock scoreClock = new ScoreClock();

	// MISC TRACKING VARIABLES
	public static boolean timeRunning = false;// timer running?
	public static int score = 0;// keep track of the score
	public static int time = 0;// keep track of seconds elapsed

	public EOSolitaire(int tw, int th, JFrame gp)
	{
		gpFrame = gp;
		gpFrame.setVisible(false);
		TABLE_WIDTH = tw; TABLE_HEIGHT = th;
		Container contentPane;
		frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
		table.setLayout(null);
		table.setBackground(new Color(0, 180, 0));
		contentPane = frame.getContentPane();
		contentPane.add(table);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playNewGame();
		table.addMouseListener(cmm);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				GamePlatform.SaveEOScore(time, score, win);
			}
		});
	}

	static void playNewGame()
	{
		score = 0; time = 0; win = false;
		deck = new EOCardStack(true); // deal 52 cards
		deck.shuffle();
		table.removeAll();
		// reset stacks if user starts a new game in the middle of one
		if (tableau != null && reserve != null && foundationA != null && foundationB != null)
		{
			for (int x = 0; x < NUM_TABLEAU_DECKS; x++)
			{
				tableau[x].makeEmpty();
			}
			for (int x = 0; x < NUM_RESERVE_DECKS; x++)
			{
				reserve[x].makeEmpty();
			}
			for (int x = 0; x < NUM_FINAL_DECKS; x++)
			{
				foundationA[x].makeEmpty();
			}
			for (int x = 0; x < NUM_FINAL_DECKS; x++)
			{
				foundationB[x].makeEmpty();
			}
		}
		// initialize & place final (foundation) decks/stacks
		foundationA = new EOFinalStack[NUM_FINAL_DECKS];
		foundationB = new EOFinalStack[NUM_FINAL_DECKS];
		for (int x = 0; x < NUM_FINAL_DECKS; x++)
		{
			foundationA[x] = new EOFinalStack();
			foundationA[x].setXY((FINAL_POS.x + (x * EOCard.CARD_WIDTH)) + 10, FINAL_POS.y);
			table.add(foundationA[x]);

			foundationB[x] = new EOFinalStack();
			foundationB[x].setXY((FINAL_POS.x + ((x+4) * EOCard.CARD_WIDTH)) + 40, FINAL_POS.y);
			table.add(foundationB[x]);

		}
		// place new card distribution button
		table.add(moveCard(newCardButton, DECK_POS.x, DECK_POS.y));
		// initialize & place play (tableau) decks/stacks
		reserve = new EOCardStack[NUM_RESERVE_DECKS];
		for (int x = 0; x < NUM_RESERVE_DECKS; x++)
		{
			reserve[x] = new EOCardStack(false);
			reserve[x].setXY((DECK_POS.x + (x * (EOCard.CARD_WIDTH + 10))), PLAY_POS.y);
			table.add(reserve[x]);
		}
		tableau = new EOCardStack[NUM_TABLEAU_DECKS];
		for (int x = 0; x < NUM_TABLEAU_DECKS; x++)
		{
			tableau[x] = new EOCardStack(false);
			tableau[x].setXY((DECK_POS.x + ((x+3) * (EOCard.CARD_WIDTH + 10))), PLAY_POS.y);
			table.add(tableau[x]);
		}
		waste = new EOWasteStack();
		waste.setXY(SHOW_POS.x, SHOW_POS.y);
		table.add(waste);

		// Dealing new game
		for (int x = 0; x < NUM_RESERVE_DECKS; x++)
		{
			for (int y = 0; y < 5; y++)
			{
				reserve[x].putFirst(deck.pop());
			}
			reserve[x].putFirst(deck.pop().setFaceup());
		}
		for (int x = 0; x < NUM_TABLEAU_DECKS; x++)
		{
			EOCard c = deck.pop().setFaceup();
			tableau[x].putFirst(c);
		}
		waste.push(deck.pop().setFaceup());

		// reset time
		time = 0;

		newGameButton.addActionListener(ngl);
		newGameButton.setBounds(0, TABLE_HEIGHT - 70, 120, 30);

		menuReturnButton.addActionListener(mrl);
		menuReturnButton.setBounds(785, TABLE_HEIGHT - 70, 120, 30);

		showRulesButton.addActionListener(srl);
		showRulesButton.setBounds(120, TABLE_HEIGHT - 70, 120, 30);

		scoreBox.setBounds(240, TABLE_HEIGHT - 70, 120, 30);
		scoreBox.setText("Score: 0");
		scoreBox.setEditable(false);
		scoreBox.setOpaque(false);

		timeBox.setBounds(360, TABLE_HEIGHT - 70, 120, 30);
		timeBox.setText("Seconds: 0");
		timeBox.setEditable(false);
		timeBox.setOpaque(false);

		startTimer();

		toggleTimerButton.setBounds(480, TABLE_HEIGHT - 70, 125, 30);
		toggleTimerButton.addActionListener(ttl);

		statusBox.setBounds(605, TABLE_HEIGHT - 70, 180, 30);
		statusBox.setEditable(false);
		statusBox.setOpaque(false);

		table.add(statusBox);
		table.add(toggleTimerButton);
		table.add(timeBox);
		table.add(newGameButton);
		table.add(showRulesButton);
		table.add(scoreBox);
		table.add(menuReturnButton);
		table.repaint();
	}

	// moves a card to abs location within a component
	protected static EOCard moveCard(EOCard c, int x, int y)
	{
		c.setBounds(new Rectangle(new Point(x, y), new Dimension(EOCard.CARD_WIDTH + 10, EOCard.CARD_HEIGHT + 10)));
		c.setXY(new Point(x, y));
		return c;
	}

	// add/subtract points based on gameplay actions
	protected static void setScore(int deltaScore)
	{
		EOSolitaire.score += deltaScore;
		String newScore = "Score: " + EOSolitaire.score;
		scoreBox.setText(newScore);
		scoreBox.repaint();
	}

	// GAME TIMER UTILITIES
	protected static void updateTimer()
	{
		EOSolitaire.time += 1;
		// every 10 seconds elapsed we take away 2 points
		if (EOSolitaire.time % 10 == 0)
		{
			setScore(-2);
		}
		String time = "Seconds: " + EOSolitaire.time;
		timeBox.setText(time);
		timeBox.repaint();
	}

	protected static void startTimer()
	{
		scoreClock = new ScoreClock();
		// set the timer to update every second
		timer = new Timer();
		timer.scheduleAtFixedRate(scoreClock, 1000, 1000);
		timeRunning = true;
	}

	// the pause timer button uses this
	protected static void toggleTimer()
	{
		if (timeRunning && scoreClock != null)
		{
			scoreClock.cancel();
			timeRunning = false;
		} else
		{
			startTimer();
		}
	}

	private static class ScoreClock extends TimerTask
	{
		@Override
		public void run()
		{
			updateTimer();
		}
	}

	// BUTTON LISTENERS
	private static class NewGameListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			GamePlatform.SaveEOScore(time, score, win);
			newGameButton.removeActionListener(ngl);
			showRulesButton.removeActionListener(srl);
			toggleTimerButton.removeActionListener(ttl);
			menuReturnButton.removeActionListener(mrl);
			timer.cancel();
			playNewGame();
		}
	}

	private static class MenuReturnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			GamePlatform.SaveEOScore(time, score, win);
			newGameButton.removeActionListener(ngl);
			showRulesButton.removeActionListener(srl);
			toggleTimerButton.removeActionListener(ttl);
			menuReturnButton.removeActionListener(mrl);
			table.removeMouseListener(cmm);
			frame.setVisible(false);
			frame.dispose();
			gpFrame.setVisible(true);
		}
	}

	private static class ShowRulesListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JDialog ruleFrame = new JDialog(frame, true);
			ruleFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ruleFrame.setSize(800, 600);
			JEditorPane rulesTextPane = new JEditorPane("text/html", "");
			rulesTextPane.setEditable(false);
			String rulesText = "<b>Even and Odd Solitaire - Rules</b>"
					+ "<br><br><b>Cards in play:</b> 1 deck (52 cards)."
					+ "<br><br><b>Difficulty:</b> EASY"
					+ "<br><br><b>Even and Odd Solitaire </b>uses one deck (52 cards). You have 9 tableau piles with one card in each pile and 3 reserve piles (with 6 cards in each pile). You also have 8 foundation piles."
					+ "<br><br><b>Objective</b>"
					+ "<br><ul><li>Build up the left four foundations in ascending sequence regardless of suit by twos starting with Ace (Ace,3,5,7,9,J,K) and</li>"
					+ "<li>Build up the right four foundations in ascending sequence regardless of suit by twos starting with 2 (2,4,6,8,10,Q)</li></ul>"
					+ "<b>Rules</b>"
					+ "<br>Each tableau pile may contain only one card. All cards in tableaus, top cards of reserve, stock and waste piles are available to play. Spaces in tableaus are filled from waste or stock piles. Empty reserve piles cannot be filled."
					+ "<br><br>When you have made all the moves initially available, begin turning over cards from the stock pile."
					+ "<br><br>There is no redeal.";
			rulesTextPane.setText(rulesText);
			ruleFrame.add(new JScrollPane(rulesTextPane));
			ruleFrame.setVisible(true);
		}
	}

	private static class ToggleTimerListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			toggleTimer();
			if (!timeRunning)
			{
				toggleTimerButton.setText("Start Timer");
			} else
			{
				toggleTimerButton.setText("Pause Timer");
			}
		}
	}

	/*
	 * This class handles all of the logic of moving the Card components as well
	 * as the game logic. This determines where Cards can be moved according to
	 * the rules of Even and Odd Solitaire
	 */
	private static class CardMovementManager extends MouseAdapter
	{
		private boolean checkForWin = false;// should we check if game is over?
		private boolean gameOver = true;// easier to negate this than affirm it
		private Point start = null;// where mouse was clicked
		private EOCard card = null; // card to be moved
		private EOCardStack source = null;
		private boolean sourceInTableau = false;
		private EOCardStack dest = null;

		private boolean validFinalStackMove(EOCard source, EOCard dest)
		{
			int s_val = source.getValue().ordinal();
			int d_val = dest.getValue().ordinal();
			if (s_val == (d_val + 2)) // destination must be two lower
				return true;
			else
				return false;
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			start = e.getPoint();
			boolean stopSearch = false;
			statusBox.setText("");

			for (int x = 0; x < (NUM_RESERVE_DECKS); x++)
			{
				if (stopSearch)
					break;
				source = reserve[x];
				// pinpointing exact card pressed
				for (Component ca : source.getComponents())
				{
					EOCard c = (EOCard) ca;
					if (c.contains(start) && source.contains(start) && c.getFaceStatus())
					{
						card = c;
						stopSearch = true;
						break;
					}
				}
			}
			if (card == null) 
			{
				for (int x = 0; x < (NUM_TABLEAU_DECKS); x++)
				{
					if (stopSearch)
						break;
					source = tableau[x];
					// pinpointing exact card pressed
					for (Component ca : source.getComponents())
					{
						EOCard c = (EOCard) ca;
						if (c.contains(start) && source.contains(start) && c.getFaceStatus())
						{
							card = c;
							stopSearch = true;
							sourceInTableau = true;
							break;
						}
					}
				}
			}
			if (card == null && waste.contains(start)) 
			{
				card = waste.pop();
			}

			// SHOW (WASTE) CARD OPERATIONS
			// display new show card
			if (newCardButton.contains(start) && deck.showSize() > 0)
			{
				EOCard c = deck.pop().setFaceup();
				waste.push(c);
				c.repaint();
				table.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// used for status bar updates
			boolean validMoveMade = false;
			
			// SHOW CARD MOVEMENTS
			if (waste.contains(start) && !newCardButton.contains(start))
			{
				// Moving from SHOW TO FINAL
				if (card.getValue().ordinal()%2==0) 
				{
					for (int x = 0; x < NUM_FINAL_DECKS; x++)
					{
						dest = foundationA[x];
						// only aces can go first
						if (dest.empty())
						{
							if (card.getValue() == EOCard.Value.ACE)
							{
								dest.push(card);
								dest.repaint();
								table.repaint();
								card = null;
								setScore(10);
								validMoveMade = true;
								break;
							}
						}
						else if (validFinalStackMove(card, dest.getLast()))
						{
							dest.push(card);
							dest.repaint();
							table.repaint();
							card = null;
							checkForWin = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				}
				else 
				{
					for (int x = 0; x < NUM_FINAL_DECKS; x++)
					{
						dest = foundationB[x];
						// only twos can go first
						if (dest.empty())
						{
							if (card.getValue() == EOCard.Value.TWO)
							{
								dest.push(card);
								dest.repaint();
								table.repaint();
								card = null;
								setScore(10);
								validMoveMade = true;
								break;
							}
						}
						else if (validFinalStackMove(card, dest.getLast()))
						{
							dest.push(card);
							dest.repaint();
							table.repaint();
							card = null;
							checkForWin = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				}
			}

			if (card != null && source != null && card.getFaceStatus()) 
			{
				if (card.getValue().ordinal()%2==0) 
				{
					for (int x = 0; x < NUM_FINAL_DECKS; x++)
					{
						dest = foundationA[x];

						// TO EMPTY STACK
						if (dest.empty())// empty final should only take an ACE
						{
							if (card.getValue() == EOCard.Value.ACE)
							{
								EOCard c = source.popFirst();
								c.repaint();
								if (source.getFirst() != null)
								{
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
						} 
						else if (validFinalStackMove(card, dest.getLast()))
						{
							EOCard c = source.popFirst();
							c.repaint();
							if (source.getFirst() != null)
							{
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
							checkForWin = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				}
				else
				{
					for (int x = 0; x < NUM_FINAL_DECKS; x++)
					{
						dest = foundationB[x];

						// TO EMPTY STACK
						if (dest.empty())// empty final should only take an ACE
						{
							if (card.getValue() == EOCard.Value.TWO)
							{
								EOCard c = source.popFirst();
								c.repaint();
								if (source.getFirst() != null)
								{
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
						} 
						else if (validFinalStackMove(card, dest.getLast()))
						{
							EOCard c = source.popFirst();
							c.repaint();
							if (source.getFirst() != null)
							{
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
							checkForWin = true;
							setScore(10);
							validMoveMade = true;
							break;
						}
					}
				}
				if (sourceInTableau && validMoveMade) 
				{
					if (!waste.empty()) {
						source.push(waste.pop().setFaceup());
					}
					else if (!deck.empty()) {
						source.push(deck.pop().setFaceup());
					}
				}
			}

			// SHOWING STATUS MESSAGE IF MOVE INVALID
			if (!validMoveMade && dest != null && card != null)
			{
				statusBox.setText("That Is Not A Valid Move");
			}

			// CHECKING FOR WIN
			if (checkForWin)
			{
				boolean gameNotOver = false;
				// cycle through final decks, if they're all full then game over
				for (int x = 0; x < NUM_FINAL_DECKS; x++)
				{
					dest = foundationA[x];
					if (dest.showSize() != 7)
					{
						// one deck is not full, so game is not over
						gameNotOver = true;
						break;
					}
				}
				for (int x = 0; x < NUM_FINAL_DECKS; x++)
				{
					dest = foundationB[x];
					if (dest.showSize() != 6)
					{
						// one deck is not full, so game is not over
						gameNotOver = true;
						break;
					}
				}
				if (!gameNotOver)
					gameOver = true;
			}

			if (checkForWin && gameOver)
			{
				scoreClock.cancel();
				timeRunning = false;
				JOptionPane.showMessageDialog(table, "Congratulations! You've Won!");
				statusBox.setText("Game Over!");
				win = true;
			}

			if (waste.empty() && !deck.empty()) 
			{
				waste.push(deck.pop().setFaceup());
			}

			// RESET VARIABLES FOR NEXT EVENT
			start = null;
			source = null;
			dest = null;
			card = null;
			checkForWin = false;
			gameOver = false;
			sourceInTableau = false;
		}
	}
}