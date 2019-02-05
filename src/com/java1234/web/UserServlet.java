package com.java1234.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.java1234.dao.UserDao;
import com.java1234.model.User;
import com.java1234.util.DateUtil;
import com.java1234.util.JdbcUtil;
import com.java1234.util.PropertiesUtil;

/*
* @author chenyanwei
* @date 26 Jan 2019 19:54:47
* @version 1.0
*/
public class UserServlet extends HttpServlet {

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
		String action = request.getParameter("action");
		if ("preSave".equals(action)) {
			userPreSave(request, response);
		} else if ("save".equals(action)) {
			userSave(request, response);
		}
	}

	public void userPreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("mainPage", "./user/userSave.jsp");
		request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
	}

	public void userSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		List<FileItem> tFileitems = null;
		try {
			tFileitems = servletFileUpload.parseRequest(new ServletRequestContext(request));
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<FileItem> iter = tFileitems.iterator();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		boolean ifImageChange = false;
		while (iter.hasNext()) {
			FileItem item = iter.next();
			if (item.isFormField()) {
				String fieldName = item.getFieldName();
				if ("nickName".equals(fieldName)) {
					user.setNickName(item.getString("utf-8"));
				}
				if ("mood".equals(fieldName)) {
					user.setMood(item.getString("utf-8"));
				}
			} else if (!"".equals(item.getFieldName())) {
				ifImageChange = true;
				DateUtil dateUtil = new DateUtil();
				try {
					String imageName = dateUtil.getCurrentDateStr();
					user.setImageName(imageName + "." + item.getName().split("\\.")[1]);
					String filePath = PropertiesUtil.getValue("imagePath") + "\\" + imageName + "."
							+ item.getName().split("\\.")[1];
					item.write(new File(filePath));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		if (!ifImageChange) {
			user.setImageName(user.getImageName().replaceFirst(PropertiesUtil.getValue("imageFile"), ""));
		}
		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
			UserDao userDao = new UserDao();
			int saveNum = userDao.userUpdate(con, user);
			if (saveNum > 0) {
				user.setImageName(PropertiesUtil.getValue("imageFile") + user.getImageName());
				session.setAttribute("currentUser", user);
				request.getRequestDispatcher("main").forward(request, response);
				request.getRequestDispatcher("main?all=true").forward(request, response);
			} else {
				request.setAttribute("currentUser", user);
				request.setAttribute("error", "±£¥Ê ß∞‹");
				request.setAttribute("mainPage", "./user/userSave.jsp");
				request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			JdbcUtil.release(con, null, null);

		}
	}
}
