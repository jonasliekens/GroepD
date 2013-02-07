import be.kdg.Utilities.Utilities;
import org.junit.Assert;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:03
 * Copyright @ Soulware.be
 */
public class UtilitiesTest {
    @Test
    public void testPasswordHash(){
        String hash = Utilities.encryptPassword("hahayoucantreadthis123456");
        assertEquals(hash, "986a78574b78edb9c480835b582991d1c561caf7262794132950ca3edd3bcc5a");
    }
}
