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

@WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {
	  @Override
	    protected BaseBean populateBean(HttpServletRequest request) {
	        MarksheetBean bean = new MarksheetBean();

	        return bean;
	    }

	    /**
	     * Contains Display logics
	     */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {


	        int pageNo = 1;

	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

	        MarksheetBean bean = (MarksheetBean) populateBean(request);

	        String op = DataUtility.getString(request.getParameter("operation"));

	        MarksheetModel model = new MarksheetModel();
	        List list = null;
	        try {
	            list = model.getMeritList(pageNo, pageSize);
	           // ServletUtility.setList(list, request);
	            
	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }
	            ServletUtility.setList(list, request);
	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(ORSView.MARKSHEET_MERIT_LIST_VIEW, request,
	                    response);
	        } catch (ApplicationException e) {
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	    }

	    /**
	     * Contains Submit logics
	     */
	    @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        List list = null;
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;
	        MarksheetBean bean = (MarksheetBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        MarksheetModel model = new MarksheetModel();
	        try {
	            if (OP_BACK.equalsIgnoreCase(op)) {
	                ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
	                return;
	            }
	            list = model.getMeritList(pageNo, pageSize);
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }
	            ServletUtility.setList(list, request);
	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(ORSView.MARKSHEET_MERIT_LIST_VIEW, request,
	                    response);
	        } catch (ApplicationException e) {
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	    }
		@Override
		protected String getView() {
			// TODO Auto-generated method stub
			return ORSView.MARKSHEET_MERIT_LIST_VIEW;
		}

	}