package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.CollegeBean;
import com.raystec.Model.CollegeModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet(name="CollegeListCtl",urlPatterns={"/ctl/CollegeListCtl"})
public class CollegeListCtl extends BaseCtl {
	    

		protected void preload(HttpServletRequest request) {

			CollegeModel Cmodel = new CollegeModel();
			try {
				List clist = Cmodel.list(0, 0);
				request.setAttribute("CList", clist);

			} catch (ApplicationException e) {
				e.printStackTrace();
				}
			}

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {
	        CollegeBean bean = new CollegeBean();

	        bean.setId(DataUtility.getLong(request.getParameter("name")));
	        bean.setCity(DataUtility.getString(request.getParameter("city")));

	        return bean;
	    }

	    /**
	     * Contains display logic
	     */
	    @Override
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        int pageNo = 1;
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

	        CollegeBean bean = (CollegeBean) populateBean(request);
	        String [] ids = request.getParameterValues("ids");
	        CollegeModel model = new CollegeModel();

	        List list = null;

	        try {
	            list = model.search(bean, pageNo, pageSize);
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
	     * Contains submit logic
	     */
	    @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        String op = DataUtility.getString(request.getParameter("operation"));
	        if (OP_RESET.equalsIgnoreCase(op)) {
	 			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
	 			return;
	 			}

	        List list = null;

	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	        pageNo = (pageNo == 0) ? 1 : pageNo;

	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;

	        CollegeBean bean = (CollegeBean) populateBean(request);
	               String [] ids = request.getParameterValues("ids");
	        String ops = DataUtility.getString(request.getParameter("operation"));

	        CollegeModel model = new CollegeModel();

	        try {

	            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                    || "Previous".equalsIgnoreCase(op) || OP_NEW.equalsIgnoreCase(op)  || OP_DELETE.equalsIgnoreCase(op)) {

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                }  else if (OP_NEW.equalsIgnoreCase(op)) {
						ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
						return ;
					}
	                else if (OP_RESET.equalsIgnoreCase(op)) {
						ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
						return ;
					}
	                else if (OP_DELETE.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                    if (ids != null && ids.length > 0) {
	                       CollegeBean deletebean = new CollegeBean();
	                  
	                        for (String id : ids) {
	                            deletebean.setId(DataUtility.getInt(id));
	                            try {
	    							model.delete(deletebean);
	    						} catch (ApplicationException e) {
	    							ServletUtility.handleException(e, request, response);
	    							return;
	    						} catch (Exception e) {
	    							// TODO Auto-generated catch block
	    							e.printStackTrace();
	    						}ServletUtility.setSuccessMessage("College Data Successfully Deleted", request);
	                        }
	                    } 
	                    else {
	                        ServletUtility.setErrorMessage(
	                                "Select at least one record", request);
	                    }
	                }
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

	        } catch (ApplicationException e) {
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	    }


		@Override
		protected String getView() {
			// TODO Auto-generated method stub
			return ORSView.COLLEGE_LIST_VIEW;
		}

	}