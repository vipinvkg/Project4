package com.raystec.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.MarksheetBean;
import com.raystec.Model.MarksheetModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DatabaseException;
import com.raystec.exception.DuplicateRecordException;

public class Test_MarksheetModel {

	static MarksheetBean bean = new MarksheetBean();
	static MarksheetModel model = new MarksheetModel();
public static void main(String[] args) throws ApplicationException, DatabaseException {
	//testAdd();
	//testDelete();
	//testUpdate();
	//testFindByRollNo();
	//testFindByPK();
	//testSearch();
	testMeritList();
	//testList();
}
private static void testList() throws ApplicationException {
	List list = new ArrayList();
	list = model.list(0, 10);
	Iterator it = list.iterator();
	while (it.hasNext()) {
		bean = (MarksheetBean) it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getRollNo());
		System.out.println(bean.getName());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getChemistry());
		System.out.println(bean.getMaths());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
	}
	
}
private static void testMeritList() throws ApplicationException {
	List list = new ArrayList();
	list = model.getMeritList(10, 0);
	Iterator it = list.iterator();
	while (it.hasNext()) {
		bean = (MarksheetBean) it.next();
		System.out.print(bean.getId());
		System.out.print(bean.getRollNo());
		System.out.print(bean.getName());
		System.out.print(bean.getPhysics());
		System.out.print(bean.getChemistry());
		System.out.println(bean.getMaths());
	}
}
private static void testSearch() throws ApplicationException {
	List list = new ArrayList();
	bean.setRollNo("101");
	list= model.search(bean, 0, 10);
	Iterator it = list.iterator();
	while (it.hasNext()) {
		bean = (MarksheetBean) it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getRollNo());
		System.out.println(bean.getName());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getChemistry());
		System.out.println(bean.getMaths());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
	}
	
}
private static void testFindByPK() throws ApplicationException {
	bean = model.findByPK(2);
	System.out.println(bean.getId());
	System.out.println(bean.getRollNo());
	System.out.println(bean.getName());
	System.out.println(bean.getChemistry());
	System.out.println(bean.getPhysics());
	System.out.println(bean.getMaths());
}
private static void testFindByRollNo() throws ApplicationException {
	bean = model.findByRollNo("105");
	System.out.println(bean.getId());
	System.out.println(bean.getRollNo());
	System.out.println(bean.getStudentId());
	System.out.println(bean.getName());
	System.out.println(bean.getChemistry());
	System.out.println(bean.getPhysics());
	System.out.println(bean.getMaths());
	
	
}
private static void testUpdate() throws ApplicationException {
	bean.setId(7);
	bean.setRollNo("107");
	bean.setName("Mayank");
	bean.setStudentId(8);
	bean.setPhysics(30);
	bean.setChemistry(72);
	bean.setMaths(80);
	bean.setCreatedBy("Admin");
	bean.setModifiedBy("Admin");
	bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	model.update(bean);
	
	System.out.println("Successfully Updated");
}
private static void testDelete() throws ApplicationException {
	bean.setId(4);
	model.delete(bean);
	System.out.println("Succecfully deleted");
}
private static void testAdd() throws ApplicationException, DatabaseException, DuplicateRecordException {
	bean.setRollNo("107");
	bean.setName("Vikas");
	bean.setStudentId(9);
	bean.setPhysics(71);
	bean.setChemistry(81);
	bean.setMaths(91);
	bean.setCreatedBy("Admin");
	bean.setModifiedBy("Admin");
	bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	
	model.add(bean);
	
	System.out.println("Added successfully");
	
}

}
