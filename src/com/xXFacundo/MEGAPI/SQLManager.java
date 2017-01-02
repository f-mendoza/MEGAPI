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
	//START constructor
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
	//END constructor
	//START InsertUser
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
	//END InsertUser
	//START ListAccounts
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
			stmt.close();
		} catch (SQLException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return list;
	}
	//END ListAccounts
	//START GetEmail
	public String GetEmail(int id){
		String sql= "SELECT email FROM accounts WHERE id = ?;";
		String result = null;
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()){
				result = EncDec.Decrypt(rs.getString("email"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}
	//END GetEmail
	//START GetPassword
	public String GetPassword(int id){
		String sql = "SELECT pass FROM accounts WHERE id = ?";
		String result = null;
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()){
				result = EncDec.Decrypt(rs.getString("pass"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}
	//END GetPassword
	public void DeleteAccount(String email){
		String sql = "DELETE FROM accounts WHERE email = ?;";
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void CloseConnection(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
