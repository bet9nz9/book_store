package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String genre;
    private String date;
    private Float coast;
    private Integer views;

    public Book() {
    }

    public Book(String title, String genre, String date, Float coast) {
        this.title = title;
        this.genre = genre;
        this.date = date;
        this.coast = coast;
    }
}
