import java.util.*;
import java.util.stream.Collectors;

////////////// BASE CLASS /////////////////////
abstract class LibraryItems{
    private  String title;
    private  String itemId;
    private  boolean checkedOut;

    public LibraryItems(String title, String itemId){
        this.title = title;
        this.itemId = itemId;
        this.checkedOut = false;
    }

    public String getTitle(){
        return title;
    }
    public String getItemId(){
        return itemId;
    }
    public boolean isCheckedOut(){
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut){
        this.checkedOut = checkedOut;
    }

    // Abstract methods for subclasses
    public abstract String getItemType();

    // Polymorphism method

    public String getItemDetails(){
        return "Title : " + title  + "\nID : " + itemId + "\n Type : " + getItemType();
    }
}


/////////////////// DERIVED CLASSES ////////////////////////

class Book extends LibraryItems{
    private String author;
    private String isbn;

    public Book(String title, String itemId, String author, String isbn ){
        super(title, itemId);
        this.author = author;
        this.isbn = isbn;
    }
    public String getAuthor(){
        return author;
    }
    public String getIsbn(){
        return isbn;
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    @Override
    public String getItemDetails(){
        return super.getItemDetails() + "\nAuthor : " + author + "\nISBN : " + isbn;
    }
}

class DVD extends LibraryItems{
    private String director;
    private int duration;

    public DVD(String title, String itemId, String director, int duration){
        super(title, itemId);
        this.director = director;
        this.duration = duration;
    }
    public String getDirector(){
        return director;
    }
    public int getDuration(){
        return duration;
    }
    @Override
    public String getItemType() {
        return "DVD";
    }

    @Override
    public String getItemDetails(){
        return super.getItemDetails() + "\nDirector : " + director + "\nDuration : " + duration;
    }
}

/////////////// Library Member ////////////////

class LibraryMember{
    private String name;
    private String memberId;
    private List<LibraryItems> checkOutItems;

    public LibraryMember(String name, String memberId){
        this.name = name;
        this.memberId = memberId;
        this.checkOutItems = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    public String getMemberId(){
        return memberId;
    }
    public void CheckOutItem(LibraryItems item){

        if(!item.isCheckedOut()){
            item.setCheckedOut(true);
            checkOutItems.add(item);
        }
    }
    public void returnItem(LibraryItems item){
        if(checkOutItems.remove(item)){
            item.setCheckedOut(false);
        }
    }
    public List<LibraryItems> getCheckOutItems(){
        return new ArrayList<>(checkOutItems);
    }
}


class Library {
    private List<LibraryMember> members;
    private List<LibraryItems> items;

    public Library(){
        this.members = new ArrayList<>();
        this.items = new ArrayList<>();
    }
    public void addItem(LibraryItems item){
        if(items.add(item))
            System.out.println("Item added successfully.");
        else
            System.out.println("Item not added.");
    }
    public void addMember(LibraryMember member){
        members.add(member);
    }
    public void removeMember(String memberId){

        if(members.removeIf(member -> member.getMemberId().equals(memberId)))
            System.out.println("Member " + memberId + " removed");
        else
            System.out.println("Member " + memberId + " not found");

    }
    public LibraryItems findItem(String itemId){
        return items.stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    public LibraryMember findMember(String memberId){
        return members.stream()
                .filter(member -> member.getMemberId().equals(memberId))
                .findFirst()
                .orElse(null);
    }
    public void checkOutItem(String memberId, String itemId){
        LibraryItems item = findItem(itemId);
        LibraryMember member = findMember(memberId);
        if(item != null && member != null){
            member.CheckOutItem(item);
            System.out.println("Successfully Given the Item");
        }else{
            System.out.println("Member or Item does not Exist");
        }
    }
    public void returnItem(String memberId, String itemId) {
        LibraryItems item = findItem(itemId);
        LibraryMember member = findMember(memberId);

        // Check if the member exists
        if (member == null) {
            System.out.println("Member not found with ID: " + memberId);
            return; // Exit the method if the member is not found
        }

        // Check if the item was checked out by the member
        if (member.getCheckOutItems().contains(item)) {
            member.returnItem(item);
            System.out.println("Item returned successfully.");
        } else {
            System.out.println("This item was not checked out by member ID: " + memberId);
        }
    }
    public void getAvailableItems() {
        List<LibraryItems> availableItems = items.stream()
                .filter(item -> !item.isCheckedOut())
                .collect(Collectors.toList());
        System.out.println("Available items count: " + availableItems.size());

    }
    public void getAvailableMembers() {
        List<LibraryMember> availableMembers = members.stream().collect(Collectors.toList());
        System.out.println("Available Members : " + availableMembers.size());
    }
}
//////////////////////// Library Management System ///////////////////////////////////////////

public class LibraryManagementSystem{

