package com.example.B20DCDT008.library.DAO;

import com.example.B20DCDT008.library.Model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookDAO {
    private String jdbcURL = "jdbc:mysql://localhost/library";
    private String jdbcUsername = "root";
    private String jdbcPassword = "vietanh2409";

    //Query
    private static final String GET_ALL_BOOKS = "SELECT * FROM book";
    private static final String GET_A_BOOK_BY_ID = "SELECT * FROM book WHERE id = ?";
    private static final String UPDATE_A_BOOK = "UPDATE `library`.`book` SET `name` = ?, `author` = ?, `category` = ?, `public_day` = ?, `pages_number` = ?, `cover` = ?, `sold` = ?, `description` = ? WHERE (`id` = ?);";
    private static final String CHECK_BOOK_EXSITED = "SELECT * FROM book WHERE name = ? AND author = ?";
//    private static final String ADD_A_BOOK = "INSERT INTO `library`.`book` (`name`, `author`, `category`, `public_day`, `pages_number`, `sold`, `cover`, `description`) VALUES (?, ?, ?, ?, ?, 0, ?, ?);";
    private static final String ADD_A_BOOK =  "INSERT INTO book (name, author, category, public_day, pages_number, sold, cover, description) VALUES (?, ?, ?, ?, ?, 0, ?, ?)";
    private static final String DELETE_A_BOOK = "DELETE FROM book WHERE id = ?";
    public bookDAO(){

    }
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setCategory(rs.getString("category"));
                book.setPublic_day(rs.getDate("public_day"));
                book.setPages_number(rs.getInt("pages_number"));
                book.setSold(rs.getInt("sold"));
                book.setDescription(rs.getString("description"));
                book.setAuthor(rs.getString("author"));
                book.setCover(rs.getString("cover"));
                books.add(book);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }

    public Book getABookById(int id){
        Book book = new Book();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_A_BOOK_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setCategory(rs.getString("category"));
                book.setPublic_day(rs.getDate("public_day"));
                book.setPages_number(rs.getInt("pages_number"));
                book.setSold(rs.getInt("sold"));
                book.setDescription(rs.getString("description"));
                book.setAuthor(rs.getString("author"));
                book.setCover(rs.getString("cover"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }

    public boolean updateABook(Book book){
        boolean result = false;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_A_BOOK);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDate(4, (java.sql.Date) book.getPublic_day());
            ps.setInt(5, book.getPages_number());
            ps.setString(6, book.getCover());
            ps.setInt(7, book.getSold());
            ps.setString(8, book.getDescription());
            ps.setInt(9, book.getId());

            ps.execute();
            ps.close();

            result = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean checkExsited(Book book) {
        boolean exsited = false;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(CHECK_BOOK_EXSITED);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                exsited = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return exsited;
    }

    public boolean addBook(Book book){
        boolean result = false;
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_A_BOOK);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDate(4, (Date) book.getPublic_day());
            ps.setInt(5, book.getPages_number());
            ps.setString(6, book.getCover());
            ps.setString(7, book.getDescription());
            ps.execute();
            ps.close();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteABook(int id){
        boolean result = false;
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_A_BOOK);
            ps.setInt(1, Integer.valueOf(id));
            ps.execute();
            ps.close();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
