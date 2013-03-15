package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/03/13
 */
@Entity
@Table(name = "T_EQUIPMENT")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String description;

    @ManyToOne()
    @JoinColumn(name = "tripId")
    private Trip trip;

    public Equipment() {
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }
}
