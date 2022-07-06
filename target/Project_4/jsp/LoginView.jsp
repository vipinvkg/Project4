<%@page import="com.raystec.project4.controller.LoginCtl"%>
<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<html>
<head>
<title>Login page</title>
</head>
<!-- <style>
body {
  background-image: url('img/architech.jpeg');
 background-repeat: no-repeat; background-attachment: fixed;background-size: cover; "
  
}
</style> -->
<body>
<form action="<%=ORSView.LOGIN_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="com.raystec.Bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1>Login</h1>
              <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>

            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <% 
String msg = (String) request.getAttribute("message");
            if(msg!=null){
            
%>
<h1 align = "center"> <font style ="color:red"><%=msg %></font></h1>
<%} %>
              <input type="hidden" name="id" value="<%=bean.getId()%>">
              <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
                <input type="hidden" name="URI" value= "<%=session.getAttribute("uri") %>">
            <table>
                <tr>
                    <th align = "left">LoginId<font color="red" >*</font></th>
                    <td><input type="text" name="login" placeholder=" Enter_email_"size=30 
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
                        <td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				
                </tr>
                <tr>
                    <th align = "left">Password<font color="red" >*</th>
                    <td><input type="password" name="password" 
                     placeholder=" Enter_Password_"size=30
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
                        <td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				
                </tr>
                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=LoginCtl.OP_SIGN_IN %>"> &nbsp; <input type="submit"
                        name="operation" value="<%=LoginCtl.OP_SIGN_UP %>" > &nbsp;</td>
                </tr>
                <tr><th></th>
                <td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password</b></a>&nbsp;</td>
            </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>