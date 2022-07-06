package com.raystec.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Bean.BaseBean;
import com.raystec.Bean.CourseBean;
import com.raystec.Bean.SubjectBean;
import com.raystec.Bean.TimetableBean;
import com.raystec.Model.CourseModel;
import com.raystec.Model.SubjectModel;
import com.raystec.Model.TimeTableModel;
import com.raystec.Util.DataUtility;
import com.raystec.Util.DataValidator;
import com.raystec.Util.PropertyReader;
import com.raystec.Util.ServletUtility;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

@WebServlet("/ctl/TimetableCtl")
public class TimeTableCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		CourseModel crsm = new CourseModel();
		SubjectModel stm = new SubjectModel();
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		
		try {
			clist = crsm.list();
			slist = stm.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
						
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require","Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require","Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require","Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require","Exam Date"));
			pass = false;
		}else if (!DataValidator.isDate(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("Exam Date is not valid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExTime"))) {
			request.setAttribute("ExTime", PropertyReader.getValue("error.require","Exam Time"));
			pass = false;
		}
		return pass;
	}
	
	@Override
	protected TimetableBean populateBean(HttpServletRequest request) {
		TimetableBean tb = new TimetableBean();
		tb.setId(DataUtility.getLong(request.getParameter("id")));
		tb.setSubject_Id(DataUtility.getInt(request.getParameter("subjectId")));
		tb.setCourse_id(DataUtility.getInt(request.getParameter("courseId")));
		tb.setSemester(DataUtility.getString(request.getParameter("semester")));
		tb.setExam_Date(DataUtility.getDate(request.getParameter("ExDate")));
		tb.setExam_Time(DataUtility.getString(request.getParameter("ExTime")));
		
		populateDTO(tb, request);
		
		return tb;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		int id = (int) DataUtility.getLong(request.getParameter("id"));
		
		TimeTableModel tm = new TimeTableModel();
		TimetableBean tb = null;
		if (id>0) {
			try {
					tb = tm.findByPk(id);
					ServletUtility.setBean(tb, request);
					
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		TimeTableModel tm = new TimeTableModel();
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimetableBean tb = (TimetableBean)populateBean(request);
			try {
				if (id>0) {
					tm.update(tb);
					ServletUtility.setBean(tb, request);
					ServletUtility.setSuccessMessage("TimeTable is Successfully Update", request);
				}else {
					tm.add(tb);
					
					ServletUtility.setBean(tb, request);
					ServletUtility.setSuccessMessage("TimeTable Successfully Added", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			
			}catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setBean(tb, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			}
			
		}else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	
	}
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
