
import java.time.LocalDate;

public class Rental {
    private Customer customer;
    private Car car;
    private LocalDate starDate;
    private LocalDate endDate;
    private Payment payment;
    private boolean isActive;


public Rental(Customer customer, Car car, LocalDate start, LocalDate end) {
    this.customer = customer;
    this.car = car;
    this.starDate = start;
    this.endDate = end;
    this.payment = new Payment(calculateRentalFee());
    car.setAvailable(false);



}


public double calculateRentalFee() {
    long days = ChronoUnit.DAYS.between(starDate, endDate) + 1;
    return car.calculateRentalFee() * days; 
}


public void returnCar() {
    if (!isActive) {
        System.out.println("Rental already completed.");
        return;
    }
    isActive = false;
    car.setAvailable(true); 
    payment.setPaid(true); 
    System.out.println("Car returned successfully. Total fee: $" + payment.getAmount());
}

public Customer getCustomer() { return customer; }
    public Car getCar() { return car; }
    public LocalDate getStartDate() { return starDate; }
    public LocalDate getEndDate() { return endDate; }
    public Payment getPayment() { return payment; }
    public boolean isActive() { return isActive; }
}
