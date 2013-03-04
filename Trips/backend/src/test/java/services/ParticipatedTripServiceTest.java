package services;

import be.kdg.backend.services.interfaces.ParticipatedTripService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 4/03/13
 * Time: 4:08
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ParticipatedTripServiceTest {
    @Autowired(required = true)
    ParticipatedTripService participatedTripService;

    //UITWERKEN
    @Test
    public void testAddParticipatedTrip(){
         assertTrue(true);
    }
}
