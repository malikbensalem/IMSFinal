package com.qa.databases.persistances;

import com.qa.databases.daos.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * this is the menu of the system
 */

public class Control {
    public static final Logger LOGGER = Logger.getLogger(Control.class);
    @SuppressWarnings("CanBeFinal")
    private String name;
    private String pWord;

    /**
     * use this constructor if this is your first connection to the database during the run
     */
    public Control() {
        LOGGER.info("User:");
        this.name = Utils.INPUT.nextLine();
        LOGGER.info("password:");
        this.pWord = Utils.INPUT.nextLine();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://35.242.130.225/IMS", name, pWord);
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
        }
    }

    /**
     * displays the menu
     */
    public void menu() {
        choose(chooseCRUD(chooseTable()));
    }

    /**
     * which table do you want to use?
     *
     * @return - passes input onto chooseCRUD method
     */
    public String chooseTable() {

        LOGGER.info("What would you like to access?\n1.Customers\n2.Items\n3.Orders\n4.Users");
        return Utils.INPUT.nextLine();
    }

    /**
     * which CRUD do you want to use?
     *
     * @param f - chooseTable input
     * @return - completed user's input
     */
    public String chooseCRUD(String f) {

        LOGGER.info("What would you like to access?\n1.Create\n2.Read\n3.Update\n4.Delete");
        f += Utils.INPUT.nextLine();
        return f;
    }

    /**
     * picks the corresponding functionality depending on input
     *
     * @param f - determines which functionality will be used
     */
    public void choose(String f) {
        switch (f) {
            case "11": {
                MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
                custConnection.create();
                custConnection.closeConnection();
                break;
            }
            case "12": {
                MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
                custConnection.read();
                custConnection.closeConnection();
                break;
            }
            case "13": {
                MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
                custConnection.update();
                custConnection.closeConnection();
                break;
            }
            case "14": {
                MySQLCustomerDAO custConnection = new MySQLCustomerDAO(name, pWord);
                custConnection.delete();
                custConnection.closeConnection();
                break;
            }
            case "21": {
                MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
                itemConnection.create();
                itemConnection.closeConnection();
                break;
            }
            case "22": {
                MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
                itemConnection.read();
                itemConnection.closeConnection();
                break;
            }
            case "23": {
                MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
                itemConnection.update();
                itemConnection.closeConnection();
                break;
            }
            case "24": {
                MySQLItemsDAO itemConnection = new MySQLItemsDAO(name, pWord);
                itemConnection.delete();
                itemConnection.closeConnection();
                break;
            }
            case "31": {
                MySQLOrdersDAO orderConnection = new MySQLOrdersDAO(name, pWord);
                MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
                orderConnection.addTotal(oiConnection.create(orderConnection.create()));
                oiConnection.closeConnection();
                orderConnection.closeConnection();
                break;
            }
            case "32":
                LOGGER.info("1.All orders\n2.Details of specific order");
                String pick = Utils.INPUT.nextLine();
                if (pick.equals("1")) {
                    MySQLOrdersDAO orderConnection = new MySQLOrdersDAO(name, pWord);
                    orderConnection.read();
                    orderConnection.closeConnection();
                } else if (pick.equals("2")) {
                    MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
                    oiConnection.read();
                    oiConnection.closeConnection();
                }
                break;
            case "33": {
                MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
                MySQLOrdersDAO orderConnection = new MySQLOrdersDAO(name, pWord);
                orderConnection.addTotal(oiConnection.update());
                oiConnection.closeConnection();
                orderConnection.closeConnection();
                break;
            }
            case "34": {
                MySQLOrderedItemsDAO oiConnection = new MySQLOrderedItemsDAO(name, pWord);
                oiConnection.delete();
                oiConnection.closeConnection();
                break;
            }
            case "41": {
                MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
                userConnection.create();
                userConnection.closeConnection();
                break;
            }
            case "42": {
                MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
                userConnection.read();
                userConnection.closeConnection();
                break;
            }
            case "43": {
                MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
                userConnection.update();
                userConnection.closeConnection();
                break;
            }
            case "44": {
                MySQLUsersDAO userConnection = new MySQLUsersDAO(name, pWord);
                userConnection.delete();
                userConnection.closeConnection();
                break;
            }
        }
    }
}
