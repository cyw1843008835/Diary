package com.java1234.util;
/*
* @author chenyanwei
* @date 1 Dec 2018 13:28:28
* @version 1.0
*/

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	private static String dbUrl = PropertiesUtil.getValue("dbUrl");
	private static String dbUserName = PropertiesUtil.getValue("dbUserName");
	private static String dbPassword = PropertiesUtil.getValue("dbPassword");
	private static String jdbcName = PropertiesUtil.getValue("jdbcName");

	public static Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}

	public static void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	/*
	 * public void main(String[] args) {
	 * 
	 * try { DbUtil.getCon(); System.out.println("dbconnected"); } catch (Exception
	 * e) { System.out.println("dbconnection defeat!"); // TODO Auto-generated catch
	 * block e.printStackTrace(); } }
	 */
}
