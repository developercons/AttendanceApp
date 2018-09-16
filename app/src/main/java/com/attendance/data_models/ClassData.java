package com.attendance.data_models;

public class ClassData {
	private String teacherName;
	private String className;

	public ClassData(String teacherName, String className) {
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
