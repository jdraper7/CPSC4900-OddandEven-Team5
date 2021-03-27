import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EOFinalStackTest {
    @Test
    void setXY() {
        EOFinalStack fs = new EOFinalStack();
		fs.setXY(0,1);
		assertEquals(0,fs._x);
		assertEquals(1,fs._y);
    }

    @Test
    void contains() {
        EOFinalStack fs = new EOFinalStack();
		Point p = new Point();
		assertTrue(fs.contains(p));
    }

    @Test
    void paintComponent() {
        EOFinalStack fs = new EOFinalStack();
		Graphics g = fs.getGraphics();
		fs.paintComponent(g);
		assertEquals(1,1);
    }
}