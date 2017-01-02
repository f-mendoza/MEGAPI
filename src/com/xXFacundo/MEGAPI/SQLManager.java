package com.xXFacundo.MEGAPI;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SQLManager {
	Connection c;
	Statement stmt;
	PreparedStatement pstmt;
	EncDec EncDec = new EncDec();
	public SQLManager(String path){
		String sql = "CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, email TEXT NOT NULL, pass TEXT NOT NULL);";
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+path);
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	public void CreateUserTable(){
		
	}
	public void InsertUser(String user, String pass){
		String encryptedUser = null;
		String encryptedPass = null;
		try {
			encryptedUser = EncDec.Encrypt(user);
			encryptedPass = EncDec.Encrypt(pass);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO accounts(email,pass) VALUES (?,?);";
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, encryptedUser);
			pstmt.setString(2, encryptedPass);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<String> ListAccounts(){
		String sql = "SELECT id,email FROM accounts;";
		List<String> list = new ArrayList<>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				list.add(rs.getInt("id")+") "+EncDec.Decrypt(rs.getString("email")));
			}
			rs.close();
		} catch (SQLException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void CloseConnection(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
