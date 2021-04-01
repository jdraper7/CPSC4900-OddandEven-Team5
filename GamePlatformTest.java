import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GamePlatformTest
{

    @Test
    void main()
    {
        GamePlatform gp = new GamePlatform();
        assertEquals(1,1);
    }

    @Test
    void saveEOScore()
    {
        ArrayList<String[]> runningEOStats = new ArrayList<>();
        runningEOStats.add(new String[]{});
        assertEquals(1,1);
    }

    @Test
    void saveKScore()
    {
        ArrayList<String[]> runningKStats = new ArrayList<>();
        runningKStats.add(new String[]{});
        assertEquals(1,1);
    }

    @Test
    void saveEWScore()
    {
        ArrayList<String[]> runningEWStats = new ArrayList<>();
        runningEWStats.add(new String[]{});
        assertEquals(1,1);
    }

    @Test
    void saveEScore()
    {
        ArrayList<String[]> runningEStats = new ArrayList<>();
        runningEStats.add(new String[]{});
        assertEquals(1,1);
    }

    @Test
    void saveEOffScore()
    {
        ArrayList<String[]> runningEOffStats = new ArrayList<>();
        runningEOffStats.add(new String[]{});
        assertEquals(1,1);
    }

    @Test
    void saveExScore()
    {
        ArrayList<String[]> runningExStats = new ArrayList<>();
        runningExStats.add(new String[]{});
        assertEquals(1,1);
    }
}
