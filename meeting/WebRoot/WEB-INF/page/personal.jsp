<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>personal</title>
 <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">

<script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="js/meeting/personal.js"></script>

<script type="text/javascript">
	var userId = null;
	<c:if test="${login_user.userId == ''}">
		 userId = ${login_user.userId};
	</c:if>
</script>
</head>

<body>
	<div id="updateDataDialog"></div>
	<table align="center" style="font-size: 12px">
		<tr>
			<td>姓名：</td>
			<td><input type="text" id="name" value="${login_user.name }" /> <span style="color:red">*</span>
			</td>
		</tr>
		<tr>
			<td>性别：</td>
			<td><input type="text" id="gender" value="${login_user.gender }" /> <span style="color:red">*</span>
			</td>
		</tr>
		<tr>
			<td>所属部门：</td>
			<td><input type="text" id="departmentId" value="${login_user.departmentId }"/> <span
				style="color:red">*</span>
			</td>
		</tr>
		<tr>
			<td>电话号码：</td>
			<td><input type="text" id="phoneNumber" value="${login_user.phoneNumber }"/> <span
				style="color:red">*</span>
			</td>
		</tr>
		<tr>
			<td>QQ号码：</td>
			<td><input type="text" id="qq" value="${login_user.qq }" />
			</td>
		</tr>
		<tr>
			<td>传真号码：</td>
			<td><input type="text" id="fax" value="${login_user.fax }"/>
			</td>
		</tr>
		<tr>
			<td>联系地址：</td>
			<td><input type="text" id="address" value="${login_user.address }"/>
			</td>
		</tr>
		<tr>
			<td>备注：</td>
			<td><input type="text" id="note"  value="${login_user.note }"/>
			</td>
		</tr>
	</table>
	<div style="text-align: center;margin-top: 30px;">
		<input id="subBtn" type="button" value="保存" style="width: 60px; height: 30px;">
	</div>
</body>
</html>
