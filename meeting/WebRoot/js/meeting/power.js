$(document).ready(function(){
	var dataList = $('#dataList');
	var addDataDialog = $('#addDataDialog');
	var width = $(document.body).width();
	var height = $(document.body).height();
	var selectRow = null;
	$(dataList).datagrid({  
		url:'Power.do?action=query&pid='+pid,
	    rownumbers:true,
	    fitColumns:true,
	    singleSelect:true,
	    fit:true,
	    columns:[[   
            {field:'powerId',hidden:true},   
	        {field:'powerName',title:'权限名称',width:100},   
	        {field:'url',title:'权限URL',width:100}
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
				onDelete();
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
	    title: '编辑权限',   
	    width: 300,   
	    height: 200,   
	    closed: true,   
	    cache: false,   
	    closable:false,
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
			handler:function(){
				$(addDataDialog).dialog('close');
			}
		}]
	});
	var onSave = function(){
		/**
		 * 必须填写
		 */
		var powerName = $('#powerName').val();
		var url = $('#url').val();
		var powerId = $('#powerId').val();
		if(powerName==null || powerName==''|| typeof powerName === undefined){
			$.messager.alert('警告','请填写权限名称');
			return;
		}
		if(url==null || url==''|| typeof url === undefined){
			$.messager.alert('警告','请填写url');
			return;
		}
		var data = 'powerId='+powerId+'&powerName='+powerName+'&url='+url+'&action=add&pid='+pid;
		$.ajax({
			   type: "POST",
			   url: "Power.do",
			   data:data,
			   success: function(result){
				   eval('var a = '+result+';');
				   if(a.success){
					   $.messager.alert('提示',a.message,'info');
					   $(addDataDialog).dialog('close');
					   $(dataList).datagrid('reload');
				   }else{
					   $.messager.alert('警告',a.message,'warning');
				   }
			   }
		});
	};
	var onDelete = function(){
		if(selectRow == null){
			$.messager.alert('提示','请选择您要删除的数据','info');
			return;
		}
		$.messager.confirm('确认', '您确定删除这条数据吗', function(r){
			if (r){
				var powerId = $('#powerId').val();
				var data = 'powerId='+powerId+'&action=delete';
				$.ajax({
					   type: "POST",
					   url: "Power.do",
					   data:data,
					   success: function(result){
						   eval('var a = '+result+';');
						   if(a.success){
							   $.messager.alert('提示',a.message,'info');
							   $(addDataDialog).dialog('close');
							   $(dataList).datagrid('reload');
						   }else{
							   $.messager.alert('警告',a.message,'warning');
						   }
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
		$('#powerName').val(selectRow.powerName);
		$('#url').val(selectRow.url);
		$('#powerId').val(selectRow.powerId);
		$(addDataDialog).dialog('open');
	};
	var clearForm = function(){
		$('#powerName').val('');
		$('#url').val('');
		$('#powerId').val('');
	};
});