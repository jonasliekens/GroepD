import be.kdg.entities.User;
import be.kdg.utilities.Utilities;

/**
 * Created with IntelliJ IDEA.
 * User: Fluffy
 * Date: 7/02/13
 * Time: 9:55
 * Copyright @ Soulware.be
 */
public class TestData {
    public TestData() {
        new User("soulscammer@soulware.be", Utilities.encryptPassword("souly"), "Jonas", "Liekens", Utilities.makeDate("04/05/1991"));
    }
}