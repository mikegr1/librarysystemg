import java.util.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
class LibrarySystem{
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<Item> itemList = new ArrayList<Item>();
    ArrayList<LoanItem> checkedOutItems = new ArrayList<LoanItem>();
    ArrayList<Request> requestList = new ArrayList<Request>();
    ArrayList<Renew> renewList = new ArrayList<Renew>();
    LocalDate currentDay = LocalDate.now();

    void addUser(User newUser){
        userList.add(newUser);
    }

    void addItem(Item newItem){
        itemList.add(newItem);
    }

    void addRequest(LoanItem item, User user){
        Request request = new Request(item, user);
        requestList.add(request);
    }

    void addRenew(LoanItem item, User user){
        Renew renew = new Renew(item, user);
        for(Request request: requestList){
            if(request.getItem() == renew.getItem()){
                return;
            }
        }
        if(item.renew()){
            renewList.add(renew);
        }

    }


    void loanItem(LoanItem item, User user){
        user.addLoanItem(item);
        item.loanItem(user);
        checkedOutItems.add(item);
    }

    void returnItem(LoanItem item, User user){
        user.removeLoanItem(item);
        item.returnItem();
        checkedOutItems.remove(item);
    }

    void checkOverDueItems(){
        for(LoanItem item: checkedOutItems){
            if(item.getDueDate().isBefore(currentDay)){
                item.overDue();
            }
        }
    }
    void displayUsers(){
        for (User user: userList){
            System.out.println("-----------------------");
            user.display();
            System.out.println("-----------------------");
        }
    }

    void displayItems(){
        for (Item item: itemList){
            System.out.println("-----------------------");
            item.display();
            System.out.println("-----------------------");
        }
    }

    void displayMenu(){
        System.out.println("============================");
        System.out.println("|   Library Selection Menu |");
        System.out.println("============================");
        System.out.println("| Options:                 |");
        System.out.println("|        1. Renew          |");
        System.out.println("|        2. Request        |");
        System.out.println("|        3. Display items  |");
        System.out.println("|        4. Checkout items |");
        System.out.println("|        5. Return items   |");
        System.out.println("|        6. User info      |");
        System.out.println("|        7. Exit Library   |");
        System.out.println("============================");
    }

    public static void main(String args[]){
        LibrarySystem library = new LibrarySystem();
        User userOne = new User("Tommy", "Frans", "123 New Lane", 22, 1, false);
        library.addUser(userOne);
        User userTwo = new User("Jenny", "Hamilton", "777 Lucky Ave", 33, 2, true);
        library.addUser(userTwo);
        LoanItem itemOne = new LoanBook(2244, "Book of Snail", 10, true);
        library.addItem(itemOne);
        LoanItem itemTwo = new LoanAV(2266, "End of All Things", 12);
        library.addItem(itemTwo);
        LoanItem itemThree = new LoanAV(2277, "Avatar", 20);
        library.addItem(itemThree);
        Item itemFour = new RefItem(2277, "Science Weekly", 2);
        library.addItem(itemFour);
        Item itemFive = new RefBook(2277, "Dictionary", 20);
        library.addItem(itemFive);
        Item itemSix = new RefMag(2277, "Math Journal", 3);
        library.addItem(itemSix);
        LoanItem itemSeven = new LoanBook(1277, "Bad Book", 10, false);
        library.addItem(itemSeven);
        
        int swValue;
        boolean inMenu = true;
        Scanner scanInput = new Scanner(System.in);
        Scanner getItem = new Scanner(System.in);
        library.displayMenu();
        swValue = scanInput.nextInt();



    // Selection menu
        while(inMenu == true){
            switch (swValue) {
                case 1:
                System.out.println("Renew selected");
                String searchTerm;
                System.out.println("please enter the name of the item to renew");
                searchTerm = getItem.next();
                int size = library.checkedOutItems.size();
        
                for(int i = 0; i < size ; i++){
                    if(searchTerm == library.checkedOutItems.get(i).getTitle()){
                       library.addRenew(library.checkedOutItems.get(i), userOne);
                       System.out.println("The item " + searchTerm + " is now renewed");
                    }
                }
        
                System.out.println("\nwhat Else would you like to do?");
                library.displayMenu();
                swValue = scanInput.nextInt();
                break;
        
                case 2:
                System.out.println("request selected");
                System.out.println("please enter the name of the item to request");
                String searchTerm2 = getItem.next();
                int size2 = library.checkedOutItems.size();
        
                for(int k = 0; k < size2 ; k++){
                    if(searchTerm2 == library.checkedOutItems.get(k).getTitle()){
                       library.addRequest(library.checkedOutItems.get(k), userOne);
                       System.out.println("Request for " + searchTerm2 + " is placed");
                    }
                }
        
                System.out.println("\nwhat Else would you like to do?");
                library.displayMenu();
                swValue = scanInput.nextInt();
                break;
                
                case 3:
                System.out.println("Displaying items");
                library.displayItems();
                System.out.println("\nwhat Else would you like to do?");
                library.displayMenu();
                swValue = scanInput.nextInt();
                break;
        
                case 4:
                System.out.println("Checkout item");
                System.out.println("Please enter the name of the item you want to checkout");
                String searchTerm3 = getItem.next();
                int size3 = library.itemList.size();
                String tempTitle;

                for(int p = 0; p < size3; p++){
                    tempTitle = library.itemList.get(p).getTitle();
                    if(tempTitle == searchTerm3){
                        System.out.println("Its all good");
                        if(library.itemList.get(p) instanceof LoanBook){
                            library.loanItem((LoanBook)library.itemList.get(p), userOne);
                            System.out.println("the item " + searchTerm3 + " has been checked out");
                        }
                        else if(library.itemList.get(p) instanceof LoanAV){
                            library.loanItem((LoanAV)library.itemList.get(p), userOne);
                            System.out.println("the item " + searchTerm3 + " has been checked out");
                        }
                       
                    }
                }
        
                System.out.println("\nwhat Else would you like to do?");
                library.displayMenu();
                swValue = scanInput.nextInt();
                break;
        
                case 5:
                System.out.println("Return Item");
                System.out.println("Please enter the name of the item you want to return");
                String searchTerm4 = getItem.next();
                int size4 = library.checkedOutItems.size();
                
                for(int q = 0; q < size4; q++){
                    if(searchTerm4 == library.checkedOutItems.get(q).getTitle()){
                        library.returnItem(library.checkedOutItems.get(q), userOne);
                        System.out.println("the item " + searchTerm4 + " has been returned");
                    }
                }
        
                System.out.println("\nwhat Else would you like to do?");
                library.displayMenu();
                swValue = scanInput.nextInt();
                break;
        
                case 6:
                System.out.println("User Credentials");
                userOne.display();
                userOne.displayLoans();
                System.out.println("\nwhat Else would you like to do?");
                library.displayMenu();
                swValue = scanInput.nextInt();
                break;
        
                case 7:
                System.out.println("exiting library system");
                getItem.close();
                scanInput.close();
                inMenu = false;
                break;
        
                default:
                System.out.println("Invalid selection");
                inMenu = false;
                break; // This break is not really necessary
                }
        }



    }
}