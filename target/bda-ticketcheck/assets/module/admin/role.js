/**
 * 角色界面控制
 */


//所有变化过的权限id都登记在这个数组中
var authChangeIndexs = {
		menuAuthGrid:[],
		orgAuthGrid:[],
		tagAuthGrid:[],
		btnAuthGrid:[]
};

//模块化区
define(function(require,exports,module){
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js");
	var utils = require("module/base/utils.js"); // 工具
	var roleUrl = "rolemanage/role/getRoles";
	var editUrl = "rolemanage/role/edit";
	var editPermsUrl="rolemanage/role/editPerms"
//editRole
	var delUrl = "rolemanage/role/del";
	
//editUserRole
	var getUsersRoleUrl = "rolemanage/role/searchUsers";
	var updateUserRoleUrl = "rolemanage/role/updateUsers";
//editAuth
	var menuUrl = "admin/auth/loadRoleAuth";
	var orgUrl = "admin/org/loadOrgAuth";
	var tagUrl = "tag/loadTagAuth";
	var reportUrl = "admin/auth/loadReportAuth"
	var tagTreeUrl = "tag/getTagTree";
	var getAuthUrl = "admin/role/getResources";
	var btnUrl = "admin/auth/loadBtnAuth";
	var curEditRoleId;
	var width='500px';
	var height='230px';
	var editingId;
	var RoleMain =function(){
		this.$grid;
		this.$dialog;
		this.$form;
		this.$toolbar;
		this.$searchBox;
		this.$authTab;
		this.$menuAuthGrid;
		this.$reportAuthGrid;
		this.$tagAuthGrid;
		this.$userRoleGrid;
		this.$userRoleSearch;
		this.$userRoleGridBar;
	}
		
	RoleMain.prototype = {
		constructor:RoleMain,
		/**
		 * 初始化函数
		 */
		index : function(){
			var self = this;
			extend.init();
			self.$grid=$('#roleGrid');
			self.$dialog=$('#editWindow');
			self.$permsDialog=$('#permsWin');
			self.$toolbar=$("#roleGridBar");
			self.$form=$("#authRoleForm");
			self.$searchBox = $("#roleSearch");
//			角色列表头
			var dgColumns = [[
			      {field: 'roleName',title: '角色名称',width: 130}, 
			      {field: "description",title: "描述",width: 170},
			      /*{field: 'createTime',title: '创建时间',width: 135,
			           formatter: function(val, row, index) {return utils.formatDate("yyyy-MM-dd hh:mm:ss", new Date(val));}
			       }*/
			 ]];
			
//			搜索框
			self.$searchBox.searchbox({
			    searcher:function(value,name){
			    	self.$grid.datagrid("load",{"keyword":value})
			    }
			});
			self.$searchBox.textbox('addClearBtn', 'icon-clear');//添加关闭小标签
			
//			表格初始化
			self.$grid.datagrid({
				url : roleUrl,
				columns : dgColumns,
				toolbar : self.$toolbar,
				rownumbers : true,
			    fit : true,
//				fitColumns:true,
				pagination : true,
				pageSize:20,
				pageList : [ 10, 20, 50 ,100 ],
				ctrlSelect:true,
                singleSelect:false
			});
			self.initEleEvent();
		},//end init

//事件初始化
//窗口底部按钮
		initEleEvent:function(){
			var self = this;

			var footButtons=[{
					text:'保存',
					handler:function(){
						self.$form.submit();
					}
				},{
					text:'取消',
					handler:function(){
						self.$dialog.dialog("close");
					}
				}];
			var authFootButtons=[{
				text:'保存',
				handler:function(){ 
					var roleId = document.getElementsByName("role")[0].value;
					var rows =[];
					for(var i in authChangeIndexs.menuAuthGrid){
						rows.push($("#menuAuthGrid").treegrid('find',authChangeIndexs.menuAuthGrid[i]));
						rows[rows.length-1].name="Menu";
					}
					for(var i in authChangeIndexs.tagAuthGrid){
						rows.push($("#tagAuthGrid").treegrid('find',authChangeIndexs.tagAuthGrid[i]));
						rows[rows.length-1].name="TagIndex";
					}
					for(var i in authChangeIndexs.orgAuthGrid){
						rows.push($("#orgAuthGrid").treegrid('find',authChangeIndexs.orgAuthGrid[i]));
						rows[rows.length-1].name="Organization";
					}
					for(var i in authChangeIndexs.btnAuthGrid){
						rows.push(authChangeIndexs.btnAuthGrid[i]);
					}
					//记得清洗全局变量数组
					//每次提交都清洗监听更新的变量
					authChangeIndexs.menuAuthGrid.length = 0;
					authChangeIndexs.tagAuthGrid.length = 0;
					authChangeIndexs.orgAuthGrid.length = 0;
					authChangeIndexs.btnAuthGrid.length = 0;
					for (var i = 0; i < rows.length; i++) {
						if (rows[i].isNew === undefined  || !rows[i].isNew) {
							rows.splice(i,1);
							i--;
						}
						rows[i].roleId = roleId;
					}	
					//rows最后是已勾选的行
					$.messager.alert('提示','修改成功','info');
					
					self.$dialog.dialog("close");
				}
			},{
				text:'取消',
				handler:function(){self.$dialog.dialog("close");}
			}];
			
			var userRolrEditFootBtn=[{
				text:'保存',
				handler:function(){
					var roleId = $("input[name='role']").attr("value");
					var rows = self.$userRoleGrid.datagrid("getRows");
					var checkedRows=self.$userRoleGrid.datagrid("getChecked");
					var checkedUserIds=[];
					var addIds=[];
					var delIds=[];
					$(checkedRows).each(function(index,row){
						checkedUserIds.push(row.userId);
					});
					$(rows).each(function(index,row){
						if(checkedUserIds.indexOf(row.userId)!=-1){//如果在勾选列中
							if(!row.assigned){//如果没有被分配，新增的用户
								addIds.push(row.userId);
							}
						}else if(row.assigned){//没有包含在勾选列表,并且是已经分配的用户
							delIds.push(row.userId);
						}
					});
					$.post(updateUserRoleUrl,{'roleId':roleId,addUserIds:addIds,delUserIds:delIds},function(resp){
						if (resp.meta.success) {
							$.messager.alert('提示', "保存成功", 'info', function() {
								self.$dialog.dialog("close");
								self.$grid.datagrid('reload');
							});
						} else {
							$.messager.alert('错误', "保存失败", 'error');
						}
					},'json');
				}
			},{
				text:'取消',
				handler:function(){self.$dialog.dialog("close");}
			}];
		
			self.$toolbar.on("click",".bda-btn-add",function(){ 
				self.$dialog.dialog({
					title:'新建角色',
					width:width,
					height:height,
					href:editUrl,
					queryParams:{},
					onClose : function() {
                        $(this).dialog('destroy');
                    },
					buttons:footButtons,
					minimizable:true,
					maximizable:true,
					resizable:true					
				}); 
				self.$dialog.dialog("center");
				return false;
			})
			.on("click",".bda-btn-edit",function(){
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				self.$dialog.dialog({
					title:'编辑角色',
					width:width,
					height:height,
					href:editUrl,
					queryParams:{roleId:selected.roleId},
					buttons:footButtons,
					onClose : function() {
                        $(this).dialog('destroy');
                    },
					minimizable:true,
					maximizable:true,
					resizable:true
				}); 
				self.$dialog.dialog("center");
				return false;
			})
			.on("click",".bda-btn-del",function(){
				var rows=self.$grid.datagrid('getSelections');
				if(!rows.length>0){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				var ids=new Array();
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].roleId);
				}
				$.messager.confirm('请确认','删除所选角色吗？',function(r){
				    if (r){
				    	$.ajax({
				    		url:delUrl,
				    		data:{"ids[]":ids},
				    		type:'post',
				    		dataType:'json',
				    		success:function(resp){
			                	if(resp.meta.success){
					    			self.$grid.datagrid('reload');
					    		}else {
					    			$.messager.alert('提示', "删除失败", 'info')
					    		}
				    		}
						});
				    	//同时异步删除角色和权限关系数据
				    	$.ajax({
				    		url:"datamanage/userDetail/delRoleAndPer",
				    		data:{"ids[]":ids},
				    		type:'post',
				    		dataType:'json'
						});
				    }
				});
				return false;
			})
			.on("click",".bda-btn-editAuth",function(){
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				self.$permsDialog.dialog({
					title:'分配页面',
					width:'80%',
					height:'80%',
					href:editPermsUrl,
					queryParams:{roleId:selected.roleId,name:"editAuth",id:''},
//					buttons:authFootButtons,
					minimizable:true,
					maximizable:true,
					resizable:true,
				}); 
				return false;
			})
			.on("click",".bda-btn-editPermis",function(){
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				self.$permsDialog.dialog({
					title:'修改权限',
					width:'80%',
					height:'80%',
					href:'admin/permission/editPermission',
					queryParams:{roleId:selected.roleId},
//					buttons:authFootButtons,
					minimizable:true,
					maximizable:true,
					resizable:true,
				}); 
				return false;
			})
			.on("click",".bda-btn-editUserRold",function(){
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				self.$dialog.dialog({
					title:'分配用户',
					width:'80%',
					height:'80%',
					href:editUrl,
					queryParams:{roleId:selected.roleId,name:"editUserRole",id:''},
					buttons:userRolrEditFootBtn,
					minimizable:true,
					maximizable:true,
					resizable:true,
				}); 
				self.$dialog.dialog("center");
				return false;
			});
		},//end initEleEvent

//edit页面初始化
		editRole : function() {
			var self = this;
			self.$form=$("#authRoleForm");
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
				success : function(resp) {
					resp = $.parseJSON(resp);
					if (resp.meta.success) {
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
//						$.messager.alert('提示', "保存成功", 'info', function() {
//						});
					} else {
						$.messager.alert('错误', "保存失败", 'error');
					}
				}
			});
		},
		
		editAuth:function(){
			var self = this;
			var roleId = $("input[name='role']").attr("value");
			var menuColumns = [
			                   	[
			                   	 {
			                   		 title:'菜单目录',
			                   		 field:'menuName',
			                   		 width:'150px'
			                   	 },{
			                   		 title:'可见',
			                   		 field:'view',
			                   		 width:'50px',
			                   		 height:'25px',
			                   		 formatter: function(value,row,index){	
			                   			var actions = row.actions;
			                   			var rowId = row.menuId;
			                   			var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="view" data-gridId="menuAuthGrid" data-rowId="'+rowId+'" ';
			                   			if (actions != 'undefined') {
			                   				for(var i in actions){
			                   					if(actions[i] == 'view'){
			                   						return authCheckBox + 'checked="checked" />';
			                   					}			
			                   				}			                   				
			                   			}
		                   				return authCheckBox +'/>';          						                   			
			                   		 }
			                   	 },{
			                   		 title:'编辑',
			                   		 field:'edit',
			                   		 width:'50px',
			                   		 height:'25px',
			                   		 formatter: function(value,row,index){
			                   			var actions = row.actions;
			                   			var rowId = row.menuId;
			                   			var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="edit" data-gridId="menuAuthGrid" data-rowId="'+rowId+'" ';
			                   			if (actions != 'undefined') {
			                   				for(var i in actions){
			                   					if(actions[i] == 'edit'){
			                   						return authCheckBox + 'checked="checked" />'
			                   					}			
			                   				}			                   				
			                   			}
		                   				return authCheckBox +'/>';         						                   			
			                   		 }
			                   	 }
			                   	]
			                  ];
			
			var tagColumns = [
			                   	[
			                   	 {
			                   		 title:'标签目录',
			                   		 field:'text',
			                   		 width:'150px'
			                   	 },{
			                   		 title:'可见',
			                   		 field:'view',
			                   		 width:'50px',
			                   		 height:'25px',
			                   		 formatter: function(value,row,index){	
			                   			var actions = row.actions;
			                   			var rowId = row.tagId;
			                   			var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="view" data-gridId="tagAuthGrid" data-rowId="'+rowId+'" ';
			                   			if (actions != 'undefined') {
			                   				for(var i in actions){
			                   					if(actions[i] == 'view'){
			                   						return authCheckBox + 'checked="checked" />';
			                   					}			
			                   				}			                   				
			                   			}
		                   				return authCheckBox +'/>';          						                   			
			                   		 }				               
			                   	 },{
			                   		 title:'编辑',
			                   		 field:'edit',
			                   		 width:'50px',
			                   		 height:'25px',
			                   		 formatter: function(value,row,index){
			                   			var actions = row.actions;
			                   			var rowId = row.tagId;
			                   			var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="edit" data-gridId="tagAuthGrid" data-rowId="'+rowId+'" ';
			                   			if (actions != 'undefined') {
			                   				for(var i in actions){
			                   					if(actions[i] == 'edit'){
			                   						return authCheckBox + 'checked="checked" />'
			                   					}			
			                   				}			                   				
			                   			}
		                   				return authCheckBox +'/>';         						                   			
			                   		 }
			                   	 }
			                   	]
			                  ];
			var reportColumns = [
			                  [
			                   {
			                	   title:'案例目录',
			                	   field:'text',
			                	   width:'150px'
			                   },{
			                	   title:'可见',
			                	   field:'view',
			                	   width:'50px',
			                	   height:'25px',
			                	   formatter: function(value,row,index){	
			                		   var actions = row.actions;
			                		   var rowId = row.tagId;
			                		   var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="view" data-gridId="reportAuthGrid" data-rowId="'+rowId+'" ';
			                		   if (actions != 'undefined') {
			                			   for(var i in actions){
			                				   if(actions[i] == 'view'){
			                					   return authCheckBox + 'checked="checked" />';
			                				   }			
			                			   }			                   				
			                		   }
			                		   return authCheckBox +'/>';          						                   			
			                	   }				               
			                   },{
			                	   title:'编辑',
			                	   field:'edit',
			                	   width:'50px',
			                	   height:'25px',
			                	   formatter: function(value,row,index){
			                		   var actions = row.actions;
			                		   var rowId = row.tagId;
			                		   var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="edit" data-gridId="reportAuthGrid" data-rowId="'+rowId+'" ';
			                		   if (actions != 'undefined') {
			                			   for(var i in actions){
			                				   if(actions[i] == 'edit'){
			                					   return authCheckBox + 'checked="checked" />'
			                				   }			
			                			   }			                   				
			                		   }
			                		   return authCheckBox +'/>';         						                   			
			                	   }
			                   }
			                   ]
			                  ];
			
			var orgColumns = [
			                   	[
			                   	 {
			                   		 title:'机构目录',
			                   		 field:'orgName',
			                   		 width:180
			                   	 },{
			                   		 title:'可见',
			                   		 field:'view',
			                   		 width:'50px',
			                   		 height:'25px',
			                   		 formatter: function(value,row,index){	
			                   			var actions = row.actions;
			                   			var rowId = row.orgId;
			                   			var authCheckBox = '<input type="checkbox" onClick="authCheckBoxClick(this)" data-field="view" data-gridId="orgAuthGrid" data-rowId="'+rowId+'" ';
			                   			if (actions != 'undefined') {
			                   				for(var i in actions){
			                   					if(actions[i] == 'view'){
			                   						return authCheckBox + 'checked="checked" />';
			                   					}			
			                   				}			                   				
			                   			}
		                   				return authCheckBox +'/>';          						                   			
			                   		 }				               
			                   	 }
			                   	]
			                  ];
			
			self.$authTab=$("#authTab");
			self.$orgAuthGrid = $("#orgAuthGrid");
			self.$tagAuthGrid = $("#tagAuthGrid");
			self.$reportAuthGrid = $("#reportAuthGrid");
			self.$menuAuthGrid=$("#menuAuthGrid");
			self.$btnAuthGrid=$("#btnAuthGrid");
//			self.$tagsAuthGrid=$("#tagsAuthGrid");
			
			var $menuAuthEditToolBar=$("#menuAuthEditToolBar");
			var $orgAuthEditToolBar=$("#orgAuthEditToolBar");
			
			self.initTreeGrid(self.$menuAuthGrid,menuUrl,menuColumns,roleId,"menuId","menuName",'Menu');
			self.initTreeGrid(self.$tagAuthGrid,tagUrl,tagColumns,roleId,"tagId","text",'Tag' );
			self.initTreeGrid(self.$reportAuthGrid,reportUrl,reportColumns,roleId,"reportId","text",'Report' );
			self.initTreeGrid(self.$orgAuthGrid,orgUrl,orgColumns,roleId,"orgId","orgName",'Organization');
			self.initDataGrid(self.$btnAuthGrid);
		},
		
		initTreeGrid:function($grid,url,columns,roleId,idField,treeField,authType,toolbar){
			var self = this;
			curEditRoleId = roleId;
			$grid.treegrid({
			    url:url,
			    rownumbers: true,
			    fit: true,
			    pagination:true,
			    toolbar:toolbar,
			    pageList: [1,10,20],
			    idField : idField,
			    treeField : treeField,
			    columns: columns,
			    animate:true,
			    queryParams:{
			    	"roleId":roleId,
			    	"authType":authType
			    },
			    onBeforeLoad: function(row,param){
                    if (!row) {    // load top level rows
                    	param.id = 0;    // set id=0, indicate to load new page rows
                    }
                },
                ctrlSelect:true,
                singleSelect:false
			});
		},
		//-----------------------标签及按钮级控制---------------------------------
		initDataGrid : function($grid){
			$.post(btnUrl,{roleId:$("input[name='role']").attr("value")},function(respData){
				respData = JSON.parse(respData);
				$grid.datagrid({
					columns:[[
					          {field:'name',title:'名称',width:150,align:'center'},
//				           {field:'add',title:'增加',width:80,align:'center'},
//				           {field:'edit',title:'修改',width:80,align:'center'},
					          {field:'delete',title:'删除',width:80,align:'center'}
					          ]],
					rownumbers:true,
					singleSelect:true,
					  data:[
					       {"name":"标签",
//				    	   "add":"<input type='checkbox'  data-field='add' onClick='authDgCheckBoxClick(this)'  data-gridId='btnAuthGrid' data-name='Tag' />",
//				    	   "edit":"<input type='checkbox'  data-field='edit' onClick='authDgCheckBoxClick(this)'  data-gridId='btnAuthGrid'  data-name='Tag' />",
					         "delete":"<input type='checkbox' name='Tag' data-field='delete' onClick='authDgCheckBoxClick(this)'  data-gridId='btnAuthGrid'  data-name='Tag' />"},
					          {"name":"标签目录",
//				    		   "add":"<input type='checkbox'  data-field='add' onClick='authDgCheckBoxClick(this)'  data-gridId='btnAuthGrid'  data-name='TagIndex' />",
//				    		   "edit":"<input type='checkbox'  data-field='edit' onClick='authDgCheckBoxClick(this)'  data-gridId='btnAuthGrid'  data-name='TagIndex' />",
					           "delete":"<input type='checkbox' name='TagIndex' data-field='delete' onClick='authDgCheckBoxClick(this)'  data-gridId='btnAuthGrid'  data-name='TagIndex' />"}
					        ],
					onLoadSuccess:function(data){ 
						for(var i =0;i<respData.length;i++){
							$("input[name='"+respData[i]['delete']+"']").attr('checked',true);
						}
					}
				});
			});
		},
		
		editUserRole : function(){
			var self = this;
			self.$userRoleGridBar = $("#userRoleGridBar");
			self.$userRoleSearch = $("#userRoleSearch");
			self.$userRoleGrid = $("#userRoleGrid");
			var roleId = $("input[name='role']").attr("value");
			var userRoleColumn = [[
			    {field: "assigned",checkbox:true},
			    {field: 'account',title: '用户编号',width: 100},
			    {field: 'userName',title: '用户名称',width: 100},
			    {field: 'status',title: '状态',width: 100,
			    	formatter: function(val, row, index){
			    		 if(val == '1') return "有效";
			    		 else if(val == '2') return "无效";
			    	  }	
			    } 
			]];
			
//			用户筛选框
			self.$userRoleGridBar.find("select").combobox({
				editable:false,
				onChange:function(newValue,oldValue){
					var keyword=self.$userRoleSearch.searchbox('getValue');
					self.$userRoleGrid.datagrid("load",{"roleId":roleId,"keyword":keyword,"filter":newValue});
				}
			});
//			搜索框
			self.$userRoleSearch.searchbox({
				searcher:function(value,name){
					var filter=self.$userRoleGridBar.find("select").combobox("getValue");
					self.$userRoleGrid.datagrid("load",{"roleId":roleId,"keyword":value,"filter":filter});
				}
			});
			self.$userRoleSearch.textbox('addClearBtn', 'icon-clear');//添加关闭小标签
			self.$userRoleGrid.datagrid({
				url : getUsersRoleUrl,
				idField: "userId",
				columns : userRoleColumn ,
				toolbar : self.$userRoleGridBar,
				rownumbers : true,
			    fit : true,
			    checkOnSelect:true,
			    selectOnCheck:true,
			    onLoadSuccess:function(data){
			    	if(data){
			    		$.each(data.rows, function(index, user){
				    		if(user.assigned){
				    			self.$userRoleGrid.datagrid('checkRow', index);
				    		}
			    		});
		    		}
			    },
				pagination : true,
				pageSize:10,
				pageList : [10, 20, 50 ,100 ],
				queryParams:{
			    	"roleId":roleId
			    }
			});
			
			$("#thisRoleUserStatus").change(function(){
				var usersStaus = $(this).val();
				switch(usersStaus){
					case '全部用户'  : 
						self.$userRoleGrid.datagrid("unselectAll");
						self.$userRoleGrid.datagrid("load",{"fiterFlag":"全部用户","roleId":roleId});
						break;
					case '当前用户' : 
						self.$userRoleGrid.datagrid("load",{"fiterFlag":"当前用户","roleId":roleId});
						break;
					case '可分配用户' : 
						self.$userRoleGrid.datagrid("unselectAll");
						self.$userRoleGrid.datagrid("load",{"fiterFlag":"可分配用户","roleId":roleId});
						break;
				}
				
			})
		},
		editPerms:function(){
//			var $tab=$("#authTab");
//			var roleId=$("input[name=roleId]").val();
//			$tab.tabs();
//			base.openAjaxTab($tab, '1', "标签目录权限", 'tag/config?roleId='+roleId);
//			base.openAjaxTab($tab, '12', "标签目录权限2", 'tag/config?roleId='+roleId);
		}
	}


	module.exports = new RoleMain();
});


