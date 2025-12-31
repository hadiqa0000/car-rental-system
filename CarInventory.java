import java.io.*;
import java.util.*;

public class CarInventory {
    private List<Car> cars = new ArrayList<>();

    public void loadFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String brand = parts[1];
                String model = parts[2];
                int year = Integer.parseInt(parts[3]);
                double pricePerDay = Double.parseDouble(parts[4]);
                boolean available = Boolean.parseBoolean(parts[5]);
                String fuelType = parts[6];

                Car car;
                if (fuelType.equalsIgnoreCase("Gas")) {
                    car = new GasCar(id, brand, model, year, pricePerDay, fuelType);
                } else {
                    double electricCapacity = Double.parseDouble(parts[7]);
                    car = new ElectricCar(id, brand, model, year, pricePerDay, electricCapacity);
                }

                if (!available) {
                    car.returnVehicle();
                }

                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Car getCarById(int id) {
        for (Car car : cars) {
            if (car.id() == id) {
                return car;
            }
        }
        return null;
    }
    

    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) availableCars.add(car);
        }
        return availableCars;
    }

    public List<Car> searchCars(
        Integer minYear,
        String model,
        String brand,
        Double maxPrice
) {
        List<Car> results = new ArrayList<>();
        for (Car car : cars) {
            if (!car.isAvailable()) continue;
            if (minYear != null && car.getYear() < minYear) continue;
            if (model != null && !car.getModel().equalsIgnoreCase(model)) continue;
            if (brand != null && !car.getBrand().equalsIgnoreCase(brand)) continue;
            if (maxPrice != null && car.getpricePerDay() > maxPrice) continue;
            results.add(car);
        }
        return results;
    }
}



