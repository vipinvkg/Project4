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
import com.raystec.Util.DataUtility;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		MarksheetModel model = new MarksheetModel();
		try {
			List mlist = model.list();
			request.setAttribute("marksheetList", mlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MarksheetBean bean = new MarksheetBean();
		bean.setId(DataUtility.getLong(request.getParameter("name")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo == 0)? 1: pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		
		List list= null;
		MarksheetModel model = new MarksheetModel();
		try {
			list = model.search(bean, pageNo, pageSize);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
		
		}
		if (list == null || list.size()==0) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list = null;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo==0) ? 1 : pageNo;
		pageSize = (pageSize==0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String [] ids = request.getParameterValues("ids");
		
		MarksheetModel model = new MarksheetModel();
		
		try {
			
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				}else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				}else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			}else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			}else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
				return;
			}else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					MarksheetBean deletebean = new MarksheetBean();
					for(String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}	
				}else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
				}
			list = model.search(bean, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size()== 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		
		}
	}
	
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_LIST_VIEW;
	}
}
