<%@page import="com.raystec.Model.UserModel"%>
<%@page import="com.raystec.Util.HTMLUtility"%>
<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Model.RoleModel"%>
<%@page import="com.raystec.project4.controller.UserListCtl"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
 <script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>
<head>
<meta charset="ISO-8859-1">
<title>UserList</title>
</head>
<body>
<jsp:useBean id="bean" class="com.raystec.Bean.UserBean" scope="request" ></jsp:useBean>

	<%@include file="Header.jsp"%>
<form action="<%=ORSView.USER_LIST_CTL%>" method="post">

    <center>
    
	<div align="center" >
    			<h1>User List</h1>
                <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
                    
	</div>

	  
	   <%List ulist= (List) request.getAttribute("userList"); %>
	              
	    
	     
	     <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;
                    List list = ServletUtility.getList(request);
                    Iterator<UserBean> it = list.iterator();
                    
                    if(list.size() !=0){
                    	
        %>
            <table width="80%"  align="center">
                <tr><th></th>
                    <td align="center">
                    <label><b>First Name</b></font> :</label> 
                     <input type="text" name="Name" placeholder="Enter First Name" value="<%=ServletUtility.getParameter("loginid", request)%>">
                     &nbsp; 
                    <label><b>Role</b></font> :</label> 
                     <%=HTMLUtility.getList("Roleid", String.valueOf(bean.getRoleId()), ulist) %>
                     
                   &nbsp; 
                    <label><b>LoginId</b></font> :</label> 
                     <input type="text" name="login" placeholder="Enter Login-Id" value="<%=ServletUtility.getParameter("loginid", request)%>">
                    
<%--                      <%=ServletUtility.getParameter("loginid", request) %> --%>
                    
                     &nbsp; 
                    <input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH %>">
                    &nbsp; 
                    <input type="submit" name="operation" value="<%=UserListCtl.OP_RESET %>">
         	
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" width="100%" align="center" cellpadding=6px cellspacing=".2">
                 <tr style="background: white">
                	<th> <input type="checkbox" id="select_all" name="select">Select All </th>
                    
                    <th>S.No.</th>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>Role</th>
                    <th>LoginId</th>
                    <th>Gender</th>
                    <th>Date Of Birth</th>
                    <th>Mobile No </th>
                    <th>Edit</th>
                </tr>
				
				<%
					while (it.hasNext())
					{
						bean = it.next();
						RoleModel model = new RoleModel();
						RoleBean rolebean = new RoleBean();
								rolebean = model.findByPK(bean.getRoleId());
				
				%>


                <tr align="center">
                    <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"
                    <%if(userBean.getId() == bean.getId() || bean.getRoleId() == RoleBean.ADMIN) {                    
                    %>
                    <%="disabled" %><% } %>  >

                  
                    </td>
                    <td><%=index++%></td>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=rolebean.getName()%></td>
                    <td><%=bean.getLogin()%></td>
                    <td><%=bean.getGender()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getMobileNo()%></td>
                    <td><a href="UserCtl?id=<%=bean.getId()%>"
                    
                    <%if(userBean.getId() == bean.getId() || bean.getRoleId() == RoleBean.ADMIN) {%>
 						onclick = "return false;"                   
                    <% } %>>Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
				
            <table width="100%">
                <tr><th></th>
					<%if(pageNo == 1){ %> 
                   <td ><input type="submit" name="operation" disabled="disabled" value="<%=UserListCtl.OP_PREVIOUS%>"></td>
                    <%}else{ %>
                    <td ><input type="submit" name="operation"  value="<%=UserListCtl.OP_PREVIOUS%>"></td>
                    <%} %>
                     
                     <td ><input type="submit" name="operation" value="<%=UserListCtl.OP_DELETE%>"></td>
                     <td ><input type="submit" name="operation" value="<%=UserListCtl.OP_NEW%>"></td>
                     
                     <%	UserModel model = new UserModel();
                     %>
                     
                     <% if(list.size() < pageSize || model.nextPK()-1 == bean.getId() ){%>
                     		<td align="right"><input type="submit" name="operation" disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
                     <% }else{%>
                     		<td align="right"><input type="submit" name="operation" value="<%=UserListCtl.OP_NEXT%>"></td>
                     <%} %>
       
        <%-- <td align="right"><input type="submit"  name="operation" value="<%=UserListCtl.OP_NEXT%>" <%=(list.size()<pageSize)?"disabled":"" %>> </td>
		 --%>
        
        
                </tr>          
            </table>
            		<%}if(list.size() == 0){ %>
            		<td align="center"><input type="submit" name="operation" value="<%=UserListCtl.OP_BACK%>"></td>	
            		<% } %>
            		
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
               </br>
               </br>
    </center>
	<%@include file="Footer.jsp"%>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
</body>
</html>