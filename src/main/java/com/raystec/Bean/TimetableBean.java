package com.raystec.Bean;

import java.util.Date;

public class TimetableBean extends BaseBean {

	private String Course_Name;
	private int Course_id;
	private String Subject_Name;
	private int Subject_Id;
	private Date Exam_Date;
	private String Exam_Time;
	private String Semester;
	
	public TimetableBean() {
		// TODO Auto-generated constructor stub
	}

	public String getCourse_Name() {
		return Course_Name;
	}

	public void setCourse_Name(String course_Name) {
		Course_Name = course_Name;
	}

	public int getCourse_id() {
		return Course_id;
	}

	public void setCourse_id(int course_id) {
		Course_id = course_id;
	}

	public String getSubject_Name() {
		return Subject_Name;
	}

	public void setSubject_Name(String subject_Name) {
		Subject_Name = subject_Name;
	}

	public int getSubject_Id() {
		return Subject_Id;
	}

	public void setSubject_Id(int subject_Id) {
		Subject_Id = subject_Id;
	}

	public Date getExam_Date() {
		return Exam_Date;
	}

	public void setExam_Date(Date exam_Date) {
		Exam_Date = exam_Date;
	}

	public String getExam_Time() {
		return Exam_Time;
	}

	public void setExam_Time(String exam_Time) {
		Exam_Time = exam_Time;
	}

	public String getSemester() {
		return Semester;
	}

	public void setSemester(String semester) {
		Semester = semester;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return Course_Name;
	}

}
