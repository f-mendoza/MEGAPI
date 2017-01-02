package com.xXFacundo.MEGAPI;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncDec {
	final static String ALGO = "AES";
	final static byte[] keyValue = {'C','3','m','6','B','1','U','l','M','d','g','y','Z','8','X','U'};
	public String Encrypt(String Data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Key key = GenerateKey();
		Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = cipher.doFinal(Data.getBytes());
		encVal = Base64.getEncoder().encode(encVal);
		String encryptedValue = new String(encVal);
		return encryptedValue;
	}
	public String Decrypt(String encryptedData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Key key = GenerateKey();
        Cipher cipher;
		cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = cipher.doFinal(decordedValue);
        String decodedValue = new String(decValue);
		return decodedValue;
	}
	private static Key GenerateKey(){
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

}
