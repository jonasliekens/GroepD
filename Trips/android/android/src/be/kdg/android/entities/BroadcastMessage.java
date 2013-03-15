package be.kdg.android.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 6/03/13
 * Time: 10:18
 * Copyright @ Soulware.be
 */
public class BroadcastMessage {
    private Integer id;
    private String message;
    private Set<User> recievers;
    private Trip trip;
    private Date date;

    public BroadcastMessage() {
    }

    public BroadcastMessage(String message, Trip trip, Date date) {
        this.message = message;
        this.trip = trip;
        this.date = date;
        this.recievers = new HashSet<User>();
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

    public Set<User> getRecievers() {
        return recievers;
    }

    public void setRecievers(Set<User> recievers) {
        this.recievers = recievers;
    }

    public void addReciever(User user){
        this.recievers.add(user);
    }

    public void removeReciever(User user){
        if(this.recievers.contains(user)){
            this.recievers.remove(user);
        }
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
