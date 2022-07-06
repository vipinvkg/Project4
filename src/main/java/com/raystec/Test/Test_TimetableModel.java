package com.raystec.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.TimetableBean;
import com.raystec.Model.TimeTableModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

public class Test_TimetableModel {

	public static void main(String[] args) throws ParseException, ApplicationException {
		
		//testADD();
		//testDelete();
		//testUpdate();
		//testFindByName();
		//testCheckByCss();
		//testSearch();
		testList();
	}

	private static void testList() throws ApplicationException {
		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		List list = new ArrayList();
		list = model.list(10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
				bean = (TimetableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getSubject_Name());
				System.out.println(bean.getExam_Date());
				System.out.println(bean.getExam_Time());	
		}
		
	}

	private static void testSearch() throws ApplicationException {
		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		bean.setCourse_Name("B");
		//bean.setCourse_id(3);
		List list = new ArrayList();
		list = model.search(bean, 10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean= (TimetableBean) it.next();
			
			
			System.out.println(bean.getCourse_Name());
			System.out.println(bean.getSubject_Name());
			System.out.println(bean.getExam_Date());
			System.out.println(bean.getExam_Time());
		}
		
		
	}

	private static void testCheckByCss() throws ApplicationException, ParseException {
		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		bean = model.checkByCss(4, 1, 4);
		System.out.println(bean.getCourse_Name());
		System.out.println(bean.getExam_Time());
	}

	private static void testFindByName() throws ApplicationException {

		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		bean = model.findByName("science");
		System.out.println(bean.getId());
		System.out.println(bean.getCourse_id());
		System.out.println(bean.getCourse_Name());
		System.out.println(bean.getExam_Time());
		System.out.println(bean.getExam_Date());
		
	}

	private static void testUpdate() throws ApplicationException {

		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		bean = model.findByPk(1);
		bean.setCourse_Name("MCA");
		bean.setCourse_id(3);
		bean.setSubject_Name("java");
		model.update(bean);
		
		System.out.println("updated");
	}

	private static void testDelete() throws ApplicationException {

		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		bean.setId(3);
		model.delete(bean);
		System.out.println("deleted");
	}

	private static void testADD() throws ParseException, ApplicationException, DuplicateRecordException {
		TimetableBean bean = new TimetableBean();
		TimeTableModel model = new TimeTableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setCourse_Name("BCOM");
		bean.setCourse_id(4);
		bean.setSubject_Name("Commerce");
		bean.setSubject_Id(1);
		bean.setExam_Date(sdf.parse("10/03/2021"));
		bean.setExam_Time("3:00");
		bean.setSemester("Fourth");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Added");
		
	}
}
