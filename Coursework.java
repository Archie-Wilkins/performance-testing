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
    //args [3] = n for the customers of the most recent n orders
    public static void main(String[] args) {
        // add validation of args -- please do not change the orderings of these
        Coursework cw = new Coursework(args[0]); // add arguments if needed
        System.out.println(cw.getCustomer(Integer.parseInt(args[1])));
        System.out.println();
        System.out.println(cw.getCountOfCustomersWhoLiveIn(args[2]));
        System.out.println();
        String[] customers = cw.getCustomersForMostRecentOrders(Integer.parseInt(args[3]));
        for (int i = 0; i < Integer.parseInt(args[3]); i++) {
            System.out.println(customers[i]+",");
        }
        System.out.println();
    }

//    Your application will provide a method to return details of a customer chosen via the ID
    public String getCustomer(int id){
        String customer = ""+id;
        //        your code
        return customer;
    }

//    Your application will provide a method to return the number of customers that live in a specific city c
    public int getCountOfCustomersWhoLiveIn(String c){
        int count = 0;
        //        your code
        return count;
    }

//    Your application will provide a method to return the n customers that most recently made an order
    public String[] getCustomersForMostRecentOrders(int n){
        String[] customers = new String[n];
        //        your code
        return customers;
    }

}
