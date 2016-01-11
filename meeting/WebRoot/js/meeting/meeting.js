$(document).ready(function(){
	/**
	 * 会议
	 */
	var width = $(document.body).width();
	var height = $(document.body).height();
	var dataList = $('#dataList');
	var addDataDialog = $('#addDataDialog');
	var selectRow = null;
	$(dataList).datagrid({  
		url:'Meeting.do?action=query',
	    rownumbers:true,
	    fitColumns:true,
	    singleSelect:true,
	    fit:true,
	    pagination:true,
	    columns:[[   
            {field:'meetingId',hidden:true},   
	        {field:'name',title:'会议名称',width:100},   
	        {field:'peopleNumber',title:'已经报名人数',width:100,}, 
	        {field:'registerBegin',title:'报名开始时间',width:100},
	        {field:'registerEnd',title:'报名结束时间',width:100},   
	        {field:'beginDate',title:'会议开始时间',width:100},
	        {field:'endDate',title:'会议结束时间',width:100},
	        {field:'money',title:'会务费（元）',width:100}, 
	        {field:'pay',title:'支付方式',width:100}, 
	        {field:'address',title:'开会地址',width:100},  
	        {field:'host',title:'主办单位',width:100},
	        {field:'organizer',title:'承办单位',width:100},
	        {field:'co_organizer',title:'协办单位',width:100}
	    ]] ,
	    toolbar: [{
			iconCls: 'icon-add',
			text:'发布新会议',
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
			iconCls: 'icon-print',
			text:'打印会议报表',
			handler: function(){
				onPrint();
			}
		}],
		onSelect:function(rowIndex, rowData){
			selectRow = rowData;
		},
		onDblClickRow:function(rowIndex, rowData){
			onModify();
		}
	});  
	var onPrint = function(){
		if(selectRow==null){
			$.messager.alert('提示','请选中一条记录',"warning");
			return;
		}
		var meetingId = selectRow.meetingId;
		var url = 'Meeting.do?action=reportPage&meetingId='+meetingId;
		window.open(url);
	};
	
	$(addDataDialog).dialog({   
	    title: '编辑会议',   
	    width: 500,   
	    height: 420,   
	    closed: true,
	    closable:false,
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
			handler:function(){
				onClose();
			}
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
		var registerBegin = $('#registerBegin').val();
		var registerEnd = $('#registerEnd').val();
		var beginDate = $('#beginDate').val();
		var endDate = $('#endDate').val();
		var money = $('#money').numberbox('getValue');
		var address = $('#address').val();
		var host = $('#host').val();
		/**
		 * 不必要填写
		 */
		var organizer = $('#organizer').val();
		var co_organizer = $('#co_organizer').val();
		var meetingId = $('#meetingId').val();
		var pay = $('#pay').val();
		/**
		 * 检测非空
		 */
		if(name==null || name==''|| typeof name === undefined){
			$.messager.alert('提示','请填写姓名','info');
			return;
		}
		if(registerBegin==null || registerBegin==''|| typeof registerBegin === undefined){
			$.messager.alert('提示','请选择报名开始时间','info');
			return;
		}
		if(registerEnd==null || registerEnd==''|| typeof registerEnd === undefined){
			$.messager.alert('提示','请选择报名结束时间','info');
			return;
		}
		if(beginDate==null || beginDate==''|| typeof beginDate === undefined){
			$.messager.alert('提示','请选择会议开始时间','info');
			return;
		}
		if(endDate==null || endDate==''|| typeof endDate === undefined){
			$.messager.alert('提示','请选择会议结束时间','info');
			return;
		}
		if(money==null || money==''|| typeof money === undefined){
			$.messager.alert('提示','请填写会务费','info');
			return;
		}
		if(address==null || address==''|| typeof address === undefined){
			$.messager.alert('提示','请填写开会地址','info');
			return;
		}
		if(host==null || host==''|| typeof host === undefined){
			$.messager.alert('提示','请填写主办单位','info');
			return;
		}
		var data = 'meetingId='+meetingId+'&pay='+pay+'&name='+name+'&registerBegin='+registerBegin
		+'&registerEnd='+registerEnd+'&beginDate='+beginDate+'&endDate='+endDate
		+'&money='+money+'&address='+address+'&host='+host+'&organizer='+organizer+'&co_organizer='+co_organizer+'&action=add';
		$.ajax({
			   type: "POST",
			   url: "Meeting.do",
			   data:data,
			   success: function(result){
				   eval('var a = '+result+';');
				   if(a.success){
					   $.messager.alert('提示',a.message,'info');
					   $(addDataDialog).dialog('close');
					   $(dataList).datagrid('reload');
					   selectRow = null;
				   }else{
					   $.messager.alert('警告',a.message,'warning');
				   }
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
				var data = 'meetingId='+selectRow.meetingId+'&action=remove';
				$.ajax({
					   type: "POST",
					   url: "Meeting.do",
					   data:data,
					   success: function(result){
						   eval('var a = '+result+';');
						   if(a.success){
							   $.messager.alert('提示',a.message,'info');
							   selectRow = null;
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
			$.messager.alert('警告','请选中一条数据','info');
			return;
		}
		$('#name').val(selectRow.name);
		$('#registerBegin').val(selectRow.registerBegin);
		$('#registerEnd').val(selectRow.registerEnd);
		$('#beginDate').val(selectRow.beginDate);
		$('#endDate').val(selectRow.endDate);
		$('#money').numberbox('setValue',selectRow.money);
		$('#address').val(selectRow.address);
		$('#host').val(selectRow.host);
		$('#organizer').val(selectRow.organizer);
		$('#co_organizer').val(selectRow.co_organizer);
		$('#meetingId').val(selectRow.meetingId);
		$('#pay').val(selectRow.pay);
		$(addDataDialog).dialog('open');
	};
	var clearForm = function(){
		$('#name').val('');
		$('#registerBegin').val('');
		$('#registerEnd').val('');
		$('#beginDate').val('');
		$('#endDate').val('');
		$('#money').numberbox('clear');
		$('#address').val('');
		$('#host').val('');
		$('#organizer').val('');
		$('#co_organizer').val('');
		$('#meetingId').val('');
		$('#pay').val('');
	};
	$('#queryBtn').click(function(){
		var meetingName = $('#meetingNameQuery').val();
		$(dataList).datagrid({  
			url:'Meeting.do?action=query&meetingName='+meetingName,
		});
	});
	$('#resetBtn').click(function(){
		$('#meetingNameQuery').val('');
	});
});