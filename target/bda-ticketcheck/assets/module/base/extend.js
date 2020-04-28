/**
 *  拓展js
 */
define(function(require, exports, module) {
	
	Extend = function(){};
	
	Extend.prototype = {
			constructor:Extend,
			init:function(){
				// extend the 'textbox' addClearBtn
				$.extend($.fn.textbox.methods, {
			         addClearBtn: function(jq, iconCls){
			             return jq.each(function(){
			                 var t = $(this);
			                 var opts = t.textbox('options');
			                 opts.icons = opts.icons || [];
			                 opts.icons.unshift({
			                     iconCls: iconCls,
			                     handler: function(e){
			                         $(e.data.target).textbox('clear').textbox('textbox').focus();
			                         $(this).css('visibility','hidden');
			                     }
			                 });
			                 t.textbox();
			                 if (!t.textbox('getText')){
			                     t.textbox('getIcon',0).css('visibility','hidden');
			                 }
			                 t.textbox('textbox').bind('keyup', function(){
			                     var icon = t.textbox('getIcon',0);
			                     if ($(this).val()){
			                         icon.css('visibility','visible');
			                     } else {
			                         icon.css('visibility','hidden');
			                     }
			                 });
			             });
			         }
			     });
				// extend the 'equals' rule
				$.extend($.fn.validatebox.defaults.rules, {
				    /*equals: {
				        validator: function(value,param){
				        	console.log(value,$(param[0]).val());
				            return value == $(param[0]).val();
				        },
				        message: '数据值不相等'
				    }*/
					unsignInt: {
						validator: function(value, param){
							var rgx = /^((\d)|([1-9]\d+))$/;
							return rgx.test(value);
						},
						message: "请输入正确的数字"
					},
					unsignPsw: {
						validator: function(value, param){
							var rgx = /^(?:(?=.*[0-9].*)(?=.*[A-Za-z].*)(?=.*[,\.#%@\~\='"\+\*\-:;^_`\?\!\$&\|\(\)\[\]\{\}<>\/\\].*))[?=.*[,\.#%@\~\='"\+\*\-:;^_`\?\!\$&\|\(\)\[\]\{\}<>\/\\0-9A-Za-z]{8,100}$/;
							return rgx.test(value);
						},
						message: "请输入至少8位字母、数字及符号组合~"
					},
				});
			},
			
			queryCombotree: function(selector){
				$(selector).combotree({
				   prompt:'(默认全局)',
				   panelHeight:'auto',
				   panelMaxHeight:250,
				   editable:true,
				   panelWidth:280,
                   valueField:'value',
                   textField:'text',
                   url:'electric/organization/getComboxTree',
		           keyHandler : {
		        	   query : function(q) {
			        	   var t = $(this).combotree('tree');  
			        	   var nodes = t.tree('getChildren');  
			        	   for(var i=0; i<nodes.length; i++){  
			        	   	   var node = nodes[i];  
			        	   	   if (node.text.indexOf(q) >= 0){  
			        	           $(node.target).show();  
			        	   	   }else {  
			        	           $(node.target).hide();  
			        	   	   }  
			        	   	   var opts = $(this).combotree('options');  
			        	   	   if (!opts.hasSetEvents){  
			        	           opts.hasSetEvents = true;  
			        	           var onShowPanel = opts.onShowPanel;  
			        	           opts.onShowPanel = function(){  
			        	               var nodes = t.tree('getChildren');  
			        	               for(var i=0; i<nodes.length; i++){  
			        	                   $(nodes[i].target).show();  
			        	               }  
			        	               onShowPanel.call(this);  
			        	         	};  
			        	            $(this).combo('options').onShowPanel = opts.onShowPanel;  
			        	   	    }  
			        	   	}
			        	}
		            }
				})
			}
	}
	
	
	module.exports = new Extend();
});
