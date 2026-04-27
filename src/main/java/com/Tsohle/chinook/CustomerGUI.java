package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerGUI extends JFrame {

    JTable table;

    JTextField nameField;
    JTextField emailField;
    JTextField phoneField;
    JTextField countryField;

    public CustomerGUI() {

        setTitle("Customers");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        table = new JTable();

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        countryField = new JTextField();

        inputPanel.add(new JLabel("Name"));
        inputPanel.add(new JLabel("Email"));
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(new JLabel("Country"));

        inputPanel.add(nameField);
        inputPanel.add(emailField);
        inputPanel.add(phoneField);
        inputPanel.add(countryField);

        // ===== BUTTONS =====
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        // ===== LAYOUT =====
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====

        // CREATE
        addBtn.addActionListener(e -> {
            CustomerDAO.addCustomer(
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    countryField.getText()
            );
            loadCustomers();
        });

        // UPDATE (uses selected row)
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            int id = (int) table.getValueAt(row, 0);

            CustomerDAO.updateCustomer(
                    id,
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    countryField.getText()
            );

            loadCustomers();
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            int id = (int) table.getValueAt(row, 0);

            CustomerDAO.deleteCustomer(id);
            loadCustomers();
        });

        // REFRESH
        refreshBtn.addActionListener(e -> loadCustomers());

        // Load initial data
        loadCustomers();

        setVisible(true);
    }

    public void loadCustomers() {

        ArrayList<Object[]> data = CustomerDAO.getCustomers();

        String[] cols = {"ID", "Name", "Email", "Phone", "Country"};

        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        table.setModel(model);
    }
}