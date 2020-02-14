package com.qa.databases.persistances;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class Runner {
    public static void main(String[] args) throws SQLException {
    	final Logger LOGGER = Logger.getLogger(Runner.class);
    	   
    	Control control = new Control();
        String cont = "y";
        
        while (!cont.equals("n")) {
            control.menu();
            LOGGER.info("Do you want to continue(Y/N)?");
            cont = Utils.INPUT.nextLine().toLowerCase();
        }
    }
}