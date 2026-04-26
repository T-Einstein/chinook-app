package com.Tsohle.chinook;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TracksGUI extends JFrame{

    JTable table;

   public TracksGUI() {

        setTitle("Tracks");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        table = new JTable();

        JButton addBtn = new JButton("Add Track");
        JButton refreshBtn = new JButton("Refresh");

        JPanel topPanel = new JPanel();
        topPanel.add(addBtn);
        topPanel.add(refreshBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addBtn.addActionListener(e -> new AddTrackDialog(this).setVisible(true));
        refreshBtn.addActionListener(e -> loadTracks());

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
