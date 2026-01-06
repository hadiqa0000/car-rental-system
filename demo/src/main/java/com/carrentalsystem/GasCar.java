package com.carrentalsystem;


public class GasCar extends Car {
    private String fuelType;

    public GasCar(int id, String brand, String model, int year, double pricePerDay, String fuelType) {
        super(id, brand, model, year, pricePerDay);
        this.fuelType = fuelType;
    }

    @Override
    public double calculateRentalFee(int days) {
        return getpricePerDay() * days;
    }

    public String getFuelType() {
        return fuelType;
    }
}
