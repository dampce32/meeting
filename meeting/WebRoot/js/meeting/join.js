$(document).ready(function(){
	var dataList = $('#dataList');
	var selectRow = null;
	$(dataList).datagrid({  
		url:'Meeting.do?action=queryCanJoin',
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
	        {field:'money',title:'会务费（元）',width:200}, 
	        {field:'address',title:'开会地址',width:100},  
	        {field:'host',title:'主办单位',width:100},
	        {field:'organizer',title:'承办单位',width:100},
	        {field:'co_organizer',title:'协办单位',width:100}
	    ]] ,
	    toolbar: [{
			iconCls: 'icon-edit',
			text:'点击报名参会',
			handler: function(){
				onJoin();
			}
		}],
		onSelect:function(rowIndex, rowData){
			selectRow = rowData;
		},
		onDblClickRow:function(rowIndex, rowData){
		}
	});  
	var onJoin = function(){
		if(selectRow == null){
			$.messager.alert('提示','请选中您要参加的会议','info');
			return;
		}
		$.messager.confirm('确认', '您确定报名参加这个会议吗？', function(r){
			if(r){
				var data = 'meetingId='+selectRow.meetingId+'&action=join';
				$.ajax({
					   type: "POST",
					   url: "Meeting.do",
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
	}
});