$(document).ready( function(){
	var data = [{"text":"管理员","value":"1","selected":true},{"text":"普通用户","value":"2"}];
	$('#roleSelect').combobox({
		editable:false,
		valueField:'value',
		textField:'text',
		width:150,
		data:data,
		onSelect:function(record){
			var roleId = record.value;
			var url = 'Power.do?action=node&roleId='+roleId;
			$('#powerTree').tree({
				url:url
			});
		}
	});
	
	$('#powerTree').tree({
		url:'Power.do?action=node&roleId=1',
		onSelect:function (node) {
	  		if($('#powerTree').tree('isLeaf',node.target)){
	            var url = 'Power.do?powerId='+node.id;
	            var title = node.text;
	            addTabs(title, url);
	  		}
        }
	});
	function addTabs(title, url){
		if ($('#powerTabs').tabs('exists', title)){
			$('#powerTabs').tabs('select', title);//选中并刷新
			var currTab = $('#powerTabs').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			if(url != undefined && currTab.panel('options').title != 'Home') {
				$('#powerTabs').tabs('update',{
					tab:currTab,
					options:{
						content:createFrame(url)
					}
				})
			}
		} else {
			var content = createFrame(url);
			$('#powerTabs').tabs('add',{
				title:title,
				content:content,
				closable:true
			});
		}
		tabClose();
	}
	function createFrame(url) {
		var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	}
	function tabClose() {
	    /*双击关闭TAB选项卡*/
	    $(".tabs-inner").dblclick(function () {
	        var subtitle = $(this).children(".tabs-closable").text();
	        $('#powerTabs').tabs('close', subtitle);
	    })
	    /*为选项卡绑定右键*/
	    $(".tabs-inner").bind('contextmenu', function (e) {
	        $('#mm').menu('show', {
	            left:e.pageX,
	            top:e.pageY
	        });

	        var subtitle = $(this).children(".tabs-closable").text();

	        $('#mm').data("currtab", subtitle);
	        $('#powerTabs').tabs('select', subtitle);
	        return false;
	    });
	}
	tabCloseEven();
});
function tabCloseEven() {
    //关闭当前
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#powerTabs').tabs('close', currtab_title);
    })
    //全部关闭
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            if (t != '首页') {
                $('#powerTabs').tabs('close', t);
            }
        });
    });
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        var nextall = $('.tabs-selected').nextAll();
        if (prevall.length > 0) {
            prevall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#powerTabs').tabs('close', t);
                }
            });
        }
        if (nextall.length > 0) {
            nextall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#powerTabs').tabs('close', t);
                }
            });
        }
        return false;
    });
}