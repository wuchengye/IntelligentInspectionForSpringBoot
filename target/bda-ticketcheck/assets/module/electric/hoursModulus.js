/**
 * 工作负责人工时统计系数
 */
define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/workingHours/';//模块界面根目录
	var width='500px';
	var height='400px';
	
	var getHourMudulusUrl = moduleRequestRootUrl + "getHourModulus";
	var updateHoutModulusUrl = moduleRequestRootUrl + "updateHourModulus";
	

	var Modulu = function(){
		this.width = '655px';
		this.height = '350px';
		
		this.$grid;
		this.$dialog;
		this.$toolbar;
		this.$form;
	}
	Modulu.prototype = {
			
			constructor: Modulu,
			
			init: function() {
				var self = this;
				
				self.$grid = $("#hours_modulu_grid");
				self.$dialog = $("#modify_dialog");
				self.$toolbar = $("#hours_modulu_bar");
				self.$form = $("#modify_form");

				function showAllvalue(value){
					if(value==null){
						return null;
					}else{
						return '<span title='+value+'>'+value+'</span>';
					}
				}
				
				var frozenColumns = [[
			        {field:'type',title:'资格人员类型',width:150,align:'center',formatter:showAllvalue}            
				]];
				
				var comluns = [[
					{field:'liveWorking',title:'带电作业工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'lowVoltage',title:'低压配网工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'urgentRepairs',title:'紧急抢修工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'writtenForm',title:'书面形式布置及记录系数',width:150,align:'center',formatter:showAllvalue},
					{field:'stationOne',title:'厂站第一种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'stationTwo',title:'厂站第二种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'stationThree',title:'厂站第三种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'lineOne',title:'线路第一种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'lineTwo',title:'线路第二种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'fireOne',title:'动火第一种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'fireTwo',title:'动火第二种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'special',title:'特殊工作票系数',width:150,align:'center',formatter:showAllvalue}
				]]
				
				self.$grid.datagrid({
					url: getHourMudulusUrl,
					frozenColumns:frozenColumns,
					columns: comluns,
					toolbar : self.$toolbar,
					loadMsg: '正在查询数据，请稍候...',
					rownumbers: true,
					striped:true,
					pagination :true,
					pageList :[ 5,20,50,100 ],
					pageNumber:1,
					pageSize:20,
					fit: true,
					animate:true,
					singleSelect:true,
				});
				
				self.initEleEvent();
				
			},
			
			initEleEvent: function(){
				var self = this	;
				
				self.$toolbar.on("click",".bda-btn-update",function(){
					
					var selected = self.$grid.datagrid('getSelected');
					if(!selected){
						$.messager.alert('提示', "请选择要修改的行", 'error');
			    		return false;
					}
					
					var buttons=[
						{
						  text:'确认',
						  handler:function(){
							  var type = $('#type').val();
							  var liveWorking = $('#liveWorking').val();
							  var lowVoltage = $('#lowVoltage').val();
							  var urgentRepairs = $('#urgentRepairs').val();
							  var writtenForm = $('#writtenForm').val();
							  var stationOne = $('#stationOne').val();
							  var stationTwo = $('#stationTwo').val();
							  var stationThree = $('#stationThree').val();
							  var lineOne = $('#lineOne').val();
							  var lineTwo = $('#lineTwo').val();
							  var fireOne = $('#fireOne').val();
							  var fireTwo = $('#fireTwo').val();
							  var special = $("#special").val();
							  
							  if(liveWorking==""||lowVoltage==""||urgentRepairs==""||writtenForm==""||stationOne==""||stationTwo==""||stationThree==""||lineOne==""||lineTwo==""||fireOne==""||fireTwo==""||special==""){
								  $.messager.alert('提示', "不能为空", 'error');
								  return false;
							  }
								  
							  var reg = /^[0-9]+(\.[0-9]+)?$/;
							  if(!reg.test(liveWorking)||!reg.test(lowVoltage)||!reg.test(urgentRepairs)||!reg.test(writtenForm)||!reg.test(stationOne)||!reg.test(stationTwo)||!reg.test(stationThree)||!reg.test(lineOne)||!reg.test(lineTwo)||!reg.test(fireOne)||!reg.test(fireTwo)||!reg.test(special)){
								  $.messager.alert('提示', "只能输入正数", 'error');
								  return false;
							  }
							  
							  parent.$.messager.progress({
									title : '提示',
									text : '修改中，请稍后....'
							  });
							  $.ajax({
									url: updateHoutModulusUrl,
									type: "post",
									data: {
										type:type,
										liveWorking:liveWorking,
										lowVoltage:lowVoltage,
										urgentRepairs:urgentRepairs,
										writtenForm:writtenForm,
										stationOne:stationOne,
										stationTwo:stationTwo,
										stationThree:stationThree,
										lineOne:lineOne,
										lineTwo:lineTwo,
										fireOne:fireOne,
										fireTwo:fireTwo,
										special:special
									},
									cache: false,
									dataType: "json",
									success: function(data){
										parent.$.messager.progress('close');
										if(data['meta']['success']==true){
											$.messager.alert('提示', "修改成功", 'info');
											self.$grid.datagrid('reload');
										}else{
											$.messager.alert('提示', "修改失败！", 'warning');
										}
									}
								});
							  self.$dialog.dialog("close");
						  }
						},{
						  text:'取消',
						  handler:function(){
							  self.$dialog.dialog("close");
						  }
					}];
					
					$('#type').val(selected.type);
					$('#liveWorking').val(selected.liveWorking);
					$('#lowVoltage').val(selected.lowVoltage);
					$('#urgentRepairs').val(selected.urgentRepairs);
					$('#writtenForm').val(selected.writtenForm);
					$('#stationOne').val(selected.stationOne);
					$('#stationTwo').val(selected.stationTwo);
					$('#stationThree').val(selected.stationThree);
					$('#lineOne').val(selected.lineOne);
					$('#lineTwo').val(selected.lineTwo);
					$('#fireOne').val(selected.fireOne);
					$('#fireTwo').val(selected.fireTwo);
					$('#special').val(selected.special);
					
					
					self.$dialog.show();
					self.$dialog.dialog({
						title:'修改工时统计系数',
						width: '500px',
						height: '400px',
						modal:true,
						queryParams:{},
						buttons:buttons,
						maximizable:true,
						resizable:true,
						onClose:function(){
							self.$form.form("reset");
						}
					}); 
					return false;
					
				});
				
			}
	}
	
	module.exports = new Modulu();
})