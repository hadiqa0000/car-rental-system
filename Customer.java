import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Customer {

    private String id;
    private String name;
    private String email;
    private String password;

    private List<Rental> activeRentals = new ArrayList<>();
    private List<Rental> pastRentals = new ArrayList<>();

    public Customer(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }



    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

  

    public void addRental(Rental rental) {
        System.out.println("addRental() CALLED");

        System.out.println("Customer instance: " + System.identityHashCode(this));

        activeRentals.add(rental);
    }

    public void returnCar(String rentalID, Scanner scanner) {
    Iterator<Rental> iterator = activeRentals.iterator();

    while (iterator.hasNext()) {
        Rental rental = iterator.next();

        if (rental.getRentalID().equals(rentalID)) {

            System.out.printf(
                "Amount due: $%.2f%n",
                rental.calculateRentalFee()
            );

            while (true) {
                System.out.print("Pay now? (yes/no): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("yes")) {
                    rental.markPaid();
                    rental.completeRental();
                    iterator.remove();
                    pastRentals.add(rental);
                    System.out.println("Payment successful. Car returned.");
                    return;
                }

                if (input.equals("no")) {
                    System.out.println("Return cancelled. Payment required.");
                    return;
                }

                System.out.println("Type yes or no.");
            }
        }
    }

    System.out.println("No active rental found with that ID.");
}


    public void viewMyRentals() {

        
        if (!activeRentals.isEmpty()) {
            System.out.println("=== ACTIVE RENTALS ===");
            for (Rental r : activeRentals) {
                System.out.println(r);
            }
        }
        if (!pastRentals.isEmpty()) {
            System.out.println("\n=== PAST RENTALS ===");
            for (Rental r : pastRentals) {
                System.out.println(r);
            }
        }
        if (activeRentals.isEmpty() && pastRentals.isEmpty()) {
            System.out.println("You have no rentals.");
            return;
        }
        System.out.println("Customer instance: " + System.identityHashCode(this));




    }
}
