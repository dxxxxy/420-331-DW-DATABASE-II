package studio.dreamys.assignment5;

import java.sql.*;
import java.util.Scanner;

public class JustLeeServices {
    public static String url = "jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca";
    public static Connection conn;

    public static void main(String[] args) throws SQLException {
        //connection
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        conn = DriverManager.getConnection(url, username, password);

        //different types of publisher constructors
        Publisher publisher = new Publisher("Publisher", "Contact", "123-456-7890"); //non existing unique publisher
        Publisher publisher1 = new Publisher(1); //already existing publisher with id 0
        Publisher publisher2 = null; //no publisher

        //test 1
        Book book = new Book("00000", "The Book", "2005-01-21", publisher, 10.0, 20.0, 0.5, "Category");
        addBook(book);
        System.out.println(getBook(book.getIsbn()));

        //test 2
        Book book1 = new Book("00001", "The Book", "2005-01-21", publisher1, 10.0, 20.0, 0.5, "Category");
        addBook(book1);
        System.out.println(getBook(book1.getIsbn()));

        //test 3
        Book book2 = new Book("00002", "The Book", "2005-01-21", publisher2, 10.0, 20.0, 0.5, "Category");
        addBook(book2);
        System.out.println(getBook(book2.getIsbn()));
    }

    public static Book getBook(String isbn) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from BOOKS left outer join PUBLISHER using(PUBID) where ISBN = ?");
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("pubdate"), new Publisher(rs.getInt("pubid"), rs.getString("name"), rs.getString("contact"), rs.getString("phone")), rs.getDouble("cost"), rs.getDouble("retail"), rs.getDouble("discount"), rs.getString("category"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addBook(Book book) {
        try {
            //fetch number of books with isbn
            PreparedStatement countBookISBN = conn.prepareStatement("select count(*) from BOOKS where ISBN = ?");
            countBookISBN.setString(1, book.getIsbn());
            ResultSet uniqueBookRS = countBookISBN.executeQuery();
            uniqueBookRS.next();

            //validate book data
            if (uniqueBookRS.getInt(1) > 0) throw new SQLException("Book ISBN already exists");
            if (book.getIsbn().length() > 10) throw new SQLException("Book ISBN must be 10 characters or less");
            if (book.getTitle().length() > 30) throw new SQLException("Book title must be 30 characters or less");
            if (String.valueOf(book.getCost()).length() > 5) throw new SQLException("Book cost must be 5 digits or less");
            if (String.valueOf(book.getRetail()).length() > 5) throw new SQLException("Book retail must be 5 digits or less");
            if (String.valueOf(book.getDiscount()).length() > 4) throw new SQLException("Book discount must be 4 digits or less");
            if (book.getCategory().length() > 12) throw new SQLException("Book category must be 12 characters or less");

            //if the book has a publisher
            if (book.getPublisher() != null) {
                //fetch number of publishers with pubid
                PreparedStatement countPublisherPUBID = conn.prepareStatement("select count(*) from PUBLISHER where PUBID = ?");
                countPublisherPUBID.setInt(1, book.getPublisher().getPubId());
                ResultSet uniquePublisherRS = countPublisherPUBID.executeQuery();
                uniquePublisherRS.next();

                //if publisher doesn't exist, add it
                if (uniquePublisherRS.getInt(1) == 0) {
                    //validate publisher data
                    if (book.getPublisher().getName().length() > 23) throw new SQLException("Publisher name must be 23 characters or less");
                    if (book.getPublisher().getContact().length() > 15) throw new SQLException("Publisher contact must be 15 characters or less");
                    if (book.getPublisher().getPhone().length() > 12) throw new SQLException("Publisher phone must be 12 characters or less");

                    //fetch number of publishers
                    ResultSet pubCount = conn.prepareStatement("select count(*) from PUBLISHER").executeQuery();
                    pubCount.next();

                    //set unique pubid
                    int count = pubCount.getInt(1);
                    if (book.getPublisher().getPubId() <= count) {
                        book.getPublisher().setPubId(count + 1);
                    }

                    //insert publisher
                    PreparedStatement ps = conn.prepareStatement("insert into PUBLISHER values(?, ?, ?, ?)");
                    ps.setInt(1, book.getPublisher().getPubId());
                    ps.setString(2, book.getPublisher().getName());
                    ps.setString(3, book.getPublisher().getContact());
                    ps.setString(4, book.getPublisher().getPhone());
                    ps.executeUpdate();
                } else {
                    //find, create and set book's publisher object
                    PreparedStatement ps = conn.prepareStatement("select * from PUBLISHER where PUBID = ?");
                    ps.setInt(1, book.getPublisher().getPubId());
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    book.setPublisher(new Publisher(rs.getInt("pubid"), rs.getString("name"), rs.getString("contact"), rs.getString("phone")));
                }
            }

            //insert book
            PreparedStatement ps = conn.prepareStatement("insert into BOOKS values(?, ?, TO_DATE(?, 'yyyy-mm-dd'), ?, ?, ?, ?, ?)");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getPubDate());
            if (book.getPublisher() == null) ps.setNull(4, Types.NUMERIC);
            else ps.setInt(4, book.getPublisher().getPubId());
            ps.setDouble(5, book.getCost());
            ps.setDouble(6, book.getRetail());
            ps.setDouble(7, book.getDiscount());
            ps.setString(8, book.getCategory());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}