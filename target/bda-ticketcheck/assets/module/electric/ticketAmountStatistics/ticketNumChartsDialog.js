define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'electric/ticketExplore/';//模块界面根目录
	
//	var getUnitNumAndCheckUrl = moduleWebRootUrl + 'getUnitNumAndCheck';
	
	var ticketNumChartsDialog = function(){
		
	}
	ticketNumChartsDialog.prototype = {
			constructor: ticketNumChartsDialog,
			dialog : function() {
				var self = this;
				self.$grid = $('#dialog_grid');
				self.$toolbar = $('#dialog_bar');
				function showAllvalue(value){
					if(value==null){
						return null;
					}else{
						return '<span title='+value+'>'+value+'</span>';
					}
				}
				var newComluns = [[
				    {field:'ticketNo',title:'工作票票号',width:150,align:'center',formatter:showAllvalue},
					{field:'ticketType',title:'工作票类型',width:150,align:'center',formatter:function(val, row, index){
				    	switch (val) {
						case "11":
							val = "厂站第一种工作票";
							break;
						case "12":
							val = "厂站第二种工作票";
							break;
						case "13":
							val = "厂站第三种工作票";
							break;
						case "21":
							val = "线路第一种工作票";
							break;
						case "22":
							val = "线路第二种工作票";
							break;
						case "31":
							val = "一级动火工作票";
							break;
						case "32":
							val = "二级动火工作票";
							break;
						case "41":
							val = "配电第一种工作票";
							break;
						case "42":
							val = "配电第二种工作票";
							break;
						case "43":
							val = "带电作业工作票";
							break;
						case "44":
							val = "低压配电网工作票";
							break;
						case "51":
							val = "紧急抢修工作票";
							break;
						case "61":
							val = "安全技术交底单";
							break;
						case "71":
							val = "二次措施单";
							break;
						case "81":
							val = "书面布置和记录";
							break;
						case "91":
							val = "现场勘察记录";
							break;
						default:
							break;
						}
				    	return val;
					}},
					{field:'functionLocationName',title:'站/线路',width:150,align:'center',formatter:showAllvalue},
					{field:'workTask',title:'工作任务',width:150,align:'center',formatter:showAllvalue},
					{field:'orgName',title:'单位和班组',width:150,align:'center',formatter:showAllvalue},
					{field:'principalName',title:'工作负责人',width:150,align:'center',formatter:showAllvalue},
					{field:'pmisName',title:'工作许可人',width:150,align:'center',formatter:showAllvalue},
					{field:'signName',title:'工作票签发人',width:150,align:'center',formatter:showAllvalue},
					{field:'workState',title:'工作票状态',width:150,align:'center',formatter:function(val,row,index){
						switch (val) {
						case '1':
							val = "填写";
							break;
						case '2':
							val = "待签发";
							break;
						case '3':
							val = "待会签";
							break;
						case '4':
							val = "待接收";
							break;
						case '5':
							val = "待许可";
							break;
						case '6':
							val = "执行中";
							break;
						case '7':
							val = "工作终结";
							break;
						case '8':
							val = "工作票终结";
							break;
						case '9':
							val = "已取消";
							break;
						case '10':
							val = "已作废";
							break;
						case '11':
							val = "已删除";
							break;
						default:
							break;
						}
						return val;
					}},
					{field:'permissionTime',title:'实际开始时间',width:150,align:'center',formatter:showAllvalue},
					{field:'workEndTime',title:'实际终结时间',width:150,align:'center',formatter:showAllvalue},
					{field:'planStartTime',title:'计划开始时间',width:150,align:'center',formatter:showAllvalue},
					{field:'planEndTime',title:'计划结束时间',width:150,align:'center',formatter:showAllvalue},
					{field:'isDelay',title:'是否延期',width:150,align:'center',formatter:function(val,row,index){
						if(val != null && val == 0){
							return "未延期";
						}else{
							return "延期";
						}
					}},
					{field:'delayTime',title:'延期时间(小时)',width:150,align:'center',formatter:function(val,row,index){
						if(row.isDelay != null && row.isDelay == 1){
							var endTime = new Date(row.workEndTime.replace(/-/g, "/"));//将-转化为/，使用new Date
							var beginTime = new Date(row.planEndTime.replace(/-/g, "/"));//将-转化为/，使用new Date
							var dateDiff = endTime.getTime() - beginTime.getTime();
							var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数
						    var leave1=dateDiff%(24*3600*1000);    //计算天数后剩余的毫秒数
						    var hours=Math.floor(leave1/(3600*1000));//计算出小时数
						    //计算相差分钟数
						    var leave2=leave1%(3600*1000);    //计算小时数后剩余的毫秒数
						    var minutes=Math.floor(leave2/(60*1000));
						    return dayDiff*24 + hours + (minutes == 0 ? 0:1);
						}else{
							return "-";
						}
					}},
					{field:'checkResult',title:'是否合格',width:150,align:'center',formatter:function(val,row,index){
						if(val != null && val == 0){
							return "合格";
						}else{
							return "不合格";
						}
					}},
					{field:'standardResult',title:'是否规范',width:150,align:'center',formatter:function(val,row,index){
						if(row.checkResult != null && row.checkResult == 0){
							if(val != null && val == 0){
								return "规范";
							}else{
								return "不规范";
							}
						}
						return "";
					}}
				]];
				var beginTime = $("#beginTimeDio").val();
				var endTime = $("#endTimeDio").val();
				var unit = $("#unitDio").val();
				var url = $("#urlDio").val();
				var seriesIndex = $("#seriesIndex").val();
				var qcCenter = $("#qcCenter").val();
				var group = $("#group").val();
				self.$grid.datagrid({
					columns: newComluns,
					toolbar : self.$toolbar,
					loadMsg: '正在查询数据，请稍候...',
					rownumbers: true,
					striped:true,
					pagination :true,
					pageList :[ 10,20,50,100 ],
					pageNumber:1,
					pageSize:20,
					fit: true,
					animate:true,
					singleSelect:true,
					queryParams: {
						"beginTime":beginTime,
						"endTime":endTime,
						"unit":unit,
						"seriesIndex":seriesIndex,
						"qcCenter":qcCenter,
						"group":group
					},
					url: url
				});
			}
	}
	
	module.exports = new ticketNumChartsDialog();
})