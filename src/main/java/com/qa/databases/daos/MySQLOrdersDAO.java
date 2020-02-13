//worng i need help with this and orderitems table

package com.qa.databases.daos;

import com.qa.databases.interfaces.CreateReturn;
import com.qa.databases.interfaces.Read;
import com.qa.databases.persistances.Utils;

import java.sql.*;

public class MySQLOrdersDAO implements CreateReturn, Read {

    private Connection connection;
    /**
     * this class allows a connection between java and the Orders' table
     */

    /**
     * use this constructor if this is your first connection to the database during the run
     */

    public MySQLOrdersDAO() {
        System.out.println("User:");
        String name = Utils.INPUT.nextLine();
        System.out.println("password:");
        String pWord = Utils.INPUT.nextLine();
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * use this constructor if you have already got access to the database
     *
     * @param name  - database name
     * @param pWord - database password
     */

    public MySQLOrdersDAO(String name, String pWord) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * closes this DAO down when called
     */

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * updates the total price the order
     *
     * @param ordersID - this will be the ID you want to set the totol for
     */
    public void addTotal(int ordersID) {//ordersid be where?
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "UPDATE Orders SET total= (SELECT sum(price) FROM OrderedItems, Items WHERE OrderedItems.ordersID = " + ordersID + ") WHERE ID = " + ordersID);
            statement.executeUpdate("UPDATE Orders SET total = IF ( total>10000, total*9/10, total) WHERE ID = " + ordersID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds a new order into the database
     *
     * @return - orderID so the OrderItems' table can take multiple items with the same Orders' ID
     */
    public int create() {
        int ID = 0;
        try (Statement statement = connection.createStatement()) {
            System.out.println("customerID:");
            int customersID = Utils.INPUT2.nextInt();
            statement.executeUpdate("INSERT INTO Orders(customersID) VALUES(" + customersID + ");");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders");
            while (resultSet.next()) {
                ID = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }

    /**
     * allows people to see all the orders that have been made
     * @return - Orders' table
     */
    public ResultSet read() {
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement
                    .executeQuery("SELECT * FROM Orders;");
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String custID = resultSet.getString("customersID");
                String total = resultSet.getString("total");
                System.out.println(ID + " | " + custID + " | " + total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
