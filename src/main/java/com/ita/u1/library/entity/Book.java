package com.ita.u1.library.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book implements Serializable {

    private long id;
    private String title;
    private String originalTitle;
    private List<Genre> genres;
    private int numberOfCopies;
    private List<Author> authors;
    private List<Byte[]> covers;
    private double costPerDay;
    private int publishingYear;
    private Date registrationDate;
    private int numberOfPages;
    private int numberOfAvailableCopies;
    private double rating;

    public Book() {
    }







}