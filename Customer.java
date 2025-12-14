public class Customer {
    private String id;
    private String name;
    private String email;
    private String password;

    public Customer(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean checkPassword(String pass) { return password.equals(pass); }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
