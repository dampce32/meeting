$(document).ready(function(){
	if ($.browser.msie && $.browser.version <=6){
		var fn = function(){
			window.close();
		};
		$.messager.alert('提示','对不起,您使用的浏览器版本过低,请至少使用IE7','info',fn);
		return;
	}
	var init = function(){
		var userinfo = $.cookie("meetinginfo");
		if(userinfo!=null&&""!=userinfo){
			var array = userinfo.split("§");
			$("#userCode").val(array[0]);
			$("#password").val(array[1]);
			$("#mindpwd").attr('checked','true');
		}
		
		$('#loginCaptcha').val('')
	}
	init();
	
	var loginCaptchaImageRefresh = function(){
		$("#loginCaptcha").val('');
		$("#loginCaptchaImage").attr("src", "Img.do?date="+new Date());
	};
	// 点击刷新验证码图片
	$("#loginCaptchaImage").click( function() {
		loginCaptchaImageRefresh();
	});
	$(document).keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
			$('#btnSubmit').click();
		} 
	}); 
	
	/**
	 * 登录
	 */
	var login = function(){
		var userCode = $('#userCode').val();
		var password = $('#password').val();
		var loginCaptcha = $('#loginCaptcha').val();
		if(userCode == null || $.trim(userCode)==''){
			alert("请填写账号");
			return;
		}
		if(password ==null || $.trim(password)==''){
			alert("请填写密码"); 
			return;
		}
		if(loginCaptcha ==null || $.trim(loginCaptcha)==''){
			alert("请填写验证码"); 
			return;
		}
		var data = 'userCode='+userCode+'&password='+password+'&loginCaptcha='+loginCaptcha+'&action=login';
		$.ajax({
			   type: "POST",
			   url: "User.do",
			   data:data,
			   success: function(result){
				   eval('var a = '+result+';');
				   if(a.success == false){
					   alert(a.message);
					   if('验证码错误'==a.message){
						   loginCaptchaImageRefresh();
					   }
				   }else{
					   if($("#mindpwd").attr("checked")){
							 var localInfo = $("#userCode").val()+"§"+$("#password").val();
							 $.cookie("meetinginfo",localInfo,{expires: 30,path:'/'});
						 }else{
							 $.cookie("meetinginfo",null,{path:'/'});
					   }
					   window.location.href='index.jsp';
				   }
			   }
		});
	};
	/**
	 * 登录按钮点击
	 */
	$('#btnSubmit').click(function(){
		login();
	}); 
});