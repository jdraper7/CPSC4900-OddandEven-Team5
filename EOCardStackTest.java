import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.Point;
import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

class EOCardStackTest {

    @Test
    void empty() {

        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            assertEquals(!testValue, cardStackTest.empty());

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);

            assertEquals(false, cardStackTest.empty());

            cardStackTest.makeEmpty();

            assertEquals(true, cardStackTest.empty());

        }
    }

    @Test
    void putFirst() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();

            cardStackTest.putFirst(addcard);
            cardStackTest.putFirst(addcard2);

            assertSame(addcard2, cardStackTest.getFirst());
        }
    }

    @Test
    void getFirst() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);
            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();

            cardStackTest.putFirst(addcard);
            cardStackTest.putFirst(addcard2);

            assertSame(addcard2, cardStackTest.getFirst());
        }
    }

    @Test
    void getLast() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);

            assertSame(addcard2, cardStackTest.getLast());

            cardStackTest.makeEmpty();

            assertNull(cardStackTest.getLast());
        }
    }

    @Test
    void popFirst() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            cardStackTest.makeEmpty();

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);

            assertSame(addcard, cardStackTest.popFirst());

            cardStackTest.makeEmpty();

            assertNull(cardStackTest.popFirst());
        }
    }

    @Test
    void push() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();
            EOCard addcard3 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);
            cardStackTest.push(addcard3);

            assertFalse(cardStackTest.empty());

            cardStackTest.makeEmpty();

            assertTrue(cardStackTest.empty());
        }
    }

    @Test
    void pop() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);

            assertSame(addcard2, cardStackTest.pop());

            cardStackTest.makeEmpty();

            assertNull(cardStackTest.pop());
        }
    }

    @Test
    void shuffle() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            cardStackTest.makeEmpty();

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();
            EOCard addcard3 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);
            cardStackTest.push(addcard3);

            assertSame(addcard, cardStackTest.getFirst());

            EOCardStack secondDeck = new EOCardStack(testValue);

            cardStackTest.shuffle();

            assertNotSame(secondDeck.getFirst(), cardStackTest.getFirst());
        }
    }

    @Test
    void showSize() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            cardStackTest.makeEmpty();

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();
            EOCard addcard3 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);
            cardStackTest.push(addcard3);

            assertEquals(3, cardStackTest.showSize());
        }
    }

    @Test
    void reverse() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            EOCard addcard = new EOCard();
            EOCard addcard2 = new EOCard();
            EOCard addcard3 = new EOCard();

            cardStackTest.push(addcard);
            cardStackTest.push(addcard2);
            cardStackTest.push(addcard3);

            cardStackTest.reverse();

            assertSame(addcard3, cardStackTest.getFirst());
        }
    }

    @Test
    void makeEmpty() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            EOCard addcard = new EOCard();

            cardStackTest.push(addcard);

            cardStackTest.makeEmpty();

            assertTrue(cardStackTest.empty());
        }
    }

    @Test
    void contains() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            Point containsTest = new Point();

            assertTrue(cardStackTest.contains(containsTest));
        }
    }

    @Test
    void setXY() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            cardStackTest.setXY(8, 16);

            assertEquals(8, cardStackTest._x);
            assertEquals(16, cardStackTest._y);
        }
    }

    @Test
    void getXY() {
        boolean[] testValues = new boolean[2];
        testValues[0] = false;
        testValues[1] = true;

        for (boolean testValue : testValues) {

            EOCardStack cardStackTest = new EOCardStack(testValue);

            cardStackTest.setXY(8, 16);

            Point getXYTest = new Point(8, 16);

            assertSame(getXYTest.x, cardStackTest.getXY().x);
            assertSame(getXYTest.y, cardStackTest.getXY().y);
        }
    }

//    @Test
//    void paintComponent() {
//    }
}