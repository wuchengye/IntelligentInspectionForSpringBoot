
define(function(require, exports, module) {
	var CheckInput =function(){
	}
	CheckInput.prototype = {
		checkinput : function (){
			$("body").on("keydown","input",function(e){
				if(e.keyCode==32){ //处理空格,不让输入
					return false;
				}
			});
		}
	}
	module.exports = new CheckInput();
})