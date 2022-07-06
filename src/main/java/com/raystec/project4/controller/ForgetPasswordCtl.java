package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.UserBean;
import com.raystec.Model.UserModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.RecordNotFoundException;
@WebServlet(name = "/ForgetPasswordCtl", urlPatterns = {"/ForgetPasswordCtl"})
public class ForgetPasswordCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email id"));
			pass = false;
		}else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;
		}
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = (UserBean)populateBean(request);
		UserModel model = new UserModel();
		
		if (OP_GO.equalsIgnoreCase(op)) {
			try {
//				boolean flag = 
						model.forgetPassword(bean.getLogin());
//				if (flag==true) {
					ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
//					ServletUtility.forward(getView(), request, response);
//				}else{
//					ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
//					return;
//				}
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				
			}catch (ApplicationException e){
				ServletUtility.handleException(e, request, response);
				return;
			}
				
		}
		ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}
	
}
