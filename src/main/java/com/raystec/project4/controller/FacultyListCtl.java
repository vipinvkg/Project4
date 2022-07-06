package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.FacultyBean;
import com.raystec.Model.FacultyModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet (name = "FacultyListCtl" , urlPatterns = {"/ctl/FacultyListCtl"})
public class FacultyListCtl extends BaseCtl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void preload(HttpServletRequest request) {
		FacultyModel Fmodel = new FacultyModel();
		try {
			List flist = Fmodel.list(0, 0);
			request.setAttribute("FList", flist);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		FacultyBean bean= new FacultyBean();
		bean.setId(DataUtility.getLong(request.getParameter("firstname")));
		bean.setLast_Name(DataUtility.getString(request.getParameter("lastname")));
		bean.setEmail_id(DataUtility.getString(request.getParameter("login")));
		
		
		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list;
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = (FacultyBean) populateBean(request); 
		
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		
		try {
			list = model.search(bean, pageNo,pageSize);
			ServletUtility.setList(list, request);
			if (list==null || list.size()== 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		pageNo = (pageNo==0) ? 1 : pageNo;
		pageSize = (pageSize==0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();
		
		String [] ids = (String [])request.getParameterValues("ids");
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				}else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				}
				else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					if (pageNo>1) {
						pageNo--;
					}else {
						pageNo = 1;
					}
				}
				else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
					return;
				}else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
					return;
				}
				else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids!=null && ids.length!=0) {
						FacultyBean deletebean = new FacultyBean();
						for (String id : ids) {
							deletebean.setId(DataUtility.getInt(id));
							try {
								model.delete(deletebean);
							} catch (ApplicationException e) {
								e.printStackTrace();
								ServletUtility.handleException(e, request, response);
								return;
							
							}
							ServletUtility.setSuccessMessage("Data deleted Successfully", request);
						}
					}else {
						ServletUtility.setErrorMessage("Select at least one Record", request);
					}
				}
				try {
					list = model.search(bean, pageNo, pageSize);
					
				} catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
				
				}
				if (list == null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found", request);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);
				
	}
	
	
	@Override
	protected String getView() {
		return ORSView.FACULTY_LIST_VIEW;
	}

}
