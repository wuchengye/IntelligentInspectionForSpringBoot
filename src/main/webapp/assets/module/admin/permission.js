/**
 * 
 */
define(function(require, exports, module) {
	var tplCheckbox='<input type="checkbox">';
	var saveUrl="admin/permission/savePermission";
	var Permission = function() {
		this.$grid;
		this.$gridData;
		this.$gridColumns;
	};
	Permission.prototype = {
			constructor : Permission,
			init : function() {
				var self = this;
				var roleId=$("input[name=roleId]").val();
				self.$grid=$('#permissionGrid');
				self.$gridColumns = [[
                    {field:'text',title:'权限名',width:150},
                    {field:'useable',title:'可用',width:100,
            			formatter : function(value, row, index) {
            				return self.formatCheckbox(row);
            			}},
                    {field:'action',title:'action',hidden:true},
                    {field:'name',title:'name',hidden:true},
                    {field:'primkey',title:'primkey',hidden:true},
                    {field:'permission',title:'permission',hidden:true}
                ]]
				self.$gridData = [
				    {text:'批次操作',action:'edit',name:'Opr',primkey:'batch',permission:'Opr:edit:batch',checked:false},
				    {text:'可疑结果导出',action:'export',name:'Opr',primkey:'judge',permission:'Opr:export:judge',checked:false},
				    {text:'词典维护',action:'edit',name:'Opr',primkey:'dict',permission:'Opr:edit:dict',checked:false},
				    {text:'人工初核',action:'recheck',name:'Opr',primkey:'judge',permission:'Opr:recheck:judge',checked:false},
				    {text:'人工复核',action:'recheck',name:'Opr',primkey:'secondjudge',permission:'Opr:recheck:secondjudge',checked:false},
				    {text:'开通、删除账户',action:'edit',name:'Opr',primkey:'user',permission:'Opr:edit:user',checked:false},
				    {text:'数据权限限制（本区域）',action:'browse',name:'Data',primkey:'area',permission:'Data:browse:area',checked:false},
				    {text:'数据权限限制（本班组）',action:'browse',name:'Data',primkey:'workgroup',permission:'Data:browse:workgroup',checked:false},
				    {text:'数据权限限制（本人）',action:'browse',name:'Data',primkey:'self',permission:'Data:browse:self',checked:false},
				    {text:'隐藏词典查看权限',action:'browse',name:'Hiddendic',primkey:'self',permission:'Hiddendic:browse:self',checked:false},
				    {text:'监控分析导出权限',action:'export',name:'Monitor',primkey:'self',permission:'Monitor:export:self',checked:false},
				    {text:'监控分析审核权限',action:'recheck',name:'Monitor',primkey:'self',permission:'Monitor:recheck:self',checked:false},
				    {text:'外呼营销口径导入导出权限',action:'export',name:'Opr',primkey:'dict',permission:'Opr:export:dict',checked:false},
				    {text:'任务分配(页面)',action:'assign',name:'Opr',primkey:'assign',permission:'Opr:assign:assign',checked:false},
				    {text:'待办任务(页面)',action:'backlog',name:'Opr',primkey:'assign',permission:'Opr:backlog:assign',checked:false}
				    ];
				self.initGridData(roleId);
				
			},
			initGridData : function(roleId){
				var self = this;
				$.ajax({
					type: "post",
			        dataType: "json",
			        url: 'admin/permission/getPermission',
			        data: {"roleId": roleId},
			        success: function (data) {
			        	if(data.length>0){
			        		for(var i=0;i<data.length;i++){
				        		switch(data[i].permission){
				        		case 'Opr:edit:batch':
				        			self.$gridData[0].checked=true
				        			break;
				        		case 'Opr:export:judge':
				        			self.$gridData[1].checked=true
				        			break;
				        		case 'Opr:edit:dict':
				        			self.$gridData[2].checked=true
				        			break;
				        		case 'Opr:recheck:judge':
				        			self.$gridData[3].checked=true
				        			break;
				        		case 'Opr:recheck:secondjudge':
				        			self.$gridData[4].checked=true
				        			break;
				        		case 'Opr:edit:user':
				        			self.$gridData[5].checked=true
				        			break;
				        		case 'Data:browse:area':
				        			self.$gridData[6].checked=true
				        			break;
				        		case 'Data:browse:workgroup':
				        			self.$gridData[7].checked=true
				        			break;
				        		case 'Data:browse:self':
				        			self.$gridData[8].checked=true
				        			break;
				        		case 'Hiddendic:browse:self':
				        			self.$gridData[9].checked=true
				        			break;
				        		case 'Monitor:export:self':
				        			self.$gridData[10].checked=true
				        			break;
				        		case 'Monitor:recheck:self':
				        			self.$gridData[11].checked=true
				        			break;
				        		case 'Opr:export:dict':
				        			self.$gridData[12].checked=true
				        			break;
				        		case 'Opr:assign:assign':
				        			self.$gridData[13].checked=true
				        			break;
				        		case 'Opr:backlog:assign':
				        			self.$gridData[14].checked=true
				        			break;
				        		}
				        	}
			        	}
			        	self.renderGrid(self.$grid,self.$gridColumns,self.$gridData);
			        	self.initBtnEvent();
			        }
				})
				
			},
			formatCheckbox : function(row){
				var $checkbox=$(tplCheckbox).attr("name",'permission');
				$checkbox.attr('id',row.permission);
				if(row.checked){
					return $checkbox.attr('checked','checked')[0].outerHTML;
				}
				return $checkbox[0].outerHTML;
			},
			renderGrid : function(grid,columns,data){
				var self = this;
				grid.datagrid({
					columns : columns,
					data : data,
					rownumbers : false,
					fit : true,
					ctrlSelect:false,
	                singleSelect:true
				});
				$("input[name=permission]").on("change",function(){
					var checked=$(this).prop("checked");
					for(i in self.$gridData){
						if($(this).attr("id") == self.$gridData[i].permission){
							self.$gridData[i].checked = checked;
						}
					}
				})
			},
			initBtnEvent : function(){
				var self = this;
				$(".bda-btn-save").on("click",function(){
					var gridData = self.$gridData;
					var roleId = $("input[name=roleId]").val();
					var obj = {};
					obj.roleId = roleId;
					obj.addList = new Array();
					obj.delList = new Array();
					var i=0;
					for(p in gridData){
						if(gridData[p].checked){
							obj.addList.push(gridData[p]);
							if(p == 6 || p==7 || p==8){
								i++;
								if(i>1){
									$.messager.alert("提示","数据权限只能选一个！！","warning");
									return 0;
								}
							}
						}else{
							obj.delList.push(gridData[p]);
							
						}
					}
					parent.$.messager.progress({
					    title : '提示',
					    text : '保存中，请稍后....'
					});
					$.ajax({
						url:saveUrl,
						type : 'post',
						data : JSON.stringify(obj),
						dataType : 'json',
						contentType : 'application/json',
						success:function(resp){
							if(resp.meta.success){
								parent.$.messager.progress('close');
								$.messager.alert("提示","保存成功","info");
							}else{
								parent.$.messager.progress('close');
								$.messager.alert("提示",resp.meta.code+resp.meta.message,"warning");
							}
						}
					});
				});
				$(".bda-btn-cancel").on("click",function(){
					$('#permsWin').dialog('destroy');
				});
			},
			getPostData : function(grid){
				
			}
	}
	module.exports = new Permission();
});