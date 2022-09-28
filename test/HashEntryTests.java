import org.junit.*;
import static org.junit.Assert.*;


public class HashEntryTests {
    @Test
    public void testConstructor()
    {
        HashEntry empty = new HashEntry("", 0);
        HashEntry entry = new HashEntry("NotEmpty", 0);

        assertEquals("", empty.getKey());
        assertEquals("NotEmpty", entry.getKey());
        assertEquals(0, empty.getValue());

        empty.setValue(90);
        assertEquals(90, entry.getValue());
    }
}
