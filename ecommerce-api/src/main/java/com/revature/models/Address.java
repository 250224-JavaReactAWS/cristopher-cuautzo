package com.revature.models;

public class Address {
    private int addressId;
    private String country;
    private String street;
    private String zipCode;
    private String instructions;
    private int userId;

    private static int addressIdCounter = 1;

    public Address(String country, String street, String zipCode, String instructions, int userId) {
        this.country = country;
        this.street = street;
        this.zipCode = zipCode;
        this.instructions = instructions;
        this.userId = userId;

        this.addressId = addressIdCounter;
        addressIdCounter++;
    }

    public Address(String country, String street, String zipCode, int userId) {
        this.country = country;
        this.street = street;
        this.zipCode = zipCode;
        this.userId = userId;

        this.addressId = addressIdCounter;
        addressIdCounter++;
    }

    public Address(){}

    public int getAddressId() { return addressId; }

    public void setAddressId(int addressId) { this.addressId = addressId; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { instructions = instructions; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Address {" +
                "addressId = " + addressId +
                ", country = '" + country + "'" +
                ", street = '" + street + "'" +
                ", zipCode = '" + zipCode + "'" +
                ", instructions = '" +  instructions + "'" +
                ", userId = '" + userId + "'" +
                "}";
    }
}
