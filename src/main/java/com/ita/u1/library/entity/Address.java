package com.ita.u1.library.entity;

import java.io.Serializable;

public class Address implements Serializable {

    private int postcode;
    private String country;
    private String locality;
    private String street;
    private int houseNumber;
    private String building;
    private int apartmentNumber;

    public Address() {
    }

    public Address(int postcode, String country, String locality, String street, int houseNumber, String building, int apartmentNumber) {
        this.postcode = postcode;
        this.country = country;
        this.locality = locality;
        this.street = street;
        this.houseNumber = houseNumber;
        this.building = building;
        this.apartmentNumber = apartmentNumber;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (postcode != address.postcode) return false;
        if (houseNumber != address.houseNumber) return false;
        if (apartmentNumber != address.apartmentNumber) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (locality != null ? !locality.equals(address.locality) : address.locality != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return building != null ? building.equals(address.building) : address.building == null;
    }

    @Override
    public int hashCode() {
        int result = postcode;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + houseNumber;
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + apartmentNumber;
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "postcode=" + postcode +
                ", country='" + country + '\'' +
                ", locality='" + locality + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", building='" + building + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
