package com.Tsohle.chinook;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRecommendationDAO {

    // Get all customers for the dropdown
    public static ArrayList<Item> getCustomers() {
        ArrayList<Item> list = new ArrayList<>();
        String sql = "SELECT CustomerId, FirstName, LastName FROM Customer ORDER BY FirstName";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("CustomerId");
                String name = rs.getString("FirstName") + " " + rs.getString("LastName");
                list.add(new Item(id, name));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get spending summary: total spent, total purchases, last purchase date
    public static Object[] getSpendingSummary(int customerId) {
        String sql = """
            SELECT 
                COUNT(i.InvoiceId)          AS TotalPurchases,
                SUM(i.Total)                AS TotalSpent,
                MAX(i.InvoiceDate)          AS LastPurchase
            FROM Invoice i
            WHERE i.CustomerId = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Object[]{
                    rs.getInt("TotalPurchases"),
                    rs.getDouble("TotalSpent"),
                    rs.getString("LastPurchase")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object[]{0, 0.0, "N/A"};
    }

    // Get favourite genre via SQL aggregation
    public static String getFavouriteGenre(int customerId) {
        String sql = """
            SELECT g.Name AS Genre, COUNT(il.TrackId) AS TrackCount
            FROM Invoice i
            JOIN InvoiceLine il ON i.InvoiceId = il.InvoiceId
            JOIN Track t        ON il.TrackId = t.TrackId
            JOIN Genre g        ON t.GenreId = g.GenreId
            WHERE i.CustomerId = ?
            GROUP BY g.GenreId, g.Name
            ORDER BY TrackCount DESC
            LIMIT 1
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Genre");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unknown";
    }

    // Get recommended tracks: customer's favourite genre, not already purchased
    public static ArrayList<Object[]> getRecommendedTracks(int customerId) {
        ArrayList<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT 
                t.Name      AS TrackName,
                ar.Name     AS Artist,
                al.Title    AS Album,
                g.Name      AS Genre
            FROM Track t
            JOIN Genre g        ON t.GenreId = g.GenreId
            JOIN Album al       ON t.AlbumId = al.AlbumId
            JOIN Artist ar      ON al.ArtistId = ar.ArtistId
            WHERE g.Name = (
                SELECT g2.Name
                FROM Invoice i2
                JOIN InvoiceLine il2 ON i2.InvoiceId = il2.InvoiceId
                JOIN Track t2        ON il2.TrackId = t2.TrackId
                JOIN Genre g2        ON t2.GenreId = g2.GenreId
                WHERE i2.CustomerId = ?
                GROUP BY g2.GenreId, g2.Name
                ORDER BY COUNT(il2.TrackId) DESC
                LIMIT 1
            )
            AND t.TrackId NOT IN (
                SELECT il3.TrackId
                FROM Invoice i3
                JOIN InvoiceLine il3 ON i3.InvoiceId = il3.InvoiceId
                WHERE i3.CustomerId = ?
            )
            LIMIT 20
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("TrackName"),
                    rs.getString("Artist"),
                    rs.getString("Album"),
                    rs.getString("Genre")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}