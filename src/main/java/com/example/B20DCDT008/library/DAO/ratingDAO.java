package com.example.B20DCDT008.library.DAO;

import com.example.B20DCDT008.library.Model.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ratingDAO {
    private String jdbcURL = "jdbc:mysql://localhost/library";
    private String jdbcUsername = "root";
    private String jdbcPassword = "vietanh2409";

    private static final String GET_RATING_OF_A_BOOK = "SELECT * FROM rating WHERE book_id = ?";
    private static final String GET_RATING_OF_AN_USER_FOR_A_BOOK = "SELECT * FROM rating WHERE book_id = ? AND user_id = ?";
    private static final String NEW_RATING = "INSERT INTO rating(user_id, username, book_id, rating, comment) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_RATING = "UPDATE rating SET rating = ?, comment = ? WHERE id = ?";
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

    public List<Rating> getAllRatingOfABook(int book_id) {
        List<Rating> ratings = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_RATING_OF_A_BOOK);
            ps.setInt(1, book_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Rating rating = new Rating();
                rating.setBook_id(book_id);
                rating.setComment(rs.getString("comment"));
                rating.setUsername(rs.getString("username"));
                rating.setId(rs.getInt("id"));
                rating.setUser_id(rs.getInt("user_id"));
                rating.setRating(rs.getInt("rating"));
                ratings.add(rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ratings;
    }

    public int getRatingOfAnUserForABook(int book_id, int user_id){
        int result = -1;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_RATING_OF_AN_USER_FOR_A_BOOK);
            ps.setInt(1, book_id);
            ps.setInt(2, user_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean newRating(Rating rating){
        boolean result = false;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(NEW_RATING);
            ps.setInt(1, rating.getUser_id());
            ps.setString(2, rating.getUsername());
            ps.setInt(3, rating.getBook_id());
            ps.setInt(4, rating.getRating());
            ps.setString(5, rating.getComment());
            ps.execute();
            ps.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateRating(Rating rating){
        boolean result = false;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_RATING);
            ps.setInt(1, rating.getRating());
            ps.setString(2, rating.getComment());
            ps.setInt(3, rating.getId());
            ps.execute();
            ps.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
