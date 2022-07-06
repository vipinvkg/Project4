package com.raystec.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.CollegeBean;
import com.raystec.Model.CollegeModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

public class Test_CollegeModel {
	
	public static CollegeModel model = new CollegeModel();

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindbyName();
		//testFindbyCity();
		//testFindbyPK();
		//testSearch();
	testList();
	}

	public static void testList() throws ApplicationException {
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
		list=model.list(0, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CollegeBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());	
		}
		
	}

	public static void testSearch() throws ApplicationException {
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
		//bean.setName("Gujrati");
		bean.setAddress("new");
		//bean.setCity("Indore");
		list = model.search(bean, 0, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CollegeBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());			
		}
	}

	public static void testFindbyPK() throws ApplicationException {
		CollegeBean bean =model.findbypk(3);
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	public static void testFindbyCity() throws ApplicationException {
		CollegeBean bean = model.findbycity("Bhopal");
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	public static void testFindbyName() throws ApplicationException {
		CollegeBean bean = model.findbyname("Devi Ahilyabai college of management");
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	public static void testUpdate() throws ApplicationException, DuplicateRecordException {
		CollegeBean bean = model.findbypk(3);
		bean.setName("Chitransh College of Management");
		bean.setAddress("Sai Board");
		bean.setState("M.P.");
		bean.setCity("Bhopal");
		bean.setPhoneNo("8882564453");
		bean.setCreatedBy("Amit");
		bean.setModifiedBy("Amit");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Successfully updated");
	}

	public static void testDelete() throws ApplicationException {
		CollegeBean bean = new CollegeBean();
		bean.setId(11);
		model.delete(bean);
		System.out.println("Successfully deleted");
	}

	public static void testAdd() throws ApplicationException, DuplicateRecordException {
		CollegeBean bean = new CollegeBean();
		bean.setName("Gujrati College of management");
		bean.setAddress("Satya sai square");
		bean.setState("M.P.");
		bean.setCity("Indore");
		bean.setPhoneNo("8839675438");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		System.out.println("added successfully");
	}

}
