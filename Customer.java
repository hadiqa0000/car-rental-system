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
    
    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    
   
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}