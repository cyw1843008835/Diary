package com.java1234.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.UserDao;
import com.java1234.model.User;
import com.java1234.util.JdbcUtil;
import com.java1234.util.MD5Util;

/*
* @author chenyanwei
* @date 1 Dec 2018 15:18:57
* @version 1.0
*/
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection con = null;
		User currentUser = null;
		User user = null;
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		try {
			String MD5Password = MD5Util.EncoderPwdByMd5("password");
			user = new User(userName, MD5Password);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			con = JdbcUtil.getConnection();
			currentUser = UserDao.login(con, user);
			if (currentUser == null) {
				session.setAttribute("error", "用户名或密码错误");
				session.setAttribute("user", user);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				if ("remember-me".equals(remember)) {
					rememberMe(userName, password, response);
				}
				session.setAttribute("currentUser", currentUser);
				request.getRequestDispatcher("main").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.release(con, null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void rememberMe(String userName, String password, HttpServletResponse response) {
		Cookie user = new Cookie("user", userName + "-" + password);
		user.setMaxAge(1 * 60 * 60 * 7);
		response.addCookie(user);
	}
}
