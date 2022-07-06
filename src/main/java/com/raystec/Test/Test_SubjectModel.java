package com.raystec.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.SubjectBean;
import com.raystec.Model.SubjectModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

public class Test_SubjectModel {

	static SubjectBean bean = new SubjectBean();
	static SubjectModel model = new SubjectModel();
	
	public static void main(String[] args) throws ApplicationException {
		
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPk();
		//testFindByName();
		//testSearch();
		testList();
	}

	private static void testList() throws ApplicationException {
		List list = new ArrayList();
		list = model.list(10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (SubjectBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getSubject_Name());
			System.out.println(bean.getCourse_Name());
			System.out.println(bean.getCourse_id());
			System.out.println(bean.getDiscription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());	
		}
		
	}

	private static void testSearch() throws ApplicationException {
		//bean.setSubject_Name("sale");
		//bean.setDiscription("mana");
		//bean.setCourse_id(2);
		bean.setId(2);
		List list = new ArrayList();
		list = model.search(bean, 10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (SubjectBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getSubject_Name());
			System.out.println(bean.getCourse_Name());
			System.out.println(bean.getCourse_id());
			System.out.println(bean.getDiscription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());	
			
		}
		
	}

	private static void testFindByName() throws ApplicationException {

		bean = model.findByName("C++");
		System.out.println(bean.getId());
		System.out.println(bean.getSubject_Name());
		System.out.println(bean.getCourse_Name());
		System.out.println(bean.getCourse_id());
		System.out.println(bean.getDiscription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
	}

	private static void testFindByPk() throws ApplicationException {
		bean = model.findByPk(1);
		System.out.println(bean.getId());
		System.out.println(bean.getSubject_Name());
		System.out.println(bean.getCourse_Name());
		System.out.println(bean.getCourse_id());
		System.out.println(bean.getDiscription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
		
	}

	private static void testUpdate() throws ApplicationException {
		bean = model.findByPk(4);
		bean.setSubject_Name("Sales");
		bean.setCourse_Name("BBA");
		bean.setCourse_id(5);
		
		model.update(bean);
		System.out.println("Updated");
	}

	private static void testDelete() throws ApplicationException {
			bean.setId(5);
		model.delete(bean);
		
		System.out.println("Deleted");
	}

	private static void testAdd() throws ApplicationException, DuplicateRecordException {
		bean.setSubject_Name("Marketing");
		bean.setCourse_Name("BBA");
		bean.setCourse_id(4);
		bean.setDiscription("Management");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Added");
		
	}
}
