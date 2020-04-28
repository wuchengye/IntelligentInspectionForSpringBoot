

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/';//模块界面根目录
	var getHitListenDataUrl = moduleRequestRootUrl + 'getStatement';
	var dialogWebUrl = moduleWebRootUrl +'dialog.jsp';
	var exportUrl = moduleRequestRootUrl +'export';
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
		
	}
	threePerson.prototype = {
			constructor: threePerson,
			threePerson:function() {
				var self = this;
				
				var Columns = [[
						{field:'month',title:'月份',width:100,align:'center',rowspan:3},
						{field:'person',title:'三种人',width:100,align:'center',rowspan:3},
						{field:'a',title:'工作票数量',align:'center',colspan:12},
						{field:'unqualified',title:'不合格票',width:100,align:'center',rowspan:3},
						{field:'unstandard',title:'不规范票',width:100,align:'center',rowspan:3},
						{field:'standard',title:'合格票',width:100,align:'center',rowspan:3},
						{field:'qualified',title:'规范票',width:100,align:'center',rowspan:3},
						{field:'total',title:'总计',width:100,align:'center',rowspan:3},
						{field:'passRate',title:'合格率',width:100,align:'center',rowspan:3},
						{field:'standardRate',title:'规范率',width:100,align:'center',rowspan:3},
					],[
						{field:'b',title:'厂站工作票',align:'center',colspan:3},
						{field:'c',title:'线路工作票',align:'center',colspan:2},
						{field:'liveWorking',title:'带电作业工作票',align:'center',rowspan:2},
						{field:'lowVoltage',title:'低压配网工作票',align:'center',rowspan:2},
						{field:'urgentRepairs',title:'紧急抢修工作票',align:'center',rowspan:2},
						{field:'d',title:'动火票',align:'center',colspan:2},
						{field:'writtenForm',title:'书面形式布置及记录',align:'center',rowspan:2},
						{field:'dispatching',title:'调度检修申请单',align:'center',rowspan:2},
					],[
						{field:'stationOne',title:'第一种',align:'center',rowspan:1},
						{field:'stationTwo',title:'第二种',align:'center',rowspan:1},
						{field:'stationThree',title:'第三种',align:'center',rowspan:1},
						{field:'lineOne',title:'第一种',align:'center',rowspan:1},
						{field:'lineTwo',title:'第二种',align:'center',rowspan:1},
						{field:'fireOne',title:'一级',align:'center',rowspan:1},
						{field:'fireTwo',title:'二级',align:'center',rowspan:1},
					]]
				$('#news_grid').datagrid({
					//url: getHitListenDataUrl,
					queryParams: {status: '1'},
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
					onLoadSuccess: function() {
						$(this).datagrid('appendRow', { 'person':'总计', 'unqualified':'0','unstandard':'23','standard':'223','qualified':'488','total':'22','passRate':'100%','standardRate':'100%','liveWorking':'2','lowVoltage':'12','urgentRepairs':'23','writtenForm':'324','dispatching':'0','stationOne':'0','stationTwo':'43','stationThree':'22','lineOne':'1','lineTwo':'67','fireOne':'0','fireTwo':'0'});
					},
					data:[{'month':'201709','person':'工作许可人','unqualified':'0','unstandard':'23','standard':'223','qualified':'488','total':'22','passRate':'100%','standardRate':'100%','liveWorking':'2','lowVoltage':'12','urgentRepairs':'23','writtenForm':'324','dispatching':'0','stationOne':'0','stationTwo':'43','stationThree':'22','lineOne':'1','lineTwo':'67','fireOne':'0','fireTwo':'0'},
						{'month':'201709','person':'工作负责人','unqualified':'206','unstandard':'22','standard':'5','qualified':'23','total':'23','passRate':'100%','standardRate':'100%','liveWorking':'34','lowVoltage':'2','urgentRepairs':'44','writtenForm':'34','dispatching':'0','stationOne':'0','stationTwo':'32','stationThree':'2','lineOne':'1','lineTwo':'0','fireOne':'0','fireTwo':'0'},
						{'month':'201709','person':'工作票签发人','unqualified':'378','unstandard':'488','standard':'12','qualified':'44','total':'34','passRate':'100%','standardRate':'100%','liveWorking':'33','lowVoltage':'3','urgentRepairs':'53','writtenForm':'55','dispatching':'0','stationOne':'0','stationTwo':'12','stationThree':'2','lineOne':'2','lineTwo':'0','fireOne':'0','fireTwo':'0'},
						{'month':'201710','person':'工作许可人','unqualified':'385','unstandard':'32','standard':'34','qualified':'5','total':'34','passRate':'100%','standardRate':'100%','liveWorking':'45','lowVoltage':'4','urgentRepairs':'0','writtenForm':'4','dispatching':'0','stationOne':'0','stationTwo':'55','stationThree':'2','lineOne':'32','lineTwo':'0','fireOne':'0','fireTwo':'0'},
						{'month':'201710','person':'工作负责人','unqualified':'0','unstandard':'44','standard':'9','qualified':'70','total':'444','passRate':'100%','standardRate':'100%','liveWorking':'7','lowVoltage':'33','urgentRepairs':'0','writtenForm':'2','dispatching':'0','stationOne':'0','stationTwo':'43','stationThree':'3','lineOne':'32','lineTwo':'0','fireOne':'0','fireTwo':'0'},
						{'month':'201710','person':'工作票签发人','unqualified':'0','unstandard':'5','standard':'0','qualified':'58','total':'54','passRate':'100%','standardRate':'100%','liveWorking':'90','lowVoltage':'456','urgentRepairs':'0','writtenForm':'34','dispatching':'0','stationOne':'0','stationTwo':'33','stationThree':'3','lineOne':'23','lineTwo':'12','fireOne':'0','fireTwo':'0'},
						{'month':'201711','person':'工作许可人','unqualified':'5','unstandard':'0','standard':'0','qualified':'23','total':'23','passRate':'100%','standardRate':'100%','liveWorking':'0','lowVoltage':'32','urgentRepairs':'1','writtenForm':'3','dispatching':'23','stationOne':'0','stationTwo':'25','stationThree':'3','lineOne':'3','lineTwo':'32','fireOne':'0','fireTwo':'0'},
						{'month':'201711','person':'工作负责人','unqualified':'0','unstandard':'0','standard':'1','qualified':'47','total':'12','passRate':'100%','standardRate':'100%','liveWorking':'0','lowVoltage':'32','urgentRepairs':'1','writtenForm':'23','dispatching':'23','stationOne':'0','stationTwo':'43','stationThree':'4','lineOne':'2','lineTwo':'43','fireOne':'0','fireTwo':'0'},
						{'month':'201711','person':'工作票签发人','unqualified':'1','unstandard':'2','standard':'2','qualified':'21','total':'32','passRate':'100%','standardRate':'100%','liveWorking':'0','lowVoltage':'33','urgentRepairs':'1','writtenForm':'2','dispatching':'44','stationOne':'0','stationTwo':'32','stationThree':'5','lineOne':'43','lineTwo':'43','fireOne':'0','fireTwo':'0'},
						{'month':'201712','person':'工作许可人','unqualified':'9','unstandard':'34','standard':'10','qualified':'22','total':'22','passRate':'100%','standardRate':'100%','liveWorking':'2','lowVoltage':'22','urgentRepairs':'2','writtenForm':'5','dispatching':'33','stationOne':'0','stationTwo':'43','stationThree':'5','lineOne':'123','lineTwo':'44','fireOne':'0','fireTwo':'0'},
						{'month':'201712','person':'工作负责人','unqualified':'16','unstandard':'22','standard':'21','qualified':'0','total':'33','passRate':'100%','standardRate':'100%','liveWorking':'3','lowVoltage':'0','urgentRepairs':'2','writtenForm':'65','dispatching':'2','stationOne':'0','stationTwo':'34','stationThree':'6','lineOne':'22','lineTwo':'45','fireOne':'0','fireTwo':'0'},
						{'month':'201712','person':'工作票签发人','unqualified':'33','unstandard':'10','standard':'20','qualified':'0','total':'44','passRate':'100%','standardRate':'100%','liveWorking':'55','lowVoltage':'0','urgentRepairs':'3','writtenForm':'56','dispatching':'1','stationOne':'0','stationTwo':'33','stationThree':'7','lineOne':'23','lineTwo':'23','fireOne':'0','fireTwo':'0'},
						{'month':'201801','person':'工作许可人','unqualified':'2','unstandard':'21','standard':'5','qualified':'1','total':'55','passRate':'100%','standardRate':'100%','liveWorking':'33','lowVoltage':'9','urgentRepairs':'3','writtenForm':'67','dispatching':'0','stationOne':'0','stationTwo':'22','stationThree':'77','lineOne':'43','lineTwo':'43','fireOne':'0','fireTwo':'0'},
						{'month':'201801','person':'工作负责人','unqualified':'23','unstandard':'22','standard':'34','qualified':'2','total':'67','passRate':'100%','standardRate':'100%','liveWorking':'32','lowVoltage':'8','urgentRepairs':'3','writtenForm':'23','dispatching':'0','stationOne':'0','stationTwo':'234','stationThree':'88','lineOne':'33','lineTwo':'12','fireOne':'0','fireTwo':'0'},
						{'month':'201801','person':'工作票签发人','unqualified':'4','unstandard':'5','standard':'2','qualified':'3','total':'88','passRate':'100%','standardRate':'100%','liveWorking':'23','lowVoltage':'23','urgentRepairs':'4','writtenForm':'432','dispatching':'0','stationOne':'0','stationTwo':'32','stationThree':'77','lineOne':'2','lineTwo':'32','fireOne':'0','fireTwo':'0'},],
					onDblClickCell:function(index,field,value){
						if(/^[0-9]+$/.test(value)){
							var self = this	
							self.$dialog = $('#news_win');
							var importButtons=[{
								text:'取消',
								handler:function(){self.$dialog.dialog("close");}
							}];
							self.$dialog.dialog({
								title:'查询源数据',
								width: '500px',
								height: '400px',
								modal:true,
								href:dialogWebUrl,
								queryParams:{},
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
				
				self.$dialog.dialog({
					title:'导入观察表',
					width: '500px',
					height: '400px',
					modal:true,
					href:uploadWebUrl,
					queryParams:{},
					buttons:importButtons,
//					minimizable:true,
					maximizable:true,
					resizable:true
				}); 
				return false;
			  }).on('click','.bda-btn-hitlsn-export',function(){
				  $.messager.confirm('请确认','导出工作票统计表吗？',function(r){
					    if (r){
					    	var $form = $('<form></form>')
					    	$form.attr('action', exportUrl);
					    	$form.attr('method', 'post');
							$(document.body).append($form);
					    	$form.submit();
					    	$form.remove();
					    	self.exportKeyword();
					    }
					});
			  });
			},
			exportKeyword : function() {
				$.messager.show({
					title:'温馨提示',
					msg:'正在导出，请稍候。。。',
					timeout:2000,
					showType:'slide'
				});
			},
			doSearch:function(updateTimeBeginSelector, updateTimeEndSelector){
				var self = this;
				var options = $('#news_grid').datagrid('getPager').data("pagination").options;  
		    	var pageNum = options.pageNumber;  
		    	var pageSize = options.pageSize; 
		    	//日期验证
		    	var updateTimeBegin = $(updateTimeBeginSelector).datebox('getValue');
		    	//日期验证
		    	if(updateTimeBegin && utils.isNotDate(updateTimeBegin)) {
		    		$.messager.alert('温馨提示', '请选择或输入正确的开始日期', 'warning');
		    		return;
		    	}
		    	var updateTimeEnd = $(updateTimeEndSelector).datebox('getValue');
		    	//日期验证
		    	if(updateTimeEnd && utils.isNotDate(updateTimeEnd)) {
		    		$.messager.alert('温馨提示', '请选择或输入正确的结束日期', 'warning');
		    		return;
		    	}
				var params = {
						'updateTimeBegin': updateTimeBegin,
						'updateTimeEnd': updateTimeEnd,
						"page":pageNum,
						"rows":pageSize,			
				};
				$('#news_grid').datagrid('load', params);
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