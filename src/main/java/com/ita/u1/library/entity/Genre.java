package com.ita.u1.library.entity;

public enum Genre {
    FICTION("Fiction"), NON_FICTION("Non-fiction"), BUSINESS("Business"), NOVEL("Novel"), HISTORY("History"), DETECTIVE("Detective"), FANTASY("Fantasy"), BIOGRAPHY("Biography"), THRILLER("Thriller"), HEALTH("Health"), CHILDREN("Children");

    private String value;
    Genre (String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }


}
