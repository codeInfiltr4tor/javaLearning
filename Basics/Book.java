
import java.time.LocalDate;
import java.util.List;


public class Book{

    private String name,author;
    private List<String> topic;
    private float price;
    private LocalDate publishedDate;

    public Book(String bname, String bauthor, List<String> btopic, float bprice, LocalDate bpublishDate){

        this.name = bname;
        this.author = bauthor;
        this.topic = btopic;
        this.price = bprice;
        this.publishedDate = bpublishDate;

    }

    // All getter Method

    public String getName(){
        return this.name;
    }

    public String getAuthor(){
        return this.author;
    }

    public List<String> getTopic(){
        return this.topic;
    }

    public float getPrice(){
        return this.price;
    }

    public LocalDate getPublishDate(){
        return this.publishedDate;
    }

    // All Setter method

    public void setAuthor(String author){
        this.author = author;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setTopic(List<String> topic){
        this.topic = topic;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public void setPublishDate(LocalDate publishDate){
        this.publishedDate = publishDate;
    }

    

}


