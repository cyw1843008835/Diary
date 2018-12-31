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
	 * @Description: �����ݿ����ӳ��л�ȡ���ݿ����Ӷ���
	 * 
	 * @return Connection���ݿ����Ӷ���
	 * @throws SQLException
	 */
	public static Connection getConnection() throws Exception {
		return jdbcPool.getConnection();
	}

	/**
	 * @Method: release
	 * @Description: �ͷ���Դ��
	 *               �ͷŵ���Դ����Connection���ݿ����Ӷ��󣬸���ִ��SQL�����Statement���󣬴洢��ѯ�����ResultSet����
	 * 
	 *
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn, Statement st, ResultSet rs) {

		if (rs != null) {
			try {
				// �رմ洢��ѯ�����ResultSet����
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				// �رո���ִ��SQL�����Statement����
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (conn != null) {
			try {
				// �ر�Connection���ݿ����Ӷ���
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
