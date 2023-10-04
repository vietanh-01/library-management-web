package com.example.B20DCDT008.library.DAO;

import com.example.B20DCDT008.library.Model.Order;
import com.example.B20DCDT008.library.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class orderDAO {
    private String jdbcURL = "jdbc:mysql://localhost/library";
    private String jdbcUsername = "root";
    private String jdbcPassword = "vietanh2409";

    private static final String GET_ALL_ORDERS = "SELECT * FROM userorder WHERE user_id = ? AND status = 1";
    private static final String ADD_NEW_ORDER = "INSERT INTO userorder (user_id, book_id, book_name, quantity, status) VALUES(?, ? ,?, ?, 1)";
    private static final String CANCEL_ORDER = "UPDATE userorder SET status = 0 WHERE id = ?";
    private static final String GET_SOLD = "SELECT sold FROM book WHERE id = ?";
    private static final String UPDATE_SOLD = "UPDATE book SET sold = ? WHERE id = ?";
    private static final String GET_ORDER_BY_ID = "SELECT * FROM userorder WHERE id = ?";
    public orderDAO(){}

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

    public List<Order> getAllOrders(User user){
        List<Order> orders = new ArrayList<>();
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_ALL_ORDERS);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setBook_id(rs.getInt("book_id"));
                order.setBook_name(rs.getString("book_name"));
                order.setQuantity(rs.getInt("quantity"));
                order.setStatus(rs.getInt("status"));
                orders.add(order);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    public boolean addNewOrder(Order order){
        boolean result = false;
        try{
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_NEW_ORDER);
            PreparedStatement ps1 = connection.prepareStatement(GET_SOLD);
            PreparedStatement ps2 = connection.prepareStatement(UPDATE_SOLD);

            ps.setInt(1, order.getUser_id());
            ps.setInt(2, order.getBook_id());
            ps.setString(3, order.getBook_name());
            ps.setInt(4, order.getQuantity());
            ps.execute();

            ps1.setInt(1, order.getBook_id());
            ResultSet rs = ps1.executeQuery();
            while(rs.next()){
                int new_sold = rs.getInt("sold") + order.getQuantity();
                ps2.setInt(1, new_sold);
                ps2.setInt(2, order.getBook_id());
                ps2.execute();
                ps2.close();
            }
            ps1.close();
            ps.close();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean cancelOrder(int id){
        boolean result = false;
        Order order = new Order();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(CANCEL_ORDER);
            PreparedStatement ps1 = connection.prepareStatement(GET_ORDER_BY_ID);
            PreparedStatement ps2 = connection.prepareStatement(GET_SOLD);
            PreparedStatement ps3 = connection.prepareStatement(UPDATE_SOLD);

            ps.setInt(1, id);
            ps.execute();
            ps.close();
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();
            while(rs.next()){
                order.setBook_id(rs.getInt("book_id"));
                order.setQuantity(rs.getInt("quantity"));
            }
            ps1.close();
            ps2.setInt(1, order.getBook_id());
            ResultSet rs1 = ps2.executeQuery();
            int new_sold = 0;
            while(rs1.next()){
                new_sold = rs1.getInt("sold") - order.getQuantity();
            }
            ps2.close();
            ps3.setInt(1, new_sold);
            ps3.setInt(2, order.getBook_id());
            ps3.execute();
            ps3.close();

            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
