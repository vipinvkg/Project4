<%@page import="com.raystec.project4.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="com.raystec.Util.HTMLUtility"%>
<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<html>
<body>
 <title>Marksheet </title>   
	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.raystec.Bean.MarksheetBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("Studentlist");
		%>

		<center>
		   
        <h1>
        	<%
        		if( bean != null && bean.getId()>0){
        	%> 
        	<tr><th><font>Update Marksheet</font></th></tr>
        	<% }else{%>
        	<tr><th><font>Add Marksheet</font></th></tr>
        	<% }%>
        </h1>
		
		
			 <title>Add Marksheet</title>  
		  
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>


			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<th  align = "left"">Rollno<font color="red" >* </th>
					<td><input type="text" name="rollNo" size="23" placeholder="Enter The Roll_no"
						value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td><td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>

				<tr>
					<th  align = "left"">Name<font color="red" >* </th>
					<td><%=HTMLUtility.getList("StudentId", String.valueOf(bean.getStudentId()), l)%></td><td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("StudentId", request)%></font></td>
				</tr>
				<tr>
					<th  align = "left"">Physics<font color="red" >* </th>
					<td><input type="text" name="physics" size="23" placeholder="Enter The Physics_no"
						value="<%=DataUtility.getStringData(bean.getPhysics()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPhysics())%>"><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
				</tr>
				<tr>
					<th  align = "left"">Chemistry<font color="red" >* </th>
					<td><input type="text" name="chemistry" size="23"placeholder="Enter The chemistry_no"
						value="<%=DataUtility.getStringData(bean.getChemistry()).equals("0") ? ""
					: DataUtility.getStringData(bean.getChemistry())%>"></td><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
				</tr>
				<tr>
					<th  align = "left"">Maths<font color="red" >* </th>
					<td><input type="text" name="maths" size="23" placeholder="Enter The Math_no"
					value="<%=DataUtility.getStringData(bean.getMaths()).equals("0") ? ""
					: DataUtility.getStringData(bean.getMaths())%>"></td><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>

				</tr>
				<tr>
					<th></th>
					<%
 	if (bean.getId() > 0) {
 %><td colspan="2"> &nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_CANCEL%>"></td>
						<%
						} else {
							%>
						
						<td  colspan="2">
						<input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_SAVE%>"> &nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_RESET%>"></td>
							<%
						} 
							%>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
</html>