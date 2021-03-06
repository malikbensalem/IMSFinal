package com.qa.databases.daos;

import com.qa.databases.interfaces.Create;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.Update;
import com.qa.databases.persistances.Utils;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * this class allows a connection between java and the Items' table
 */

public class MySQLItemsDAO implements Create, Read, Update, Delete {
	private Connection connection;
	private String name;
	private String pWord;
	public static final Logger LOGGER = Logger.getLogger(MySQLItemsDAO.class);

	/**
	 * use this constructor if this is your first connection to the database during
	 * the run
	 */
	public MySQLItemsDAO() {
		LOGGER.info("User:");
		this.name = Utils.INPUT.nextLine();
		LOGGER.info("password:");
		this.pWord = Utils.INPUT.nextLine();
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
		}
	}

	/**
	 * use this constructor if you have already got access to the database
	 *
	 * @param name  - database name
	 * @param pWord - database password
	 */

	public MySQLItemsDAO(String name, String pWord) {
		this.name = name;
		this.pWord = pWord;
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
		}
	}

	/**
	 * adds an item to the database (authorisation needed)
	 */
	public void create() {
		MySQLUsersDAO ud = new MySQLUsersDAO(name, pWord);
		if (ud.authenticate()) {
			try (Statement statement = connection.createStatement()) {
				Scanner input = new Scanner(System.in);
				LOGGER.info("Item name:");
				String name = Utils.INPUT.nextLine();
				LOGGER.info("Price:");
				float price = Utils.INPUT3.nextFloat();
				statement.executeUpdate("INSERT INTO Items (iName,price) VALUES( \"" + name + "\", " + price + ");");
			} catch (SQLException e) {
				LOGGER.debug(e.getStackTrace());
			} catch (InputMismatchException i) {
				LOGGER.debug(i.getStackTrace());
			}
		}
		ud.closeConnection();
	}

	/**
	 * allows you change the name and a price of an item (authorisation needed)
	 */
	public void update() {
		MySQLUsersDAO ud = new MySQLUsersDAO(name, pWord);
		if (ud.authenticate()) {
			try (Statement statement = connection.createStatement()) {
				LOGGER.info("Item id:");
				int id = Utils.INPUT2.nextInt();
				LOGGER.info("New Item name:");
				String name = Utils.INPUT.nextLine();
				LOGGER.info("New Price:");
				float price = Utils.INPUT3.nextFloat();
				statement.executeUpdate("UPDATE Items(iName,price) SET iName = \"" + name + "\", price = " + price
						+ " WHERE id= " + id + ";");
			} catch (SQLException e) {
				LOGGER.debug(e.getStackTrace());
			} catch (InputMismatchException i) {
				LOGGER.debug(i.getStackTrace());
			}
		}
		ud.closeConnection();
	}

	/**
	 * allows you to delete an item in the database (authorisation needed)
	 */
	public void delete() {
		MySQLUsersDAO ud = new MySQLUsersDAO(name, pWord);
		if (ud.authenticate()) {
			try (Statement statement = connection.createStatement()) {
				LOGGER.info("Item id:");
				int id = Utils.INPUT2.nextInt();
				statement.executeUpdate("DELETE FROM Items WHERE id = " + id + ";");
			} catch (SQLException e) {
				LOGGER.debug(e.getStackTrace());
			}
		}
		ud.closeConnection();
	}

	/**
	 * allows people to see all items in the database
	 *
	 * @return - Items' table
	 */
	public ResultSet read() {
		ResultSet resultSet = null;
		try (Statement statement = connection.createStatement()) {
			resultSet = statement.executeQuery("SELECT * FROM Items;");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("iName");
				String price = resultSet.getString("price");
				LOGGER.info(id + " | " + name + " | " + price);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
		}
		return resultSet;
	}

	/**
	 * closes this DAO down when called
	 */

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
		}
	}

}
