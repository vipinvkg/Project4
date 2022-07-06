package com.raystec.Bean;

public class SubjectBean extends BaseBean{

	private String Subject_Name;
	private String Course_Name;
	private int Course_id;
	private String Discription;
	
	public SubjectBean() {
		// TODO Auto-generated constructor stub

	}

	public String getSubject_Name() {
		return Subject_Name;
	}

	public void setSubject_Name(String subject_Name) {
		this.Subject_Name = subject_Name;
	}

	public String getCourse_Name() {
		return Course_Name;
	}

	public void setCourse_Name(String course_Name) {
		this.Course_Name = course_Name;
	}

	public int getCourse_id() {
		return Course_id;
	}

	public void setCourse_id(int course_id) {
		this.Course_id = course_id;
	}

	public String getDiscription() {
		return Discription;
	}

	public void setDiscription(String discription) {
		this.Discription = discription;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Subject_Name;
	}

}