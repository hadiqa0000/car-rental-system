import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rentalmanager {
    private List<Rental> activeRentals = new ArrayList<>();
    private List<Rental> rentalHistory = new ArrayList<>();
    private String rentalsFilePath;

    public void RentalManager(String filePath) {
        this.rentalsFilePath = filePath;
        loadRentals();
    }

    public void loadRentals() {
        File file = new File(rentalsFilePath);
        if(!file.exists()){
            try{

            
            file.createNewFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.println("rental_id,customer_id,customer_name,car_id,car_brand,car_model,start_date,end_date,total_fee,is_active");
            }
            catch (IOException e) {
                System.err.println("Error creating rentals file: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rentalsFilePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 10) continue;
                
                String rentalId = parts[0];
                String customerId = parts[1];
                String customerName = parts[2];
                int carId = Integer.parseInt(parts[3]);
                String carBrand = parts[4];
                String carModel = parts[5];
                LocalDate startDate = LocalDate.parse(parts[6]);
                LocalDate endDate = LocalDate.parse(parts[7]);
                double totalFee = Double.parseDouble(parts[8]);
                boolean isActive = Boolean.parseBoolean(parts[9]);
                Customer customer = new Customer(customerId, customerName, "", "");
                Car car = new GasCar(carId, carBrand, carModel, 2020, totalFee/(getDaysBetween(startDate, endDate)), "Gasoline");
                Rental rental = new Rental(rentalId, customer, car, startDate, endDate, totalFee, isActive);
                
                if(isActive){
                    activeRentals.add(rental);
                }
                else{
                    rentalHistory.add(rental);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading rentals: " + e.getMessage());
        }
    

    }
    }

    private long getDaysBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
    }

public Rental createRental(Customer customer, Car car, LocalDate startDate, LocalDate endDate) {
    String rentalId = "RENT" + System.currentTimeMillis();
    double totalFee = car.calculateRentalFee((int)getDaysBetween(startDate, endDate));
    Rental rental = new Rental(rentalId, customer, car, startDate, endDate, totalFee, true);
        activeRentals.add(rental);

        



    }
