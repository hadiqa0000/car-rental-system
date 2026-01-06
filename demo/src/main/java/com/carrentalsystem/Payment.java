package com.carrentalsystem;
public class Payment {
    private double amount;
    private boolean isPaid;
    
    public Payment(double amount) {
        this.amount = amount;
        this.isPaid = false;
    }
    
    
    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    
}