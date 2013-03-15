package utilities;

import be.kdg.backend.utilities.Utilities;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
    public void testPasswordHash() {
        String hash = null;

        try {
            hash = Utilities.getEncryptPassword("hahayoucantreadthis123456");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertEquals(hash, "986a78574b78edb9c480835b582991d1c561caf7262794132950ca3edd3bcc5a");
    }

    @Test
    public void testRandomPassword(){
        String pass1 = Utilities.newPass(8);
        String pass2 = Utilities.newPass(8);

        Assert.assertNotEquals(pass1, pass2);
    }

    @Test
    public void testDateMakerCorrect(){
        Assert.assertNotNull(Utilities.makeDate("04/05/1991"));
    }
}
