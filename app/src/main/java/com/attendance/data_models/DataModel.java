package com.attendance.data_models;

public class DataModel {
	private String teacherName;
	private String className;

	public DataModel(String teacherName, String className) {
		this.teacherName = teacherName;
		this.className = className;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getClassName() {
		return className;
	}
}
