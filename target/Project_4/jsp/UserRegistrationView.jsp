<%@page import="com.raystec.project4.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.raystec.Util.HTMLUtility"%>
<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<html>

<head><title>User Registration</title> 
            </head>

<body>
    <form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

        <%@ include file="Header.jsp"%>
        <script type="text/javascript" src="./js/calendar.js"></script>
        <jsp:useBean id="bean" class="com.raystec.Bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1>User Registration</h1>

            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table>

                <tr>
                    <th  align = "left">First Name <font color="red">*</th>
                    <td><input type="text" name="firstName" size="23"  placeholder="Enter  First_Name"
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th  align = "left">Last Name <font color="red">*</th>
                    <td><input type="text" name="lastName" size="23"  placeholder="Enter  Last_Name"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th  align = "left">LoginId <font color="red">*</th>
                    <td><input type="text" name="login" size="23" 
                        placeholder="Must be Email ID"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>
                <tr>
                    <th  align = "left">Password <font color="red">*</th>
                    <td><input type="password" name="password" size="23"  placeholder="Enter  password_"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
                <tr>
                    <th  align = "left">Confirm Password <font color="red">*</th>
                    <td><input type="password" name="confirmPassword"
                    size="23"  placeholder="Enter  confirm_password"
                     value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility
                    .getErrorMessage("confirmPassword", request)%></font></td>
                </tr>
                   <tr>
                    <th  align = "left">Mobile no. <font color="red">*</th>
                    <td><input type="text" name="mobileNo"
                    size="23"  placeholder="Enter  Mobile_no" maxlength="10"
                     value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility
                    .getErrorMessage("mobileNo", request)%></font></td>
                </tr>
                <tr>
                    <th  align = "left">Gender<font color="red">*</th>
                    <td>
                        <%
                            HashMap map = new HashMap();
                        	map.put(" ","---------Select--------");
                            map.put("Male", "Male");
                            map.put("Female", "Female");
                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                                    map);
                        %> <%=htmlList%>
</td><td style="position: fixed"><font
                        color="red"> <%=ServletUtility
                    .getErrorMessage("gender", request)%></font></td>
                </tr>

                 <tr>
                    <th  align = "left">Date Of Birth  <font color="red">*</th>
                    <td><input type="text" name="dob" readonly="readonly" id="datepicker" size="23"  placeholder="Enter DOB_ (mm/dd/yy)"
                        value="<%=DataUtility.getDateString(bean.getDob())%>"> </td><td style="position: fixed">
                        <a
                        href="javascript:getCalendar(document.forms[0].dob);"> <!-- <img
                            src="./img/cal.jpg" width="18" height="20" border="0"
                            alt="Calender"> -->
                    </a><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                <tr>
                    <th></th>
                    <td colspan="2">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                        &nbsp; <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP %>">
                        <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_RESET %>">
                    </td>
                </tr>
            </table>
    </form>
    <br>

     
          <br>
        <br>
          <br>
        <br>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>