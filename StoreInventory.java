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
public class StoreInventory {
    private Inventory currentInventory;
    private Inventory reservedItems;
    private ArrayList<Order> pastOrders;
    private double totalCost;
    private double totalGrossProfit;
    private double totalStoreShrink;
    
    StoreInventory(Inventory items, ArrayList<Order> orders, double costs, double profit, double shrink){
        setCurrentInventory(items);
        setReservedItems(new Inventory());
        setPastOrders(orders);
        setTotalCost(costs);
        setTotalGrossProfit(profit);
        setTotalStoreShrink(shrink);
    }
    
    //Getters and Setters
    public Inventory getCurrentInventory(){
        return currentInventory;
    }
    
    public Inventory getReservedItems(){
        return reservedItems;
    }
    
    public ArrayList<Order> getPastOrders(){
        return pastOrders;
    }
    
    public double getTotalCost(){
        return totalCost;
    }
    
    public double getTotalGrossProfit(){
        return totalGrossProfit;
    }
    
    public double getTotalStoreShrink(){
        return totalStoreShrink;
    }
    
    public void setCurrentInventory(Inventory items){
        currentInventory = items;
    }
    
    public void setReservedItems(Inventory items){
        reservedItems = items;
    }
    
    public void setPastOrders(ArrayList<Order> orders){
        pastOrders = orders;
    }
    
    public void setTotalCost(double cost){
        totalCost = cost;
    }
    
    public void setTotalGrossProfit(double profit){
        totalGrossProfit = profit;
    }
    
    public void setTotalStoreShrink(double shrink){
        totalStoreShrink = shrink;
    }
    
    //Calculates the profit by subtracting our gross from our costs
    public double calculateProfit(){
        return (totalGrossProfit - totalCost);
    }
    
    //Repeats a past order associated with this class by checking its name
    public boolean  repeatOrder(String name){
        int index = -1;
        for(Order var : pastOrders){
            if(var.getOrderName().equals(name)){
                index = pastOrders.indexOf(var);
                break;
            }
        }
        if(index >= 0){
            currentInventory.addItem(pastOrders.get(index).getOrderedItems());
            totalCost += pastOrders.get(index).calculateTotalCost();
            return true;
        }
        else{
            return false;
        }
    }
    
    //Takes an Inventory of items and removes them from the store inventory
    public void removeItems(Inventory items, boolean purchased){
        if(purchased){
            totalGrossProfit +=  items.totalPrice();
            //Remove them from the reserve inventory (items cannot be purchased before first being reserved)
            reservedItems.removeItems(items);
        } else{
            totalStoreShrink += items.totalCost();
            //Remove the items from the current stock (they were not purchased and so won't be reserved
            currentInventory.removeItems(items);
        }
    }
    
    //Removes items from the inventory and adds them to "reserved inventory" without affecting price totals
    public void reserveItems(Inventory reserve){
        //Reserve CDs
        for(CD var : reserve.getCDList()){
            if(currentInventory.getCDList().contains(var)){
                currentInventory.removeCD(var);
                reservedItems.addItem(var);
            }
        }
        //Reserve DVDs
        for(DVD var : reserve.getDVDList()){
            if(currentInventory.getDVDList().contains(var)){
                currentInventory.removeDVD(var);
                reservedItems.addItem(var);
            }
        }
        //Reserve books
        for(Book var : reserve.getBookList()){
            if(currentInventory.getBookList().contains(var)){
                currentInventory.removeBook(var);
                reservedItems.addItem(var);
            }
        }
    }
    
    //iterate through the inventory, determine whatitems are present and in what amounts, and return a formatted string
    public String printContents(){
        String printList;
        String itemName = "DEFAULT";
        
        //Set up the CD list
        printList= "CDs:\n";
        for(CD var1 : currentInventory.getCDList()){
            if(!(var1.getName().equals(itemName))){
                itemName = var1.getName();
                
                printList = printList.concat("\t" + itemName + ": " + currentInventory.countCDs(itemName)+ "\n");
            }
        }
        itemName = "NULL";
        
        //Set up DVD List
        printList = printList.concat("DVDs:\n");
        for(DVD var1 : currentInventory.getDVDList()){
            if(!(var1.getName().equals(itemName))){
                itemName = var1.getName();
                
                printList = printList.concat("\t" + itemName + ": " + currentInventory.countDVDs(itemName) + "\n");
            }
        }
        
        //Set up Book List
        printList = printList.concat("Books:\n");
        for(Book var1 : currentInventory.getBookList()){
            if(!(var1.getName().equals(itemName))){
                itemName = var1.getName();
                
                printList = printList.concat("\t" + itemName + ": " + currentInventory.countBooks(itemName) + "\n");
            }
        }
        
        return printList;
    }

    //Adds a new order to our list of past orders, and orders all their items
    public void addOrder(Order order) {
        pastOrders.add(order);
        this.repeatOrder(order.getOrderName());
    }
}
