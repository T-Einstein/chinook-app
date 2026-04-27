package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerRecommendationsGUI extends JFrame {

    JTable table;
    JComboBox<Item> customerBox;

    JLabel totalSpentLabel;
    JLabel totalPurchasesLabel;
    JLabel lastPurchaseLabel;
    JLabel favouriteGenreLabel;

    public CustomerRecommendationsGUI() {

        setTitle("Customer Recommendations");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        table = new JTable();

        // ===== TOP PANEL: DROPDOWN =====
        customerBox = new JComboBox<>();

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Customer:"));
        topPanel.add(customerBox);

        // ===== SUMMARY PANEL =====
        JPanel summaryPanel = new JPanel(new GridLayout(2, 4));

        summaryPanel.add(new JLabel("Total Spent"));
        summaryPanel.add(new JLabel("Purchases"));
        summaryPanel.add(new JLabel("Last Purchase"));
        summaryPanel.add(new JLabel("Favourite Genre"));

        totalSpentLabel     = new JLabel("-");
        totalPurchasesLabel = new JLabel("-");
        lastPurchaseLabel   = new JLabel("-");
        favouriteGenreLabel = new JLabel("-");

        summaryPanel.add(totalSpentLabel);
        summaryPanel.add(totalPurchasesLabel);
        summaryPanel.add(lastPurchaseLabel);
        summaryPanel.add(favouriteGenreLabel);

        // ===== NORTH: stack dropdown + summary =====
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(summaryPanel, BorderLayout.SOUTH);

        // ===== LAYOUT =====
        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== LOAD CUSTOMERS INTO DROPDOWN =====
        for (Item c : CustomerRecommendationDAO.getCustomers()) {
            customerBox.addItem(c);
        }

        // ===== DROPDOWN ACTION =====
        customerBox.addActionListener(e -> refreshData());

        // load initial data
        refreshData();

        setVisible(true);
    }

    public void refreshData() {

        Item selected = (Item) customerBox.getSelectedItem();
        if (selected == null) return;

        int id = selected.getId();

        // spending summary
        Object[] summary = CustomerRecommendationDAO.getSpendingSummary(id);
        totalPurchasesLabel.setText(String.valueOf(summary[0]));
        totalSpentLabel.setText(String.format("$%.2f", summary[1]));
        String date = (String) summary[2];
        lastPurchaseLabel.setText(date != null ? date.substring(0, 10) : "N/A");
        favouriteGenreLabel.setText(CustomerRecommendationDAO.getFavouriteGenre(id));

        // recommended tracks
        ArrayList<Object[]> tracks = CustomerRecommendationDAO.getRecommendedTracks(id);

        String[] cols = {"Track", "Artist", "Album", "Genre"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Object[] row : tracks) model.addRow(row);
        table.setModel(model);
    }
}