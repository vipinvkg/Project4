package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.raystec.Bean.BaseBean;
import com.raystec.Bean.UserBean;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.ServletUtility;



/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 *
 * @author Vipin Gupta
 
 */
@WebServlet("/BaseCtl")
public abstract class BaseCtl extends HttpServlet {
	
	    public static final String OP_SAVE = "Save";
	    public static final String OP_CANCEL = "Cancel";
	    public static final String OP_DELETE = "Delete";
	    public static final String OP_LIST = "List";
	    public static final String OP_SEARCH = "Search";
	    public static final String OP_VIEW = "View";
	    public static final String OP_NEXT = "Next";
	    public static final String OP_PREVIOUS = "Previous";
	    public static final String OP_NEW = "New";
	    public static final String OP_GO = "Go";
	    public static final String OP_BACK = "Back";
	    public static final String OP_LOG_OUT = "Logout";
	    public static final String OP_UPDATE = "Update";
	    public static final String OP_RESET = "Reset";

	    /**
	     * Success message key constant
	     */
	    public static final String MSG_SUCCESS = "success";

	    /**
	     * Error message key constant
	     */
	    public static final String MSG_ERROR = "error";

	    /**
	     * 
	     * @param request validate methods
	     * @return value
	     */
	    
	    protected boolean validate(HttpServletRequest request) {
	        
	    	return true;
	        
	        
	    }

	    /**
	     * Loads list and other data required to display at HTML form
	     * @param preload methods
	 
	     */
	    protected void preload(HttpServletRequest request) {
	    }

	    /**
	     * Populates bean object from request parameters
	     * @return value
	     * @param populatebean
	 
	     */
	    protected BaseBean populateBean(HttpServletRequest request) {
	        return null;
	    }

	    /**
	     * Populates Generic attributes in DTO
	     * @return values
	     * @param populatebean
	     *
	  
	     */
	    protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

	        String createdBy = request.getParameter("createdBy");
	        String modifiedBy = null;

	        UserBean userbean = (UserBean) request.getSession()
	                .getAttribute("user");

	        if (userbean == null) {
	            // If record is created without login
	            createdBy = "root";
	            modifiedBy = "root";
	        } else {

	            modifiedBy = userbean.getLogin();

	            // If record is created first time
	            if ("null".equalsIgnoreCase(createdBy)
	                    || DataValidator.isNull(createdBy)) {
	                createdBy = modifiedBy;
	            }

	        }

	        dto.setCreatedBy(createdBy);
	        dto.setModifiedBy(modifiedBy);

	        int cdt = DataUtility.getInt(request.getParameter("createdDatetime"));

	        
	        if (cdt > 0) {
	            dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
	        } else {
	            dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
	        }

	        dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

	        return dto;
	    }

	    protected void service(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	    	System.out.println("Super");
	        // Load the preloaded data required to display at HTML form
	        preload(request);

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // Check if operation is not DELETE, VIEW, CANCEL, and NULL then
	        // perform input data validation
	        
	        if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op)
	                && !OP_VIEW.equalsIgnoreCase(op) 
	                && !OP_DELETE.equalsIgnoreCase(op) &&  !OP_RESET.equalsIgnoreCase(op)) {
	            // Check validation, If fail then send back to page with error
	            // messages
	        	
	            if (!validate(request)) {
	            	System.out.println("  Add user base ");
	                BaseBean bean = (BaseBean) populateBean(request);
	                ServletUtility.setBean(bean, request);;
	                ServletUtility.forward(getView(), request, response);
	                return;
	            }
	        }
	      
 super.service(request, response);
	    }

	    /**
	     * Returns the VIEW page of this Controller
	     * @return view page
	
	     */
	    protected abstract String getView();

	}

	