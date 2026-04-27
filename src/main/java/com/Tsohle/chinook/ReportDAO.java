package com.Tsohle.chinook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*; 
import java.util.ArrayList;


public class ReportDAO {
    public static ArrayList<Object[]> getGenreRevenue() {
    ArrayList<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT 
            g.Name AS Genre,
            SUM(il.UnitPrice * il.Quantity) AS Revenue
        FROM InvoiceLine il
        JOIN Track t ON il.TrackId = t.TrackId
        JOIN Genre g ON t.GenreId = g.GenreId
        GROUP BY g.Name
        ORDER BY Revenue DESC
    """;

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("Genre"),
                rs.getDouble("Revenue")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
}
