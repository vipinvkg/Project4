package com.raystec.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.FacultyBean;
import com.raystec.Model.FacultyModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

public class Test_FacultyModel {

	static FacultyBean bean = new FacultyBean();
	static FacultyModel model = new FacultyModel();
	
	public static void main(String[] args) throws ParseException, ApplicationException {
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPk();
		//testFindByEmail();
		//testFindByName();
		//testSearch();
		testList();
	}

	private static void testList() throws ApplicationException {
		List list = new ArrayList();
		list = model.list(10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (FacultyBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirst_Name());
			System.out.println(bean.getLast_Name());
			System.out.println(bean.getGender());
			System.out.println(bean.getDOJ());
			System.out.println(bean.getQualification());
			System.out.println(bean.getEmail_id());
			System.out.println(bean.getMobile_No());
			System.out.println(bean.getCollege_id());
			System.out.println(bean.getCollege_Name());
			System.out.println(bean.getCourse_id());
			System.out.println(bean.getCollege_Name());
			System.out.println(bean.getSubject_id());
			System.out.println(bean.getSubject_Name());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());	
		}
	}

	private static void testSearch() throws ApplicationException {
		//bean.setId(3);
		//bean.setFirst_Name("vip");
		//bean.setLast_Name("shar");
		
		List list = new ArrayList();
		list = model.search(bean, 10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (FacultyBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirst_Name());
			System.out.println(bean.getLast_Name());
			System.out.println(bean.getGender());
			System.out.println(bean.getDOJ());
			System.out.println(bean.getQualification());
			System.out.println(bean.getEmail_id());
			System.out.println(bean.getMobile_No());
			System.out.println(bean.getCollege_id());
			System.out.println(bean.getCollege_Name());
			System.out.println(bean.getCourse_id());
			System.out.println(bean.getCollege_Name());
			System.out.println(bean.getSubject_id());
			System.out.println(bean.getSubject_Name());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}
	}

	private static void testFindByName() throws ApplicationException {
		bean = model.findByName("Luckey");
		System.out.println(bean.getId());
		System.out.println(bean.getFirst_Name());
		System.out.println(bean.getLast_Name());
		System.out.println(bean.getGender());
		System.out.println(bean.getDOJ());
		System.out.println(bean.getQualification());
		System.out.println(bean.getEmail_id());
		System.out.println(bean.getMobile_No());
		System.out.println(bean.getCollege_id());
		System.out.println(bean.getCollege_Name());
		System.out.println(bean.getCourse_id());
		System.out.println(bean.getCollege_Name());
		System.out.println(bean.getSubject_id());
		System.out.println(bean.getSubject_Name());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
	}

	private static void testFindByEmail() throws ApplicationException {
		bean = model.findByEmail("vipin@gmail.com");
		System.out.println(bean.getId());
		System.out.println(bean.getFirst_Name());
		System.out.println(bean.getLast_Name());
		System.out.println(bean.getGender());
		System.out.println(bean.getDOJ());
		System.out.println(bean.getQualification());
		System.out.println(bean.getEmail_id());
		System.out.println(bean.getMobile_No());
		System.out.println(bean.getCollege_id());
		System.out.println(bean.getCollege_Name());
		System.out.println(bean.getCourse_id());
		System.out.println(bean.getCollege_Name());
		System.out.println(bean.getSubject_id());
		System.out.println(bean.getSubject_Name());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	private static void testFindByPk() throws ApplicationException {
		bean = model.findByPk(2);
		System.out.println(bean.getId());
		System.out.println(bean.getFirst_Name());
		System.out.println(bean.getLast_Name());
		System.out.println(bean.getGender());
		System.out.println(bean.getDOJ());
		System.out.println(bean.getQualification());
		System.out.println(bean.getEmail_id());
		System.out.println(bean.getMobile_No());
		System.out.println(bean.getCollege_id());
		System.out.println(bean.getCollege_Name());
		System.out.println(bean.getCourse_id());
		System.out.println(bean.getCollege_Name());
		System.out.println(bean.getSubject_id());
		System.out.println(bean.getSubject_Name());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	private static void testUpdate() throws ApplicationException {
		bean = model.findByPk(3);
		bean.setFirst_Name("Luckey");
		bean.setLast_Name("Sharma");
		bean.setCollege_Name("Oriental college");
		bean.setMobile_No("9893454334");
		model.update(bean);
		
		System.out.println("Updated");
		
	}

	private static void testDelete() throws ApplicationException {
		bean.setId(4);
		model.delete(bean);
		System.out.println("Deleted");
		
	}

	private static void testAdd() throws ParseException, ApplicationException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-yyy");
		bean.setFirst_Name("Ram");
		bean.setLast_Name("Kumar");
		bean.setGender("M");
		bean.setDOJ(sdf.parse("12-02-1992"));
		bean.setQualification("MBA");
		bean.setEmail_id("ram@gmail.com");
		bean.setMobile_No("7879322332");
		bean.setCollege_id(1);
		bean.setCollege_Name("Rajiv gandhi college");
		bean.setCourse_id(1);
		bean.setCourse_Name("Management");
		bean.setSubject_id(5);
		bean.setSubject_Name("Marketing");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		
		System.out.println("Added");
	}
}
