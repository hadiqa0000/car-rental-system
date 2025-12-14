package com.carrental;

public abstract class Car implements Rentable {
    private String licensePlate;
    private String brand;
    private String model;
    private double dailyRate;
    private boolean isAvailable;
    
   
    public Car(String licensePlate, String brand, String model, double dailyRate) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.dailyRate = dailyRate;
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
        System.out.println(brand + " " + model + " returned");
        return true;
    }
    
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }
    
    
    @Override
    public abstract double calculateRentalFee(int days);
    
   
    public String getLicensePlate() { return licensePlate; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }
    
    @Override
    public String toString() {
        return brand + " " + model + " [" + licensePlate + "]";
    }
}
