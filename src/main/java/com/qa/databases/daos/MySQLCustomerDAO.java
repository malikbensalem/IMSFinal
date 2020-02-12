//close everything
//make everything at the start

package com.qa.databases.daos;

import com.qa.databases.persistances.Utils;
import com.qa.databases.interfaces.Create;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.Update;

import java.sql.*;

public class MySQLCustomerDAO implements Create, Read, Update, Delete {
	private Connection connection;
	private String name;
	private String pWord;

	public MySQLCustomerDAO() {
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
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public MySQLCustomerDAO(String name, String pWord) {
		this.name = name;
		this.pWord = pWord;
		try (Statement statement=connection.createStatement()){
			this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void create() {
		try (Statement statement=connection.createStatement()){
			System.out.println("First name:");
			String first = Utils.INPUT.nextLine();
			System.out.println("Last name:");
			String last = Utils.INPUT.nextLine();
			statement
					.executeUpdate("INSERT INTO Customers(firstName,lastName) VALUES(\"" + first + "\",\"" + last + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		MySQLUsersDAO UD = new MySQLUsersDAO(this.name, this.pWord);
		if (UD.authenticate()) {
			try (Statement statement=connection.createStatement()){
				System.out.println("Customer ID:");
				int ID = Utils.INPUT2.nextInt();
				System.out.println("Change First Name to:");
				String first = Utils.INPUT.nextLine();
				System.out.println("Change Last Name to:");
				String last = Utils.INPUT.nextLine();
				statement.executeUpdate("UPDATE Customers SET firstName = \"" + first + "\", lastName = \"" + last
						+ "\" WHERE ID= " + ID + ";");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}UD.closeConnection();
	}

	public void delete() {
		MySQLUsersDAO UD = new MySQLUsersDAO(this.name, this.pWord);
		if (UD.authenticate()) {
			try (Statement statement=connection.createStatement()){
				System.out.println("Customer ID:");
				int ID = Utils.INPUT2.nextInt();
				statement.executeUpdate("DELETE FROM Customers WHERE ID = " + ID + ";");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}UD.closeConnection();
	}

	public ResultSet read() {
		ResultSet resultSet = null;
		try (Statement statement=connection.createStatement()){
			resultSet = statement.executeQuery("SELECT * FROM Customers;");
			while (resultSet.next()) {
				int ID = resultSet.getInt("ID");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				System.out.println(ID +" | "+ firstName +" | "+ lastName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}
}
