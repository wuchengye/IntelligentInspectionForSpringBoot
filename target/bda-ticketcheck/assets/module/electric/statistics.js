
//使用情况统计表
define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'dict/caliberword';//模块界面根目录
	    moduleRequestRootUrl = 'electric/statistics/';
	var getOriginalUrl = 'electric/statistics/getOriginData'
	var getDataUrl = 'electric/statistics/getAllData';
	//var test ='dict/caliberword/testtest'
	var dialogWebUrl = moduleWebRootUrl +'dialog.jsp';
	var unitDialogWebUrl = moduleWebRootUrl +'detailDialog/unitDialog.jsp';
	var exportUrl = moduleRequestRootUrl +'export';
	var getExternalUrl = 'electric/statistics/getExternalUnit';
	var width='500px';
	var height='400px';
	var paramType;
	var paramFiled;
	var dataList;
	var map;
	
	
	var statistics = function(){
		this.width = '655px';
		this.height = '350px';
		this.footButtons;
		this.batchFootButtons;
		this.$grid;
		this.$batchgrid;
		this.$dialog;
		this.$batchdialog;
		this.$form;
		this.$batchform;
		this.$toolbar;
		this.$batchtoolbar;
		this.$typeTreeIndex;
		this.$searchbox;
		this.$searchbox_batch;
		this.smsOpType;
		this.batchOpType;
		
	}
	statistics.prototype = {
			constructor: statistics,
			statistics:function() {
				var self = this;
				function formatDept(value){
					if(null != value && value.indexOf("供电局") > -1){
						return value.replace("广州","");
					}
					return value;
				}
				var Columns = [[
					{field:'month',title:'月份',width:130,align:'center',rowspan:3},
					{field:'departmentOname',title:'单位',width:100,align:'center',rowspan:3,formatter:formatDept},
					{field:'a',title:'工作票数量&nbsp;<span style="color:red;">（双击数字可显示数据源）</span>',align:'center',colspan:12},
					{field:'unqualified',title:'不合格票',width:100,align:'center',rowspan:3},
					{field:'unstandard',title:'不规范票',width:100,align:'center',rowspan:3},
					{field:'standard',title:'合格票',width:100,align:'center',rowspan:3},
					{field:'qualified',title:'规范票',width:100,align:'center',rowspan:3},
					{field:'total',title:'总计',width:100,align:'center',rowspan:3},
					{field:'passRate',title:'合格率',width:100,align:'center',rowspan:3},
					{field:'standardRate',title:'规范率',width:100,align:'center',rowspan:3}
				],[
					{field:'b',title:'厂站工作票',align:'center',width:210,colspan:3},
					{field:'c',title:'线路工作票',align:'center',width:140,colspan:2},
					{field:'liveWorking',title:'带电作业工作票',align:'center',width:93,rowspan:2},
					{field:'lowVoltage',title:'低压配网工作票',align:'center',width:93,rowspan:2},
					{field:'urgentRepairs',title:'紧急抢修工作票',align:'center',width:93,rowspan:2},
					{field:'unMarked',title:'未标注工作票',align:'center',width:93,rowspan:2},
					{field:'d',title:'动火票',align:'center',width:140,colspan:2},
					{field:'writtenForm',title:'书面形式布置及记录',align:'center',width:120,rowspan:2},
					{field:'dispatching',title:'调度检修申请单',align:'center',width:93,rowspan:2}
				],[
					{field:'stationOne',title:'第一种',width:70,align:'center',rowspan:1},
					{field:'stationTwo',title:'第二种',width:70,align:'center',rowspan:1},
					{field:'stationThree',title:'第三种',width:70,align:'center',rowspan:1},
					{field:'lineOne',title:'第一种',width:70,align:'center',rowspan:1},
					{field:'lineTwo',title:'第二种',width:70,align:'center',rowspan:1},
					{field:'fireOne',title:'一级',width:70,align:'center',rowspan:1},
					{field:'fireTwo',title:'二级',width:70,align:'center',rowspan:1}
				]]
				
				$('#news_grid').datagrid({
					//url: getDataUrl,
					//queryParams: {status: '1'},
					columns: Columns,
					toolbar : $('#news_bar'),
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
					    //allDatas = data.all;
					    map = data.map;
					    Obj.total = data.total;
					    Obj.rows = data.show;  // 类似的取出（转换数据）
					    return Obj;  // 再输出给控件加载
					},
					onLoadSuccess: function(data) {
						//console.log(data);
						dataList = data;
						var sum = self.SumData(data)
						$(this).datagrid('appendRow', sum);
					},
					onDblClickCell:function(index,field,value){
						var title;
						if(value=="外协单位"){
							var self = this	
							self.$dialog = $('#news_win');
							var importButtons=[{
								text:'取消',
								handler:function(){self.$dialog.dialog("close");}
							}];
							self.$dialog.dialog({
								title:'查询外协单位数据',
								width: '400px',
								height: '500px',
								modal:true,
								href:unitDialogWebUrl,
								//queryParams:{type:title,field:field},
								buttons:importButtons,
//								minimizable:true,
								maximizable:true,
								resizable:true
							});
						}
						
						for(var i=0;i<Columns.length;i++){
							for(var j=0;j<Columns[i].length;j++){
								if(Columns[i][j]["field"]==field){
									title = Columns[i][j]["title"]
								}
							}
						}
						paramType = title;
						paramFiled = field;
						var selectedRow = $('#news_grid').datagrid('getSelected');
						var flag = selectedRow.departmentOname;
						if(/^[0-9]+$/.test(value)&&flag!='总计'){
							var self = this	
							self.$dialog = $('#news_win');
							var importButtons=[{
								text:'取消',
								handler:function(){self.$dialog.dialog("close");}
							}];
							self.$dialog.dialog({
								title:'查询源数据',
								width: '700px',
								height: '500px',
								modal:true,
								href:dialogWebUrl,
								//queryParams:{type:title,field:field},
								buttons:importButtons,
//								minimizable:true,
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
				var self = this	
				self.$dialog = $('#news_win');
				$('a.bda-btn-hitlsn-query').bind('click', function() {
					self.doSearch('#begin_date_batch', '#end_date_batch');
				});
				$('#news_bar').on('click','.bda-btn-hitlsn-upload',function(){
				var importButtons=[{
					text:'取消',
					handler:function(){self.$dialog.dialog("close");}
				}];
				return false;
			  }).on('click','.bda-btn-hitlsn-export',function(){
			    	//日期验证
			    	var updateTimeBegin = $('#begin_date_batch').datebox('getValue');
			    	var updateTimeEnd = $('#end_date_batch').datebox('getValue');
			    	//日期验证
			    	if(updateTimeBegin >updateTimeEnd) {
			    		$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
			    		return;
			    	}
			    	var type = $('#data_query').combobox("getText");
			    	if(type!=""){
			    		type = '%'+type+'%';
			    	}else{
			    		type = "";
			    	}
					var params = {
							'updateTimeBegin': updateTimeBegin,
							'updateTimeEnd': updateTimeEnd,
							'type':type			
					};
				  $.messager.confirm('请确认','导出工作票使用情况统计表吗？',function(r){
					    if (r){
					    	parent.$.messager.progress({
								title : '提示',
								text : '导出中，请稍后....'
							});
					    	//$('.download')[0].click();
//					    	var exportSta = '${pageContext.request.contextPath}/upload/electric/使用情况统计表.xlsx';
//					    	var $form = $('<form></form>')
//					    	$form.attr('action', exportSta);
//					    	$form.attr('method', 'post');
//							$(document.body).append($form);
//					    	$form.submit();
//					    	$form.remove();
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
					    	//self.exportKeyword();
					    }
					});
			  })
			  .on('click','.bda-btn-reset',function(){
				$('#begin_date_batch').datebox('setValue', '');
				$('#end_date_batch').datebox('setValue', '');
				$("#data_query").combobox('setValue', '');
				return false;
			  })
			},
			exportKeyword : function() {
				$.messager.show({
					title:'温馨提示',
					msg:'正在导出，请稍候。。。',
					timeout:2000,
					showType:'slide'
				});
			},
			SumData:function(data){
				var stationOne=0;var stationTwo=0;var stationThree=0;var lineOne=0;
				var lineTwo=0;var liveWorking=0;var lowVoltage=0;var urgentRepairs=0;
				var fireOne=0;var fireTwo = 0; var unMarked = 0;
				for(var i=0;i<data.rows.length;i++){
					stationOne =Number(stationOne)+ Number(data.rows[i].stationOne);
					stationTwo =Number(stationTwo)+ Number(data.rows[i].stationTwo);
					stationThree =Number(stationThree)+ Number(data.rows[i].stationThree);
					lineOne =Number(lineOne)+ Number(data.rows[i].lineOne);
					lineTwo =Number(lineTwo)+ Number(data.rows[i].lineTwo);
					liveWorking =Number(liveWorking)+ Number(data.rows[i].liveWorking);
					lowVoltage =Number(lowVoltage)+ Number(data.rows[i].lowVoltage);
					urgentRepairs =Number(urgentRepairs)+ Number(data.rows[i].urgentRepairs);
					fireOne =Number(fireOne)+ Number(data.rows[i].fireOne);
					fireTwo =Number(fireTwo)+ Number(data.rows[i].fireTwo);
					unMarked = Number(unMarked)+ Number(data.rows[i].unMarked);
				}
	
				var sum = {
					'departmentOname':'总计',
					'liveWorking':liveWorking,
					'lowVoltage':lowVoltage,
					'urgentRepairs':urgentRepairs,
					'stationOne':stationOne,
					'stationTwo':stationTwo,
					'stationThree':stationThree,
					'lineOne':lineOne,
					'lineTwo':lineTwo,
					'fireOne':fireOne,
					'fireTwo':fireTwo,
					'unMarked':unMarked
				}
				return sum
			},
			doSearch:function(updateTimeBeginSelector, updateTimeEndSelector){
				var self = this;
				var options = $('#news_grid').datagrid('getPager').data("pagination").options;  
		    	var pageNum = options.pageNumber;  
		    	var pageSize = options.pageSize; 
		    	//日期验证
		    	var updateTimeBegin = $(updateTimeBeginSelector).datebox('getValue');
		    	var updateTimeEnd = $(updateTimeEndSelector).datebox('getValue');
		    	//日期验证
		    	if(updateTimeBegin >updateTimeEnd) {
		    		$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
		    		return;
		    	}
		    	var type = $('#data_query').combobox("getText");
		    	//var type2 = $('#data_query').combotree("getText");
		    	if(type!=""){
		    		type = '%'+type+'%';
		    	}else{
		    		type = "";
		    	}
		    	
				var params = {
						'updateTimeBegin': updateTimeBegin,
						'updateTimeEnd': updateTimeEnd,
						'type':type,
						"page":pageNum,
						"rows":pageSize,			
				};
				$('#news_grid').datagrid({url:getDataUrl,queryParams:params});
			},
			unitDialog:function(){
		    	
		    	var updateTimeBegin = $('#begin_date_batch').datebox('getValue');
		    	var updateTimeEnd = $('#end_date_batch').datebox('getValue');
				
		    	console.log(updateTimeBegin);
		    	console.log(updateTimeEnd);
		    	//console.log(map);
				var textColumns = [[
					{field:'departmentOname',title:'单位',width:100,align:'center'},
					{field:'total',title:'总数',width:100,align:'center'}
					]]
				$('#unitDialog_grid').datagrid({
					url: getExternalUrl,
					queryParams: {TimeBegin:updateTimeBegin,TimeEnd:updateTimeEnd},
					columns: textColumns,
					toolbar : $('#unitDialog_bar'),
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
					onDblClickCell:function(index,field,value){
						var selectedRow = $('#unitDialog_grid').datagrid('getSelected');
						var flag = selectedRow.departmentOname;
						if(/^[0-9]+$/.test(value)&&flag!='总计'){
							var self = this	
							self.$dialog = $('#unitDialog_win');
							var importButtons=[{
								text:'取消',
								handler:function(){self.$dialog.dialog("close");}
							}];
							self.$dialog.dialog({
								title:'查询源数据',
								width: '700px',
								height: '500px',
								modal:true,
								href:dialogWebUrl,
								//queryParams:{type:title,field:field},
								buttons:importButtons,
//								minimizable:true,
								maximizable:true,
								resizable:true
							});
						}else{
							return false;
						}
					}
				});
			},
			dialog : function() {
				function showAllvalue(value){
					if(value==null){
						return null;
					}else{
						return '<span title='+value+'>'+value+'</span>';
					}
				}
				var self = this;
				self.$grid = $('#news_grid');
				self.$dialog = $('#news_win');
				self.$toolbar = $("#news_bar");
				var selectedRow = $('#news_grid').datagrid('getSelected');
				
				var unit = selectedRow.departmentOname+'%';
				var month = selectedRow.month+'%';
				if(paramType=="单位"){
					var newSelectedRow = $('#unitDialog_grid').datagrid('getSelected');
					paramType = "";
					paramFiled ="";
				}
		    	switch (paramFiled) {
				case 'stationOne':
					paramFiled = "11";
					break;
				case 'stationTwo':
					paramFiled = "12";
					break;
				case 'stationThree':
					paramFiled = "13";
					break;
				case 'lineOne':
					paramFiled = "21";
					break;
				case 'lineTwo':
					paramFiled = "22";
					break;
				case 'fireOne':
					paramFiled = "31";
					break;
				case 'fireTwo':
					paramFiled = "32";
					break;
				case 'liveWorking':
					paramFiled = "43";
					break;
				case 'lowVoltage':
					paramFiled = "44";
					break;
				case 'urgentRepairs':
					paramFiled = "51";
					break;
				default:
					break;
				}
				var newComluns = [[
					{field:'workTask',title:'工作任务',width:150,align:'center',formatter:showAllvalue},
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
					{field:'principalName',title:'工作负责人',width:150,align:'center',formatter:showAllvalue},
					{field:'departmentOname',title:'单位和班组',width:150,align:'center',formatter:showAllvalue},
					{field:'functionLocationName',title:'站/线路',width:150,align:'center',formatter:showAllvalue},
					{field:'planStartTime',title:'计划开始时间',width:150,align:'center',formatter:showAllvalue},
					{field:'planEndTime',title:'计划结束时间',width:150,align:'center',formatter:showAllvalue},
					{field:'workPlace',title:'工作地点',width:150,align:'center',formatter:showAllvalue},
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
					{field:'ticketNo',title:'工作票票号',width:150,align:'center',formatter:showAllvalue},
					{field:'signName',title:'工作票签发人',width:150,align:'center',formatter:showAllvalue},
					{field:'ticketSignTime',title:'签发时间',width:150,align:'center',formatter:showAllvalue},
					{field:'watchName',title:'值班负责人',width:150,align:'center',formatter:showAllvalue},
					{field:'receiveTime',title:'接收时间',width:150,align:'center',formatter:showAllvalue},
					{field:'pmisName',title:'工作许可人',width:150,align:'center',formatter:showAllvalue},
					{field:'permissionTime',title:'许可工作时间',width:150,align:'center',formatter:showAllvalue},
					{field:'endPmisName',title:'工作终结许可人',width:150,align:'center',formatter:showAllvalue},
					{field:'workEndTime',title:'工作终结时间',width:150,align:'center',formatter:showAllvalue},
					{field:'delayTime',title:'延期时间',width:150,align:'center',formatter:showAllvalue}
				]]
				$('#dialog_grid').datagrid({
					url: getOriginalUrl,
					queryParams: {
						month:month,
						unit:unit,
						type:paramType,
						field:paramFiled,
					},
					columns: newComluns,
					toolbar : $('#dialog_bar'),
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
			}
	}
	
	module.exports = new statistics();
})