public class Main {
    public static void main(String[] args) {
        CarInventory inventory = new CarInventory();
        inventory.loadFromCSV("C:\\Users\\Administrator\\car rental system\\car_inventory.csv");

        System.out.println("Available cars:");
        for (Car car : inventory.getAvailableCars()) {
            System.out.println(car.getBrand() + " " + car.getModel() + " - $" + car.getpricePerDay() + "/day");
        }
    }
}
