import java.util.Scanner;

//test commit :(
interface Rentable {
    double calculateRentalFee(int days);
}

class Car implements Rentable {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean available;
    String damageReport = "";

    Car(int id, String brand, String model, int year, double pricePerDay, boolean available) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public double calculateRentalFee(int days) {
        return pricePerDay * days;
    }

    public String getDamageReport() {
        return damageReport;
    }
}

class ElectricCar extends Car {
    private int batteryCapacity;

    ElectricCar(int id, String brand, String model, int year, double pricePerDay, boolean available,
            int batteryCapacity) {
        super(id, brand, model, year, pricePerDay, available);
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public double calculateRentalFee(int days) {
        return super.calculateRentalFee(days) + (days * 5);
    }
}

class GasCar extends Car {
    int mpg;

    public GasCar(int id, String brand, String model, int year,
            double pricePerDay, boolean available, int mpg) {

        super(id, brand, model, year, pricePerDay, available);
        this.mpg = mpg;
    }

    public int getMpg() {
        return mpg;
    }
}

class Customer {
    int id;
    String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Rental {
    int rentalId;
    Car car;
    Customer customer;
    int days;
    double totalFee;

    public Rental(int rentalId, Car car, Customer customer, int days) {
        this.rentalId = rentalId;
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.totalFee = car.calculateRentalFee(days);
    }
}

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static CarInventory inv = new CarInventory();
    private static Map<Integer, Customer> customers = new HashMap<>();

    public static void main(String[] args) {
        inv.loadFromCSV("car_inventory.csv");

    }

}