package com.java1234.util;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

/*
* @author chenyanwei
* @date 31 Dec 2018 19:42:06
* @version 1.0
*/
/**
 * @Field: listConnections ʹ��LinkedList������������ݿ����ӣ�
 *         ����ҪƵ����дList���ϣ���������ʹ��LinkedList�洢���ݿ����ӱȽϺ���
 */

public class JdbcPool implements DataSource {
	private static LinkedList<Connection> listConnections = new LinkedList<Connection>();
	static {
		String dbUrl = PropertiesUtil.getValue("dbUrl");
		String dbUserName = PropertiesUtil.getValue("dbUserName");
		String dbPassword = PropertiesUtil.getValue("dbPassword");
		String jdbcName = PropertiesUtil.getValue("jdbcName");
		int jdbcPoolInitSize = Integer.parseInt(PropertiesUtil.getValue("jdbcPoolInitSize"));
		try {
			Class.forName(jdbcName);
			for (int i = 0; i < jdbcPoolInitSize; i++) {
				Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
				System.out.println("��ȡ��������" + conn);
				// ����ȡ�������ݿ����Ӽ��뵽listConnections�����У�listConnections���ϴ�ʱ����һ����������ݿ����ӵ����ӳ�
				listConnections.add(conn);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ExceptionInInitializerError(e);
		}

	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// ������ݿ����ӳ��е����Ӷ���ĸ�������0
		if (listConnections.size() > 0) {
			// ��listConnections�����л�ȡһ�����ݿ�����
			final Connection conn = listConnections.removeFirst();
			System.out.println("listConnections���ݿ����ӳش�С��" + listConnections.size());
			// ����Connection����Ĵ������
			return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (!method.getName().equals("close")) {
								return method.invoke(conn, args);
							} else {
								// ������õ���Connection�����close�������Ͱ�conn�������ݿ����ӳ�
								listConnections.add(conn);
								System.out.println(conn + "������listConnections���ݿ����ӳ��ˣ���");
								System.out.println("listConnections���ݿ����ӳش�СΪ" + listConnections.size());
								return null;
							}

						}
					});
		} else {
			throw new RuntimeException("�Բ������ݿ�æ");
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;

	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
