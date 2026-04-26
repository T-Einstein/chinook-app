package com.Tsohle.chinook;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class TracksGUI extends JFrame{

    JTable table;

    public TracksGUI() {

        setTitle("Tracks");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);

        add(scroll);

        loadTracks();

        setVisible(true);
    }




    public void loadTracks() {

        ArrayList<Object[]> data = TrackDAO.getTracks();

        String[] cols = {"Name", "Album", "Genre", "Media Type"};

        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        table.setModel(model);
    }
}
