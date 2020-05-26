/**
 * 用户管理界面控制
 */
define(function(require, exports, module) {

	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
//	var admin = require("module/admin/js/admin.js");//父框架调用
	var utils = require("module/base/utils.js"); // 工具
	//连接配置
	var staffUrl = "admin/staff/getStaffs";
	var addUrl = "admin/staff/add";
	var delUrl = "admin/staff/delete";
	var editUrl = 'admin/staff/edit';
	var dialogUrl = "admin/staff/openDialog";
	var resetUserUrl = "datamanage/userDetail/resetUser";
	var url;
	var width='600px';
	var height='350px';

	var $oldPas;
	var $firstPas;
	var $newPas;

	var $dataType;

	var Staff =function(){
		this.$grid;
		this.$dialog;
		this.$form;
		this.$toolbar;
		this.$searchbox;
		this.$groupComboGrid;
		this.$orgId;
	}

	Staff.prototype = {
		constructor: Staff,
		/**
		 * 初始化函数
		 */
		init: function() {
			extend.init();
			var self = this;
			self.$grid=$('#staffGrid');
			self.$dialog=$('#staffEditWindow');
			self.$toolbar=$("#staffGridBar");
			self.$form=$("#staffForm");
			self.$searchbox=self.$toolbar.find(".js-searchbox");
			
			$dataType = parent.window.document.getElementsByClassName('l-btn-selected')[0].id
			//表头
			var dgColumns;
			if($dataType == 'Online'){
				dgColumns= [[
				             {field: 'pkid',title: 'pkid',width: 10,hidden:true},
				             {field: "staffId",title: "员工工号",width: 150},
				             {field: 'staffName',title: '员工姓名',width: 100},
				             /*{field: 'status',title: '状态',width: 100,
						    	  formatter: function(val, row, index){
						    		 if(val == '1') return "正常";
						    		 else if(val == '2') return "停用";
						    		 else if(val == '3') return "作废删除";
						    	  }	
						      },*/
				             {field: 'staffRoom',title: '员工科室',width: 100},
				             {field: 'groupName',title: '员工班组',width: 200},
				             {field: 'orgName',title: '员工所属组织',width: 100},
				             {field: 'updateUserName',title: '更新人',width: 150},
				             {field: 'updateTime',title: '更新时间',width: 150},
				             {field: 'yearmonth',title: '所属年月',width: 100,hidden:true}
				             ]];
			}else{
				dgColumns= [[
				             {field: 'pkid',title: 'pkid',width: 10,hidden:true},
				             {field: "staffId",title: "员工工号",width: 150},
				             {field: 'staffName',title: '员工姓名',width: 100},
				             {field: 'staffRoom',title: '员工科室',width: 100},
				             {field: 'groupName',title: '员工班组',width: 100},
				             {field: 'orgName',title: '员工所属组织',width: 100},
				             {field: 'serviceType',title: '服务类型',width: 100},
				             {field: 'personType',title: '人员类型',width: 100},
				             {field: 'cooperatorName',title: '合作商名称',width: 100},
				             {field: 'updateUserName',title: '更新人',width: 150},
				             {field: 'updateTime',title: '更新时间',width: 150},
				             {field: 'yearmonth',title: '所属年月',width: 100,hidden:true}
				             ]];
			}
			
			self.$grid.datagrid({
				url : staffUrl,
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
			var options = $('#staffGrid').datagrid('getPager').data("pagination").options;  
	    	var pageNum = options.pageNumber;  
	    	var pageSize = options.pageSize;  
	    	$('#staffGrid').datagrid('load',{
	    		"keyword":value,
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
								if($dataType == 'Online'){
								}else{
									var val = $('#cooperatorName').val();
									if(val.match(/[（|\(].*?[）|\)]/g)){
										$.messager.alert('提示','合作商名称不加后缀,<br/>不使用点动(XX中心)形式','warning');
										return false;
									}
								}
								if(self.$form.form("validate"))
									return true;
								else 
									return false;
							},
							success : function(resp) {
								resp = $.parseJSON(resp);
								if(resp.meta.success){
									$.messager.alert('提示', "保存成功", 'info',function(){
										self.$dialog.dialog("close");
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
					handler:function(){self.$dialog.dialog("close");}
				}];
			
			
			self.$toolbar.on("click",".bda-btn-add",function(){
				self.$dialog.dialog({
					title:'新建员工',
					modal:true, 
					width:width,
					height:height,
					href:dialogUrl,
					queryParams:{insertFlag:true},
					buttons:footButtons,
					maximized:false,
					minimizable:true,
					resizable:true,
					onLoad:function(){
						if($dataType == 'Online'){
						}else{
							$('#serviceType').combobox('clear');
						}
					},
					onClose : function() {
                        $(this).dialog('destroy');
                    }
				});
				self.$dialog.window('center');
				return false;
			})
			.on("click",".bda-btn-edit",function(){
				var selected=self.$grid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择行','warning');
					return false;
				}
				if(''==selected.pkid || undefined==selected.pkid || null==selected.pkid){
					$.messager.alert('提示','打开错误，员工PKID为空！','warning');
					return false;
				}
				self.$dialog.dialog({
					title:'编辑员工',
					modal:true,
					width:width,
					height:height,
					href:dialogUrl,
					queryParams:{insertFlag:false,pkid:selected.pkid},
					buttons:footButtons,
					maximized:false,
					minimizable:true,
					resizable:true,
					onLoad:function(){
						var tmpOrgId = $('#tmpOrgId').val();
						$('#orgId').combobox("select",tmpOrgId);
						$('#staffId').textbox('readonly',true);
						$('#yearmonth').textbox('readonly',true);
					},
					onClose : function() {
                        $(this).dialog('destroy');
                    }
				}); 
				self.$dialog.window('center');
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
					ids.push(rows[i].pkid);
				}
				$.messager.confirm('请确认','删除所选员工吗？',function(r){
					if (r){
						$.post(delUrl,{"ids[]":ids},function(resp){
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
			}).on("click",".bda-btn-imp",function(){
				self.$dialog.dialog({
					title:'员工及班组信息批量导入/修改',
					width:width,
					height:height,
					href:dialogUrl,
					queryParams:{importFlag:true},
					buttons:[{
						text:'导入',
						iconCls:'icon-ok',
						handler:function(){
							$('#import_staff_form').form('submit',{
								url : "admin/staff/importStaff",
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
										$.messager.alert('提示', "导入成功。<br/>新增员工："+resp.data[0]+"个。", 'info',function(){
											self.$dialog.dialog("close");
											self.$grid.datagrid('reload');
										});
									}else{
										parent.$.messager.progress('close');
										$.messager.alert('错误', "导入失败，请检查导入数据正确性！"+resp.data, 'error');
									}
								}
							});
						}
					},{
						text:'取消',
						handler:function(){self.$dialog.dialog("close");}
					}],
					maximized:false,
					minimizable:true,
					maximizable:true,
					resizable:true
				}); 
				self.$dialog.window('center');
				return false;
			})
			/**
			 * 点击班组信息事件
			 */
			.on("click",".bda-btn-workgroup",function(){
				self.$dialog.dialog({
					title:'班组信息',
					width:width,
					height:height,
					modal:true,
					href:"admin/workgroup",
					queryParams:{},
					buttons:[],
//					minimizable:true,
					maximized:true,
					maximizable:false,
					resizable:false,
					onClose : function() {
                        $(this).dialog('destroy');
                    }
				}); 
				return false;
			});
		},//end initEleEvent
		
		edit:function(){
			var self = this;
			self.$form=$('#staffForm');
			var pkid = $('#pkid').val();
			var tmpOrgId = $('#tmpOrgId').val();
			/*self.$groupComboGrid = $('#staffGroup');
			var dgColumns = [[
			    {field: "groupId",title: "班组ID",hidden:true},
				{field: "groupName",title: "班组名称",width: 150},
				{field: "orgId",title: "组织机构ID",hidden:true},
				{field: 'orgName',title: '班组所属区域',width: 100}
			]];
			
			var option = {
				panelHeight:'250',
				panelMaxHeight:250,
				url:'admin/workgroup/getGroupComboGrid',
				required:true,
			    idField:'groupId',    
			    textField:'groupName',    
			    columns: dgColumns,
			    singleSelect:true,
			    editable:false,
			    fitColumns:true,
				queryParams:{},
				onChange:function(newValue,oldValue){
					var row = self.$groupComboGrid.combogrid('grid').datagrid('getSelected');
					var orgId = row.orgId;
					$('#orgId').combobox("select",orgId);
				}
			}*/
			
			if(pkid != '') {
				/*option.onLoadSuccess = function(){
					self.$groupComboGrid.combogrid('setValue',tmpGroupId)
					$('#staffId').textbox('readonly',true);
				};*/
				url = editUrl;
			} else{
				url = addUrl;
			}
			//self.$groupComboGrid.combogrid(option); 
			
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
			}); 
		}
		
	}

	module.exports = new Staff();
})