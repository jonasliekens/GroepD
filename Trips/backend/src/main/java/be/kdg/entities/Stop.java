package be.kdg.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @Column(columnDefinition="text")
    private String description;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Integer accuracy;

    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "tripId")
    private Trip trip;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval=true)
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "stopId")
    private Set<Picture> pictures;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval=true)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "stopId")
    private Set<MultipleChoiceQuestion> multipleChoiceQuestions;

    public Stop() {
        initLists();
    }

    public Stop(String name, double latitude, double longitude, Integer accuracy) {
        initLists();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    private void initLists() {
        pictures = new HashSet<Picture>();
        multipleChoiceQuestions = new HashSet<MultipleChoiceQuestion>();
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPicture(Picture picture){
        this.pictures.add(picture);
    }

    public void addMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion){
        this.multipleChoiceQuestions.add(multipleChoiceQuestion);
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

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Set<Picture> getPictures(){
        return pictures;
    }

    public Set<MultipleChoiceQuestion> getQuestions(){
        return multipleChoiceQuestions;
    }
}
