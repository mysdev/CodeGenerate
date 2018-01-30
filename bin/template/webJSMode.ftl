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
    	myAjax("/${pathName}/"+opid, "GET", null, function (data){
#foreach($item in $!{columnList})
#if(!${item.isBaseColumn})
			self.${item.classParam}(data.${item.classParam});
#end
#end
		}, true);
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
    		myAjaxJson("/${pathName}", "POST", submitPar, function (data){
				ChangeUrl(".${webPackage}/${className}List.html");
			}, true);
		}else{
    		var opid=getQueryString('id');
    		myAjaxJson("/${pathName}/"+opid, "PUT", submitPar, function (data){
				ChangeUrl(".${webPackage}/${className}List.html");
			}, true);
    	}
    };
};


$().ready(function(){
	$("#txtName").focus();
    ko.applyBindings(new ${className}EditViewModel());
});