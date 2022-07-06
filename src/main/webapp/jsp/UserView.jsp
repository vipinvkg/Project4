<%@page import="com.raystec.project4.controller.UserCtl"%>
<%@page import="com.raystec.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="com.raystec.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add user</title>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js">
	</script>
</head>
<body>
<form action="<%=ORSView.USER_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		<script type="text/javascript" src="../js/calendar.js"></script>
		<jsp:useBean id="bean" class="com.raystec.Bean.UserBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("roleList");
		%>

		<center>
		
         <h1>
        	<%
        		if( bean != null && bean.getId()>0){
        	%> 
        	<tr><th><font>Update User</font></th></tr>
        	<% }else{%>
        	<tr><th><font>Add User</font></th></tr>
        	<% }%>
        </h1>
		

			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>



			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			 <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<th align = "left">First Name <font color="red" >* </th>					
					<td><input type="text" name="firstName" size="23" placeholder="Enter  First_Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td><td><td style="position: fixed">
						<font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align = "left">Last Name<font color="red">*</th>
					<td><input type="text" name="lastName" size="23"  placeholder="Enter  Last_Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><td><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align = "left">LoginId<font color="red">*</th>
					<td><input type="text" name="login" size="23"  placeholder="Enter  Login_id"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>><td><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
				<% if(bean.getId() > 0 && bean != null){ %>
				<tr>
                   
                    <td><input type="hidden" name="password" value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
                   <td><input type="hidden" name="confirmPassword"  value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
                   </tr>
				<% }else{ %>
				<tr>
					<th align="left">Password <font color="red">*</font></th>
					<td><input type="password" name="password" size="23" placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				                 <tr> 
				                     <th>Confirm Password <font color="red">*</font></th> 
				                     <td><input type="password" name="confirmPassword" size="23" placeholder="Confirm Your Password"
				                        value="<%=DataUtility.getStringData(bean.getPassword())%>"></td><td style="position: fixed"><font 
				                        color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", 
				                     request)%></font></td> 
				                 </tr> 
				<%} %>
 <tr>
                    <th  align = "left">Mobile no. <font color="red">*</th>
                    <td><input type="text" name="mobileNo" maxlength="10"
                    size="23"  placeholder="Enter  Mobile_no"
                     value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility
                    .getErrorMessage("mobileNo", request)%></font></td>
                </tr>
				             
				<tr>
					<th align = "left">Gender<font color="red">*</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("","---------Select--------");
							map.put("Male", "Male");
							map.put("Female", "Female");
							String htmlList = HTMLUtility.getList("gender", String.valueOf( bean.getGender()), map);
						%><%=htmlList%>
						 &nbsp;<font style="position: fixed;" color="red">
							<%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Role<font color="red">*</th>
					<td><%=HTMLUtility.getList("roleId",   String.valueOf(bean.getRoleId()), l)%>

						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font>
					</td>
				</tr>
				
				
				
				
		<tr>		
			<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text"  name="dob"  id="datepicker"
						placeholder="Enter Date Of Birth"   readonly="readonly" size="23"
						value="<%=DataUtility.getDateString(bean.getDob())%>">
						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
</tr>	
				
					<tr>
					<th></th>
					<%
 	if (bean.getId() > 0) {
 %><td colspan="2"> &nbsp; &nbsp; <input type="submit"  name="operation"
						value="<%=UserCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input type="submit"  name="operation"
						value="<%=UserCtl.OP_CANCEL%>"></td>
						<%
						} else {
							%>
						
						<td  colspan="2">
						<input type="submit"  name = "operation"  value ="<%=UserCtl.OP_SAVE %>">
						 &nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=UserCtl.OP_RESET%>"></td>
							<%
						} 
							%>
				</tr>

			</table>
	</form>
	<br>
  <br>
	</center>
	
	<%@ include file="Footer.jsp"%>
</body>
</html>