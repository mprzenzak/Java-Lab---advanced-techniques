package com.mprzenzak;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    private JFrame frame;
    private JButton showQuestionButton;
    private JButton submitButton;
    private JPanel responsePanel;
    private JLabel question = new JLabel();
    private JLabel check = new JLabel();
    private JTextField answerField = new JTextField();
    private String songArtist;
    private String json;
    private QuestionType questionType;
    private String correctAnswer;
    private List<JRadioButton> radioButtons = new ArrayList<>();

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
        panel.setLayout(new GridLayout(3, 2));
        frame.add(panel, BorderLayout.CENTER);

        showQuestionButton = new JButton("Show question");
        submitButton = new JButton("Submit answer");
        JButton showHintButton = new JButton("Show hint");

        showQuestionButton.addActionListener(e -> {
            try {
                check.setText("");
                String answerFieldHintText = "Enter your answer here";
                answerField.setText(answerFieldHintText);
                answerField.setForeground(Color.GRAY);
                answerField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (answerField.getText().equals(answerFieldHintText)) {
                            answerField.setText("");
                            answerField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (answerField.getText().isEmpty()) {
                            answerField.setText(answerFieldHintText);
                            answerField.setForeground(Color.GRAY);
                        }
                    }
                });
                getRandomQuestion();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        submitButton.addActionListener(e -> submitAnswer(questionType));
        showHintButton.addActionListener(e -> showHint());

        answerField.setPreferredSize(new Dimension(400, 20));
        answerField.setText("Enter your answer here");
        answerField.setForeground(Color.GRAY);


        panel.add(showQuestionButton);
        panel.add(submitButton);
        panel.add(question);

        responsePanel = new JPanel();
        panel.add(responsePanel);

        panel.add(showHintButton);
        panel.add(check);

        frame.setVisible(true);
    }

    private void showHint() {
        check.setText(correctAnswer);
    }

    private String getAnswerFromRadioButton() {
        for (JRadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                return radioButton.getText();
            }
        }
        return null;
    }

    private void getRandomQuestion() throws IOException {
        questionType = getRandomQuestionType();
        switch (questionType) {
            case SONG_TITLE -> getRandomSong();
            case BAND_SONGS_NUMBER, BAND_CAREER_START -> getRandomBand();
        }
        updateResponsePanel();
    }

    private void updateResponsePanel() {
        responsePanel.removeAll();
        responsePanel.setLayout(new GridLayout());

        if (questionType == QuestionType.BAND_SONGS_NUMBER) {
            Random random = new Random();

            String randomResponse1 = Integer.toString(random.nextInt(100) + 1);
            String randomResponse2 = Integer.toString(random.nextInt(100) + 1);

            List<String> possibleAnswers = new ArrayList<>();
            possibleAnswers.add(randomResponse1);
            possibleAnswers.add(randomResponse2);
            possibleAnswers.add(correctAnswer);

            int selectedIndex = random.nextInt(possibleAnswers.size());
            String possibleAnswer = possibleAnswers.get(selectedIndex);
            JRadioButton radioButton1 = new JRadioButton(possibleAnswer);
            possibleAnswers.remove(selectedIndex);

            selectedIndex = random.nextInt(possibleAnswers.size());
            possibleAnswer = possibleAnswers.get(selectedIndex);
            JRadioButton radioButton2 = new JRadioButton(possibleAnswer);
            possibleAnswers.remove(selectedIndex);

            selectedIndex = random.nextInt(possibleAnswers.size());
            possibleAnswer = possibleAnswers.get(selectedIndex);
            JRadioButton radioButton3 = new JRadioButton(possibleAnswer);
            possibleAnswers.remove(selectedIndex);

            radioButtons.add(radioButton1);
            radioButtons.add(radioButton2);
            radioButtons.add(radioButton3);

            ButtonGroup radioGroup = new ButtonGroup();
            radioGroup.add(radioButton1);
            radioGroup.add(radioButton2);
            radioGroup.add(radioButton3);

            JPanel radioPanel = new JPanel(new GridLayout(3, 1));
            radioPanel.add(radioButton1);
            radioPanel.add(radioButton2);
            radioPanel.add(radioButton3);

            responsePanel.add(radioPanel);
        } else {
            responsePanel.add(answerField);
        }

        responsePanel.revalidate();
        responsePanel.repaint();
    }

    private void getRandomSong() throws IOException {
        URL url = new URL("https://musicbrainz.org/ws/2/release/?query=primarytype:album&fmt=json&limit=1&offset=" + (int) (Math.random() * 100000));
        json = sendGETRequest(url);
        String title = getTitleFromJson(json);
        correctAnswer = getArtistFromSongJson(json);
        question.setText("<html>What is the name of the artist of the song:<br>\"" + title +"\"?</html>");
    }

    private void getRandomBand() throws IOException {
        URL url = new URL("https://musicbrainz.org/ws/2/artist/?query=type:group&fmt=json&limit=1&offset=" + (int) (Math.random() * 100000));
        json = sendGETRequest(url);
        songArtist = getArtistFromBandJson(json);
        switch (questionType) {
            case BAND_SONGS_NUMBER -> {
                correctAnswer = getNumberOfSongsFromBandJson(json);
                question.setText("How many songs has " + songArtist + " released?");
            }
            case BAND_CAREER_START -> {
                correctAnswer = getCareerStartFromBandJson(json);
                question.setText("When did the " + songArtist + " started career?");
            }
        }
    }

    private void submitAnswer(QuestionType questionType) {
        switch (questionType) {
            case SONG_TITLE -> submitSongTitleAnswer();
            case BAND_SONGS_NUMBER -> submitBandSongsNumberAnswer();
            case BAND_CAREER_START -> submitBandCareerStartAnswer();
        }
    }

    private void submitBandCareerStartAnswer() {
        String answer = answerField.getText();
        if (answer.equals(correctAnswer)) {
            check.setText("Correct!");
        } else {
            check.setText("Incorrect! The correct answer is: " + correctAnswer + ".");
        }
    }

    private void submitBandSongsNumberAnswer() {
        String answer = getAnswerFromRadioButton();
        if (answer.equals(correctAnswer)) {
            check.setText("Correct!");
        } else {
            check.setText("Incorrect! The correct answer is: " + correctAnswer + ".");
        }
    }

    private void submitSongTitleAnswer() {
        String answer = answerField.getText();
        if (answer.equals(correctAnswer)) {
            check.setText("Correct!");
        } else {
            check.setText("Incorrect! The correct answer is: " + correctAnswer + ".");
        }
    }

    private static String getTitleFromJson(String json) {
        Gson gson = new Gson();
        JsonDataSong jsonDataSong = gson.fromJson(json, JsonDataSong.class);
        return jsonDataSong.getReleases().get(0).getTitle();
    }

    private static String getArtistFromSongJson(String json) {
        Gson gson = new Gson();
        JsonDataSong jsonDataSong = gson.fromJson(json, JsonDataSong.class);
        return jsonDataSong.getReleases().get(0).getArtistCredit().get(0).getName();
    }

    private static String getNumberOfSongsFromBandJson(String json) {
        Gson gson = new Gson();
        JsonDataBand jsonDataSong = gson.fromJson(json, JsonDataBand.class);
        return jsonDataSong.getArtists().get(0).getScore();
    }

    private String getCareerStartFromBandJson(String json) {
        Gson gson = new Gson();
        JsonDataBand jsonDataSong = gson.fromJson(json, JsonDataBand.class);
        return jsonDataSong.getArtists().get(0).getLifeSpan().getBegin();
    }

    private static String getArtistFromBandJson(String json) {
        Gson gson = new Gson();
        JsonDataBand jsonDataBand = gson.fromJson(json, JsonDataBand.class);
        return jsonDataBand.getArtists().get(0).getName();
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

    public static QuestionType getRandomQuestionType() {
        QuestionType[] questionTypes = QuestionType.values();
        Random random = new Random();
        int randomIndex = random.nextInt(questionTypes.length);
        return questionTypes[randomIndex];
    }

    public static void main(String[] args) {
        new App();
    }
}
