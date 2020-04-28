
define(function(require, exports, module) {
	function initDate(){
		$('#startTime').datetimebox({  
		    onShowPanel:function(){  
		        $(this).datetimebox("spinner").timespinner("setValue","00:00:00");  
		    }  
		});  
		$('#endTime').datetimebox({  
		    onShowPanel:function(){  
		        $(this).datetimebox("spinner").timespinner("setValue","23:59:59");  
		    }  
		});  
	}
	
	module.exports ={initDate:initDate};
})