package com.Tsohle.chinook;
import java.sql.*; 
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddTrackDialog extends JDialog{

    JTextField trackNameField; 
    JComboBox<String> albumBox; 
    JComboBox<String> genreBox;
    JComboBox<String> mediaTypeBox;

    public AddTrackDialog(JFrame parent){
        super(parent, "Add Track", true);

        setSize(400,300);
        setLayout(new GridLayout(5,2));

        trackNameField = new  JTextField();
        albumBox = new JComboBox<>();
        genreBox = new JComboBox<>();
        mediaTypeBox = new JComboBox<>();


        add(new JLabel("Track Name"));
        add(trackNameField);

        add(new JLabel("Album"));
        add(albumBox);

        add(new JLabel("Genre"));
        add(genreBox);

        add(new JLabel("Media Type"));
        add(mediaTypeBox);

        ArrayList<String> albums = TracksDAO.getAlbums();
        for (String a : albums) {
            albumBox.addItem(a);
        }

        ArrayList<String> genres = TracksDAO.getGenres();
        for (String a : genres) {
            genreBox.addItem(a);
        }

        ArrayList<String> medias = TracksDAO.getMedia();
        for (String a : medias) {
            mediaTypeBox.addItem(a);
        }

       

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> {

            String trackName = trackNameField.getText();
            String album = (String) albumBox.getSelectedItem();
            String genre = (String) genreBox.getSelectedItem();
            String media = (String) mediaTypeBox.getSelectedItem();

             if (trackName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Track name required");
                    return;
                }

            TracksDAO.insertTrack(trackName, album, genre, media);

            dispose(); 
        });



        add(saveButton);


        setVisible(true);
    }
}
