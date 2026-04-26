package com.Tsohle.chinook;
import java.sql.*; 
import java.util.ArrayList;
public class EmployeeDAO {
    public static ArrayList<Object[]> getEmployees(){

ArrayList<Object[]> list = new ArrayList<>();

        String sql = """
                SELECT e.FirstName, e.LastName, e.Title, e.City, e.Country, e.Phone,
                    CONCAT(s.FirstName,' ', s.LastName) AS Supervisor
                FROM Employee e
                LEFT JOIN Employee s ON e.ReportsTo = s.EmployeeId
                """;

    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);){

        while(rs.next()){

             System.out.println("Found employee: " + rs.getString("FirstName"));

            list.add(new Object[]{
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Title"),
                rs.getString("City"),
                rs.getString("Country"),
                rs.getString("Phone"),
                rs.getString("Supervisor")
            });
        }


    } catch(Exception e){
        e.printStackTrace();
    }

    return list;
}

    public static ArrayList<Object[]> searchEmployees(String keyword){
        ArrayList<Object[]> list = new ArrayList<>();

        String sql = """
                SELECT e.FirstName, e.LastName, e.Title, e.City, e.Country, e.Phone,
                    CONCAT(s.FirstName,' ', s.LastName) AS Supervisor
                FROM Employee e
                LEFT JOIN Employee s ON e.ReportsTo = s.EmployeeId
                WHERE e.FirstName LIKE? 
                OR e.LastName LIKE ? 
                OR e.City LIKE ?
                """;

         try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            String search = "%" + keyword + "%";

        stmt.setString(1, search);
        stmt.setString(2, search);
        stmt.setString(3, search);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){

             System.out.println("Found employee: " + rs.getString("FirstName"));

            list.add(new Object[]{
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Title"),
                rs.getString("City"),
                rs.getString("Country"),
                rs.getString("Phone"),
                rs.getString("Supervisor")
            });
        }


    } catch(Exception e){
        e.printStackTrace();
    }

    return list;
    }

    }
        