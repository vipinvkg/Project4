package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.Util.ServletUtility;

import javassist.SerialVersionUID;

@WebServlet ("/ErrorCtl")
public class ErrorCtl extends BaseCtl{
	
	private static final long SerialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("________error ctl-_-------->");
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
		
	}
	
	@Override
	protected String getView() {
		return ORSView.ERROR_VIEW;
	}
}
