<%@page import="com.raystec.Util.ServletUtility"%>
<%@page import="com.raystec.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
	<%@ include file="Header.jsp"  %>
	<h1 align="center">
	<font size="10px" color="red"> Welcome to ORS</font>
	</h1>
	<h1><%=ServletUtility.getSuccessMessage(request) %></h1>
	<%
	UserBean beanUserBean = (UserBean) session.getAttribute("user");
	if(beanUserBean != null){
		if(beanUserBean.getRoleId()==2) {
			
	%>
	
	<h2 align="center">
	<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your Marksheet </a>
	</h2>		
	<%
		}
	}
	%>
	
	
	</form>
	
	<%@ include file="Footer.jsp" %>

</body>
</html>