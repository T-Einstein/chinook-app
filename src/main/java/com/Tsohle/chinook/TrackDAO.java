package com.Tsohle.chinook;
import java.sql.*; 
import java.util.ArrayList;

public class TrackDAO {
    public static ArrayList<String[]> getAlbums(){
        ArrayList<String[]> list = new ArrayList<>();

        String sql = """
                SELECT AlbumId, Title 
                FROM Album
                """;

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while(rs.next()){

                list.add(new String[]{
                    rs.getString("AlbumId"), 
                    rs.getString("Title")
                     });
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        return list;
    }


    public static ArrayList<String[]> getGenres(){
        ArrayList<String[]> list = new ArrayList<>();

        String sql = """
                SELECT GenreId, Name 
                FROM Genre
                """;

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while(rs.next()){

                list.add(new String[]{
                    rs.getString("GenreId"), 
                    rs.getString("Name")
                     });
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        return list;
    }

    public static ArrayList<String[]> getMedia(){
        ArrayList<String[]> list = new ArrayList<>();

        String sql = """
                SELECT MediaTypeId, Name 
                FROM MediaType
                """;

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while(rs.next()){

                list.add(new String[]{
                    rs.getString("MediaTypeId"), 
                    rs.getString("Name")
                     });
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        return list;
    }


    public static void insertTrack(String name, int albumId, int genreId, int mediaTypeId) {

    String sql = """
        INSERT INTO Track (Name, AlbumId, GenreId, MediaTypeId)
        VALUES (?, ?, ?, ?)
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setInt(2, albumId);
        stmt.setInt(3, genreId);
        stmt.setInt(4, mediaTypeId);

        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static ArrayList<Object[]> getTracks() {

    ArrayList<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT 
            t.Name AS TrackName,
            al.Title AS Album,
            g.Name AS Genre,
            mt.Name AS MediaType
        FROM Track t
        LEFT JOIN Album al ON t.AlbumId = al.AlbumId
        LEFT JOIN Genre g ON t.GenreId = g.GenreId
        LEFT JOIN MediaType mt ON t.MediaTypeId = mt.MediaTypeId
    """;

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {

            list.add(new Object[]{
                rs.getString("TrackName"),
                rs.getString("Album"),
                rs.getString("Genre"),
                rs.getString("MediaType")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
