/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

/**
 *
 * @author a_com
 */
public class Cart {
    private Inventory cartContents;
    private Member cartMember;
    
    Cart(Inventory contents, Member member){
        setCartContents(contents);
        setCartMember(member);
    }
    
    //Geters and Setters
    public Inventory getCartContents(){
        return cartContents;
    }
    
    public Member getCartMember(){
        return cartMember;
    }
    
    public void setCartContents(Inventory contents){
        cartContents = contents;
    }
    
    public void setCartMember(Member member){
        cartMember = member;
    }
    
    //The following three methods allow the user to add individual items of the corresponding type to the cart
    public void addItem(CD item){
        cartContents.addItem(item);
    }
    
    public void addItem(DVD item){
        cartContents.addItem(item);
    }
    
    public void addItem(Book item){
        cartContents.addItem(item);
    }
    
    /*The following three methods allow the user to remove a specified amount of
    the corresponding type of item from the cart*/
    public void removeItem(CD item, int amount){
        for(int i = 0; i == amount; i++){
            cartContents.getCDList().remove(item);
        }
    }
    
    public void removeItem(DVD item, int amount){
        for(int i = 0; i == amount; i++){
            cartContents.getDVDList().remove(item);
        }
    }
    
    public void removeItem(Book item, int amount){
        for(int i = 0; i == amount; i++){
            cartContents.getBookList().remove(item);
        }
    }
    
    //Calculates the total price of all items in the cart
    public double calculateTotal(){
        return cartContents.totalPrice();
    }
    
    //Adds all items currently in the cart to the corresponding Member's purchase history, and updates their spending total
    public void completePurchase(){
        cartMember.addPurchase(cartContents);
        cartMember.addNewPayment(cartContents.totalPrice());
    }
}
