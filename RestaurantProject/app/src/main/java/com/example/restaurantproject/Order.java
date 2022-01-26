package com.example.restaurantproject;

public class Order {

    private int orderId;
    private String username;
    private String foodName;
    private int foodQuantity;
    private double cost;
    private double price;

    public Order() {

    }

    public Order(String username, String foodName, int foodQuantity, double cost, double price) {
        this.username = username;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.cost = cost;
        this.price = price;
    }

    public Order(int orderId, String username, String foodName, int foodQuantity, double cost, double price) {
        this.orderId = orderId;
        this.username = username;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.cost = cost;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
