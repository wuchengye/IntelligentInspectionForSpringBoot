/**
 * 浏览器兼容性问题
 * 
 * @author yegui.lao
 */
define(function(require, exports, module) {
	module.exports.init = function() {

	}
	
	//IE8不支持数组的indexOf方法,可以在用数组的indexOf方法之前调用该方法
	module.exports.indexOf = function indexOf() {
		if(document.all){
			if (!Array.prototype.indexOf) {
				Array.prototype.indexOf = function(elt /*, from*/) {
					var len = this.length >>> 0;
					var from = Number(arguments[1]) || 0;
					from = (from < 0) ? Math
							.ceil(from)
							: Math
									.floor(from);
					if (from < 0)
						from += len;
					for (; from < len; from++) {
						if (from in this
								&& this[from] === elt)
							return from;
					}
					return -1;
				};
			} 
		}
	}

});