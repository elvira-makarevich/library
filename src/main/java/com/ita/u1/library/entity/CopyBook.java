package com.ita.u1.library.entity;

public class CopyBook {

    private int id;
    private int bookId;
    private double costPerDay;
    private boolean availability;

    public CopyBook() {
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

    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
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
        if (Double.compare(copyBook.costPerDay, costPerDay) != 0) return false;
        return availability == copyBook.availability;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + bookId;
        temp = Double.doubleToLongBits(costPerDay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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

