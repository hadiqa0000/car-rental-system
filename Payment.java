public class Payment {
    private double amount;
    private  boolean ispaid;

    public Payment(double amount){
        this.amount = amount;
        this.ispaid = false;


    }

    public void setPaid(boolean paid){
        this.ispaid = true;


    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }
}
}
