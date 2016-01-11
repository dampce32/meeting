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
		$('#id').val('');
		$('#departmentCode').val('');
		$('#departmentName').val('');
	};
	$(dataList).datagrid({  
		url:'Department.do?action=query',
	    rownumbers:true,
	    fitColumns:true,
	    singleSelect:true,
	    fit:true,
	    pagination:true,
	    columns:[[   
            {field:'id',hidden:true},   
	        {field:'departmentCode',title:'编号',width:100},   
	        {field:'departmentName',title:'部门名称',width:100}   
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
		}],
		onSelect:function(rowIndex, rowData){
			selectRow = rowData;
		},
		onDblClickRow:function(rowIndex, rowData){
			onModify();
		}
	});  
	$(addDataDialog).dialog({   
	    title: '编辑部门',   
	    width: 400,   
	    height: 200,   
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
		var departmentCode = $('#departmentCode').val();
		var departmentName = $('#departmentName').val();
		/**
		 * 主键
		 */
		var id = $('#id').val();
		/**
		 * 检测非空
		 */
		if(departmentCode==null || departmentCode==''|| typeof departmentCode === undefined){
			$.messager.alert('提示','请填写编号','info');
			return;
		}
		if(departmentName==null || departmentName==''|| typeof departmentName === undefined){
			$.messager.alert('提示','请填写部门名称','info');
			return;
		}
		var data = 'id='+id+'&departmentCode='+departmentCode+'&departmentName='+departmentName+'&action=add';
		$.ajax({
			   type: "POST",
			   url: "Department.do",
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
		$.messager.confirm('确认', '您确定删除该条数据吗？', function(r){
			if(r){
				var data = 'id='+selectRow.id+'&action=remove';
				$.ajax({
					   type: "POST",
					   url: "Department.do",
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
		$('#id').val(selectRow.id);
		$('#departmentCode').val(selectRow.departmentCode);
		$('#departmentName').val(selectRow.departmentName);
		$(addDataDialog).dialog('open');
	};
});