<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="CollegeCtl" method="post">
<%@ include file= "Header.jsp" %>
<jsp:useBean id="bean" class="com.raystec.Bean.CollegeBean" scope="request"></jsp:useBean>

<center>
<h1> <%
if(bean != null && bean.getId()>0){
%>
<tr><th><font>Update College</font></th></tr>
<%}else{ %>
<tr><th><font>Add College</font></th></tr>
<%} %>
</h1>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>
<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
 <input type= "hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
 <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
 <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
 
 <table>
 <tr>	<th align="left">Name <font color="red">*</font></th>
 		<td><input type="text" name="name" placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
 		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name",request) %></font></td>
 </tr>		
 <tr>
 	<th align="left">Address <font color="red">*</font></th>
 	<td><input type="text" name="address" placeholder="Enter Address" value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
 	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address",request) %></font></td>
 </tr>
 <tr>
 	<th align="left">State <font color="red">*</font></th>
 	<td><input type="text" name="state" placeholder="Enter State Name" value="<%=DataUtility.getStringData(bean.getState())%>"></td>
 	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("state",request) %></font></td>
</tr>
<tr>
	<th align="left">City <font color="red">*</font></th>
	<td><input type="text" name="city" placeholder="Enter City Name" value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("city",request) %></font></td>
</tr>
<tr>
	<th align="left">PhoneNo<font color="red">*</font></th>
	<td><input type="text" name="phoneNo" placeholder="Enter Phone No." maxlength="10" value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo",request) %></font></td>
		
</tr>
<tr>	<th></th>
			<%
			if(bean.getId()>0){			
			%><td colspan="2">&nbsp;&nbsp; <input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>">&nbsp;&nbsp;<input type="submit" name="operation"
					value="<%=CollegeCtl.OP_CANCEL%>"></td>
					<%
			}else{
					%>
					<td colspan="2"><input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>">&nbsp;&nbsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>"></td>
			<%} %>
			</tr>

 </table>
 
</center>
</form>

<%@ include file="Footer.jsp" %>
</body>
</html>