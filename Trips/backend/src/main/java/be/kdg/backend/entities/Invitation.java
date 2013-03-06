package be.kdg.backend.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Maarten
 * Date: 6/03/13
 * Time: 9:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "T_INVITATION")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tripId")
    private Trip trip;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    private Boolean isConfirmed;

    public Invitation() {
        setConfirmed(false);
    }

    public Invitation(Trip trip, User user) {
        this.trip = trip;
        this.user = user;
        setConfirmed(false);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }
}
