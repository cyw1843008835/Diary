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
}
