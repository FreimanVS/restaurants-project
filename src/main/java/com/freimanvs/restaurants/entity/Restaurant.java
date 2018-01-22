package com.freimanvs.restaurants.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Freiman V.S.
 * @version 1.0
 */
@Entity
@Table(name="rest", schema = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rest")
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rest_menu",
            joinColumns = @JoinColumn(name = "rest_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menu> menu = new HashSet<>();

    public Restaurant() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Menu> getMenu() {
        return menu;
    }

    public void setMenu(Set<Menu> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{" +
                "\"id\": " + id + ",\r\n" +
                "\"name\": \"" + name + '\"' +
                '}');
        return sb.toString();
    }
}