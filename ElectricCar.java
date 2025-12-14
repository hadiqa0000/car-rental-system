public class ElectricCar extends Car {
    private double electricCapacity; // kWh

    public ElectricCar(int id, String brand, String model, int year, double pricePerDay, double electricCapacity) {
        super(id, brand, model, year, pricePerDay);
        this.electricCapacity = electricCapacity;
    }

    @Override
    public double calculateRentalFee(int days) {
        
        return getpricePerDay() * days;
    }

    public double getElectricCapacity() {
        return electricCapacity;
    }
}

