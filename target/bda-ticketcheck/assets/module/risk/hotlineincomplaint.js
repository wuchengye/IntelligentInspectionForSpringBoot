/**
 * 使用服务禁语
 */
define(function(require, exports, module) {

	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js"); //扩展方法
	var utils = require("module/base/utils.js"); // 工具
	var showdetail = require("module/commonUtil/showdetail.js");
	
	var dialogUrl = "risk/complaint/openDialog";
	var getLatestDateUrl = "monitor/hotlineinconversationreview/getLatestDate";
	var submitCheckUrl = "risk/complaint/submitCheck";
	//连接配置
	var width='500px';
	var height='580px';

	var HotlineTaboo =function(){
		this.$grid;
		this.$dialog;
		this.$form;
		this.$toolbar;
		
		this.$taboo;
	}

	HotlineTaboo.prototype = {
		constructor: HotlineTaboo,
		
		init: function() {
			extend.init();
			var self = this;
			self.$grid=$('#business_grid');
			self.$dialog=$('#business_edit_window');
			self.$toolbar=$("#business_grid_bar");
			self.$form=$("#business_form");
			
			//表头
			var dgColumns = [[
				/*{field: 'judgeId',title: 'judgeId',hidden:true},
				{field: 'hitSentence',title: 'hitSentence',hidden:true},*/
				{field: 'operate',title: '核查',width: 80,align: 'center',
					formatter: function(val, row, index) {
					   return "<input type='button' onclick='$(this).parent().parent().parent().trigger(\"dblclick\")' value='核查' />";
					}
				},
			    {field: 'fileName',title: '录音文件名',width: 150},
			    {field: 'recordDate',title: '录音日期',width: 150},
			    {field: 'keyword',title: '命中关键字',width: 150,
			    	formatter: function(val, row, index){
			    		if(val!=null && val!='' && val!=undefined){
			    			var timeArr = new Array();
							timeArr = val.split(';');
							var timeSet = new Set();
							for(var i in timeArr){
								timeArr[i]=timeArr[i].split(':');
								timeSet.add(timeArr[i][1]);
							}
							var str = '';
							timeSet.forEach(function(item, sameItem, s){
								if(str.length>0){
									str += ','+item;
								}else{
									str += item;
								}
							});
							return str;
						}
				    }
			    
			    },
			    {field: 'contactTime',title: '接触时长(s)',width: 100,
			    	formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},
			    {field: 'checkStatus',title: '复核状态',width: 150,
					formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},
				{field: 'checkAccounts',title: '复核工号',width: 100,
					formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},  
			    {field: 'personIsProblem',title: '人员是否有问题',width: 100,
			    	formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},
			    {field: 'transferIstrue',title: '转写是否正确',width: 150,
			    	formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},
			    {field: 'problemDescribe',title: '问题描述',width: 150,
			    	formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},
			    {field: 'trueTransferContent',title: '通正确转写内容',width: 150,
			    	formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }},
			    {field: 'remark',title: '评语',width: 100,
			    	formatter: function(val, row, index) {
						if(null == val || val == undefined || val == ""){
							return '-';
						}else{
							return val;
						}
			    }}
			]];
			
			self.$grid.datagrid({
				loadMsg : '正在查询...',
				emptyMsg : '无数据加载！',
				columns : dgColumns,
				toolbar : self.$toolbar,
				rownumbers : true,
				fit : true,
				pagination : true,
				pageList : [ 10,20,50,100 ],
				pageNumber:1,
				pageSize:20,
				ctrlSelect:true,
                singleSelect:false,
                queryParams: self.getFormData(),
                onLoadSuccess : function(data) {
                	showdetail.showDetail();
                	return false;
                },
                onDblClickRow : function(index,row) {
                	$(this).datagrid('clearSelections');
                	$(this).datagrid('selectRow',index);
                	//var selectedBatch = $('#batch_grid').find('.list_grid').datagrid("getSelected");
                	self.$taboo = row.keyword;
					self.$dialog.dialog({
						title:'人工复核',
						width:'90%',
						height:height,
						modal:true,
						href:dialogUrl,
						queryParams:{
							sessionId:row.sessionId
							//sessionBatchId:selectedBatch.sessionBatchId,
							//judgeId:row.judgeId,
//							staffName: row.staffName,
//							workGroupName: row.workGroupName,
//							staffRoom: row.staffRoom,
							//firstIsWrong: row.firstIsWrong,
							//secondIsWrong: row.secondIsWrong
						},
						buttons:[],
						minimizable:false,
						maximizable:false,
						maximized:true,
						resizable:false,
						onClose: function(){
							self.$dialog.dialog('destroy');
						}
					});
				}
			});
			
			self.initEleEvent();
			
		},
		
		initEleEvent:function(){
			var self = this;
			/**
			 * 点击查看可疑数据事件
			 */
			self.$toolbar.on("click",".bda-btn-start",function(){
//				var selectedBatch = $('#batch_grid').find('.list_grid').datagrid("getSelected");
//            	if(!selectedBatch){
//					$.messager.alert('提示','请选择批次','warning');
//					return false;
//				}
				if(!self.$form.form("validate")){
					$.messager.alert('错误','请填入必填项！','error');
					return false;
				}
				var startTime = $('#recordStartDate').datetimebox('getValue');
				var endTime = $('#recordEndDate').datetimebox('getValue');
				if(startTime == '' ||endTime == ''){
					//return false;
				}else if(new Date(startTime.replace(/-/g, "/")) > new Date(endTime.replace(/-/g, "/"))){
					$.messager.alert('日期错误','起始日期不能大于结束日期！','error');
					return false;
				}
				//提交表单设置
				self.$grid.datagrid({
					url : 'risk/complaint/getComplaintJudgeDetail',
					queryParams:self.getFormData()
				});
				return false;
			})
			
			/**
			 * 点击清空条件事件
			 */
			.on("click",".bda-btn-del",function(){
				self.$form.form('reset');
				return false;
			})
			
			/**
			 * 点击导出可疑结果事件
			 */
			self.$toolbar.on("click",".bda-btn-exp",function(){
//				var selectedBatch = $('#batch_grid').find('.list_grid').datagrid("getSelected");
//				if(!selectedBatch){
//					$.messager.alert('提示','请选择批次','warning');
//					return false;
//				}
				parent.$.messager.progress({
					title : '提示',
					text : '导出中，请稍后....'
				});
				
				$.ajax({
					url:"risk/complaint/exportJudgeDetail",
					type:"post",
					data:self.getFormData(),
					cache:false,
					dataType:"json",
					success:function(data){
						parent.$.messager.progress('close');
						if(data.meta.success){
							$.messager.alert('提示', "导出成功，请点击“确定”下载！", 'info',function(){
								self.$form.form('submit',{
									url:"risk/usedtaboo/downloadResult",
									queryParams:{
										fileName : data.meta.code
									},
									onSubmit : function() {
										if(self.$form.form("validate")){
											return true;
										}else 
											return false;
									},
									success : function(data) {
										
									}
								});
							});
						}else{
							$.messager.alert('错误', "导出失败", 'error');
						}
					}
				});
				
				return false;
			})

		},
		
		getFormData:function(){
			var result = {};
			 $.each($('#business_form').serializeArray(), function(index) {  
			        if (result[this['name']]) {  
			        	result[this['name']] = result[this['name']] + "," + this['value'];  
			        } else {  
			        	result[this['name']] = this['value'];  
			        }  
			    }); 
			 return result;
		},
		
		// 日期类型格式成指定的字符串
		formatDate: function(date) {
			var formatdate = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate()
						+ ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
	        return formatdate;
	    },
		
	    //会话详情页面的初始化方法
		detailDialog:function(){
			var self = this;
			$("#check_form").on("click",".bda-btn-submit",function(){
				$(".bda-btn-submit").attr("disabled",true);//禁用按钮
				$("#check_form").form('submit',{
					url : submitCheckUrl,
					success : function(resp) {
						resp = $.parseJSON(resp);
						if(resp.status){
							$.messager.alert('提示', "提交成功", 'info');
						}else{
							$.messager.alert('错误', "提交失败", 'error');
						}
						$(".bda-btn-submit").attr("disabled",false);//恢复按钮
					}
				});
				
				
			});
			var audioUrl = "/bdaqm/assets/audio/test.mp3";
	    	//var audioUrl = "datamanage/hotlinesession/getAudio/" + $("#sessionId").text();
			
			//Example：self.$taboo = 2:唉,6:（不好意思|拜拜）&你好
			var tabooArr = self.$taboo.replace(/\(|\)|\（|\）/g,'');	//去括号
			tabooArr = tabooArr.split(';');//分割逗号      2:唉    6:不好意思|拜拜
			for(var i=0;i<tabooArr.length;i++){
				tabooArr[i] = tabooArr[i].split(':');//分割冒号
				if(typeof(tabooArr[i][1]) != 'undefined'){
					tabooArr[i][1] = tabooArr[i][1].split('#');
				}
			}
			
			/*var ismuteArr =self.$ismute.replace(/\(|\)|\（|\）/g,'');	
			ismuteArr = ismuteArr.split(';');
			for(var i=0;i<ismuteArr.length;i++){
				ismuteArr[i] = ismuteArr[i].split(',');
			}*/
			//初始化panel
			initPanel();
	    	//调整panel的高度
	    	setPanelHeight();
	    	
			//加载会话详情
			setContactDetail();
			
			function initPanel() {
				$('#basic_info').panel();
				$('#detail_info').panel({
				    title:'会话详情数据'
				});
			};
			
	
		
			function setPanelHeight() {
				var parent = $("#basic_info").parent().parent();
				parent.css("overflow-y", "hidden");
				var firstPanel = $("#basic_info").parent();
				var secondPanelHead = $("#detail_info").parent().children(".panel-header");
				
				var parentHeight = parent[0].offsetHeight;
				var firstPanelHeight = firstPanel[0].offsetHeight;
				var secondPanelHeadHeight = secondPanelHead[0].offsetHeight;
				$("#detail_info").css("height", parentHeight-firstPanelHeight-secondPanelHeadHeight);
			};
			
			function setAudioElement(detailInfo) {
				//移出ajax请求外面，防止上一个请求未完成，在新页面里完成之后执行新的渲染
				var playerBar = $("#detail_info > .player_bar");
				$.ajax({
					url: "risk/usedtaboo/playV3",
					type: "post",
					data: {
						filePath: $("#filePath").val(),
						fileName: $("#fileName").val()
					},
					dataType: "json",
					success: function(result){
						if(result.meta.success){
							var wavPath = result.data;
							//var wavPath = "/bdasjs/upload/dataManage/wav/2345.wav";
							var myDiv = document.createElement("div");
							$(myDiv).css("width", "50%");
							var audio = document.createElement("audio");
							$(audio).attr("src", wavPath)
									.attr("controls", "controls")
									.attr("preload", "preload")
									.attr("id", "myAudio");
							//$(audio).
							$(myDiv).append($(audio));
							playerBar.append($(myDiv));
							$("audio").audioPlayer();
						}else{
							$.messager.alert('提示','加载音频失败','warning');
						}
						//执行监听滚动字幕函数
						listenAudio(detailInfo);
					}
				});
			};
			
			function setContactDetail() {
				$.ajax({
					url: "risk/complaint/getSessionDetail",
					type: "post",
					data: {
						sessionId:$("#sessionId").val()
					},
					dataType: "json",
					success: function(result){
						var detailInfo = result.rows;
						var table = $("#detail_info > .text_content > table");
						var index = 1;
						detailInfo.forEach(function(item){
							var tr = document.createElement("tr");
							
							var tdHandle = document.createElement("td");
							$(tdHandle).html(index);
							$(tr).append($(tdHandle));
							
							var contentType = "--";
							if(item.qaType == "n0"){
								contentType = "客服";
								
							}else if(item.qaType == "n1"){
								contentType = "客户";
							}
							var tdType = document.createElement("td");
							$(tdType).html(contentType);
							$(tr).append($(tdType));
							
							var tdContent = document.createElement("td");
							/*for(var i = 0; i < ismuteArr.length; i++){
								if(item.rankNumber >= ismuteArr[i][0] && item.rankNumber <= ismuteArr[i][1]){
									item.sessionContent = "<span style='background-color:#4cae4c;padding: 0;color:white;'>"+item.sessionContent+"</span>";
								}
							}*/
							
							//if(item.qaType == "n0"){
			    				for(var i = 0; i < tabooArr.length; i++){
						    		if(item.rankNumber == tabooArr[i][0] && typeof(tabooArr[i][1]) != "undefined"){
						    			for(var j = 0; j < tabooArr[i][1].length; j++){
						    				var reg = new RegExp(tabooArr[i][1][j],"gm");
						    				var temp = item.sessionContent.match(reg);
						    				if(null!=temp){
						    					//item.sessionContent = item.sessionContent.replace(temp, "<span style='background-color:#cc0000;padding: 0;color:white;'>" + temp + "</span>")
						    					item.sessionContent = item.sessionContent.replace(new RegExp(temp[0],'g'), "<span style='background-color:#cc0000;padding: 0;color:white;'>" + temp[0] + "</span>");
						    				}
						    			}
						    		}
					    		}
				    		//}
							$(tdContent).html(item.sessionContent);
							$(tr).append($(tdContent));
							
							table.append($(tr));
							item.index = index++;
						});
						//加载音频文件
						setAudioElement(detailInfo);
						
					}
				});
			};
			
			function listenAudio(detailInfo) {
				var audio = document.getElementById("myAudio");
				var currentIndex = 0;
				audio.ontimeupdate = function(){
					var index = currentIndex;
					var currentTime = audio.currentTime*1000;
					for(var i = 0; i < detailInfo.length; i++){
						if(i < detailInfo.length-1){
							if(detailInfo[i].beginTime < currentTime && detailInfo[i+1].beginTime > currentTime){
								index = detailInfo[i].index;
								break;
							}
						}else{
							index = detailInfo[i].index;
						}
					}
					
					if(index != currentIndex){
						$("#detail_info > .text_content > table tr:nth-child(" + currentIndex + ")").css("background-color", "transparent");
						$("#detail_info > .text_content > table tr:nth-child(" + index + ")").css("background-color", "#ffe7b2");
						currentIndex = index;
					}
				};
				audio.onended = function(){
					$("#detail_info > .text_content > table tr").css("background-color", "transparent");
				};
			};
		}
	}

	module.exports = new HotlineTaboo();
})