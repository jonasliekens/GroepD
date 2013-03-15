package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 14:29
 * Copyright @ Soulware.be
 */

@Entity
@Table(name = "T_QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionId")
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

}
