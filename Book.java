/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

/**
 *
 * @author a_com
 */
public class Book {
    private String name; //The name of the Book.
    private double price; //How much a customer pays to purchase the Book
    private double cost; //How much the store pays to purchase the Book from a Vendor
    
    Book(String name, double price, double cost){
        setName(name);
        setPrice(price);
        setCost(cost);
    }
    
    //Getters and Setters
    public String getName(){
        return name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public double getCost(){
        return cost;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public void setCost(double cost){
        this.cost = cost;
    }
}
