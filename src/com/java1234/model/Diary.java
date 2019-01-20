package com.java1234.model;

import java.util.Date;
/*
* @author chenyanwei
* @date 30 Dec 2018 11:06:41
* @version 1.0
*/

public class Diary {

	private int diaryId;
	private String title;
	private String content;
	private int typeId = -1;
	private Date releaseDate;
	private String releaseDateStr;
	private int diaryCount;
	private String typeName;

	public int getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

	public int getDiaryCount() {
		return diaryCount;
	}

	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
