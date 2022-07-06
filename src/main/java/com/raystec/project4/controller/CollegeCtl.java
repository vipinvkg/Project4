package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.CollegeBean;
import com.raystec.Model.CollegeModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("Enter the valid name"));
			pass = false;
		}
			if (DataValidator.isNull(request.getParameter("address"))) {
				request.setAttribute("address", PropertyReader.getValue("error.require" , "Address"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("state"))) {
				request.setAttribute("state", PropertyReader.getValue("error.require" , "State"));
				pass = false;
			}if (DataValidator.isNull(request.getParameter("city"))) {
				request.setAttribute("city", PropertyReader.getValue("error.require" , "City"));
				pass = false;
			}if (DataValidator.isNull(request.getParameter("phoneNo"))) {
				request.setAttribute("phoneNo", PropertyReader.getValue("error.require" , "Phone No"));
				pass = false;
			}else if (!DataValidator.isMobileNo(request.getParameter("phoneNo"))) {
				request.setAttribute("phoneNo", PropertyReader.getValue("Enter the valid no. "));
				pass = false;
			}
			
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CollegeBean bean = new CollegeBean();
		
		bean.setId(DataUtility.getInt(request.getParameter("id")));
		
		bean.setName(DataUtility.getString(request.getParameter("name")));
		
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		
		bean.setState(DataUtility.getString(request.getParameter("state")));
		
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		
		populateDTO(bean, request);
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CollegeModel model = new CollegeModel();
		
		int id = DataUtility.getInt(request.getParameter("id"));
		
		if (id>0) {
			CollegeBean bean;
			try {
				bean = model.findbypk(id);
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
		
			CollegeModel model = new CollegeModel();
			long id = DataUtility.getLong(request.getParameter("id"));
			
			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
				CollegeBean bean = (CollegeBean) populateBean(request);
				
				try {
					if (id>0) {
						model.update(bean);
						ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Data is Successfully Updated", request);
					
					}else {
						long pk = model.add(bean);
					}
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is Successfully added", request);
				} catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
				}catch (DuplicateRecordException e) {
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("College Name already Exists", request);
				}
			}else if (OP_DELETE.equalsIgnoreCase(op)) {
				CollegeBean bean = (CollegeBean) populateBean(request);
				try {
					model.delete(bean);
					ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
					return;
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
			}else if (OP_CANCEL.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;
			}
			
			ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
