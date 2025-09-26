import java.util.ArrayList;
import java.util.List;

public class Library{

    private List<Book> books;

    public Library(){
        this.books = new ArrayList<>();
    }

    public void addBooks(Book book){

        this.books.add(book);

    }

    public void displayCounts(){
        System.out.println("Total Books Count is : "+this.books.size());
    }

}

