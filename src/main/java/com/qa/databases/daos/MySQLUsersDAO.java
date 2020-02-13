package com.qa.databases.daos;

import com.qa.databases.interfaces.Create;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.Update;
import com.qa.databases.persistances.Control;
import com.qa.databases.persistances.Utils;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * this class allows a connection between java and the Users' table
 * (all methods in this class need authorisation by a user)
 */

public class MySQLUsersDAO implements Create, Read, Update, Delete {
    private Connection connection;
    public static final Logger LOGGER = Logger.getLogger(Control.class);
    

    /**
     * use this constructor if this is your first connection to the database during the run
     */
    public MySQLUsersDAO() {
        System.out.println("User:");
        String name = Utils.INPUT.nextLine();
        System.out.println("password:");
        String pWord = Utils.INPUT.nextLine();
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
    public MySQLUsersDAO(String name, String pWord) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
        }
    }

    /**
     * needs a verified user to return true
     */
    public boolean authenticate() {
        System.out.println("Admin User:");
        String name = Utils.INPUT.nextLine();
        System.out.println("Admin password:");
        String pWord = Utils.INPUT.nextLine();
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(
                    "SELECT username FROM Users WHERE username = \"" + name + "\" AND pWord = \"" + pWord + "\";");
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.debug(e.getStackTrace());
        }
        return false;
    }

    /**
     * adds a user into the database
     */
    public void create() {
        if (authenticate()) {
            try (Statement statement = connection.createStatement()) {
                System.out.println("New Username:");
                String name = Utils.INPUT.nextLine();
                System.out.println("password:");
                String pWord = Utils.INPUT.nextLine();
                statement.executeUpdate(
                        "INSERT INTO Users(username,pWord) values(\"" + name + "\", \"" + pWord + "\");");
            } catch (SQLException e) {
                LOGGER.debug(e.getStackTrace());
            }
        }
    }

    /**
     * allows user to change password
     */
    public void update() {
        if (authenticate()) {
            try (Statement statement = connection.createStatement()) {
                System.out.println("Username you want to change the password for:");
                String name = Utils.INPUT.nextLine();
                System.out.println("New Password:");
                String pWord = Utils.INPUT.nextLine();
                statement
                        .executeUpdate("UPDATE Users SET pWord = \"" + pWord + "\" WHERE username = \"" + name + "\";");
            } catch (SQLException e) {
                LOGGER.debug(e.getStackTrace());
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
     * deletes a user from the database
     */
    public void delete() {
        if (authenticate()) {
            try (Statement statement = connection.createStatement()) {
                System.out.println("Item ID:");
                String name = Utils.INPUT.nextLine();
                statement.executeUpdate("DELETE FROM Users WHERE username = \"" + name + "\";");
            } catch (SQLException e) {
                LOGGER.debug(e.getStackTrace());
            }
        }
    }

    /**
     * allows users to see other users on the database
     *
     * @return - the users table
     */
    public ResultSet read() {
        ResultSet resultSet = null;
        if (authenticate()) {
            try (Statement statement = connection.createStatement()) {
                resultSet = statement.executeQuery("SELECT * FROM Users;");
                while (resultSet.next()) {
                    String name = resultSet.getString("username");
                    String pWord = resultSet.getString("pWord");
                    System.out.println(name + " | " + pWord);
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
            }
        }
        return resultSet;
    }
}
