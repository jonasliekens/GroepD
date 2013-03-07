package be.kdg.android.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 13:32
 */
public class Chat {
    private Integer id;
    private Set<User> participants;
    private Set<Message> messages;

    public Chat() {
        this.participants = new HashSet<User>();
        this.messages = new HashSet<Message>();
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
