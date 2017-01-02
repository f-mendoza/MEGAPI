package com.xXFacundo.MEGAPI;

import java.io.IOException;
import java.util.List;
import com.Ale46.MegaJava.*;
import org.json.*;

public class MEGAPI {
	SQLManager sql = new SQLManager("C:/Users/Facundo Mendoza/Desktop/data.db");
	public static void main(String[] args){
		MegaHandler mh = new MegaHandler("xxfacundo@hotmail.com","Facundo05");
		try {
			mh.login();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(mh.get_user());
	}
	public List<String> ListAccounts(){
		return sql.ListAccounts();
	}
	public void DownloadFile(String link){
		
	}
}