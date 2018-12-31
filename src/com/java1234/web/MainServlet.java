package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.DiaryDao;
import com.java1234.dao.DiaryTypeDao;
import com.java1234.model.Diary;
import com.java1234.model.PageBean;
import com.java1234.util.DbUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.StringUtil;

/*
* @author chenyanwei
* @date 9 Dec 2018 10:33:32
* @version 1.0
*/
public class MainServlet extends HttpServlet {

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
		// TODO Auto-generated method stub
		DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
		HttpSession session = request.getSession();
		List<Diary> diaryList = new ArrayList<Diary>();
		Connection con = null;
		request.setCharacterEncoding("UTF-8");
		String page = request.getParameter("page");
		String s_typeId = request.getParameter("s_typeId");
		String s_releaseDateStr = request.getParameter("s_releaseDateStr");
		Diary diary = new Diary();
		if (StringUtil.isNotEmpty(s_typeId)) {
			diary.setTypeId(Integer.parseInt(s_typeId));
			session.setAttribute("s_typeId", s_typeId);
		}
		if (StringUtil.isNotEmpty(s_releaseDateStr)) {
			diary.setReleaseDateStr(s_releaseDateStr);
			session.setAttribute("s_releaseDateStr", s_releaseDateStr);
		}
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		try {
			con = DbUtil.getCon();
			diaryList = DiaryDao.diaryList(con, pageBean, diary);
			int total = DiaryDao.diaryCount(con, diary);
			String pageCode = this.gePagination(total, Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(con));
			session.setAttribute("releaseDateList", new DiaryDao().releaseDateStrList(con));
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("diaryList", diaryList);
			request.setAttribute("mainPage", "diary/diaryList.jsp");
			request.getRequestDispatcher("mainTemplate.jsp").forward(request, response);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				DbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private String gePagination(int totalNum, int currentPage, int pageSize) {
		int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>��ҳ</a></li>");
		if (currentPage == 1) {
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (currentPage - 1) + " '>��һҳ</a></li>");
		}
		for (int i = currentPage - 2; i <= currentPage + 2; i++) {
			if (i < 1 || i > totalPage) {
				continue;
			}
			if (i == currentPage) {
				pageCode.append("<li class='active'><a href='#'>" + i + "</a></li>");
			} else {
				pageCode.append("<li ><a href='main?page=" + i + " '>" + i + "</a></li>");
			}
		}
		if (currentPage == totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (currentPage + 1) + " '>��һҳ</a></li>");
		}
		pageCode.append("<li><a href='main?page=" + totalPage + " '>βҳ</a></li>");
		return pageCode.toString();
	}

}
