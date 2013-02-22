package dao;

import be.kdg.dao.interfaces.StopDao;
import be.kdg.entities.Answer;
import be.kdg.entities.MultipleChoiceQuestion;
import be.kdg.entities.Picture;
import be.kdg.entities.Stop;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 21/02/13
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = "/persistence-beans.xml")
public class StopTest extends AbstractJUnit4SpringContextTests {

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
        assertTrue(stopDao.find(temp.getId()).getPictures().size() > 0);
    }

    @Test
    public void testAddMultipleChoice(){
        Stop temp = newStop();
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setQuestion("Wat werpt Brabo?");

        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion();
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
        /*
        question.addAnswer(answer);
        temp.addMultipleChoiceQuestion(question);
        question.addAnswer(answer2);*/
        question.addAnswer(answer3);
        temp.addMultipleChoiceQuestion(question2);
        stopDao.add(temp);
        assertTrue(stopDao.find(temp.getId()).getQuestions().size() > 0);
    }

    @After
    public void deleteStops(){
        for(Stop stop : stopDao.list()){

            stopDao.remove(stop);
        }
        assertTrue(stopDao.list().size() == 0);
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
