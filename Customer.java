import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        activeRentals.add(rental);
    }

    public void returnCar(String rentalID) {
        Iterator<Rental> iterator = activeRentals.iterator();

        while (iterator.hasNext()) {
            Rental rental = iterator.next();

            if (rental.getRentalID().equals(rentalID)) {
                rental.completeRental();
                iterator.remove();
                pastRentals.add(rental);

                System.out.println("Car returned successfully.");
                return;
            }
        }

        System.out.println("No active rental found with that ID.");
    }

    public void viewMyRentals() {
        if (activeRentals.isEmpty() && pastRentals.isEmpty()) {
            System.out.println("You have no rentals.");
            return;
        }

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
    }
}
