
//使用情况统计表
define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	
	var getDataUrl = 'electric/ticketCheck/getTicketsData';
	var doCheckUrl = 'electric/ticketCheck/checkTicket';
	var allDatas;//校验全数据
	var TicketCheck = function(){
		this.$datagrid;
		this.$checkResDialog;
		this.$toolbar;
		if (typeof String.prototype.endsWith != 'function') {  
			 String.prototype.endsWith = function(suffix) {  
			  return this.indexOf(suffix, this.length - suffix.length) !== -1;  
			 };  
		} 
	}
	
	TicketCheck.prototype = {
			
		constructor: TicketCheck,
		
		init:function() {
			extend.queryCombotree("#department");
			var self = this;
			self.$datagrid = $("#check_grid");
			self.$toolbar = $("#check_bar");
			self.$checkedResDialog = $("#checked_dialog");
			self.$checkedResGrid = $("#checkResult");
			
			var Columns = [[
			    {field:'checkBox',checkbox:true},
				{field:'id',title:'工作票ID',width:250,align:'center',hidden:true},
				{field:'ticket_no',title:'工作票票号',width:150,align:'center'},
				{field:'ticket_type',title:'工作票类型',width:120,align:'center',
				    formatter:function(val, row, index){
				    	switch (val) {
						case 11:
							val = "厂站第一种工作票";
							break;
						case 12:
							val = "厂站第二种工作票";
							break;
						case 13:
							val = "厂站第三种工作票";
							break;
						case 21:
							val = "线路第一种工作票";
							break;
						case 22:
							val = "线路第二种工作票";
							break;
						case 31:
							val = "一级动火工作票";
							break;
						case 32:
							val = "二级动火工作票";
							break;
						case 41:
							val = "配电第一种工作票";
							break;
						case 42:
							val = "配电第二种工作票";
							break;
						case 43:
							val = "带电作业工作票";
							break;
						case 44:
							val = "低压配电网工作票";
							break;
						case 51:
							val = "紧急抢修工作票";
							break;
						case 61:
							val = "安全技术交底单";
							break;
						case 71:
							val = "二次措施单";
							break;
						case 81:
							val = "书面布置和记录";
							break;
						case 91:
							val = "现场勘察记录";
							break;
						default:
							break;
						}
				    	return val;
					}
				},
				{field:'function_location_name',title:'站名/线路',width:150,align:'center'},
				{field:'work_task',title:'工作任务',width:180,align:'center'},
				{field:'pri_name',title:'工作负责人',width:100,align:'center'},
				{field:'sign_name',title:'工作票签发人',width:100,align:'center'},
				{field:'permis_name',title:'工作许可人',width:100,align:'center'},
				{field:'end_pmis_name',title:'工作终结许可人',width:100,align:'center'},
				{field:'department_oname',title:'单位和班组',width:200,align:'center'},
				{field:'plan_start_time',title:'计划开始时间',width:135,align:'center'},
				{field:'plan_end_time',title:'计划结束时间',width:135,align:'center'},
				{field:'work_end_time',title:'工作终结时间',width:135,align:'center'},
				{field:'permission_time',title:'工作许可时间',width:135,align:'center'},
				{field:'whether_outer_dept',title:'是否外来单位',width:100,align:'center',
					formatter:function(val,row,index){
				    	if("1" == val)
				    		return "是";
				    	else if ("2" == val)
				    		return "否";
				    }	
				},
				{field:'counter_name',title:'工作票会签人',width:100,align:'center'},
				{field:'ticket_sign_time',title:'签发时间',width:135,align:'center'},
				{field:'ticket_counter_sign_time',title:'会签时间',width:135,align:'center'},
//				{field:'workDelayTime',title:'工作延期时间',width:100,align:'center'}
		    ]]	
			
			self.$datagrid.datagrid({
				//url: getDataUrl,
				columns: Columns,
				toolbar : self.$toolbar,
				loadMsg: '正在查询数据，请稍候...',
				rownumbers: true,
				pagination :true,
				pageList :[ 10,20,50,100],
				pageNumber:1,
				pageSize:20,
				fit: true,
				loadFilter:function(data){
				    console.log(data); // 这是你加载到的原始数据，调试到console上方便查看
				    var Obj = {};
				   // debugger;
				    allDatas = data.all;
				    Obj.total = data.total;
				    Obj.rows = data.show;  // 类似的取出（转换数据）
				    return Obj;  // 再输出给控件加载
				}
//				data:[
//				    {ticketId:"20020190001",ticketType:"厂站第一种工作票",stationLineName:"九佛站",workTask:"10kV#2A电容器组检修",workPalce:"",charger:"尹尚斌",
//				    	receiver:"",signer:"黄桂华",licensor:"",workEndLicensor:"文凯",department:"检修试验班",planStartTime:"2017-04-01 08:30:00",
//				    	planEndTime:"2017-04-01 18:29:59",workLastTime:"",ticketEndTime:"2017-04-01 10:00:00",isOuterDept:"否",issuer:"",ticketSignTime:"2017-03-31 16:00:00",
//				    	ticketCounterSignTime:"",workDelayTime:""},
//			    	{ticketId:"20020190001",ticketType:"厂站第一种工作票",stationLineName:"木棉站",workTask:"#3主变变高5041开关检修",workPalce:"",charger:"王志荣",
//				    	receiver:"",signer:"杨炳运",licensor:"黄国钊",workEndLicensor:"方贤金",department:"检修试验班",planStartTime:"2017-04-28 09:00:00",
//				    	planEndTime:"2017-04-28 16:00:00",workLastTime:"",ticketEndTime:"2017-04-28 19:00:00",isOuterDept:"是",issuer:"",ticketSignTime:"2017-04-27 15:00:00",
//				    	ticketCounterSignTime:"2017-04-28 07:59:59",workDelayTime:"2017-04-28 19:30:00"}
//				]
			});
			self.initEleEvent();

		},
		
		initEleEvent:function(){
			
			var self = this	;
			console.log(allDatas);
			self.$toolbar.on('click','.bda-btn-query',function(){
				self.doSearch();
				return false;
			})
			.on('click','.bda-btn-check',function(){
				var i  = '1';
				self.doCheck(i);
				return false;
			})
			.on('click','.bda-btn-reset',function(){
				$('#conditionForm').form("reset");
				return false;
			})
			.on('click','.bda-btn-check-all',function(){
				var i  = '0';
				self.doCheck(i);
				return false;
			});
			
			$("input[name='ticketType']").combobox({
				valueField:'value',
				textField:'text',
				panelHeight:'auto',
				panelMaxHeight:250,
				editable:true,
				data:[
				      {value:'',text:'--请选择--',selected:true},
				      {value:'21',text:'线路第一种工作票'},{value:'22',text:'线路第二种工作票'},
				      {value:'11',text:'厂站第一种工作票'},{value:'12',text:'厂站第二种工作票'},
				      {value:'13',text:'厂站第三种工作票'},
				      {value:'31',text:'一级动火工作票'},{value:'32',text:'二级动火工作票'},
				      {value:'51',text:'紧急抢修工作票'},
				      {value:'43',text:'带电作业工作票'},
				      {value:'44',text:'低压配电网工作票'}
				]
			})
		},
		
		doSearch:function(){
			var self = this;
			var condition = self.getFormData();
			var hadCondition = false;//是否填写查询条件
			for(var i in condition){
				if(condition[i] != ""){
					hadCondition = true;
					break;
				}else{
					continue;
				}
			}
			if(hadCondition){
			}else{
				$.messager.alert("提示","请录入查询条件！","info");
				return false;
			}
			//self.$datagrid.datagrid('load', self.getFormData());
			self.$datagrid.datagrid({
				url: getDataUrl,
				queryParams: self.getFormData()
			});
		},
		
		doCheck:function(i){
			var self = this;
			var params = {};
			var conditions = self.getFormData();
			var conditionType = $("input[name='conditionType']:checked").val() ;
			console.log(allDatas);
			var checkedRow = self.$datagrid.datagrid("getChecked");
			var ids = new Array();
			if(i=='1'){
				$.each(checkedRow,function(index,entry){
					ids.push(entry.id);
				});
				
				if(conditionType == 0){//按ID校验
					if(ids.length == 0){
						$.messager.alert("提示","请至少勾选一条数据进行校验！","info");
						return false;
					}else {
						params = {'ids[]':ids};
						self.doRealCheck(params);
					}
				}else if(conditionType == 1){
					var falg = false;
					for(var i in conditions){
						if(conditions[i]){
							falg = true;
							break;
						}else{
							continue;
						}
					}
					if( !falg ){
						$.messager.alert("提示","请填写条件再进行校验！","info");
						return false;
					}else{
						params = conditions;
						self.doRealCheck(params);
					}
				}
			}else if(i=='0'){
				
				if(allDatas!=undefined&&allDatas.length>0){
					$.messager.confirm('请确认','全部工作票校验时间较长，确定继续进行校验？',function(r){
						  
						  if (r){
						    	$.each(allDatas,function(index,entry){
									ids.push(entry.id);
								});
						    	params = {'ids[]':ids};
						    	self.doRealCheck(params);
						    }else{
						    	 return false;
						    }
						});					
				}else{
					$.messager.alert("提示","请先查询数据再进行校验！","info");
					return false;
				}
				
			}
		},
		doRealCheck:function(data){
			var params = data;
			var self = this;
			parent.$.messager.progress({
				title : '提示',
				text : '校验中，请稍后....'
			});
			var row ;
			$.ajax({
				url: doCheckUrl,
				type: "post",
				data: params,
				cache: false,
				dataType: "json",
				success: function(data){
					parent.$.messager.progress("close");
					self.$checkedResDialog.show();
					row = data.rows[0];
					
					var closeButton=[
						{
							text:'详情',
							handler:function(){
								if(row){
									parent.$("#p_newBatch").val(row.batchTime);
								}
								
								var parent$ = parent.$;
								var content = '<div style="overflow: hidden;height:100%;"><iframe scrolling="auto" frameborder="0"  src="web/electric/chartsOfNewTab.jsp" style="width:100%;height:100%;"></iframe></div>';
								var tabs = parent$('.js-tabs').tabs("tabs");
					        	for(var i = 0; i < tabs.length; i++){
					        		var title = parent$('.js-tabs').tabs('getTab',i).panel('options').title;
					        		if(title.endsWith("结果详情")){
					        			parent$('.js-tabs').tabs('select',i);
					        			parent$('.js-tabs').tabs('update',{
					        				tab:parent$('.js-tabs').tabs('getTab',i),
					        				type:"all",
					        				options:{
					        					title:"工作票校验结果详情",
					        					content:content
					        				}
					        			});
					        			return false;
					        		}
					        	}
					        	
					        	parent$('.js-tabs').tabs('add',{       //实现add方法
					        		id:"tab_new",
					    			title:'工作票校验结果详情',
					    			content:content,
									closable:true,
									fit:true
					    		});
							}
						},
						{
							text:'关闭',
							handler:function(){self.$checkedResDialog.dialog("close");}
						}
					];
					
					self.$checkedResDialog.dialog({
						title:'校验结果',
						width: '600px',
						height: '400px',
						modal:true,
						buttons:closeButton,
						maximizable:true,
						resizable:true,
						onClose:function(){
						}
					})
					function formatVal(val){
						if(val == "0") return "合格";
						else if(val == "1" || val == "2") return "不合格";
						//else if(val == "2") return "异常";
					}
					function formatGfVal(val){
						if(val == "0") return "规范";
						else if(val == "1" || val == "2") return "不规范";
						//else if(val == "2") return "异常";
					}
					self.$checkedResGrid.datagrid({
						data:data,
						columns: [[
                            {field:'batchTime',title:'校验批次',width:150,align:'center'},
						    {field:'ticketNo',title:'工作票票号',width:135,align:'center'},
//						    {field:'message',title:'校验结果',width:400,align:'center'}
						    {field:'checkResult',title:'合格性校验结果',width:120,align:'center',formatter:formatVal},
						    {field:'standardResult',title:'规范性校验结果',width:120,align:'center',formatter:formatGfVal}
						]],
						loadMsg: '正在查询数据，请稍候...',
						rownumbers: true,
						pagination :false,
						fit: true,
						singleSelect:true
					})
				}
			})
		},
		
		getFormData:function(){
			var self = this;
			var result = {};
			$.each($('#conditionForm').serializeArray(), function(index) { 
				
			    if(result[this['name']]) {
			        result[this['name']] = result[this['name']] + "," + this['value'];  
			    } else {  
			        result[this['name']] = this['value'];
			    }
			}); 
			delete result.conditionType;
			
			var department = $("#department").combotree("getText");
	    	//var orgId = null;
			var ids = new Array();
			
	    	if(department != null && department != "" && department != "广州供电局有限公司"){
				var t = $("#department").combotree('tree');  
				var selected = t.tree("getSelected");
				ids = self.getOrgIds(ids, selected);
				//orgId = t.tree("getSelected").id;
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
	    	//result['orgId'] = orgId;
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
	
	module.exports = new TicketCheck();
})