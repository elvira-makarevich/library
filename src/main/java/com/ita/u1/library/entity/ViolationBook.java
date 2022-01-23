package com.ita.u1.library.entity;

import java.io.Serializable;
import java.util.List;

public class ViolationBook implements Serializable {

    private int id;
    private int copyId;
    private String message;
    private List<byte[]> images;

    public ViolationBook() {
    }

    public ViolationBook(int copyId, String message, List<byte[]> images) {
        this.copyId = copyId;
        this.message = message;
        this.images = images;
    }

    public ViolationBook(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        ViolationBook that = (ViolationBook) o;

        if (id != that.id) return false;
        if (copyId != that.copyId) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return images != null ? images.equals(that.images) : that.images == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + copyId;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViolationBook{" +
                "id=" + id +
                ", copyId=" + copyId +
                ", message='" + message + '\'' +
                ", images=" + images +
                '}';
    }
}
