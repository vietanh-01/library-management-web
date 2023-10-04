package com.example.B20DCDT008.library.DAO;

import com.example.B20DCDT008.library.Model.User;

import java.sql.*;

public class userDAO {
    private String jdbcURL = "jdbc:mysql://localhost/library";
    private String jdbcUsername = "root";
    private String jdbcPassword = "vietanh2409";

    //Query
    private static final String CHECK_LOGIN = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String CHECK_USER_EXIST = "SELECT * FROM user WHERE username = ? OR email = ?";
    private static final String REGISTER_NEW_USER = "INSERT INTO `library`.`user` (`name`, `email`, `username`, `password`, `role`) VALUES ( ?, ?, ?, ?, 'user')";
    public userDAO() {
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

    public User checkLogin(String username, String password) {
        User user = new User();
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(CHECK_LOGIN);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                user.setEmail(rs.getString("email"));
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public boolean checkExist(String username, String email){
        boolean result = false;
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(CHECK_USER_EXIST);
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = null;
            rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean registerUser(User user){
        boolean check = false;
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(REGISTER_NEW_USER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.execute();
            ps.close();
            check = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }


}
