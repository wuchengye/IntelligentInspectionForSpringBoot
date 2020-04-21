/**
 * Tag页面控制，标签管理的入口页面
 */
define(function(require, exports, module) {
	var base = require("module/base/main.js");
	var extend = require("module/base/extend.js");
	var width='600px';
	var height='300px';
	var menuUrl = "admin/qmauth/searchMenus";
	var menuTreeUrl = "admin/qmauth/getMenu";
	var editUrl = "admin/qmauth/edit";
	var delUrl = "admin/qmauth/del";
	
	var MenuMain = function() {
		this.$grid;
		this.$form;
		this.$dialog;
	};

	MenuMain.prototype = {
		constructor : MenuMain,
		/**
		 * 初始化函数
		 */
		index : function() {
			extend.init();
			var self = this;
			self.$grid=$('#menu_grid');
			self.$dialog=$('#win');
			//关闭事件
			var close=function(){
				self.$dialog.dialog("close");
			};
			//窗口底部按钮
			var footButtons=[{
					text:'保存',
					handler:function(){
						self.$form.submit();
					}
				},{
					text:'取消',
					handler:function(){close();}
				}];
			var toolbars=[{
				iconCls: 'icon-add',
				text:'新增',
				handler: function(){
					var selected=self.$grid.treegrid('getSelected');
					var parentId=0;
					if(selected){
						parentId=selected.menuId;
					}
					self.$dialog.dialog({
						title:'新建菜单',
						width:width,
						height:height,
						href:editUrl,
						queryParams:{parentId:parentId},
						buttons:footButtons,
						minimizable:true,
						maximizable:true,
						resizable:true
					}); 
//					self.$dialog.dialog("center");
				}
			},'-',{
				iconCls: 'icon-edit',
				text:'编辑',
				handler: function(){
					var selected=self.$grid.treegrid('getSelected');
					if(!selected){
						$.messager.alert('提示','请选择行','warning');
						return;
					}
					self.$dialog.dialog({
						title:'编辑菜单',
						width:width,
						height:height,
						href:editUrl,
						queryParams:{menuId:selected.menuId},
						buttons:footButtons,
						minimizable:true,
						maximizable:true,
						resizable:true
					}); 
					self.$dialog.dialog("center");
				}
			},'-',{
				iconCls: 'icon-remove',
				text:'删除',
				handler: function(){
					var rows=self.$grid.treegrid('getSelections');
					if(!rows.length>0){
						$.messager.alert('提示','请选择行','warning');
						return false;
					}
					var ids=new Array;
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].menuId);
					}
					$.messager.confirm('提示','确认删除所选菜单吗？',function(r){
					    if (r){
					    	$.post(delUrl,{ids:ids},function(resp){
					    		if(resp.meta.success){
					    			self.$grid.treegrid('reload');
					    			self.$grid.treegrid('unselectAll');
					    		}
							},'json');
					    }
					});
						
					
				}
			}];
			
			//xuying.chen	判断是否显示操作按钮,jsp有使用shiro控制标记
			if(!$("#toolbarsFlag").length){
				toolbars = [{}];
			}
			
			self.$grid.treegrid({
			    url:menuUrl,
			    rownumbers: true,
			    fit: true,
			    pagination:true,
			    pageList: [1,10,20],
			    pageNumber:1,
				pageSize:20,
			    idField:'menuId',
			    treeField:'menuName',
			    toolbar: toolbars,
			    columns:[[
			        {title:'菜单',field:'menuName',width:180},
			        {title:'URL',field:'menuUrl',width:180}
			    ]],
			    onBeforeLoad: function(row,param){
                    if (!row) {    // load top level rows
                        param.id = 0;    // set id=0, indicate to load new page rows
                        param.isRoot=true;
                    }
                },
                ctrlSelect:true,
                singleSelect:false
			});
		},
		
		edit:function(){
			var self = this;
			var $combotree=$('input[name=parentId]');
			self.$form=$('#menu_form');
			$combotree.combotree({
				icons: [{
					iconCls:'icon-clear',
					handler: function(e){
						$combotree.combotree('clear');
					}
				}],
			    url: menuTreeUrl
			});
			self.$form.form({
                success:function(resp){
                	resp=$.parseJSON(resp);
                	if(resp.meta.success){
	                    $.messager.alert('提示', "保存成功", 'info',function(){
	                    	self.$dialog.dialog("close");
	                    	self.$grid.treegrid('reload');
	                    });
                	}else{
                		 $.messager.alert('错误', "保存失败", 'error');
                	}
                }
            });
		}
	}

	module.exports = new MenuMain();
})