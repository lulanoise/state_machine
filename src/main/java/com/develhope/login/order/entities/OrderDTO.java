package com.develhope.login.order.entities;

public class OrderDTO {

    private String description;
    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;
    private double totalPrice;
    private Long restaurant;

    public OrderDTO(String description, String address, String number, String city, String zipCode, String state, double totalPrice, Long restaurant) {
        this.description = description;
        this.address = address;
        this.number = number;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.totalPrice = totalPrice;
        this.restaurant = restaurant;
    }

    public OrderDTO(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", totalPrice=" + totalPrice +
                ", restaurant=" + restaurant +
                '}';
    }
}
