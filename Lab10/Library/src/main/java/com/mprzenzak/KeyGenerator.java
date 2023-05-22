package com.mprzenzak;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class KeyGenerator {

	private KeyPairGenerator keyPairGenerator;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public KeyGenerator(int keylength) throws NoSuchAlgorithmException {
		this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		this.keyPairGenerator.initialize(keylength);
	}

	public void createKeys() {
		this.pair = this.keyPairGenerator.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
		try {
			writeToFile("Keys/publicKey", getPublicKey().getEncoded());
			writeToFile("Keys/privateKey", getPrivateKey().getEncoded());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File file = new File(path);
		file.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(key);
		fos.flush();
		fos.close();
	}

}