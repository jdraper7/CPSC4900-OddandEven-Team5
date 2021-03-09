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

public class GamePlatform 
{
    // This will be the main menu of the entire game library.
    
    // It is likely possible to create this using the same logic that generates the GUI in the two solitaire.java games and the logic that creates the buttoms at the 
    // bottom of each game session.

    //Main GUI
    private static final JFrame frame = new JFrame("Card Game Platform - Team 5");
    protected static final JPanel table = new JPanel();
    //other components
    private static JButton showRulesButton = new JButton("Show Rules");
    private static JButton showStatsButton = new JButton("My Stats");
    private static JButton eo_GameButton = new JButton("Play Even and Odd");
    private static JButton k_GameButton = new JButton("Play Klondike");
    private static JButton ew_GameButton = new JButton("Play Eagle Wing");
    private static JButton e_GameButton = new JButton("Play Easthaven");
    private static JButton eightoff_GameButton = new JButton("Play Eight Off");
    private static JButton ex_GameButton = new JButton("Play Exit");

}
