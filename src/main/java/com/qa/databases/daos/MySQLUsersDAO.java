package com.qa.databases.daos;

import com.qa.databases.Utils;
import com.qa.databases.interfaces.Create;
import com.qa.databases.interfaces.Delete;
import com.qa.databases.interfaces.Read;
import com.qa.databases.interfaces.Update;

import java.sql.*;

public class MySQLUsersDAO implements Create, Read, Update, Delete {
    private Connection connection;

    public MySQLUsersDAO() {

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

    public MySQLUsersDAO(String name, String pWord) {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            e.printStackTrace();
        }
        return false;
    }

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
                e.printStackTrace();
            }
        }
    }

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
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        if (authenticate()) {
            try (Statement statement = connection.createStatement()) {
                System.out.println("Item ID:");
                String name = Utils.INPUT.nextLine();
                statement.executeUpdate("DELETE FROM Users WHERE username = \"" + name + "\";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
                e.printStackTrace();
            }
        }
        return resultSet;
    }
}
