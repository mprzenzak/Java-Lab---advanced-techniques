package com.mprzenzak.md5coder;

import com.mprzenzak.library.Coder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App extends JFrame implements ActionListener {
    private final JButton chooseFileButton;
    private final JFileChooser fileChooser;

    public App() {
        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(this);

        fileChooser = new JFileChooser();

        JPanel panel = new JPanel();
        panel.add(chooseFileButton);

        add(panel);
        setTitle("MD5 Hash Coder");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseFileButton) {
            Coder coder = new Coder();
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Path selectedFilePath = fileChooser.getSelectedFile().toPath();
                String fileNameWithExtension = selectedFilePath.getFileName().toString();
                String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
                String hashCodeFileName = fileNameWithoutExtension + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
                Path outputDirectory = Paths.get(System.getProperty("user.home"), ".snapshots");

                try {
                    byte[] fileBytes = Files.readAllBytes(selectedFilePath);
                    byte[] hashBytes = coder.computeMD5Hash(fileBytes);
                    String hashString = coder.bytesToHexString(hashBytes);
                    Files.createDirectories(outputDirectory);
                    Path matchingHashFile = coder.findHashesForThisFile(outputDirectory.toString(), fileNameWithoutExtension, selectedFilePath.toAbsolutePath().toString());

                    if (matchingHashFile != null) {
                        List<String> fileLines = Files.readAllLines(matchingHashFile, StandardCharsets.UTF_8);
                        if (fileLines.size() >= 2) {
                            String absolutePath = fileLines.get(0);
                            String currentHash = fileLines.get(1);
                            if (absolutePath.equals(selectedFilePath.toAbsolutePath().toString()) && currentHash.equals(hashString)) {
                                JOptionPane.showMessageDialog(this, "File was not modified");
                            } else {
                                JOptionPane.showMessageDialog(this, "File was modified");
                            }
                        }
                    }

                    Path outputPath = Paths.get(outputDirectory.toString(), hashCodeFileName);
                    Files.writeString(outputPath, selectedFilePath.toAbsolutePath() + "\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
                    Files.writeString(outputPath, hashString, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    JOptionPane.showMessageDialog(this, "MD5 hash: " + hashString + "\nOutput file created: " + outputPath.toAbsolutePath());

                } catch (IOException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
