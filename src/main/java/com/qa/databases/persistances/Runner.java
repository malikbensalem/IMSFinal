package com.qa.databases.persistances;

import java.sql.SQLException;

public class Runner {

	public static void main(String[] args) throws SQLException{
		Control control = new Control();
		String cont="y";
		while (!cont.equals("n")) {
			control.menu();
			System.out.println("Do you want to continue(Y/N)?");
			cont=Utils.INPUT.nextLine().toLowerCase();
		}

	}
}