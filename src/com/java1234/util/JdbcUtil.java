package com.java1234.util;
/*
* @author chenyanwei
* @date 31 Dec 2018 20:19:41
* @version 1.0
*/

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class JdbcUtil {
	/**
	 * Field:pool
	 */
	private static JdbcPool jdbcPool = new JdbcPool();

	/**
	 * @Method: getConnection
	 * @Description: 从数据库连接池中获取数据库连接对象
	 * 
	 * @return Connection数据库连接对象
	 * @throws SQLException
	 */
	public static Connection getConnection() throws Exception {
		return jdbcPool.getConnection();
	}

	/**
	 * @Method: release
	 * @Description: 释放资源，
	 *               释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
	 * 
	 *
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn, Statement st, ResultSet rs) {

		if (rs != null) {
			try {
				// 关闭存储查询结果的ResultSet对象
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (conn != null) {
			try {
				// 关闭Connection数据库连接对象
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
