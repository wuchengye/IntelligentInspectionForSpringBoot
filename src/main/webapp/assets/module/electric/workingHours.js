
//工时统计表
define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/workingHours/';//模块界面根目录
	    
	var getDataUrl = moduleRequestRootUrl + "getCountOfTicket";
	var getDetailDataUrl = moduleRequestRootUrl + "getDetailOfPerson";
	
	var dialogWebUrl = moduleRequestRootUrl +'openDialog';	
	var exportUrl = moduleRequestRootUrl + "export";
	
	var workingHours = function(){
		
		this.$dataGrid;
		this.$toolbar;
		this.$form;
		this.$dialog;
		
		this.$detailGrid;
	}
	
	workingHours.prototype = {
			
		constructor: workingHours,
			
		workingHours:function() {
			extend.queryCombotree("#data_query");
			var self = this;
			
			self.$dataGrid = $('#hours_grid');
			self.$dialog = $('#hours_win');
			
			function sumTicket(val,row,index){
				var sum = 0;
				sum = row.station_one + row.station_two + row.station_three + row.line_one + row.line_two 
				      + row.fire_one + row.fire_two + row.live_working + row.low_voltage + row.urgent_repairs 
				      + row.written_form + row.special 
				      /*+ row. safety_technology + row.twice_measure + row.site_survey*/ ;
				return sum;
			}
			
			var Columns = [[
			    {field:'unit_name',title:'单位',width:100,align:'center'},
			    {field:'deptment_name',title:'部门',width:100,align:'center'},
			    {field:'group_name',title:'班组',width:100,align:'center'},
				{field:'person_name',title:'姓名',width:100,align:'center'},
				{field:'station_one',title:'厂站第一种工作票',width:100,align:'center',hidden:true},
				{field:'station_two',title:'厂站第二种工作票',width:100,align:'center',hidden:true},
				{field:'station_three',title:'厂站第三种工作票',width:100,align:'center',hidden:true},
				{field:'line_one',title:'线路第一种工作票',width:100,align:'center',hidden:true},
				{field:'line_two',title:'线路第二种工作票',width:100,align:'center',hidden:true},
				{field:'fire_one',title:'一级动火工作票',width:100,align:'center',hidden:true},
				{field:'fire_two',title:'二级动火工作票 ',width:100,align:'center',hidden:true},
				{field:'live_working',title:'带电作业工作票',width:100,align:'center',hidden:true},
				{field:'low_voltage',title:'低压配电网工作票',width:100,align:'center',hidden:true},
				{field:'urgent_repairs',title:'紧急抢修工作票',width:100,align:'center',hidden:true},
				//{field:'safety_technology',title:'安全技术交底单',width:100,align:'center'},
				//{field:'twice_measure',title:'二次措施单',width:100,align:'center'},
				{field:'written_form',title:'书面布置和记录',width:100,align:'center',hidden:true},
				//{field:'site_survey',title:'现场勘察记录',width:100,align:'center'},
				{field:'special',title:'特殊工作票',width:100,align:'center',hidden:true},
				
				{field:'stationOneHours',title:'厂站第一种工作票',width:100,align:'center'},
				{field:'stationTwoHours',title:'厂站第二种工作票',width:100,align:'center'},
				{field:'stationThreeHours',title:'厂站第三种工作票',width:100,align:'center'},
				{field:'lineOneHours',title:'线路第一种工作票',width:100,align:'center'},
				{field:'lineTwoHours',title:'线路第二种工作票',width:100,align:'center'},
				{field:'fireOneHours',title:'一级动火工作票',width:100,align:'center'},
				{field:'fireTwoHours',title:'二级动火工作票 ',width:100,align:'center'},
				{field:'liveWorkingHours',title:'带电作业工作票',width:100,align:'center'},
				{field:'lowVoltageHours',title:'低压配电网工作票',width:100,align:'center'},
				{field:'urgentRepairsHours',title:'紧急抢修工作票',width:100,align:'center'},
				//{field:'safety_technology',title:'安全技术交底单',width:100,align:'center'},
				//{field:'twice_measure',title:'二次措施单',width:100,align:'center'},
				{field:'writtenFormHours',title:'书面布置和记录',width:100,align:'center'},
				//{field:'site_survey',title:'现场勘察记录',width:100,align:'center'},
				{field:'specialHours',title:'特殊工作票',width:100,align:'center'},
				
				{field:'sum_ticket',title:'总持票数',width:100,align:'center',formatter:sumTicket},
				{field:'sum_hours',title:'实际总工时',width:100,align:'center'}
			]];
			
			self.$dataGrid.datagrid({
				//url: getDataUrl,
				columns: Columns,
				toolbar : $('#hours_bar'),
				loadMsg: '正在查询数据，请稍候...',
				rownumbers: true,
				striped:true,
				pagination :true,
				pageList :[ 5,20,50,100],
				pageNumber:1,
				pageSize:20,
				fit: true,
				animate:true,
				singleSelect:true,
				loadFilter:function(data){
				    //console.log(data); // 这是你加载到的原始数据，调试到console上方便查看
				    
					var Obj = {};
				    Obj.total = data.total;
				    Obj.rows = data.pageData;  // 类似的取出（转换数据）
				    
				    return Obj;  // 再输出给控件加载
				},
				//data:[
				//	{'personName':'卞佳音','stationOne':'2','stationTwo':'23','stationThree':'5','lineOne':'32','lineTwo':'2','fireOne':'4','fireTwo':'8','liveWorking':'3','lowVoltage':'6','urgentRepairs':'5','safetyTechnology':'3','twiceMeasure':'23','writtenForm':'78','siteSurvey':'57','sumTicket':'334','sumHours':'643'}
				//],
				onDblClickCell:function(index,field,value){
					if(field=='sum_ticket'||field=='sum_hours'){
						return false;
					}
					
					var selectRow = self.$dataGrid.datagrid("getSelected");
					var temp = "0";
					switch(field){
						case "stationOneHours":temp = selectRow.station_one;break;
						case "stationTwoHours":temp = selectRow.station_two;break;
						case "stationThreeHours":temp = selectRow.station_three;break;
						case "lineOneHours":temp = selectRow.line_one;break;
						case "lineTwoHours":temp = selectRow.line_two;break;
						case "fireOneHours":temp = selectRow.fire_one;break;
						case "fireTwoHours":temp = selectRow.fire_two;break;
						case "liveWorkingHours":temp = selectRow.live_working;break;
						case "lowVoltageHours":temp = selectRow.low_voltage;break;
						case "urgentRepairsHours":temp = selectRow.urgent_repairs;break;
						case "writtenFormHours":temp = selectRow.written_form;break;
						case "specialHours":temp = selectRow.special;break;
						default:break;
					}
					if('0' == temp){
						$.messager.alert('警告', "没有数据", 'info');
						return false;
					}
					if(/^[0-9]+\.[0-9]+$/.test(value)){
						
						var importButtons=[{
							text:'取消',
							handler:function(){self.$dialog.dialog("close");}
						}];
						
						
						var unitName = selectRow.unit_name;
						var personName = selectRow.person_name;
						var startTime = $('#begin_date_batch').datebox('getValue');
						var endTime = $('#end_date_batch').datebox('getValue');
						var ticketType = self.formatType(field);
						
						self.$dialog.dialog({
							title:'查询工时详情列表',
							width: '700px',
							height: '500px',
							modal:true,
							href:dialogWebUrl,
							queryParams:{
								'unitName':unitName,
								'personName': personName,
								'ticketType': ticketType,
								'startTime': startTime,
								'endTime': endTime
							},
							buttons:importButtons,
							maximizable:true,
							resizable:true
						});
					}else{
						return false;
					}
				}
			});

			self.initEleEvent();
		},
		
		initEleEvent:function(){
			var self = this	;
			
			$('a.bda-btn-count').bind('click', function() {
				self.doSearch('#begin_date_batch', '#end_date_batch');
			});
			
			$('a.bda-btn-reset').bind('click', function() {
				$("#workHoursQueryForm").form("reset");
			});
			
			$('.bda-btn-export').bind('click',function(){
				var self = this;
				
				//日期验证
				var updateTimeBegin = $('#begin_date_batch').datebox('getValue');
				var updateTimeEnd = $('#end_date_batch').datebox('getValue');
				//日期验证
				if(updateTimeBegin > updateTimeEnd) {
					$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
					return;
				}
				
				var type = $('#data_query').combotree("getText");
				var x = null;
		    	var level = null;
				if(type != null && type != ""){
					var t = $("#data_query").combotree('tree');  
					x = t.tree("getSelected").id;
					level = t.tree("getSelected").level;
				}
				//var type = $('#data_query').combobox("getValue");
				var params = {
						'sumStartTime': updateTimeBegin,
						'sumEndTime': updateTimeEnd,
						'unitName':type,
						'orgId':x,
						'level':level
				};
			    $.messager.confirm('请确认','导出三种人工时统计表吗？',function(r){
				    if (r){
				    	parent.$.messager.progress({
							title : '提示',
							text : '导出中，请稍后....'
						});
				    	$.ajax({
							url: exportUrl,
							type: "post",
							data: params,
							cache: false,
							dataType: "json",
							success: function(data){
								parent.$.messager.progress('close');
								if(data.meta.success){
									var $form = $('<form></form>')
							    	$form.attr('method', 'post');
									$(document.body).append($form);
									$form.form('submit', {
										url: "common/downloadFile/downloadReport",
										queryParams: {
											fileName: data.meta.code
										}
									});
							    	$form.remove();
								}else{
									$.messager.alert('错误', "导出失败", 'error');
								}
							}
						});
				    }
				});
		  });
			
		},
		
		doSearch:function( beginTimeSelector, endTimeSelector){
			var self = this;
			
			//日期验证
			var updateTimeBegin = $(beginTimeSelector).datebox('getValue');
			var updateTimeEnd = $(endTimeSelector).datebox('getValue');
			//日期验证
			if(updateTimeBegin >updateTimeEnd) {
				$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
				return;
			}
			
			var type = $('#data_query').combotree("getText");
			var x = null;
	    	var level = null;
			if(type != null && type != ""){
				var t = $("#data_query").combotree('tree');  
				x = t.tree("getSelected").id;
				level = t.tree("getSelected").level;
			}
			//var type = $('#data_query').combobox("getValue");
			var params = {
					'sumStartTime': updateTimeBegin,
					'sumEndTime': updateTimeEnd,
					'unitName':type,
					'orgId':x,
					'level':level
			};
			
			self.$dataGrid.datagrid({
				url: getDataUrl,
				queryParams: params
			});
		},
		
		dialog : function() {
			var self = this;
			
			self.$detailGrid = $('#dialog_grid');
			
			function formatVal(value,row,index){
				var val = $("#ticketType_d").val();
				if(val == "special") {
					val = "特殊工作票";
				}
				return val;
			}
			
			var newComluns = [[
				{field:'person_name',title:'姓名',width:150,align:'center'},
				{field:'ticket_type',title:'工作票类型',width:150,align:'center',formatter:formatVal},
				{field:'ticket_no',title:'工作票票号',width:150,align:'center'},
				{field:'permission_time',title:'实际开始时间',width:150,align:'center'},
				{field:'work_end_time',title:'实际结束时间',width:150,align:'center'},
				{field:'gap_use_time',title:'间断用时',width:150,align:'center'},
				{field:'sum_time',title:'所耗工时',width:150,align:'center'}
			]]
			
			var unitName = $("#unitName_d").val();
			var personName = $("#personName_d").val();
			var ticketType = $("#ticketType_d").val();
			var startTime = $("#startTime_d").val();
			var endTime = $("#endTime_d").val();
			
			self.$detailGrid.datagrid({
				url: getDetailDataUrl,
				queryParams: {
					unitName:unitName,
					personName:personName,
					ticketType:ticketType,
					startTime:startTime,
					endTime:endTime
				},
				columns: newComluns,
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
				loadFilter:function(data){
				    //console.log(data); // 这是你加载到的原始数据，调试到console上方便查看
				    
					var Obj = {};
				    Obj.total = data.total;
				    Obj.rows = data.pageData;  // 类似的取出（转换数据）
				    
				    return Obj;  // 再输出给控件加载
				}
			});
		},
		
		formatType : function(val){
			switch (val) {
			case "stationOneHours":
				val = "厂站第一种工作票";
				break;
			case "stationTwoHours":
				val = "厂站第二种工作票";
				break;
			case "stationThreeHours":
				val = "厂站第三种工作票";
				break;
			case "lineOneHours":
				val = "线路第一种工作票";
				break;
			case "lineTwoHours":
				val = "线路第二种工作票";
				break;
			case "fireOneHours":
				val = "一级动火工作票";
				break;
			case "fireTwoHours":
				val = "二级动火工作票";
				break;
			case "liveWorkingHours":
				val = "带电作业工作票";
				break;
			case "lowVoltageHours":
				val = "低压配电网工作票";
				break;
			case "urgentRepairsHours":
				val = "紧急抢修工作票";
				break;
			case "safety_technology":
				val = "安全技术交底单";
				break;
			case "twice_measure":
				val = "二次措施单";
				break;
			case "written_form":
				val = "书面布置和记录";
				break;
			case "site_survey":
				val = "现场勘察记录";
				break;
			case "specialHours":
				val = "special";
				break;
			default:
				break;
			}
	    	return val;
		}
	}
	
	module.exports = new workingHours();
})