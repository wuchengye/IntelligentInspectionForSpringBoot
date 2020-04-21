/**
 * 工作负责人得分汇总
 */
define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	require('library/echarts4/echarts.min.js');
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/ticketCheckRes/';//模块界面根目录
	
	var getDataUrl = moduleRequestRootUrl + "getResultData";
	var getChartDataUrl = moduleRequestRootUrl + "getChartData";
	var getExceptionBarDataUrl = moduleRequestRootUrl + "getExceptionBarData";
	var getPieDataUrl = moduleRequestRootUrl + "getPieData";
	var popUrl = moduleRequestRootUrl +'getCheckDialog'
	var batchManageDialogUrl = moduleRequestRootUrl + "openBatchManageDialog";
	var getManageBatchUrl = moduleRequestRootUrl + "getManageBatch";
	
	var width='500px';
	var height='400px';
//	var myPie1, myPie2, myChart2, myChart3, myChart4, myChart5;

	var CheckResult = function(){
		this.width;
		this.height;
		
		this.$dataGrid;
		this.$toolbar;
		this.$dialog;
		this.$form;
		
		this.$chartToolBar;
		if (typeof String.prototype.endsWith != 'function') {  
			 String.prototype.endsWith = function(suffix) {  
			  return this.indexOf(suffix, this.length - suffix.length) !== -1;  
			 };  
		} 
	}
	CheckResult.prototype = {
		constructor: CheckResult,

		init:function() {
			extend.queryCombotree("#department");
			var self = this;
			String.prototype.endWith=function(str){
			    if(str==null||str==""||this.length==0||str.length>this.length)
			        return false;
			    if(this.substring(this.length-str.length)==str)
			        return true;
			    else
			        return false;
			    return true;
			}
			self.$dataGrid = $('#data_grid');
			self.$toolbar = $('#myToolBar');
			self.$dialog = $('#myDialog');
			self.$form = $('#myForm');
			
			function formatVal(val){
				if(val == "0") return "合格";
				else if(val == "1" || val == "2") return "不合格";
				//else if(val == "2") return "异常";
				else return "合格";
			}
			function formatGfVal(val){
				if(val == "0") return "规范";
				else if(val == "1" || val == "2") return "不规范";
				//else if(val == "2") return "异常";
				else return "规范";
			}
			
			var Columns = [[
				{field:'ticketNo',title:'工作票票号',width:120,align:'center',
					formatter: function(val, row, index) {
						if(val==null){
							val = ""
						}
						return "<span style='color:#0AA1ED' title='点击弹出工作票信息' onclick='$(this).parent().parent().parent().trigger(\"dblclick\")' >"+val+"<span/>";
					}
				},
				{field:'ticketType',title:'工作票种类',width:130,align:'center'},
				{field:'classes',title:'班组',width:150,align:'center'},
				{field:'ticketFinalTime',title:'工作票终结时间',width:135,align:'center'},
				{field:'workPrincipal',title:'工作负责人',width:100,align:'center'},
				{field:'ticketSigner',title:'工作签发人',width:100,align:'center'},
				{field:'licensor',title:'工作许可人',width:100,align:'center'},
				{field:'checkResult',title:'合格校验结果',width:100,align:'center',formatter:formatVal},
				{field:'misuseTicket',title:'合格判据1',width:80,align:'center',formatter:formatVal},
				{field:'beyondPlan',title:'合格判据2',width:80,align:'center',formatter:formatVal},
				{field:'keywordError',title:'合格判据3',width:80,align:'center',formatter:formatVal},
				{field:'hg_4',title:'合格判据4',width:80,align:'center',formatter:formatVal},
				{field:'workMemberCount',title:'合格判据5',width:80,align:'center',formatter:formatVal},
				{field:'fillinTaskError',title:'合格判据6',width:80,align:'center',formatter:formatVal},
				{field:'fillinSafeError',title:'合格判据7',width:80,align:'center',formatter:formatVal},
				{field:'doubleIssueError',title:'合格判据8',width:80,align:'center',formatter:formatVal},
				{field:'emptyPermissTime',title:'合格判据9',width:80,align:'center',formatter:formatVal},
				{field:'handleChange',title:'合格判据10',width:80,align:'center',formatter:formatVal},
				{field:'handleDelay',title:'合格判据11',width:80,align:'center',formatter:formatVal},
				{field:'hg_12',title:'合格判据12',width:80,align:'center',formatter:formatVal},
				{field:'finalContentError',title:'合格判据13',width:80,align:'center',formatter:formatVal},
				{field:'licensorNoSign',title:'合格判据14',width:80,align:'center',formatter:formatVal},
				{field:'keepMultiple',title:'合格判据15',width:80,align:'center',formatter:formatVal},
				{field:'signError',title:'合格判据16',width:80,align:'center',formatter:formatVal},
				{field:'hg_17',title:'合格判据17',width:80,align:'center',formatter:formatVal},
				{field:'standardResult',title:'规范校验结果',width:100,align:'center',formatter:formatGfVal},
				{field:'gf_1',title:'规范判据1',width:80,align:'center',formatter:formatGfVal},
				{field:'gf_2',title:'规范判据2',width:80,align:'center',formatter:formatGfVal},
				{field:'gf_3',title:'规范判据3',width:80,align:'center',formatter:formatGfVal},
				{field:'noSave',title:'规范判据4',width:80,align:'center',formatter:formatGfVal},
				{field:'nonstandardWord',title:'规范判据5',width:80,align:'center',formatter:formatGfVal},
				{field:'gf_6',title:'规范判据6',width:80,align:'center',formatter:formatGfVal},
				{field:'gf_7',title:'规范判据7',width:80,align:'center',formatter:formatGfVal}
			]]
			
			self.$dataGrid.datagrid({
				url:getDataUrl,
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
				queryParams:self.getFormData(),
				onDblClickRow:function(index,row){
					if(row.ticketType==""){
						$.messager.alert('温馨提示', '工作票种类信息错误', 'error');
						return false;
					}

					if(row.ticketType!="厂站第一种工作票"&&row.ticketType!="线路第一种工作票"&&row.ticketType!="厂站第二种工作票"&&row.ticketType!="厂站第三种工作票"
						&&row.ticketType!="线路第二种工作票"&&row.ticketType!="紧急抢修工作票"&&row.ticketType!="一级动火工作票"&&row.ticketType!="二级动火工作票"&&row.ticketType!="低压配电网工作票"&&row.ticketType!="带电作业工作票"){

					

						$.messager.alert('温馨提示', '此票种正在开发中', 'info');
						return false;
					}
					console.log(row);
					self.$dialog.dialog({
		        		title:"工作票信息",
		        		width:'80%',
		        		height:'85%',
		        		href:popUrl,
		        		queryParams:{ticketID:row.ticketId,batchTime:row.batchTime},
		        		//content:"<img src='assets/module/electric/workticket.png' style='width:820px'/>",
		        		resizable:true,
		        		cache: false,    
		        	    modal: true 
		        	})
				},
				
//				data:[
//				    {ticketId:"20020190001",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2017-04-01 10:00:00",licensor:"文凯",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"1",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"1",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190002",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2017-04-28 19:00:00",licensor:"方贤金",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"1",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"1",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190003",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2018-01-01 21:00:00",licensor:"苏毅聪",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"1",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190004",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2018-03-05 19:20:00",licensor:"邱宏彬",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"1",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190005",ticketType:"厂站第三种工作票",classes:"电缆二班",ticketFinalTime:"2019-01-20 18:00:00",licensor:"刘东朗",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"1",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190006",ticketType:"线路第一种工作票",classes:"输电管理所电缆二班",ticketFinalTime:"2018-05-18 19:20:00",licensor:"何维才",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",fillinSafeError:"0",doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190007",ticketType:"线路第一种工作票",classes:"输电管理所检修一班",ticketFinalTime:"2017-06-30 18:20:00",licensor:"朱锡华",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"1",emptyPermissTime:"0",handleChange:"0",handleDelay:"1",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190008",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2018-10-10 18:09:59",licensor:"曾令力",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190009",ticketType:"线路第二种工作票",classes:"输电管理所检修三班",ticketFinalTime:"2018-07-27 18:00:00",licensor:"刁百家",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"1",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190010",ticketType:"线路第一种工作票",classes:"输电管理所城北电缆四班",ticketFinalTime:"2018-03-01 18:20:00",licensor:"陈永强",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190011",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2018-01-01 18:29:59",licensor:"丁广智",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190012",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2017-05-18 18:00:00",licensor:"黄健伦",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190013",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2018-08-18 12:00:00",licensor:"李浩恩",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"1",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"1",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190014",ticketType:"线路第一种工作票",classes:"广东省输变电工程公司输电第二分公司线路一队",ticketFinalTime:"2018-04-20 19:00:00",licensor:"彭伟强",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"1",emptyPermissTime:"0",handleChange:"0",handleDelay:"1",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190015",ticketType:"厂站第二种工作票",classes:"检修试验班",ticketFinalTime:"2018-08-08 18:29:59",licensor:"苏活基",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190016",ticketType:"厂站第二种工作票",classes:"检修试验班",ticketFinalTime:"2018-09-30 16:39:59",licensor:"蔡红旗",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"1",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190017",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2018-02-22 15:00:00",licensor:"李四四",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190018",ticketType:"厂站第二种工作票",classes:"电缆二班",ticketFinalTime:"2018-11-04 18:49:59",licensor:"邓炳坤",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190019",ticketType:"厂站第一种工作票",classes:"运维三班",ticketFinalTime:"2018-12-12 19:20:00",licensor:"黄耀峰",checkResult:"0",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"0",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"0",	standardResult:"0",noSave:"0",nonstandardWord:"0"},
//				    {ticketId:"20020190020",ticketType:"厂站第一种工作票",classes:"检修试验班",ticketFinalTime:"2017-04-01 19:20:00",licensor:"林惠贤",checkResult:"1",misuseTicket:"0",beyondPlan:"0",keywordError:"0",workMemberCount:"0",	fillinTaskError:"0",	fillinSafeError:"0",	doubleIssueError:"0",emptyPermissTime:"0",handleChange:"0",handleDelay:"1",finalContentError:"0",licensorNoSign:"0",keepMultiple:"0",signError:"1",	standardResult:"0",noSave:"0",nonstandardWord:"0"}
//				]
				
			});
			
			self.initEleEvent();
		},
		initEleEvent:function(){
			var self = this	;
			
			self.$toolbar.on("click",".bda-btn-query",function(){
				self.$dataGrid.datagrid("load",self.getFormData());
				return false;
			})
			.on("click",".bda-btn-reset",function(){
				self.$form.form('reset');
				return false;
			})
			
			$("#ticketType").combobox({
				valueField:'value',
				textField:'text',
				panelHeight:'auto',
				panelMaxHeight:250,
				editable:true,
				data:[
				      {value:'',text:'--请选择--',selected:true},
				      {value:'线路第一种工作票',text:'线路第一种工作票'},{value:'线路第二种工作票',text:'线路第二种工作票'},
				      {value:'厂站第一种工作票',text:'厂站第一种工作票'},{value:'厂站第二种工作票',text:'厂站第二种工作票'},
				      {value:'厂站第三种工作票',text:'厂站第三种工作票'},
				      {value:'一级动火工作票',text:'一级动火工作票'},{value:'二级动火工作票',text:'二级动火工作票'},
				      {value:'紧急抢修工作票',text:'紧急抢修工作票'},
				      {value:'带电作业工作票',text:'带电作业工作票'},
				      {value:'低压配电网工作票',text:'低压配电网工作票'},
				]
			})
			
		},
		
		getChartData:function(){
			extend.queryCombotree("#department");
			var self = this;
			self.$chartToolBar = $("#chartToolBar");
			self.$form = $("#myForm");
			self.$batchManageDialog = $("#batch_manage_dialog");
			
			var newBatch = parent.$("#p_newBatch").val();
			if(newBatch){
				parent.$('#p_ticketType').val("");
				parent.$('#p_workPrincipal').val( "" );
				parent.$('#p_ticketSigner').val( "" );
				parent.$('#p_workLicensor').val( "" );
				parent.$('#p_department').val("");
				parent.$('#p_ticketEndTime1').val( "");
				parent.$('#p_ticketEndTime2').val("");
				parent.$('#p_orgids').val("");
				
				parent.$("#p_batchTime").val(newBatch);
				$("#batch").combobox({
					onLoadSuccess:function(){
						$(this).combobox("select",newBatch);
					}
				});
				doSearch();
				doSearchPie();
				//doSearchException();
				parent.$("#p_newBatch").val("");
				parent.$("#p_batchTime").val("");
				return false;
			}
			
			self.initPie1(null);
			self.initPie2(null);
			self.initBar2(null);
			self.initBar3(null);
			//self.initBar4(null);
			//self.initBar5(null);
			//doSearch();
			//doSearchPie();
			//doSearchException();
			
			self.$chartToolBar.on("click",".bda-btn-query",function(){
				if(!self.$form.form("validate")){
					$.messager.alert("提示","请录入必填条件！","warning");
					return false;
				}
				parent.$('#p_ticketType').val("");
	        	parent.$('#p_batchTime').val( "" );
				parent.$('#p_workPrincipal').val( "" );
				parent.$('#p_ticketSigner').val( "" );
				parent.$('#p_workLicensor').val( "" );
				parent.$('#p_department').val("");
				parent.$('#p_ticketEndTime1').val( "");
				parent.$('#p_ticketEndTime2').val("");
				parent.$('#p_orgids').val("");
				doSearch();
				doSearchPie();
				//doSearchException();
				return false;
			})
			.on("click",".bda-btn-reset",function(){
				self.$form.form('reset');
				return false;
			})
			.on("click",".bda-btn-manage",function(){
				self.$batchManageDialog.dialog({
					href:batchManageDialogUrl,
					title:"批次管理",
					width:550,
					height:450,
					modal:true,
					buttons:[
						{
							text:'关闭',
							handler:function(){
								self.$batchManageDialog.dialog("close");
							}
						}
					],
					resizable:true
				});
				return false;
			});
			
			function doSearch(){
				
				var myChart2 = echarts.init(document.getElementById('bar_2'));
				myChart2.showLoading({text:'正在加载数据...'});
				var myChart3 = echarts.init(document.getElementById('bar_3'));
				myChart3.showLoading({text:'正在加载数据...'});
				
				var data ;
				$.ajax({
					url:getChartDataUrl,
					type:"post",
					data:self.getFormData(),
					cache:false,
					dataType:"json",
					success:function(resp){
						myChart2.hideLoading();
						myChart3.hideLoading();
						if(resp.meta.success){
							data = resp.data[0];
							if(null == data || undefined == data){
								$.messager.alert("提示","查无数据！","warning");
								return false;
							}
							self.initBar2(data);
							self.initBar3(data);
						}
					}
				})
				return data; 
			}
			
			function doSearchException(){
				var data ;
				$.ajax({
					url:getExceptionBarDataUrl,
					type:"post",
					data:self.getFormData(),
					cache:false,
					dataType:"json",
					success:function(resp){
						if(resp.meta.success){
							data = resp.data[0];
							if(null == data || undefined == data){
								$.messager.alert("提示","查无数据！","warning");
								return false;
							}
							self.initBar4(data);
							self.initBar5(data);
						}
					}
				})
				return data; 
			}
			
			function doSearchPie(){
				
				var myPie1 = echarts.init(document.getElementById('pie_1'));
				myPie1.showLoading({text:'正在加载数据...'});
				var myPie2 = echarts.init(document.getElementById('pie_2'));
				myPie2.showLoading({text:'正在加载数据...'});
				
				var data ;
				$.ajax({
					url:getPieDataUrl,
					type:"post",
					data:self.getFormData(),
					cache:false,
					dataType:"json",
					success:function(resp){
						myPie1.hideLoading();
						myPie2.hideLoading();
						if(resp.meta.success){
							data = resp.data[0];
							if(null == data || undefined == data){
								$.messager.alert("提示","查无数据！","warning");
								return false;
							}
							self.initPie1(data);
							self.initPie2(data);
						}
					}
				})
				return data; 
			}
			
		},
		
		initPie1:function(data){
			var self = this	;
			if(null != data){
			var cntQualified = data.cnt_qualified,
			    cntUnqualified = data.cnt_unqualified,
			    cntException = data.cnt_exception;
			}
			var option = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
				    title : {
				        text: '工作票合格性总体统计',
				        x:'center'
				    },
				    color:['#F2707A','#3078CA'/*,'#33C15D'*/],
				    legend: {
				    	selectedMode:false,
				    	orient: 'horizontal',       // 'vertical'
				        x: 'center',                // 'center' | 'left' | {number},
				        y: 'bottom',                // 'center' | 'bottom' | {number}
				        data:['合格','不合格'/*,'异常'*/]
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    calculable : true,
				    series : [
				        {
				            name:'校验情况',
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '50%'],
				            itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'outer',
							            formatter: '{c} ({d}%)'
							        }
							    }
							},
				            data:[
				                {value:cntQualified, name:'合格'},
				                {value:cntUnqualified, name:'不合格'},
				                //{value:cntException, name:'异常'}
				            ]
				        }
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myPie1 = echarts.init(document.getElementById('pie_1'));
	        // 使用刚指定的配置项和数据显示图表。
	        myPie1.setOption(option);
	        
	        myPie1.on('click', function (params) {
	        	var parent$ = parent.$; 
	        	
	        	var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/ticketCheckResult.jsp" style="width:100%;height:100%;"></iframe></div>';
	        	parent$('#type').val("hg" + params.name);
	        	
	        	var department = $("#department").combotree("getText");
				var ids = new Array();
		    	if(department != null && department != "" && department != "广州供电局有限公司"){
					var t = $("#department").combotree('tree');  
					var selected = t.tree("getSelected");
					ids = self.getOrgIds(ids, selected);
				}
		    	var orgIds = "";
		    	for(var i = 0; i < ids.length; i++){
		    		if(orgIds.length > 0){
		    			orgIds += (",\'" + ids[i] + "\'");
		    			continue;
		    		}
		    		orgIds += ("\'" + ids[i] + "\'");
		    	}
	        	
	        	parent$('#p_ticketType').val($("#ticketType").combobox("getValue"));
	        	parent$('#p_batchTime').val( $("#batch").combobox("getValue") );
				parent$('#p_workPrincipal').val( $("#workPrincipal").textbox("getValue") );
				parent$('#p_ticketSigner').val( $("#ticketSigner").textbox("getValue") );
				parent$('#p_workLicensor').val( $("#workLicensor").textbox("getValue") );
				parent$('#p_department').val(department);
				parent$('#p_ticketEndTime1').val( $("#startTime").datetimebox("getValue"));
				parent$('#p_ticketEndTime2').val($("#endTime").datetimebox("getValue"));
				parent$('#p_orgids').val(orgIds);
				
	        	
	        	var tabs = parent$('.js-tabs').tabs("tabs");
	        	for(var i = 0; i < tabs.length; i++){
	        		
	        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
	        		if(title.endsWith("结果")){
	        			parent$('.js-tabs').tabs('select',i);
	        			parent$('.js-tabs').tabs('update',{
	        				tab:parent$('.js-tabs').tabs('getTab',i),
	        				type:"all",
	        				options:{
	        					title:"工作票校验结果",
	        					content:content
	        				}
	        			});
	        			return false;
	        		}
	        	}
	        	
	        	parent$('.js-tabs').tabs('add',{       //实现add方法
	    			title:"工作票校验结果",
	    			content:content,
					closable:true,
					fit:true
	    		});
	        });
	        
		},
		
		initPie2:function(data){
			var self = this	;
			
			if(null != data){
			var cntStandard = data.cnt_standard,
			    cntNonstandard = data.cnt_nonstandard,
			    cntStdException = data.cnt_std_exception;
			}
			var option = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
				    title : {
				        text: '工作票规范性总体统计',
				        x:'center'
				    },
				    color:['#F2707A','#FFBA56'/*,'#33C15D'*/],
				    legend: {
				    	selectedMode:false,
				    	orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: 'bottom', // 'center' | 'bottom' | {number}
				        data:['规范', '不规范'/*,'异常'*/]
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    calculable : true,
				    series : [
				        {
				            name:'校验情况',
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '50%'],
				            itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'outer',
							            formatter: '{c} ({d}%)'
							        }
							    }
							},
				            data:[
				                {value:cntStandard, name:'规范'},
				                {value:cntNonstandard, name:'不规范'},
				                //{value:cntStdException, name:'异常'}
				            ]
				        }
				    ]
				};
			
			var myPie2 = echarts.init(document.getElementById('pie_2'));
			myPie2.setOption(option);
	        
			myPie2.on('click', function (params) {
				var parent$ = parent.$; 
				var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/ticketCheckResult.jsp?type=hege" style="width:100%;height:100%;"></iframe></div>';
				parent$('#type').val("gf" + params.name);
				
				var department = $("#department").combotree("getText");
				var ids = new Array();
		    	if(department != null && department != "" && department != "广州供电局有限公司"){
					var t = $("#department").combotree('tree');  
					var selected = t.tree("getSelected");
					ids = self.getOrgIds(ids, selected);
				}
		    	var orgIds = "";
		    	for(var i = 0; i < ids.length; i++){
		    		if(orgIds.length > 0){
		    			orgIds += (",\'" + ids[i] + "\'");
		    			continue;
		    		}
		    		orgIds += ("\'" + ids[i] + "\'");
		    	}
	        	
	        	parent$('#p_ticketType').val($("#ticketType").combobox("getValue"));
	        	parent$('#p_batchTime').val( $("#batch").combobox("getValue") );
				parent$('#p_workPrincipal').val( $("#workPrincipal").textbox("getValue") );
				parent$('#p_ticketSigner').val( $("#ticketSigner").textbox("getValue") );
				parent$('#p_workLicensor').val( $("#workLicensor").textbox("getValue") );
				parent$('#p_department').val(department);
				parent$('#p_ticketEndTime1').val( $("#startTime").datetimebox("getValue"));
				parent$('#p_ticketEndTime2').val($("#endTime").datetimebox("getValue"));
				parent$('#p_orgids').val(orgIds);
				
	        	var tabs = parent$('.js-tabs').tabs("tabs");
	        	for(var i = 0; i < tabs.length; i++){
	        		
	        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
	        		if(title.endsWith("结果")){
	        			parent$('.js-tabs').tabs('select',i);
	        			parent$('.js-tabs').tabs('update',{
	        				tab:parent$('.js-tabs').tabs('getTab',i),
	        				type:"all",
	        				options:{
	        					title:"工作票校验结果",
	        					content:content
	        				}
	        			});
	        			return false;
	        		}
	        	}
	        	
	        	parent$('.js-tabs').tabs('add',{       //实现add方法
	        		id:"tab_new",
	    			title:'工作票校验结果',
	    			content:content,
					closable:true,
					fit:true
	    		});
	        });
	        
		},
		
		initBar2:function(data){
			var self = this	;
			
			if(null != data){
			var cntMisuse = data.cnt_misuse, cntPlan = data.cnt_plan, 
			cntKeyWord = data.cnt_keyword_error, cntMenber = data.cnt_member,
		    cntFillinTask = data.cnt_fillintask, cntFillinSafe = data.cnt_fillinsafe, 
		    cntDoubleIssue = data.cnt_doubleissue, cntEmptyPmis = data.cnt_emptypmis,
		    cntChange = data.cnt_change, cntDelay = data.cnt_delay, 
		    cntFinalContent = data.cnt_finalcontent, cntLicensorSign = data.cnt_licensornosign,
		    cntMultiple = data.cnt_multiple, cntSignError = data.cnt_signerror, 
		    cntNoSave = data.cnt_nosave, cntNonstandard = data.cnt_nonstandard;
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "不合格票分类统计",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        },
				        formatter:function(val){
				        	return self.qualifiedTipFormatter(val);
				        }
				    },
				    grid: {
		                left: '5%',
		                right: '10%',
		                bottom: '3%',
		                containLabel: true
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	name : '合格判据',
				        	data : ["判据1","判据2","判据3","判据4","判据5","判据6","判据7","判据8","判据9","判据10","判据11","判据12","判据13","判据14","判据15","判据16","判据17"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45",
				                color: function(val) {

		                            // build a color map as your need.
		                            var colorList = ['#F2707A','#00B050','#3078CA'];//红 绿   蓝
		                            if(val == '判据4' || val == '判据12' || val == '判据17'){
		                            	return colorList[0];
		                            }else{
		                            	return colorList[2];
		                            }
		                        }
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '不合格票数量'
				        }
				    ],
				    series : [
				        {
							data: [cntMisuse?cntMisuse:0, cntPlan?cntPlan:0, cntKeyWord?cntKeyWord:0, 0, cntMenber?cntMenber:0, cntFillinTask?cntFillinTask:0, 
								   cntFillinSafe?cntFillinSafe:0, cntDoubleIssue?cntDoubleIssue:0, cntEmptyPmis?cntEmptyPmis:0, cntChange?cntChange:0, 
							       cntDelay?cntDelay:0, 0, cntFinalContent?cntFinalContent:0, cntLicensorSign?cntLicensorSign:0, cntMultiple?cntMultiple:0, 
							       cntSignError?cntSignError:0 , 0],
							type: 'bar',
							itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'top'
							        },
							        color: function(params) {

			                            // build a color map as your need.
			                            var colorList = ['#F2707A','#00B050','#3078CA'];//红 绿   蓝
			                            if(params.dataIndex == '3' || params.dataIndex == '11' || params.dataIndex == '16'){
			                            	return colorList[0];
			                            }else{
			                            	return colorList[2];
			                            }
			                        },
							    }
							},
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_2'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
	        
	        myChart2.on('click', function (params) {
				var parent$ = parent.$; 
				var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/ticketCheckResult.jsp?type=hege" style="width:100%;height:100%;"></iframe></div>';
				parent$('#type').val("hg"+params.name);
				
				var department = $("#department").combotree("getText");
				var ids = new Array();
		    	if(department != null && department != "" && department != "广州供电局有限公司"){
					var t = $("#department").combotree('tree');  
					var selected = t.tree("getSelected");
					ids = self.getOrgIds(ids, selected);
				}
		    	var orgIds = "";
		    	for(var i = 0; i < ids.length; i++){
		    		if(orgIds.length > 0){
		    			orgIds += (",\'" + ids[i] + "\'");
		    			continue;
		    		}
		    		orgIds += ("\'" + ids[i] + "\'");
		    	}
	        	
	        	parent$('#p_ticketType').val($("#ticketType").combobox("getValue"));
	        	parent$('#p_batchTime').val( $("#batch").combobox("getValue") );
				parent$('#p_workPrincipal').val( $("#workPrincipal").textbox("getValue") );
				parent$('#p_ticketSigner').val( $("#ticketSigner").textbox("getValue") );
				parent$('#p_workLicensor').val( $("#workLicensor").textbox("getValue") );
				parent$('#p_department').val(department);
				parent$('#p_ticketEndTime1').val( $("#startTime").datetimebox("getValue"));
				parent$('#p_ticketEndTime2').val($("#endTime").datetimebox("getValue"));
				parent$('#p_orgids').val(orgIds);
				
				var tabs = parent$('.js-tabs').tabs("tabs");
	        	for(var i = 0; i < tabs.length; i++){
	        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
	        		if(title.endsWith("结果")){
	        			//parent$('.js-tabs').tabs('getTab',i).panel({'title':'工作票校验结果'});
	        			parent$('.js-tabs').tabs('select',i);
	        			parent$('.js-tabs').tabs('update',{
	        				tab:parent$('.js-tabs').tabs('getTab',i),
	        				type:"all",
	        				options:{
	        					title:"工作票校验结果",
	        					content:content
	        				}
	        			});
	        			return false;
	        		}
	        	}
	        	
	        	parent$('.js-tabs').tabs('add',{       //实现add方法
	        		id:"tab_new",
	    			title:'工作票校验结果',
	    			content:content,
					closable:true,
					fit:true
	    		});
	        });
	        
