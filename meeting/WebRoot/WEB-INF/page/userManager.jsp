<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户管理</title>
	 <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
    
    <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/meeting/userManager.js"></script>

  </head>
  
  <body>
  	<div id="dataList"></div>
  	<div id="addDataDialog">
  		<table align="center" style="font-size: 12px;">
  			<input type="hidden" id="userId"/>
  			<tr>
  				<td>姓名：</td>
  				<td><input style="width: 150px;" type="text" id="name" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>用户名：</td>
  				<td><input style="width: 150px;" type="text" id="userCode" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>性别：</td>
  				<td><input style="width: 150px;" type="text" id="gender" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>所属部门：</td>
  				<td><input  style="width: 150px;" type="text" id="departmentId" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>电话号码：</td>
  				<td><input style="width: 150px;" type="text" id="phoneNumber" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>用户角色：</td>
  				<td><input type="text" style="width: 150px;"  id="roleId" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>QQ号码：</td>
  				<td><input type="text" style="width: 150px;"  id="qq" /></td>
  			</tr>
  			<tr>
  				<td>传真号码：</td>
  				<td><input type="text" style="width: 150px;"  id="fax" /></td>
  			</tr>
  			<tr>
  				<td>联系地址：</td>
  				<td><input type="text" style="width: 150px;"  id="address" /></td>
  			</tr>
  			<tr>
  				<td>备注：</td>
  				<td><textarea style="width: 150px; height: 100px;" id="note" ></textarea></td>
  			</tr>
  		</table>
  		<div style="text-align: center; margin-top: 30px;"><span style="color:red">提示：带有红色*号的为必填项</span></td></div>
  	</div>
  </body>
</html>
