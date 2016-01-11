<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>部门管理</title>
  <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
    <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/meeting/department.js"></script>
  </head>
  <body>
  	<div id="dataList"></div>
  	<div id="addDataDialog">
  		<table align="center" style="font-size: 12px;">
  			<input type="hidden" id="id"/>
  			<tr>
  				<td>编号：</td>
  				<td><input type="text" style="width: 150px;"  id="departmentCode" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>部门名称：</td>
  				<td><input type="text" style="width: 150px;"  id="departmentName" /> <span style="color:red">*</span></td>
  			</tr>
  		</table>
  		<div style="text-align: center; margin-top: 30px;"><span style="color:red">提示：带有红色*号的为必填项</span></td></div>
  	</div>
  </body>
</html>
