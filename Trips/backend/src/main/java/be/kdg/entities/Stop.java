package be.kdg.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:09
 * Copyright @ Soulware.be
 */
@Entity
@Table(name = "T_STOP")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    private Integer accuracy;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "tripId")
    private Trip trip;

    public Stop() {
    }

    public Stop(String name, double latitude, double longitude, Integer accuracy) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String plaatsnaam) {
        this.name = plaatsnaam;
    }
}
