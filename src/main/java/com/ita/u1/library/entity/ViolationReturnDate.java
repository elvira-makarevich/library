package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ViolationReturnDate implements Serializable {

    private Order order;
    private Client client;
    private List<CopyBook> copyBooks;
    private BigDecimal penaltyAmountForDelaying;

    public ViolationReturnDate() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<CopyBook> getCopyBooks() {
        return copyBooks;
    }

    public void setCopyBooks(List<CopyBook> copyBooks) {
        this.copyBooks = copyBooks;
    }

    public BigDecimal getPenaltyAmountForDelaying() {
        return penaltyAmountForDelaying;
    }

    public void setPenaltyAmountForDelaying(BigDecimal penaltyAmountForDelaying) {
        this.penaltyAmountForDelaying = penaltyAmountForDelaying;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViolationReturnDate that = (ViolationReturnDate) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        if (copyBooks != null ? !copyBooks.equals(that.copyBooks) : that.copyBooks != null) return false;
        return penaltyAmountForDelaying != null ? penaltyAmountForDelaying.equals(that.penaltyAmountForDelaying) : that.penaltyAmountForDelaying == null;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (copyBooks != null ? copyBooks.hashCode() : 0);
        result = 31 * result + (penaltyAmountForDelaying != null ? penaltyAmountForDelaying.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViolationReturnDate{" +
                "order=" + order +
                ", client=" + client +
                ", copyBooks=" + copyBooks +
                ", penaltyAmountForDelaying=" + penaltyAmountForDelaying +
                '}';
    }
}
