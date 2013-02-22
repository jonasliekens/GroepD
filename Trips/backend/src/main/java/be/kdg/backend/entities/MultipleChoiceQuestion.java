package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "T_MULTIPLECHOICE")
public class MultipleChoiceQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String question;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name = "multipleChoiceId")
    private Set<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "stopId")
    private Stop stop;

    public MultipleChoiceQuestion() {
        answers = new HashSet<Answer>();
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
}
