package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CopyBook implements Serializable {

    private int id;
    private int bookId;
    private BigDecimal costPerDay;
    private boolean availability;
    private String title;

    public CopyBook() {
    }

    public CopyBook(BigDecimal costPerDay, boolean availability) {
        this.costPerDay = costPerDay;
        this.availability = availability;
    }

    public CopyBook(int id) {
        this.id=id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyBook copyBook = (CopyBook) o;

        if (id != copyBook.id) return false;
        if (bookId != copyBook.bookId) return false;
        if (availability != copyBook.availability) return false;
        if (costPerDay != null ? !costPerDay.equals(copyBook.costPerDay) : copyBook.costPerDay != null) return false;
        return title != null ? title.equals(copyBook.title) : copyBook.title == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + bookId;
        result = 31 * result + (costPerDay != null ? costPerDay.hashCode() : 0);
        result = 31 * result + (availability ? 1 : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
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
                '}';
    }
}

