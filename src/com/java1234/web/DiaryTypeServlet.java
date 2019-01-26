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
import com.java1234.util.StringUtil;

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
	Connection con = null;

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
		} else if ("preSave".equals(action)) {
			diaryTypeShow(request, response);
		} else if ("save".equals(action)) {
			diaryTypeSave(request, response);
		}
	}

	public void diaryTypeList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

	public void diaryTypeShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		if (StringUtil.isNotEmpty(diaryTypeId)) {
			try {
				con = JdbcUtil.getConnection();
				DiaryType diaryType = diaryTypeDao.diaryTyeShow(con, diaryTypeId);
				request.setAttribute("diaryType", diaryType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JdbcUtil.release(con, null, null);
			}
		}
		request.setAttribute("mainPage", "./diaryType/diaryTypeSave.jsp");
		request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);

	}

	public void diaryTypeSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int savedNum;
		String diaryTypeId = request.getParameter("diaryTypeId");
		String typeName = request.getParameter("typeName");
		DiaryType diaryType = new DiaryType();

		try {
			con = JdbcUtil.getConnection();
			if (StringUtil.isNotEmpty(diaryTypeId)) {
				diaryType.setTypeName(typeName);
				diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
				savedNum = diaryTypeDao.diaryTypeUpdate(con, diaryType);
			} else {
				savedNum = diaryTypeDao.diaryTypeAdd(con, typeName);
			}
			if (savedNum < 0 || savedNum == 0) {
				request.setAttribute("diaryType", diaryType);
				request.setAttribute("error", "±£´æÊ§°Ü");
				request.setAttribute("mainPage", "./diaryType/diaryTypeSave.jsp");
				request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("diaryType?action=diaryTypeList").forward(request, response);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(con, null, null);
		}
	}

}
