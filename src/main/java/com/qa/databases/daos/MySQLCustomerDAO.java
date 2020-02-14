package com.qa.databases.daos;

import com.qa.databases.interfaces.Create;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.Update;
import com.qa.databases.persistances.Utils;

import java.sql.*;
import java.util.InputMismatchException;

import org.apache.log4j.Logger;

/**
 * this class allows a connection between java and the Customers' table
 */
public class MySQLCustomerDAO implements Create, Read, Update, Delete {
	private Connection connection;
	private String name;
	private String pWord;
	public static final Logger LOGGER = Logger.getLogger(MySQLCustomerDAO.class);

	/**
	 * use this constructor if this is your first connection to the database during
	 * the run
	 */

	public MySQLCustomerDAO() {
		LOGGER.info("User:");
		this.name = Utils.INPUT.nextLine();
		LOGGER.info("password:");
		this.pWord = Utils.INPUT.nextLine();
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.error(ele.toString());
			}
		}
	}

	/**
	 * use this constructor if you have already got access to the database
	 *
	 * @param name  - database name
	 * @param pWord - database password
	 */

	public MySQLCustomerDAO(String name, String pWord) {
		this.name = name;
		this.pWord = pWord;
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
		} catch (SQLException e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.error(ele.toString());
			}
		}
		try (Statement statement = connection.createStatement()) {
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.error(ele.toString());
			}
		}
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

	/**
	 * adds a new user into the database
	 */
	@Override
	public void create() {
		try (Statement statement = connection.createStatement()) {
			LOGGER.info("First name:");
			String first = Utils.INPUT.nextLine();
			LOGGER.info("Last Name:");
			String last = Utils.INPUT.nextLine();
			statement.executeUpdate(
					"INSERT INTO Customers(firstName,lastName) VALUES(\"" + first + "\",\"" + last + "\");");
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
		}
	}

	/**
	 * changes the names of customer by there id (authorisation needed)
	 */
	@Override
	public void update() {
		MySQLUsersDAO ud = new MySQLUsersDAO(this.name, this.pWord);
		if (ud.authenticate()) {
			try (Statement statement = connection.createStatement()) {
				LOGGER.info("Customer id:");
				int id = Utils.INPUT2.nextInt();
				LOGGER.info("Change First Name to:");
				String first = Utils.INPUT.nextLine();
				LOGGER.info("Change Last Name to:");
				String last = Utils.INPUT.nextLine();
				statement.executeUpdate("UPDATE Customers SET firstName = \"" + first + "\", lastName = \"" + last
						+ "\" WHERE id= " + id + ";");
			} catch (SQLException e) {
				LOGGER.debug(e.getStackTrace());
			} catch (InputMismatchException e) {
				LOGGER.debug(e.getStackTrace());
			}
		}
		ud.closeConnection();
	}

	/**
	 * deletes a customer from the databases (choose by id) (authorisation needed)
	 */
	@Override
	public void delete() {
		MySQLUsersDAO ud = new MySQLUsersDAO(this.name, this.pWord);
		if (ud.authenticate()) {
			try (Statement statement = connection.createStatement()) {
				LOGGER.info("Customer id:");
				int id = Utils.INPUT2.nextInt();
				statement.executeUpdate("DELETE FROM Customers WHERE id = " + id + ";");
			} catch (SQLException e) {
				LOGGER.debug(e.getStackTrace());
			} catch (InputMismatchException e) {
				LOGGER.debug(e.getStackTrace());
			}

		}
		ud.closeConnection();
	}

	/**
	 * read all customers in the database
	 *
	 * @return - Customers' table
	 */
	@Override
	public ResultSet read() {
		ResultSet resultSet = null;
		try (Statement statement = connection.createStatement()) {
			resultSet = statement.executeQuery("SELECT * FROM Customers;");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				LOGGER.info(id + " | " + firstName + " | " + lastName);
			}
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
		}
		return resultSet;
	}
}
