package com.raystec.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.StudentBean;
import com.raystec.Model.StudentModel;
import com.raystec.exception.ApplicationException;

public class Test_StudentModel {

	static StudentBean bean = new StudentBean();
	static StudentModel model = new StudentModel();
	
	public static void main(String[] args) throws ParseException, ApplicationException {
		
		//testAdd();
		//testDelete();
		//testFindByMailId();
		//testFindByPk();
		//testUpdate();
		//testSearch();
		//testList();
		
		
	}

	private static void testList() throws ApplicationException {
		List list = new ArrayList();
		list = model.list(0, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (StudentBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getDob());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}
		
	}

	private static void testSearch() throws ApplicationException {
		List list = new ArrayList();
		//bean.setLastName("dubey");
		//bean.setFirstName("mayank");
		bean.setId(2);
		list = model.search(bean, 10, 1);
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (StudentBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getDob());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
	
		}
		
			}

	private static void testUpdate() throws ParseException, ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setId(4);
		bean.setCollegeId(123);
		bean.setCollegeName("Devi Ahilyabai");
		bean.setFirstName("Mayank");
		bean.setLastName("Dubey");
		bean.setDob(sdf.parse("12/06/1990"));
		bean.setMobileNo("9893233445");
		bean.setEmail("Mayank@gmail.com");
		bean.setCreatedBy("Amit");
		bean.setModifiedBy("Amit");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.update(bean);
		System.out.println("Successfully updated");
		
	}

	private static void testFindByPk() throws ApplicationException {
		bean = model.findByPk(1);
		System.out.println(bean.getId());
		System.out.println(bean.getCollegeId());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getMobileNo());
		
		
	}

	private static void testFindByMailId() throws ApplicationException {
		bean = model.findByEmailId("gagan@gmail.com");
		System.out.println(bean.getId());
		System.out.println(bean.getCollegeId());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getMobileNo());
		
	}

	private static void testDelete() throws ApplicationException {
		bean.setId(5);
		model.delete(bean);
		System.out.println("Seuccessfully Deleted");
	}

	private static void testAdd() throws ParseException, ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setCollegeId(123);
		bean.setFirstName("Gagan");
		bean.setLastName("Sunhare");
		bean.setCollegeName("Gujrati College");
		bean.setDob(sdf.parse("15/02/1989"));
		bean.setMobileNo("8889348976");
		bean.setEmail("Gagan@gmail.com");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		System.out.println("Successfully Added");
	}
}
