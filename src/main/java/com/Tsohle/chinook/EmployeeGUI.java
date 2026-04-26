package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EmployeeGUI extends JFrame {
    JTable table;
    DefaultTableModel model; 

    public EmployeeGUI(){
        setTitle("Emloyees");
        setSize(800,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {
                "First Name", "Last Name", "Title",
                "City", "Country", "Phone", "Supervisor"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadEmployees();

        setVisible(true);
    }

     private void loadEmployees() {
        model.setRowCount(0); 

        ArrayList<Object[]> data = EmployeeDAO.getEmployees();

        for (Object[] row : data) {
            model.addRow(row);
        }
    }
}
