package com.cullumg.carpark.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class ConnectionManager {
	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			init();
		}

		try {
			if (!conn.isValid(0)) {
				try {
					conn.close();
				} catch (SQLException arg0) {
					;
				}

				init();
			}
		} catch (SQLException arg1) {
			;
		}

		return conn;
	}

	private static void init() {
		try {
			InitialContext arg3 = new InitialContext();
			Context envCtx = (Context) arg3.lookup("java:comp/env");
			BasicDataSource ds = (BasicDataSource) envCtx.lookup("jdbc/mPark");
			conn = ds.getConnection();
		} catch (NamingException arg2) {
			arg2.printStackTrace();
		} catch (SQLException arg31) {
			arg31.printStackTrace();
		}

	}
}