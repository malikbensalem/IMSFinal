package com.qa.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import com.qa.databases.daos.*;

public class Control {
	private String name;
	private String pWord;
	private Connection connection;

	public Control() {
		System.out.println("User:");
		
		this.name = Utils.INPUT.nextLine();
		System.out.println("password:");
		this.pWord = Utils.INPUT.nextLine();
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void menu() {
		choose(chooseCRUD(chooseTable()));
	}

	public String chooseTable() {
		
		System.out.println("What would you like to access?\n1.Customers\n2.Items\n3.Orders\n4.Users");
		String f = Utils.INPUT.nextLine();
		return f;
	}

	public String chooseCRUD(String f) {
		
		System.out.println("What would you like to access?\n1.Create\n2.Read\n3.Update\n4.Delete");
		f += Utils.INPUT.nextLine();
		return f;
	}

	public void choose(String f) {
		if (f.equals("11")) {
			MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
			custConnection.create();
			custConnection.closeConnection();
		} else if (f.equals("12")) {
			MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
			custConnection.read();
			custConnection.closeConnection();
		} else if (f.equals("13")) {
			MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
			custConnection.update();
			custConnection.closeConnection();
		} else if (f.equals("14")) {
			MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
			custConnection.delete();
			custConnection.closeConnection();
		} else if (f.equals("21")) {
			MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
			itemConnection.create();
			itemConnection.closeConnection();
		} else if (f.equals("22")) {
			MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
			itemConnection.read();
			itemConnection.closeConnection();
		} else if (f.equals("23")) {
			MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
			itemConnection.update();
			itemConnection.closeConnection();
		} else if (f.equals("24")) {
			MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
			itemConnection.delete();
			itemConnection.closeConnection();			
		} else if (f.equals("31")) {
			MySQLOrdersDAO orderConnection = new MySQLOrdersDAO(name, pWord);
			MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
			orderConnection.addTotal(oiConnection.create(orderConnection.create()));
			oiConnection.closeConnection();
			orderConnection.closeConnection();
		} else if (f.equals("32")) {
			MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
			oiConnection.read();
			oiConnection.closeConnection();
		} else if (f.equals("33")) {
			MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
			MySQLOrdersDAO orderConnection = new MySQLOrdersDAO(name, pWord);
			orderConnection.addTotal(oiConnection.update());
			oiConnection.closeConnection();
			orderConnection.closeConnection();
		} else if (f.equals("34")) {
			MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
			oiConnection.delete();
			oiConnection.closeConnection();
		} else if (f.equals("41")) {
			MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
			userConnection.create();
			userConnection.closeConnection();
		} else if (f.equals("42")) {
			MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
			userConnection.read();
			userConnection.closeConnection();
		} else if (f.equals("43")) {
			MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
			userConnection.update();
			userConnection.closeConnection();
		} else if (f.equals("44")) {
			MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
			userConnection.delete();
			userConnection.closeConnection();
		}
	}
}
