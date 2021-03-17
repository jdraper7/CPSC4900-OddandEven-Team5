import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.util.Date;

public class GamePlatform 
{
    // This will be the main menu of the entire game library.
    
    // It is likely possible to create this using the same logic that generates the GUI in the two solitaire.java games and the logic that creates the buttoms at the 
    // bottom of each game session.

    //*** Maybe use a global variable to track wins, losses, and scores.

    //Main GUI
    protected static final JFrame gpFrame = new JFrame("Card Game Platform - Team 5");
    protected static final JPanel table = new JPanel();
    protected static final JPanel panel = new JPanel();
    protected static JTable j;
    protected static JScrollPane sp;

    //Buttons
    private static JButton eoStatsButton = new JButton("Even and Odd Stats");
    private static JButton kStatsButton = new JButton("Klondike Stats");
    private static JButton ewStatsButton = new JButton("Eagle Wing Stats");
    private static JButton estatsButton = new JButton("Easthaven Stats");
    private static JButton eoffStatsButton = new JButton("Eight Off Stats");
    private static JButton exStatsButton = new JButton("Exit Stats");
    private static JButton eo_GameButton = new JButton("Play Even and Odd");
    private static JButton k_GameButton = new JButton("Play Klondike");
    private static JButton ew_GameButton = new JButton("Play Eagle Wing");
    private static JButton e_GameButton = new JButton("Play Easthaven");
    private static JButton eoff_GameButton = new JButton("Play Eight Off");
    private static JButton ex_GameButton = new JButton("Play Exit");

    private static final int TABLE_HEIGHT = EOCard.CARD_HEIGHT * 4+150;
	private static final int TABLE_WIDTH = (EOCard.CARD_WIDTH * 12) + 140;

    private static Object[][] eoStats;
    private static final String[] columnNames = { "Win/Loss", "Score", "Game Time", "Date" }; 
    private static ArrayList<String[]> runningStats = new ArrayList<>();

    public static void main(String[] args)
	{
        read();
		Container contentPane;
		gpFrame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		table.setLayout(null);
		table.setBackground(new Color(0, 180, 0));
        panel.add(table);
		contentPane = gpFrame.getContentPane();
		contentPane.add(panel);
		gpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpFrame.setVisible(true);
        eo_GameButton.addActionListener(new ButtonListener());
		eo_GameButton.setBounds(40, 25, 200, 100);
        k_GameButton.addActionListener(new ButtonListener());
		k_GameButton.setBounds(250, 25, 200, 100);
        ew_GameButton.addActionListener(new ButtonListener());
		ew_GameButton.setBounds(460, 25, 200, 100);
        e_GameButton.addActionListener(new ButtonListener());
		e_GameButton.setBounds(670, 25, 200, 100);
        eoff_GameButton.addActionListener(new ButtonListener());
		eoff_GameButton.setBounds(880, 25, 200, 100);
        ex_GameButton.addActionListener(new ButtonListener());
		ex_GameButton.setBounds(1090, 25, 200, 100);
        eoStatsButton.addActionListener(new ButtonListener());
		eoStatsButton.setBounds(40, 126, 200, 30);
        kStatsButton.addActionListener(new ButtonListener());
		kStatsButton.setBounds(250, 126, 200, 30);
        ewStatsButton.addActionListener(new ButtonListener());
		ewStatsButton.setBounds(460, 126, 200, 30);
        estatsButton.addActionListener(new ButtonListener());
		estatsButton.setBounds(670, 126, 200, 30);
        eoffStatsButton.addActionListener(new ButtonListener());
		eoffStatsButton.setBounds(880, 126, 200, 30);
        exStatsButton.addActionListener(new ButtonListener());
		exStatsButton.setBounds(1090, 126, 200, 30);
		table.add(eo_GameButton);
		table.add(k_GameButton);
		table.add(ew_GameButton);
		table.add(e_GameButton);
		table.add(eoff_GameButton);
        table.add(ex_GameButton);
        table.add(eoStatsButton);
        table.add(kStatsButton);
        table.add(kStatsButton);
        table.add(ewStatsButton);
        table.add(estatsButton);
        table.add(eoffStatsButton);
        table.add(exStatsButton);
        table.repaint();
	}

    private static class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
            JButton b = (JButton) e.getSource();
			switch (b.getText()) {
                case "Play Even and Odd": new EOSolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Play Klondike": 
                break;
                case "Play Eagle Wing": 
                break;
                case "Play Easthaven": 
                break;
                case "Play Eight Off": 
                break;
                case "Play Exit": 
                break;
                case "Even and Odd Stats":
                if (panel.getComponents().length > 1) {
                    panel.remove(1);
                    panel.repaint();
                    panel.revalidate();
                }
                else {
                    int i=0;
                    eoStats = new Object[runningStats.size()][];
                    for (String[] str : runningStats) {
                        eoStats[i++] = str;
                    } 
                    j = new JTable(eoStats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                    panel.repaint();
                    panel.revalidate();
                }
                break;
                case "Klondike Stats": 
                break;
                case "Eagle Wing Stats": 
                break;
                case "Easthaven Stats": 
                break;
                case "Eight Off Stats": 
                break;
                case "Exit Stats": 
                break;
            }
		}
	}

    public static void SaveEOScore(int time, int score, boolean win) { 
        runningStats.add(new String[]{win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    private static void read() {
        try {
            Scanner scanner = new Scanner(new File("stats.txt"));
            while (scanner.hasNextLine()) {
                String[] arr = scanner.nextLine().split(":", 4);
                runningStats.add(new String[]{arr[0],arr[1],arr[2],arr[3]});
            }
        }
        catch (Exception e) {
            System.out.println("Failed to read\n" + e);
        }
    }

    private static void write() {
        try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter("stats.txt"))) {
            for (String[] str : runningStats) {
                outputWriter.write(str[0]+":"+str[1]+":"+str[2]+":"+str[3]);
                outputWriter.newLine();
            }
            outputWriter.flush();
        }
        catch (Exception e) {
            System.out.println("Failed to write\n" + e);
        }
    }
}