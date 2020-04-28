
define(function(require, exports, module) {
	function initNumlist(){
		var numlist = [{'value':10,'text':10},
		               {'value':20,'text':20,'selected':'true'},
		               {'value':50,'text':50},
		               {'value':100,'text':100}];
		$('#numlist').combobox({
			data:numlist,
			panelHeight:'auto',
			width:'50px',
			editable:false
		});  
	}
	
	module.exports ={initNumlist:initNumlist};
})