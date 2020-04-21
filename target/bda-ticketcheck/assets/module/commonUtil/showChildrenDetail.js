
define(function(require, exports, module) {
	
	var ChildrenDetial =function(){
		
	}
	
	ChildrenDetial.prototype = {
		
		constructor: ChildrenDetial,
			
		init : function (){
			var primaryVal = $("#childLs").val();//后台传来的原始json对象值
			var json = JSON.parse(primaryVal);//转化为json格式
			
			for(var i in json){
				console.log(json[i]);
				var field = json[i].field;
				var fieldArr = field.split("@");
				for(var o in fieldArr){
					$('.'+fieldArr[o]).css("color","red");
					var title = $('.'+fieldArr[o]).attr("title");
					if(title == undefined || title == null || title == ""){
						$('.'+fieldArr[o]).attr("title",json[i].description);
					}else{
						$('.'+fieldArr[o]).attr("title",title + "\n" + json[i].description);
					}
				}
			}
		}
	
	}
	
	module.exports = new ChildrenDetial();
})