package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Order implements Serializable {

    private int id;
    private int clientId;
    private List<CopyBook> books;
    private BigDecimal preliminaryCost;
    private BigDecimal totalCost;
    private BigDecimal penalty;
    private LocalDate orderDate;
    private LocalDate possibleReturnDate;
    private LocalDate realReturnDate;
    private boolean status;

    public Order() {
    }

    public Order(int clientId, BigDecimal preliminaryCost, List<CopyBook> books, LocalDate orderDate, LocalDate possibleReturnDate) {
        this.clientId = clientId;
        this.preliminaryCost = preliminaryCost;
        this.books = books;
        this.orderDate = orderDate;
        this.possibleReturnDate = possibleReturnDate;
    }

    public Order(int orderId, List<CopyBook> books, LocalDate orderDate, LocalDate possibleReturnDate, LocalDate realReturnDate, BigDecimal preliminaryCost, BigDecimal penalty, BigDecimal totalCost) {
        this.id = orderId;
        this.books = books;
        this.orderDate = orderDate;
        this.possibleReturnDate = possibleReturnDate;
        this.realReturnDate = realReturnDate;
        this.preliminaryCost = preliminaryCost;
        this.penalty = penalty;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public List<CopyBook> getBooks() {
        return books;
    }

    public void setBooks(List<CopyBook> books) {
        this.books = books;
    }

    public BigDecimal getPreliminaryCost() {
        return preliminaryCost;
    }

    public void setPreliminaryCost(BigDecimal preliminaryCost) {
        this.preliminaryCost = preliminaryCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getPossibleReturnDate() {
        return possibleReturnDate;
    }

    public void setPossibleReturnDate(LocalDate possibleReturnDate) {
        this.possibleReturnDate = possibleReturnDate;
    }

    public LocalDate getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(LocalDate realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (clientId != order.clientId) return false;
        if (status != order.status) return false;
        if (books != null ? !books.equals(order.books) : order.books != null) return false;
        if (preliminaryCost != null ? !preliminaryCost.equals(order.preliminaryCost) : order.preliminaryCost != null)
            return false;
        if (totalCost != null ? !totalCost.equals(order.totalCost) : order.totalCost != null) return false;
        if (penalty != null ? !penalty.equals(order.penalty) : order.penalty != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (possibleReturnDate != null ? !possibleReturnDate.equals(order.possibleReturnDate) : order.possibleReturnDate != null)
            return false;
        return realReturnDate != null ? realReturnDate.equals(order.realReturnDate) : order.realReturnDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + clientId;
        result = 31 * result + (books != null ? books.hashCode() : 0);
        result = 31 * result + (preliminaryCost != null ? preliminaryCost.hashCode() : 0);
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (penalty != null ? penalty.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (possibleReturnDate != null ? possibleReturnDate.hashCode() : 0);
        result = 31 * result + (realReturnDate != null ? realReturnDate.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", books=" + books +
                ", preliminaryCost=" + preliminaryCost +
                ", totalCost=" + totalCost +
                ", penalty=" + penalty +
                ", orderDate=" + orderDate +
                ", possibleReturnDate=" + possibleReturnDate +
                ", realReturnDate=" + realReturnDate +
                ", status=" + status +
                '}';
    }

}
