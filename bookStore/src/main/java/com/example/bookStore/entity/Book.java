package com.example.bookStore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Book {
    @Id
    private String id;
    private String title;
    private String genre;
    private String date;
    private String coast;
    private Integer addings;
    @ManyToMany(mappedBy = "userLibrary")
    private Set<User> users = new HashSet<>();

    public Book() {
    }

    public Book(String title, String genre, String date, String coast) {
        this.title = title;
        this.genre = genre;
        this.date = date;
        this.coast = coast;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }

    public void incrementAddings() {
        this.addings++;
    }
}
