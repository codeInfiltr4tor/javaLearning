

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryApp{

        public static void main(String[] args) {
            Book mybook = new Book("sakamoto Days", "sakamoto", "Action,Anime", 200, LocalDate.of(2022, 9, 24));
        
            System.out.println("Book Name : "+mybook.getName());

            mybook.setPrice(199.50f);

            System.out.println("Price : "+mybook.getPrice());

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

                        System.out.println("Enter the Name of Author : ");
                        String bAuthor = vval.nextLine();

                        System.out.println("Enter the Topic of Book : ");
                        String bTopic = vval.nextLine();

                        System.out.println("Enter the Selling Price : ");
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

                        Book bookadd = new Book(bname, bAuthor, bTopic, bPrice, bPDate);
                        library.addBooks(bookadd);

                        library.displayCounts();

                        vval.close();

                    }

                    case 2 -> {
                        System.out.println("Displaying all the books -> ");
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


