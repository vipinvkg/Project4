package com.raystec.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.CourseBean;
import com.raystec.Model.CourseModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

public class Test_CourseModel {

	static CourseModel model = new CourseModel();
	static CourseBean bean = new CourseBean();

	public static void main(String[] args) throws ApplicationException {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByName();
		// testFindByPk();
		testSearch();
//		testList();
	}

		private static void testAdd() throws ApplicationException, DuplicateRecordException {
		bean.setCourse_Name("Bcom");
		bean.setDiscription("Bachelour of Commerce");
		bean.setDuration("3 years");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);

		System.out.println("added Successully");
	}

	private static void testDelete() throws ApplicationException {
		bean.setId(5);
		model.delete(bean);
		System.out.println("Deleted");

	}

	private static void testUpdate() throws ApplicationException, DuplicateRecordException {
		bean = model.findByPk(4);
		bean.setCourse_Name("BCOM");
		bean.setDiscription("Bachelour of commerce");
		bean.setDuration("3 years");
		model.update(bean);

		System.out.println("Updated");
	}

	private static void testFindByName() throws ApplicationException {
		bean = model.findByName("MBA");
		System.out.println(bean.getId());
		System.out.println(bean.getCourse_Name());
		System.out.println(bean.getDiscription());
		System.out.println(bean.getDuration());

	}

	private static void testFindByPk() throws ApplicationException {
		bean = model.findByPk(2);
		System.out.println(bean.getId());
		System.out.println(bean.getCourse_Name());
		System.out.println(bean.getDiscription());
		System.out.println(bean.getDuration());

	}

	private static void testSearch() throws ApplicationException {
		//bean.setCourse_Name("BBA");
		//bean.setDiscription("Bachelo");
		bean.setDuration("3");
		List list = new ArrayList();
		list = model.search(bean,1,10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean= (CourseBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCourse_Name());
			System.out.println(bean.getDiscription());
			System.out.println(bean.getDuration());
		}
	}
	private static void testList() throws ApplicationException {
		List list = new ArrayList();
		list = model.list(10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CourseBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCourse_Name());
			System.out.println(bean.getDiscription());
			System.out.println(bean.getDuration());
		}
		
	}


}
