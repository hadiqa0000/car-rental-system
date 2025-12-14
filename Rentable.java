
public interface Rentable {
    boolean rent(Customer customer, int days);
    boolean returnVehicle();
    double calculateRentalFee(int days);
    boolean isAvailable();
}