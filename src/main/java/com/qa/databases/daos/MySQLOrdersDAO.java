//worng i need help with this and orderitems table

package com.qa.databases.daos;

import com.qa.databases.persistances.Utils;
import com.qa.databases.interfaces.CreateReturn;

import java.sql.*;

public class MySQLOrdersDAO implements CreateReturn {

    private Connection connection;

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
    public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public MySQLOrdersDAO(String name, String pWord) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTotal(int ordersID) {//ordersid be where?
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate(
                    "UPDATE Orders SET total= (SELECT sum(price) FROM OrderedItems, Items WHERE OrderedItems.ordersID = "+ordersID+") WHERE ID = "+ ordersID);
            statement.executeUpdate("UPDATE Orders SET total = IF ( total>10000, total*9/10, total) WHERE ID = "+ordersID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int create() {
        int ID = 0;
        try (Statement statement=connection.createStatement()){
            System.out.println("customerID:");
            int customersID = Utils.INPUT2.nextInt();
            statement.executeUpdate("INSERT INTO Orders(customersID) VALUES(" + customersID + ");");
            ResultSet resultSet= statement.executeQuery("SELECT * FROM Orders");
            //int ID = 0;//resultSet.getInt("ID");
            while (resultSet.next()) {
            	ID = resultSet.getInt("ID");
            }
            //here, add a ResultSet that will read in the last order you created, and take its orderID
            //int orderID = readLast();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
        //instead of returning customerID, return new orderID
    }
}
