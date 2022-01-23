package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CopyBook implements Serializable {

    private int id;
    private int bookId;
    private BigDecimal costPerDay;
    private String title;
    private double rating;
    private List<ViolationBook> copyBooksViolations;
    private boolean existence;
    private int available;

    public CopyBook() {
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

    public CopyBook(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public CopyBook(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
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

    public List<ViolationBook> getCopyBooksViolations() {
        return copyBooksViolations;
    }

    public void setCopyBooksViolations(List<ViolationBook> copyBooksViolations) {
        this.copyBooksViolations = copyBooksViolations;
    }

    public boolean isExistence() {
        return existence;
    }

    public void setExistence(boolean existence) {
        this.existence = existence;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyBook copyBook = (CopyBook) o;

        if (id != copyBook.id) return false;
        if (bookId != copyBook.bookId) return false;
        if (Double.compare(copyBook.rating, rating) != 0) return false;
        if (existence != copyBook.existence) return false;
        if (available != copyBook.available) return false;
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
        result = 31 * result + (title != null ? title.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (copyBooksViolations != null ? copyBooksViolations.hashCode() : 0);
        result = 31 * result + (existence ? 1 : 0);
        result = 31 * result + available;
        return result;
    }

    @Override
    public String toString() {
        return "CopyBook{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", costPerDay=" + costPerDay +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", copyBooksViolations=" + copyBooksViolations +
                ", existence=" + existence +
                ", available=" + available +
                '}';
    }
}

