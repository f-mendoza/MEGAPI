package com.xXFacundo.MEGAPI;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.Ale46.MegaJava.*;
import org.json.*;

public class MEGAPI {
	public static void main(String[] args){
		EncDec ed = new EncDec();
		try {
			ed.Encrypt("hola mundo");
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
	}
}