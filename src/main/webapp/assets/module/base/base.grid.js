/**
 * 封装easyui grid
 */
define(function(require, exports, module) {

	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具

	var BaseGrid = function(){
		this.pathUrl;
		this.title;
		this.$grid;
		this.$dialog;
		this.$toolbar;
		this.gridParam;
		this.dialogParam;
		this.insertDialogButtons;
		this.updateDialogButtons;
	}

	BaseGrid.prototype = {
		constructor: BaseGrid,
		/**
		 * 
		 * @param container
		 * @param param
		 */
		init : function(param){
			var self = this;
			self.pathUrl = param.pathUrl;
			self.title = param.title;
			self.idKey = param.idKey;
			self.$grid = $(param.container.find('.list_grid')[0]);
			self.$dialog = $(param.container.find('.edit_window')[0]);
			self.$toolbar = $(param.container.find('.grid_bar')[0]);
			self.initDialogButton();
			self.setDialogParam(param.dialogParam);
			self.$grid.datagrid(self.setGridParam(param.gridParam));
			self.$toolbar.on('click','.bda-btn-add', function(){ self.add(); return false;});
			self.$toolbar.on('click','.bda-btn-edit', function(){ self.edit(); return false;});
			self.$toolbar.on('click','.bda-btn-del', function(){ self.del(); return false;});
		},
		/**
		 * 设置表格属性
		 * @param param
		 * @returns
		 */
		setGridParam : function(param){
			var self = this;
			var resultParam = {
				url : self.pathUrl + 'get',
				toolbar : self.$toolbar,
				rownumbers : true,
				fit : true,
				pagination : true,
				pageList : [ 10,20,50,100 ],
				pageNumber : 1,
				pageSize : 20,
				ctrlSelect : true,
                singleSelect : false,
                onDblClickRow : function(index,row){
                	if($('#dbclickFlag').val()){
                		self.dbclickEdit(row); 
                	}
                }
			};
			self.gridParam = $.extend(resultParam, param);
			return self.gridParam;
		},
		/**
		 * 设置编辑窗口属性
		 * 
		 * @param param
		 * @returns
		 */
		setDialogParam : function(param) {
			var self = this;
			var resultParam = {
				width : 480,
				height : 270,
				modal : true,
				minimizable : true,
				maximizable : true,
				resizable : true
			};
			
			self.dialogParam = $.extend(resultParam, param);
			return self.dialogParam;
		},
		/**
		 * 初始化窗口按钮
		 */
		initDialogButton : function(){
			var self = this;
			self.insertDialogButtons = [{
				text : '添加',
				iconCls : 'icon-ok',
				handler : function(){self.insert(); }
			},{
				text:'取消',
				handler:function(){self.$dialog.dialog('close');}
			}];
			
			self.updateDialogButtons = [{
				text : '修改',
				iconCls : 'icon-ok',
				handler : function(){self.update(); }
			},{
				text:'取消',
				handler:function(){self.$dialog.dialog("close");}
			}];
		},
		reload : function(){
			var self = this;
			self.$grid.datagrid('reload');
		},
		// 双击编辑
		dbclickEdit : function(row){
			var self = this;
			var param = $.extend({},self.dialogParam);
			param.title = '编辑' + self.title;
			param.href = self.pathUrl + 'edit';
			param.queryParams = {id:row[self.idKey]};
			param.buttons = self.updateDialogButtons;
			self.$dialog.dialog(param); 
			return false;
		},
	
		add : function(){
			var self = this;
			var param = $.extend({},self.dialogParam);
			param.title = '新增' + self.title;
			param.href = self.pathUrl + 'add';
			param.buttons = self.insertDialogButtons;
			self.$dialog.dialog(param); 
			return false;
		},
		
		edit : function(){
			var self = this;
			var selected = self.$grid.datagrid('getSelected');
			if(!selected){
				$.messager.alert('提示','请选择行','warning');
				return false;
			}
			var param = $.extend({},self.dialogParam);
			param.title = '编辑' + self.title;
			param.href = self.pathUrl + 'edit';
			param.queryParams = {id:selected[self.idKey]};
			param.buttons = self.updateDialogButtons;
			self.$dialog.dialog(param); 
			return false;
		},
		
		insert : function(){
			var self = this;
			self.save('insert');
		},
		
		update : function(){
			var self = this;
			self.save('update');
		},
		
		save : function(method){
			var self = this;
			//新增或编辑弹出框点击保存后提交表单设置
			$form = $('.base-edit-form');
			$form.form('submit',{
				url : self.pathUrl + method,
				onSubmit : function() {
					if($form.form('validate')){
						parent.$.messager.progress({
						    title : '提示',
						    text : '正在处理中，请稍候....'
						});
						return true;
					}else 
						return false;
				},
				success : function(resp) {
					resp = $.parseJSON(resp);
					parent.$.messager.progress('close');
					if(resp.meta.success){
						$.messager.alert('提示', '保存成功', 'info',function(){
							self.$dialog.dialog('close');
							self.$grid.datagrid('reload');
						});
					}else{
						$.messager.alert('错误', '保存失败'+resp.meta.code, 'error');
					}
				}
			});
		},
		
		del : function(){
			var self = this;
			var rows = self.$grid.datagrid('getSelections');
			if(rows != ''){
				var ids = new Array();
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i][self.idKey]);
				}
				$.messager.confirm('请确认','确认删除所行吗？',function(r){
				    if (r){
				    	$.post(self.pathUrl + 'del',{'ids[]':ids},function(resp){
				    		resp=$.parseJSON(resp);
		                	if(resp.meta.success){
				    			self.$grid.datagrid('reload');
				    		} else {
				    			$.messager.alert('提示', '删除失败'+resp.meta.code, 'error');
				    		}
						});
				    }
				});
			} else {
				$.messager.alert('提示', '请至少选择一行', 'info');
			}
			return false;
		},
		
		search : function(keys, values){
			var self = this;
			var options = self.$grid.datagrid('getPager').data("pagination").options;
			self.$grid.datagrid('load',{
				'keys[]' : keys,
	    		'values[]' : values,
    			'page' : options.pageNumber,
    			'rows' : options.pageSize
	    	});
		},
		
		datagrid : function(method, parm){
			var self = this;
			return self.$grid.datagrid(method, parm);
		}
	}

	module.exports = new BaseGrid();
})