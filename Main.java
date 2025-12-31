import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {


        private static CarInventory inventory;
        private static LoginManager loginManager;
        private static Scanner scanner;
        private static Customer currentCustomer;
        private static Rentalmanager rentalManager;
    

    public static void main(String[] args) {


        inventory = new CarInventory();
        inventory.loadFromCSV("car_inventory.csv");
    
        loginManager = new LoginManager("customers.csv");
        scanner = new Scanner(System.in);
    
        rentalManager =
                new Rentalmanager(loginManager, inventory, "rentals.csv");
    
        showMainMenu();
        scanner.close();
    }
    

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Car Rental System ===");
            System.out.println("\nONLY ENTER THE NUMBER FROM 1-4");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Browse Available Cars (No login required)");
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
                        browseCarsByBrand();
                        break;
                    case 4:
                        System.out.println("Thank you for using our system!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("only integers are valid!");
            }
        }
    }

    private static void browseCarsByBrand() {
        List<Car> availableCars = inventory.getAvailableCars();

        if (availableCars.isEmpty()) {
            System.out.println("\nNo cars available at the moment.");
            return;
        }

        Set<String> brands = new TreeSet<>();
        Map<String, List<Car>> carsByBrand = new HashMap<>();

        for (Car car : availableCars) {
            String brand = car.getBrand();
            brands.add(brand);
            carsByBrand.computeIfAbsent(brand, k -> new ArrayList<>()).add(car);
        }

        System.out.println("\n=== Available Brands ===");
        String[] brandArray = brands.toArray(new String[0]);

        for (int i = 0; i < brandArray.length; i++) {
            int count = carsByBrand.get(brandArray[i]).size();
            System.out.printf("%d. %s (%d car%s available)\n",
                    i + 1, brandArray[i], count, count == 1 ? "" : "s");
        }
        System.out.println("0. Back to Main Menu");

        System.out.print("\nSelect brand number: ");

        try {
            int brandChoice = Integer.parseInt(scanner.nextLine());

            if (brandChoice == 0) {
                return;
            }

            if (brandChoice > 0 && brandChoice <= brandArray.length) {
                String selectedBrand = brandArray[brandChoice - 1];
                displayCarsByBrand(selectedBrand, carsByBrand.get(selectedBrand), false);
            } else {
                System.out.println("Invalid selection!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number!");
        }
    }

    private static void displayCarsByBrand(String brand, List<Car> cars, boolean showRentOptions) {
        System.out.println("\n=== " + brand + " Cars ===");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-15s %-15s %-8s %-12s %-10s %s\n",
                "ID", "Brand", "Model", "Year", "Type", "Price/Day", "Details");
        System.out.println("-".repeat(80));

        for (Car car : cars) {
            String type = (car instanceof ElectricCar) ? "Electric" : "Gas";
            String details = "";

            if (car instanceof ElectricCar) {
                details = "Battery: " + ((ElectricCar) car).getElectricCapacity() + " kWh";
            } else if (car instanceof GasCar) {
                details = "Fuel: " + ((GasCar) car).getFuelType();
            }

            System.out.printf("%-5d %-15s %-15s %-8d %-12s $%-9.2f %s\n",
                    car.id(), car.getBrand(), car.getModel(), car.getYear(),
                    type, car.getpricePerDay(), details);
        }

        System.out.println("=".repeat(80));

        if (showRentOptions && currentCustomer != null) {

            System.out.println("\nOptions:");
            System.out.println("1. Rent a car");
            System.out.println("2. Back to brands");
            System.out.println("3. Back to main menu");
            System.out.print("Choose: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        rentCarFromList(cars);
                        break;
                    case 2:
                        browseCarsByBrand();
                        break;
                    case 3:

                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        } else {

            if (!showRentOptions) {
                System.out.println("\n[Login to rent a car]");
            }
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    private static boolean checkfullname(String name) {

        if (name == null)
            return false;

        name = name.trim();
        if (name.isEmpty())
            return false;

        int firstSpace = -1;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                firstSpace = i;
                break;
            }
        }

        if (firstSpace == -1) {
            return false;
        }

        String firstName = name.substring(0, firstSpace);

        String lastName = "";
        int lastCharIndex = name.length() - 1;
        while (lastCharIndex > firstSpace && name.charAt(lastCharIndex) == ' ') {
            lastCharIndex--;
        }

        if (lastCharIndex > firstSpace) {
            lastName = name.substring(firstSpace + 1, lastCharIndex + 1);
        }

        if (firstName.length() < 2 || lastName.length() < 2) {
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        email = email.trim().toLowerCase();

        String[] allowedDomains = {
                "@gmail.com",
                "@yahoo.com",
                "@outlook.com",
                "@hotmail.com"
        };

        boolean validDomain = false;
        for (String domain : allowedDomains) {
            if (email.endsWith(domain)) {
                validDomain = true;
                break;
            }
        }

        if (!validDomain) {
            System.out.println("Allowed domains: gmail.com, yahoo.com, outlook.com, hotmail.com");
            return false;
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return false;
        }

        String localPart = email.substring(0, atIndex);
        if (localPart.isEmpty()) {
            return false;
        }

        if (!localPart.matches("^[a-zA-Z0-9._%+-]+$")) {
            System.out.println(
                    "Email can only contain letters upper and lowercase, numbers, dots, underscores, plus, and hyphens");
            return false;
        }

        if (localPart.contains("..")) {
            System.out.println("Email cannot have consecutive dots");
            return false;
        }

        if (localPart.startsWith(".") || localPart.endsWith(".")) {
            System.out.println("Email cannot start or end with a dot");
            return false;
        }

        return true;
    }

    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");

        String email = scanner.nextLine();

        isValidEmail(email);
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
        if (!checkfullname(name)) {
            System.out.print("full name is mandatory!");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.print("cannot register with an invalid email");
            return;

        }

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
            currentCustomer = loginManager.getCustomerByEmail(email);
            System.out.println("Registration successful! Welcome " + currentCustomer.getName());
            showCustomerMenu();
        }
        
    }

    private static void showCustomerMenu() {
        while (currentCustomer != null) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Search & Rent a Car");
            System.out.println("2. Browse Cars by Brand");
            System.out.println("3. View My Rentals");
            System.out.println("4. Return a Car");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        searchAndRentCar();
                        break;
                    case 2:
                        browseCarsForCustomer();
                        break;
                    case 3:
                        currentCustomer.viewMyRentals();
                        break;
                    case 4:
                        returnCarMenu();
                        break;
                    case 5:
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

    private static void browseCarsForCustomer() {
        List<Car> availableCars = inventory.getAvailableCars();

        if (availableCars.isEmpty()) {
            System.out.println("\nNo cars available at the moment.");
            return;
        }

        Set<String> brands = new TreeSet<>();
        Map<String, List<Car>> carsByBrand = new HashMap<>();

        for (Car car : availableCars) {
            String brand = car.getBrand();
            brands.add(brand);
            carsByBrand.computeIfAbsent(brand, k -> new ArrayList<>()).add(car);
        }

        System.out.println("\n=== Available Brands ===");
        String[] brandArray = brands.toArray(new String[0]);

        for (int i = 0; i < brandArray.length; i++) {
            int count = carsByBrand.get(brandArray[i]).size();
            System.out.printf("%d. %s (%d car%s available)\n",
                    i + 1, brandArray[i], count, count == 1 ? "" : "s");
        }
        System.out.println("0. Back to Customer Menu");

        System.out.print("\nSelect brand number: ");

        try {
            int brandChoice = Integer.parseInt(scanner.nextLine());

            if (brandChoice == 0) {
                return;
            }

            if (brandChoice > 0 && brandChoice <= brandArray.length) {
                String selectedBrand = brandArray[brandChoice - 1];
                displayCarsByBrand(selectedBrand, carsByBrand.get(selectedBrand), true);
            } else {
                System.out.println("Invalid selection!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number!");
        }
    }

    private static void rentCarFromList(List<Car> cars) {
        System.out.print("\nEnter car ID to rent: ");
        try {
            int carId = Integer.parseInt(scanner.nextLine());

            Car selectedCar = null;
            for (Car car : cars) {
                if (car.id() == carId) {
                    selectedCar = car;
                    break;
                }
            }

            if (selectedCar != null) {
                rentCar(selectedCar);
            } else {
                System.out.println("Car ID not found in this brand!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
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
                maxPrice == 0 ? null : maxPrice);

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

        LocalDate startDate;
        LocalDate endDate;

        while (true) {
            System.out.print("Start Date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                startDate = LocalDate.parse(input);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Example: 2026-01-19");
            }
        }

        while (true) {
            System.out.print("End Date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                endDate = LocalDate.parse(input);
                if (endDate.isBefore(startDate)) {
                    System.out.println("End date must be after start date.");
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Example: 2026-02-04");
            }
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double totalFee = car.calculateRentalFee((int) days);

        System.out.println("\n=== Rental Summary ===");
        System.out.println("Car: " + car.getBrand() + " " + car.getModel());
        System.out.println("Rental Period: " + startDate + " to " + endDate + " (" + days + " days)");
        System.out.println("Daily Rate: $" + car.getpricePerDay());
        System.out.println("Total Cost: $" + totalFee);

        while (true) {
            System.out.print("\nConfirm rental (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes")) {

                Rental rental = rentalManager.createRental(currentCustomer,car,startDate,endDate);
            
                if (rental == null) {
                    System.out.println("Car is no longer available!");
                } else {
                    System.out.println("Car rented successfully!");
                }
                break;
            }
    }
}

    private static void returnCarMenu() {
        System.out.println("=== RETURN A CAR ===");
    
        List<Rental> active = rentalManager.getRentalsForCustomer(currentCustomer.getId())
                .stream()
                .filter(r -> r.isActive())
                .toList();
    
        if (active.isEmpty()) {
            System.out.println("No active rentals.");
            return;
        }
    
        for (Rental r : active) {
            System.out.println("Rental ID: " + r.getRentalID()
                    + " | Car: " + r.getCar().getBrand() + " " + r.getCar().getModel()
                    + " | Fee: $" + r.getTotalFee());
        }
    
        System.out.print("Enter Rental ID to return: ");
        String id = scanner.nextLine().trim();
    
        Rental selected = null;
        for (Rental r : active) {
            if (r.getRentalID().equals(id)) {
                selected = r;
                break;
            }
        }
    
        if (selected == null) {
            System.out.println("Invalid rental ID.");
            return;
        }
    
        // PAYMENT FIRST
        System.out.println("Amount due: $" + selected.getTotalFee());
        System.out.print("Confirm payment (yes/no): ");
        if (!scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }
    
      
        rentalManager.completeRental(id);
    
        selected.getCar().setAvailable(true);
        System.out.println("Car returned successfully.");
    }
    
}
