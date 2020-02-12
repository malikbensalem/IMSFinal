package com.qa.databases.daos;

import com.qa.databases.persistances.Utils;
import com.qa.databases.interfaces.Create;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.Update;

import java.sql.*;
import java.util.Scanner;

public class MySQLItemsDAO implements Create, Read, Update, Delete {
    private Connection connection;
    private String name;
    private String pWord;

    public MySQLItemsDAO() {
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

    public MySQLItemsDAO(String name, String pWord) {
        this.name = name;
        this.pWord = pWord;
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        MySQLUsersDAO UD = new MySQLUsersDAO(name, pWord);
        if (UD.authenticate()) {
            try (Statement statement = connection.createStatement()) {
                Scanner input = new Scanner(System.in);
                System.out.println("Item name:");
                String name = Utils.INPUT.nextLine();
                System.out.println("Price:");
                float price = Utils.INPUT3.nextFloat();
                statement.executeUpdate("INSERT INTO Items (iName,price) VALUES( \"" + name + "\", " + price + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        UD.closeConnection();
    }

    public void update() {
        MySQLUsersDAO UD = new MySQLUsersDAO(name, pWord);
        if (UD.authenticate()) {
            try (Statement statement = connection.createStatement()) {
                System.out.println("Item ID:");
                int ID = Utils.INPUT2.nextInt();
                System.out.println("New Item name:");
                String name = Utils.INPUT.nextLine();
                System.out.println("New Price:");
                float price = Utils.INPUT3.nextFloat();
                statement.executeUpdate("UPDATE Items(name,price) SET iName = \"" + name + "\", price = " + price
                        + " WHERE ID= " + ID + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        UD.closeConnection();
    }

    public void delete() {
        MySQLUsersDAO UD = new MySQLUsersDAO(name, pWord);
        if (UD.authenticate()) {
            try (Statement statement = connection.createStatement()) {
                System.out.println("Item ID:");
                int ID = Utils.INPUT2.nextInt();
                statement.executeUpdate("DELETE FROM Items WHERE ID = " + ID + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        UD.closeConnection();
    }

    public ResultSet read() {
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM Items;");
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String name = resultSet.getString("iName");
                String price = resultSet.getString("price");
                System.out.println(ID + " | " + name + " | " + price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}