package com.ita.u1.library.entity;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private long id;
    private long clientId;
    private List<Book> books;
    private double advanceOrderValue;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getAdvanceOrderValue() {
        return advanceOrderValue;
    }

    public void setAdvanceOrderValue(double advanceOrderValue) {
        this.advanceOrderValue = advanceOrderValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (clientId != order.clientId) return false;
        if (Double.compare(order.advanceOrderValue, advanceOrderValue) != 0) return false;
        return books != null ? books.equals(order.books) : order.books == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + (books != null ? books.hashCode() : 0);
        temp = Double.doubleToLongBits(advanceOrderValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", books=" + books +
                ", advanceOrderValue=" + advanceOrderValue +
                '}';
    }

}
