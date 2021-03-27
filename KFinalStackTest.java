
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KFinalStackTest 
{
    @Test
    void setXY() {
        KFinalStack fs = new KFinalStack();
		fs.setXY(0,1);
		assertEquals(0,fs._x);
		assertEquals(1,fs._y);
    }

    @Test
    void contains() {
        KFinalStack fs = new KFinalStack();
		Point p = new Point();
		assertTrue(fs.contains(p));
    }

    @Test
    void paintComponent() {
        KFinalStack fs = new KFinalStack();
		Graphics g = fs.getGraphics();
		fs.paintComponent(g);
		assertEquals(1,1);
    }
}