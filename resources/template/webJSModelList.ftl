var myPage;

function Node(obj) {
#foreach($item in $!{columnList})
	this.${item.classParam} = ko.observable(obj.${item.classParam}); 
#end
}

//定义ViewModel对象
var ${className}ViewModel = function () {  
	var self=this;
    //添加动态监视数组对象
    self.dataList = ko.observableArray([]);
    
    //初始化数据
    $.getJSON(homeUrl+"/${pathName}s",function(result){
		var mappedTasks = $.map(result.data, function(item) { return new Node(item) });  
	    self.dataList(mappedTasks);
	    myPage = result.page;
	    bindPage();
	    
	    $("table tbody td .tomodify").bind(function(){
	    	$("#mainframe", parent.window.document).attr("src",'/${package}/${className}.html?action=Edit&id='+$(this).attr('data'));
	    });
	});
	
	//搜索
	self.search = function(obj) {
		$.getJSON(homeUrl+"/${pathName}s?attendanceName="+$("txtKeywords").val(),function(result){
			var mappedTasks = $.map(result.data, function(item) { return new Node(item) });  
		    self.deptlist(mappedTasks);
		    myPage = result.page;
		    bindPage();
		});
    };
    
    //新增
    self.add = function(obj) {
    	$("#mainframe", parent.window.document).attr("src",'/${package}/${className}.html?action=Add');
    };
    
    //修改
    self.modify=function(){
    	$("#mainframe", parent.window.document).attr("src",'/${package}/${className}.html?action=Edit&id='+$(event.currentTarget).attr('data'));
    };
    
    //删除
    self.delete=function(obj){
    	var id = $(event.currentTarget).attr('data');
    	.ajax({
	        type: 'DELETE',
	        url: homeUrl+'/${pathName}/'+id,
	        cache: false,
	        async: false,
	        dataType: "json",
	        success: function (datas) {
	            parent.dialog(datas.message).showModal();
	        }
	    });
    }
    
    //批量删除
    self.deletes = function(obj) {
    	if ($(".checkall input:checked").size() < 1) {
        	parent.dialog({
	            title: '提示',
	            content: '对不起，请选中您要操作的记录！',
	            okValue: '确定',
	            ok: function () { }
	        }).showModal();
	        return false;
	    }
	    var msg = "删除记录后不可恢复，您确定吗？";
	    parent.dialog({
	        title: '提示',
	        content: msg,
	        okValue: '确定',
	        ok: function () {
	        	$(".checkall input:checked").each(function(i){
	        		.ajax({
				        type: 'DELETE',
				        url: homeUrl+'/${pathName}/'+$(this).attr('data'),
				        cache: false,
				        async: false,
				        dataType: "json",
				        success: function (datas) {
				            
				        }
				    });
	        	});
	        	document.URL=location.href;
	        },
	        cancelValue: '取消',
	        cancel: function () { }
	    }).showModal();
    };

};

$().ready(function(){

    ko.applyBindings(new ${className}ViewModel());

});

var bindPage =function(){
	//分页控件加载处理
    .jqPaginator('#pagination', {
        totalPages: myPage.totalPages,
        visiblePages: myPage.limit,
        currentPage: myPage.page,
        onPageChange: function (num, type) {
            if (type != 'init') {
            	$("#mainframe", parent.window.document).attr("src",'/${pathName}/${className}List.html?page=' + num);
            }
        }
    });
}

