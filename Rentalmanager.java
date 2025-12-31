import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rentalmanager {
    private LoginManager loginManager;
    private CarInventory inventory;

    private final List<Rental> rentals = new ArrayList<>();
    private final String filePath;

    public  Rentalmanager(LoginManager loginmanager, CarInventory inventory, String filePath) {
        this.loginManager = loginmanager;
        this.inventory = inventory;
        this.filePath = filePath;

    }

    public void load() {
        File file = new File(filePath);
        if (!file.exists()) return;
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length != 6) continue;
    
                Customer c = loginManager.getCustomerById(p[1]);
                Car car = inventory.getCarById(Integer.parseInt(p[2]));
    
                if (c == null || car == null) continue;
    
                rentals.add(Rental.fromCSV(p, c, car));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Rental r : rentals) {
                pw.println(r.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRental(Rental r) {
        rentals.add(r);
        save();
    }
    public void completeRental(String rentalId) {
        for (Rental r : rentals) {
            if (r.getRentalID().equals(rentalId)) {
                r.completeRental();
                save();
                return;
            }
        }
    }
    
    

    public List<Rental> getRentalsForCustomer(String customerId) {
        List<Rental> list = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getCustomer().getId().equals(customerId)) {
                list.add(r);
            }
        }
        return list;
    }

    public Rental createRental(Customer customer, Car car,LocalDate start, LocalDate end) {

    if (!car.isAvailable()) return null;

    String rentalId = "R" + (rentals.size() + 1);

    Rental rental = new Rental(
            rentalId,
            customer,
            car,
            start,
            end
    );

    car.setAvailable(false);
    rentals.add(rental);
    save();

    return rental;
}

}
