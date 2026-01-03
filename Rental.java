
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private String rentalID;
    private Customer customer;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private Payment payment;
    private boolean isActive;

    public Rental(String rentalID, Customer customer, Car car,
                  LocalDate start, LocalDate end) {
        this.rentalID = rentalID;
        this.customer = customer;
        this.car = car;
        this.startDate = start;
        this.endDate = end;
        this.payment = new Payment(calculateRentalFee());
        this.isActive = true;
    }



    public double calculateRentalFee() {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return car.calculateRentalFee((int) days);
    }

    public void completeRental() {
        if(!isActive) return;
        isActive = false;
    }

    public void markPaid() {
       if(isActive){
        System.out.println("cannot pay for an active rental");
        return;

       }
       payment.setPaid(true);
    }

    public boolean isPaid() {
        return payment.isPaid();
    }

    public boolean isActive() {
        return isActive;
    }

    public String getRentalID() {
        return rentalID;
    }


    //prints a human readable summary, just returns a string
    @Override
public String toString() {
    String status = isActive ? "Active" : "Completed";
    String paymentStatus = payment.isPaid() ? "Paid" : "Pending";

    return String.format(
        "Rental ID: %s | Car: %s %s | %s → %s | Fee: $%.2f | %s | %s",
        rentalID,
        car.getBrand(),
        car.getModel(),
        startDate,
        endDate,
        payment.getAmount(),
        status,
        paymentStatus
    );
}

public String toCSV() {
    return String.join(",",
        rentalID,
        customer.getId(),
        String.valueOf(car.id()),
        startDate.toString(),
        endDate.toString(),
        String.valueOf(isActive)
    );
}

public static Rental fromCSV(
        String[] parts,
        Customer customer,
        Car car) {
    Rental r = new Rental(
        parts[0],
        customer,
        car,
        LocalDate.parse(parts[3]),
        LocalDate.parse(parts[4])
    );

    if (!Boolean.parseBoolean(parts[5])) {
        r.completeRental();
    }

    return r;
}
public Car getCar(){
    return car;
}
}
