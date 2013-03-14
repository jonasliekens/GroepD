package be.kdg.backend.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 00:49
 */
@Entity
@Table(name = "T_EXPENSE")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "tripId")
    @NotNull
    private Trip trip;

    @ManyToOne()
    @JoinColumn(name = "userId")
    @NotNull
    private User user;

    @NotNull
    private Double price;

    @NotNull
    @Type(type = "text")
    private String description;

    @NotNull
    private Date date;

    public Expense() {
    }

    public Expense(Trip trip, User user, Double price, String description) {
        this.trip = trip;
        this.user = user;
        this.price = price;
        this.description = description;
        this.date = new Date();
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
