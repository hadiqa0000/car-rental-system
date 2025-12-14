import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CarInventory inventory;
    private static LoginManager loginManager;
    private static Scanner scanner;
    private static Customer currentCustomer;
    
    public static void main(String[] args) {
        inventory = new CarInventory();
        inventory.loadFromCSV("car_inventory.csv");
        
        loginManager = new LoginManager("customers.csv");
        scanner = new Scanner(System.in);
        
        showMainMenu();
        
        scanner.close();
    }
    
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Car Rental System ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Browse Available Cars");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        browseCars();
                        break;
                    case 4:
                        System.out.println("Thank you for using our system!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }
    
    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        currentCustomer = loginManager.login(email, password);
        if (currentCustomer != null) {
            System.out.println("Login successful! Welcome " + currentCustomer.getName());
            showCustomerMenu();
        } else {
            System.out.println("Invalid email or password!");
        }
    }
    
    private static void register() {
        System.out.println("\n=== Registration ===");
        
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Password (min 6 characters): ");
        String password = scanner.nextLine();
        
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        
        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters!");
            return;
        }
        
        if (loginManager.register(name, email, password)) {
            System.out.println("Registration successful! You can now login.");
        }
    }
    
    private static void browseCars() {
        System.out.println("\n=== Available Cars ===");
        List<Car> availableCars = inventory.getAvailableCars();
        
        if (availableCars.isEmpty()) {
            System.out.println("No cars available at the moment.");
            return;
        }
        
        System.out.printf("%-5s %-15s %-15s %-8s %-12s %-10s\n", 
            "ID", "Brand", "Model", "Year", "Type", "Price/Day");
        System.out.println("-".repeat(75));
        
        for (Car car : availableCars) {
            String type = (car instanceof ElectricCar) ? "Electric" : "Gas";
            System.out.printf("%-5d %-15s %-15s %-8d %-12s $%-9.2f\n",
                car.id(), car.getBrand(), car.getModel(), car.getYear(),
                type, car.getpricePerDay());
        }
    }
    
    private static void showCustomerMenu() {
        while (currentCustomer != null) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Search & Rent a Car");
            System.out.println("2. View My Rentals");
            System.out.println("3. Return a Car");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        searchAndRentCar();
                        break;
                    case 2:
                        // yet the implement the view rental option
                        System.out.println("Feature coming soon!");
                        break;
                    case 3:
                        // yet to write the return car method 
                        System.out.println("Feature coming soon!");
                        break;
                    case 4:
                        currentCustomer = null;
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }
    
    private static void searchAndRentCar() {
        System.out.println("\n=== Search Cars ===");
        
        System.out.print("Brand (or leave blank): ");
        String brand = scanner.nextLine();
        
        System.out.print("Model (or leave blank): ");
        String model = scanner.nextLine();
        
        System.out.print("Min Year (0 to skip): ");
        int minYear = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Max Price (0 to skip): ");
        double maxPrice = Double.parseDouble(scanner.nextLine());
        
        List<Car> results = inventory.searchCars(
            minYear == 0 ? null : minYear,
            model.isEmpty() ? null : model,
            null,  
            maxPrice == 0 ? null : maxPrice
        );
        
        if (results.isEmpty()) {
            System.out.println("No cars found matching your criteria.");
            return;
        }
        
        System.out.println("\n=== Search Results ===");
        for (int i = 0; i < results.size(); i++) {
            Car car = results.get(i);
            System.out.printf("%d. %s %s (%d) - $%.2f/day\n",
                i + 1, car.getBrand(), car.getModel(), 
                car.getYear(), car.getpricePerDay());
        }
        
        System.out.print("\nSelect car number to rent (0 to cancel): ");
        int selection = Integer.parseInt(scanner.nextLine());
        
        if (selection > 0 && selection <= results.size()) {
            Car selectedCar = results.get(selection - 1);
            rentCar(selectedCar);
        }
    }
    
    private static void rentCar(Car car) {
        System.out.println("\n=== Rent " + car.getBrand() + " " + car.getModel() + " ===");
        
        System.out.print("Start Date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        
        System.out.print("End Date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        
        // Create rental
        Rental rental = new Rental(currentCustomer, car, startDate, endDate);
        
        System.out.printf("\n=== Rental Summary ===\n");
        System.out.println("Car: " + car.getBrand() + " " + car.getModel());
        System.out.println("Rental Period: " + startDate + " to " + endDate);
        System.out.println("Total Cost: $" + rental.calculateRentalFee());
        
        System.out.print("\nConfirm rental (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            // im trying to actually rent the car here
            if (car.rent(currentCustomer, (int)java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate))) {
                System.out.println("Car rented successfully!");
                // i have yet to save rental to file
            } else {
                System.out.println("Car is no longer available!");
            }
        } else {
            System.out.println("Rental cancelled.");
        }
    }
}