package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.CourseBean;
import com.raystec.Model.CourseModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

@WebServlet(name="CourseCtl", urlPatterns={"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl{
	
	private static final long serialVersionUID = 1L;
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
			pass =  false;
		}else if (DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("Enter the valis course name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourse_Name(DataUtility.getString(request.getParameter("name")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDiscription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);
		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		CourseModel model = new CourseModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (id>0) {
			CourseBean bean;
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
		String op= DataUtility.getString(request.getParameter("operation"));
		CourseModel model = new CourseModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			try {
				if (id>0) {
					model.update(bean);
				}else {
					long pk = model.add(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course is successfully updated", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course Name Already Exists", request);
			}
		}else if (OP_DELETE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
				try {
					model.delete(bean);
					ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
					return;
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;				
				}
		}
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	
	}
	
	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

	
}
