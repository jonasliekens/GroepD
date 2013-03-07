package be.kdg.android.entities;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 13:28
 */

import java.util.Date;

public class Message {
    private Integer id;
    private String message;
    private User sender;
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
}
