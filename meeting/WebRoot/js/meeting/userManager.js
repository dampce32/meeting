$(document).ready(function(){
	/**
	 * 用户管理
	 */
	var width = $(document.body).width();
	var height = $(document.body).height();
	var dataList = $('#dataList');
	var addDataDialog = $('#addDataDialog');
	var selectRow = null;
	var clearForm = function(){
		$('#userCode').attr('disabled',false);
		$('#userId').val('');
		$('#name').val('');
		$('#userCode').val('');
		$('#gender').combobox('clear');
		$('#departmentId').combobox('clear');
		$('#phoneNumber').val('');
		$('#roleId').combobox('clear');
		$('#qq').val('');
		$('#fax').val('');
		$('#address').val('');
		$('#note').val('');
	};
	var roleData = [{'text':'管理员','value':1},{'text':'普通用户','value':2}];
	$('#roleId').combobox({ 
		editable:false,
		data:roleData,
	    valueField:'value',   
	    textField:'text'  
	});  
	
	var genderData = [{'text':'男','value':'男'},{'text':'女','value':'女'}];
	$('#gender').combobox({ 
		editable:false,
		data:genderData,
		valueField:'value',   
		textField:'text'  
	});  
	$('#departmentId').combobox({ 
		editable:false,
		url:'Department.do?action=combobox',
		valueField:'id',   
		textField:'departmentName'  
	});  
	
	$(dataList).datagrid({  
		url:'User.do?action=query',
	    rownumbers:true,
	    fitColumns:true,
	    singleSelect:true,
	    fit:true,
	    pagination:true,
	    columns:[[   
            {field:'userId',hidden:true},   
	        {field:'name',title:'姓名',width:100},   
	        {field:'userCode',title:'账号',width:100},
	        {field:'gender',title:'性别',width:100},   
	        {field:'roleId',title:'用户角色',width:100,formatter: function(value,row,index){
	        	if (value==1){
	        		return "管理员";
	        	} else {
	        		return "普通用户";
	        	}
	        }
	        },
	        {field:'departmentName',title:'所属部门',width:100},
	        {field:'departmentId',hidden:true},
	        {field:'phoneNumber',title:'电话号码',width:100},
	        {field:'fax',title:'传真号码',width:100},
	        {field:'address',title:'联系地址',width:200}, 
	        {field:'qq',title:'qq号码',width:100},  
	        {field:'note',title:'备注',width:100}   
	    ]] ,
	    toolbar: [{
			iconCls: 'icon-add',
			text:'添加',
			handler: function(){
				clearForm();
				$(addDataDialog).dialog('open');
			}
		},'-',{
			iconCls: 'icon-remove',
			text:'删除',
			handler: function(){
				remove();
			}
		},'-',{
			iconCls: 'icon-edit',
			text:'修改',
			handler: function(){
				onModify();
			}
		},'-',{
			iconCls: 'icon-reload',
			text:'重置密码',
			handler: function(){
				resetPassword();
			}
		}],
		onSelect:function(rowIndex, rowData){
			selectRow = rowData;
		},
		onDblClickRow:function(rowIndex, rowData){
			onModify();
		}
	});  
	$(addDataDialog).dialog({   
	    title: '编辑用户',   
	    width: 400,   
	    height: 500,   
	    closed: true,   
	    cache: false,   
	    modal: true,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-add',
			handler:function(){
				onSave();
			}
		},{
			text:'退出',
			iconCls:'icon-exit',
			handler:function(){onClose();}
		}]
	});   
	var onClose = function(){
		$(addDataDialog).dialog('close');
	};
	var onSave = function(){
		/**
		 * 必须填写
		 */
		var name = $('#name').val();
		var userCode = $('#userCode').val();
		var gender = $('#gender').combobox('getValue');
		var departmentId = $('#departmentId').combobox('getValue');
		var phoneNumber = $('#phoneNumber').val();
		var roleId = $('#roleId').combobox('getValue');
		/**
		 * 不必要填写
		 */
		var qq = $('#qq').val();
		var fax = $('#fax').val();
		var address = $('#address').val();
		var note = $('#note').val();
		/**
		 * 主键
		 */
		var userId = $('#userId').val();
		/**
		 * 检测非空
		 */
		if(name==null || name==''|| typeof name === undefined){
			$.messager.alert('提示','请填写姓名','info');
			return;
		}
		if(userCode==null || userCode==''|| typeof userCode === undefined){
			$.messager.alert('提示','请填写用户名','info');
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
		if(roleId==null || roleId==''|| typeof roleId === undefined){
			$.messager.alert('提示','请选择用户角色','info');
			return;
		}
		var data = 'userId='+userId+'&name='+name+'&userCode='+userCode+'&gender='+gender+'&departmentId='+departmentId+'&phoneNumber='+phoneNumber
		+'&roleId='+roleId+'&qq='+qq+'&fax='+fax+'&address='+address+'&note='+note+'&action=add';
		$.ajax({
			   type: "POST",
			   url: "User.do",
			   data:data,
			   success: function(result){
				   eval('var a = '+result+';');
					if(a.success){
						$.messager.alert('提示',a.message,'info');
					    $(addDataDialog).dialog('close');
					    selectRow = null;
					}else{
						$.messager.alert('警告',a.message,'warning');
					}
					$(dataList).datagrid('reload');
			   }
		});
	};
	var remove = function(){
		if(selectRow == null){
			$.messager.alert('提示','请选中您要删除的数据','info');
			return;
		}
		$.messager.confirm('确认', '删除用户会删除该用户所对应的参会记录，您确定删除该条数据吗？', function(r){
			if(r){
				var data = 'userId='+selectRow.userId+'&action=remove';
				$.ajax({
					   type: "POST",
					   url: "User.do",
					   data:data,
					   success: function(result){
						   eval('var a = '+result+';');
						   if(a.success){
							   $.messager.alert('提示',a.message,'info');
							   selectRow = null;
						   }else{
							   $.messager.alert('警告',a.message,'warning');
						   }
						   $(dataList).datagrid('reload');
					   }
				});
			}
		});
	};
	var onModify = function(){
		if(selectRow == null){
			$.messager.alert('提示','请选择您要修改的数据','info');
			return;
		}
		$('#userCode').attr('disabled','disabled');
		$('#userId').val(selectRow.userId);
		$('#name').val(selectRow.name);
		$('#userCode').val(selectRow.userCode);
		$('#gender').combobox('setValue',selectRow.gender);
		$('#departmentId').combobox('setValue',selectRow.departmentId);
		$('#phoneNumber').val(selectRow.phoneNumber);
		$('#roleId').combobox('setValue',selectRow.roleId);
		$('#qq').val(selectRow.qq);
		$('#fax').val(selectRow.fax);
		$('#address').val(selectRow.address);
		$('#note').val(selectRow.note);
		$(addDataDialog).dialog('open');
	};
	var resetPassword = function(){
		if(selectRow == null){
			$.messager.alert('提示','请选中您要重置密码的数据','info');
			return;
		}
		$.messager.confirm('确认', '您确定重置该用户的密码吗？', function(r){
			if (r){
				var data = 'userId='+selectRow.userId+'&action=resetPassword';
				$.ajax({
					   type: "POST",
					   url: "User.do",
					   data:data,
					   success: function(result){
						   eval('var a = '+result+';');
						   if(a.success){
							   $.messager.alert('提示',a.message,'info');
						   }else{
							   $.messager.alert('警告',a.message,'warning');
						   }
					   }
				});
			}
		});
	}
});