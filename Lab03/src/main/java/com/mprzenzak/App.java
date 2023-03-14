package com.mprzenzak;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {
    private JFrame frame;
    private JButton showQuestionButton;
    private JButton submitButton;
    private JLabel question = new JLabel();
    private JLabel check = new JLabel();
    private JTextField answerField = new JTextField();

    private String json;

    public App() {
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 400));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setLayout(new FlowLayout());
        frame.add(panel, BorderLayout.CENTER);
        boolean fileLoadedFromCache = false;

        showQuestionButton = new JButton("Show question");
        submitButton = new JButton("Submit answer");
        showQuestionButton.addActionListener(e -> {
            try {
                getRandomQuestion();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        submitButton.addActionListener(e -> submitAnswer());

        answerField.setSize(100, 20);
        answerField.setText("Enter your answer here");

        panel.add(showQuestionButton);
        panel.add(question);
        panel.add(answerField);
        panel.add(submitButton);
        panel.add(check);

        frame.setVisible(true);
    }

    public void getRandomQuestion() throws IOException {
        URL apiUrl = new URL("https://musicbrainz.org/ws/2/release/?query=*&limit=1&offset=" + (int) (Math.random() * 1000));
        json = sendGETRequest(apiUrl);
        String songTitle = getTitleFromJson(json);
        String songArtist = getArtistFromJson(json);
        question.setText("Hint: " + songArtist + "Enter the artist of the song \"" + songTitle + "\": ");
}

    private void submitAnswer() {
        String answer = answerField.getText();
        String artistName = getArtistFromJson(json);
        if (answer.equals(artistName)) {
            check.setText("Correct!");
        } else {
            check.setText("Incorrect! The correct answer is: " + artistName + ".");
        }
    }

    private static String getTitleFromJson(String json) {
        Gson gson = new Gson();
        JsonData jsonData = gson.fromJson(json, JsonData.class);
        return jsonData.getReleases().get(0).getTitle();
    }

    private static String getArtistFromJson(String json) {
        Gson gson = new Gson();
        JsonData jsonData = gson.fromJson(json, JsonData.class);
        return jsonData.getReleases().get(0).getArtistCredit().get(0).getName();
    }

    private static String sendGETRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/json");

        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        new App();
    }
}
