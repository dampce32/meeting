<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>发布会议</title>
   <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
    
    <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    
    <script type="text/javascript" src="js/meeting/meeting.js"></script>
  </head>
  <body>
  <div id="layout" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'">
			会议名称：<input type="text" id="meetingNameQuery"/> &nbsp;&nbsp;&nbsp;<input type="button" id="queryBtn" value="查询">&nbsp;&nbsp;&nbsp;<input type="button" id="resetBtn" value="重置">
		</div>
		<div data-options="region:'center'">
			<div id="dataList"></div>
		</div>
	</div>
  	<div id="addDataDialog">
  		<table align="center" style="font-size: 12px;">
			<input type="hidden" id="meetingId" />
  			<tr>
  				<td>会议名称：</td>
  				<td><input type="text" id="name" style="width: 250px;" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>报名开始时间：</td>
  				<td><input type="text" id="registerBegin" style="width: 150px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>报名结束时间：</td>
  				<td>
  					<input type="text" id="registerEnd" style="width: 150px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
  					<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>会议开始时间：</td>
  				<td>
  					<input type="text" id="beginDate" style="width: 150px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
  					<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>会议结束时间：</td>
  				<td>
  					<input type="text" id="endDate" style="width: 150px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
  				 	<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>会务费（元）：</td>
  				<td><input id="money" class="easyui-numberbox" data-options="min:0,max:99999,precision:2"/>
					<span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>支付方式：</td>
  				<td><input style="width: 250px;" type="text" id="pay" /></td>
  			</tr>
  			<tr>
  				<td>开会地址：</td>
  				<td><input style="width: 250px;" type="text" id="address" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>主办单位：</td>
  				<td><input style="width: 250px;" type="text" id="host" /> <span style="color:red">*</span></td>
  			</tr>
  			<tr>
  				<td>承办单位：</td>
  				<td><input style="width: 250px;" type="text" id="organizer" /></td>
  			</tr>
  			<tr>
  				<td>协办单位：</td>
  				<td><input type="text" style="width: 250px;" id="co_organizer" /></td>
  			</tr>
  		</table>
  		<div style="text-align: center; margin-top: 30px;"><span style="color:red">提示：带有红色*号的为必填项</span></td></div>
  	</div>
  </body>
</html>