function authCheckBoxClick(target){ 
	var rowId = target.getAttribute("data-rowId");
	var gridId = target.getAttribute("data-gridId");
	var field = target.getAttribute("data-field");
	var row = $('#'+gridId).treegrid('find',rowId);
	if(row.children !== undefined){
	}
	if(row.actions === undefined){
		row.actions = [];
	}
	var length = row.actions.length;
	for(var i in row.actions){
		if (row.actions[i] == field) {
			row.actions.splice(i,1);
		}
	}
	if (length == row.actions.length) {
		row.actions.push(field);
	}
	if (row.isNew === undefined) {
		row.isNew = true;
		authChangeIndexs[gridId].push(rowId);
	}
	$('#'+gridId).treegrid('update',{
		id: rowId,
		row: row
	})
}
	function authDgCheckBoxClick(target){ 
//		var rowId = target.getAttribute("data-rowId");
	    var rowNum;
	    var check = $(target).is(':checked');
	    var primkey = '*'
		var gridId = target.getAttribute("data-gridId");
		var field = target.getAttribute("data-field");
		var name = 'Btn';
		var entity = target.getAttribute("data-name");
		var gridIdArr=authChangeIndexs[gridId];
		
		if(gridIdArr.length==0){
			var row = {'name':name,'entity':entity,'check':check,'isNew':true,'primkey':primkey};
			row.actions = [];
			row.actions[0]=field;
			gridIdArr.push(row);
		}else if(gridIdArr.length>0){
			var index=checkInArr();
			if(gridIdArr.length>index){
				gridIdArr[index].check=check;
			}else{
				var row = {'name':name,'entity':entity,'check':check,'isNew':true,'primkey':primkey};
				row.actions = [];
				row.actions[0]=field;
				gridIdArr.push(row);
			}
			
		//-----------------判断是否是在authChangeIndexs数组内，是则返回所在下标-----------	
			function checkInArr(){
				for(var i in gridIdArr){
					if(entity == gridIdArr[i].entity){
						return i;
					}
				}
				return authChangeIndexs.length+1;
			}
		}
		
}	