package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EmployeeGUI extends JFrame {
    JTable table;
    DefaultTableModel model; 
    JTextField search;

    public EmployeeGUI(){


        search = new JTextField(20);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search:"));
        topPanel.add(search);

        add(topPanel, BorderLayout.NORTH);



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
        search.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent e) {
        searchEmployees();
    }
});

        setVisible(true);
    }

    private void searchEmployees(){
        String keyword = search.getText();
        model.setRowCount(0);

        var data = EmployeeDAO.searchEmployees(keyword);

        for(Object[] row : data){
            model.addRow(row);
        }
    }
    

     private void loadEmployees() {
        model.setRowCount(0); 

        ArrayList<Object[]> data = EmployeeDAO.getEmployees();

        for (Object[] row : data) {
            model.addRow(row);
        }
    }
}
