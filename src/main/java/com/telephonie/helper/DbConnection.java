package com.telephonie.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	public Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/telephonie","telephonie_role","123456");
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return c;
	}
}
