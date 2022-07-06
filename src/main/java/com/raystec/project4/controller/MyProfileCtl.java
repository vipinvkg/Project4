package com.raystec.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
import com.raystec.exception.DuplicateRecordException;

@ WebServlet(name="MyProfileCtl",urlPatterns={"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl {

    public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue( "Enter the valid firstName"));
			 pass = false ;
		}

        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("Enter the valid Last Name"));
			 pass = false ;
		}

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
            pass = false;
        }
        else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
        	request.setAttribute("mobileNo", PropertyReader.getValue("Enter the valid Mobile no."));
        	pass = false;
			
		}
        if (DataValidator.isNull("dob")) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dob"))) {
        	request.setAttribute("dob", PropertyReader.getValue("Enter the valid Dob"));
        	pass = false;
        
        }
      
        return pass;

    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        UserBean bean = new UserBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setFirstName(DataUtility.getString(request .getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
   

        populateDTO(bean, request);
        return bean;
    }
    /**
     * Contains Display logics
     */

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        UserBean UserBean = (UserBean) session.getAttribute("user");
        long id = UserBean.getId();
        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();
        
        if (id > 0 || op != null) {
            UserBean bean;
            try {
                bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * contain Submit Logic
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        UserBean UserBean = (UserBean) session.getAttribute("user");
        long id = UserBean.getId();
        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            UserBean bean = (UserBean) populateBean(request);
            try {
                if (id > 0) {
                    UserBean.setFirstName(bean.getFirstName());
                    UserBean.setLastName(bean.getLastName());
                    UserBean.setGender(bean.getGender());
                    UserBean.setMobileNo(bean.getMobileNo());
                    UserBean.setDob(bean.getDob());
                  
                    model.update(UserBean);

                }	
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage( "Profile is updated Successfully. ", request);
            } catch (ApplicationException e) {
            	e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login id already exists", request);
            }
        } else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request,
                    response);
            return;

        }

        ServletUtility.forward(getView(), request, response);

    }

    @Override
    protected String getView() {
        return ORSView.MY_PROFILE_VIEW;
    }

}