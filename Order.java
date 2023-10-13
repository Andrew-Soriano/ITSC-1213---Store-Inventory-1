/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

import java.util.ArrayList;

/**
 *
 * @author a_com
 */
class Order {
    private String orderName;
    private Vendor vendor;
    private Inventory orderedItems;
    
    Order(String name, Vendor vendor, Inventory items){
        setOrderName(name);
        setVendor(vendor);
        setOrderedItems(items);
    }
    
    //Getters and Setters
    public String getOrderName(){
        return orderName;
    }
    
    public Vendor getVendor(){
        return vendor;
    }
    
    public Inventory getOrderedItems(){
        return orderedItems;
    }
    
    public void setOrderName(String name){
        orderName = name;
    }
    
    public void setVendor(Vendor vendor){
        this.vendor = vendor;
    }
    
    public void setOrderedItems(Inventory items){
        orderedItems = items;
    }
    
    //Calls the totalCost function of the order's inventory
    public double calculateTotalCost(){
        return orderedItems.totalCost();
    }
    
    //Returns a string containing a formatted list of all items in the order
    public String printOrder(){
        String print;
        ArrayList<String> alreadySeen = new ArrayList<>();
        boolean seen = false;
        
        print = "Order Name: ";
        print = print.concat(orderName);
        print = print.concat("\n\tVendor: " + vendor.getName());
        print = print.concat("\n\tItems:\n\t\tCDs:\n");
        
        for(CD var : orderedItems.getCDList()){
            //Determine if we already went over a similar CD
            for(String stVar : alreadySeen){
                if(var.getName().equals(stVar)){
                    seen = true;
                } else{
                    seen = false;
                }
            }
            
            if(!seen){
                print = print.concat("\t\t\t" + var.getName() + " "+ orderedItems.countCDs(var.getName())
                        + " $" + var.getCost() + "\n");
                alreadySeen.add(var.getName());
            }
        }
        
        print = print.concat("\t\tDVDs:\n");
        
        seen = false;
        
        for(DVD var : orderedItems.getDVDList()){
            //Determine if we already went over a similar CD
            for(String stVar : alreadySeen){
                if(var.getName().equals(stVar)){
                    seen = true;
                }
            }
            
            if(!seen){
                print = print.concat("\t\t\t" + var.getName() + " "+ orderedItems.countDVDs(var.getName())
                        + " $" + var.getCost() + "\n");
                seen = false;
                alreadySeen.add(var.getName());
            }
        }
        
        print = print.concat("\t\tBooks:\n");
        
        seen = false;
        
        for(Book var : orderedItems.getBookList()){
            //Determine if we already went over a similar CD
            for(String stVar : alreadySeen){
                if(var.getName().equals(stVar)){
                    seen = true;
                }
            }
            
            if(!seen){
                print = print.concat("\t\t\t" + var.getName() + " "+ orderedItems.countBooks(var.getName())
                        + " $" + var.getCost() + "\n");
                seen = false;
                alreadySeen.add(var.getName());
            }
        }
        
        return print;
    }
}
