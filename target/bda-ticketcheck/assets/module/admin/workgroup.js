/**
 * 用户管理界面控制
 */
define(function(require, exports, module) {

	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
//	var admin = require("module/admin/js/admin.js");//父框架调用
	var utils = require("module/base/utils.js"); // 工具
	//连接配置
	var getWrokgroupUrl = "admin/workgroup/getGroups";
	var dialogUrl = "admin/workgroup/openDialog";
	var addUrl = "admin/workgroup/add";
	var delUrl = "admin/workgroup/delete";
	var editUrl = 'admin/workgroup/edit';
	var userRoleUrl = "datamanage/userDetail/getUserRole";
	var resetUserUrl = "datamanage/userDetail/resetUser";
	var url;
	var width='600px';
	var height='300px';

	var $oldPas;
	var $firstPas;
	var $newPas;


	var Workgroup =function(){
		this.$grid;
		this.$dialog;
		this.$form;
		this.$toolbar;
		this.$searchbox;
		this.$roleTypeGrid;
		this.$orgId;
	}

	Workgroup.prototype = {
		constructor: Workgroup,
		/**
		 * 初始化函数
		 */
		init: function() {
			extend.init();
			var self = this;
			self.$grid=$('#workgroupGrid');
			self.$dialog=$('#workgroupEditWindow');
			self.$toolbar=$("#workgroupGridBar");
			self.$form=$("#groupForm");
			self.$searchbox=self.$toolbar.find(".js-searchbox");
			//表头
			var dgColumns = [[
				{field: "groupId",title: "班组ID",width: 150,hidden:true},
				{field: "groupName",title: "班组名称",width: 150},
				{field: 'orgName',title: '班组所属区域',width: 100},
				{field: 'updateUserName',title: '更新者姓名',width: 100},
				{field: 'updateTime',title: '更新时间',width: 130},
				{field: 'status',title: '状态',width: 100,
					formatter: function(val, row, index){
						if(val == '1') return "有效";
							else if(val == '0') return "无效";
					}	
				}
			]];
			
			self.$grid.datagrid({
				url : getWrokgroupUrl,
				columns : dgColumns,
				toolbar : self.$toolbar,
				rownumbers : true,
				fit : true,
//				fitColumns:true,
				pagination : true,
				pageList : [ 10,20,50,100 ],
				pageNumber:1,
				pageSize:20,
				ctrlSelect:true,
                singleSelect:false,
                onDblClickRow : function(index,row) {
    				self.$dialog.dialog({
    					title:'编辑班组',
    					width:width,
    					height:height,
    					href:dialogUrl,
    					queryParams:{groupId:row.groupId},
    					buttons:footButtons,
    					minimizable:true,
    					maximizable:false,
    					resizable:true,
    					onClose : function() {
                            $(this).dialog('destroy');
                        }
    				}); 
				}
			});
			self.initEleEvent();
			
			self.$searchbox.searchbox({
			    searcher:function(value,name){
			    	//模糊搜索keyword
			    	//返回结果（分页）刷新列表
			    	self.doSearch(value);

			    }
			});
			self.$searchbox.textbox('addClearBtn', 'icon-clear');//添加清空搜索框按钮
			//点击搜索框清空按钮搜索（全部）
			$('a.icon-clear').bind('click', function() {
				self.doSearch('');
			});
			//输入框内容即时改变时对关键词进行搜索
			self.$searchbox.searchbox('textbox').bind('keyup', function() {
				var keyword =$(this).val();
				self.doSearch(keyword);
			});
		},
		doSearch : function(value) {
			var options = $('#workgroupGrid').datagrid('getPager').data("pagination").options;  
	    	var pageNum = options.pageNumber;  
	    	var pageSize = options.pageSize;  
	    	$('#workgroupGrid').datagrid('load',{
	    		"groupName":value,
    			"page":pageNum,
    			"rows":pageSize
	    	});
		},
		
		initEleEvent:function(){
			var self = this;
			//窗口底部按钮
			var footButtons=[{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						self.$form.form('submit',{
							url : url,
							onSubmit : function() {
								if(self.$form.form("validate"))
									return true;
								else 
									return false;
							},
							success : function(resp) {
								resp = $.parseJSON(resp);
								if(resp.meta.success){
									$.messager.alert('提示', "保存成功", 'info',function(){
										self.$dialog.dialog("destroy");
										self.$grid.datagrid('reload');
									});
								}else{
									$.messager.alert('错误', "保存失败"+resp.meta.code, 'error');
								}
							}
						});
					}
				},{
					text:'取消',
					handler:function(){self.$dialog.dialog("destroy");}
				}];
			
			
			self.$toolbar.on("click",".bda-btn-add",function(){
				self.$dialog.dialog({
					title:'新建班组',
					width:width,
					height:height,
					href:dialogUrl,
					queryParams:{insertFlag:true},
					buttons:footButtons,
					minimizable:true,
					maximizable:false,
					resizable:true,
					onClose : function() {
                        $(this).dialog('destroy');
                    }
				}); 
				return false;
			})
			.on("click",".bda-btn-edit",function(){
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				self.$dialog.dialog({
					title:'编辑班组',
					width:width,
					height:height,
					href:dialogUrl,
					queryParams:{insertFlag:false,groupId:selected.groupId},
					buttons:footButtons,
					minimizable:true,
					maximizable:false,
					resizable:true,
					onClose : function() {
                        $(this).dialog('destroy');
                    }
				}); 
				return false;
			})
			.on("click",".bda-btn-del",function(){
				var rows=self.$grid.datagrid('getSelections');
				if(!rows.length>0){
					$.messager.alert('提示','请选择班组','warning');
					return false;
				}
				var ids=new Array();
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].groupId);
				}
				$.messager.confirm('请确认','删除所选班组吗？',function(r){
					if (r){
						$.post(delUrl,{"ids[]":ids},function(resp){
							resp=$.parseJSON(resp);
							if(resp.meta.success){
								self.$grid.datagrid('reload');
							}else {
								if(undefined!=resp.meta.code || null!=resp.meta.code){
									$.messager.alert('错误', resp.meta.code, 'error');
								}else{
									$.messager.alert('提示', "删除失败", 'error')
								}
								
							}
						});
					}
				});
				return false;
			}).on("click",".bda-btn-imp",function(){
				self.$dialog.dialog({
					title:'班组信息批量导入/修改',
					width:width,
					height:height,
					href:"datamanage/userDetail/openImp",
					buttons:[{
						text:'导入',
						iconCls:'icon-ok',
						handler:function(){
							$('#import_userDetail_form').form('submit',{
								url : "datamanage/userDetail/importUserDetail",
								onSubmit : function() {
									if($('#import_userDetail_form').form("validate")){
										parent.$.messager.progress({
										    title : '提示',
										    text : '数据处理中，请稍后....'
										});
										return true;
									}
									else 
										return false;
								},
								success : function(resp) {
									resp = $.parseJSON(resp);
									if(resp.meta.success){
										parent.$.messager.progress('close');
										$.messager.alert('提示', "导入成功", 'info',function(){
											self.$dialog.dialog("destroy");
											self.$grid.datagrid('reload');
										});
									}else{
										parent.$.messager.progress('close');
										$.messager.alert('错误', "导入失败，请检查导入数据正确性！", 'error');
									}
								}
							});
						}
					},{
						text:'取消',
						handler:function(){self.$dialog.dialog("destroy");}
					}],
					minimizable:true,
					maximizable:true,
					resizable:true,
					onClose : function() {
                        $(this).dialog('destroy');
                    }
				}); 
				return false;
			});
			
		},//end initEleEvent
		
		edit:function(){
			var self = this;
			self.$form=$('#groupForm');
			var tmpOrgId = $('#tmpOrgId').val();
			var groupId = $('#groupId').val();
			var orgComboOption = {
					required:true,
					panelHeight:'auto',
					panelMaxHeight:250,
					editable:false,
					valueField:'value',
					textField:'text',
					url:'datamanage/org/getAreaCombo'
			}
			
			if(groupId != '') {
				url = editUrl;
				orgComboOption.onLoadSuccess = function () {  
					$(this).combobox("select",tmpOrgId);  
                }
			}else{
				url = addUrl;
			}
			$('#orgId').combobox(orgComboOption);
		}
		
		
	}

	module.exports = new Workgroup();
})