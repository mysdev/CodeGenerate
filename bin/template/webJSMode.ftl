//定义ViewModel对象
var ${className}EditViewModel = function () {  
	var self=this;
#foreach($item in $!{columnList})
	self.${item.classParam} = ko.observable(''); 
#end

    var opFalg=getQueryString('action');
    
    if(opFalg!="Add"){
    	var opid=getQueryString('id');
    	\$.ajax({
	        type: 'GET',
	        url: homeUrl+"/${pathName}/"+opid,
	        cache: false,
	        async: false,
	        dataType: "json",
	        success: function (result) {
#foreach($item in $!{columnList})
				self.${item.classParam}(result.${item.classParam});
#end
	        }
	    });
	}

	//【提交】按钮押下处理
    self.Commit = function () {    	
    	if(opFalg=="Add"){
    		var vStartTime=self.ondutyTime.toString();
    		var vEndTime=self.ondutyTime.toString();
	        \$.ajax({
	            type: "POST",
	            url: homeUrl+"/${pathName}",  //新增接口
	            dataType: "json",
	            data: {
#set($i=0)
#foreach($item in $!{columnList})
#if($i!=0)
					${item.classParam}:self.${item.classParam} #if($i!=$!{columnList.size()}),#end
#end
#end
					$!{keyColumn.classParam}:self.$!{keyColumn.classParam}
	            },
	            success: function (result) {
	                if(result.code==200){
	                	$("#mainframe", parent.window.document).attr("src","/${package}/${className}List.html");
	                }
	                else{
	                	parent.dialog(result.message).showModal();
	                }	                
	            }
	        });
		}
    	else{
    		var opid=getQueryString('id');
    		\$.ajax({
	            type: "PUT",
	            url: homeUrl+"/${pathName}/"+opid,  //修改接口
	            data: {
#set($i=0)
#foreach($item in $!{columnList})
#if($i!=0)
					${item.classParam}:self.${item.classParam},
#end
#end
					$!{keyColumn.classParam}:opid
	            },
	            success: function (json) {
	                alert(json.result);
	                $("#mainframe", parent.window.document).attr("src","/${package}/${className}List.html");
	            }
	        });
    	}
    };
};

$().ready(function(){
	$("#txtName").focus();
    ko.applyBindings(new ${className}EditViewModel());
})