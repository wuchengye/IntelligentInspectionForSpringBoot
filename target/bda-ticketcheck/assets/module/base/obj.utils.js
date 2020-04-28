define(function(require, exports, module) {

	var ObjUtil = function() {

	};

	ObjUtil.prototype = {
		constructor : ObjUtil,
		/**
		 * 初始化函数
		 */
		index : function() {

		},

		cmp : function(x, y) {

			// If both x and y are null or undefined and exactly the same
			if (x === y) {
				return true;
			}

			// If they are not strictly equal, they both need to be Objects
			if (!(x instanceof Object) || !(y instanceof Object)) {
				return false;
			}

			//They must have the exact same prototype chain,the closest we can do is
			//test the constructor.
			if (x.constructor !== y.constructor) {
				return false;
			}

			for (var p in x) {
				//Inherited properties were tested using x.constructor === y.constructor
				if (x.hasOwnProperty(p)) {
					// Allows comparing x[ p ] and y[ p ] when set to undefined
					if (!y.hasOwnProperty(p)) {
						return false;
					}

					// If they have the same strict value or identity then they are equal
					if (x[p] === y[p]) {
						continue;
					}

					// Numbers, Strings, Functions, Booleans must be strictly equal
					if (typeof(x[p]) !== "object") {
						return false;
					}

					// Objects and Arrays must be tested recursively
					if (!arguments.callee(x[p], y[p])) {
						return false;
					}
				}
			}

			for (p in y) {
				// allows x[ p ] to be set to undefined
				if (y.hasOwnProperty(p) && !x.hasOwnProperty(p)) {
					return false;
				}
			}
			return true;

		},
		
		size:function(obj){
			var len = 0;
			for(var attr in obj){
				if(obj.hasOwnProperty(attr)){
					len++;
				}
			}
			return len;
		}
	};

	module.exports = new ObjUtil();
});
