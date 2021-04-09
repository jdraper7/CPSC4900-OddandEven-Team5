import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.util.Date;

public class GamePlatform {
    //Main GUI
    protected static final JFrame frame = new JFrame("Card Game Platform - Team 5");
    protected static final ImagePanel game_menu = new ImagePanel( new ImageIcon("assets/images/backgrounds/platform.jpg").getImage());
    protected static final JPanel gui = new JPanel();
    protected static JTable statistics_table;
    protected static JScrollPane statistics_table_scroll_panel;

    private static final int game_menu_height = EOCard.CARD_HEIGHT * 4+150;
    private static final int game_menu_width = (EOCard.CARD_WIDTH * 12) + 140;

    private static final String[] statistics_table_column_names = { "Win/Loss", "Score", "Game Time in Seconds", "Date" };
    private static final ArrayList<String[]> runningEOStats = new ArrayList<>();
    private static final ArrayList<String[]> runningKStats = new ArrayList<>();
    private static final ArrayList<String[]> runningEWStats = new ArrayList<>();
    private static final ArrayList<String[]> runningEStats = new ArrayList<>();
    private static final ArrayList<String[]> runningEOffStats = new ArrayList<>();
    private static final ArrayList<String[]> runningExStats = new ArrayList<>();

    public static void main(String[] args) {
        read();
        // Layout
        game_menu.setLayout(null);
        gui.setLayout(new BoxLayout(gui, BoxLayout.Y_AXIS));
        gui.add(game_menu);
        frame.setSize(game_menu_width, game_menu_height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container contentPane = frame.getContentPane();;
        contentPane.add(gui);

        // Menu Dimensions Logic.
        Dimension size = frame.getBounds().getSize();
        int center = (int) (size.getWidth()/2);
        int width_column = 200;
        int height_launcher = 100;
        int height_stats = 30;
        int gap_launcher_stats = 10;
        int gap_row = 30;
        int gap_column = 100;
        int x_column_1 = center-((width_column)/2)-gap_column-width_column;
        int x_column_2 = center-((width_column)/2);
        int x_column_3 = center+((width_column)/2)+gap_column;
        int y_row_launcher_1 = (int) (size.getHeight()/5);
        int y_row_stats_1 = y_row_launcher_1+height_launcher+gap_launcher_stats;
        int y_row_launcher_2 = y_row_stats_1+height_stats+gap_row;
        int y_row_stats_2 = y_row_launcher_2+height_launcher+gap_launcher_stats;

        // Buttons
        // - Even and Odd
        String game_title_1 = "Even & Odd";
        LauncherButton launcher_EvenAndOdd = new LauncherButton(game_title_1);
        launcher_EvenAndOdd.setBounds(x_column_1, y_row_launcher_1, width_column, height_launcher);
        game_menu.add(launcher_EvenAndOdd);
        StatisticsButton statistics_EvenAndOdd = new StatisticsButton(game_title_1);
        statistics_EvenAndOdd.setBounds(x_column_1, y_row_stats_1, width_column, height_stats);
        game_menu.add(statistics_EvenAndOdd);
        // - Klondike
        String game_title_2 = "Klondike";
        LauncherButton launcher_Klondike = new LauncherButton(game_title_2);
        launcher_Klondike.setBounds(x_column_2, y_row_launcher_1, width_column, height_launcher);
        game_menu.add(launcher_Klondike);
        StatisticsButton statistics_Klondike = new StatisticsButton(game_title_2);
        statistics_Klondike.setBounds(x_column_2, y_row_stats_1, width_column, height_stats);
        game_menu.add(statistics_Klondike);
        // - EagleWing
        String game_title_3 = "Eagle Wing";
        LauncherButton launcher_EagleWing = new LauncherButton(game_title_3);
        launcher_EagleWing.setBounds(x_column_3, y_row_launcher_1, width_column, height_launcher);
        game_menu.add(launcher_EagleWing);
        StatisticsButton statistics_EagleWing = new StatisticsButton(game_title_3);
        statistics_EagleWing.setBounds(x_column_3, y_row_stats_1, width_column, height_stats);
        game_menu.add(statistics_EagleWing);
        // - Easthaven
        String game_title_4 = "Easthaven";
        LauncherButton launcher_Easthaven = new LauncherButton(game_title_4);
        launcher_Easthaven.setBounds(x_column_1, y_row_launcher_2, width_column, height_launcher);
        game_menu.add(launcher_Easthaven);
        StatisticsButton statistics_Easthaven = new StatisticsButton(game_title_4);
        statistics_Easthaven.setBounds(x_column_1, y_row_stats_2, width_column, height_stats);
        game_menu.add(statistics_Easthaven);
        // - EightOff
        String game_title_5 = "Eight Off";
        LauncherButton launcher_EightOff = new LauncherButton(game_title_5);
        launcher_EightOff.setBounds(x_column_2, y_row_launcher_2, width_column, height_launcher);
        game_menu.add(launcher_EightOff);
        StatisticsButton statistics_EightOff = new StatisticsButton(game_title_5);
        statistics_EightOff.setBounds(x_column_2, y_row_stats_2, width_column, height_stats);
        game_menu.add(statistics_EightOff);
        // - Exit
        String game_title_6 = "Exit";
        LauncherButton launcher_Exit = new LauncherButton(game_title_6);
        launcher_Exit.setBounds(x_column_3, y_row_launcher_2, width_column, height_launcher);
        game_menu.add(launcher_Exit);
        StatisticsButton statistics_Exit = new StatisticsButton(game_title_6);
        statistics_Exit.setBounds(x_column_3, y_row_stats_2, width_column, height_stats);
        game_menu.add(statistics_Exit);

        gui.repaint();
        game_menu.repaint();

        frame.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Menu Dimensions Logic.
                Component frame_data = (Component)e.getSource();
                int center = (int) (frame_data.getWidth()/2);
                int width_column = 200;
                int height_launcher = 100;
                int height_stats = 30;
                int gap_launcher_stats = 8;
                int gap_row = 30;
                int gap_column = 100;
                int x_column_1 = center-((width_column)/2)-gap_column-width_column;
                int x_column_2 = center-((width_column)/2);
                int x_column_3 = center+((width_column)/2)+gap_column;
                int y_row_launcher_1 = (int) (frame_data.getHeight()/5);
                int y_row_stats_1 = y_row_launcher_1+height_launcher+gap_launcher_stats;
                int y_row_launcher_2 = y_row_stats_1+height_stats+gap_row;
                int y_row_stats_2 = y_row_launcher_2+height_launcher+gap_launcher_stats;

                // Update Buttons
                // - Even and Odd
                launcher_EvenAndOdd.setBounds(x_column_1, y_row_launcher_1, width_column, height_launcher);
                statistics_EvenAndOdd.setBounds(x_column_1, y_row_stats_1, width_column, height_stats);
                // - Klondike
                launcher_Klondike.setBounds(x_column_2, y_row_launcher_1, width_column, height_launcher);
                statistics_Klondike.setBounds(x_column_2, y_row_stats_1, width_column, height_stats);
                // - EagleWing
                launcher_EagleWing.setBounds(x_column_3, y_row_launcher_1, width_column, height_launcher);
                statistics_EagleWing.setBounds(x_column_3, y_row_stats_1, width_column, height_stats);
                // - Easthaven
                launcher_Easthaven.setBounds(x_column_1, y_row_launcher_2, width_column, height_launcher);
                statistics_Easthaven.setBounds(x_column_1, y_row_stats_2, width_column, height_stats);
                // - EightOff
                launcher_EightOff.setBounds(x_column_2, y_row_launcher_2, width_column, height_launcher);
                statistics_EightOff.setBounds(x_column_2, y_row_stats_2, width_column, height_stats);
                // - Exit
                launcher_Exit.setBounds(x_column_3, y_row_launcher_2, width_column, height_launcher);
                statistics_Exit.setBounds(x_column_3, y_row_stats_2, width_column, height_stats);
            }
        });
    }
    
    static class ImagePanel extends JPanel {
        private Image img;

        public ImagePanel(Image img) {
            this.img = img;
            Dimension size = new Dimension(1080, 2200);
            setPreferredSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

    public static class LauncherButton extends JButton implements MouseListener {
        String title;
        String text;
        String text_hover;
        Dimension size = new Dimension(300, 100);
        boolean hover = false;
        boolean click = false;


        public LauncherButton(String text) {
            this.title = text;
            this.text_hover = "Play " + text;
            this.text = text;
            setText(text);
            setFont(new Font("Dialog", Font.BOLD, 20));
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
        public Dimension getPreferredSize() {
            return size;
        }

        @Override
        public Dimension getMaximumSize() {
            return size;
        }

        @Override
        public Dimension getMinimumSize() {
            return size;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hover = true;
            setText(text_hover);
            setFont(new Font("Dialog", Font.BOLD, 22));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hover = false;
            setText(text);
            setFont(new Font("Dialog", Font.BOLD, 20));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            click = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            click = false;
            launch();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            launch();
        }
        public void launch() {
            if (gui.getComponents().length > 1) {gui.remove(1);}
            switch (title) {
                case "Even & Odd": new EOSolitaire(game_menu_width, game_menu_height, frame);
                    break;
                case "Klondike": new KSolitaire(game_menu_width, game_menu_height, frame);
                    break;
                case "Eagle Wing": //new EWSolitaire(game_menu_width, game_menu_height, frame);
                    break;
                case "Easthaven": //new ESolitaire(game_menu_width, game_menu_height, frame);
                    break;
                case "Eight Off": //new EOffSolitaire(game_menu_width, game_menu_height, frame);
                    break;
                case "Exit": //new ExSolitaire(game_menu_width, game_menu_height, frame);
                    break;
            }
            gui.repaint();
            gui.revalidate();
        }
    }

    public static class StatisticsButton extends JButton implements MouseListener {
        String title;
        String text;
        String text_hover;
        Dimension size = new Dimension(300, 100);
        boolean hover = false;
        boolean click = false;

        public StatisticsButton(String text) {
            this.title = text;
            this.text = text+" statistics";
            this.text_hover = "View statistics";
            setText(text);
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
        public Dimension getPreferredSize() {
            return size;
        }

        @Override
        public Dimension getMaximumSize() {
            return size;
        }

        @Override
        public Dimension getMinimumSize() {
            return size;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hover = true;
            setText(text_hover);
            setFont(new Font("Dialog", Font.BOLD, 14));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hover = false;
            setText(text);
            setFont(new Font("Dialog", Font.BOLD, 12));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            click = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            click = false;
            showStatisticsTable();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            showStatisticsTable();
        }
        public void showStatisticsTable() {
            if (gui.getComponents().length > 1) {gui.remove(1);}
            int i=0;
            switch (title) {
                case "Even & Odd" -> getStatisticsTable(runningEOStats);
                case "Klondike" -> getStatisticsTable(runningKStats);
                case "Eagle Wing" -> getStatisticsTable(runningEWStats);
                case "Easthaven" -> getStatisticsTable(runningEStats);
                case "Eight Off" -> getStatisticsTable(runningEOffStats);
                case "Exit" -> getStatisticsTable(runningExStats);
            }
            gui.repaint();
            gui.revalidate();
        }

        public void getStatisticsTable(ArrayList<String[]> data) {
            int i=0;
            Object[][] statistics_data = new Object[data.size()][4];
            for (String[] str : data) {
                statistics_data[i++] = Arrays.copyOfRange(str, 1, str.length);
            }
            statistics_table = new JTable(statistics_data, statistics_table_column_names);
            statistics_table_scroll_panel = new JScrollPane(statistics_table);
            statistics_table_scroll_panel.setMaximumSize((new Dimension(game_menu_width, 0)));
            gui.add(statistics_table_scroll_panel);
        }
    }

    public static void saveScore(int time, int score, boolean win, String title) {
        switch(title) {
            case "Even & Odd" -> runningEOStats.add(new String[]{"EO",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
            case "Klondike" -> runningKStats.add(new String[]{"K",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
            case "Eagle Wing" -> runningEWStats.add(new String[]{"EW",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
            case "Easthaven" -> runningEStats.add(new String[]{"E",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
            case "Eight Off" -> runningEOffStats.add(new String[]{"EOff",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
            case "Exit" -> runningExStats.add(new String[]{"Ex",win?"Win":"Loss", Integer.toString(score), Integer.toString(time), new Date().toString()});
        }
        write();
    }

    private static void read() {
        try {
            Scanner scanner = new Scanner(new File("stats.txt"));
            while (scanner.hasNextLine()) {
                String[] arr = scanner.nextLine().split(":", 5);
                switch (arr[0]) {
                    case "EO" -> runningEOStats.add(arr);
                    case "K" -> runningKStats.add(arr);
                    case "EW" -> runningEWStats.add(arr);
                    case "E" -> runningEStats.add(arr);
                    case "EOff" -> runningEOffStats.add(arr);
                    case "Ex" -> runningExStats.add(arr);
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