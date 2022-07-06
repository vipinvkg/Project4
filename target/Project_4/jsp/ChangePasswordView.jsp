<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<%@page import="com.raystec.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
<style >
.p1{
	font-size: 18px;
}

.p2{padding: 5px;
	margin: 3px; }
</style>
</head>
<body>

<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="com.raystec.Bean.UserBean" scope="request"></jsp:useBean>

<center>
<h1 style="font-size: 30px;">Change Password</h1>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>
<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name ="createdby" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedby" value=<%=bean.getModifiedBy() %>>
<input type="hidden" name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
<input type="hidden" name="modifieddatetime" value="<%=bean.getModifiedDatetime()%>">

<table>
	<tr>
		<th align="left" class="p1">Old Password <span style="color: red">*</span></th>
		<td><input type="password" name="oldpassword" class="p2" size="30"placeholder="Enter Old Password"
		 value=<%=DataUtility.getString(request.getParameter("oldpassword")==null ? ""
				 : DataUtility.getString(request.getParameter("oldpassword")))%>></td>
			<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("oldpassword",request) %></font></td>	 
	</tr>
	<tr>
		<th align="left" class="p1">New Password<span style="color: red">*</span></th>
		<td><input type="password" name="newpassword" class="p2" size="30" placeholder="Enter new Password"
		 value=<%=DataUtility.getString(request.getParameter("newpassword")==null ? ""
				 :DataUtility.getString(request.getParameter("newpassword")))%>></td>
				 <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("newpassword",request) %></font></td>
	</tr>
	<tr>
		<th align="left" class="p1">Confirm Password<span style="position: fixed;color:red">*</span></th>
		<td><input type="password" name="confirmpassword" class="p2" size="30" placeholder="Enter confirm Password" value=<%=DataUtility.getString(request.getParameter("confirmpasword")==null ?""
				: DataUtility.getString(request.getParameter("confirmpassword"))) %>></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmpassword",request) %></font></td>
		</tr>
		
	<tr>
		<th></th>
		<td colspan="2" align="center"><input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_SAVE%>" style="padding: 5px;">&nbsp;<input type="submit" name="operation"
		 value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>" style="padding: 5px;">&nbsp;
		 &nbsp;<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_RESET%>" style="padding: 5px;"></td>
	</tr>	 						 
</table>
</center>

</form>
<%@include file="Footer.jsp" %>
</body>
</html>