   private static Library library = new Library();

    public static void addBook(Scanner sc){
        System.out.print("Enter title : ");
        String title = sc.nextLine();
        System.out.print("Enter itemId : ");
        String itemId = sc.nextLine();
        System.out.print("Enter author : ");
        String author = sc.nextLine();
        System.out.print("Enter isbn : ");
        String isbn = sc.nextLine();

        LibraryItems item = new Book(title,itemId,author,isbn);
        library.addItem(item);
    }
    public static void addDVD(Scanner sc){
        System.out.println("Enter title : ");
        String title = sc.nextLine();
        System.out.println("Enter itemId : ");
        String itemId = sc.nextLine();
        System.out.println("Enter director : ");
        String dir = sc.nextLine();
        System.out.println("Enter duration : ");
        int dur = sc.nextInt();
        sc.nextLine();

        LibraryItems item = new DVD(title,itemId,dir,dur);
        library.addItem(item);
        System.out.println("DVD added successfully.");
    }
    public static void giveItem(Scanner sc){

        System.out.println("Enter itemId : ");
        String itemId = sc.nextLine();
        System.out.println("Enter Member ID : ");
        String memberId = sc.nextLine();
        library.checkOutItem(memberId,itemId);
    }
    public static void returnItem(Scanner sc){
        System.out.println("Enter itemId : ");
        String itemId = sc.nextLine();
        System.out.println("Enter Member ID : ");
        String memberId = sc.nextLine();
        library.returnItem(memberId,itemId);
//        System.out.println("Returned successfully.");
    }
    public static void addMemberToLib(Scanner sc){
        System.out.println("Enter Member ID : ");
        String memberId = sc.nextLine();
        System.out.println("Enter Name : ");
        String name = sc.nextLine();

        LibraryMember member = new LibraryMember(name,memberId);
        library.addMember(member);
        System.out.println("Member added successfully to Library.");

    }

    public static void main(String [] args){

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        do{
            System.out.println("******************************\n");
            System.out.println("Choose the Menu :  ");
            System.out.println("1. Enter Book to Library ");
            System.out.println("2. Enter DVD to Library ");
            System.out.println("3. Give Item to Member ");
            System.out.println("4. Return Item fom Member ");
            System.out.println("5. Add Member to Library ");
            System.out.println("6. Remove Member from Library ");
            System.out.println("7. Get Available Items and Members");
            System.out.println("8. Exit Library ");
            System.out.println("******************************\n");

            int choice;
            System.out.println("Enter your choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    addDVD(sc);
                    break;
                case 3:
                    giveItem(sc);
                    break;
                case 4:
                    returnItem(sc);
                    break;
                case 5:
                    addMemberToLib(sc);
                    break;
                case 6: {
                    System.out.println("Enter Member ID : ");
                    String memberId = sc.nextLine();
                    library.removeMember(memberId);
                    break;
                }
                case 7:
                    library.getAvailableItems();
                    library.getAvailableMembers();
                    break;
                case 8:
                    System.out.println("Thank you for visiting the Library ");
                    running =  false;

            }
        }while (running);

        sc.close(); // Close the Scanner once the program exits
    }

}
