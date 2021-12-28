package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Book implements Serializable {

    private int id;
    private String title;
    private String originalTitle;
    private List<Genre> genres;
    private BigDecimal price;
    private int numberOfCopies;
    private int numberOfAvailableCopies;
    private List<Author> authors;
    private List<byte[]> covers;
    private int publishingYear;
    private Date registrationDate;
    private int numberOfPages;
    private double rating;

    public Book() {
    }

    public Book(String title, String originalTitle, List<Genre> genreList, BigDecimal bookPrice, int copiesNumber, List<Author> authors, List<byte[]> covers, int bookPublishingYear, int bookNumberOfPages) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.genres = genreList;
        this.price = bookPrice;
        this.numberOfCopies = copiesNumber;
        this.authors = authors;
        this.covers = covers;
        this.publishingYear = bookPublishingYear;
        this.numberOfPages = bookNumberOfPages;
    }

    public Book(int id, String title, int copiesNumber) {
        this.id = id;
        this.title = title;
        this.numberOfCopies = copiesNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public int getNumberOfAvailableCopies() {
        return numberOfAvailableCopies;
    }

    public void setNumberOfAvailableCopies(int numberOfAvailableCopies) {
        this.numberOfAvailableCopies = numberOfAvailableCopies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<byte[]> getCovers() {
        return covers;
    }

    public void setCovers(List<byte[]> covers) {
        this.covers = covers;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (numberOfCopies != book.numberOfCopies) return false;
        if (numberOfAvailableCopies != book.numberOfAvailableCopies) return false;
        if (publishingYear != book.publishingYear) return false;
        if (numberOfPages != book.numberOfPages) return false;
        if (Double.compare(book.rating, rating) != 0) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (originalTitle != null ? !originalTitle.equals(book.originalTitle) : book.originalTitle != null)
            return false;
        if (genres != null ? !genres.equals(book.genres) : book.genres != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;
        if (authors != null ? !authors.equals(book.authors) : book.authors != null) return false;
        if (covers != null ? !covers.equals(book.covers) : book.covers != null) return false;
        return registrationDate != null ? registrationDate.equals(book.registrationDate) : book.registrationDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + numberOfCopies;
        result = 31 * result + numberOfAvailableCopies;
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (covers != null ? covers.hashCode() : 0);
        result = 31 * result + publishingYear;
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + numberOfPages;
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", genres=" + genres +
                ", price=" + price +
                ", numberOfCopies=" + numberOfCopies +
                ", numberOfAvailableCopies=" + numberOfAvailableCopies +
                ", authors=" + authors +
                ", covers=" + covers +
                ", publishingYear=" + publishingYear +
                ", registrationDate=" + registrationDate +
                ", numberOfPages=" + numberOfPages +
                ", rating=" + rating +
                '}';
    }
}