package studio.dreamys.assignment5;

public class Book {
    private String isbn;
    private String title;
    private String pubDate;
    private Publisher publisher;
    private double cost;
    private double retail;
    private double discount;
    private String category;

    public Book(String isbn, String title, String pubDate, Publisher publisher, double cost, double retail, double discount, String category) {
        this.isbn = isbn;
        this.title = title;
        this.pubDate = pubDate;
        this.publisher = publisher;
        this.cost = cost;
        this.retail = retail;
        this.discount = discount;
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRetail() {
        return retail;
    }

    public void setRetail(double retail) {
        this.retail = retail;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + pubDate + '\'' +
                ", publisher=" + publisher +
                ", cost=" + cost +
                ", retail=" + retail +
                ", discount=" + discount +
                ", category='" + category + '\'' +
                '}';
    }
}
