package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.StudentBean;
import com.raystec.Model.StudentModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet("/ctl/StudentListCtl")
public class StudentListCtl extends BaseCtl{
	   @Override
		protected void preload(HttpServletRequest request) {

			StudentModel Smodel = new StudentModel();
			try {
				List Slist = Smodel.list();
				request.setAttribute("StudentList", Slist);

			} catch (ApplicationException e) {
				e.printStackTrace();
				}
			}

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {

	        StudentBean bean = new StudentBean();

	        bean.setId(DataUtility.getLong(request
	                .getParameter("firstName")));
	        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
	        bean.setEmail(DataUtility.getString(request.getParameter("email")));

	        return bean;
	    }

	    /**
	     * Contains Display logics
	     */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        List list = null;

	        int pageNo = 1;

	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

	        StudentBean bean = (StudentBean) populateBean(request);

	        String op = DataUtility.getString(request.getParameter("operation"));

	        StudentModel model = new StudentModel();
	        try {
	            list = model.search(bean, pageNo,pageSize);
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No record found ", request);
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

	    /**
	     * Contains Submit logics
	     */
	    @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        String ops = DataUtility.getString(request.getParameter("operation"));
	       
	        
	        
	        List list = null;
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;

	        StudentBean bean = (StudentBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        StudentModel model = new StudentModel();

	        try {

	            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                    || "Previous".equalsIgnoreCase(op) || OP_NEW.equalsIgnoreCase(op)) {

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                } else if (OP_NEW.equalsIgnoreCase(op)) {
						ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
						return ;
					}
	            }else  if (OP_RESET.equalsIgnoreCase(ops)) {
	     			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
	     			return;

	        } 
	            
	            list = model.search(bean, pageNo, pageSize);
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }
	            ServletUtility.setBean(bean, request);
	            ServletUtility.setList(list, request);

	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(getView(), request, response);

	        } catch (Exception e) {
	        	e.printStackTrace();
	            //ServletUtility.handleException(e, request, response);
	            return;
	        }

	    }

	   


		@Override
		protected String getView() {
			// TODO Auto-generated method stub
			return ORSView.STUDENT_LIST_VIEW;
		}

	}

