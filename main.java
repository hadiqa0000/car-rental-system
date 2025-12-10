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

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

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

    ElectricCar(int id, String brand, String model, int year, double pricePerDay, boolean available, int batteryCapacity) {
        super(id, brand, model, year, pricePerDay, available);
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public double calculateRentalFee(int days) {
        return super.calculateRentalFee(days) + (days * 5);
    }
}
public class main { 
    private static Scanner sc = new Scanner(System.in); private static CarInventory inv = new CarInventory();
}