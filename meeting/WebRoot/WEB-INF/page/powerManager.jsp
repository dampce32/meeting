<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
 <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
    
    <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/meeting/powerManager.js"></script>
	
</head>
  <body>
  <div class="easyui-layout plugins" data-options="fit:true">
	<div data-options="region:'west',border:false,title:'权限管理'" style="width:180px;">
            <ul id="powerTree"></ul>
	</div>
	<div data-options="region:'north',border:false">
		<div style="margin-left: 10px;">请选择角色：<input id="roleSelect" name="roleId"> </div>
		
		
	</div>
	<div data-options="region:'center'">
        <div id="powerTabs" class="easyui-tabs"  fit="true" border="false" >
        </div>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
	</div>
</div>
  </body>
</html>
