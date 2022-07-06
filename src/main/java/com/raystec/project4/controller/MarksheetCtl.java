package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.MarksheetBean;
import com.raystec.Model.MarksheetModel;
import com.raystec.Model.StudentModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DatabaseException;
import com.raystec.exception.DuplicateRecordException;

@ WebServlet(name="MarksheetCtl",urlPatterns={"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl {
@Override
protected void preload(HttpServletRequest request) {
	StudentModel model = new StudentModel();
	
	try {
		List l = model.list();
		request.setAttribute("Studentlist", l);
	} catch (ApplicationException e) {
		
	
	}
	
}
@Override
	protected boolean validate(HttpServletRequest request) {
	boolean pass = true;
	if (DataValidator.isNull(request.getParameter("rollNo"))) {
		request.setAttribute("rollNo", PropertyReader.getValue("error.require", "RollNumber"));
		pass=false;
		}
		  else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
		  request.setAttribute("rollNo", "Enter the Valid Roll no."); pass = false; 
	}if (DataValidator.isNull(request.getParameter("physics"))) {
		request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics Number"));
		pass = false;
	}if (DataValidator.isNotNull(request.getParameter("physics")) && !DataValidator.isInteger(request.getParameter("physics"))) {
		request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
		pass = false;
	}if (DataUtility.getInt(request.getParameter("physics"))>100) {
		request.setAttribute("physics", "Marks cannot be greater than 100");
		pass = false;
	}
	
	if (DataUtility.getInt(request.getParameter("physics"))<0) {
		request.setAttribute("physics", "Marks cannot be less than 0");
		pass = false;
	}
	if (DataValidator.isNull(request.getParameter("chemistry"))) {
		request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Number"));
		pass = false;
	}else if (DataValidator.isNotNull(request.getParameter("chemistry"))&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
		request.setAttribute("chemistry", PropertyReader.getValue("error.integer","Marks"));
		pass = false;
	}
	if (DataUtility.getInt(request.getParameter("chemistry"))>100) {
		request.setAttribute("chemistry", "Marks cannot be greater than 100");
		pass = false;
	}
	if (DataUtility.getInt(request.getParameter("chemistry"))<0) {
		request.setAttribute("chemistry", "Marks cannot be less than 0");
		pass = false;
	}if (DataValidator.isNull(request.getParameter("maths"))) {
		request.setAttribute("chemistry", PropertyReader.getValue("error.require","Maths Number"));
		pass = false;
	}else if (DataValidator.isNotNull(request.getParameter("maths"))&& !DataValidator.isInteger(request.getParameter("maths"))) {
		request.setAttribute("maths", PropertyReader.getValue("error.integer", "Maths"));
		pass = false;		
	}
	if (DataUtility.getInt(request.getParameter("maths"))>100) {
		request.setAttribute("maths", "Marks can not be greater than 100");
		pass = false;
	}
	if (DataUtility.getInt(request.getParameter("maths"))<0) {
		request.setAttribute("maths", "Marks cannot be less than 0");
		pass = false;
	}
	if (DataValidator.isNull(request.getParameter("StudentId"))) {
		request.setAttribute("StudentId", PropertyReader.getValue("error.require","Student Name"));
	}

	return pass;
	}

@Override
	protected BaseBean populateBean(HttpServletRequest request) {
	MarksheetBean bean = new MarksheetBean();
	bean.setId(DataUtility.getInt(request.getParameter("id")));
	bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
	bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
	bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
	bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
	bean.setStudentId(DataUtility.getLong(request.getParameter("StudentId")));
	populateDTO(bean, request);
	return bean;
	}

	@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MarksheetModel model = new MarksheetModel();
		int id = DataUtility.getInt(request.getParameter("id"));
		if (id>0) {
			MarksheetBean bean;
			try {
				bean = model.findByPK(id);
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
		MarksheetModel model = new MarksheetModel();
		int id = DataUtility.getInt(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetBean bean = (MarksheetBean) populateBean(request);
			
			try {
				if (id>0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is Successfully Updated", request);
				}else {
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is Successfully Saved", request);
				}
			} catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
			}catch (DuplicateRecordException e) {
					e.printStackTrace();
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Rollno. is already exists", request);
			
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}else if (OP_DELETE.equalsIgnoreCase(op)) {
			MarksheetBean bean = (MarksheetBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	
	}
	
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}
