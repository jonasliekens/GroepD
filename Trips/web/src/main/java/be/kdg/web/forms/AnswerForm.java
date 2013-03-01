package be.kdg.web.forms;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 1/03/13
 * Time: 0:24
 * To change this template use File | Settings | File Templates.
 */
public class AnswerForm {
    private String answer;
    private boolean iscorrect;

    public AnswerForm() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(boolean iscorrect) {
        this.iscorrect = iscorrect;
    }
}
