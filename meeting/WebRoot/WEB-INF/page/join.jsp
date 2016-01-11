<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>JOIN</title>
  <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
    
    <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    
    <script type="text/javascript" src="js/meeting/join.js"></script>
  </head>
  <body>
  	<div id="dataList"></div>
  </body>
</html>
