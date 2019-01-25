package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.DiaryTypeDao;
import com.java1234.model.DiaryType;
import com.java1234.util.JdbcUtil;

/*
* @author chenyanwei
* @date 25 Jan 2019 20:03:36
* @version 1.0
*/
public class DiaryTypeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

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
		if ("diaryTypeList".equals(action)) {
			diaryTypeList(request, response);
		}
	}

	public void diaryTypeList(HttpServletRequest request, HttpServletResponse response) {
		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
			List<DiaryType> diaryTypeList = diaryTypeDao.diaryTypeList(con);
			request.setAttribute("diaryTypeList", diaryTypeList);
			request.setAttribute("mainPage", "./diaryType/diaryType.jsp");
			request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(con, null, null);
		}
	}

}
