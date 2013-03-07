package be.kdg.android.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 14:29
 * Copyright @ Soulware.be
 */

public class Question {
    private Integer id;
    private String question;
    private Set<Answer> answers;

    public Question() {
        initLists();
    }

    private void initLists(){
        this.answers = new HashSet<Answer>();
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
