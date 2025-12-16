
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Rental {
    private String rentalID;
    private double totalFee;
    private Customer customer;
    private Car car;
    private LocalDate startDate;  
    private LocalDate endDate;
    private Payment payment;
    private boolean isActive = true;  
    
    public Rental(String rentalID, Customer customer, Car car, LocalDate start, LocalDate end, double totalFee, ) {
        this.customer = customer;
        this.totalFee = totalFee;
        this.rentalID = rentalID;

        this.car = car;
        this.startDate = start;
        this.endDate = end;
        this.payment = new Payment(calculateRentalFee());
        car.setAvailable(false);
    }
    
    public double calculateRentalFee() {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return car.calculateRentalFee((int)days);
    }
    
    public void returnCar() {
        if (!isActive) {
            System.out.println("Rental already completed.");
            return;
        }
        isActive = false;
     
        payment.setPaid(true);
        System.out.println("Car returned successfully. Total fee: $" + payment.getAmount());
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public boolean isActive() {
        return isActive;
    }
    public string getrentalID(){
        return rentalID
    }


    @Override
    public String toString() {
        String status = isActive ? "Active" : "Completed";
        return String.format("Rental ID: %s | Car: %s %s | Period: %s to %s | Fee: $%.2f | Status: %s",
            rentalID, car.getBrand(), car.getModel(), startDate, endDate, payment, status);
    }
    
}