

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	require('library/echarts4/echarts.min.js');
	
//	var getwrongwords = require("module/commonUtil/getwrongwords.js");
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/ticketTypeStatistic/';//模块界面根目录
	var getChartDataUrl = moduleRequestRootUrl +'getStatistic';
	var getDelayStatisticUrl = moduleRequestRootUrl + 'getDelayStatistic';
	var dialogWebUrl = moduleWebRootUrl +'/detailDialog/ticketTypeStatisticDialog.jsp';
	var getOriginalUrl = moduleRequestRootUrl +'getOrigina';
	
	var width='500px';
	var height='400px';
	var monthList = ["01","02","03","04","05","06","07","08","09","10","11","12"];
	var flag;//是否合格参数
	var staFlag;//是否规范参数
	var ticketType;//工作票类型
	var fullPath;//是否外协单位参数
	var delayFlag;//是否延期
	var time;//月份
	
	var TicketTypeCharts = function(){
		this.width = '655px';
		this.height = '350px';
		this.$form;
		
	}
	
	TicketTypeCharts.prototype = {
		constructor: TicketTypeCharts,
		
		init:function(){
			var self = this;	
			self.$chartToolBar = $("#chartToolBar");
			self.$form = $("#myForm");

			self.$chartToolBar.on("click",".bda-btn-hitlsn-query",function(){
				if(!self.$form.form("validate")){
					$.messager.alert("提示","请录入必填条件！","warning");
					return false;
				}
				doSearch();
				//doSearchPie();
				//doSearchException();
				return false;
			})
			.on("click",".bda-btn-reset",function(){
				self.$form.form('reset');
				return false;
			})
			function doSearch(){
				var updateTimeBegin = $('#begin_date_batch').datebox('getValue');
		    	var updateTimeEnd = $('#end_date_batch').datebox('getValue');
		    	var unit = $('#data_query').combobox("getText");
		    	//日期验证
		    	if(updateTimeBegin >updateTimeEnd) {
		    		$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
		    		return;
		    	}
		    	var timeBegin = updateTimeBegin.substr(0,4);
		    	var timeEnd = updateTimeEnd.substr(0,4);
		    	if((timeEnd-timeBegin)>=1){
		    		$.messager.alert('温馨提示', '时间不能跨年份', 'warning');
		    		return;
		    	}
		    	var params = {
		    		"updateTimeBegin":updateTimeBegin,
		    		"updateTimeEnd":updateTimeEnd,
		    		"unit":unit
		    	};
				var data ;
				var myChart1 = echarts.init(document.getElementById('bar_1'));
				myChart1.showLoading({ text: '正在加载数据' });
				var myChart2 = echarts.init(document.getElementById('bar_2'));
				myChart2.showLoading({ text: '正在加载数据' }); 
				var myChart3 = echarts.init(document.getElementById('bar_3'));
				myChart3.showLoading({ text: '正在加载数据' }); 
				var myChart4 = echarts.init(document.getElementById('bar_4'));
				myChart4.showLoading({ text: '正在加载数据' }); 
				var myChart5 = echarts.init(document.getElementById('bar_5'));
				myChart5.showLoading({ text: '正在加载数据' }); 
				var myChart6 = echarts.init(document.getElementById('bar_6'));
				myChart6.showLoading({ text: '正在加载数据' }); 
				var myChart7 = echarts.init(document.getElementById('bar_7'));
				myChart7.showLoading({ text: '正在加载数据' }); 
				var myChart8 = echarts.init(document.getElementById('bar_8'));
				myChart8.showLoading({ text: '正在加载数据' }); 
				var myChart9 = echarts.init(document.getElementById('bar_9'));
				myChart9.showLoading({ text: '正在加载数据' }); 
				$.ajax({
					url:getChartDataUrl,
					type:"post",
					data:params,
					cache:false,
					dataType:"json",
					async:true,
					success:function(resp){
						data = resp;
//						if(null == data || undefined == data){
//							$.messager.alert("提示","查无数据！","warning");
//							return false;
//						}
						myChart1.hideLoading(); //关闭提示
						myChart2.hideLoading(); //关闭提示
						myChart3.hideLoading(); //关闭提示
						myChart4.hideLoading(); //关闭提示
						myChart5.hideLoading(); //关闭提示
						myChart6.hideLoading(); //关闭提示
						myChart7.hideLoading(); //关闭提示
						myChart8.hideLoading(); //关闭提示
						myChart9.hideLoading(); //关闭提示
						self.initBar1(data,myChart1);
						self.initBar2(data,myChart2);
						self.initBar3(data,myChart3);
						self.initBar4(data,myChart4);
						self.initBar5(data,myChart5);
						self.initBar6(data,myChart6);
						self.initBar7(data,myChart7);
						self.initBar8(data,myChart8);
						self.initBar9(data,myChart9);
					}
				})
			var myChart10 = echarts.init(document.getElementById('bar_10'));
			myChart10.showLoading({ text: '正在加载数据' });  
			$.ajax({
				url:getDelayStatisticUrl,
				type:"post",
				data:params,
				cache:false,
				dataType:"json",
				async:true,
				success:function(resp){
					myChart10.hideLoading(); //关闭提示
					self.initBar10(resp,myChart10);
//					if(null == resp || undefined == resp){
//						$.messager.alert("提示","查无数据！","warning");
//						return false;
//					}
				}
			})
	 			window.addEventListener("resize", function () {	
	 				window.setTimeout(function(){
	 					myChart1.resize();
	 					myChart2.resize();
	 					myChart3.resize();
	 					myChart4.resize();
	 					myChart5.resize();
	 					myChart6.resize();
	 					myChart7.resize();
	 					myChart8.resize();
	 					myChart9.resize();
	 					myChart10.resize();
		            },200)
		        }); 
			}

		},
		initBar1:function(data,myChart1){
			var self = this	;
			var quality = [];
			var noquality = [];
			if(null != data){
				
				for(var i =0;i<data.quality.length;i++){
					var value = data.quality[i];
					var para = {
						name:"check_result",
						value:value,
						result:"0"
					};
					quality.push(para);
				}
				
				for(var i =0;i<data.noquality.length;i++){
					var value = data.noquality[i];
					var para = {
						name:"check_result",
						value:value,
						result:"1"
					};
					noquality.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "合格和不合格工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['合格票数','不合格票数'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize : 10     
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize : 10     
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'合格票数',
							data: quality,
							barWidth:20,
							type: 'bar',
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'不合格票数',
							data: noquality,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
					            	color:'#92D050'
					            }
					        }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_1'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart1.setOption(option2);
	        myChart1.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = params.data.result;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = null;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar2:function(data,myChart2){
			var self = this	;
			var standard = [];
			var noStandard = [];
			if(null != data){
				for(var i =0;i<data.standard.length;i++){
					var value = data.standard[i];
					var para = {
						name:"standard_result",
						value:value,
						result:"0"
					};
					standard.push(para);
				}
				
				for(var i =0;i<data.noStandard.length;i++){
					var value = data.noStandard[i];
					var para = {
						name:"standard_result",
						value:value,
						result:"1"
					};
					noStandard.push(para);
				}
			}
			
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "规范和不规范工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['规范','不规范'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize : 10  
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize : 10     
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'规范',
							data: standard,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#DC853D'
				            	}
				            }
				    	},
				    	{
				        	name:'不规范',
							data: noStandard,
							type: 'bar',
							stack:'数量',
							barWidth:20,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_2'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
	        myChart2.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = params.data.result; //是否规范参数
	        	ticketType = null;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar3:function(data,myChart3){
			var self = this	;
			var stationOne = [];
			var stationTwo = [];
			var stationThree = [];
			if(null != data){
				for(var i =0;i<data.stationOne.length;i++){
					var value = data.stationOne[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"11"
					};
					stationOne.push(para);
				}
				for(var i =0;i<data.stationTwo.length;i++){
					var value = data.stationTwo[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"12"
					};
					stationTwo.push(para);
				}
				for(var i =0;i<data.stationThree.length;i++){
					var value = data.stationThree[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"13"
					};
					stationThree.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "三种厂站工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['厂站一种','厂站二种','厂站三种'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize : 10  
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize : 10  
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'厂站一种',
							data: stationOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	},
				    	{
				        	name:'厂站二种',
							data: stationTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
					            	color:'#FFC000'
					            }
					        }
				    	},
				    	{
				    		name:'厂站三种',
							data: stationThree,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
					            	color:'#F79646'
					            }
					        }    	
					    }
				    	
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_3'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart3.setOption(option2);
	        myChart3.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = params.data.result;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar4:function(data,myChart4){
			var self = this	;
			var fire=[];
			if(null != data){
				for(var i =0;i<data.fire.length;i++){
					var value = data.fire[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"32+31"
					};
					fire.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "动火工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['动火工作票'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'动火工作票',
							data: fire,
							type: 'bar',
							barWidth:20,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_4'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart4.setOption(option2);
	        myChart4.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = params.data.result;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar5:function(data,myChart5){
			var self = this	;
			var lineOne = [];
			var lineTwo = [];
			if(null != data){
				for(var i =0;i<data.lineOne.length;i++){
					var value = data.lineOne[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"21"
					};
					lineOne.push(para);
				}
				for(var i =0;i<data.lineTwo.length;i++){
					var value = data.lineTwo[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"22"
					};
					lineTwo.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "两种线路工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['线一','线二'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'线一',
							data: lineOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'线二',
							data: lineTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_5'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart5.setOption(option2);
	        myChart5.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = params.data.result;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar6:function(data,myChart6){
			var self = this	;
			var liveWorking=[];
			if(null != data){
				for(var i =0;i<data.liveWorking.length;i++){
					var value = data.liveWorking[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"43"
					};
					liveWorking.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "带电作业工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['带电作业工作票'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'带电作业工作票',
							data: liveWorking,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_6'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart6.setOption(option2);
	        myChart6.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = params.data.result;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar7:function(data,myChart7){
			var self = this	;
			var lowVoltage=[];
			if(null != data){
				for(var i =0;i<data.lowVoltage.length;i++){
					var value = data.lowVoltage[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"44"
					};
					lowVoltage.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "低压配网工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['低压配网工作票'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'低压配网工作票',
							data: lowVoltage,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_7'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart7.setOption(option2,myChart7);
	        myChart7.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = params.data.result;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar8:function(data,myChart8){
			var self = this	;
			var urgentRepairs = [];
			if(null != data){
				for(var i =0;i<data.urgentRepairs.length;i++){
					var value = data.urgentRepairs[i];
					var para = {
						name:"ticket_type",
						value:value,
						result:"51"
					};
					urgentRepairs.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "紧急抢修工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['紧急抢修工作票'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'紧急抢修工作票',
							data: urgentRepairs,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_8'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart8.setOption(option2,myChart8);
	        myChart8.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = params.data.result;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar9:function(data,myChart9){
			var self = this	;
			var principal=[];
			var constructUnit = [];
			if(null != data){
				for(var i =0;i<data.principal.length;i++){
					var value = data.principal[i];
					var para = {
						name:"name_full_path",
						value:value,
						result:"1"
					};
					principal.push(para);
				}
				for(var i =0;i<data.constructUnit.length;i++){
					var value = data.constructUnit[i];
					var para = {
						name:"name_full_path",
						value:value,
						result:"0"
					};
					constructUnit.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "局内负责人和施工单位工作票数量统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:10,
			            y:70,
			            x2:10,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['局内负责人','施工单位'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
			            	splitLine: {
			            		show: false
			            	},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'局内负责人',
							data: principal,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#F79646'
				            	}
				            }
				    	},
				    	{
				        	name:'施工单位',
							data: constructUnit,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
					            	color:'#92D050'
					            }
					        }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        //var myChart2 = echarts.init(document.getElementById('bar_9'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart9.setOption(option2);
	        myChart9.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = null;//工作票类型
	        	fullPath = params.data.result;//是否外协单位参数
	        	delayFlag = null;//是否延期
	        	time =params.dataIndex+1;
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
//					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		initBar10:function(data,myChart10){
			var self = this	;
			var isDelay = []; 
			var delayTime = [];
			if(null != data){
				for(var i =0;i<data.isDelay.length;i++){
					var value = data.isDelay[i];
					var para = {
						name:"is_delay",
						value:value,
						result:"1"
					};
					isDelay.push(para);
				}
				for(var i =0;i<data.delayTime.length;i++){
					var value = data.delayTime[i];
					var para = {
						name:"is_delay",
						value:value,
						result:"1"
					};
					delayTime.push(para);
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "工作票延期票数与延期时间统计",
				        x:'center',
				        y:'15'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	x:20,
			            y:70,
			            x2:20,
			            y2:'15%',
		                containLabel: true
		            },
		            legend: {
		                data:['延期工作票数','延期时数'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0",
				                fontSize:10
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '延期工作票数',
				            splitLine: {
				                show: false
				            },
				            axisLabel : {
				                fontSize:10
				            }
				        },
				        {
				        	type : 'value',
				        	name : '延期时数',
				            splitLine: {
				                show: false
				            },
				            axisLabel : {
				                fontSize:10
				            }
				        }
	
				    ],
				    series : [
				    	{
				        	name:'延期工作票数',
							data: isDelay,
							type: 'bar',
							barWidth:20,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
					            	color:'#4F80BD'
					            }
					        }
				    	},
				        {
				        	name:'延期时数',
							data: delayTime,
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			fontSize:10,
				            			color:'#000000',
				            			formatter: function (params) {
				                        	if (params.value > 0) {
				                            	return params.value;
				                        	} else {
				                            	return '';
				                        	}
				                    	}
				            		},
				            		color:'#FFC000'
				            	}
				            }
				    	}
				    ]
				};
			

	        // 使用刚指定的配置项和数据显示图表。
	        myChart10.setOption(option2);
	        myChart10.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	flag = null;//是否合格参数
	        	staFlag = null; //是否规范参数
	        	ticketType = null;//工作票类型
	        	fullPath = null;//是否外协单位参数
	        	delayFlag = params.data.result;//是否延期
	        	time =params.dataIndex+1;
	        	console.log(delayFlag);
				self.$dialog.dialog({
					title:'查询源数据',
					width: '85%',
					height: '90%',
					modal:true,
					href:dialogWebUrl,
					//queryParams:{type:title,field:field},
					minimizable:true,
					maximizable:true,
					resizable:true
				});
	        });
		},
		typeChartsDialog:function(){
			console.log(delayFlag);
			function showAllvalue(value){
				if(value==null){
					return null;
				}else{
					return '<span title='+value+'>'+value+'</span>';
				}
			}
			var self = this;
	    	var monthEnd = $('#end_date_batch').datebox('getValue');
	    	var unit = $('#data_query').combobox("getText");
	    	monthEnd = monthEnd.substr(0,4);
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
				{field:'functionLocationName',title:'厂站/线路名',width:150,align:'center',formatter:showAllvalue},
				{field:'workTask',title:'工作任务',width:150,align:'center',formatter:showAllvalue},
				{field:'orgName',title:'单位和班组',width:150,align:'center',formatter:showAllvalue},
				{field:'principalName',title:'工作负责人',width:150,align:'center',formatter:showAllvalue},
				{field:'pmisName',title:'工作许可人',width:150,align:'center',formatter:showAllvalue},
				{field:'signName',title:'工作签发人',width:150,align:'center',formatter:showAllvalue},
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
				{field:'workEndTime',title:'实际结束时间',width:150,align:'center',formatter:showAllvalue},
				{field:'planStartTime',title:'计划开始时间',width:150,align:'center',formatter:showAllvalue},
			    {field:'planEndTime',title:'计划结束时间',width:150,align:'center',formatter:showAllvalue},
			    {field:'isDelay',title:'是否延期',width:150,align:'center',formatter:function(val,row,index){
			      console.log(row);
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
					if(val==0){
						val = "合格";
					}else if(val==1||val==2){
						val = "不合格";
					}else{
						val = "";
					}
					return val;
				}},
				{field:'standardResult',title:'是否规范',width:150,align:'center',formatter:function(val,row,index){
					if(val==0){
						val = "规范";
					}else if(val==1||val==2){
						val = "不规范";
					}else{
						val = "";
					}
					return val;
				}}
			]]
			$('#typeChartsDialog_grid').datagrid({
				url: getOriginalUrl,
				queryParams: {
					flag:flag,
					staFlag:staFlag,
					ticketType:ticketType,
					fullPath:fullPath,
					delayFlag:delayFlag,
					time:monthEnd,
					monthEnd:time,
					unit:unit
				},
				columns: newComluns,
				toolbar : $('#typeChartsDialog_bar'),
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
		},
	}
	
	module.exports = new TicketTypeCharts();
})