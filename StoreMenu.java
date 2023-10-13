/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package andrewsorianoproject1;

import java.time.LocalDate;
import java.time.Month;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author a_com
 */
public class StoreMenu {
    private final Scanner sc = new Scanner(System.in);
    private ArrayList<Member> memberList = new ArrayList<>(); //Holds a list of all store members
    private int lastInput; //Holds the last input on numerical menus
    private StoreInventory storeInventory = new StoreInventory( new Inventory(),
        new ArrayList<Order>(), 0.0, 0.0, 0.0);
    private Member activeMember; //Holds information about the member currently being worked on
    private Cart activeCart; //Holds information about the Cart currently being worked on.
    private ArrayList<Cart> cartList = new ArrayList<>(); //The list of all carts currently in use by customers

    StoreMenu(){
        ArrayList<CD> tempCDList = new ArrayList();
        tempCDList.add(new CD("The Good Music", 14.99, 10.0));
        ArrayList<DVD> tempDVDList = new ArrayList();
        tempDVDList.add(new DVD("The Good Movie", 14.99, 10.0));
        ArrayList<Book> tempBookList = new ArrayList();
        tempBookList.add(new Book("The Good Book", 14.99, 10.0));
        Inventory inv = new Inventory(tempCDList, tempDVDList, tempBookList);
        storeInventory.setCurrentInventory(inv);
        
        storeInventory.setPastOrders(new ArrayList<Order>());
        Inventory orderInventory = new Inventory();
        orderInventory.addItem(new Book("the good Book",14.99,10.0));
        orderInventory.addItem(new CD("the good CD",14.99,10.0));
        orderInventory.addItem(new DVD("the good DVD",14.99,10.0));
        storeInventory.addOrder(new Order("Order 1", new Vendor("Joja Mart", orderInventory),orderInventory));
        
        activeCart = new Cart(new Inventory(), new Member("NULL"));
        activeMember = new Member("Jerry", true,
                new PaymentCard("His Card","Jerry",1234567,321,LocalDate.of(2023, Month.MARCH, 1)),
                LocalDate.of(2023, Month.AUGUST, 5),LocalDate.of(2023, Month.SEPTEMBER, 5),
                new Inventory(),0.0);
        memberList.add(activeMember);
    }
    
    //Controls the display of all other menus
    public void mainMenu(){
        // TODO code application logic here
        //Initialize some default values
        boolean mainLoop = true;
        while(mainLoop){
            //Begin menu interaction
            System.out.println("Hello! Welcome to your store Inventory Management System. Are you a customer or an employee?");
            System.out.println("1. Customer");
            System.out.println("2. Employee");
            System.out.println("3. Quit");

            //Take customer input. Uses data validation to ensure one of the options was selected
            lastInput = sc.nextInt();
            sc.nextLine();
            // Use data validation
            while(lastInput < 1 || lastInput > 3){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 3");
                System.out.println("Hello! Welcome to your store Inventory Management System. Are you a customer or an employee?");
                System.out.println("\t1. Customer");
                System.out.println("\t2. Employee");
                System.out.println("3. Quit");

                lastInput = sc.nextInt();
                sc.nextLine();
            }

            //Implement a switch to choose between customer interactions and employee interactions
            switch(lastInput){
                case 1:
                    beginCustomerMenu();
                    break;
                case 2:
                    beginEmployeeMenu();
                    break;
                case 3:
                    //End the main loop and exit the program
                    mainLoop = false;
                    System.out.println("Now exiting program...");
            }
        }
    }
    
