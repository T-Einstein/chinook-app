package com.Tsohle.chinook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReportGUI {
    JTable reportTable = new JTable();

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
