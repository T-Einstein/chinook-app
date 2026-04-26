package com.Tsohle.chinook;

import javax.swing.*;
import java.awt.*;

public class AddTrackDialog extends JDialog {

    JTextField trackNameField;
    JComboBox<Item> albumBox;
    JComboBox<Item> genreBox;
    JComboBox<Item> mediaTypeBox;

    public AddTrackDialog(TracksGUI parent) {
        super(parent, "Add Track", true);

        setSize(450, 320);
        setLocationRelativeTo(parent);          // centres the dialog

        // Use BoxLayout instead of GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // INPUT FIELD
        trackNameField = new JTextField(20);

        // DROPDOWNS
        albumBox     = new JComboBox<>();
        genreBox     = new JComboBox<>();
        mediaTypeBox = new JComboBox<>();

        // LOAD DATA
        for (Item a : TrackDAO.getAlbums())  albumBox.addItem(a);
        for (Item g : TrackDAO.getGenres())  genreBox.addItem(g);
        for (Item m : TrackDAO.getMedia())   mediaTypeBox.addItem(m);

        // BUILD ROWS
        panel.add(makeRow("Track Name", trackNameField));
        panel.add(Box.createVerticalStrut(8));
        panel.add(makeRow("Album",      albumBox));
        panel.add(Box.createVerticalStrut(8));
        panel.add(makeRow("Genre",      genreBox));
        panel.add(Box.createVerticalStrut(8));
        panel.add(makeRow("Media Type", mediaTypeBox));
        panel.add(Box.createVerticalStrut(15));

        // SAVE BUTTON
        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(e -> {
            String name  = trackNameField.getText().trim();
            Item album   = (Item) albumBox.getSelectedItem();
            Item genre   = (Item) genreBox.getSelectedItem();
            Item media   = (Item) mediaTypeBox.getSelectedItem();

            if (name.isEmpty() || album == null || genre == null || media == null) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            TrackDAO.insertTrack(name, album.getId(), genre.getId(), media.getId());
            JOptionPane.showMessageDialog(this, "Track added successfully!");
            parent.loadTracks();
            dispose();
        });

        panel.add(saveButton);
        add(panel);

         
    }

    // Helper to create a label + field row
    private JPanel makeRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(90, 25));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
}