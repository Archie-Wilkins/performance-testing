import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Coursework {

    // Please do not hardcode the filename into the program! It's ok to change the program to prompt the user
    // for the filename, but when I download assignments from LC the filenames get changed so hardcoding can cause problems.
    public Coursework(String filename) {
        System.out.println(filename);
        // your code here if needed.
    }

    //A main method that will take command line arguments to call the given methods.
    //args [0] = data file filename
    //args [1] = customer ID
    //args [2] = city c -- as a String e.g. "Aylesbury"
    //args [3] = n for the Customer of the most recent n orders
    public static void main(String[] args) throws ParseException {
        // add validation of args -- please do not change the orderings of these

        String file = args[0];

        Coursework cw = new Coursework(args[0]); // add arguments if needed
        System.out.println("Get Customer Linked List Answer:");
        System.out.println(cw.getCustomerLinkedList(Integer.parseInt(args[1])));
        System.out.println("Get Customer Hash Map Answer:");
        System.out.println(cw.getCustomerHashMap(Integer.parseInt(args[1])));
        System.out.println();
        System.out.println("Get City Count Linked List Answer:");
        System.out.println(cw.getCountOfCustomersWhoLiveInLinkedList(args[2]));
        System.out.println("Get City Count Hash Map Answer:");
        System.out.println(cw.getCountOfCustomersWhoLiveInHashMap(args[2]));
        System.out.println();
        System.out.println("Most Recent Customer Purchases:");
        String[] customers = cw.getCustomersForMostRecentOrdersFunctionalProgramming(Integer.parseInt(args[3]));
        for (int i = 0; i < Integer.parseInt(args[3]); i++) {
            System.out.println(customers[i]+",");
        }
        System.out.println();
        System.out.println("Version 2");
        String[] customers2 = cw.getCustomersForMostRecentOrdersInsertionSort(Integer.parseInt(args[3]));
        for (int i = 0; i < Integer.parseInt(args[3]); i++) {
            System.out.println(customers2[i]+",");
        }
        System.out.println();
    }


    public ArrayList<Customer> readFileArrayList(){
        ArrayList<Customer> customerList = new ArrayList<Customer>();

        try {
            File file = new File("data.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //Reads first line and effectively allows us to skip it as we dont want it in the list
            reader.readLine();
            String line=null;

            while((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                Customer customer  = new Customer(Integer.parseInt(lineData[0]),lineData[1],lineData[2],lineData[3], lineData[4]);
                customerList.add(customer);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    public HashMap<Integer, Customer> readFileHashMap(){
        HashMap<Integer, Customer> customersDict = new HashMap<>();

        try {
            File file = new File("data.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //Reads first line and effectively allows us to skip it as we dont want it in the list
            reader.readLine();
            String line=null;

            while((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                Customer customer  = new Customer(Integer.parseInt(lineData[0]),lineData[1],lineData[2],lineData[3],lineData[4]);
                customersDict.put(customer.id,customer);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return customersDict;
    }

    //Could add second read


//    Your application will provide a method to return details of a customer chosen via the ID

    //Simple implementation of get customer
    public String getCustomerLinkedList(int id){

        ArrayList<Customer> customers = readFileArrayList();
        for(Customer customer : customers){
            if(customer.id == id){
                String customerInfo = "ID: " + customer.id + " Name: " + customer.name + " Email" + customer.email + " City: " + customer.city;
                return customerInfo;
        }
        }
        return "Invalid Customer";
    }

    //More advanced implementation
    public String getCustomerHashMap(int id){

        HashMap<Integer, Customer> customers = readFileHashMap();
        Customer targetCustomer = customers.get(id);
        if(targetCustomer != null) {
            String customerInfo = "ID: " + targetCustomer.id + " Name: " + targetCustomer.name + " Email" + targetCustomer.email + " City: " + targetCustomer.city;
            return customerInfo;
        }else{
            return "Invalid Customer";
        }
    }

//    Your application will provide a method to return the number of Customer that live in a specific city c
    public int getCountOfCustomersWhoLiveInLinkedList(String c){
        int count = 0;
        ArrayList<Customer> customerLinkedList = this.readFileArrayList();
        for(Customer customer : customerLinkedList){
            if(customer.city.equals(c)){
                count++;
            }
        }
        return count;
    }

    public int getCountOfCustomersWhoLiveInHashMap(String c){
        int count = 0;
        HashMap<Integer, Customer> customerHashMap = this.readFileHashMap();
        for(Map.Entry<Integer, Customer> customerEntry : customerHashMap.entrySet()){
            if(customerEntry.getValue().city.equals(c)){
                count++;
            }
        }
        return count;
    }

    public String[] getCustomersForMostRecentOrdersFunctionalProgramming(int n){
        String[] customers = new String[n];
        ArrayList<Customer> customerList = this.readFileArrayList();

        Comparator<Customer> compareByDate = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return dateStringConverter(o2.date).compareTo(dateStringConverter(o1.date));
            }
        };

        Collections.sort(customerList, compareByDate);
        int i = 0;
        while(i < n){
            customers[i] = customerList.get(i).toString();
            i++;
        }
        return customers;
    }

    private LocalDate dateStringConverter(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate customerPurchase = LocalDate.parse(stringDate, formatter);
        return customerPurchase;
    }

    //Date comparer2 modified from
    //https://stackoverflow.com/questions/25963720/how-to-compare-two-string-dates-in-java#:~:text=The%20simplest%20and%20safest%20way,date%20object%20to%20compare%20them.
    private boolean dateComparer(String stringDateOne, String stringDateTwo) throws ParseException {
        //BUG IN CODE SOMEWHERE HERE
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse(stringDateOne);
        Date end = sdf.parse(stringDateTwo);
        boolean res = start.before(end);
        return res;
    }

    //Get reference for what this is adapted from
    public String[] getCustomersForMostRecentOrdersInsertionSort(int n) throws ParseException {
        String[] customers = new String[n];
        ArrayList<Customer> customerList = readFileArrayList();

        for (int index = 1; index < customerList.size(); ++index) {
            Customer key = customerList.get(index);
            int j = index - 1;
            while (j >= 0 && dateComparer(key.date, customerList.get(j).date)) {
                customerList.set(j + 1, customerList.get(j));
                j = j - 1;
            }
            customerList.set(j + 1, key);
        }

        int i = 0;
        while (i < n) {
            customers[i] = customerList.get(customerList.size() - i - 1).toString();
            i++;
        }
        return customers;
    }


}

class Customer {

    int id;
    String name;
    String email;
    String city;
    String date;

    public Customer(int id, String name, String email, String city, String date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
