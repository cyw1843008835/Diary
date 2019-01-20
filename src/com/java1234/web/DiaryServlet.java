package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.DiaryDao;
import com.java1234.model.Diary;
import com.java1234.util.JdbcUtil;

/*
* @author chenyanwei
* @date 19 Jan 2019 17:57:54
* @version 1.0
*/
public class DiaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DiaryDao diaryDao = new DiaryDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String diaryId = request.getParameter("diaryId");
		if ("show".equals(action)) {
			diaryShow(request, response, diaryId);
		}
	}

	private void diaryShow(HttpServletRequest request, HttpServletResponse response, String diaryId)
			throws ServletException, IOException {

		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
			Diary diary = diaryDao.diaryShow(con, diaryId);
			request.setAttribute("diary", diary);
			request.setAttribute("mainPage", "./diary/diaryShow.jsp");
			request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
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

}
