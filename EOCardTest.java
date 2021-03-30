import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class EOCardTest {
    @BeforeAll
    static void setUp(){
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
    }



    @org.junit.jupiter.api.Test


    /*
     * States in Main EO Card class that method is never used
     */
    void getSuit() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        assertEquals(card.getSuit(), EOCard.Suit.HEARTS);
    }

    @Test
    /*
     *
     */
    void getValue() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        assertEquals(card.getValue(), EOCard.Value.ACE );
    }

    @Test
    /*
     *
     */
    void setWhereAmI() {
        Point p = new Point(10, 15);
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        card.setWhereAmI(p);
        assertEquals(card.getWhereAmI(), p);
    }

    @Test
    /*
     *
     */
    void getWhereAmI() {
        Point p = new Point(10, 15);
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        card.setWhereAmI(p);
        assertEquals(card.getWhereAmI(), p);
    }

    @Test
    /*
     *
     */
    void getXY() {
//        Point m = new Point(10, 15);
//        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
//        card.setWhereAmI(m);
//        assertEquals(m.get, m);
    }

    @Test
    /*
     *
     */
    void getFaceStatus() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        assertFalse(card.getFaceStatus());
    }

    @Test
    /*
     *
     */
    void setXY() {
//        Point m = new Point(10,15);
//        Point P = new Point(20, 25);
//        setXY(m)
    }

    @Test
    /*
     *
     */
    void setSuit() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        card.setSuit(EOCard.Suit.CLUBS);
        assertSame(card.getSuit(), EOCard.Suit.CLUBS);
    }

    @Test
    /*
     *
     */
    void setValue() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        card.setValue(EOCard.Value.TWO);
        assertEquals(card.getValue(), EOCard.Value.TWO);
    }

    @Test
    /*
     *
     */
    void setFaceup() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        card.setFacedown();
        card.setFaceup();
        assertTrue(card.getFaceStatus());
    }

    @Test
    /*
     *
     */
    void setFacedown() {
        EOCard card = new EOCard(EOCard.Suit.HEARTS, EOCard.Value.ACE);
        card.setFacedown();
        assertFalse(card.getFaceStatus());
    }

    @Test
    /*
     * Unable to complete testing, will test via practical method
     */
    void contains() {

        
    }

    @Test
    /*
     * Unable to complete testing, will test via practical method
     */
    void paintComponent() {
    }

}