package be.kdg.android.entities;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 7/03/13
 */
public class Announcement {
    private Integer id;
    private String message;
    private Trip trip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
