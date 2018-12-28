package com.java1234.model;
/*
* @author chenyanwei
* @date 9 Dec 2018 20:16:43
* @version 1.0
*/

import java.util.Date;

import javafx.util.converter.DateStringConverter;

public class Diary {
	private int DiaryId;
	private String title;
	private String content;
	private int typeId=-1;
	private Date releaseDate;
	public int getDiaryId() {
		return DiaryId;
	}
	public void setDiaryId(int diaryId) {
		DiaryId = diaryId;
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
	

}
