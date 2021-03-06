package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 13:54
 * Copyright @ Soulware.be
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")
    @NotNull
    private String email;

    @NotNull
    //TODO: JSONIGNORE?
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String facebookID;

    @NotNull
    private Date birthday;

    private boolean receiveMails;

    private boolean shareLocation;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "recievers")
    private Set<BroadcastMessage> broadcastMessages;

    //TODO: Remove eager loading, it fails on ChatServiceTest.testGetOrCreate() though
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "participants", fetch = FetchType.EAGER)
    private Set<Chat> chats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<ParticipatedTrip> participatedTrips;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private Set<Message> messages;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Expense> expenses;
    @OneToMany()
    private Set<User> blockedUsers;


    public User() {
        initLists();
    }

    public User(String email, String password, String firstName, String lastName, Date birthday) {
        initLists();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public User(String email, String password, String firstName, String lastName, Date birthday, String facebookID) {
        initLists();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.facebookID = facebookID;
    }

    private void initLists() {
        this.participatedTrips = new HashSet<ParticipatedTrip>();
        this.chats = new HashSet<Chat>();
        this.broadcastMessages = new HashSet<BroadcastMessage>();
        this.messages = new HashSet<Message>();
        this.blockedUsers = new HashSet<User>();
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public Date getBirthday() {
        return birthday;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Set<ParticipatedTrip> getParticipatedTrips() {
        return participatedTrips;
    }

    public void setParticipatedTrips(Set<ParticipatedTrip> participatedTrips) {
        this.participatedTrips = participatedTrips;
    }

    public void addParticipatedTrips(ParticipatedTrip participatedTrip) {
        this.participatedTrips.add(participatedTrip);
    }

    public boolean isReceiveMails() {
        return receiveMails;
    }

    public void setReceiveMails(boolean receiveMails) {
        this.receiveMails = receiveMails;
    }

    public boolean isShareLocation() {
        return shareLocation;
    }

    public void setShareLocation(boolean shareLocation) {
        this.shareLocation = shareLocation;
    }

    public Set<BroadcastMessage> getBroadcastMessages() {
        return broadcastMessages;
    }

    public void setBroadcastMessages(Set<BroadcastMessage> broadcastMessages) {
        this.broadcastMessages = broadcastMessages;
    }

    @Deprecated
    //TODO: "detached entity passed to persist" exception on usage, use the dao getMessageByUserId instead
    public void addBroadcastMessage(BroadcastMessage message) {
        this.broadcastMessages.add(message);
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public Set<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }
    public void addBlockedUser(User user){
        this.blockedUsers.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
