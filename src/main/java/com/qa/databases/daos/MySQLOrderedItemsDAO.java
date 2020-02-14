package com.qa.databases.daos;

import com.qa.databases.interfaces.CreateParam;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.UpdateReturn;
import com.qa.databases.persistances.Utils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.InputMismatchException;

/**
 * this class allows a connection between java and the OrderedItems' table
 */

public class MySQLOrderedItemsDAO implements CreateParam, UpdateReturn, Delete {

    private String name;
    private String pWord;
    private Connection connection;
    public static final Logger LOGGER = Logger.getLogger(MySQLOrderedItemsDAO.class);
    

    /**
     * use this constructor if this is your first connection to the database during the run
     */

    public MySQLOrderedItemsDAO() {
        LOGGER.info("User:");
        String name = Utils.INPUT.nextLine();
        LOGGER.info("password:");
        String pWord = Utils.INPUT.nextLine();
        this.name = name;
        this.pWord = pWord;
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

    public MySQLOrderedItemsDAO(String name, String pWord) {
        this.name = name;
        this.pWord = pWord;
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
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
     * allows customers to add orders to there basket
     *
     * @param ordersID - this will be order number of the order
     * @return - this will return the OrdersID so that total amount can be calculated for this specific order
     */
    public int create(int ordersID) {
        int itemsID = -1;
        try (Statement statement = connection.createStatement()) {
            LOGGER.info("Item's ID: \n(press 0 once you are done)");
            while (true) {
                itemsID = Utils.INPUT2.nextInt();
                if (itemsID <= 0) {
                    break;
                }
                statement.executeUpdate(
                        "INSERT INTO OrderedItems (ordersID,itemsID) VALUES(" + ordersID + ", " + itemsID + ");");
                LOGGER.info(">");
            }
        } catch (SQLException e) {
            LOGGER.debug(e.getStackTrace());
        } catch (InputMismatchException i) {
        	LOGGER.debug(i.getStackTrace());
        }
        return ordersID;
    }

    /**
     * shows the details of a specific order that a customer has made
     *
     * @return - specific customer's order
     */
    public ResultSet read() {
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            LOGGER.info("OrderID:");
            int oID = Utils.INPUT2.nextInt();
            resultSet = statement
                    .executeQuery("SELECT * FROM OrderedItems WHERE ordersID = " + oID + ";");
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                int itemsID = resultSet.getInt("itemsID");
                int ordersID = resultSet.getInt("ordersID");
                LOGGER.info(ID + " | " + itemsID + " | " + ordersID);
            }
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
        }
        return resultSet;
    }

    /**
     * allows customer to change there item
     *
     * @return
     */

    public int update() {
        int ordersID = -1;
        try (Statement statement = connection.createStatement()) {
            LOGGER.info("order's ID to be changed:");
            ordersID = Utils.INPUT2.nextInt();
            LOGGER.info("Item's ID to be changed:");
            int oldItem = Utils.INPUT2.nextInt();
            LOGGER.info("new Item's ID:");
            int newItem = Utils.INPUT2.nextInt();
            statement.executeUpdate("UPDATE OrderedItems SET ItemsID = " + newItem + " WHERE ItemsID = " + oldItem
                    + "AND ordersID = " + ordersID + ";");
        } catch (SQLException e) {
            LOGGER.debug(e.getStackTrace());
        }catch (InputMismatchException i) {
        	LOGGER.debug(i.getStackTrace());
        }
        return ordersID;
    }

    /**
     * deletes an item from an order
     */
    public void delete() {
        try (Statement statement = connection.createStatement()) {
            LOGGER.info("OrderID:");
            int orderID = Utils.INPUT2.nextInt();
            statement.executeUpdate("DELETE FROM OrderedItems WHERE OrdersID = " + orderID + ";");
            MySQLOrdersDAO o = new MySQLOrdersDAO(name, pWord);
            o.addTotal(orderID);

        } catch (SQLException e) {
            LOGGER.debug(e.getStackTrace());
        }catch (InputMismatchException i) {
        	LOGGER.debug(i.getStackTrace());
        }
    }
}
