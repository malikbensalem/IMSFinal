package com.qa.databases.daos;

import com.qa.databases.Utils;
import com.qa.databases.interfaces.CreateParam;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.UpdateReturn;

import java.sql.*;

public class MySQLOrderedItemsDAO implements CreateParam, Read, UpdateReturn, Delete {

    String name;
    String pWord;
    private Connection connection;

    public MySQLOrderedItemsDAO() {

        System.out.println("User:");
        String name = Utils.INPUT.nextLine();
        System.out.println("password:");
        String pWord = Utils.INPUT.nextLine();
        this.name = name;
        this.pWord = pWord;
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MySQLOrderedItemsDAO(String name, String pWord) {
        this.name = name;
        this.pWord = pWord;
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

    public int create(int ordersID) {
        int itemsID = -1;
        try (Statement statement = connection.createStatement()) {
            System.out.println("Item's ID: \n(press 0 once you are done)");
            while (true) {
                itemsID = Utils.INPUT2.nextInt();
                if (itemsID <= 0) {
                    break;
                }
                statement.executeUpdate(
                        "INSERT INTO OrderedItems (ordersID,itemsID) VALUES(" + ordersID + ", " + itemsID + ");");
                System.out.println(">");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersID;
    }

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

    public int update() {
        int ordersID = -1;
        try (Statement statement = connection.createStatement()) {
            System.out.println("order's ID to be changed:");
            ordersID = Utils.INPUT2.nextInt();
            System.out.println("Item's ID to be changed:");
            int oldItem = Utils.INPUT2.nextInt();
            System.out.println("new Item's ID:");
            int newItem = Utils.INPUT2.nextInt();
            statement.executeUpdate("UPDATE OrderedItems SET ItemsID = " + newItem + " WHERE ItemsID = " + oldItem
                    + "AND ordersID = " + ordersID + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersID;
    }

    public void delete() {
        try (Statement statement = connection.createStatement()) {
            System.out.println("OrderID:");
            int orderID = Utils.INPUT2.nextInt();
            statement.executeUpdate("DELETE FROM OrderedItems WHERE OrdersID = " + orderID + ";");
            MySQLOrdersDAO o = new MySQLOrdersDAO(name, pWord);
            o.addTotal(orderID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
