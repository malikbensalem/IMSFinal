package com.qa.databases.interfaces;

import java.sql.ResultSet;

public interface ReadParam {
	public ResultSet read(int orderID);
}
