package com.raystec.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.Bean.UserBean;
import com.raystec.Model.UserModel;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;
import com.raystec.exception.RecordNotFoundException;

public class Test_UserModel {

	public static UserModel model = new UserModel();
	
	public static void main(String[] args) throws ParseException, ApplicationException, DuplicateRecordException, RecordNotFoundException {
		
		//testAdd();
		//testDelete();
		testUpdate();
		//testFindByPk();
//		testFinfByLogin();
		//testGetRole();
		//testSearch();
		//testList();
		//testAuthenticate();
		//testRegisterUser();
		//testChangePassword();
		//testForgetPassword();
		//testResetPassword();
	}
	
		private static void testAdd() throws ParseException, ApplicationException, DuplicateRecordException {
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setFirstName("Ankit");
		bean.setLastName("Singh");
		bean.setLogin("ankit@gmail.com");
		bean.setPassword("ankit123");
		bean.setConfirmPassword("ankit@123");
		bean.setDob(sdf.parse("06/07/1980"));
		bean.setMobileNo("8889675324");
		bean.setRoleId(5);
		bean.setGender("Male");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		
		System.out.println("Successfully added");
		
	}
		private static void testDelete() throws ApplicationException {
			UserBean bean = new UserBean();
			bean.setId(4);
			model.delete(bean);
			
			System.out.println("Successfully deleted");
		}
		private static void testUpdate() throws ApplicationException, DuplicateRecordException, ParseException {
			UserBean bean = model.findByPk(3);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setFirstName("Ritesh");
			bean.setLastName("Guptaaaaa");
			bean.setLogin("Ritesh@gmail.com");
			bean.setPassword("ritesh123");
			model.update(bean);
			System.out.println("Successfully updated");			
			
		}
		private static void testFindByPk() throws ApplicationException {
		UserBean bean = model.findByPk(1);
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLogin());
		System.out.println(bean.getPassword());
			
		}	
		private static void testFinfByLogin() throws ApplicationException {
			UserBean bean = model.findByLogin("gupta.vipin02@gmail.com");
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
				
			
		}
		private static void testGetRole() throws ApplicationException {
			UserBean bean = new UserBean();
			bean.setRoleId(2);
			List list= new ArrayList();
			list = model.getRoles(bean);
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getDob());
				
			}
			
			
		}
		private static void testSearch() throws ApplicationException {
		UserBean bean = new UserBean();
		//bean.setId(2);
		//bean.setFirstName("vip");
		bean.setLastName("ja");
		List list = new ArrayList();
		list = model.search(bean, 10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getGender());

		}
		
					
		}


		private static void testList() throws ApplicationException {
		UserBean bean = new UserBean();
		List list = new ArrayList();
		list = model.list(10, 1);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
		}
			
		}
		
	private static void testAuthenticate() throws ApplicationException {
		UserBean bean = new UserBean();
		bean.setLogin("vipin@gmail.com");
		bean.setPassword("vipin123");
		bean = model.authenticate(bean.getLogin(), bean.getPassword());
		
		if (bean!= null) {
			System.out.println("Successully login");	
		} else {
			System.out.println("Invalid Login");
		}
		
		
	}

	private static void testRegisterUser() throws ParseException, ApplicationException, DuplicateRecordException {
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setFirstName("Priyal");
		bean.setLastName("Saxena");
		bean.setLogin("priyal@gmail.com");
		bean.setPassword("1234");
		bean.setConfirmPassword("1234");
		bean.setMobileNo("9898989898");
		bean.setDob(sdf.parse("02/09/1988"));
		bean.setGender("female");
		bean.setRoleId(1);
		
		model.registerUser(bean);
		
		System.out.println("Successfully register");
	}


	private static void testChangePassword() throws ApplicationException, RecordNotFoundException, DuplicateRecordException {
		UserBean bean = model.findByLogin("ankit@gmail.com");
		String oldPassword = bean.getPassword();
		bean.setId(5);
		bean.setPassword("gagan@123");
		String newPassword = bean.getPassword();
		model.changePassword(5, oldPassword, newPassword);
		System.out.println("password has been changed");
	
}

	private static void testForgetPassword() throws ApplicationException, RecordNotFoundException {
		boolean b = model.forgetPassword("Vipin@gmail.com");
		System.out.println("forget password success");
		
	}
	private static void testResetPassword() throws ApplicationException {
		UserBean bean = new UserBean();
		bean = model.findByLogin("vipin@gmail.com");
		boolean pass = model.resetPassword(bean);
		if(pass = false) {
			System.out.println("test update fail");
		}
	}		
}
