/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class MyBenchmark {

    String filePath = "src/main/java/org/sample/data.csv";

    @Param({"2998","2999"})
    public int customerID;

    @Param({"Didcot","Tenbury Wells","Knares"})
    public String cityData;


    ArrayList<Customer> customersLinkedList = fileReadSpeedLinkedList();
    HashMap<Integer, Customer> customerHashMap = fileReadSpeedHashMap();





    //@Setup
    //@Benchmark
    public HashMap<Integer, Customer> fileReadSpeedHashMap(){
        HashMap<Integer, Customer> customersDict = new HashMap<>();

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //Reads first line and effectively allows us to skip it as we dont want it in the list
            reader.readLine();
            String line=null;

            while((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                Customer customer  = new Customer(Integer.parseInt(lineData[0]),lineData[1],lineData[2],lineData[3]);
                customersDict.put(customer.id,customer);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

       return customersDict;
    }

    //@Setup
    //@Benchmark
    public ArrayList<Customer> fileReadSpeedLinkedList(){
        ArrayList<Customer> customerList = new ArrayList<Customer>();

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //Reads first line and effectively allows us to skip it as we dont want it in the list
            reader.readLine();
            String line=null;

            while((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                Customer customer  = new Customer(Integer.parseInt(lineData[0]),lineData[1],lineData[2],lineData[3]);
                customerList.add(customer);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    //@Benchmark
    public String getCustomerLinkedList(){
        //ArrayList<Customer> customers = fileReadSpeedLinkedList();
        for(Customer customer : customersLinkedList){
            if(customer.id == customerID){
                String customerInfo = "ID: " + customer.id + " Name: " + customer.name + " Email" + customer.email + " City: " + customer.city;
                return customerInfo;
            }
        }
        return "Invalid Customer";
    }


    //@Benchmark
    public String getCustomerHashMap(){
        //HashMap<Integer, Customer> customers = fileReadSpeedHashMap();
        Customer targetCustomer = customerHashMap.get(customerID);
        if(targetCustomer != null) {
            String customerInfo = "ID: " + targetCustomer.id + " Name: " + targetCustomer.name + " Email" + targetCustomer.email + " City: " + targetCustomer.city;
            return customerInfo;
        }else{
            return "Invalid Customer";
        }
    }

    @Benchmark
    public int getCountOfCustomersWhoLiveInLinkedList(){
        int count = 0;
        //ArrayList<Customer> customersLinkedList = this.readFileArrayList();
        for(Customer customer : customersLinkedList){
            if(customer.city.equals(cityData)){
                count++;
            }
        }
        return count;
    }

    @Benchmark
    public int getCountOfCustomersWhoLiveInHashMap(){
        int count = 0;
        //HashMap<Integer, Customer> customerHashMap = this.readFileHashMap();
        for(Map.Entry<Integer, Customer> customerEntry : customerHashMap.entrySet()){
            if(customerEntry.getValue().city.equals(cityData)){
                count++;
            }
        }
        return count;
    }




}
class Customer {

    int id;
    String name;
    String email;
    String city;

    public Customer(int id, String name, String email, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
    }
}
