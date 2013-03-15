package be.kdg.android.entities;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 28/02/13
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */

public class ParticipatedTrip implements Serializable {
    private Integer id;
    private Boolean isStarted;
    private Boolean isFinished;
    private Boolean isConfirmed;
    private Trip trip;
    private User user;
    private Double latitude;
    private Double longitude;

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

    public Boolean isStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public Boolean isFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
