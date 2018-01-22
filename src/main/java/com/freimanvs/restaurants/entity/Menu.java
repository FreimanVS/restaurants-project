package com.freimanvs.restaurants.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Freiman V.S.
 * @version 1.0
 */
@Entity
@Table(name="menu", schema = "restaurants")
public class Menu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="dish")
    private String dish;

    @Column(name="price")
    private double price;

    @JsonIgnore
    @ManyToMany(mappedBy = "menu")
    private Set<Restaurant> rests = new HashSet<>();

    public Menu() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Restaurant> getRests() {
        return rests;
    }

    public void setRests(Set<Restaurant> rests) {
        this.rests = rests;
    }

    @Override
    public String toString() {
        return "{" +
                "id\"" + id + "\"," +
                "dish\"" + dish + "\"," +
                "price\"" + price + "\"," +
                '}';
    }
}