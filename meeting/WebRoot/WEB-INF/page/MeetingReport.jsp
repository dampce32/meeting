<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sessionId = session.getId();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>打印会议报表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
	 <link rel="stylesheet" type="text/css" href="js/easyui/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/easyui/icon/icon.css">
	
	<script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/gridpp/GRInstall.js" ></script>
	<script type="text/javascript" src="js/gridpp/CreateControl.js" ></script>
	<script type="text/javascript">
			var meetingId = "<%=request.getParameter("meetingId") %>";
			var sessionId ='<%=sessionId%>';
	</script>
	<style type="text/css">
        html,body {
            margin:0;
            height:100%;
        }
    </style>
    <script language="javascript" type="text/javascript">
		    Install_InsertReport();
		</script>
  </head>
  
<body style="margin:0; background-color: #f0ffff;">
	<table border="0" width="100%" height="100%">
        <tr style="height: 100%;">
            <td style="font-size: 10pt;">
            	<script type="text/javascript">
					var Installed = Install_Detect();
					if ( Installed )
						CreateReport("Report");
				</script>
                <script type="text/javascript"> 
                    CreatePrintViewerEx("100%", "100%", "<%=basePath%>report/MEETING.grf", "<%=basePath%>Meeting.do;jsessionid="+sessionId+"?action=report&meetingId="+<%=request.getParameter("meetingId") %>, true, "");
                </script>
            </td>
        </tr>
	</table>
	
</body>
</html>
