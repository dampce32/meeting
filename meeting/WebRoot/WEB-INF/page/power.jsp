<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>POWER</title>
  <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
    
    <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/meeting/power.js"></script>
    <script type="text/javascript">
    	var pid = '${pid}';
    </script>
  </head>
  <body>
   	<div id="dataList"></div>
   	 	<div id="addDataDialog">
  		<table align="center" style="font-size: 12px;">
  			<input type="hidden" id="powerId"/>
  			<tr>
  				<td>权限名称 ：</td>
  				<td><input type="text" id="powerName" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>url：</td>
  				<td><input type="text" id="url" /> <span style="color:red">*</span></td>
  			</tr>
  		</table>
  	</div>
  </body>
</html>
