<%@page import="com.raystec.Util.ServletUtility"%>
<%@page import="com.raystec.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forget Pasword</title>
<style type="text/css">
.p1 {
	font-size: 18px;
}

.p2 {
	padding: 5px;
	margin: 3px;
}
</style>

</head>
<body>
	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="com.raystec.Bean.UserBean"
			scope="request"></jsp:useBean>
		<center>
			<h1 style="font-size: 40px;">Forget Your Password</h1>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <font
				color="blue">Submit Your Email Address and we will Send you
				Password</font><br>
			<br>

			<div align="center">
				<font color="green" size="5px"> <%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
			</div>

			<table>
				<th class="p1">Email Id <span style="color: Red;">*</span></th>&emsp;
				<td><input type="text" size="40" name="login" class="p2"
					placeholder="Enter ID Here"
					value="<%=ServletUtility.getParameter("login", request)%>">&emsp;
					<input type="submit" name="operation"
					value="<%=ForgetPasswordCtl.OP_GO%>" style="padding: 3px;"><br></td>
				<td style="position: fixed; padding-top: 10px;"><font
					color="red"><%=ServletUtility.getErrorMessage("login", request)%>
				</font></td>
	
			</table>
		</center>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>