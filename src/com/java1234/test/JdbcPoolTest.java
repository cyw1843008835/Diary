package com.java1234.test;

import com.java1234.util.JdbcUtil;

/*
* @author chenyanwei
* @date 31 Dec 2018 20:16:06
* @version 1.0
*/
public class JdbcPoolTest {
	public static void main(String[] args) {
		try {
			JdbcUtil.getConnection();
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			System.out.println("���ݿ�����ʧ��");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
