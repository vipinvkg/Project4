package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.RoleBean;
import com.raystec.Model.RoleModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet(name="RoleListCtl",urlPatterns={"/ctl/RoleListCtl"})
public class RoleListCtl extends BaseCtl {

	
@Override
	protected void preload(HttpServletRequest request)
	{
		RoleModel rmodel=new RoleModel();
		try {
			List rlist=rmodel.list();
			request.setAttribute("roleList", rlist);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}
	
	
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        RoleBean bean = new RoleBean();
        bean.setId(DataUtility.getLong(request.getParameter("roleid")));
      //  System.out.println("name>>>>>>"+request.getParameter("name"));

        return bean;
    }

    /**
     * Contains Display logics
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException{
        System.out.println(">>>>>>>inside doGet>>>>");
    	List list = null;
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        RoleBean bean = (RoleBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        RoleModel model = new RoleModel();
    	try {
        list = model.Search(bean, pageSize, pageNo);
		ServletUtility.setList(list, request);
		if (list == null || list.size() == 0) {
		    ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
	
			ServletUtility.forward(getView(), request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("do get end>>>>>>>>>");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String op = DataUtility.getString(request.getParameter("operation"));

      if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
			}
        List list = null;
        System.out.println(">>>>>>>inside dopost>>>>>>>.");
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;
        RoleBean bean = (RoleBean) populateBean(request);
		String[] ids = request.getParameterValues("ids");

        RoleModel model = new RoleModel();
        		
        if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) 
		        || "Previous".equalsIgnoreCase(op)) {

		    if (OP_SEARCH.equalsIgnoreCase(op)) {
		    	System.out.println("if search>>>>>>>>>");
		        pageNo = 1;
		    }
		    
		    else if (OP_NEXT.equalsIgnoreCase(op)) {
		        pageNo++;
		    } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
		        pageNo--;
		    }
		    else{
		    	ServletUtility.setErrorMessage("Select at least one record", request);
		    }
        }
	    else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
	    }else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
	    RoleBean deletebean = new RoleBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Record Deleted Successfully", request);

					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
	
        
		}
		list = model.Search(bean, pageNo, pageSize);
		
		ServletUtility.setList(list, request);
		
		if (list == null || list.size() == 0) {
		    ServletUtility.setErrorMessage("No record found ", request);
		}
	    ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);

		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
    }

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_LIST_VIEW;
	}

}
