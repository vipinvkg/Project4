package com.raystec.Bean;

public class CourseBean extends BaseBean {
	
	private String Course_Name;
	private String Discription;
	private String Duration;
	
	public CourseBean() {
		// TODO Auto-generated constructor stub
	}

	public String getCourse_Name() {
		return Course_Name;
	}

	public void setCourse_Name(String course_Name) {
		this.Course_Name = course_Name;
	}

	public String getDiscription() {
		return Discription;
	}

	public void setDiscription(String discription) {
		this.Discription = discription;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		this.Duration = duration;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return Course_Name;
	}

}
