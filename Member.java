/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author a_com
 */
public class Member {
    final private double PREMIUM_FEE = 2.99;
    
    private String name; //The name of the member
    private boolean premium; //Whether or not this member is a premium member
    private PaymentCard payCard; //The card associated with the premium member's account
    private LocalDate lastPaymentDate; //The date when the user last paid their monthly premium membership fee
    private LocalDate nextPaymentDate; //The date when the user's next membership fee is due
    Inventory purchaseHistory; //Contains every item the user has ever purchased
    double totalPayments; //The running total for all payments made byt his account, including purchased items and membership fees
    
    //Static function to iterate through a list of members and return the Member object that matches the name in the list
    public static Member findMemberByName(ArrayList<Member> members, String name){
        for(Member mem: members){
            if(mem.getName().equals(name)){
                return mem;
            }
        }
        
        return new Member("NULL");
    }
    
    //Full constructor taking in all parameters
    Member(String name, boolean premium, PaymentCard card, LocalDate firstDate, LocalDate nextDate, 
            Inventory purchaseHistory, double totalPayments){
        setName(name);
        setPremium(premium);
        setPayCard(card);
        setLastPayDate(firstDate);
        setNextPayDate(nextDate);
        setPurchaseHistory(purchaseHistory);
        setTotalPayments(totalPayments);
    }
    
    //Constructor taking in only the name and leaving everything else uninitialized (use only for temporary Member objects!)
    Member(String name){
        setName(name);
    }
    
    //Constructor taking only a name, purchase history, and total payments. Used to initialize members that are not premium
    Member(String name, Inventory history, double total){
        setName(name);
        setPremium(false);
        setPurchaseHistory(history);
        setTotalPayments(total);
    }
    
    //Getters and Setters
    public String getName(){
        return name;
    }
    
    public boolean isPremium(){
        return premium;
    }
    
    public PaymentCard getPayCard(){
        return payCard;
    }
    
    public LocalDate getLastPaymentDate(){
        return lastPaymentDate;
    }
    
    public LocalDate getnextPaymentDate(){
        return nextPaymentDate;
    }
    
    public Inventory getPurchaseHitory(){
        return purchaseHistory;
    }
    
    public double getTotaPayments(){
        return totalPayments;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPremium(boolean premium){
        this.premium = premium;
    }
    
    public void setPayCard(PaymentCard card){
        this.payCard = card;
    }
    
    public void setLastPayDate(LocalDate date){
        this.lastPaymentDate = date;
    }
    
    public void setNextPayDate(LocalDate date){
        this.nextPaymentDate = date;
    }
    
    public void setPurchaseHistory(Inventory history){
        this.purchaseHistory = history;
    }
    
    public void setTotalPayments(double total){
        this.totalPayments = total;
    }
    
    /*The next four functions allow the user to add items to this member's 
        purchase History. You can add individual items of the appropriate class,
        or a whole Inventory class contianing a list of multiple items*/
    public void addPurchase(CD item){
        this.purchaseHistory.addItem(item);
    }
    
    public void addPurchase(DVD item){
        this.purchaseHistory.addItem(item);
    }
    
    public void addPurchase(Book item){
        this.purchaseHistory.addItem(item);
    }
    
    public void addPurchase(Inventory items){
        purchaseHistory.addItem(items);
    }
    
    public void addNewPayment(double payment){
        this.totalPayments += payment;
    }
    
    /*This last function updates this Member's information to reflect being 
        charged for their monthly membership*/
    public void chargeMonthlyPayment(){
        this.totalPayments += PREMIUM_FEE;
        lastPaymentDate = lastPaymentDate.plusMonths(1); //Note this does not store the actual date the charge was made, but instead stores when the last payment period ended
        nextPaymentDate = nextPaymentDate.plusMonths(1);
    }
    
    //function to call to remove the user's premium status and delete their card information
    public void removePremium(){
        if(this.isPremium()){
            this.setPayCard(new PaymentCard("NULL", "NULL", 0, 0, LocalDate.now()));
            this.setPremium(false);
        }
    }
    
    //Checks if the member is late on payments
    public boolean isLate(){
        if(isPremium()){
            if(nextPaymentDate.isBefore(LocalDate.now())){
                return true;
            }
        }
        return false;
    }
}
