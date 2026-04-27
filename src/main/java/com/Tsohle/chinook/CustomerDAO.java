package com.Tsohle.chinook;

import java.util.ArrayList;
import java.sql.*; 

public class CustomerDAO {
    public static ArrayList<Object[]> getCustomers() {
    ArrayList<Object[]> list = new ArrayList<>();

    String sql = "SELECT CustomerId, FirstName, Email, Phone, Country FROM Customer";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            list.add(new Object[]{
                rs.getInt("CustomerId"),
                rs.getString("FirstName"),
                rs.getString("Email"),
                rs.getString("Phone"),
                rs.getString("Country")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public static void addCustomer(String name, String email, String phone, String country) {
    String sql = """
        INSERT INTO Customer (FirstName, Email, Phone, Country)
        VALUES (?, ?, ?, ?)
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, phone);
        stmt.setString(4, country);

        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void updateCustomer(int id, String name, String email, String phone, String country) {
    String sql = """
        UPDATE Customer
        SET FirstName=?, Email=?, Phone=?, Country=?
        WHERE CustomerId=?
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, phone);
        stmt.setString(4, country);
        stmt.setInt(5, id);

        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void deleteCustomer(int id) {
    String sql = "DELETE FROM Customer WHERE CustomerId=?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static ArrayList<Object[]> getInactiveCustomers() {
    ArrayList<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT c.FirstName, c.Email
        FROM Customer c
        LEFT JOIN Invoice i ON c.CustomerId = i.CustomerId
        GROUP BY c.CustomerId
        HAVING MAX(i.InvoiceDate) IS NULL
        OR MAX(i.InvoiceDate) < DATE_SUB(NOW(), INTERVAL 2 YEAR)
    """;

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("FirstName"),
                rs.getString("Email")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
}
