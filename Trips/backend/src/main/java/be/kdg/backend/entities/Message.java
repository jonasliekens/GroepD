package be.kdg.backend.entities;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 13:28
 */

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "T_MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Type(type = "text")
    @NotNull
    private String message;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User sender;
    @ManyToOne()
    @JoinColumn(name = "chatId")
    private Chat chat;

    @NotNull
    private Date date;

    public Message() {
    }

    public Message(String message, User sender, Date date) {
        this.message = message;
        this.sender = sender;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
