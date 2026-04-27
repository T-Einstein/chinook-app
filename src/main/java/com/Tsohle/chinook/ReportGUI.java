package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReportGUI extends JFrame {

    JTable reportTable;

    public ReportGUI() {

        setTitle("Genre Revenue Report");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        reportTable = new JTable();

        add(new JScrollPane(reportTable), BorderLayout.CENTER);

        loadReport();

        setVisible(true);
    }

    public void loadReport() {

        ArrayList<Object[]> data = ReportDAO.getGenreRevenue();

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Genre", "Revenue"}, 0
        );

        for (Object[] row : data) {
            model.addRow(row);
        }

        reportTable.setModel(model);
    }
}