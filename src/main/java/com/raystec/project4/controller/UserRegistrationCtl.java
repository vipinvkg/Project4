package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.RoleBean;
import com.raystec.Bean.UserBean;
import com.raystec.Model.UserModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

@WebServlet (name="UserRegistrationCtl",urlPatterns = {"/UserRegistrationCtl"})

public class UserRegistrationCtl extends BaseCtl{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String OP_SIGN_UP = "SignUp";


	    @Override
	    protected boolean validate(HttpServletRequest request) {


	        boolean pass = true;

	         if (DataValidator.isNull(request.getParameter("firstName"))) {
	            request.setAttribute("firstName",
	                    PropertyReader.getValue("error.require", "First Name "));
	            pass = false;
	         }     else if (!DataValidator.isName(request.getParameter("firstName"))) {
	                request.setAttribute("firstName",
	                        PropertyReader.getValue( "Enter the valid FirstName"));
	                pass = false;
	            }
	        
	        if (DataValidator.isNull(request.getParameter("lastName"))) {
	            request.setAttribute("lastName",
	                    PropertyReader.getValue("error.require", "Last Name"));
	            pass = false;
	        }
	        else if (!DataValidator.isName(request.getParameter("lastName"))) {
	            request.setAttribute("lastName",
	                    PropertyReader.getValue( "Enter the valid LastName"));
	            pass = false;
	        }
	        if (DataValidator.isNull(request.getParameter("login"))) {
	            request.setAttribute("login",
	                    PropertyReader.getValue("error.require", "Login Id"));
	            pass = false;
	        } else if (!DataValidator.isEmail(request.getParameter("login"))) {
	            request.setAttribute("login",
	                    PropertyReader.getValue("Enter the valid EmailId"));
	            pass = false;
	        }
	        if (DataValidator.isNull(request.getParameter("password"))) {
	            request.setAttribute("password",
	                    PropertyReader.getValue("error.require", "Password"));
	            pass = false;
	        }else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password", "Enter the valid password");
				pass=false;
			}
	        
	       if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
	            request.setAttribute("confirmPassword", PropertyReader.getValue(
	                    "error.require", "Confirm Password"));
	           pass = false;
	        }
	      
	        if (DataValidator.isNull(request.getParameter("gender"))) {
	            request.setAttribute("gender",
	                    PropertyReader.getValue("error.require", "Gender"));
	            pass = false;
	        }
	        if (DataValidator.isNull( request.getParameter("dob"))) {
	            request.setAttribute("dob",
	                    PropertyReader.getValue("error.require", "Date Of Birth"));
	            pass = false;
	        } else if (!DataValidator.isvalidateAge( request.getParameter("dob"))) {
	            request.setAttribute("dob",
	                    PropertyReader.getValue("Enter the valid DOB"));
	            pass = false;
	        }
	         if (DataValidator.isNull( request.getParameter("mobileNo"))) {
	            request.setAttribute("mobileNo",
	                    PropertyReader.getValue("error.require", "Mobile No."));
	            pass = false;
	        } else if (!DataValidator. isMobileNo(request.getParameter("mobileNo"))) {
	            request.setAttribute("mobileNo",
	                    PropertyReader.getValue("Enter the valid Mobile No."));
	            pass = false;
	        }
	        if (!request.getParameter("password").equals(
	                request.getParameter("confirmPassword"))
	                && !"".equals(request.getParameter("confirmPassword"))) {
	          request.setAttribute("confirmPassword",
	                    "Confirm  Password  should  be matched.");

	            pass = false;
	        }

	        return pass;
	    }

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {


	        UserBean bean = new UserBean();

	        bean.setId(DataUtility.getInt(request.getParameter("id")));

	        bean.setRoleId(RoleBean.STUDENT);

	        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

	        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

	        bean.setLogin(DataUtility.getString(request.getParameter("login")));

	        bean.setPassword(DataUtility.getString(request.getParameter("password")));

	        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
	        
	      bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
	        
	        bean.setGender(DataUtility.getString(request.getParameter("gender")));

	        bean.setDob(DataUtility.getDate(request.getParameter("dob")));

	        populateDTO(bean, request);


	        return bean;
	    }

	    /**
	     * Display concept of user registration
	     */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        ServletUtility.forward(getView(), request, response);

	    }

	    /**
	     * Submit concept of user registration
	     */
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        System.out.println("in get method");
	        String op = DataUtility.getString(request.getParameter("operation"));
	        // get model
	        UserModel model = new UserModel();
	        int id = DataUtility.getInt(request.getParameter("id"));
	        if (OP_SIGN_UP.equalsIgnoreCase(op)) {
	            UserBean bean = (UserBean) populateBean(request);
	            try {
	                int pk = model.add(bean);
	                bean.setId(pk);
	                //request.getSession().setAttribute("UserBean", bean);
	                ServletUtility.setSuccessMessage("Data is successfully saved", request);
	                ServletUtility.forward(getView(), request, response);
	                return;
	            } catch (ApplicationException e) {
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DuplicateRecordException e) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("Login id already exists",
	                        request);
	                ServletUtility.forward(getView(), request, response);
	                
	            }
	            
	        }
	    	else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
				return ;
			}			
	    }

	    @Override
	    protected String getView() {
	        return ORSView.USER_REGISTRATION_VIEW;
	    }
	}