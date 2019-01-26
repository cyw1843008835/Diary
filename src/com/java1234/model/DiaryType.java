package com.java1234.model;

/*
* @author chenyanwei
* @date 30 Dec 2018 11:06:41
* @version 1.0
*/
public class DiaryType {
	private int diaryTypeId;
	private String typeName;
	private int diaryCount;

	public int getDiaryTypeId() {
		return diaryTypeId;
	}

	public void setDiaryTypeId(int diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getDiaryCount() {
		return diaryCount;
	}

	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}

	public DiaryType(int diaryTypeId, String typeName) {
		super();
		this.diaryTypeId = diaryTypeId;
		this.typeName = typeName;
	}

	public DiaryType() {
		super();
		// TODO Auto-generated constructor stub
	}

}
