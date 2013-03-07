package be.kdg.backend.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 28/02/13
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "T_PARTICIPATEDTRIP")
public class ParticipatedTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean isStarted;

    private boolean isFinished;

    private Boolean isConfirmed;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "tripId")
    Trip trip;

    @ManyToOne(cascade ={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "userId")
    User user;

    public ParticipatedTrip() {
        this.isStarted = false;
        this.isFinished = false;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
