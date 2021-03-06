package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Diary;
import com.java1234.model.PageBean;
import com.java1234.util.DateUtil;
import com.java1234.util.StringUtil;

/*
* @author chenyanwei
* @date 10 Dec 2018 22:00:07
* @version 1.0
*/
public class DiaryDao {

	public static List<Diary> diaryList(Connection con, PageBean pageBean, Diary s_diary) throws Exception {

		List<Diary> diaryList = new ArrayList<Diary>();
		StringBuffer sb = new StringBuffer(
				"select * from t_diary as t1,t_diaryType as t2 where t1.typeId=t2.diaryTypeId");
		if (StringUtil.isNotEmpty(s_diary.getTitle())) {
			sb.append(" and t1.title like '%" + s_diary.getTitle() + "%'");
		}
		if (s_diary.getTypeId() != -1) {
			sb.append(" and t1.typeId=" + s_diary.getTypeId());
		}
		if (StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
			sb.append(" and date_format(t1.releaseDate,'%Y年%m月')='" + s_diary.getReleaseDateStr() + "'");
		}
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

	public static int diaryCount(Connection con, Diary s_diary) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from t_diary as t1,t_diaryType as t2 where t1.typeId=t2.diaryTypeId");
		if (StringUtil.isNotEmpty(s_diary.getTitle())) {
			sb.append(" and t1.title like '%" + s_diary.getTitle() + "%'");
		}
		if (s_diary.getTypeId() != -1) {
			sb.append(" and t1.typeId=" + s_diary.getTypeId());
		}
		if (StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
			sb.append(" and date_format(t1.releaseDate,'%Y年%m月')='" + s_diary.getReleaseDateStr() + "'");
		}
		PreparedStatement ps = con.prepareStatement(sb.toString());
		ResultSet rSet = ps.executeQuery();
		if (rSet.next()) {
			return rSet.getInt("total");
		} else {
			return 0;
		}

	}

	public List<Diary> releaseDateStrList(Connection con) throws Exception {
		String sql = "select date_format(releaseDate,'%Y年%m月') as releaseDateStr, count(*) as diaryCount "
				+ "from t_diary " + "group by date_format(releaseDate,'%Y年%m月') "
				+ "order by date_format(releaseDate,'%Y年%m月') desc;";
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

	public Diary diaryShow(Connection con, String diaryId) throws Exception {
		String sql = "select * from t_diary t1,t_diarytype t2  where t1.typeId=t2.diaryTypeId and t1.diaryId=?";
		PreparedStatement pStatement = con.prepareStatement(sql);
		pStatement.setString(1, diaryId);
		ResultSet rSet = pStatement.executeQuery();
		Diary diary = new Diary();
		while (rSet.next()) {
			diary.setDiaryId(rSet.getInt("diaryId"));
			diary.setTitle(rSet.getString("title"));
			diary.setContent(rSet.getString("content"));
			diary.setTypeId(rSet.getInt("typeId"));
			diary.setTypeName(rSet.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(rSet.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
		}
		return diary;

	}

	public int diaryAdd(Connection con, Diary diary) throws Exception {
		String sql = "insert into t_diary values(null,?,?,?,now());";
		PreparedStatement pStatement = con.prepareStatement(sql);
		pStatement.setString(1, diary.getTitle());
		pStatement.setString(2, diary.getContent());
		pStatement.setInt(3, diary.getTypeId());
		return pStatement.executeUpdate();
	}

	public int diaryUpdate(Connection con, Diary diary) throws Exception {
		String sql = "update t_diary set title=?,content=?,TypeId=? where diaryid=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, diary.getTitle());
		preparedStatement.setString(2, diary.getContent());
		preparedStatement.setInt(3, diary.getTypeId());
		preparedStatement.setInt(4, diary.getDiaryId());
		return preparedStatement.executeUpdate();

	}

	public int diaryDelete(Connection con, int diaryId) throws Exception {
		String sql = "delete from t_diary where diaryId=" + diaryId;
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		return preparedStatement.executeUpdate();

	}

	public boolean hasDiaryOfThisType(Connection con, int typeId) throws Exception {
		String sql = "select * from t_diary where typeId=" + typeId;
		PreparedStatement preparedStatemsent = con.prepareStatement(sql);
		ResultSet resultSet = preparedStatemsent.executeQuery();
		if (resultSet.next()) {
			return true;
		}
		return false;

	}
}
