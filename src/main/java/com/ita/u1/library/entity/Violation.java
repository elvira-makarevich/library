package com.ita.u1.library.entity;

import java.io.Serializable;
import java.util.List;

public class Violation implements Serializable {

    private int id;
    private int orderId;
    private int copyId;
    private String message;
    private List<byte[]> images;

    public Violation() {
    }

    public Violation(int orderId, int copyId, String message, List<byte[]> images) {
        this.orderId=orderId;
        this.copyId=copyId;
        this.message=message;
        this.images=images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Violation violation = (Violation) o;

        if (id != violation.id) return false;
        if (orderId != violation.orderId) return false;
        if (copyId != violation.copyId) return false;
        if (message != null ? !message.equals(violation.message) : violation.message != null) return false;
        return images != null ? images.equals(violation.images) : violation.images == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderId;
        result = 31 * result + copyId;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", copyId=" + copyId +
                ", message='" + message + '\'' +
                ", images=" + images +
                '}';
    }
}
