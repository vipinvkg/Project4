package com.raystec.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.RoleBean;
import com.raystec.Model.RoleModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;
import com.raystec.exception.RecordNotFoundException;

public class Test_RoleModel {

	static RoleBean bean = new RoleBean();
	static RoleModel model = new RoleModel();
	
	public static void main(String[] args) throws ApplicationException {
		
		//testAdd();
		//testUpdate();
		//testfindByPk();
		//testfindByName();
		//testDelete();
		testSearch();
		//testList();
	}

	private static void testList() throws ApplicationException {
		List list = new ArrayList();
		list = model.list(0, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
		 bean = (RoleBean) it.next();
		 System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}
		
	}

	private static void testSearch() throws ApplicationException {
		List list = new ArrayList();
		//bean.setId(1);
		//bean.setName("vipi");
		bean.setDescription("char");
		list = model.Search(bean, 0, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (RoleBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}
		
	}

	private static void testDelete() throws ApplicationException {
		bean.setId(4);
		model.delete(bean);
		System.out.println("Successfully Deleted");
		
	}

	private static void testfindByName() throws ApplicationException {
		bean = model.findByName("vineet");
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	private static void testfindByPk() throws ApplicationException {
		bean = model.findByPK(2);
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}

	private static void testUpdate() throws ApplicationException, RecordNotFoundException, DuplicateRecordException {
		bean.setId(1);
		bean.setName("Kanish");
		bean.setDescription("Chartered Acoountant");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		
		System.out.println("Updated");
	}

	private static void testAdd() throws ApplicationException {
		bean.setName("Aman");
		bean.setDescription("Meetha");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		
		System.out.println("Successfully added");
		
	}
}
