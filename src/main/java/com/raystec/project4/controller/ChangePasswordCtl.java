package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.UserBean;
import com.raystec.Model.UserModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.RecordNotFoundException;

@WebServlet(name="ChangePasswordCtl",urlPatterns = {"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl{

	  public static final String OP_CHANGE_MY_PROFILE = "Change Profile";

	    @Override
	    protected boolean validate(HttpServletRequest request) {
	        String op = request.getParameter("operation");
	        boolean pass = true;

	        if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
	            return pass;
	        }
	        if (DataValidator.isNull(request.getParameter("oldpassword"))) {
	            request.setAttribute("oldpassword",PropertyReader.getValue("error.require", "Old Password"));
	            pass = false;
	        }
	        if (DataValidator.isNull(request.getParameter("newpassword"))) {
	            request.setAttribute("newpassword",PropertyReader.getValue("error.require", "New Password"));
	            pass = false;
	        }
	       
	        if (DataValidator.isNull(request.getParameter("confirmpassword"))) {
	            request.setAttribute("confirmpassword", PropertyReader.getValue("error.require", "Confirm Password"));
	            pass = false;
	        }
	        
	        if (!request.getParameter("newpassword").equals(request.getParameter("confirmpassword"))
	                && !"".equals(request.getParameter("confirmpassword"))) {
	           request.setAttribute("confirmpassword", PropertyReader.getValue( "Password And Confirm Password should be match"));
	            pass = false;
	        }

	        return pass;
	    }

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {

	        UserBean bean = new UserBean();

	        bean.setPassword(DataUtility.getString(request.getParameter("oldpassword")));
	        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmpassword")));

	        populateDTO(bean, request);
	        return bean;
	    }

	    /**
	     * Contains Display logics
	     */

	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        ServletUtility.forward(getView(), request, response);
	    }
	    /**
	     * Contains Submit logics
	     */

	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        HttpSession session = request.getSession();
	        String op = DataUtility.getString(request.getParameter("operation"));

	        UserModel model = new UserModel();
	        UserBean bean = (UserBean) populateBean(request); 
	        UserBean UserBean = (UserBean) session.getAttribute("user");
	        String newPassword = (String) request.getParameter("newpassword");
	        long id = UserBean.getId();

	        if (OP_SAVE.equalsIgnoreCase(op)) {
	            try {
	                boolean flag = model.changePassword(id, bean.getPassword(),newPassword);
	                if (flag == true) {
	                    bean = model.findByLogin(UserBean.getLogin());
	                    session.setAttribute("user", bean);
	                    ServletUtility.setBean(bean, request);
	                    ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
		                }
	            } catch (ApplicationException e) {
	            	e.printStackTrace();
	                ServletUtility.handleException(e, request, response);
	                return;

	            } catch (RecordNotFoundException e) {
	                ServletUtility.setErrorMessage("Old Password is Invalid",request);
	                
	            }
	        }else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
				return;

	        } else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
	            return;
	        }

	        ServletUtility.forward(getView(), request, response);
	    }

	    @Override
	    protected String getView() {
	        return ORSView.CHANGE_PASSWORD_VIEW;
	    }

	}