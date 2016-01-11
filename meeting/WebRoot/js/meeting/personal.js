$(document).ready(function(){
	var width = $(document.body).width();
	var height = $(document.body).height();
	var genderDate = [{"text":"男","value":"男"},{"text":"女","value":"女"}];
	$('#gender').combobox({   
		data:genderDate,
		editable:false,
	    valueField:'value',   
	    textField:'text'  
	});  
	$('#departmentId').combobox({
		url:'Department.do?action=combobox',
		editable:false,
		valueField:'id',   
		textField:'departmentName'  
	});  
	$('#subBtn').click(function(){
		onSave();
	});
	var onSave = function(){
		var name = $('#name').val();
		var gender = $('#gender').combobox('getValue');
		var departmentId = $('#departmentId').combobox('getValue');
		var phoneNumber = $('#phoneNumber').val();
		var qq = $('#qq').val();
		var fax = $('#fax').val();
		var address = $('#address').val();
		var note = $('#note').val();
		/**
		 * 检测非空
		 */
		if(name==null || name==''|| typeof name === undefined){
			$.messager.alert('提示','请填写姓名','info');
			return;
		}
		if(gender==null || gender==''|| typeof gender === undefined){
			$.messager.alert('提示','请选择性别','info');
			return;
		}
		if(departmentId==null || departmentId==''|| typeof departmentId === undefined){
			$.messager.alert('提示','请选择所在部门','info');
			return;
		}
		if(phoneNumber==null || phoneNumber==''|| typeof phoneNumber === undefined){
			$.messager.alert('提示','请填写联系电话','info');
			return;
		}
		var data = 'name='+name+'&gender='+gender+'&departmentId='+departmentId+'&phoneNumber='+phoneNumber
		+'&qq='+qq+'&fax='+fax+'&address='+address+'&note='+note+'&action=personal';
		$.ajax({
			   type: "POST",
			   url: "User.do",
			   data:data,
			   success: function(result){
				   eval('var a = '+result+';');
				   if(a.success){
						$.messager.alert('提示',a.message,'info');
					    //更新信息
					}else{
						$.messager.alert('警告',a.message,'warning');
					}
			   }
		});
	};
});