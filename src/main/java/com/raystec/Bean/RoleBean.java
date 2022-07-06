package com.raystec.Bean;

public class RoleBean extends BaseBean {

	public static final int ADMIN =1;
	public static final int STUDENT =2;
	public static final int COLLEGE =4;
	public static final int KIOSK =3;
	
	private String name;
	private String description;
	
	public RoleBean() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}
	
}
