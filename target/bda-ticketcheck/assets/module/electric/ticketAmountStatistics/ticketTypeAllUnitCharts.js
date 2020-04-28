

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	require('library/echarts4/echarts.min.js');
	
//	var getwrongwords = require("module/commonUtil/getwrongwords.js");
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/ticketTypeStatistic/';//模块界面根目录

	var getChartDataUrl = moduleRequestRootUrl+'statisticPrincipal';
	var getExtrlDelayDataUrl = moduleRequestRootUrl + 'getExtrlDelayStatistic';
	var statisticTypeForUnitUrl = moduleRequestRootUrl + 'statisticTypeForUnit';
	var statisticTypeForAllUnitUrl = moduleRequestRootUrl +'statisticTypeForAllUnit';
	var statictisStateUrl = moduleRequestRootUrl + 'statictisState';
	var dialogWebUrl = moduleWebRootUrl +'/detailDialog/ticketTypeAllUnitStatisticDialog.jsp';
	var getOriginalUrl = moduleRequestRootUrl +'getOriginaAllUnit';
	
	
	var width='500px';
	var height='400px';
	
	var mainUnitArr = ["调控中心","输电管理一所","输电管理二所","变电管理一所","变电管理二所","变电管理三所"];
	
	var UnitArr = ["越秀","荔湾","海珠","天河","黄埔","白云","南沙","番禺","花都","增城","从化"];
	
	var otherUnitArr = ["计量中心","试研院","通信中心"];
	
	var AllUnitArr = ["中国南方","外协单位"];
	
	var index;//图表下标
	var name;//查看源数据的维度名称
	var isExtrl;//0-主网,1-配网,2-外协
	var ticketType;//工作票类型
	var lastFlag;//判断是否点击最后一个图表
	
	var TicketNumCharts = function(){
		this.width = '655px';
		this.height = '350px';
		this.$form;
		
	}
	TicketNumCharts.prototype = {
		constructor: TicketNumCharts,
		
		init:function(){
			var self = this;
			self.$chartToolBar = $("#chartToolBar");
			self.$form = $("#myForm");
			//self.initBar1(null);
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
		    	//日期验证
		    	if(updateTimeBegin >updateTimeEnd) {
		    		$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
		    		return;
		    	}
		    	var params = {
		    		"updateTimeBegin":updateTimeBegin,
		    		"updateTimeEnd":updateTimeEnd
		    	};
				var data ;
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
						myChart7.hideLoading(); //关闭提示
						myChart8.hideLoading(); //关闭提示
						myChart9.hideLoading(); //关闭提示
						self.initBar7(data,myChart7);
						self.initBar8(data,myChart8);
						self.initBar9(data,myChart9);
					}
				})
				var myChart1 = echarts.init(document.getElementById('bar_1'));
				myChart1.showLoading({ text: '正在加载数据' });  
				$.ajax({
					url:getExtrlDelayDataUrl,
					type:"post",
					data:params,
					cache:false,
					dataType:"json",
					async:true,
					success:function(resp){
						//console.log(resp);
						myChart1.hideLoading(); //关闭提示
						self.initBar1(resp,myChart1);
					}
				})
				var myChart3 = echarts.init(document.getElementById('bar_3'));
				myChart3.showLoading({ text: '正在加载数据' });  
				var myChart4 = echarts.init(document.getElementById('bar_4'));
				myChart4.showLoading({ text: '正在加载数据' });
				var myChart5 = echarts.init(document.getElementById('bar_5'));
				myChart5.showLoading({ text: '正在加载数据' });
				$.ajax({
					url:statisticTypeForUnitUrl,
					type:"post",
					data:params,
					cache:false,
					dataType:"json",
					async:true,
					success:function(resp){
						myChart3.hideLoading(); //关闭提示
						myChart4.hideLoading(); //关闭提示
						myChart5.hideLoading(); //关闭提示
						self.initBar3(resp,myChart3);
						self.initBar4(resp,myChart4);
						self.initBar5(resp,myChart5);
					}
				})
				var myChart6 = echarts.init(document.getElementById('bar_6'));
				myChart6.showLoading({ text: '正在加载数据' });
				$.ajax({
					url:statisticTypeForAllUnitUrl,
					type:"post",
					data:params,
					cache:false,
					dataType:"json",
					async:true,
					success:function(resp){
						myChart6.hideLoading(); //关闭提示
						self.initBar6(resp,myChart6);
					}
				})
				var myChart10 = echarts.init(document.getElementById('bar_10'));
				myChart10.showLoading({ text: '正在加载数据' });
				$.ajax({
					url:statictisStateUrl,
					type:"post",
					data:params,
					cache:false,
					dataType:"json",
					async:true,
					success:function(resp){
						myChart10.hideLoading(); //关闭提示
						self.initBar10(resp,myChart10);
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
			var totalList = [];
			var checkList = [];
			var delayList = [];
			var delayTimeList = [];
			if(null != data){
//				for(var i =0;i<data.totalList.length;i++){
//					var value = data.totalList[i];
//					var para = {
//						name:"ticket_type",
//						value:value,
//						result:"0"
//					};
//					totalList.push(para);
//				}
				totalList = data.totalList;
				checkList = data.checkList;
				delayList = data.delayList;
				delayTimeList= data.delayTimeList;
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "外委单位工作票数量统计",
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
		                data:['工作票数量','合格数量','延期数量','延期时间'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ['厂站第一种工作票','厂站第二种工作票','厂站第三种工作票','线路第一种工作票','线路第二种工作票','低压配网工作票','带电作业工作票','紧急抢修工作票','动火工作票','调度检修单','未标注工作票'],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45",
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
				        	name:'工作票数量',
							data: totalList,
							type: 'bar',
							barGap:'0%',
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
				        	name:'合格数量',
							data: checkList,
							type: 'bar',
							barGap:'0%',
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
				    	},
				    	{
				        	name:'延期数量',
							data: delayList,
							type: 'bar',
							barGap:'0%',
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
				            		color:'#DC853D'
				            	}
				            }
				    	},
				    	{
				        	name:'延期时间',
							data: delayTimeList,
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
				            		color:'#8064A2'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例

	        // 使用刚指定的配置项和数据显示图表。
	        myChart1.setOption(option2);
	        myChart1.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '2';
	        	lastFlag = '2';
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
			var stationOne = [];//厂站一种票数组
			var stationTwo = [];//厂站二种票数组
			var stationThree = [];//厂站三种票数组
			var lineOne = [];//线路一种票数组
			var lineTwo = [];//线路二种票数组
			var fire = [];//动火票数组
			var liveWorking = [];//带电作业工作票数组
			var lowVoltage = [];//低压配电网工作票数组
			var urgentRepairs = [];//紧急抢修工作票数组
			var dispatch = [];//调度检修数组
			var unMarked = [];//未标注工作票数组
			if(null != data){
				for(var i=0;i<mainUnitArr.length;i++) {
					
					for(var j=0;j<data.length;j++) {
						var map = data[j];
						//给前端图表需要的数组赋值
						if(map["unitname"].indexOf(mainUnitArr[i])!=-1) {
							stationOne.push(map["stationone"]);
							stationTwo.push(map["stationtwo"]);
							stationThree.push(map["stationthree"]);
							lineOne.push(map["lineone"]);
							lineTwo.push(map["linetwo"]);
							fire.push(map["fireone"]+map["firetwo"]);
							liveWorking.push(map["liveworking"]);
							lowVoltage.push(map["lowvoltage"]);
							urgentRepairs.push(map["urgentrepairs"]);
							dispatch.push(map["dispatch"]);
							unMarked.push(map["unmarked"]);
							break;
						}else {
							//sql统计出来的数据如果没有某一月的就赋值0
							if(j==data.length-1) {
								stationOne.push(0);
								stationTwo.push(0);
								stationThree.push(0);
								lineOne.push(0);
								lineTwo.push(0);
								fire.push(0);
								liveWorking.push(0);
								lowVoltage.push(0);
								urgentRepairs.push(0);
								dispatch.push(0);
								unMarked.push(0);
							}
						}
						
						
					}
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "主网单位工作票数量统计",
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
			            y2:'25%',
		                containLabel: true
		            },
		            legend:[
		            	{
		            		x : '1%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',	
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第三种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '80%',
		                    align: 'left',
			                data:['线路第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '85%',
		                    align: 'left',
			                data:['线路第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '85%',
		                    align: 'left',
			                data:['低压配网工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '85%',
		                    align: 'left',
			                data:['带电作业工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '85%',
		                    align: 'left',
			                data:['紧急抢修工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '90%',
		                    align: 'left',
			                data:['动火工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '90%',
		                    align: 'left',
			                data:['调度维修单'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '90%',
		                    align: 'left',
			                data:['未标注工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
		            ],
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["调控中心","输电管理一所","输电管理二所","变电管理一所","变电管理二所","变电管理三所"],
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
				        	name:'厂站第一种工作票',
							data: stationOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		color:'#00B050'
				            	}
				            }
				    	},
				    	{
				        	name:'厂站第二种工作票',
							data: stationTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#79BC32'
					            }
					        }
				    	},
				    	{
				        	name:'厂站第三种工作票',
							data: stationThree,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92D050'
					            }
					        }
				    	},
				    	{
				        	name:'线路第一种工作票',
							data: lineOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F4740C'
					            }
					        }
				    	},
				    	{
				        	name:'线路第二种工作票',
							data: lineTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F79646'
					            }
					        }
				    	},
				    	{
				        	name:'低压配网工作票',
							data: lowVoltage,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#FFC000'
					            }
					        }
				    	},
				    	{
				        	name:'带电作业工作票',
							data: liveWorking,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#2784D3'
					            }
					        }
				    	},
				    	{
				        	name:'紧急抢修工作票',
							data: urgentRepairs,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#00B0F0'
					            }
					        }
				    	},
				    	{
				        	name:'动火工作票',
							data: fire,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#9483DF'
					            }
					        }
				    	},
				    	{
				        	name:'调度维修单',
							data: dispatch,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#46A2A8'
					            }
					        }
				    	},
				    	{
				        	name:'未标注工作票',
							data: unMarked,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92CDDC'
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
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '0';
	        	lastFlag = '2';
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
			var stationOne = [];//厂站一种票数组
			var stationTwo = [];//厂站二种票数组
			var stationThree = [];//厂站三种票数组
			var lineOne = [];//线路一种票数组
			var lineTwo = [];//线路二种票数组
			var fire = [];//动火票数组
			var liveWorking = [];//带电作业工作票数组
			var lowVoltage = [];//低压配电网工作票数组
			var urgentRepairs = [];//紧急抢修工作票数组
			var dispatch = [];//调度检修数组
			var unMarked = [];//未标注工作票数组
			if(null != data){
				for(var i=0;i<UnitArr.length;i++) {
					
					for(var j=0;j<data.length;j++) {
						var map = data[j];
						//给前端图表需要的数组赋值
						if(map["unitname"].indexOf(UnitArr[i])!=-1) {
							stationOne.push(map["stationone"]);
							stationTwo.push(map["stationtwo"]);
							stationThree.push(map["stationthree"]);
							lineOne.push(map["lineone"]);
							lineTwo.push(map["linetwo"]);
							fire.push(map["fireone"]+map["firetwo"]);
							liveWorking.push(map["liveworking"]);
							lowVoltage.push(map["lowvoltage"]);
							urgentRepairs.push(map["urgentrepairs"]);
							dispatch.push(map["dispatch"]);
							unMarked.push(map["unmarked"]);
							break;
						}else {
							//sql统计出来的数据如果没有某一月的就赋值0
							if(j==data.length-1) {
								stationOne.push(0);
								stationTwo.push(0);
								stationThree.push(0);
								lineOne.push(0);
								lineTwo.push(0);
								fire.push(0);
								liveWorking.push(0);
								lowVoltage.push(0);
								urgentRepairs.push(0);
								dispatch.push(0);
								unMarked.push(0);
							}
						}
						
						
					}
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "配网单位工作票数量统计",
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
			            y2:'25%',
		                containLabel: true
		            },
		            legend:[
		            	{
		            		x : '1%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',	
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第三种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '80%',
		                    align: 'left',
			                data:['线路第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '85%',
		                    align: 'left',
			                data:['线路第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '85%',
		                    align: 'left',
			                data:['低压配网工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '85%',
		                    align: 'left',
			                data:['带电作业工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '85%',
		                    align: 'left',
			                data:['紧急抢修工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '90%',
		                    align: 'left',
			                data:['动火工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '90%',
		                    align: 'left',
			                data:['调度维修单'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '90%',
		                    align: 'left',
			                data:['未标注工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
		            ],
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["越秀","荔湾","海珠","天河","黄埔","白云","南沙","番禺","花都","增城","从化"],
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
				        	name:'厂站第一种工作票',
							data: stationOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		color:'#00B050'
				            	}
				            }
				    	},
				    	{
				        	name:'厂站第二种工作票',
							data: stationTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#79BC32'
					            }
					        }
				    	},
				    	{
				        	name:'厂站第三种工作票',
							data: stationThree,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92D050'
					            }
					        }
				    	},
				    	{
				        	name:'线路第一种工作票',
							data: lineOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F4740C'
					            }
					        }
				    	},
				    	{
				        	name:'线路第二种工作票',
							data: lineTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F79646'
					            }
					        }
				    	},
				    	{
				        	name:'低压配网工作票',
							data: lowVoltage,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#FFC000'
					            }
					        }
				    	},
				    	{
				        	name:'带电作业工作票',
							data: liveWorking,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#2784D3'
					            }
					        }
				    	},
				    	{
				        	name:'紧急抢修工作票',
							data: urgentRepairs,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#00B0F0'
					            }
					        }
				    	},
				    	{
				        	name:'动火工作票',
							data: fire,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#9483DF'
					            }
					        }
				    	},
				    	{
				        	name:'调度维修单',
							data: dispatch,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#46A2A8'
					            }
					        }
				    	},
				    	{
				        	name:'未标注工作票',
							data: unMarked,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92CDDC'
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
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '1';
	        	lastFlag = '2';
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
			var stationOne = [];//厂站一种票数组
			var stationTwo = [];//厂站二种票数组
			var stationThree = [];//厂站三种票数组
			var lineOne = [];//线路一种票数组
			var lineTwo = [];//线路二种票数组
			var fire = [];//动火票数组
			var liveWorking = [];//带电作业工作票数组
			var lowVoltage = [];//低压配电网工作票数组
			var urgentRepairs = [];//紧急抢修工作票数组
			var dispatch = [];//调度检修数组
			var unMarked = [];//未标注工作票数组
			if(null != data){
				for(var i=0;i<otherUnitArr.length;i++) {
					
					for(var j=0;j<data.length;j++) {
						var map = data[j];
						//给前端图表需要的数组赋值
						if(map["unitname"].indexOf(otherUnitArr[i])!=-1) {
							stationOne.push(map["stationone"]);
							stationTwo.push(map["stationtwo"]);
							stationThree.push(map["stationthree"]);
							lineOne.push(map["lineone"]);
							lineTwo.push(map["linetwo"]);
							fire.push(map["fireone"]+map["firetwo"]);
							liveWorking.push(map["liveworking"]);
							lowVoltage.push(map["lowvoltage"]);
							urgentRepairs.push(map["urgentrepairs"]);
							dispatch.push(map["dispatch"]);
							unMarked.push(map["unmarked"]);
							break;
						}else {
							//sql统计出来的数据如果没有某一月的就赋值0
							if(j==data.length-1) {
								stationOne.push(0);
								stationTwo.push(0);
								stationThree.push(0);
								lineOne.push(0);
								lineTwo.push(0);
								fire.push(0);
								liveWorking.push(0);
								lowVoltage.push(0);
								urgentRepairs.push(0);
								dispatch.push(0);
								unMarked.push(0);
							}
						}
						
						
					}
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "其他运行单位工作票数量",
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
			            y2:'25%',
		                containLabel: true
		            },
		            legend:[
		            	{
		            		x : '1%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',	
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第三种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '80%',
		                    align: 'left',
			                data:['线路第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '85%',
		                    align: 'left',
			                data:['线路第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '85%',
		                    align: 'left',
			                data:['低压配网工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '85%',
		                    align: 'left',
			                data:['带电作业工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '85%',
		                    align: 'left',
			                data:['紧急抢修工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '90%',
		                    align: 'left',
			                data:['动火工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '90%',
		                    align: 'left',
			                data:['调度维修单'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '90%',
		                    align: 'left',
			                data:['未标注工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
		            ],
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["计量中心","试研院","通信中心"],
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
				        	name:'厂站第一种工作票',
							data: stationOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		color:'#00B050'
				            	}
				            }
				    	},
				    	{
				        	name:'厂站第二种工作票',
							data: stationTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#79BC32'
					            }
					        }
				    	},
				    	{
				        	name:'厂站第三种工作票',
							data: stationThree,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92D050'
					            }
					        }
				    	},
				    	{
				        	name:'线路第一种工作票',
							data: lineOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F4740C'
					            }
					        }
				    	},
				    	{
				        	name:'线路第二种工作票',
							data: lineTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F79646'
					            }
					        }
				    	},
				    	{
				        	name:'低压配网工作票',
							data: lowVoltage,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#FFC000'
					            }
					        }
				    	},
				    	{
				        	name:'带电作业工作票',
							data: liveWorking,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#2784D3'
					            }
					        }
				    	},
				    	{
				        	name:'紧急抢修工作票',
							data: urgentRepairs,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#00B0F0'
					            }
					        }
				    	},
				    	{
				        	name:'动火工作票',
							data: fire,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#9483DF'
					            }
					        }
				    	},
				    	{
				        	name:'调度维修单',
							data: dispatch,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#46A2A8'
					            }
					        }
				    	},
				    	{
				        	name:'未标注工作票',
							data: unMarked,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92CDDC'
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
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '3';
	        	lastFlag = '2';
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
			var stationOne = [];//厂站一种票数组
			var stationTwo = [];//厂站二种票数组
			var stationThree = [];//厂站三种票数组
			var lineOne = [];//线路一种票数组
			var lineTwo = [];//线路二种票数组
			var fire = [];//动火票数组
			var liveWorking = [];//带电作业工作票数组
			var lowVoltage = [];//低压配电网工作票数组
			var urgentRepairs = [];//紧急抢修工作票数组
			var dispatch = [];//调度检修数组
			var unMarked = [];//未标注工作票数组
			if(null != data){
				for(var i=0;i<AllUnitArr.length;i++) {
					
					for(var j=0;j<data.length;j++) {
						var map = data[j];
						//给前端图表需要的数组赋值
						if(map["unitname"].indexOf(AllUnitArr[i])!=-1) {
							stationOne.push(map["stationone"]);
							stationTwo.push(map["stationtwo"]);
							stationThree.push(map["stationthree"]);
							lineOne.push(map["lineone"]);
							lineTwo.push(map["linetwo"]);
							fire.push(map["fireone"]+map["firetwo"]);
							liveWorking.push(map["liveworking"]);
							lowVoltage.push(map["lowvoltage"]);
							urgentRepairs.push(map["urgentrepairs"]);
							dispatch.push(map["dispatch"]);
							unMarked.push(map["unmarked"]);
							break;
						}else {
							//sql统计出来的数据如果没有某一月的就赋值0
							if(j==data.length-1) {
								stationOne.push(0);
								stationTwo.push(0);
								stationThree.push(0);
								lineOne.push(0);
								lineTwo.push(0);
								fire.push(0);
								liveWorking.push(0);
								lowVoltage.push(0);
								urgentRepairs.push(0);
								dispatch.push(0);
								unMarked.push(0);
							}
						}
						
						
					}
				}
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "局内负责人和施工单位负责人工作票数量统计",
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
			            y2:'25%',
		                containLabel: true
		            },
		            legend:[
		            	{
		            		x : '1%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',	
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '80%',
		                    align: 'left',
			                data:['厂站第三种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '80%',
		                    align: 'left',
			                data:['线路第一种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '85%',
		                    align: 'left',
			                data:['线路第二种工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '85%',
		                    align: 'left',
			                data:['低压配网工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '85%',
		                    align: 'left',
			                data:['带电作业工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '76%',
		                    y : '85%',
		                    align: 'left',
			                data:['紧急抢修工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '1%',
		                    y : '90%',
		                    align: 'left',
			                data:['动火工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '26%',
		                    y : '90%',
		                    align: 'left',
			                data:['调度维修单'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
			            {
		            		x : '51%',
		                    y : '90%',
		                    align: 'left',
			                data:['未标注工作票'],
			                textStyle:{
			                	fontSize:10
			                }
			            },
		            ],
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["局内负责人","施工单位负责人"],
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
				        	name:'厂站第一种工作票',
							data: stationOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		color:'#00B050'
				            	}
				            }
				    	},
				    	{
				        	name:'厂站第二种工作票',
							data: stationTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#79BC32'
					            }
					        }
				    	},
				    	{
				        	name:'厂站第三种工作票',
							data: stationThree,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92D050'
					            }
					        }
				    	},
				    	{
				        	name:'线路第一种工作票',
							data: lineOne,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F4740C'
					            }
					        }
				    	},
				    	{
				        	name:'线路第二种工作票',
							data: lineTwo,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#F79646'
					            }
					        }
				    	},
				    	{
				        	name:'低压配网工作票',
							data: lowVoltage,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#FFC000'
					            }
					        }
				    	},
				    	{
				        	name:'带电作业工作票',
							data: liveWorking,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#2784D3'
					            }
					        }
				    	},
				    	{
				        	name:'紧急抢修工作票',
							data: urgentRepairs,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#00B0F0'
					            }
					        }
				    	},
				    	{
				        	name:'动火工作票',
							data: fire,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#9483DF'
					            }
					        }
				    	},
				    	{
				        	name:'调度维修单',
							data: dispatch,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#46A2A8'
					            }
					        }
				    	},
				    	{
				        	name:'未标注工作票',
							data: unMarked,
							type: 'bar',
							barWidth:20,
							stack:'数量',
					        itemStyle:{
					           normal:{
					            	color:'#92CDDC'
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
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = params.name;
	        	lastFlag = '2';
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
			var mainUnitPriList;
			var mainUnitConList;
			if(null != data){
				mainUnitPriList = data.mainUnitPriList;
				mainUnitConList = data.mainUnitConList;
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "主网负责人和施工单位负责人工作票数量统计",
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
		                data:['局内负责人','施工单位负责人'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["调控中心","输电一所","输电二所","变电一所","变电二所","变电三所"],
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
							data: mainUnitPriList,
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
				        	name:'施工单位负责人',
							data: mainUnitConList,
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
	        myChart7.setOption(option2);
	        myChart7.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '0';
	        	lastFlag = '2';
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
			var unitPriList;
			var unitConList;
			if(null != data){
				unitPriList = data.unitPriList;
				unitConList = data.unitConList;
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "配网局内负责人和施工单位负责人工作票数量统计",
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
		                data:['局内负责人','施工单位负责人'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["越秀","荔湾","海珠","天河","黄埔","白云","南沙","番禺","花都","增城","从化"],
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
							data: unitPriList,
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
				        	name:'施工单位负责人',
							data: unitConList,
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
	        //var myChart = echarts.init(document.getElementById('bar_8'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart8.setOption(option2);
	        myChart8.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '1';
	        	lastFlag = '2';
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
			var otherUnitPriList;
			var otherUnitConList;
			if(null != data){
				otherUnitPriList = data.otherUnitPriList;
				otherUnitConList = data.otherUnitConList;
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "其他单位负责人和施工单位负责人工作票数量统计",
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
			            y2:'17%',
		                containLabel: true
		            },
		            legend: {
		                data:['局内负责人','施工单位负责人'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["计量中心","试研院","通信中心"],
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
							data: otherUnitPriList,
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
				        	name:'施工单位负责人',
							data: otherUnitConList,
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
	        var myChart2 = echarts.init(document.getElementById('bar_9'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart9.setOption(option2);
	        myChart9.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '3';
	        	lastFlag = '2';
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
			var stateList;
			if(null != data){
				stateList = data.stateList;
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "各类状态工作票数量统计",
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
			            y2:'10%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量'],
		                top:'90%',
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["待签发","待会签","待接收","待许可","执行中","工作终结","工作票终结","已取消","已作废","已删除"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"30",
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
				        	name:'工作票数量',
							data: stateList,
							type: 'line',
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
	        //var myChart2 = echarts.init(document.getElementById('bar_10'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart10.setOption(option2);
	        myChart10.on("click",function (params){
	        	self.$dialog = $('#batch_manage_dialog');
	        	console.log(params);
	        	index = params.dataIndex;
	        	name = params.seriesName;
	        	isExtrl = '';
	        	lastFlag = '1';
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
		typeAllChartsDialog:function(){
			function showAllvalue(value){
				if(value==null){
					return null;
				}else{
					return '<span title='+value+'>'+value+'</span>';
				}
			}
			var self = this;
	    	var monthEnd = $('#end_date_batch').datebox('getValue');
	    	var monthBegin = $('#begin_date_batch').datebox('getValue');
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
			if(lastFlag=="1"){
				var str = newComluns[0].splice(11,6);
			}
			
			
			$('#typeAllChartsDialog_grid').datagrid({
				url: getOriginalUrl,
				queryParams: {
					index:index,
					name:name,
					isExtrl:isExtrl,
					monthEnd:monthEnd,
					monthBegin:monthBegin,
					lastFlag:lastFlag
				},
				columns: newComluns,
				toolbar : $('#typeAllChartsDialog_bar'),
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
	
	module.exports = new TicketNumCharts();
})