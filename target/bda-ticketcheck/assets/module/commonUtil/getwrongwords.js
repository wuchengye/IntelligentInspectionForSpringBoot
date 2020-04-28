/**
 * 获取 消岐词 配置
 */
define(function(require, exports, module) {
	function getWrongWords(){
		var d;
		$.ajax({
			type: "get",//请求方式
            url: "assets/config/wrong_words.json",//地址，就是json文件的请求路径
            dataType: "json",//数据类型可以为 text xml json  script  jsonp
            async: false,
            success: function(data){//返回的参数就是 action里面所有的有get和set方法的参数
            	//console.log(data);
            	d = data;
            }
		})
		return d;
		
	}
	
	module.exports ={getWrongWords:getWrongWords};
})