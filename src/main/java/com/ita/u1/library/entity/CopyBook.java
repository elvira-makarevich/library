package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CopyBook implements Serializable {

    private int id;
    private int bookId;
    private BigDecimal costPerDay;
    private boolean availability;

    public CopyBook() {
    }

    public CopyBook(BigDecimal costPerDay, boolean availability) {
        this.costPerDay = costPerDay;
        this.availability = availability;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyBook copyBook = (CopyBook) o;

        if (id != copyBook.id) return false;
        if (bookId != copyBook.bookId) return false;
        if (availability != copyBook.availability) return false;
        return costPerDay != null ? costPerDay.equals(copyBook.costPerDay) : copyBook.costPerDay == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + bookId;
        result = 31 * result + (costPerDay != null ? costPerDay.hashCode() : 0);
        result = 31 * result + (availability ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CopyBook{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", costPerDay=" + costPerDay +
                ", availability=" + availability +
                '}';
    }

}

