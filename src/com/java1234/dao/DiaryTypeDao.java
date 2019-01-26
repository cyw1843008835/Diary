package com.java1234.dao;
/*
* @author chenyanwei
* @date 30 Dec 2018 11:15:41
* @version 1.0
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.DiaryType;

public class DiaryTypeDao {

	public List<DiaryType> diaryTypeCountList(Connection con) throws Exception {
		List<DiaryType> diaryTypeCountList = new ArrayList<DiaryType>();
		String sql = "SELECT diaryTypeId,typeName,count(diaryId) as diaryCount from t_diarytype \r\n"
				+ "right join t_diary\r\n" + "on t_diarytype.diaryTypeId=t_diary.typeId\r\n" + "group by diaryTypeId";
		PreparedStatement pStatement = con.prepareStatement(sql);
		ResultSet rSet = pStatement.executeQuery();
		while (rSet.next()) {
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rSet.getInt("diaryTypeId"));
			diaryType.setTypeName(rSet.getString("typeName"));
			diaryType.setDiaryCount(rSet.getInt("diaryCount"));
			diaryTypeCountList.add(diaryType);
		}
		return diaryTypeCountList;

	}

	public List<DiaryType> diaryTypeList(Connection con) throws Exception {
		String sql = "select * from t_diaryType";
		List<DiaryType> diaryTypeList = new ArrayList<DiaryType>();
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet rSet = preparedStatement.executeQuery();
		while (rSet.next()) {
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rSet.getInt("diaryTypeId"));
			diaryType.setTypeName(rSet.getString("typeName"));
			diaryTypeList.add(diaryType);
		}
		return diaryTypeList;

	}

	public int diaryTypeAdd(Connection con, String typeName) throws Exception {
		String sql = "insert into t_diarytype values(null,?)";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, typeName);
		return preparedStatement.executeUpdate();

	}

	public int diaryTypeUpdate(Connection con, DiaryType diaryType) throws Exception {
		String sql = "update t_diarytype set typeName=? where diaryTypeId=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, diaryType.getTypeName());
		preparedStatement.setInt(2, diaryType.getDiaryTypeId());
		return preparedStatement.executeUpdate();

	}

	public DiaryType diaryTyeShow(Connection con, String diaryTypeId) throws Exception {
		String sql = "select * from t_diarytype where diaryTypeId=" + diaryTypeId;
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		DiaryType diaryType = new DiaryType();
		if (resultSet.next()) {
			diaryType.setTypeName(resultSet.getString("typeName"));
			diaryType.setDiaryTypeId(resultSet.getInt("diaryTypeId"));
		}
		return diaryType;

	}

	public void diaryTypeDelete(Connection con, String diaryTypeId) throws Exception {
		String sql = "delete from t_diaryType where diaryTypeId=" + diaryTypeId;
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.executeUpdate();

	}

}
