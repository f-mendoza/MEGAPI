package com.xXFacundo.MEGAPI;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipManager {
	private ZipParameters parameters;
	private String path;
	public ZipManager(){
		parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
		parameters.setIncludeRootFolder(false);
	}
	public void setPassword(String password){
		if (password.length() > 0){
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		}else{
			System.out.println("ERROR: The password is not valid");
		}
	}
	public void setPath(String path){
		File filePath = new File(path);
		if (filePath.getParentFile().exists()){
			this.path = path;
		}else{
			System.out.println("ERROR: The directory not exists.");
		}
	}
	public void createZip(ArrayList<File> files){
		try {
			ZipFile zip = new ZipFile(path);
			for (File file : files){
				if (file.exists()){
					if (file.isDirectory()){
						zip.addFolder(file, parameters);
					}else{
						zip.addFile(file, parameters);
					}
				}else{
					System.out.println("ERROR: The file "+file.getAbsolutePath()+" doesn't exists.");
				}
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
	}
}
