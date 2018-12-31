package com.java1234.dao;
/*
* @author chenyanwei
* @date 1 Dec 2018 13:57:27
* @version 1.0
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java1234.model.User;
import com.java1234.util.PropertiesUtil;

public class UserDao {

	public static User login(Connection con, User user) throws SQLException {
		User userResult = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPassword());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			userResult = new User();
			userResult.setUserId(rs.getInt("userId"));
			userResult.setUserName(rs.getString("userName"));
			userResult.setPassword(rs.getString("password"));
			userResult.setNickName(rs.getString("nickName"));
			userResult.setImageName(PropertiesUtil.getValue("userImage") + rs.getString("imageName"));
			userResult.setMood(rs.getString("mood"));
		}
		return userResult;
	}
}
