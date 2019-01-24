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
import com.java1234.util.StringUtil;

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
		int diaryidd = Integer.parseInt(diaryId);
		if ("show".equals(action)) {
			diaryShow(request, response, diaryId);
		} else if ("preSave".equals(action)) {
			preSave(request, response, diaryId);
		} else if ("save".equals(action)) {
			diarySave(request, response, diaryId);
		} else if ("delete".equals(action)) {
			diaryDelete(request, response, diaryidd);
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

	private void preSave(HttpServletRequest request, HttpServletResponse response, String diaryId)
			throws ServletException, IOException {

		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
			if (StringUtil.isNotEmpty(diaryId)) {
				Diary diary = diaryDao.diaryShow(con, diaryId);
				request.setAttribute("diary", diary);
			}
			request.setAttribute("mainPage", "./diary/diarySave.jsp");
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

	private void diarySave(HttpServletRequest request, HttpServletResponse response, String diaryId)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String typeId = request.getParameter("typeId");
		Diary diary = null;
		Connection con = null;
		int updateNum = 0;
		try {
			con = JdbcUtil.getConnection();
			if (StringUtil.isNotEmpty(diaryId)) {
				diary = new Diary(Integer.parseInt(diaryId), title, content, Integer.parseInt(typeId));
				updateNum = diaryDao.diaryUpdate(con, diary);
			} else {
				diary = new Diary(title, content, Integer.parseInt(typeId));
				updateNum = diaryDao.diaryAdd(con, diary);
			}

			if (updateNum < 0 || updateNum == 0) {
				request.setAttribute("ERROR", "±£´æÊ§°Ü");
				request.setAttribute("diary", diary);
				request.setAttribute("mainPage", "./diary/diarySave.jsp");
				request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
			} else if (updateNum > 0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}
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

	public void diaryDelete(HttpServletRequest request, HttpServletResponse response, int diaryId)
			throws ServletException, IOException {
		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
			int deleteNum = diaryDao.diaryDelete(con, diaryId);
			if (deleteNum < 0 || deleteNum == 0) {
				request.setAttribute("ERROR", "É¾³ýÊ§°Ü");
				request.setAttribute("mainPage", "./diary/diarySave.jsp");
				request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
			} else if (deleteNum > 0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}
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
