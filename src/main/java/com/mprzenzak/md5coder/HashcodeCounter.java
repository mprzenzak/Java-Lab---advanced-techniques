package com.mprzenzak.md5coder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class HashcodeCounter extends JFrame implements ActionListener {
    private final JButton chooseFileButton;
    private final JFileChooser fileChooser;

    public HashcodeCounter() {
        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(this);

        fileChooser = new JFileChooser();

        JPanel panel = new JPanel();
        panel.add(chooseFileButton);

        add(panel);
        setTitle("File Chooser Example");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HashcodeCounter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseFileButton) {
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Path selectedFilePath = fileChooser.getSelectedFile().toPath();
                String fileNameWithExtension = selectedFilePath.getFileName().toString();
                String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
                String hashCodeFileName = fileNameWithoutExtension + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
                Path outputDirectory = Paths.get(System.getProperty("user.home"), ".snapshots");

                try {
                    byte[] fileBytes = Files.readAllBytes(selectedFilePath);
                    byte[] hashBytes = computeMD5Hash(fileBytes);
                    String hashString = bytesToHexString(hashBytes);
                    Files.createDirectories(outputDirectory);
                    Path matchingHashFile = findHashesForThisFile(outputDirectory.toString(), fileNameWithoutExtension, selectedFilePath.toAbsolutePath().toString());

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

    public static Path findHashesForThisFile(String dir, String fileName, String selectedFilePath) throws IOException {
        Path dirPath = Paths.get(dir);
        try (Stream<Path> stream = Files.list(dirPath)) {
            Path[] filesWithThisName = stream
                    .filter(path -> path.getFileName().toString().contains(fileName))
                    .sorted((file1, file2) -> Long.compare(file2.toFile().lastModified(), file1.toFile().lastModified()))
                    .toArray(Path[]::new);

            for (Path matchingHashFile : filesWithThisName) {
                List<String> fileLines = Files.readAllLines(matchingHashFile, StandardCharsets.UTF_8);
                if (fileLines.size() >= 2) {
                    String absolutePath = fileLines.get(0);
                    if (absolutePath.equals(selectedFilePath)) {
                        return matchingHashFile;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private byte[] computeMD5Hash(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(bytes);
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
