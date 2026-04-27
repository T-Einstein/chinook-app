package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InactiveCustomersGUI extends JFrame {

    JTable table;
    JTextField searchField;

    public InactiveCustomersGUI() {

        setTitle("Inactive Customers");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table = new JTable();

        // ===== SEARCH BAR =====
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Show Inactive");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(refreshBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====

        // Show inactive customers (your query)
        refreshBtn.addActionListener(e -> loadInactiveCustomers());

        // Search ALL customers (simple filter)
        searchBtn.addActionListener(e -> searchCustomers());

        // load inactive by default
        loadInactiveCustomers();

        setVisible(true);
    }

    // ===== LOAD INACTIVE =====
    public void loadInactiveCustomers() {

        ArrayList<Object[]> data = CustomerDAO.getInactiveCustomers();

        String[] cols = {"Name", "Email"};

        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        table.setModel(model);
    }


    public void searchCustomers() {

        String keyword = searchField.getText().toLowerCase();

        ArrayList<Object[]> data = CustomerDAO.getCustomers();

        String[] cols = {"ID", "Name", "Email", "Phone", "Country"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Object[] row : data) {

            String name = row[1].toString().toLowerCase();
            String email = row[2].toString().toLowerCase();

            if (name.contains(keyword) || email.contains(keyword)) {
                model.addRow(row);
            }
        }

        table.setModel(model);
    }
}