package com.mprzenzak;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class App {
    private JFrame frame;
    private JPanel panelFiles;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnSelect;
    private JLabel question = new JLabel();
    private JLabel check = new JLabel();
    private JTextField answerField = new JTextField();
    public App() {
        initComponents();
    }

    private void initComponents() throws IOException {
        frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1280, 720));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panelFiles = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelFiles);
        frame.add(scrollPane, BorderLayout.CENTER);
        //JLabel question = new JLabel("Type the song author name.");
        btnSelect = new JButton("Submit");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        frame.add(question, BorderLayout.NORTH);
        frame.add(answerField, BorderLayout.NORTH);
        frame.add(btnSelect, BorderLayout.NORTH);
        frame.add(check, BorderLayout.NORTH);

        frame.setVisible(true);
        getRandomQuestion();
    }

    public void getRandomQuestion() throws IOException {
        String apiUrl = "https://musicbrainz.org/ws/2/recording?limit=1&offset=" + (int) (Math.random() * 10000);

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer YOUR_API_TOKEN_HERE");

        Scanner scanner = new Scanner(connection.getInputStream());
        scanner.useDelimiter("\\A");
        String responseBody = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray recordings = responseJson.getJSONArray("recordings");
        JSONObject recording = recordings.getJSONObject(0);
        String artistName = recording.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name");
        String songTitle = recording.getString("title");
        String releaseDate = recording.getJSONArray("release-groups").getJSONObject(0).getString("first-release-date");

        this.question.setText("Enter the artist of the song \"" + songTitle + "\": ");
        String userInput = this.answerField.getText();

        System.out.println("The song \"" + songTitle + "\" was released by " + artistName + " on " + releaseDate + ".");
        if (userInput.equalsIgnoreCase(artistName)) {
            this.check.setText("You are correct!");
        } else {
            this.check.setText("You are incorrect. The correct answer is " + artistName + ".");
        }
    }
    public static void main(String[] args) {
        new App();
    }
}
