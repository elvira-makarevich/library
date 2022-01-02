package com.ita.u1.library.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Client implements Serializable {

    private int id;
    private byte[] image;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String passportNumber;
    private String email;
    private Address address;
    private Date dateOfBirth;

    public Client() {
    }

    public Client(String firstName, String lastName, String patronymic, String passportNumber, String email, Date dateOfBirth, Address address, byte[] image) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.passportNumber = passportNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.image = image;
    }

    public Client(int clientId) {
        this.id = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (!Arrays.equals(image, client.image)) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        if (lastName != null ? !lastName.equals(client.lastName) : client.lastName != null) return false;
        if (patronymic != null ? !patronymic.equals(client.patronymic) : client.patronymic != null) return false;
        if (passportNumber != null ? !passportNumber.equals(client.passportNumber) : client.passportNumber != null)
            return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        return dateOfBirth != null ? dateOfBirth.equals(client.dateOfBirth) : client.dateOfBirth == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", image=" + Arrays.toString(image) +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
