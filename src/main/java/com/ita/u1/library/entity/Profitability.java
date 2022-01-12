package com.ita.u1.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Profitability implements Serializable {

    private LocalDate from;
    private LocalDate to;
    private BigDecimal profit;

    public Profitability() {
    }

    public Profitability(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public Profitability(BigDecimal profit) {
        this.profit = profit;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profitability that = (Profitability) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        return profit != null ? profit.equals(that.profit) : that.profit == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Profitability{" +
                "from=" + from +
                ", to=" + to +
                ", profit=" + profit +
                '}';
    }
}
