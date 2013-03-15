package be.kdg.android.entities;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/03/13
 */
public class Equipment {
    private Integer id;
    private String description;
    private Trip trip;

    public Equipment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
