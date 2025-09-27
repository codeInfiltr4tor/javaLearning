

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp{

        public static void main(String[] args) {
            // Book mybook = new Book("sakamoto Days", "sakamoto", ["anime"], 200, LocalDate.of(2022, 9, 24));
        
            // System.out.println("Book Name : "+mybook.getName());

            // mybook.setPrice(199.50f);

            // System.out.println("Price : "+mybook.getPrice());

            Scanner sc = new Scanner(System.in);
            Library library = new Library();

            while (true) {

                System.out.println("==== Library Management App =====");
                System.out.println("1. Add new Books: ");
                System.out.println("2. Display All Books");
                System.out.println("3. Exit.");
                System.out.print("Enter Your Choice : ");

                int opt = sc.nextInt();

                switch (opt) {
                    case 1 -> {
                        System.out.println("Adding the books --> ");
                        library.displayCounts();
                        Scanner vval = new Scanner(System.in);

                        // Writing Add book 
                        System.out.print("Enter the Name of book : ");
                        String bname = vval.nextLine();

                        System.out.print("Enter the Name of Author : ");
                        String bAuthor = vval.nextLine();

                        // For Multiple Topics 
                        List<String> bTopicList = new ArrayList<>();
                        System.out.println("Enter the Topic Names, Type 'Done' to Stop");

                        while (true) {
                            System.out.print("Topic (" +(bTopicList.size()+ 1) +") : ");
                            String topicInput = vval.nextLine().trim();

                            if(topicInput.equalsIgnoreCase("done")){
                                break;
                            }

                            if(!topicInput.isEmpty()){
                                bTopicList.add(topicInput);

                            }
                            // System.out.println("Topics list : "+bTopicList);
                        }

                        System.out.print("Enter the Selling Price : ");
                        float bPrice = vval.nextFloat();
                        vval.nextLine();

                        System.out.print("Enter publication year (e.g., 2023): ");
                        int year = vval.nextInt(); 
                        vval.nextLine(); 

                        System.out.print("Enter publication month (1-12): ");
                        int month = vval.nextInt(); 
                        vval.nextLine(); 

                        System.out.print("Enter publication day (1-31): ");
                        int day = vval.nextInt(); 
                        vval.nextLine(); 

                        LocalDate bPDate = LocalDate.of(year, month, day);

                        Book bookadd = new Book(bname, bAuthor, bTopicList, bPrice, bPDate);
                        library.addBooks(bookadd);

                        library.displayCounts();
                        
                    }

                    case 2 -> {
                        System.out.println("Displaying all the books -> ");
                        library.displayBooks();
                    }

                    case 3 -> {
                        System.out.println("Exiting the store.");
                        sc.close();
                        return;
                    }

                    default -> {
                        System.out.println("Wrong option");
                        throw new AssertionError();
                    }
                }
                
            }


    }

}


