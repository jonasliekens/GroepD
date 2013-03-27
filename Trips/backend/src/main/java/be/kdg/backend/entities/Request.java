package be.kdg.backend.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 12:21
 * Copyright @ Soulware.be
 */
@Entity
@Table(name = "T_REQUEST")
public class Request {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ElementCollection
    private Set<String> userIds;

    private String requestId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "tripId")
    private Trip trip;

    public Request() {
        this.userIds = new HashSet<String>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> requestIds) {
        this.userIds = requestIds;
    }

    public void removeUserId(String id) {
        if (userIds.contains(id)) {
            userIds.remove(id);
        }
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void removeUserFromList(String userId) {
        if (this.userIds.contains(userId)) {
            this.userIds.remove(userId);
        }
    }
}
