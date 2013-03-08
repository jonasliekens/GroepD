package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 13:32
 */
@Entity
@Table(name = "T_CHAT")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="T_CHAT_USER",
            joinColumns={@JoinColumn(name="chatId")},
            inverseJoinColumns={@JoinColumn(name="userId")})
    private Set<User> participants;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chatId")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (!id.equals(chat.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
