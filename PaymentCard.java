/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

import java.time.LocalDate;

/**
 *
 * @author a_com
 */
class PaymentCard {
    private String cardName; //The name of this card to display, such as "My Card"
    private String cardHolder; //The nane of the person who owns the card
    private int cardNumber; //The number identifying the card
    private int securityCode; //The security code used to verify the card
    private LocalDate expirationDate; //The date when the card expires
    
    PaymentCard(String cardName, String cardHolder, int cardNumber,int securityCode, LocalDate expirationDate){
        setCardName(cardName);
        setCardHolder(cardHolder);
        setCardNumber(cardNumber);
        setSecurityCode(securityCode);
        setExpirationDate(expirationDate);
    }
    
    //Getters and Setters
    public String getCardName(){
        return cardName;
    }
    
    public String getCardHolder(){
        return cardHolder;
    }
    
    public int getCardNumber(){
        return cardNumber;
    }
    
    public int getSecurityCode(){
        return securityCode;
    }
    
    public LocalDate getExpirationDate(){
        return expirationDate;
    }
    
    public void setCardName(String name){
        cardName = name;
    }
    
    public void setCardHolder(String holder){
        cardHolder = holder;
    }
    
    public void setCardNumber(int number){
        cardNumber = number;
    }
    
    public void setSecurityCode(int code){
        securityCode = code;
    }
    
    public void setExpirationDate(LocalDate date){
        expirationDate = date;
    }
    
    //Checks the given date against the expiration date to see if the card is expired
    public boolean isExpired(LocalDate date){
        if(expirationDate.compareTo(date) < 0){
            return true;
        }else{
            return false;
        }
    }
}
