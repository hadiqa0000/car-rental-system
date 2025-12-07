import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Car {
    private int  id;
    private String model;
    private String brand;
    private int year;
    private double pricePerday;
    private boolean available;

    public Car(int id, String brand, String model, int year, double pricePerDay, boolean available) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerday = pricePerDay;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPricePerday() {
        return pricePerday;
    }

    public void setPricePerday(double pricePerday) {
        this.pricePerday = pricePerday;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }



};

public class Car_inventory{
    private List<Car> cars = new ArrayList<>();

    public void loadfromcsv(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0];
                String brand = values[1];
                int year = Integer.parseInt(values[2]);
                double pricePerDay = Double.parseDouble(values[3]);
                boolean available = Boolean.parseBoolean(values[4]);
                Car car = new Car(id, brand, year, pricePerDay, available);
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
}




class Main {
    public static void main(String[] args) {
        System.out.println ("Try programiz.pro");
    }
}