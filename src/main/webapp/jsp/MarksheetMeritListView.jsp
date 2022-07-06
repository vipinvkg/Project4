<%@page import="com.raystec.Util.DataUtility"%>
<%@page import="com.raystec.project4.controller.MarksheetMeritListCtl"%>
<%@page import="com.raystec.Util.ServletUtility"%>
<%@page import="com.raystec.Bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>
 <title>Marksheet Merit List</title>   
    <%@include file="Header.jsp"%>
    <center>
       
        <h1>Marksheet Merit List</h1>
        <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>

        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
            <br>
            <table border="1" width="100%" align="center" cellpadding=2px
				cellspacing=".2">
                <tr>

                    <th>ID</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
                    <th>Total</th>
					<th>Percentage</th>

                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;
                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetBean> it = list.iterator();
                    while (it.hasNext()) {
                        MarksheetBean bean = it.next();
                        int phy = DataUtility.getInt(DataUtility.getStringData(bean.getPhysics()));
						int chem = DataUtility.getInt(DataUtility.getStringData(bean.getChemistry()));
						int math = DataUtility.getInt(DataUtility.getStringData(bean.getMaths()));
						int total = (phy + chem + math);
						float perc = total / 3;
                %>
                <tr align="center">

                    <td><%=index++%></td>
                    <td><%=bean.getRollNo()%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getPhysics()%></td>
                    <td><%=bean.getChemistry()%></td>
                    <td><%=bean.getMaths()%></td>
                    <td><b><%=total%></b></td>
					<td><b><%=((perc) + "%")%></b></td>

                </tr>

                <%
                    }
                %>
            </table>
        
            <table>
                <tr>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
        <br>
<br>
<br>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>