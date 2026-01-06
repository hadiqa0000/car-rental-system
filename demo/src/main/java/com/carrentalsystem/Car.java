package com.carrentalsystem;

public abstract class Car implements Rentable {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean isAvailable;
    
    public Car(int id, String brand, String model, int year, double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }
    
    
    @Override
    public boolean rent(Customer customer, int days) {
        if (isAvailable) {
            isAvailable = false;
            System.out.println(brand + " " + model + " rented to " + customer.getName());
            return true;
        }
        return false;
    }
    
    @Override
    public boolean returnVehicle() {
        isAvailable = true;
        
        return true;
    }
    
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }
    
    
    @Override
    public abstract double calculateRentalFee(int days);
    

    public int id() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getpricePerDay() { return pricePerDay; }
    public int getYear() { return year; } 
    
    @Override
    public String toString() {
        return brand + " " + model + " [" + id + "]";
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
}
}