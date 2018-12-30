package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Diary;
import com.java1234.model.PageBean;
import com.java1234.util.DateUtil;

/*
* @author chenyanwei
* @date 10 Dec 2018 22:00:07
* @version 1.0
*/
public class DiaryDao {

	public static List<Diary> diaryList(Connection con, PageBean pageBean) throws Exception {

		List<Diary> diaryList = new ArrayList<Diary>();
		StringBuffer sb = new StringBuffer(
				"select * from t_diary as t1,t_diaryType as t2 where t1.typeId=t2.diaryTypeId");
		sb.append(" order by t1.releaseDate desc");
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}

		PreparedStatement ps = con.prepareStatement(sb.toString());
		ResultSet rSet = ps.executeQuery();
		while (rSet.next()) {
			Diary diary = new Diary();
			diary.setDiaryId(rSet.getInt("diaryId"));
			diary.setTitle(rSet.getString("title"));
			diary.setContent(rSet.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rSet.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			diaryList.add(diary);
		}
		return diaryList;
	}

	public static int diaryCount(Connection con) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from t_diary as t1,t_diaryType as t2 where t1.typeId=t2.diaryTypeId");
		PreparedStatement ps = con.prepareStatement(sb.toString());
		ResultSet rSet = ps.executeQuery();
		if (rSet.next()) {
			return rSet.getInt("total");
		} else {
			return 0;
		}

	}

	public List<Diary> releaseDateStrList(Connection con) throws Exception {
		String sql = "select date_format(releaseDate,'%YÄê%mÔÂ') as releaseDateStr, count(*) as diaryCount "
				+ "from t_diary " + "group by releaseDateStr " + "order by releaseDateStr desc";
		PreparedStatement pStatement = con.prepareStatement(sql);
		ResultSet rSet = pStatement.executeQuery();
		List<Diary> releaseDateList = new ArrayList<Diary>();
		while (rSet.next()) {
			Diary diary = new Diary();
			diary.setReleaseDateStr(rSet.getString("releaseDateStr"));
			diary.setDiaryCount(rSet.getInt("diaryCount"));
			releaseDateList.add(diary);
		}
		return releaseDateList;

	}
}
