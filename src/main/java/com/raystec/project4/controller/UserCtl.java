package com.raystec.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.UserBean;
import com.raystec.Model.RoleModel;
import com.raystec.Model.UserModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;

@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	private static final int serialVersionUID = 1;

	// private static Logger log = Logger.getLogger(UserCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel model = new RoleModel();
		try {
			List l = model.list();

			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			e.printStackTrace();
			// log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		// log.debug("UserCtl Method validate Started");
		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");
		String mobileNo = request.getParameter("mobileNo");

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("Enter the valid firstName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("Enter the valid Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("Enter the valid Email"));
			pass = false;
		}
		if (!OP_UPDATE.equalsIgnoreCase(request.getParameter("operation"))) {

			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("Enter the valid Password"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
				pass = false;
			} 
				  else if (!DataValidator.isPassword(request.getParameter("confirmPassword")))
				  { request.setAttribute("confirmPassword",
				  PropertyReader.getValue("Enter the valid confirmPassword")); pass = false; }
				 

			else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", "Confirm Password should  be matched.");
				pass = false;
			}
		}
		if (DataValidator.isNull(mobileNo)) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No."));
			pass = false;
		} else if (!DataValidator.isMobileNo(mobileNo)) {
			request.setAttribute("mobileNo", PropertyReader.getValue("Enter the valid Mobile No."));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "RoleId"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("Enter the valid DOB."));
			pass = false;
		}
		System.out.println("validotr check");
		// log.debug("UserCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		// log.debug("UserCtl Method populatebean Started");

		System.out.println("popoluated beanes");
		UserBean bean = new UserBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));

		bean.setRoleId(DataUtility.getInt(request.getParameter("roleId")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		// bean.setConfirmPassword(DataUtility.getString(request
		// .getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		System.out.println("date of birth" + request.getParameter("dob"));

		populateDTO(bean, request);

		// log.debug("UserCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains DIsplay logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// log.debug("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model

		UserModel model = new UserModel();
		int id = DataUtility.getInt(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			UserBean bean;
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		// log.debug("UserCtl Method doGet Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// log.debug("StudentCtl Method doPost Started");
		System.out.println("inside do post");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		UserModel model = new UserModel();

		int id = DataUtility.getInt(request.getParameter("id"));
//	System.out.println("inside save  op");
		if (OP_SAVE.equalsIgnoreCase(op) || (OP_UPDATE.equalsIgnoreCase(op))) {
			System.out.println("inside save  op");

			UserBean bean = (UserBean) populateBean(request);

			try {
				if (id > 0) {

					System.out.println("udpate" + id);
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {

					int pk = model.add(bean);
					bean.setId(pk);

					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully added", request);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			// } catch (DuplicateException e) {
//            ServletUtility.setBean(bean, request);
//            ServletUtility.setErrorMessage(
//                    "Student Email Id already exists", request);
//        }

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		// log.debug("StudentCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}