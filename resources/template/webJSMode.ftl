//定义ViewModel对象
var ${className}EditViewModel = function () {  
	var self=this;
#foreach($item in $!{columnList})
#if(!${item.isBaseColumn})
	self.${item.classParam} = ko.observable(''); 
#end
#end

    var opFalg=getQueryString('action');
    
    if(opFalg!="Add"){
    	var opid=getQueryString('id');
    	($).ajax({
	        type: 'GET',
	        url: homeUrl+"/${pathName}/"+opid,
	        cache: false,
	        async: false,
	        dataType: "json",
	        success: function (result) {
#foreach($item in $!{columnList})
#if(!${item.isBaseColumn})
				self.${item.classParam}(result.${item.classParam});
#end
#end
	        }
	    });
	}

	//【提交】按钮押下处理
    self.Commit = function () {    
    	var submitPar ={};
#set($i=0)
#foreach($item in $!{columnList})
#if(!${item.isBaseColumn})
#if($i!=0)
		submitPar.${item.classParam}=self.${item.classParam}();
#end
#end
#set($i=$i+1)
#end
    	
    	if(opFalg=="Add"){
	        ($).ajax({
	            type: "POST",
	            url: homeUrl+"/${pathName}",  //新增接口
	            dataType: "json",
	            contentType : "application/json", 
	            data: JSON.stringify(submitPar),
	            success: function (result) {
	                if(result.code==200){
	                	$("#mainframe", parent.window.document).attr("src",".${webPackage}/${className}List.html");
	                }
	                else{
	                	parent.dialog(result.message).showModal();
	                }	                
	            }
	        });
		}
    	else{
    		var opid=getQueryString('id');
    		($).ajax({
	            type: "PUT",
	            url: homeUrl+"/${pathName}/"+opid,  //修改接口
	            contentType : "application/json", 
	            data: JSON.stringify(submitPar),
	            success: function (json) {
	            	if(result.code==200){
	                	$("#mainframe", parent.window.document).attr("src",".${webPackage}/${className}List.html");
	                }
	                else{
	                	parent.dialog(result.message).showModal();
	                }
	            }
	        });
    	}
    };
};

$().ready(function(){
	$("#txtName").focus();
    ko.applyBindings(new ${className}EditViewModel());
});