    //The first menu the customer enters when they want to interact with the store
    private void beginCustomerMenu(){
        String name;
        boolean stopLoop = true;
        //Print a choice menu for customer interactions
        //Start by asking the customer for their name, and use it to pull up their member information
        System.out.println("Welcome, valued customer! What is your name?");
        name = sc.nextLine();
        //Search for the customer's name, passing true to indicate a customer is searching
        activeMember = searchCustomerName(true, name);
        while(stopLoop){
            System.out.println("What would you like to do today?");
            System.out.print("\t1. Add Items to Order\n\t2. Pay for Order\n\t3. Sign Up For Premium Membership"
                    + "\n\t4. Quit Premium\n\t5. Quit\n");

            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 5){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 4");
                System.out.println("What would you like to do today?");
                System.out.print("\t1. Add Items to Order\n\t2.Pay for Order\n\t3. Sign Up For Premium Membership\n" 
                        + "\t4. Quit Premium\n\t5. Quit\n");

                lastInput = sc.nextInt();
                sc.nextLine();
            }

            //Process the choice and go to the next menu
            switch(lastInput){
                case 1:
                    customerShoppingMenu();
                    break;
                case 2:
                    customerCheckout();
                    break;
                case 3:
                    //First, is the customer already a premium member?
                    if(activeMember.isPremium()){
                        System.out.println("You are already a premium member.");
                    } else{
                        makePremium(activeMember.getName(), true);
                    }
                    break;
                case 4:
                    if(activeMember.isPremium()){
                        System.out.println("You are no longer a premium member. We're sorry to see you go!");
                        activeMember.removePremium();
                    } else{
                        System.out.println("You aren't a premium member yet!");
                    }
                    break;
                case 5:
                    stopLoop = false;
                    break;
            }  
        }
    }
    
    //Used to find Customers from the customerList and return them
    private Member searchCustomerName(boolean isCustomer, String name){
        Member tempMem = null;
        
        //Check if the person searching is a customer or employee
        if(isCustomer){
            //A customer is using this to enter their information
            //System.out.println("Please enter your name:");
            //name = sc.nextLine();
            
            /*Search for the name through our list of members, storing the 
            customer's member information if already in the sysetem, and
            a Member named NULL if otherwise*/
            activeMember = Member.findMemberByName(memberList, name);
            
            //Check that the Member exists, and allow the customer to become a member if otherwise
            if(activeMember.getName().equals("NULL")){
                //The customer is new and need a a new Member in the Member List
                System.out.println("Welcome new customer! Would you like to sign on for a premium membership?");
                System.out.print("\t1. Yes, sign me up!\n\t2. No, regular membership is fine\n");
                lastInput = sc.nextInt();
                sc.nextLine();
                
                //data validation
                while(lastInput < 1 || lastInput > 2){
                    System.out.println("Welcome new customer! Would you like to sign on for a premium membership?");
                    System.out.print("\t1. Yes, sign me up!\n\t2. No, regular membership is fine\n");

                    lastInput = sc.nextInt();
                    sc.nextLine();
                }
                
                switch(lastInput){
                    case 1:
                        //User wants to be a premium member
                        makePremium(name, true);
                        tempMem = activeMember;
                        break;
                    case 2:
                        //The user does not want a premium membership, add a member appropriately
                        System.out.println("Alright, we will add you as a regular member.");
                        tempMem = new Member(name, new Inventory(), 0.0);
                        memberList.add(tempMem);
                        break;
                }
            } else{
                tempMem = activeMember;
            }
        } else{
            //An employee is using this to enter in a new customer
            
            /*Search for the name through our list of members, storing the 
            customer's member information if already in the sysetem, and
            a Member named NULL if otherwise*/
            activeMember = Member.findMemberByName(memberList, name);
            
            //Check that the Member exists, and allow the customer to become a member if otherwise
            if(activeMember.getName().equals("NULL")){
                //The customer is new and need a a new Member in the Member List
                System.out.println("This customer is new. Ask the customer if they want to sign up for premium!");
                System.out.print("\t1. Yes\n\t2. No\n");
                lastInput = sc.nextInt();
                sc.nextLine();
                
                //data validation
                while(lastInput < 1 || lastInput > 2){
                    System.out.println("This customer is new. Ask the customer if they want to sign up for premium!");
                    System.out.print("\t1. Yes\n\t2. No\n");

                    lastInput = sc.nextInt();
                    sc.nextLine();
                }
                
                switch(lastInput){
                    case 1:
                        //User wants to be a premium member
                        makePremium(name, true);
                        tempMem = activeMember;
                        break;
                    case 2:
                        //The user does not want a premium membership, add a member appropriately
                        System.out.println("Alright, we will add you as a regular member.");
                        tempMem = new Member(name, new Inventory(), 0.0);
                        memberList.add(tempMem);
                        break;
                }
            } else{
                System.out.println("This customer already exists!");
                tempMem = activeMember;
            }
        }
        
        return tempMem;
    }
    
    //Controls the menu the customer uses to shop for items
    private void customerShoppingMenu() {
        String itemName;
        int amount;
        boolean shoppingMenu = true;
        CD tempCD = new CD("NULL", 0.0, 0.0);
        DVD tempDVD = new DVD("NULL", 0.0, 0.0);
        Book tempBook = new Book("NULL", 0.0, 0.0);
        
        while(shoppingMenu){
            //First print a list of all store items onto the screen
            System.out.println(storeInventory.printContents());

            //Then ask the user what they want to add to their order
            System.out.println("What would you like to order? Enter QUIT to quit.");
            itemName = sc.nextLine();

            //Data Validation
            while(!(storeInventory.getCurrentInventory().hasCD(itemName) || storeInventory.getCurrentInventory().hasDVD(itemName)
                    || storeInventory.getCurrentInventory().hasBook(itemName) || itemName.equals("QUIT"))){
                System.out.println("We're sorry, we do not carry that. Please try again.");
                System.out.println("What would you like to order? Enter QUIT to quit.");
                itemName = sc.nextLine();
            }
            if(itemName.equals("QUIT")){
                shoppingMenu = false;
            }else{
                //Determine if the item was a CD/DVD/Book and assign it to a temporary variable
                if(storeInventory.getCurrentInventory().hasCD(itemName)){
                    tempCD = storeInventory.getCurrentInventory().getCD(itemName);
                } else if(storeInventory.getCurrentInventory().hasDVD(itemName)){
                    tempDVD = storeInventory.getCurrentInventory().getDVD(itemName);
                } else{
                    tempBook = storeInventory.getCurrentInventory().getBook(itemName);
                }

                //Then ask the user how many they want to order
                System.out.println("How many would you like to order?");
                amount = sc.nextInt();
                sc.nextLine();

                //Data Validation
                while(((storeInventory.getCurrentInventory().countCDs(itemName) < amount) && 
                        (storeInventory.getCurrentInventory().countDVDs(itemName) < amount)&& 
                        (storeInventory.getCurrentInventory().countBooks(itemName) < amount))){
                    System.out.println("We're sorry, we do not have enough of that item. Please try again.");
                    System.out.println("How many would you like to order?");
                    amount = sc.nextInt();
                    sc.nextLine();
                }

                //Print an acknowledgement to the screen and add the item to the user's cart
                System.out.println("Thank you, we will add this to your cart!");
                setActiveCart(activeMember);

                //Now that we have the customer's cart, add the item to it
                for(int i = 0; i < amount; i++){
                    //Check for a NULL CD/DVD/Book and add whichever item type is not NULL
                    System.out.println(tempCD.equals(new CD ("NULL", 0.0, 0.0)));
                    if(tempCD.getName().equals(itemName)){
                        //Add this to the customer's cart
                        activeCart.addItem(tempCD);
                        storeInventory.reserveItems(new Inventory(tempCD));
                    } else if (tempDVD.getName().equals(itemName)){
                        activeCart.addItem(tempDVD);
                        storeInventory.reserveItems(new Inventory(tempDVD));
                    } else{
                        activeCart.addItem(tempBook);
                        storeInventory.reserveItems(new Inventory(tempBook));
                    }
                }
            }
        }
    }

    //Allows the customer to pay for their order
    private void customerCheckout() {
        //First, check if a customer has a cart and set it to the activeCart if true
        setActiveCart(activeMember);
        
        if(activeCart.calculateTotal() > 0){
            //Print a message about the total in the cart to the screen
            System.out.println("Your total for today is $" + activeCart.calculateTotal());

            //Ask for confirmation to continue
            System.out.print("Would you like to complete your purchase?\n\t1. Yes\n\t2. No\n");
            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 2){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter 1 or 2");
                System.out.print("Would you like to complete your purchase?\n\t1. Yes\n\t2. No\n");
                lastInput = sc.nextInt();
                sc.nextLine();
            }

            switch(lastInput){
                case 1:
                    //Check if the user is premium
                    if(activeMember.isPremium()){
                        //Ask if they would like to use cash or card
                        System.out.print("Would you like to use your saved card, or use cash?\n\t1. Card\n\t2. Cash\n");
                        lastInput = sc.nextInt();
                        sc.nextLine();
                        //Data validation
                        while(lastInput < 1 || lastInput > 2){
                            System.out.println("We're sorry, we do not understand your response. Please try again, and enter 1 or 2");
                            System.out.print("Would you like to use your saved card, or use cash?\n\t1. Card\n\t2. Cash\n");
                            lastInput = sc.nextInt();
                            sc.nextLine();
                        }
                        if(lastInput == 1){
                            //Check that the card is not expired
                            if((activeMember.getPayCard().isExpired(LocalDate.now()))){
                                //Card was expired, print a message and redirect them to paying with cash
                                System.out.println("We're sorry, your card is expired and you will need to pay wth cash.");
                                System.out.println("Please update your card from the main menu before completing your next purchase");
                            } else {
                                System.out.println("Please enter your security code");
                                lastInput = sc.nextInt();
                                sc.nextLine();

                                if(lastInput == activeMember.getPayCard().getSecurityCode()){
                                    System.out.println("Code matches! We will charge this card for this purchase!");
                                } else{
                                    System.out.println("Code does not match, please pay with cash.");
                                }
                            }
                        } else{
                            System.out.println("[PLEASE PRETEND STUDENT MADE A CASH PAYMENT SYSTEM AND ISN'T JUST UPDATING TOTAL PAYMENTS");
                        }
                    }
                    activeCart.completePurchase();
                    System.out.println("Your payment has been accepted! Thank you for shopping with us, and have a great day!");

                    //Delete the items from storeInventory and delete the cart from the list of carts
                    storeInventory.removeItems(activeCart.getCartContents(), true);
                    cartList.remove(activeCart);
                    activeCart = new Cart(new Inventory(), new Member("NULL"));
                    break;
                case 2:
                    //Print a message and exit
                    System.out.println("We will keep your cart for you while you continue to shop!");
                    break;
            }
        } else{
            //Print error and quit
            System.out.println("There are now items in your cart!");
        }
    }
    
    //Checks that the passed Member has a cart, sets the activeCart to that cart or creates a new Cart as needed
    private void setActiveCart(Member customer){
        //First, determine if the customer's cart is not active
        if(!(activeCart.getCartMember().equals(customer))){
            //The activeCart is not currently associated with the customer, so we must find or create a cart for them
            //But first, save the active cart
            for(Cart var : cartList){
                if(var.getCartMember().equals(activeCart.getCartMember())){
                    cartList.set(cartList.indexOf(var), activeCart);
                }
            }
            //Loop through all carts to find one with the current active member
            for(Cart var : cartList){
                if(var.getCartMember().equals(activeMember)){
                    activeCart = var;
                    break;
                }
            }
            //check that the activeCart is associated with the active Member, if not, create a new cart for the active member
            if(!(activeCart.getCartMember().equals(activeMember))){
                activeCart = new Cart(new Inventory(), activeMember);
                cartList.add(activeCart);
            }
        }
    }
    
    //Sets the activeCustomer to a premium member
    private void makePremium(String name, boolean customer){
        //The user wants a premium membership. Ask them for card information, then create the Member
        //Declare temporary variables (confined to the scope of this switch) to store user input
        int cardNumber;
        String cardHolder;
        String cardName;
        int securityCode;
        int expirationYear;
        int expirationMonth;
                        
        if(customer){
            //Customer menu version
            //get user input about their credit card
            System.out.println("Excellent! Please enter some credit card information to complete your premium membership!");
            System.out.print("Enter Your Credit Card Number: ");
            cardNumber = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Your Credit Card Secutiy Code: ");
            securityCode = sc.nextInt();
            sc.nextLine();
            System.out.print("What is the name on the card: ");
            cardHolder = sc.nextLine();
            System.out.print("What year does this card expire (enter a number): ");
            expirationYear = sc.nextInt();
            sc.nextLine();
            System.out.print("What month does this card expire (enter a number): ");
            expirationMonth = sc.nextInt();
            sc.nextLine();
            System.out.print("Please give this card a name in our system for ease of access (example: My Card): ");
            cardName = sc.nextLine();
            
            if(activeMember.getName().equals(name)){
                //The member already exists and is adjusting their membership
                activeMember.setPayCard(new PaymentCard(cardName, cardHolder, cardNumber,
                                securityCode, LocalDate.of(expirationYear, expirationMonth,1)));
                activeMember.setPremium(true);
            }else{
            //Create a new Member with the user's information and store it in the list
                activeMember = new Member(name, true, new PaymentCard(cardName, cardHolder, cardNumber,
                                    securityCode, LocalDate.of(expirationYear, expirationMonth,1)),
                             LocalDate.now(), LocalDate.now().plusMonths(1), new Inventory(),0.0);
                memberList.add(activeMember);
                System.out.println("Thank You! You have been made a premium member and your card will be saved for future use.");
                System.out.println("Your next payment is due: " + activeMember.getnextPaymentDate()); 
            }
        }else{
            //Employee menu version
            //get user input about their credit card
            System.out.println("Enter the Member's credit card information");
            System.out.print("Enter Credit Card Number: ");
            cardNumber = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Credit Card Secutiy Code: ");
            securityCode = sc.nextInt();
            sc.nextLine();
            System.out.print("What is the name on the card: ");
            cardHolder = sc.nextLine();
            System.out.print("What year does this card expire (enter a number): ");
            expirationYear = sc.nextInt();
            sc.nextLine();
            System.out.print("What month does this card expire (enter a number): ");
            expirationMonth = sc.nextInt();
            sc.nextLine();
            System.out.print("Please give this card a name in our system for ease of access (example: My Card): ");
            cardName = sc.nextLine();

            if(activeMember.getName().equals(name)){
                //The member already exists and is adjusting their membership
                activeMember.setPayCard(new PaymentCard(cardName, cardHolder, cardNumber,
                                securityCode, LocalDate.of(expirationYear, expirationMonth,1)));
                activeMember.setPremium(true);
            }else{
               //Create a new Member with the user's information and store it in the list
                activeMember = new Member(name, true, new PaymentCard(cardName, cardHolder, cardNumber,
                                    securityCode, LocalDate.of(expirationYear, expirationMonth,1)),
                             LocalDate.now(), LocalDate.now().plusMonths(1), new Inventory(),0.0);
                memberList.add(activeMember);
                System.out.println("Member has been upgraded and the card saved");
                System.out.println("Their next payment is due: " + activeMember.getnextPaymentDate()); 
            }
        }
    }
    
    //The menu an employee first enters to interact with the program
    private void beginEmployeeMenu() {
        boolean stopLoop = true;
        while(stopLoop){
            System.out.println("What do you need to do today?");
            System.out.print("\t1. Ring Up Customer's Purchase\n\t2. Check Customer Out\n\t3. Manage Customers\n" 
                        + "\t4. Manage Inventory\n\t5. Quit\n");

            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 5){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 4");
                System.out.println("What would you like to do today?");
                System.out.print("\t1. Ring Up Customer's Purchase\n\t2. Check Customer Out\n\t3. Manage Customers\n" 
                        + "\t4. Manage Inventory\n\t5. Quit\n");

                lastInput = sc.nextInt();
                sc.nextLine();
            }

            //Process the choice and go to the next menu
            switch(lastInput){
                case 1:
                    employeeShoppingMenu();
                    break;
                case 2:
                    employeeCheckout();
                    break;
                case 3:
                    manageCustomers();
                    break;
                case 4:
                    manageInventory();
                    break;
                case 5:
                    stopLoop = false;
                    break;
            }  
        }
    }
    
    //The menu an employee uses to ring up a customer's items
    private void employeeShoppingMenu() {
        String name;
        boolean shoppingMenu = true;
        //Start by establishing who the customer we are shopping for its
        System.out.println("Please ask the customer for their name");
        name = sc.nextLine();
        //Search for the customer's name, passing false to indicate a employee is searching
        activeMember = searchCustomerName(false, name);
        
        while(shoppingMenu){
            String itemName;
            int amount;
            CD tempCD = new CD("NULL", 0.0, 0.0);
            DVD tempDVD = new DVD("NULL", 0.0, 0.0);
            Book tempBook = new Book("NULL", 0.0, 0.0);

            //First print a list of all store items onto the screen
            System.out.println(storeInventory.printContents());

            //Then ask the user what they want to add to their order
            System.out.println("What is the customer ordering? Enter QUIT to quit.");
            itemName = sc.nextLine();

            //Data Validation
            while(!(storeInventory.getCurrentInventory().hasCD(itemName) || storeInventory.getCurrentInventory().hasDVD(itemName)
                    || storeInventory.getCurrentInventory().hasBook(itemName) || itemName.equals("QUIT"))){
                System.out.println("We're sorry, we do not carry that. Please try again.");
                System.out.println("What would you like to order? Enter QUIT to quit.");
                itemName = sc.nextLine();
            }
            
            if(itemName.equals("QUIT")){
                shoppingMenu = false;
            }else{
                //Determine if the item was a CD/DVD/Book and assign it to a temporary variable
                if(storeInventory.getCurrentInventory().hasCD(itemName)){
                    tempCD = storeInventory.getCurrentInventory().getCD(itemName);
                } else if(storeInventory.getCurrentInventory().hasDVD(itemName)){
                    tempDVD = storeInventory.getCurrentInventory().getDVD(itemName);
                } else{
                    tempBook = storeInventory.getCurrentInventory().getBook(itemName);
                }

                //Then ask the user how many they want to order
                System.out.println("How many are they ordering?");
                amount = sc.nextInt();
                sc.nextLine();

                //Data Validation
                while(((storeInventory.getCurrentInventory().countCDs(itemName) < amount) && 
                        (storeInventory.getCurrentInventory().countDVDs(itemName) < amount)&& 
                        (storeInventory.getCurrentInventory().countBooks(itemName) < amount))){
                    System.out.println("We're sorry, we do not have enough of that item. Please try again.");
                    System.out.println("How many are they ordering?");
                    amount = sc.nextInt();
                    sc.nextLine();
                }

                //Print an acknowledgement to the screen and add the item to the user's cart
                System.out.println("items added to cart!");
                setActiveCart(activeMember);

                //Now that we have the customer's cart, add the item to it
                for(int i = 0; i < amount; i++){
                    //Check for a NULL CD/DVD/Book and add whichever item type is not NULL
                    System.out.println(tempCD.equals(new CD ("NULL", 0.0, 0.0)));
                    if(tempCD.getName().equals(itemName)){
                        //Add this to the customer's cart
                        activeCart.addItem(tempCD);
                        storeInventory.reserveItems(new Inventory(tempCD));
                    } else if (tempDVD.getName().equals(itemName)){
                        activeCart.addItem(tempDVD);
                        storeInventory.reserveItems(new Inventory(tempDVD));
                    } else{
                        activeCart.addItem(tempBook);
                        storeInventory.reserveItems(new Inventory(tempBook));
                    }
                }
            }
        }
    }

    //The menu an employee uses to check a customer out
    private void employeeCheckout() {
        String name;
        //First, ask the employee for the customer's name (customer may not be in the system yet)
        System.out.println("What is the customer's name?");
        name= sc.nextLine();
        //Search for the customer's name, passing false to indicate an employee is searching
        activeMember = searchCustomerName(false, name);
        
        //Then, check if a customer has a cart and set it to the activeCart
        setActiveCart(activeMember);
        
        if(activeCart.calculateTotal() > 0){
            //Print a message about the total in the cart to the screen
            System.out.println("Thei total is $" + activeCart.calculateTotal());

            //Ask for confirmation to continue
            System.out.print("Please ask if they would you like to complete their purchase?\n\t1. Yes\n\t2. No\n");
            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 2){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter 1 or 2");
                System.out.print("Please ask if they would you like to complete their purchase?\n\t1. Yes\n\t2. No\n");
                lastInput = sc.nextInt();
                sc.nextLine();
            }

            switch(lastInput){
                case 1:
                    //Check if the user is premium
                    if(activeMember.isPremium()){
                        //Ask if they would like to use cash or card
                        System.out.print("Ask the customer if they would like to use card or cash?\n\t1. Card\n\t2. Cash\n");
                        lastInput = sc.nextInt();
                        sc.nextLine();
                        //Data validation
                        while(lastInput < 1 || lastInput > 2){
                            System.out.println("We're sorry, we do not understand your response. Please try again, and enter 1 or 2");
                            System.out.print("Ask the customer if they would like to use card or cash?\n\t1. Card\n\t2. Cash\n");
                            lastInput = sc.nextInt();
                            sc.nextLine();
                        }
                        if(lastInput == 1){
                            //Check that the card is not expired
                            if((activeMember.getPayCard().isExpired(LocalDate.now()))){
                                //Card was expired, print a message and redirect them to paying with cash
                                System.out.println("We're sorry, their card is expired and you will need to pay wth cash.");
                            } else {
                                //Not implemented (in real life the customer would swie or scan their card, but we have no card reader attached to this project)
                            }
                        } else{
                            System.out.println("[PLEASE PRETEND STUDENT MADE A CASH PAYMENT SYSTEM AND ISN'T JUST UPDATING TOTAL PAYMENTS");
                        }
                    }
                    activeCart.completePurchase();
                    System.out.println("Payment accepted!");

                    //Delete the items from storeInventory and delete the cart from the list of carts
                    storeInventory.removeItems(activeCart.getCartContents(), true);
                    cartList.remove(activeCart);
                    activeCart = new Cart(new Inventory(), new Member("NULL"));
                    break;
                case 2:
                    //Print a message and exit
                    System.out.println("Exiting to menu.");
                    break;
            }
        }else{
            //Print an error and exit
            System.out.println("This customer does not have any items in their cart yet!");
        }
    }

    //Open a menu for employees to manage customer data
    private void manageCustomers() {
        String name;
        boolean loop = true;
        while(loop){
            //Start by printing the next menu
            System.out.print("What would you like to do for your customers?\n\t1. Add new customer\n\t"
                    + "2. Change the Active Member\n\t3. Manage Premium Membership\n"
                    + "\t4. Quit\n");
            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 4){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 4");
                System.out.print("What would you like to do for your customers?\n\t1. Add new customer\n\t"
                    + "2. Change the Active Member\n\t3. Manage Premium Membership\n"
                    + "\t4. Quit\n");

                lastInput = sc.nextInt();
                sc.nextLine();
            }

            switch(lastInput){
                case 1:
                    //Ask for the name and search for if the customer already exists
                    System.out.println("Please ask the customer for their name");
                    name = sc.nextLine();
                    //Search for the customer's name, passing false to indicate a employee is searching
                    activeMember = searchCustomerName(false, name);
                    break;
                case 2:
                    //Ask for the name and search for if the customer already exists
                    System.out.println("What customer are we switching to?");
                    name = sc.nextLine();
                    //Search for the customer's name, passing false to indicate a employee is searching
                    activeMember = searchCustomerName(false, name);
                    break;
                case 3:
                    employeePremiumMenu();
                    break;
                case 4:
                    loop = false;
                    break;
            }   
        }
    }

    //Opens a menu for the employee to interact with the store's inventory
    private void manageInventory() {
        boolean loop = true;
        while(loop){
            //Start another menu
            System.out.print("How shall we manage the inventory?\n\t1. Check current Inventory\n\t2. Order more Items\n"
                    + "\t3. Financial Reports\n\t4. Quit\n");
            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 4){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 4");
                System.out.print("How shall we manage the inventory?\n\t1. Check current Inventory\n\t2. Order more Items\n"
                    + "\t3. Financial Reports\n\t4. Quit\n");

                lastInput = sc.nextInt();
                sc.nextLine();
            }

            switch(lastInput){
                case 1:
                    //Print the full, itemized inventory
                    System.out.println(storeInventory.printContents());
                    break;
                case 2:
                    //Start ordering menu
                    System.out.print("What items will we order?\n\t1. New order\n\t2. Repeat Order\n\t3. Quit\n");
                    lastInput = sc.nextInt();
                    sc.nextLine();
                    //Data validation
                    while(lastInput < 1 || lastInput > 3){
                        System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 3");
                        System.out.print("What items will we order?\n\t1. New order\n\t2. Repeat Order\n\t3. Quit\n");

                        lastInput = sc.nextInt();
                        sc.nextLine();
                    }
                    switch(lastInput){
                        case 1:
                            //New order
                            String vendorName;
                            String orderName;
                            Inventory vendorItems = new Inventory();
                            Inventory orderItems = new Inventory();
                            CD tempCD;
                            DVD tempDVD;
                            Book tempBook;
                            int uniqueItems;
                            int amount;
                            boolean loopController = true;

                            //First ask for input and create a vendor for this order
                            System.out.println("Please enter the information for this order");
                            System.out.println("What is the vendor's name?");
                            vendorName = sc.nextLine();

                            //Ask for the number of unique items we are ordering from this vendor
                            System.out.println("How many unique items are we ordering from this Vendor?"
                                    + "(A unique item is one with a different name than any other item in this order)");
                            uniqueItems = sc.nextInt();
                            sc.nextLine();

                            //loop once for each unique item and ask for item information, and add the items to the vendorItems
                            for(int i = 0; i < uniqueItems; i++){
                                String itemName;
                                double itemCost;
                                double itemPrice;

                                System.out.println("What is the name of this item?");
                                itemName = sc.nextLine();
                                System.out.println("How much is the Vendor charging us for this item?");
                                itemCost = sc.nextDouble();
                                sc.nextLine();
                                System.out.println("How much are we charging customers for this item?");
                                itemPrice = sc.nextDouble();
                                sc.nextLine();

                                System.out.print("What is this item?\n\t1. CD\n\t2. DVD\n\t3. Book\n");
                                lastInput = sc.nextInt();
                                sc.nextLine();
                                switch(lastInput){
                                    case 1:
                                        tempCD = new CD(itemName, itemPrice, itemCost);
                                        System.out.println("How many of these are we purchasing from the Vendor?");
                                        amount = sc.nextInt();
                                        sc.nextLine();
                                        vendorItems.addItem(tempCD);
                                        for(int j = 0; j < amount; j++){
                                            orderItems.addItem(tempCD);
                                        }
                                        break;
                                    case 2:
                                        tempDVD = new DVD(itemName, itemPrice, itemCost);
                                        System.out.println("How many of these are we purchasing from the Vendor?");
                                        amount = sc.nextInt();
                                        sc.nextLine();
                                        vendorItems.addItem(tempDVD);
                                        for(int j = 0; j < amount; j++){
                                            orderItems.addItem(tempDVD);
                                        }
                                        break;
                                    case 3:
                                        tempBook = new Book(itemName, itemPrice, itemCost);
                                        System.out.println("How many of these are we purchasing from the Vendor?");
                                        amount = sc.nextInt();
                                        sc.nextLine();
                                        vendorItems.addItem(tempBook);
                                        for(int j = 0; j < amount; j++){
                                            orderItems.addItem(tempBook);
                                        }
                                        break;
                                }
                            }

                            System.out.println("Name this order for easy reuse");
                            orderName = sc.nextLine();
                            //Add the Order and Vendor information to our list of previous orders for ease of reordering
                            storeInventory.addOrder(new Order(orderName, new Vendor(vendorName, vendorItems), orderItems));
                            break;
                        case 2:
                            String name;
                            //Start menu to ask which order we will be repeating
                            System.out.println("Repeat which past order?");
                            
                            //print all past orders
                            for(Order var: storeInventory.getPastOrders()){
                                System.out.println(var.printOrder() + "\n");
                            }
                            System.out.println("Please type the name associated with the past order");
                            name = sc.nextLine();

                            //Repeat the order by name
                            if(storeInventory.repeatOrder(name)){
                                System.out.println("The order was repeated");
                            }else{
                                System.out.println("Sorry, we are unable to find your order.");
                            }
                            break;
                    }
                    break;
                case 3:
                    //Start financial menu
                    System.out.print("What do you want to know?\n\t1. Profit\n\t2. Shrink\n\t3. Quit\n");
                    lastInput = sc.nextInt();
                    sc.nextLine();
                    //Data validation
                    while(lastInput < 1 || lastInput > 3){
                        System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 3");
                        System.out.print("What do you want to know?\n\t1. Profit\n\t2. Shrink\n\t3. Quit\n");

                        lastInput = sc.nextInt();
                        sc.nextLine();
                    }
                    switch(lastInput){
                        case 1:
                            //Print the profits from storeInventory
                            System.out.println("Our profits so far are $" + storeInventory.calculateProfit());
                            break;
                        case 2:
                            //Report Shrink from storeInventory
                            System.out.println("Our total shrink is $" + storeInventory.getTotalStoreShrink());
                            break;
                    }
                    break;
                case 4:
                    loop = false;
                    break;
            }
        }
    }

    //Opens a menu for employees to interact with data about premium memberships
    private void employeePremiumMenu() {
        boolean loop = true;
        while(loop){
           //Make sure there is a customer selected before continuing
           if(activeMember == null){
               System.out.println("We need a customer before we can work on premium members! Set an active customer first!");
           } else{
               //Print the next menu
               System.out.print("What are we doing with premium members?\n\t1. Upgrade Current Member\n"
                       + "\t2. Downgrade current Member\n\t3. Manage Payments\n\t4. Quit\n");
               lastInput = sc.nextInt();
               sc.nextLine();
               //Data validation
               while(lastInput < 1 || lastInput > 4){
                   System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 4");
                   System.out.print("What are we doing with premium members?\n\t1. Upgrade Current Member\n"
                       + "\t2. Downgrade current Member\n\t3. Manage Payments\n\t4. Quit\n");

                   lastInput = sc.nextInt();
                   sc.nextLine();
               }

               switch(lastInput){
                   case 1:
                       if(activeMember.isPremium()){
                           System.out.println("This member is already a premium member!");
                       } else{
                           makePremium(activeMember.getName(),false);
                       }
                       break;
                   case 2:
                       if(activeMember.isPremium()){
                               System.out.println("Premium member downgraded to regular member!");
                               activeMember.removePremium();
                           } else{
                               System.out.println("This customer is not a premium member");
                           }
                       break;
                   case 3:
                       managePayments();
                       break;
                   case 4:
                       loop = false;
                       break;
               }
           }   
        }
    }

    //A menu for interacting with payment data for premium members
    private void managePayments() {
        boolean loop = true;
        while(loop){
            //Start another menu
            System.out.print("What will we do with premium payments?\n\t1. Check current member is up to date\n"
                    + "\t2. Check payments for all members\n\t3. Make current Member payment\n"
                    + "\t4. Make all outstanding payments\n\t5. Quit\n");
            lastInput = sc.nextInt();
            sc.nextLine();
            //Data validation
            while(lastInput < 1 || lastInput > 5){
                System.out.println("We're sorry, we do not understand your response. Please try again, and enter between 1 and 4");
                System.out.print("What will we do with premium payments?\n\t1. Check current member is up to date\n"
                    + "\t2. Check payments for all members\n\t3. Make current Member payment\n"
                    + "\t4. Make all outstanding payments\n\t5. Quit\n");
                
                lastInput = sc.nextInt();
                sc.nextLine();
            }
            switch(lastInput){
                case 1:
                    //Check that the customer is a premium customer first
                    if(activeMember.isPremium()){
                        //Now check if they were late
                        if(activeMember.isLate()){
                            //Print how late they are
                            System.out.println(activeMember.getName() + "'s payment is " +
                                    DAYS.between(activeMember.getnextPaymentDate(), LocalDate.now()) +
                                    " days overdue.");
                        }else{
                            
                            //Print that they are not overdue
                            System.out.println(activeMember.getName() + " is not overdue. There are "
                                    + (-1 * DAYS.between(activeMember.getnextPaymentDate(), LocalDate.now())) +
                                    " days until their next payment.");
                        }
                    }else{
                        //Print error and exit
                        System.out.println("A non-premium customer cannot be late on their premium payment!");
                    }
                    break;
                case 2:
                    //The same as case 1, but on all members in memberList. Dl not print unless member is late
                    int memberCount = 0;
                    for(Member var : memberList){
                        
                        if(var.isLate()){
                            //Print how late they are
                            System.out.println(var.getName() + "'s payment is " +
                                    DAYS.between(var.getnextPaymentDate(), LocalDate.now()) +
                                    " days overdue.");
                            memberCount++;
                        }
                    }
                    if(memberCount == 0){
                        //Print an error
                        System.out.println("There are no late premium members!");
                    }
                    break;
                case 3:
                    //First check that the current customer is a premium member
                    if(activeMember.isPremium()){
                        //Check that the payment has not been made within last five days
                        if(DAYS.between(activeMember.getLastPaymentDate(), LocalDate.now())
                                > 5){
                                    //Make the payment and print confirmation
                                    activeMember.chargeMonthlyPayment();
                                    System.out.println("Payment charged");
                        }else{
                            //Say it is too soon to make a payment
                            System.out.println("Sorry, our policy does not allow members to pay so far in advance.");
                        }
                    }else{
                        //Print error and exit
                        System.out.println("Cannot charge a non-premium member a membership fee!");
                    }
                    break;
                case 4:
                    //The same as case 3, but for all members late on their payments
                    int total = 0;
                    for(Member var : memberList){
                        if(var.isLate()){
                            //Make the payment and print confirmation
                            memberList.get(memberList.indexOf(var)).chargeMonthlyPayment();
                            System.out.println("Payment charged to " + var.getName());
                            total++;
                        }
                    }
                    if(total > 0){
                        System.out.println("Charge " + total + " outstanding fee(s)");
                    } else{
                        System.out.println("No customers have been charged, not late payments found");
                    }
                    break;
                case 5:
                    loop = false;
                    break;
            }
        }
    }
}
