package com.attendance.data_models;

public class StudentData {
	private String studentName;
	private String className;
	private String studentEmailId;

	public String getStudentName() {
		return studentName;
	}

	public String getClassName() {
		return className;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStudentEmailId() {
		return studentEmailId;
	}

	public void setStudentEmailId(String studentEmailId) {
		this.studentEmailId = studentEmailId;
	}
}
