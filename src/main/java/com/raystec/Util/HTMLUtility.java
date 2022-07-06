package com.raystec.Util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import com.raystec.Bean.DropdownListBean;



/**
 * HTML Utility class to produce HTML contents like Dropdown List.
 * @author vipin gupta
 *
 */
public class HTMLUtility {

	/**
	 * 
	 *  Create HTML SELECT list from MAP paramters values
	 * @param name getlist
	 * @param selectedVal select a value
	 * @param map value into map
	 * @return returns value
	 */
	
	public static String getList(String name, String selectedVal, HashMap<String, String> map) {

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		Set<String> keys = map.keySet();
		String val = null;

		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * Create HTML SELECT List from List parameter
	 *
	 * @param name create html list
	 * @param selectedVal select a value
	 * @param list create a list
	 * @return return a value
	 */
	public static String getList(String name, String selectedVal, List list) {

		Collections.sort(list);

		List<DropdownListBean> dd = (List<DropdownListBean>) list;

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		String key = null;
		String val = null;
		
		boolean select=true;
		if (select) {

			sb.append("<option selected value=''> ---------Select-------- </option>");
		}

		for (DropdownListBean obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	public static String getList(String name, String selectedVal, HashMap<String, String> map, boolean select) {

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		Set<String> keys = map.keySet();
		String val = null;

		if (select) {

			sb.append("<option selected value=''> --Select-- </option>");
		}

		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	public static String getInputErrorMessages(HttpServletRequest request) {

		Enumeration<String> e = request.getAttributeNames();

		StringBuffer sb = new StringBuffer("<UL>");
		String name = null;

		while (e.hasMoreElements()) {
			name = e.nextElement();
			if (name.startsWith("error.")) {
				sb.append("<LI class='error'>" + request.getAttribute(name) + "</LI>");
			}
		}
		sb.append("</UL>");
		return sb.toString();
	}

	/**
	 * Returns Error Message with HTML tag and CSS
	 *
	 * @param request returns error
	 * @return returns value
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String msg = ServletUtility.getErrorMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-error-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Returns Success Message with HTML tag and CSS
	 *
	 * @param request success message
	 * @return return value
	 */

	public static String getSuccessMessage(HttpServletRequest request) {
		String msg = ServletUtility.getSuccessMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-success-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Creates submit button if user has access permission.
	 *
	 * @param label create submit
	 * @param access provide access
	 * @param request get request
	 * @return return value
	 */
	public static String getSubmitButton(String label, boolean access, HttpServletRequest request) {

		String button = "";

		if (access) {
			button = "<input type='submit' name='operation'    value='" + label + "' >";
		}
		return button;
	}

	/*
	 * public static String getCommonFields(HttpServletRequest request) {
	 * 
	 * BaseModel model = ServletUtility.getModel(request);
	 * 
	 * StringBuffer sb = new StringBuffer();
	 * 
	 * sb.append("<input type='hidden' name='id' value=" + model.getId() + ">");
	 * 
	 * sb.append("<input type='hidden' name='createdBy' value=" +
	 * DataUtility.getString(model.getCreatedBy()) + ">");
	 * sb.append("<input type='hidden' name='modifiedBy' value=" +
	 * DataUtility.getString(model.getModifiedBy()) + ">");
	 * sb.append("<input type='hidden' name='createdDatetime' value=" +
	 * DataUtility.getTimestamp(model.getCreatedDatetime()) + ">");
	 * sb.append("<input type='hidden' name='modifiedDatetime' value=" +
	 * DataUtility.getTimestamp(model.getModifiedDatetime()) + ">");
	 * 
	 * return sb.toString(); }
	 */}
