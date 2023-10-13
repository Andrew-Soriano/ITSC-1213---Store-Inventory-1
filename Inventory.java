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
public class Inventory {
    private ArrayList<CD> cdList;
    private ArrayList<DVD> dvdList;
    private ArrayList<Book> bookList;
    
    //Constructor specifying all elements
    Inventory(ArrayList<CD> cdList, ArrayList<DVD> dvdList, ArrayList<Book> bookList){
        setCDList(cdList);
        setDVDList(dvdList);
        setBookList(bookList);
    }
    
    //Constructory for an Inventory containing a single CD
    Inventory(CD item){
        this.cdList = new ArrayList<>();
        this.dvdList = new ArrayList<>();
        this.bookList = new ArrayList<>();
        
        this.cdList.add(item);
    }
    
    //Constructory for an Inventory containing a single DVD
    Inventory(DVD item){
        this.cdList = new ArrayList<>();
        this.dvdList = new ArrayList<>();
        this.bookList = new ArrayList<>();
        
        this.dvdList.add(item);
    }
    
    //Constructory for an Inventory containing a single Book
    Inventory(Book item){
        this.cdList = new ArrayList<>();
        this.dvdList = new ArrayList<>();
        this.bookList = new ArrayList<>();
        
        this.bookList.add(item);
    }
    
    //Empty constructor leaving all elements uninstantiated
    Inventory(){
        this.cdList = new ArrayList<>();
        this.dvdList = new ArrayList<>();
        this.bookList = new ArrayList<>();
    }
    
    //Getters and Setters
    public ArrayList<CD> getCDList(){
        return cdList;
    }
    
    public ArrayList<DVD> getDVDList(){
        return dvdList;
    }
    
    public ArrayList<Book> getBookList(){
        return bookList;
    }
    
    public void setCDList(ArrayList<CD> list){
        cdList = list;
    }
    
    public void setDVDList(ArrayList<DVD> list){
        dvdList = list;
    }
    
    public void setBookList(ArrayList<Book> list){
        bookList = list;
    }
    
    //Itterates through the list of CDs and returns the first CD object of the specified name
    public CD getCD(String name){
        for(CD var : cdList){
            if(var.getName().equals(name)){
                return var;
            }
        }
        
        //return a CD named NULL if no CD of specified name was found
        return new CD("NULL", 0.0, 0.0);
    }
    
    //Itterates through the list of CDs and returns the first DVD object of the specified name
    public DVD getDVD(String name){
        for(DVD var : dvdList){
            if(var.getName().equals(name)){
                return var;
            }
        }
        
        //return a DVD named NULL if no CD of specified name was found
        return new DVD("NULL", 0.0, 0.0);
    }
    
    //Itterates through the list of Books and returns the first CD object of the specified name
    public Book getBook(String name){
        for(Book var : bookList){
            if(var.getName().equals(name)){
                return var;
            }
        }
        
        //return a Book named NULL if no CD of specified name was found
        return new Book("NULL", 0.0, 0.0);
    }
    
    //The following four methods allow the user to add a single item, or an Inventory of items, to the corresponding lists
    public void addItem(CD item){
        cdList.add(item);
    }
    
    public void addItem(DVD item){
        dvdList.add(item);
    }
    
    public void addItem(Book item){
        bookList.add(item);
    }
    
    public void addItem(Inventory items){
        for(CD var : items.getCDList()){
            this.addItem(var);
        }
        for(DVD var : items.getDVDList()){
            this.addItem(var);
        }
        for(Book var : items.getBookList()){
            this.addItem(var);
        }
    }
    
    /*The follow Nine Methods allow removing items from a list, either via their index, 
      by matching with a passed parameter, or by passing a list of items*/
    public void removeCD(int index){
        cdList.remove(index);
    }
    
    public void removeDVD(int index){
        dvdList.remove(index);
    }
    
    public void removeBook(int index){
        bookList.remove(index);
    }
    
    public void removeCD(CD item){
        cdList.remove(item);
    }
    
    public void removeDVD(DVD item){
        dvdList.remove(item);
    }
    
    public void removeBook(Book item){
        bookList.remove(item);
    }
    
    public void removeCD(ArrayList<CD> items){
        for(CD var: items){
            removeCD(var);
        }
    }
    
    public void removeDVD(ArrayList<DVD> items){
        for(DVD var: items){
            removeDVD(var);
        }
    }
    
    public void removeBook(ArrayList<Book> items){
        for(Book var: items){
            removeBook(var);
        }
    }
    
    //Takes a full inventory of items and removes it from the store's inventory
    public void removeItems(Inventory items){
        removeCD(items.getCDList());
        removeDVD(items.getDVDList());
        removeBook(items.getBookList());
    }
    
    //Determines if a CD with the target name is in the list
    public boolean hasCD(String target){
        for(CD var : cdList){
            if(var.getName().equals(target)){
                return true;
            }
        }
        return false;
    }
    
    //Determines if a DVD with the target name is in the list
    public boolean hasDVD(String target){
        for(DVD var : dvdList){
            if(var.getName().equals(target)){
                return true;
            }
        }
        return false;
    }
    
    //Determines if a CD with the target name is in the list
    public boolean hasBook(String target){
        for(Book var : bookList){
            if(var.getName().equals(target)){
                return true;
            }
        }
        return false;
    }
    
    //The following three methods calculate a total for the price of every item in the corresponding list
    public double priceOfCDList(){
        double total = 0;
        
        for(CD item : cdList){
            total += item.getPrice();
        }
        
        return total;
    }
    
    public double priceOfDVDList(){
        double total = 0;
        
        for(DVD item : dvdList){
            total += item.getPrice();
        }
        
        return total;
    }
    
    public double priceOfBookList(){
        double total = 0;
        
        for(Book item : bookList){
            total += item.getPrice();
        }
        
        return total;
    }
    
    //Calculates the total price of all items in this Inventory
    public double totalPrice(){
        return (this.priceOfCDList() + this.priceOfDVDList() + this.priceOfBookList());
    }
    
    //The following three methods calculate the total cost to the store for all items in the corresponding list
    public double costOfCDList(){
        double total = 0;
        
        for(CD item : cdList){
            total += item.getCost();
        }
        
        return total;
    }
    
    public double costOfDVDList(){
        double total = 0;
        
        for(DVD item : dvdList){
            total += item.getCost();
        }
        
        return total;
    }
    
    public double costOfBookList(){
        double total = 0;
        
        for(Book item : bookList){
            total += item.getCost();
        }
        
        return total;
    }
    
    //Calculates the total cost to the store of all items in this Inventory
    public double totalCost(){
        return (this.costOfCDList() + this.costOfDVDList() + this.costOfBookList());
    }
    
    //Returns how many CDs of the passed name are in the list
    public int countCDs(String name){
        int total = 0;
        for(CD var : cdList){
            if(var.getName().equals(name)){
              total++;  
            }
        }
        
        return total;
    }
    
    //Returns how many DVDs of the passed name are in the list
    public int countDVDs(String name){
        int total = 0;
        for(DVD var : dvdList){
            if(var.getName().equals(name)){
              total++;  
            }
        }
        
        return total;
    }
    
    //Returns how many Books of the passed name are in the list
    public int countBooks(String name){
        int total = 0;
        for(Book var : bookList){
            if(var.getName().equals(name)){
              total++;  
            }
        }
        
        return total;
    }
}
