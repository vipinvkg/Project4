package com.raystec.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.CourseBean;
import com.raystec.Model.CourseModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		CourseModel model = new CourseModel();
		List<CourseBean> clist = null;
		
		try {
			clist = model.list();
		} catch (ApplicationException e) {
			e.printStackTrace();
		
		}
		request.setAttribute("CourseList", clist);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong(request.getParameter("cname")));
		populateDTO(bean, request);
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CourseBean> list = new ArrayList<CourseBean>();
		int pageNo= 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();
		
		try {
			list = model.search(bean,pageNo, pageSize);
			
			if (list==null || list.size()==0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo==0) ? 1 : pageNo;
		pageSize = (pageSize==0) ? DataUtility.getInt(request.getParameter("pageSize")) : pageSize;
		
		String op = DataUtility.getString(request.getParameter("operation"));
		String [] ids = request.getParameterValues("ids");
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();
		
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		}else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		}else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			pageNo--;
		}else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids!= null && ids.length>0) {
				CourseBean deletebean = new CourseBean();
				for (String id: ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Course deleted successfully", request);
				}
			}else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageSize, pageNo);
			ServletUtility.setBean(bean, request);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list==null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
