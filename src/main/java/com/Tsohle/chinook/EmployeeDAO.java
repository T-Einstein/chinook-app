package com.Tsohle.chinook;
import java.sql.*; 
import java.util.ArrayList;
public class EmployeeDAO {
    public static ArrayList<Object[]> getEmployees(){

ArrayList<Object[]> list = new ArrayList<>();

        String sql = """
                SELECT e.FirstName, e.Last, e.Title, e.City, e.Country, e.Phone,
                    CONCAT(s.FirstName,' ', s.LastName) AS Supervisor
                FROM Employee e
                LEFT JOIN Employee s ON e.ReportsTo = s.EmployeeId
                """;

    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);){

        while(rs.next()){
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
        