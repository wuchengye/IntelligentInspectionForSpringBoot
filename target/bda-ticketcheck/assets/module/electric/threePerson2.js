

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/';//模块界面根目录
	var getHitListenDataUrl = moduleRequestRootUrl + 'threeperson/getStatement';
	var dialogWebUrl = moduleRequestRootUrl +'threeperson/getOriginalData';
	var exportUrl = moduleRequestRootUrl +'threeperson/export';
	var getAmountOfTypeFromKeepUrl = moduleRequestRootUrl + "threeperson/getAmountOfType";
	
	var width='500px';
	var height='400px';
	
	var threePerson = function(){
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
		
		this.$amountOfTypeDialog;
		
	}
	threePerson.prototype = {
			constructor: threePerson,
			threePerson:function() {
				extend.queryCombotree("#unitName");
				var self = this;
				
				self.$grid = $('#news_grid');
				self.$dialog = $("#news_win");
				self.$toolbar = $("#news_bar");
				self.$amountOfTypeDialog = $("#amount_of_type");
				
				//单位,专业,班站所,姓名,类别,持票数,最后一次持票时间,有效期,备注
				var Columns =[[
					{field:'unitName',title:'单位名称',align:'center',width:150},
					{field:'deptmentName',title:'部门',width:100,align:'center'},
				    {field:'groupName',title:'班组',width:100,align:'center'},
					{field:'specialty',title:'专业类别',align:'center',width:150},
					//{field:'classes',title:'班/站/所',align:'center',width:150},
					{field:'personName',title:'姓名',align:'center',width:150},
					{field:'threeType',title:'“三种人”类别',align:'center',width:150},
					{field:'keepTicketAmount',title:'持票数',align:'center',width:150},
					{field:'theLast',title:'最后一次持工作票信息',align:'center',width:150,
						formatter: function(value,row,index){
							if (row.keepTicketAmount == 0){
								return value;
							} else {
								return '';
							}
						}
					},
					{field:'rigthTime',title:'有效期',align:'center',width:150},
					{field:'remark',title:'备注',align:'center',width:150},
				]]
				self.$grid.datagrid({
					//url: getHitListenDataUrl,
					//queryParams: {status: '1'},
					columns: Columns,
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
					onLoadSuccess: function() {
						//$(this).datagrid('appendRow', { 'person':'总计', 'unqualified':'0','unstandard':'23','standard':'223','qualified':'488','total':'22','passRate':'100%','standardRate':'100%','liveWorking':'2','lowVoltage':'12','urgentRepairs':'23','writtenForm':'324','dispatching':'0','stationOne':'0','stationTwo':'43','stationThree':'22','lineOne':'1','lineTwo':'67','fireOne':'0','fireTwo':'0'});
					},
//					data:[{'unitName':'天河供电局','specialty':'配电','classes':'局领导','personName':'黄晓彤','threeType':'工作票签发人、工作负责人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'配电部','personName':'陈申宇','threeType':'工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'安监部','personName':'陈锦华','threeType':'工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'安监部','personName':'王孟邻','threeType':'工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'安监部','personName':'尹尚斌','threeType':'工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'工程部','personName':'王志荣','threeType':'工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'工程部','personName':'苏锦晖','threeType':'工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'工程部','personName':'梁浩钊','threeType':'工作票签发人、工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'计量班','personName':'谭向华','threeType':'工作票签发人、工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},
//						{'unitName':'天河供电局','specialty':'配电','classes':'计量班','personName':'卢  源','threeType':'工作票签发人、工作负责人、工作许可人','rigthTime':'2019/4/30','remark':'','theLast':''},],
					onDblClickCell:function(index,field,value){
						var row = $(this).datagrid("getRows")[index];
						if(field == "theLast"){
							if(row.keepTicketAmount != 0 ||null == value || "" == value){
								$.messager.alert("提示","无相关源数据！","info");
								return;
							}
							var closeButton=[{
								text:'关闭',
								handler:function(){self.$dialog.dialog("close");}
							}];
							
							self.$dialog.dialog({
								href:dialogWebUrl,
								title:'查询源数据',
								width: '60%',
								height: '85%',
								modal:true,
								queryParams:{
									"ticketNum":value,
									"personName":row.personName
								},
								buttons:closeButton,
								maximizable:true,
								resizable:true
							});
						}else if(field == "keepTicketAmount"){
							if(row.keepTicketAmount == 0){
								$.messager.alert("提示","无相关源数据！","info");
								return;
							}
							self.$amountOfTypeDialog.show();
							var personName = row.personName;
							var startTime = $('#begin_date_batch').datebox('getValue');
							var endTime = $('#end_date_batch').datebox('getValue');
							
							self.$amountOfTypeDialog.dialog({
								title:'查询源数据',
								width: '40%',
								height: '80%',
								modal:true,
								buttons:[{
									text:'关闭',
									handler:function(){
										self.$amountOfTypeDialog.dialog("close");
									}
								}],
								maximizable:true,
								resizable:true
							})
							
							$.ajax({
								url:getAmountOfTypeFromKeepUrl,
								type: "post",
								data:{
									'personName':personName,
									'startTime':startTime,
									'endTime':endTime
								},
								cache: false,
								dataType: "json",
								beforeSend: function () {
								    $.messager.progress({ 
								        title: '提示', 
								        msg: '加载中，请稍候...', 
								        text: '' 
								    });
								},
							    complete: function () {
							        $.messager.progress('close');
							    },
								success: function(data){
									if(data){
										$("#stationOne").html(data[0].stationone);
										$("#stationTwo").html(data[0].stationtwo);
										$("#stationThree").html(data[0].stationthree);
										$("#lineOne").html(data[0].lineone);
										$("#lineTwo").html(data[0].linetwo);
										$("#fireOne").html(data[0].fireone);
										$("#fireTwo").html(data[0].firetwo);
										$("#pdOne").html(data[0].pdone);
										$("#pdTwo").html(data[0].pdtwo);
										$("#ddzy").html(data[0].ddzy);
										$("#dypdw").html(data[0].dypdw);
										$("#jjqx").html(data[0].jjqx);
										$("#safty").html(data[0].safty);
										$("#twice").html(data[0].twice);
										$("#writtenForm").html(data[0].writtenform);
										$("#xckcjl").html(data[0].xckcjl);
										$("#isEmpty").html(data[0].isempty);
									}
								}
							})
							
						}else{
							return false;
						}
						
					}
				});
				self.initEleEvent();

			},
			
			formatData:function(){
				$("#workTask").each(function(i){
					var workTask = $(this).text();
					
					$(this).html(workTask);
				});
				
				$("#workPlace").each(function(i){
					var workPlace = $(this).text();
					workPlace = workPlace.replace(/、/g,"、\n");
					$(this).html(workPlace);
				});
				
				$("#ticketType").each(function(i){
					var val = $(this).text();
					
					switch (val) {
					case '11':
						val = "厂站第一种工作票";
						break;
					case '12':
						val = "厂站第二种工作票";
						break;
					case '13':
						val = "厂站第三种工作票";
						break;
					case '21':
						val = "线路第一种工作票";
						break;
					case '22':
						val = "线路第二种工作票";
						break;
					case '31':
						val = "一级动火工作票";
						break;
					case '32':
						val = "二级动火工作票";
						break;
					case '41':
						val = "配电第一种工作票";
						break;
					case '42':
						val = "配电第二种工作票";
						break;
					case '43':
						val = "带电作业工作票";
						break;
					case '44':
						val = "低压配电网工作票";
						break;
					case '51':
						val = "紧急抢修工作票";
						break;
					case '61':
						val = "安全技术交底单";
						break;
					case '71':
						val = "二次措施单";
						break;
					case '81':
						val = "书面布置和记录";
						break;
					case '91':
						val = "现场勘察记录";
						break;
					default:
						break;
					}
					
					$(this).text(val);
				});
				
				$("#ticketStatus").each(function(i){
					var val = $(this).text();
					
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
					
					$(this).text(val);
				});
				
			},
			
			initEleEvent:function(){
				var self = this	
				
				$('a.bda-btn-hitlsn-query').bind('click', function() {
					self.doSearch('#begin_date_batch', '#end_date_batch');
				});
				
				$('#news_bar').on('click','.bda-btn-hitlsn-reset',function(){
					$("#tpsForm").form('reset');
					return false;
				})
				.on('click','.bda-btn-hitlsn-export',function(){
					var self = this;
					var unitName = $("#unitName").combotree("getText");
					var x = null;
			    	var level = null;
					if(unitName != null && unitName != ""){
						var t = $("#unitName").combotree('tree');  
						x = t.tree("getSelected").id;
						level = t.tree("getSelected").level;
					}
			    	//var unitName = $("#unitName").combobox("getValue");
					var specialty = $("#specialty").combobox('getValue');
			    	var threeType = $("#threeType").combobox("getValue");
			    	var personName = $("#personName").textbox("getValue");
			    	//日期验证
			    	var updateTimeBegin = $('#begin_date_batch').datebox('getValue');
			    	//日期验证
			    	if(updateTimeBegin == "") {
			    		$.messager.alert('温馨提示', '请选择或输入正确的开始日期', 'warning');
			    		return;
			    	}
			    	var updateTimeEnd = $('#end_date_batch').datebox('getValue');
			    	//日期验证
			    	if(updateTimeEnd == "") {
			    		$.messager.alert('温馨提示', '请选择或输入正确的结束日期', 'warning');
			    		return;
			    	}
			    	if( updateTimeBegin > updateTimeEnd ){
			    		$.messager.alert('温馨提示', '开始日期不能大于结束日期！', 'warning');
			    		return;
			    	}
			    	var params = {
							'updateTimeBegin': updateTimeBegin,
							'updateTimeEnd': updateTimeEnd,
							'unitName':unitName,
							'specialty':specialty,
							'threeType':threeType,
							'personName':personName,
							'orgId':x,
							'level':level
					};
				    $.messager.confirm('请确认','导出持票情况统计表吗？',function(r){
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
			doSearch:function(updateTimeBeginSelector, updateTimeEndSelector){
				var self = this;
				
				var options = $('#news_grid').datagrid('getPager').data("pagination").options;  
		    	var pageNum = options.pageNumber;  
		    	var pageSize = options.pageSize; 
				
		    	var unitName = $("#unitName").combotree("getText")
		    	var x = null;
		    	var level = null;
		    	if(unitName != null && unitName != ""){
					var t = $("#unitName").combotree('tree');  
					x = t.tree("getSelected").id;
					level = t.tree("getSelected").level;
				}
		    	//alert($("#unitName").combotree("getId"));
		    	//var unitName = $("#unitName").combobox("getValue");
		    	var specialty = $("#specialty").combobox('getValue');
		    	var threeType = $("#threeType").combobox("getValue");
		    	var personName = $("#personName").textbox("getValue");
		    	//日期验证
		    	var updateTimeBegin = $(updateTimeBeginSelector).datebox('getValue');
		    	//日期验证
		    	if(updateTimeBegin == "") {
		    		$.messager.alert('温馨提示', '请选择或输入正确的开始日期', 'warning');
		    		return;
		    	}
		    	var updateTimeEnd = $(updateTimeEndSelector).datebox('getValue');
		    	//日期验证
		    	if(updateTimeEnd == "") {
		    		$.messager.alert('温馨提示', '请选择或输入正确的结束日期', 'warning');
		    		return;
		    	}
		    	if( updateTimeBegin > updateTimeEnd ){
		    		$.messager.alert('温馨提示', '开始日期不能大于结束日期！', 'warning');
		    		return;
		    	}
				var params = {
						'updateTimeBegin': updateTimeBegin,
						'updateTimeEnd': updateTimeEnd,
						'unitName':unitName,
						'specialty':specialty,
						'threeType':threeType,
						'personName':personName,
						'orgId':x,
						'level':level,
						"page":pageNum,
						"rows":pageSize,
				};
				self.$grid.datagrid({url:getHitListenDataUrl,queryParams:params});
			},
			dialog : function() {
				var self = this;
				var $uploadForm = $('#upload_statement_form');
				self.$grid = $('#news_grid');
				self.$dialog = $('#news_win');
				self.$toolbar = $("#news_bar");
				//导入按钮点击事件
				$('.import-btn').unbind('click');
				$('.import-btn').bind('click', function() {
					$uploadForm.form('submit',{
						onSubmit : function() {
//							if($uploadForm.form("validate")) {
//								$.messager.show({
//									title:'温馨提示',
//									msg:'正在导入，请稍候。。。',
//									timeout:2000,
//									showType:'fade'
//								});
//								self.$grid.datagrid('loading');
//								self.$dialog.dialog('close');
//								return true;
//							}
//							else {
//								$.messager.alert('提示', "请先选择一个文件", 'error');
//								return false;
//							}
							self.$grid.datagrid('loading');
							self.$dialog.dialog('close');
						},
						success : function(resp) {
							resp = JSON.parse(resp);
							self.$grid.datagrid('reload');
							if(resp.meta.success){
								if(resp.meta.message == '0') {
//									$.messager.alert('提示', "导入成功", 'info',function(){
//										self.$dialog.dialog("close");
//									});
								} else {
									var calcelButtons=[{
										text:'退出',
										handler:function(){
											self.$dialog.dialog("close");
										}
									}];
								}
							}else{
								if(!resp.meta.message) {
									$.messager.alert('错误', "导入失败", 'error');
								}else{
									$.messager.alert('错误', resp.meta.message, 'error');
								}
							}
						}
					});
				});
			}
	}
	
	module.exports = new threePerson();
})