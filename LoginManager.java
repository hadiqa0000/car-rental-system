import java.io.*;
import java.util.*;

public class LoginManager {
    private Map<String, Customer> customers = new HashMap<>();
    private String customerFilePath;
    
    public LoginManager(String filePath) {
        this.customerFilePath = filePath;
        loadCustomers();
    }
    
    public void loadCustomers() {
        File file = new File(customerFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Write header if file is new
                try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                    writer.println("id,name,email,password");
                }
            } catch (IOException e) {
                System.err.println("Error creating customer file: " + e.getMessage());
            }
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(customerFilePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String password = parts[3];
                
                Customer customer = new Customer(id, name, email, password);
                customers.put(email, customer);
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }
    
    public Customer login(String email, String password) {
        Customer customer = customers.get(email);
        if (customer != null && customer.checkPassword(password)) {
            return customer;
        }
        return null;
    }

    public Customer getCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
    
    
    public boolean register(String name, String email, String password) {

        if (customers.containsKey(email)) {
            System.out.println("Email already registered!");
            return false;
        }
        
        
        String id = "CUST" + (customers.size() + 1);
        
        
        Customer customer = new Customer(id, name, email, password);
        

        customers.put(email, customer);

        
        try (PrintWriter writer = new PrintWriter(new FileWriter(customerFilePath, true))) {
            writer.println(id + "," + name + "," + email + "," + password);
            System.out.println("Registration successful! Your ID is: " + id);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving customer: " + e.getMessage());

            customers.remove(email);
            return false;
        }
    }
    
    public Map<String, Customer> getCustomers() {
        return new HashMap<>(customers);
    }


    public Customer getCustomerByEmail(String email) {
        return customers.get(email);
    }
    

    private static void login() {
        System.out.println("\n=== Login ===");
        
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            
    
            if (email.contains("@") && email.contains(".")) {
                break;
            } else {
                System.out.println("Please enter a valid email address");
            }
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        currentCustomer = loginManager.login(email, password);
        if (currentCustomer != null) {
            System.out.println("Login successful! Welcome " + currentCustomer.getName());
            showCustomerMenu();
        } else {
            System.out.println("Invalid email or password!");
        }
    }
}

