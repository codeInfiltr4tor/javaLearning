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

    public void displayBooks(){

        if(this.books.isEmpty()){
            System.out.println("No Books Added.");
        }
        else {
            
            for(Book book : this.books){
                String topicString = String.join(",",book.getTopic());
                
                System.out.println("Title : "+book.getName()+
                " | Author : "+book.getAuthor()+
                " | Topic : ["+topicString+"]"+
                " | Price : "+book.getPrice()+
                " | Published Date : "+book.getPublishDate());  
            }
        }

    }

}

