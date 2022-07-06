package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.SubjectBean;
import com.raystec.Model.CourseModel;
import com.raystec.Model.SubjectModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

@WebServlet(name = "SubjectCtl" , urlPatterns = {"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl{

	@Override
	protected void preload(HttpServletRequest request) {
		CourseModel cmodel = new CourseModel();
		try {
			List cList = cmodel.list();
			request.setAttribute("CourseList", cList);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		
		}
		
	}
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require","Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("Enter te valid Subject Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("coursename"))) {
			request.setAttribute("coursename", PropertyReader.getValue("error.require","Course Name"));
			pass = false;
		}
		return pass;
	}
	@Override
	protected SubjectBean populateBean(HttpServletRequest request) {
		SubjectBean bean = new SubjectBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourse_id(DataUtility.getInt(request.getParameter("coursename")));
		bean.setSubject_Name(DataUtility.getString(request.getParameter("name")));
		bean.setDiscription(DataUtility.getString(request.getParameter("description")));
		
		
		populateDTO(bean, request);
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		
		SubjectModel model = new SubjectModel();
		SubjectBean bean = null;
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (id>0 || op != null) {
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
								
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			
			}
		}
		
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		SubjectModel model = new SubjectModel();
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				if (id>0) {
					model.update(bean);
				}else {
					long pk = model.add(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully added", request);
				
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;			
			}catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject name already Exists", request);
			
			}
		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
		else if (OP_DELETE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	
	}
	@Override
	protected String getView() {

		return ORSView.SUBJECT_VIEW;
	}

}
