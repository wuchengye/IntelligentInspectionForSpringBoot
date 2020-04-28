/**
 * 用户管理界面控制
 */
define(function(require, exports, module) {

	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
//	var admin = require("module/admin/js/admin.js");//父框架调用
	var utils = require("module/base/utils.js"); // 工具
	//连接配置
	var userUrl = "usermanage/user/getUsers";//var userUrl = "admin/user/getUsers";
	var editUrl = "usermanage/user/edit";//var editUrl = "admin/user/edit";
	var saveUrl = "usermanage/user/save";//这个还没有用到
	var delUrl = "usermanage/user/del";//var delUrl = "admin/user/del";
	var userRoleUrl = "usermanage/user/getUserRole";//var userRoleUrl = "admin/user/getUserRole";
	var resetUserUrl = "usermanage/user/resetUser";
	var width='600px';
	var height='300px';

	var $oldPas;
	var $firstPas;
	var $newPas;


	var UserMain =function(){
		this.$grid;
		this.$dialog;
		this.$form;
		this.$toolbar;
		this.$searchbox;
		this.$roleTypeGrid;
		this.$orgId;
	}

	UserMain.prototype = {
		constructor: UserMain,
		/**
		 * 初始化函数
		 */
		init: function() {
			extend.init();
			var self = this;
			self.$grid=$('#userGrid');
			self.$dialog=$('#userEditWindow');
			self.$toolbar=$("#userGridBar");
			self.$form=$("#userForm");
			self.$searchbox=self.$toolbar.find(".js-searchbox");
			//表头
			var dgColumns = [[
			      {field: "account",title: "用户账号",width: 150},
			      {field: 'userName',title: '用户姓名',width: 100},
			      {field: 'status',title: '状态',width: 100,
			    	  formatter: function(val, row, index){
			    		 if(val == '1') return "正常";
			    		 else if(val == '2') return "停用";
			    		 else if(val == '3') return "作废删除";
			    	  }	
			      },
			      /*{field: 'userRoom',title: '用户科室',width: 100,
			    	  formatter: function(val, row, index){
			    		  	return row.userDetail.userRoom;
			    	  }	
			      },
			      {field: 'userGroup',title: '用户班组',width: 100,
			    	  formatter: function(val, row, index){
					  		return row.userDetail.userGroup;
			    	  }	
			      },
			      {field: 'orgName',title: '用户所属组织',width: 100},*/
			]];
			
			self.$grid.datagrid({
				url : userUrl,
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
                singleSelect:false
			});
			self.initEleEvent();
			
			self.$searchbox.searchbox({
			    searcher:function(value,name){
			    	//模糊搜索keyword
			    	//返回结果（分页）刷新列表
			    	//分页是个问题
			    	var options = $('#userGrid').datagrid('getPager').data("pagination").options;  
			    	var page = options.pageNumber;  
			    	var pageSize = options.pageSize;  
			    	$('#userGrid').datagrid('load',{
			    		"keyword":value,
		    			"page":page,
		    			"rows":pageSize
			    	});

			    }
			});
			self.$searchbox.textbox('addClearBtn', 'icon-clear');//添加
		},
		
		initEleEvent:function(){
			var self = this;
			//窗口底部按钮
			var footButtons=[{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						self.$form.submit();
					}
				},{
					text:'取消',
					handler:function(){self.$dialog.dialog("close");}
				}];
			
			
			self.$toolbar.on("click",".bda-btn-add",function(){
				self.$dialog.dialog({
					title:'新建用户',
					width:width,
					height:height,
					href:editUrl,
					queryParams:{},
					buttons:footButtons,
					minimizable:true,
					maximizable:true,
					resizable:true
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
					title:'编辑用户',
					width:width,
					height:height,
					href:editUrl,
					queryParams:{userId:selected.userId},
					buttons:footButtons,
					minimizable:true,
					maximizable:true,
					resizable:true
				}); 
				return false;
			})
			.on("click",".bda-btn-del",function(){
				var rows=self.$grid.datagrid('getSelections');
				if(!rows.length>0){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				var ids=new Array();
				var accounts=new Array();//	2017.3.3xuying.chen 批量删除同时批量传递accounts
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].userId);
					accounts.push(rows[i].account);
				}
				$.messager.confirm('请确认','删除所选用户吗？',function(r){
					if (r){
						$.post(delUrl,{"ids[]":ids,"accounts[]":accounts},function(resp){
							resp=$.parseJSON(resp);
							if(resp.meta.success){
								self.$grid.datagrid('reload');
							}else {
								$.messager.alert('提示', "删除失败", 'info')
							}
						});
					}
				});
				return false;
			}).on("click",".bda-btn-editPas",function(){
				var rows=self.$grid.datagrid('getSelections');
				if(!rows.length>0){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				var ids=new Array();
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].userId);
				}
				$.messager.confirm('请确认','确认重置所选用户密码吗？',function(r){
					if (r){
						$.post(resetUserUrl,{"ids[]":ids},function(resp){
							resp=$.parseJSON(resp);
							if(resp.meta.success){
								$.messager.alert('提示', "成功把用户密码重置成初始密码（sjs@1234）", 'info')
								self.$grid.datagrid('reload');
							}else {
								$.messager.alert('提示', "重置失败", 'warning')
							}
						});
					}
				});
				
				/*var editPasFootBtn = [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						$oldPas = $('#oldPas');
						$oldPas.val($.sha256($oldPas.val()));
						$firstPas = $('#firstPas');
						$newPas = $('#newPas');
						if($firstPas.val() == $newPas.val()){
							var psd = $.sha256($firstPas.val());
							$firstPas.val(psd);
							$newPas.val(psd);
							$('#userPasForm').form({
								queryParams : {indexEditPasFlag:"yes"}
							});
							$('#userPasForm').submit();
						}else{
							$.messager.alert('提示','两次输入不一致,请重新输入','warming',function(){
								$oldPas.val("");
								$firstPas.val("");;
								$newPas.val("");
								$firstPas.focus();									
							});

						}
					}
				},{
					text:'取消',
					handler:function(){self.$dialog.dialog("close");}
				}];
				
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				self.$dialog.dialog({
					title:'重置密码',
					width:width,
					height:height,
					href:editUrl,
					queryParams:{userId:selected.userId,editPasFlag:"yes"},
					buttons:editPasFootBtn,
					minimizable:true,
					maximizable:true,
					resizable:true
				}); */
				return false;
			}).on("click",".bda-btn-imp",function(){
				self.$dialog.dialog({
					title:'员工信息批量导入/修改',
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
											self.$dialog.dialog("close");
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
						handler:function(){self.$dialog.dialog("close");}
					}],
					minimizable:true,
					maximizable:true,
					resizable:true
				}); 
				return false;
			});
		},//end initEleEvent
		
		edit:function(){
			var self = this;
			self.$form=$('#userForm');
			var userId = document.getElementsByName("userId")[0].getAttribute("value");
			self.$roleTypeGrid = $('#roleTypeGrid');
			
			var dgColumns = [[
			    {field:"checkbox",checkbox:true},
			    {field: 'roleId',title: '角色ID：',width: 100,hidden:true}, 
				{field: 'roleName',title: '角色名称：',width: 100}, 
				{field: "description",title: "描述：",width: 100},
			]];
			self.$form.form({
				onSubmit : function() {
					if(self.$form.form("validate")){
						parent.$.messager.progress({
						    title : '提示',
						    text : '保存中，请稍后....'
						});
						return true;
					}else{
						$.messager.alert('提示', "请输入必填项！", 'info');
						return false;
					}
				},
                success:function(resp){
                	resp=$.parseJSON(resp);
                	if(resp.meta.success){
                		parent.$.messager.progress('close');
                		self.$dialog.dialog("close");
                		$.messager.show({
							title:'提示',
							msg:'保存成功！',
							timeout:2000,
							showType:'fade',
							style:{
							}
						});
                		self.$grid.datagrid('reload');
	                    //$.messager.alert('提示', "保存成功", 'info',function(){
	                    //});
                	}else{
                		parent.$.messager.progress('close');
                		 $.messager.alert('错误', "保存失败!<br/>"+resp.meta.code, 'error');
                	}
                }			
            });
			self.$roleTypeGrid.combogrid({       
			    idField:'roleId',    
			    textField:'roleName',    
			    url:userRoleUrl,    
			    columns: dgColumns,
			    fitColumns:true,
			    checkbox : true,
			    singleSelect:false,
			    multiple: true,
			    editable:false,
				resizeHandle:'both',
				queryParams:{
					"userId":userId,
                	"page":1,
                	"rows":999999
                },
				onLoadSuccess:function(data){
					self.$roleTypeGrid.combogrid('setValues',data.userRoleIds)
				}
//			    mode:'remote'
			}); 
			
			//新增用户组织选项
			self.$orgId = $('#orgId');
			self.$orgId.combotree({  
//				url: 'datamanage/org/getOrgTree',
				onSelect: function(node){
					$("#orgId").val(node.id);
				},
				onLoadSuccess:function(node,data){ 
					var t = self.$orgId.combotree('tree');//获取tree  
					for (var i=0;i<data.length ;i++ ){
						node= t.tree("find",data[i].id);  
						if($("#orgId").val()==data[i].id){
							t.tree('select', node.target);//node为要选中的节点
						}
						
			         }  
				}
			});
			
			var $account = $('#weaccount');
			if($account.val() != ""){
				$account.textbox({"disabled":true});
				self.$orgId.combotree('readonly', true);
			}else{
				$account.textbox({"disabled":false});
				
			}

//远程验证用户账号唯一性
			
/*			var $account = $('input[name="account"]');
			$account.blur(function(){
				var account = $account.val();
				if(account != ""){
					$.ajax({
						type:'post',
						data:{"account" : account},
						url:"admin/user/cheakAccount",
						success:function(result){
							result = $.parseJSON(result);
							if(!result){
								 $.messager.alert('提示', "账号已被注册，请重新输入", 'warning');
								 $account.val("");
							}
						}
					})
				}				
			})*/
			
			
			$("#status").combobox({
				valueField: 'status',
				textField: 'value',
				data: [{
					status: '1',
					value: '正常'
				},{
					status: '2',
					value: '停用'
				},{
					status: '3',
					value: '作废删除'
				}]
			});
		},
		
		editPas : function(){
			extend.init();
			var self = this;
			$userPasForm=$('#userPasForm');
			$userPasForm.form({
				onSubmit:function(){
					//console.log($('#roleTypeGrid').text());
				},
                success:function(resp){
                	resp=$.parseJSON(resp);
                	if(resp.meta.success){
	                    $.messager.alert('提示', "保存成功", 'info',function(){
	                    	self.$dialog.dialog("close");
	                    	self.$grid.datagrid('reload');
	                    })
                	}else{
                		 $.messager.alert('错误', "保存失败", 'error');
                		 	$oldPas.textbox('setValue','');
							$firstPas.textbox('setValue','');
							$newPas.textbox('setValue','');
							$firstPas.focus();
                	}
                }			
            });
		},
		
		importInit : function(){
			//初始化角色下拉框
			$("#roleTypeGrid").combogrid({       
			    idField:'roleId',    
			    textField:'roleName',    
			    url:"datamanage/userDetail/getRoleByCurrentUser",    
			    columns: [[
               			    {field:"checkbox",checkbox:true},
            			    {field: 'roleId',title: '角色ID：',width: 100}, 
            				{field: 'roleName',title: '角色名称：',width: 100}, 
            				{field: "description",title: "描述：",width: 100},
            			]],
			    fitColumns:true,
			    checkbox : true,
			    singleSelect:false,
			    multiple: true,
			    editable:false,
				resizeHandle:'both',
				queryParams: {
                	"page":1,
                	"rows":999999
                }
			}); 
		}
		
	}

	module.exports = new UserMain();
})