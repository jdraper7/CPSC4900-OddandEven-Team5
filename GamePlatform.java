import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.util.Date;

public class GamePlatform 
{

    //Main GUI
    protected static final JFrame gpFrame = new JFrame("Card Game Platform - Team 5");
//    protected static final JPanel table = new JPanel();
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

    private static Object[][] stats;
    private static final String[] columnNames = { "Win/Loss", "Score", "Game Time in Seconds", "Date" }; 
    private static ArrayList<String[]> runningEOStats = new ArrayList<>();
    private static ArrayList<String[]> runningKStats = new ArrayList<>();
    private static ArrayList<String[]> runningEWStats = new ArrayList<>();
    private static ArrayList<String[]> runningEStats = new ArrayList<>();
    private static ArrayList<String[]> runningEOffStats = new ArrayList<>();
    private static ArrayList<String[]> runningExStats = new ArrayList<>();

    public static void main(String[] args)
	{
        ImagePanel table = new ImagePanel(
                new ImageIcon("assets/images/background_platform.jpg").getImage());

        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);


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

    static class ImagePanel extends JPanel {

        private Image img;

        public ImagePanel(String img) {
            this(new ImageIcon(img).getImage());
        }

        public ImagePanel(Image img) {
            this.img = img;
            Dimension size = new Dimension(TABLE_WIDTH, TABLE_HEIGHT);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

    private static class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
            if (panel.getComponents().length > 1) {panel.remove(1);}
            int i=0;
            JButton b = (JButton) e.getSource();
			switch (b.getText()) {
                case "Play Even and Odd": new EOSolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Play Klondike": new KSolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Play Eagle Wing": //new EWSolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Play Easthaven": //new ESolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Play Eight Off": //new EOffSolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Play Exit": //new ExSolitaire(TABLE_WIDTH, TABLE_HEIGHT, gpFrame);
                break;
                case "Even and Odd Stats": 
                    i=0;
                    stats = new Object[runningEOStats.size()][4];
                    for (String[] str : runningEOStats) {
                        stats[i++] = Arrays.copyOfRange(str, 1, str.length);}
                    j = new JTable(stats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                break;
                case "Klondike Stats":
                    i=0;
                    stats = new Object[runningKStats.size()][4];
                    for (String[] str : runningKStats) {
                        stats[i++] = Arrays.copyOfRange(str, 1, str.length);} 
                    j = new JTable(stats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                break;
                case "Eagle Wing Stats": 
                    i=0;
                    stats = new Object[runningEWStats.size()][4];
                    for (String[] str : runningEWStats) {
                        stats[i++] = Arrays.copyOfRange(str, 1, str.length);} 
                    j = new JTable(stats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                break;
                case "Easthaven Stats":
                    i=0;
                    stats = new Object[runningEStats.size()][4];
                    for (String[] str : runningEStats) {
                        stats[i++] = Arrays.copyOfRange(str, 1, str.length);} 
                    j = new JTable(stats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                break;
                case "Eight Off Stats": 
                    i=0;
                    stats = new Object[runningEOffStats.size()][4];
                    for (String[] str : runningEOffStats) {
                        stats[i++] = Arrays.copyOfRange(str, 1, str.length);} 
                    j = new JTable(stats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                break;
                case "Exit Stats": 
                    i=0;
                    stats = new Object[runningExStats.size()][4];
                    for (String[] str : runningExStats) {
                        stats[i++] = Arrays.copyOfRange(str, 1, str.length);} 
                    j = new JTable(stats, columnNames);
                    sp = new JScrollPane(j);
                    sp.setMaximumSize((new Dimension(TABLE_WIDTH, 0)));
                    panel.add(sp);
                break;
            }
            panel.repaint();
            panel.revalidate();
		}
	}

    public static void SaveEOScore(int time, int score, boolean win) { 
        runningEOStats.add(new String[]{"EO",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    public static void SaveKScore(int time, int score, boolean win) { 
        runningKStats.add(new String[]{"K",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    public static void SaveEWScore(int time, int score, boolean win) { 
        runningEWStats.add(new String[]{"EW",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    public static void SaveEScore(int time, int score, boolean win) { 
        runningEStats.add(new String[]{"E",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    public static void SaveEOffScore(int time, int score, boolean win) { 
        runningEOffStats.add(new String[]{"EOff",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    public static void SaveExScore(int time, int score, boolean win) { 
        runningExStats.add(new String[]{"Ex",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        write();
    }

    private static void read() {
        try {
            Scanner scanner = new Scanner(new File("stats.txt"));
            while (scanner.hasNextLine()) {
                String[] arr = scanner.nextLine().split(":", 5);
                switch (arr[0]) {
                    case "EO": runningEOStats.add(arr);
                    break;
                    case "K": runningKStats.add(arr);
                    break;
                    case "EW": runningEWStats.add(arr);
                    break;
                    case "E": runningEStats.add(arr);
                    break;
                    case "EOff": runningEOffStats.add(arr);
                    break;
                    case "Ex": runningExStats.add(arr);
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Failed to read\n" + e);
        }
    }

    private static void write() {
        try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter("stats.txt"))) {
            for (String[] str : runningEOStats) {
                outputWriter.write(String.join(":",str));
                outputWriter.newLine();
            }
            for (String[] str : runningKStats) {
                outputWriter.write(String.join(":",str));
                outputWriter.newLine();
            }
            for (String[] str : runningEWStats) {
                outputWriter.write(String.join(":",str));
                outputWriter.newLine();
            }
            for (String[] str : runningEStats) {
                outputWriter.write(String.join(":",str));
                outputWriter.newLine();
            }
            for (String[] str : runningEOffStats) {
                outputWriter.write(String.join(":",str));
                outputWriter.newLine();
            }
            for (String[] str : runningExStats) {
                outputWriter.write(String.join(":",str));
                outputWriter.newLine();
            }
            outputWriter.flush();
        }
        catch (Exception e) {
            System.out.println("Failed to write\n" + e);
        }
    }
}