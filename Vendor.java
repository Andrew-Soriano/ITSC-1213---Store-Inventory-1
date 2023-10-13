/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

/**
 *
 * @author a_com
 */
class Vendor {
    private String name;
    private Inventory availableItems;
    
    Vendor(String name, Inventory items){
        setName(name);
        setAvailableItems(items);
    }
    
    //Getters and Setters
    public String getName(){
        return name;
    }
    
    public Inventory getAvailableItems(){
        return availableItems;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setAvailableItems(Inventory items){
        this.availableItems = items;
    }
}
