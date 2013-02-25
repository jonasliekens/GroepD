package dao;

import be.kdg.backend.dao.interfaces.StopDao;
import be.kdg.backend.entities.Answer;
import be.kdg.backend.entities.Question;
import be.kdg.backend.entities.Picture;
import be.kdg.backend.entities.Stop;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 21/02/13
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class StopTest extends AbstractJUnit4SpringContextTests {

    @Qualifier("stopDaoImpl")
    @Autowired(required = true)
    private StopDao stopDao;

    @Test
    public void testAddPicture(){
        Stop temp = newStop();
        Picture picture = new Picture();
        picture.setUrl("https://www.facebook.com/");
        picture.setDescription("Facebook");
        temp.addPicture(picture);
        stopDao.add(temp);
        assertTrue(stopDao.findById(temp.getId()).getPictures().size() > 0);
    }

    @Test
    public void testAddMultipleChoice(){
        Stop temp = newStop();
        Question question = new Question();
        question.setQuestion("Wat werpt Brabo?");

        Question question2 = new Question();
        question2.setQuestion("Wordt ik mee verwijderd?");

        Answer answer = new Answer();
        answer.setCorrect(true);
        answer.setAnswer("Een Hand");

        Answer answer2 = new Answer();
        answer2.setCorrect(false);
        answer2.setAnswer("Een Voet");

        Answer answer3 = new Answer();
        answer3.setCorrect(false);
        answer3.setAnswer("Een Hoofd");

        question.addAnswer(answer);
        question.addAnswer(answer2);
        question.addAnswer(answer3);
        temp.addQuestion(question);
        temp.addQuestion(question2);
        stopDao.add(temp);
        assertTrue(stopDao.findById(temp.getId()).getQuestions().size() > 0);
    }

    @After
    public void deleteStops(){
        for(Stop stop : stopDao.findAll()){
            stopDao.remove(stop);
        }
        assertFalse(stopDao.findAll().size() > 0);
    }

    private Stop newStop(){
        Stop stop = new Stop();
        stop.setDescription("Het standbeeld van Brabo in antwerpen");
        stop.setName("Brabo");
        stop.setLatitude(1245.213);
        stop.setLongitude(1548.325);
        return stop;
    }
}
