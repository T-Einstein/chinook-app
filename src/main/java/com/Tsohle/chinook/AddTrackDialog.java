package com.Tsohle.chinook;

import java.util.ArrayList;
import javax.swing.*;

import java.awt.*;

public class AddTrackDialog extends JDialog{

    JTextField trackNameField; 
    JComboBox<String[]> albumBox; 
    JComboBox<String[]> genreBox;
    JComboBox<String[]> mediaTypeBox;

    public AddTrackDialog(JFrame parent){
        super(parent, "Add Track", true);

        setSize(400,300);
        setLayout(new GridLayout(5,2));

        trackNameField = new  JTextField();
        albumBox = new JComboBox<String[]>();
        genreBox = new JComboBox<String[]>();
        mediaTypeBox = new JComboBox<String[]>();


        add(new JLabel("Track Name"));
        add(trackNameField);

        add(new JLabel("Album"));
        add(albumBox);

        add(new JLabel("Genre"));
        add(genreBox);

        add(new JLabel("Media Type"));
        add(mediaTypeBox);

        ArrayList<String[]> albums = TrackDAO.getAlbums();
        for (String[] a : albums) {
            albumBox.addItem(a);
        }

        ArrayList<String[]> genres = TrackDAO.getGenres();
        for (String[] a : genres) {
            genreBox.addItem(a);
        }

        ArrayList<String[]> medias = TrackDAO.getMedia();
        for (String[] a : medias) {
            mediaTypeBox.addItem(a);
        }

        albumBox.setRenderer((list, value, index, isSelected, cellHasFocus) ->
    new JLabel(value[1])
);

genreBox.setRenderer((list, value, index, isSelected, cellHasFocus) ->
    new JLabel(value[1])
);

mediaTypeBox.setRenderer((list, value, index, isSelected, cellHasFocus) ->
    new JLabel(value[1])
);
       

        JButton saveButton = new JButton("Save");

       saveButton.addActionListener(e -> {

    String name = trackNameField.getText();


    String[] album = (String[]) albumBox.getSelectedItem();
    String[] genre = (String[]) genreBox.getSelectedItem();
    String[] media = (String[]) mediaTypeBox.getSelectedItem();

  
    int albumId = Integer.parseInt(album[0]);
    int genreId = Integer.parseInt(genre[0]);
    int mediaId = Integer.parseInt(media[0]);

    // 4. Call DAO.insertTrack(...)
    TrackDAO.insertTrack(name, albumId, genreId, mediaId);

    // feedback
    JOptionPane.showMessageDialog(this, "Track added successfully!");

    // 5. Close dialog
    dispose();

     
    });



        add(saveButton);


        setVisible(true);
    }

    
}
