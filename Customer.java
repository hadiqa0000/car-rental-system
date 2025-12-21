import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String password;

    private List<Rental> activeRentals = new ArrayList<>();
    private List<Rental> pastRentals = new ArrayList<>();

    public void addRental(Rental rental) {
        activeRentals.add(rental);
    }

    public void completeRental(Rental rental) {
        activeRentals.remove(rental);
        pastRentals.add(rental);
    }

    public List<Rental> getActiveRentals() {
        return activeRentals;
    }

    public List<Rental> getPastRentals() {
        return pastRentals;
    }
    public void viewmyrentals(){

        if(activeRentals.isEmpty() && pastRentals.isEmpty()){
            System.out.println("No rental history found!");
            return;
        }

        if(!activeRentals.isEmpty()){
            System.out.println("=== ACTIVE RENTALS ===");
            for( Rental r : activeRentals ){
                System.out.println(r);
            }
        }

        if(!pastRentals.isEmpty()){
            System.out.println("=== PAST RENTALS ===");
            for( Rental r : pastRentals ){
                System.out.println(r);
            }
        }
        }

    }


