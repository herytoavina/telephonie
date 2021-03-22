package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	public Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://ec2-52-6-178-202.compute-1.amazonaws.com:5432/db3hkp62m2vqov","gzzqisvzeopcbx","80e5705ec16fc2e6c38e367f31fffb1c80b3b4eb9c22878cbbe59b6f13f8db71");
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return c;
	}
}
