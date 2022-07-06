package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.MarksheetBean;
import com.raystec.Model.MarksheetModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;

@WebServlet("/ctl/GetMarksheetCtl")
public class GetMarksheetCtl extends BaseCtl{
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MarksheetBean bean = new MarksheetBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

		return bean;
	}
	
	 @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ServletUtility.forward(getView(), request, response);
		 super.doGet(request, response);
	}
	
	 @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String op = DataUtility.getString(request.getParameter("operation"));
		 
		 MarksheetModel model = new MarksheetModel();
		 MarksheetBean bean = (MarksheetBean) populateBean(request);
		 
		 int id = DataUtility.getInt(request.getParameter("id"));
		 
		 if (OP_GO.equalsIgnoreCase(op)) {
			
			 try {
				bean = model.findByRollNo(bean.getRollNo());
					if (bean !=null) {
						ServletUtility.setBean(bean, request);
					} else {
						ServletUtility.setErrorMessage("Roll No. doesn't exist", request);
					}
				 
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
			return;
		}
			ServletUtility.forward(getView(), request, response);
	}
	 @Override
	protected String getView() {
		return ORSView.GET_MARKSHEET_VIEW;
	}

	
}
