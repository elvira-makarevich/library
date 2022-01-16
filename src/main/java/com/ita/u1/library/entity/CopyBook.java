package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CopyBook implements Serializable {

    private int id;
    private int bookId;
    private BigDecimal costPerDay;
    private boolean availability;
    private String title;
    private double rating;
    private List<Violation> copyBooksViolations;
    private boolean existence;

    public CopyBook() {
    }

    public CopyBook(BigDecimal costPerDay, boolean availability) {
        this.costPerDay = costPerDay;
        this.availability = availability;
    }

    public CopyBook(int id) {
        this.id = id;
    }

    public CopyBook(int copyId, BigDecimal newCostPerDay) {
        this.id = copyId;
        this.costPerDay = newCostPerDay;
    }

    public CopyBook(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Violation> getCopyBooksViolations() {
        return copyBooksViolations;
    }

    public void setCopyBooksViolations(List<Violation> copyBooksViolations) {
        this.copyBooksViolations = copyBooksViolations;
    }

    public boolean isExistence() {
        return existence;
    }

    public void setExistence(boolean existence) {
        this.existence = existence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyBook copyBook = (CopyBook) o;

        if (id != copyBook.id) return false;
        if (bookId != copyBook.bookId) return false;
        if (availability != copyBook.availability) return false;
        if (Double.compare(copyBook.rating, rating) != 0) return false;
        if (existence != copyBook.existence) return false;
        if (costPerDay != null ? !costPerDay.equals(copyBook.costPerDay) : copyBook.costPerDay != null) return false;
        if (title != null ? !title.equals(copyBook.title) : copyBook.title != null) return false;
        return copyBooksViolations != null ? copyBooksViolations.equals(copyBook.copyBooksViolations) : copyBook.copyBooksViolations == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + bookId;
        result = 31 * result + (costPerDay != null ? costPerDay.hashCode() : 0);
        result = 31 * result + (availability ? 1 : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (copyBooksViolations != null ? copyBooksViolations.hashCode() : 0);
        result = 31 * result + (existence ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CopyBook{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", costPerDay=" + costPerDay +
                ", availability=" + availability +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", copyBooksViolations=" + copyBooksViolations +
                ", existence=" + existence +
                '}';
    }
}

