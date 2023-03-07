package com.mprzenzak.library;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Stream;

public class Coder{
    public Path findHashesForThisFile(String dir, String fileName, String selectedFilePath) throws IOException {
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

    public byte[] computeMD5Hash(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(bytes);
    }

    public String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
