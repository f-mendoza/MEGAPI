package com.xXFacundo.MEGAPI;

import java.util.List;

public class MEGAPI {
	public static void main(String[] args){
		SQLManager sql = new SQLManager("C:/Users/Facundo Mendoza/Desktop/data.db");
		List<String> list = sql.ListAccounts();
		for(String item : list){
			System.out.println(item);
		}
	}
}