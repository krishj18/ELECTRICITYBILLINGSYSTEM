package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import MODEL.customer;
import UTIL.DBConnection;
public class customerDAO {
public boolean addcustomer(customer c){
    String sql="INSERT INTO CUSTOMERS (name,address,meter_number,phone)VALUES(?,?,?,?)";
    try(Connection con= DBConnection.getConnection();
    PreparedStatement ps=con.prepareStatement(sql)){
        ps.setString(1, c.getName());
        ps.setString(2, c.getAddress());
        ps.setString(3, c.getmeternumber());
        ps.setString(4, c.getphone());
        return ps.executeUpdate() > 0;}
    catch (SQLException e){
        e.printStackTrace();
        return false;
    }
}
public List<customer>getallCustomers(){
    List<customer>list= new ArrayList<>();
    String sql= "select * from customers";
    try (Connection con= DBConnection.getConnection();
    Statement st =con.createStatement();
    ResultSet rs = st.executeQuery(sql)){
        while (rs.next()){
            list.add(new customer(
                     rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("meter_number"),
                    rs.getString("phone")
            ));
        }
    }
    catch(SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    public boolean deleteCustomers(int id) {
    String sql="DELETE FROM customers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    }
}
}
