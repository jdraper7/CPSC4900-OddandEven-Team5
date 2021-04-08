import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class EOSolitaireTest {
    @Test
    void moveCard() {
        int x = 0;
        int y = 1;
        EOCard c = new EOCard();
        c.setBounds(new Rectangle(new Point(x, y), new Dimension(EOCard.CARD_WIDTH + 10, EOCard.CARD_HEIGHT + 10)));
        c.setXY(new Point(x, y));
        assertEquals(x, c.getX());
        assertEquals(y, c.getY());
    }

//    @Test
//    void setScore() {
//        JFrame gpFrame = new JFrame("Even and Odd Solitaire");;
//        EOSolitaire test = new EOSolitaire(0, 0 , gpFrame);
//        test.setScore(10);
//        assertEquals(10, test.score);
//    }
//
//    @Test
//    void updateTimer() {
//        // initialize
//        JFrame gpFrame = new JFrame("Even and Odd Solitaire");;
//        EOSolitaire test = new EOSolitaire(0, 0 , gpFrame);
//        test.updateTimer();
//        assertEquals(1, test.time);
//    }
//
//
//    @Test
//    void startTimer() {
//        JFrame gpFrame = new JFrame("Even and Odd Solitaire");;
//        EOSolitaire test = new EOSolitaire(0, 0 , gpFrame);
//        test.startTimer();
//        assertSame(true, test.timeRunning);
//
//    }
//
//    @Test
//    void toggleTimer() {
//        JFrame gpFrame = new JFrame("Even and Odd Solitaire");;
//        EOSolitaire test = new EOSolitaire(0, 0 , gpFrame);
//        assertSame(true, test.timeRunning);
//        test.toggleTimer();
//        assertSame(false, test.timeRunning);
//        test.toggleTimer();
//        assertSame(true, test.timeRunning);
//    }
}