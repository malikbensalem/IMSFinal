package com.qa.databases.interfaces;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface DAO {
	public void create();

	public ResultSet read();

	public void update();

	public void delete();
}
