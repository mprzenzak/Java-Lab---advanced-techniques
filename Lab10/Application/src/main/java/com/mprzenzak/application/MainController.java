package com.mprzenzak.application;

import com.mprzenzak.EncryptionMethod;
import com.mprzenzak.KeyGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.security.*;

import com.mprzenzak.EncryptorDecryptor;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onEncryptButtonClick() throws Exception {
        testEncryptionAndDecryption();
    }

    @FXML
    private Label infoLabel;

    @FXML
    protected void onGenerateKeysButtonClick() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = new KeyGenerator(1024);
            keyGenerator.createKeys();
            keyGenerator.writeToFile("Keys/publicKey", keyGenerator.getPublicKey().getEncoded());
            keyGenerator.writeToFile("Keys/privateKey", keyGenerator.getPrivateKey().getEncoded());
            infoLabel.setText("Private and public keys has been created in Keys directory.");
        } catch (NoSuchAlgorithmException | IOException e) {
            infoLabel.setText(e.getMessage());
        }
    }

    public void testEncryptionAndDecryption() throws Exception {
        String encrypted = "messages/message_encrypted.txt";
        String decrypted = "messages/message_decrypted.txt";
        EncryptionMethod method = EncryptionMethod.RSA;
        EncryptorDecryptor ac = new EncryptorDecryptor(method.name());
        PrivateKey privateKey = ac.getPrivate("Keys/privateKey", method.name());
        PublicKey publicKey = ac.getPublic("Keys/publicKey", method.name());
        if (new File("messages/message.txt").exists()) {
            ac.encryptFile(ac.getFileInBytes(new File("messages/message.txt")), new File(encrypted), privateKey);
            ac.decryptFile(ac.getFileInBytes(new File(encrypted)), new File(decrypted), publicKey);
        } else {
            System.out.println("Put a message in message.txt file in messages directory.");
        }
    }
}