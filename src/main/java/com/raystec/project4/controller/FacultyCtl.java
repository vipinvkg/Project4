package com.raystec.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.CollegeBean;
import com.raystec.Bean.CourseBean;
import com.raystec.Bean.FacultyBean;
import com.raystec.Bean.SubjectBean;
import com.raystec.Model.CollegeModel;
import com.raystec.Model.CourseModel;
import com.raystec.Model.FacultyModel;
import com.raystec.Model.SubjectModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

@WebServlet (name="FacultyCtl" , urlPatterns ={"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void preload (HttpServletRequest request){
		
		System.out.println("preload  in ");
		
		CourseModel cmodel = new CourseModel();
		CollegeModel comodel = new CollegeModel();
		SubjectModel smodel = new SubjectModel();
		
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<CollegeBean> colist = new ArrayList<CollegeBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		
		try {
			clist = cmodel.list();
			colist = comodel.list(0, 0);
			slist = smodel.list();
			
				request.setAttribute("CourseList", clist);
				request.setAttribute("CollegeList", colist);
				request.setAttribute("SubjectList", slist);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected boolean validate(HttpServletRequest request){
		
		System.out.println("validate  in ");
		
		boolean pass = true;
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname", PropertyReader.getValue("error.require", "FirstName"));
			 pass = false;
			
		}else if (!DataValidator.isValidName(request.getParameter("firstname"))) {
			request.setAttribute("firstname", PropertyReader.getValue( "Enter the valid First name"));
			 pass = false;
		}
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		}else if (!DataValidator.isValidName(request.getParameter("lastname"))) {
			request.setAttribute("lastname", PropertyReader.getValue( "Enter the valid Last name"));
			 pass = false;
			
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require","Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("doj"))) {
			request.setAttribute("doj", PropertyReader.getValue("error.require","Date Of joining"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("doj"))) {
			request.setAttribute("doj", PropertyReader.getValue("error.date","Date Of joining"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("qualification"))){
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		}else if (!DataValidator.isEmail(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getValue("Enter the valid login id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		}else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("collegeid"))) {
			request.setAttribute("collegeid", PropertyReader.getValue("error.require", "CollegeName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("courseid"))) {
			request.setAttribute("courseid", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("subjectid"))) {
			request.setAttribute("subjectid", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}
		
		
		System.out.println("validate out ");
		return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
		System.out.println(" populate bean ctl  in ");
		FacultyBean bean = new FacultyBean();
	
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirst_Name(DataUtility.getString(request.getParameter("firstname")));
		bean.setLast_Name(DataUtility.getString(request.getParameter("lastname")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDOJ(DataUtility.getDate(request.getParameter("doj")));
		
		System.out.println("populate bean date faculty-------------..."+request.getParameter("doj"));
		bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
		bean.setEmail_id(DataUtility.getString(request.getParameter("loginid")));
		bean.setMobile_No(DataUtility.getString(request.getParameter("mobileno")));
		bean.setCollege_id(DataUtility.getInt(request.getParameter("collegeid")));
		bean.setCourse_id(DataUtility.getInt(request.getParameter("courseid")));
		bean.setSubject_id(DataUtility.getInt(request.getParameter("subjectid")));
//		bean.setCourseName(DataUtility.getString(request.getParameter("courseid")));
//		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectid")));
		populateDTO(bean, request);
		System.out.println(" populate bean ctl out ");
		return bean;
	}
	
	
    /**
     * Contains Display logics
     */




	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		//Get Model
		FacultyModel model = new FacultyModel();
		Long id = DataUtility.getLong(request.getParameter("id"));
		
		if(id>0 || op!= null){
			FacultyBean bean;
			try {
				bean = model.findByPk(id);
			ServletUtility.setBean(bean, request);
			
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		System.out.println(" do get out ");
		ServletUtility.forward(getView(), request, response);
	}

    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));	
			
		// Get Model
		FacultyModel model = new FacultyModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			System.out.println("date in f post---------"+bean.getDOJ());
			try{
			if(id > 0){
				model.update(bean);
				ServletUtility.setBean(bean, request);

			}else{
			long pk = model.add(bean);
		//		bean.setId(pk);
			}
			ServletUtility.setSuccessMessage("Faculty Successfully Register", request);
			}catch(ApplicationException e){
				ServletUtility.handleException(e, request, response);
				return ; 
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Faculty already Exist", request);
			}
		}/*else if (OP_DELETE.equalsIgnoreCase(op)) {
			FacultyBean bean =(FacultyBean) populateBean(request);
	
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
	}*/	else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return ;
			}			
	else if (OP_CANCEL.equalsIgnoreCase(op) ) {
		ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		return ;
	}			
		System.out.println(" do post out ");
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}