//	        myChart2.on("mouseover",function(params){
//	        	if("xAxis"==params.componentType){
//                    var offsetX =params.event.offsetX+10;
//                    var offsetY =params.event.offsetY+10;
//                    if(params.value=="判据1"){
//                    	isXLabel=true;
//                    	myChart2.dispatchAction({
//                            type: 'showTip',
//                            seriesIndex: 0,
//                            dataIndex: 0,
//                            position:[offsetX,offsetY]
//                        });
//                    }
//	        	}
//	        });
//	        myChart2.on("mouseout",function(params){
//	        	isXLabel=false;
//	        });
			
		},
		
		initBar3:function(data){
			var self = this	;
			if(null != data){
			var cntNoSave = data.cnt_nosave, 
			    cntNonstandard = data.cnt_nonstandard;
			}
			var option3 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "不规范票分类统计",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        },
				        formatter:function(val){
				        	return self.standardTipFormatter(val);
				        }
				    },
				    grid: {
		                left: '6%',
		                right: '20%',
		                bottom: '4%',
		                containLabel: true
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	name : '规范判据',
				        	data : ["判据1","判据2","判据3","判据4","判据5","判据6","判据7"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				                interval:0,
				                rotate:"45",
				                color:function(val){
				                	// build a color map as your need.
		                            var colorList = ['#F2707A','#00B050','#FFBA56'];//红 绿   蓝
		                            if(val == '判据4'){
		                            	return colorList[1];
		                            }else if(val == '判据5'){
		                            	return colorList[2];
		                            }else{
		                            	return colorList[0];
		                            }
				                }
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '不规范票数量'
				        }
				    ],
				    series : [
				        {
							data: [0, 0, 0, cntNoSave?cntNoSave:0 ,cntNonstandard?cntNonstandard:0, 0, 0],
							type: 'bar',
							itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'top'
							        },
							        color: function(params) {

			                            // build a color map as your need.
			                            var colorList = ['#F2707A','#00B050','#FFBA56'];//红 绿   蓝
			                            if(params.dataIndex == '3'){
			                            	return colorList[1];
			                            }else if(params.dataIndex == '4'){
			                            	return colorList[2];
			                            }else{
			                            	return colorList[0];
			                            }
			                        },
							    }
							},
				    	}
				        
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart3 = echarts.init(document.getElementById('bar_3'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart3.setOption(option3);
	        
	        myChart3.on('click', function (params) {
				var parent$ = parent.$; 
				var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/ticketCheckResult.jsp?type=hege" style="width:100%;height:100%;"></iframe></div>';
				parent$('#type').val("gf"+params.name);
				
				var department = $("#department").combotree("getText");
				var ids = new Array();
		    	if(department != null && department != "" && department != "广州供电局有限公司"){
					var t = $("#department").combotree('tree');  
					var selected = t.tree("getSelected");
					ids = self.getOrgIds(ids, selected);
				}
		    	var orgIds = "";
		    	for(var i = 0; i < ids.length; i++){
		    		if(orgIds.length > 0){
		    			orgIds += (",\'" + ids[i] + "\'");
		    			continue;
		    		}
		    		orgIds += ("\'" + ids[i] + "\'");
		    	}
	        	
	        	parent$('#p_ticketType').val($("#ticketType").combobox("getValue"));
	        	parent$('#p_batchTime').val( $("#batch").combobox("getValue") );
				parent$('#p_workPrincipal').val( $("#workPrincipal").textbox("getValue") );
				parent$('#p_ticketSigner').val( $("#ticketSigner").textbox("getValue") );
				parent$('#p_workLicensor').val( $("#workLicensor").textbox("getValue") );
				parent$('#p_department').val(department);
				parent$('#p_ticketEndTime1').val( $("#startTime").datetimebox("getValue"));
				parent$('#p_ticketEndTime2').val($("#endTime").datetimebox("getValue"));
				parent$('#p_orgids').val(orgIds);
				
				var tabs = parent$('.js-tabs').tabs("tabs");
	        	for(var i = 0; i < tabs.length; i++){
	        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
	        		if(title.endsWith("结果")){
	        			//parent$('.js-tabs').tabs('getTab',i).panel({'title':'工作票校验结果'});
	        			parent$('.js-tabs').tabs('select',i);
	        			parent$('.js-tabs').tabs('update',{
	        				tab:parent$('.js-tabs').tabs('getTab',i),
	        				type:"all",
	        				options:{
	        					title:"工作票校验结果",
	        					content:content
	        				}
	        			});
	        			return false;
	        		}
	        	}
	        	
	        	parent$('.js-tabs').tabs('add',{       //实现add方法
	        		id:"tab_new",
	    			title:'工作票校验结果',
	    			content:content,
					closable:true,
					fit:true
	    		});
	        });
			
		},
		
		initBar4:function(data){
			var self = this	;
			if(null != data){
			var cntMisuse = data.cnt_misuse, cntPlan = data.cnt_plan, 
			cntKeyWord = data.cnt_keyword_error, cntMenber = data.cnt_member,
		    cntFillinTask = data.cnt_fillintask, cntFillinSafe = data.cnt_fillinsafe, 
		    cntDoubleIssue = data.cnt_doubleissue, cntEmptyPmis = data.cnt_emptypmis,
		    cntChange = data.cnt_change, cntDelay = data.cnt_delay, 
		    cntFinalContent = data.cnt_finalcontent, cntLicensorSign = data.cnt_licensornosign,
		    cntMultiple = data.cnt_multiple, cntSignError = data.cnt_signerror, 
		    cntNoSave = data.cnt_nosave, cntNonstandard = data.cnt_nonstandard;
			}
			var option4 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "合格性异常票分类统计",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        },
				        formatter:function(val){
				        	return self.qualifiedTipFormatter(val);
				        }
				    },
				    grid: {
		                left: '5%',
		                right: '10%',
		                bottom: '3%',
		                containLabel: true
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	name : '合格判据',
				        	data : ["判据1","判据2","判据3","判据4","判据5","判据6","判据7","判据8","判据9","判据10","判据11","判据12","判据13","判据14","判据15","判据16","判据17"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				                interval:0,
				                rotate:"45",
				                color: function(val) {

		                            // build a color map as your need.
		                            var colorList = ['#F2707A','#00B050','#3078CA'];//红 绿   蓝
		                            if(val == '判据4' || val == '判据12' || val == '判据17'){
		                            	return colorList[0];
		                            }else if(val == '判据2'){
		                            	return colorList[1];
		                            }else{
		                            	return colorList[2];
		                            }
		                        }
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '异常票数量'
				        }
				    ],
				    series : [
				        {
							data: [cntMisuse?cntMisuse:0, cntPlan?cntPlan:0, cntKeyWord?cntKeyWord:0, 0, cntMenber?cntMenber:0, cntFillinTask?cntFillinTask:0, 
								   cntFillinSafe?cntFillinSafe:0, cntDoubleIssue?cntDoubleIssue:0, cntEmptyPmis?cntEmptyPmis:0, cntChange?cntChange:0, 
								   cntDelay?cntDelay:0, 0, cntFinalContent?cntFinalContent:0, cntLicensorSign?cntLicensorSign:0, cntMultiple?cntMultiple:0, 
								   cntSignError?cntSignError:0 , 0],
//				        	data: [0, 0, 0, 0, 0, 0, 0, 0, 
//							       0, 0, 0, 0, 0, 0, 0, 0 , 0],
							type: 'bar',
							itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'top'
							        },
							        color: function(params) {

			                            // build a color map as your need.
			                            var colorList = ['#F2707A','#00B050','#3078CA'];//红 绿   蓝
			                            if(params.dataIndex == '3' || params.dataIndex == '11' || params.dataIndex == '16'){
			                            	return colorList[0];
			                            }else if(params.dataIndex == '1'){
			                            	return colorList[1];
			                            }else{
			                            	return colorList[2];
			                            }
			                        },
							    }
							},
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart4 = echarts.init(document.getElementById('bar_4'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart4.setOption(option4);
	        
	        myChart4.on('click', function (params) {
				var parent$ = parent.$; 
				var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/ticketCheckResult.jsp?type=hege" style="width:100%;height:100%;"></iframe></div>';
				parent$('#type').val("hgyc"+params.name);
				
				parent$('#p_ticketType').val($("#ticketType").combobox("getValue"));
	        	parent$('#p_batchTime').val( $("#batch").combobox("getValue") );
				parent$('#p_workPrincipal').val( $("#workPrincipal").textbox("getValue") );
				parent$('#p_ticketSigner').val( $("#ticketSigner").textbox("getValue") );
				parent$('#p_workLicensor').val( $("#workLicensor").textbox("getValue") );
				parent$('#p_department').val($("#department").combobox("getValue"));
				parent$('#p_ticketEndTime1').val( $("#startTime").datetimebox("getValue"));
				parent$('#p_ticketEndTime2').val($("#endTime").datetimebox("getValue"));
				
				var tabs = parent$('.js-tabs').tabs("tabs");
	        	for(var i = 0; i < tabs.length; i++){
	        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
	        		if(title.endsWith("结果")){
	        			parent$('.js-tabs').tabs('select',i);
	        			parent$('.js-tabs').tabs('update',{
	        				tab:parent$('.js-tabs').tabs('getTab',i),
	        				type:"all",
	        				options:{
	        					title:"工作票校验结果",
	        					content:content
	        				}
	        			});
	        			return false;
	        		}
	        	}
	        	
	        	parent$('.js-tabs').tabs('add',{       //实现add方法
	        		id:"tab_new",
	    			title:'工作票校验结果',
	    			content:content,
					closable:true,
					fit:true
	    		});
	        });
			
		},
		
		initBar5:function(data){
			var self = this	;
			if(null != data){
			var cntNoSave = data.cnt_nosave, 
			    cntNonstandard = data.cnt_nonstandard;
			}
			var option5 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "规范性异常票分类统计",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        },
				        formatter:function(val){
				        	return self.standardTipFormatter(val);
				        }
				    },
				    grid: {
		                left: '6%',
		                right: '20%',
		                bottom: '4%',
		                containLabel: true
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	name : '规范判据',
				        	data : ["判据1","判据2","判据3","判据4","判据5","判据6","判据7"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				                interval:0,
				                rotate:"45",
				                color:function(val){
				                	// build a color map as your need.
		                            var colorList = ['#F2707A','#00B050','#FFBA56'];//红 绿   蓝
		                            if(val == '判据4'){
		                            	return colorList[1];
		                            }else if(val == '判据5'){
		                            	return colorList[2];
		                            }else{
		                            	return colorList[0];
		                            }
				                }
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '异常票数量'
				        }
				    ],
				    series : [
				        {
							data: [0, 0, 0, cntNoSave?cntNoSave:0 ,cntNonstandard?cntNonstandard:0, 0, 0],
//				        	data: [0, 0, 0, 0 ,0, 0, 0],
							type: 'bar',
							itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'top'
							        },
							        color: function(params) {

			                            // build a color map as your need.
			                            var colorList = ['#F2707A','#00B050','#FFBA56'];//红 绿   蓝
			                            if(params.dataIndex == '3'){
			                            	return colorList[1];
			                            }else if(params.dataIndex == '4'){
			                            	return colorList[2];
			                            }else{
			                            	return colorList[0];
			                            }
			                        },
							    }
							},
				    	}
				        
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart5 = echarts.init(document.getElementById('bar_5'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart5.setOption(option5);
	        
	        myChart5.on('click', function (params) {
				var parent$ = parent.$; 
				var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/ticketCheckResult.jsp?type=hege" style="width:100%;height:100%;"></iframe></div>';
				parent$('#type').val("gfyc"+params.name);
				
				parent$('#p_ticketType').val($("#ticketType").combobox("getValue"));
	        	parent$('#p_batchTime').val( $("#batch").combobox("getValue") );
				parent$('#p_workPrincipal').val( $("#workPrincipal").textbox("getValue") );
				parent$('#p_ticketSigner').val( $("#ticketSigner").textbox("getValue") );
				parent$('#p_workLicensor').val( $("#workLicensor").textbox("getValue") );
				parent$('#p_department').val($("#department").combobox("getValue"));
				parent$('#p_ticketEndTime1').val( $("#startTime").datetimebox("getValue"));
				parent$('#p_ticketEndTime2').val($("#endTime").datetimebox("getValue"));
				
				var tabs = parent$('.js-tabs').tabs("tabs");
	        	for(var i = 0; i < tabs.length; i++){
	        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
	        		if(title.endsWith("结果")){
	        			parent$('.js-tabs').tabs('select',i);
	        			parent$('.js-tabs').tabs('update',{
	        				tab:parent$('.js-tabs').tabs('getTab',i),
	        				type:"all",
	        				options:{
	        					title:"工作票校验结果",
	        					content:content
	        				}
	        			});
	        			return false;
	        		}
	        	}
	        	
	        	parent$('.js-tabs').tabs('add',{       //实现add方法
	        		id:"tab_new",
	    			title:'工作票校验结果',
	    			content:content,
					closable:true,
					fit:true
	    		});
	        });
			
		},
		
		batchManage:function(){
			var self = this;
			self.$batchGrid = $("#batch_manage_grid");
			self.batchManageDialogBar = $("#batch_manage_dialog_bar");
			
			var Columns = [[
			    {field:"save_time",title:'批次',width:200,align:'center'}  
			]]
			
			self.$batchGrid.datagrid({
				url: getManageBatchUrl,
				columns: Columns,
				toolbar : self.batchManageDialogBar,
				singleSelect:true,
				loadMsg: '正在查询数据，请稍候...',
				rownumbers: true,
				pagination :true,
				pageList :[ 10,20,50,100],
				pageNumber:1,
				pageSize:20,
				fit: true
			});
			
			self.batchManageDialogBar.on("click",".bda-btn-deleteBatch",function(){
				var selected=self.$batchGrid.datagrid('getSelected');
				if(!selected){
					$.messager.alert('提示','请选择要删除的批次！','warning');
					return false;
				}
				$.messager.confirm('请确认', '确定删除该批次吗？', function(r){
					if (r){
						parent.$.messager.progress({
						    title : '提示',
						    text : '删除中，请稍后....'
						});
						var batch = selected.save_time;
						$.ajax({
							url:"electric/ticketCheckRes/delBatch",
							type:"post",
							data:{
								batch:batch
							},
							cache:false,
							dataType:"json",
							success:function(resp){
								if(resp.meta.success){
									parent.$.messager.progress("close");
									$.messager.alert("提示",resp.meta.code,"info");
									self.$batchGrid.datagrid("reload");
								}else{
									$.messager.alert("提示",resp.meta.code,"info");
								}
							}
						})
					    return false;
					}else{
						return false;
					}
				});
				
			});
			
		},
		
		qualifiedTipFormatter:function(val){
			switch(val[0].axisValue){
	        	case"判据1":
	        		return val[0].axisValue + "：错用工作票" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据2":
	        		return val[0].axisValue + "：计划工作时间超出计划<br>停电时间或已过期" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据3":
	        		return val[0].axisValue + "：工作票关键词字迹不清、<br>错漏" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据4":
	        		return val[0].axisValue + "：工作票非关键词的错、漏<br>的修改超过3处" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据5":
	        		return val[0].axisValue + "：工作票面工作班人名、人<br>数与实际不符" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据6":
	        		return val[0].axisValue + "：工作任务、停电线路名称<br>（包括电压等级及名称）、工作<br>地段、设备双重编号填写不明确、<br>错漏，工作内容与实际不符" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据7":
	        		return val[0].axisValue + "：安全措施不完备或填写不<br>正确" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据8":
	        		return val[0].axisValue + "：应“双签发”的工作票没有<br>“双签发”" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据9":
	        		return val[0].axisValue + "：工作许可时间未填写" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据10":
	        		return val[0].axisValue + "：工作负责人有变更时未办<br>理变更手续" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据11":
	        		return val[0].axisValue + "：应办理延期的工作未按要求办<br>理工作延期手续，需办理工作间断手续<br>的未办理或记录不全" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据12":
	        		return val[0].axisValue + "：未经许可人同意擅自增加工作<br>内容；增加工作内容时需变更或增设安<br>全措施者，未重新办理工作票" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据13":
	        		return val[0].axisValue + "：工作终结栏的内容应填写的无<br>填写" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据14":
	        		return val[0].axisValue + "：工作过程中需要变更安全措施<br>时，未经许可人签名同意" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据15":
	        		return val[0].axisValue + "：一个工作负责人同一许可工作<br>时段持有两张或以上的工作票" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据16":
	        		return val[0].axisValue + "：工作票中工作票‘三种人’签发<br>人（包括会签人）、工作负责人、工作许<br>可人 不具备资格的，或冒签名、漏签名、<br>签名不全" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据17":
	        		return val[0].axisValue + "：其它不符合《电力安全工作规<br>程》或南方电网《电气工作票技术规范<br>》规定可能会引发事故/事件的" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
        	}
		},
		
		standardTipFormatter:function(val){
			switch(val[0].axisValue){
	        	case"判据1":
	        		return val[0].axisValue + "：工作票 非关键词错、漏的<br>修改在3 处及以内的" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据2":
	        		return val[0].axisValue + "：工作票编号漏填或填写不<br>规范" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据3":
	        		return val[0].axisValue + "：未按规定盖“工作终结”、<br>“工作票终结”章或盖错章" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据4":
	        		return val[0].axisValue + "：应与工作票 一同保存的 <br>安全技术交底单、二次设备及<br>回路工作安全技术措施单、附<br>页等，未与工作票一同保存" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据5":
	        		return val[0].axisValue + "：工作票上使用的技术术<br>语不规范" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据6":
	        		return val[0].axisValue + "：工作票上的各类时间没<br>有按24小时制填写" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
	        	case"判据7":
	        		return val[0].axisValue + "：其它不按《电力安全工<br>作规程》或南方电网《电气工<br>作票技术规范》要求规范填写的" + "<br>" + val[0].marker + (val[0].data!=undefined?val[0].data:"-");
        	}
		},
		
		getFormData:function(){
			var self = this;
			var result = {};
			$.each($('#myForm').serializeArray(), function(index) {  
			    if(result[this['name']]) {
			        result[this['name']] = result[this['name']] + "," + this['value'];  
			    } else {  
			        result[this['name']] = this['value'];
			    }
			}); 
			result['checkResult'] = parent.$('#type').val();
			
			var department = $("#department").combotree("getText");
	    	//var orgId = null;
			var ids = new Array();
			
	    	if(department != null && department != "" && department != "广州供电局有限公司"){
				var t = $("#department").combotree('tree');  
				var selected = t.tree("getSelected");
				ids = self.getOrgIds(ids, selected);
			}
	    	result['department'] = department;
	    	
	    	var orgIds = "";
	    	for(var i = 0; i < ids.length; i++){
	    		if(orgIds.length > 0){
	    			orgIds += (",\'" + ids[i] + "\'");
	    			continue;
	    		}
	    		orgIds += ("\'" + ids[i] + "\'");
	    	}
	    	result['orgIds'] = orgIds;
			 
			result['p_ticketType'] = parent.$('#p_ticketType').val();
			result['p_batch'] = parent.$('#p_batchTime').val();
			result['p_workPrincipal'] = parent.$('#p_workPrincipal').val();
			result['p_ticketSigner'] = parent.$('#p_ticketSigner').val();
			result['p_workLicensor'] = parent.$('#p_workLicensor').val();
			result['p_department'] = parent.$('#p_department').val();
			result['p_ticketEndTime1'] = parent.$('#p_ticketEndTime1').val();
			result['p_ticketEndTime2'] = parent.$('#p_ticketEndTime2').val();
			result['p_orgids'] = parent.$('#p_orgids').val();
			 
			return result;
		},
		
		getOrgIds:function(resArr, selected){
			var self = this;
			if(selected){
				resArr.push(selected.id);
				if(selected.children){
					for(var i = 0;i < selected.children.length;i++){
						self.getOrgIds(resArr, selected.children[i]);
					}
				}
			}
			return resArr;
		}
		
	}
	
	module.exports = new CheckResult();
})