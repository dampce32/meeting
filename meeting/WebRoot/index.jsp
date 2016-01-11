<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎您登录会议报名系统</title>
 <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
 <link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
 <link rel="stylesheet" type="text/css" href="css/index.css">
 
 <script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
 <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
 <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
 <script type="text/javascript" src="js/meeting/index.js"></script>
</head>
<body class="easyui-layout">
	<div region="north" border="true" class="cs-north">
		<div class="cs-north-bg">
		<div class="cs-north-logo">会议报名系统</div>
		<span class="cs-north-name">您好：${login_user.name }
		&nbsp;&nbsp;&nbsp;角色：
		<c:if test="${role.roleName == 'admin'}">
			管理员
		</c:if>
		<c:if test="${role.roleName == 'normal'}">
			普通用户
		</c:if>
		<input type="button" value="退出" id="logout">
		</span>
	</div>
	</div>
	
	<div region="west" border="true" split="true" class="cs-west">
		<div id="accordion" class="easyui-accordion" fit="true" border="false">
			<!-- <div title="会议管理" selected="true">
				<a href="javascript:void(0);" src="Meeting.do" class="cs-navi-tab">发布会议</a></p>
				<a href="javascript:void(0);" src="Meeting.do?action=IWANTTOJOIN" class="cs-navi-tab">我要参会</a></p>
				<a href="javascript:void(0);" src="User.do?action=personal_page" class="cs-navi-tab">个人信息</a></p>
				<a href="javascript:void(0);" src="Meeting.do?action=already" class="cs-navi-tab">已报名的会议</a></p>
			</div>
			<div title="系统管理">
				<a href="javascript:void(0);" src="User.do" class="cs-navi-tab">用户管理</a></p>
				<a href="javascript:void(0);" src="Power.do?action=tree" class="cs-navi-tab">权限管理</a></p>
			</div> -->
		</div>
	</div>
	<script type="text/javascript">
		var getParentId = function(arr,value){
			var temp = null;
			var s = null;
			$.each(arr,function(i,n){
				if(s==null){
					s = Math.abs(arr[i] - value);
					temp = arr[i];
				}else{
					if(Math.abs(arr[i]-value)<s){
						s = arr[i]-value;
						temp = arr[i];
					}
				}
			});
			return temp;
		};
	</script>
	<script type="text/javascript">
	<c:forEach var="i" items="${login_power}">
		<c:if test="${i.deepth  == 2}">
				var  id = '${i.lft}'+"_"+'${i.rgt}';
				var html = '<div id="'+id+'" title="${i.powerName}"></div>';
				$('#accordion').append(html);
		</c:if>
	</c:forEach>
	var divs = $('div',$('#accordion'));
	var arr = new Array();
	$.each(divs,function(i,n){
		arr.push(divs[i].id);
	});
	<c:forEach var="i" items="${login_power}">
		<c:if test="${i.deepth  == 3}">
				var lft = '${i.lft}';
				var rgt = '${i.rgt}';
				var intlft = parseInt(lft);
				var intrgt = parseInt(rgt);
				var temp = new Array();
				$.each(arr,function(i,n){
					var text = arr[i].split("_");
					var a = text[0];
					var b = text[1];
					var inta = parseInt(a);
					var intb = parseInt(b);
				
					if(intlft>inta && intrgt<intb){
						temp.push(inta);
					}
				});
				var result = getParentId(temp,intlft);
				var id = null;
				$.each(arr,function(i,n){
					if(arr[i].split("_")[0]==result){
						id = arr[i];
					}
				});
				var s  = '$("#'+id+'")';
				var a = eval(s);
				a.append('<a href="javascript:void(0);" src="'+'${i.url}'+'" class="cs-navi-tab">'+'${i.powerName}'+'</a></p>');
		</c:if>
	</c:forEach>
			</script>
	<div id="mainPanle" region="center" border="true" border="false">
		 <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
                <div title="Home">
				<div class="cs-home-remark">
				</div>
				</div>
        </div>
	</div>
	<div region="south" border="false" id="south"><center></center></div>
	
	<div id="mm" class="easyui-menu cs-tab-menu">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
	</div>
</body>
</html>
