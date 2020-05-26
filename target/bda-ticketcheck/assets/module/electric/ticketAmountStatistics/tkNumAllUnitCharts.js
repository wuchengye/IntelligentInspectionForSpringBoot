

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	require('library/echarts4/echarts.min.js');
	
//	var getwrongwords = require("module/commonUtil/getwrongwords.js");
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/';//模块界面根目录
	
	var moduleRootUrl = 'electric/ticketCheck/';
	
	var getUnitNumAndCheckUrl = moduleRootUrl + 'getUnitNumAndCheck';
	var getUnitDelayNumTimeUrl = moduleRootUrl + 'getUnitDelayNumTime';
	var getKVLevelNumUrl = moduleRootUrl + 'getKVLevelNum';
	var getMajorNumAndCheckUrl = moduleRootUrl + 'getMajorNumAndCheck';
	var getDeviceNumAndCheckUrl = moduleRootUrl + 'getDeviceNumAndCheck';
	var openDialogUrl = moduleRootUrl +'openDialog';
	var width='500px';
	var height='400px';
	
	var begin = '';
	var end = '';
	var unit = '';
	
	var zwArr = ['电力调度控制中心','输电管理一所','输电管理二所','变电管理一所','变电管理二所','变电管理三所'];
	
	var pwArr = ['越秀','荔湾','海珠','天河','黄埔','白云','南沙','番禺','花都','增城','从化 '];
	
	var showPWArr = ['广州越秀供电局','广州荔湾供电局','广州海珠供电局','广州天河供电局','广州黄埔供电局',
	                 '广州白云供电局','广州南沙供电局','广州番禺供电局','广州花都供电局','广州增城供电局','广州从化供电局'];
	
	var qitaArr = ['计量中心','电力试验研究院','通信中心'];
	
	var showKVLevelArr = ["500kV","220kV","110kV","66kV","35kV","20KV","10kV","0.4kV"];
	
	var KVLevelArr = ["500KV","220KV","110KV","66KV","35KV","20KV","10KV","380V"];
	
	var majorArr = ["TICKET_SUB","TICKET_LINE","TICKET_DIS"];
	var major = ["变电专业","输电专业","配电专业"];
	
	var deviceArr = ["变压器","管廊","开关房","电房","断路器","隔离开关","母线","PT","CT","电容器"];
	
	var TicketNumCharts = function(){
		this.width = '655px';
		this.height = '350px';
		this.$form;
		
	}
	TicketNumCharts.prototype = {
		constructor: TicketNumCharts,
		
		init:function(){
			var self = this;
			self.$dialog = $("#batch_manage_dialog");
			self.$chartToolBar = $("#chartToolBar");
			self.$form = $("#myForm");
			self.$chartToolBar.on("click",".bda-btn-hitlsn-query",function(){
				var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				if(beginTime >endTime) {
		    		$.messager.alert('温馨提示', '开始时间不能大于结束时间', 'warning');
		    		return false;
		    	}
				if(!self.$form.form("validate")) {
					$.messager.alert('提示', "请选择条件", 'warning');
					return false;
				}
				self.doSearchBar1(zwArr,beginTime,endTime);
				self.doSearchBar2(zwArr,beginTime,endTime);
				self.doSearchBar3(showPWArr,beginTime,endTime);
				self.doSearchBar4(showPWArr,beginTime,endTime);
				self.doSearchBar5(qitaArr,beginTime,endTime);
				self.doSearchBar6(qitaArr,beginTime,endTime);
				self.doSearchBar7(null,beginTime,endTime);
				self.doSearchBar8(null,beginTime,endTime);
				self.doSearchBar9(deviceArr,beginTime,endTime);
				return false;
			})
			.on('click','.bda-btn-reset',function(){
				self.$form.form('reset');
				return false;
			})
			
			//self.initBar1(null);
			//self.initBar2(null);
			//self.initBar3(null);
			//self.initBar4(null);
			//self.initBar5(null);
			//self.initBar6(null);
			//self.initBar7(null);
			
		},
		doSearchBar1:function(units,beginTime,endTime){
			var self = this;
			var myChart1 = echarts.init(document.getElementById('bar_1'));
			myChart1.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getUnitNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart1.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar1(data);
					}
				}
			})
		},
		initBar1:function(data){
			var self = this	;
			var unit = '';
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<zwArr.length;i++){
				unit = data[zwArr[i]];
				if(null == unit || undefined == unit){
					ticketTotal.push(0);
					ticketQualified.push(0);
					ticketStandard.push(0);
				}else{
					ticketTotal.push(unit.totalNum);
					if(unit.qualified == null || unit.qualified == undefined){
						ticketQualified.push(0);
					}else{
						ticketQualified.push(((unit.qualified/unit.totalNum)*100).toFixed(2));
					}
					if(unit.standard == null || unit.standard == undefined){
						ticketStandard.push(0);
					}else{
						ticketStandard.push(((unit.standard/unit.totalNum)*100).toFixed(2));
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option1 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "主网运行单位工作票数量以及合格率规范率",
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
		                x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','工作票合格率','工作票规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : zwArr,
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
				        	name : '张',
				        	/*min	 : 0,
				        	max	 : 700*/
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        
				        },
				        {
				        	type : 'value',
				        	name : '%',
				        	min	 : 0,
				        	max	 : 100,
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: ticketTotal,
							barWidth:20,
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票合格率',
							data: ticketQualified,
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票规范率',
							data: ticketStandard,
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
	        var myChart1 = echarts.init(document.getElementById('bar_1'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart1.setOption(option1);
	        
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart1.resize();
	            },200)
	        });
	        
	        myChart1.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getUnitNumAndCheck";
				var seriesIndex = params.seriesIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":unitName,"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		
		
		doSearchBar2:function(units,beginTime,endTime){
			var self = this;
			var myChart2 = echarts.init(document.getElementById('bar_2'));
			myChart2.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getUnitDelayNumTimeUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart2.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar2(data);
					}
				}
			})
		},
		initBar2:function(data){
			var self = this	;
			var unit = '';
			//延期数量
			var delayNumArr = new Array();
			//延期时间
			var delayTimeArr = new Array();
			for(var i=0;i<zwArr.length;i++){
				unit = data[zwArr[i]];
				if(null == unit || undefined == unit){
					delayNumArr.push(0);
					delayTimeArr.push(0);
				}else{
					if(unit.delayNum == null || unit.delayNum == undefined){
						delayNumArr.push(0);
					}else{
						delayNumArr.push(unit.delayNum);
					}
					if(unit.delayTime == null || unit.delayTime == undefined){
						delayTimeArr.push(0);
					}else{
						delayTimeArr.push(unit.delayTime);
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "主网工作票延期数量与延期时间统计",
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
				    	 x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['延期数量','延期时间'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
				        textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : zwArr,
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
				        	name : '张',
				        	//min	 : 0,
				        	//max	 : 700
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        },
				        {
				        	type : 'value',
				        	name : '小时',
				        	//min	 : 0,
				        	//max	 : 100
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'延期数量',
							data: delayNumArr,
							barWidth:20,
							type: 'bar',
							barGap:'-0%',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'延期时间',
							data: delayTimeArr,
							type: 'bar',
							barWidth:20,
							yAxisIndex: 1,
							barGap:'-0%',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_2'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart2.resize();
	            },200)
	        });
	        
	        myChart2.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getUnitDelayNumTime";
				var seriesIndex = params.seriesIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":unitName,"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		
		doSearchBar3:function(units,beginTime,endTime){
			var self = this;
			var myChart3 = echarts.init(document.getElementById('bar_3'));
			myChart3.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getUnitNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart3.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar3(data);
					}
				}
			})
		},
		initBar3:function(data){
			var self = this	;
			var unit = '';
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<showPWArr.length;i++){
				unit = data[showPWArr[i]];
				if(null == unit || undefined == unit){
					ticketTotal.push(0);
					ticketQualified.push(0);
					ticketStandard.push(0);
				}else{
					ticketTotal.push(unit.totalNum);
					if(unit.qualified == null || unit.qualified == undefined){
						ticketQualified.push(0);
					}else{
						ticketQualified.push(((unit.qualified/unit.totalNum)*100).toFixed(2));
					}
					if(unit.standard == null || unit.standard == undefined){
						ticketStandard.push(0);
					}else{
						ticketStandard.push(((unit.standard/unit.totalNum)*100).toFixed(2));
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option3 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "配网运行单位工作票数量以及合格率规范率",
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
				    	x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','工作票合格率','工作票规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
				        textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : pwArr,
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
				        	name : '张',
				        	/*min	 : 0,
				        	max	 : 700*/
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        },
				        {
				        	type : 'value',
				        	name : '%',
				        	min	 : 0,
				        	max	 : 100,
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: ticketTotal,
							barWidth:20,
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'工作票合格率',
							data: ticketQualified,
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票规范率',
							data: ticketStandard,
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
	        var myChart3 = echarts.init(document.getElementById('bar_3'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart3.setOption(option3);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart3.resize();
	            },200)
	        });
	        myChart3.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getUnitNumAndCheck";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":showPWArr[dataIndex],"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		
		doSearchBar4:function(units,beginTime,endTime){
			var self = this;
			var myChart4 = echarts.init(document.getElementById('bar_4'));
			myChart4.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getUnitDelayNumTimeUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart4.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar4(data);
					}
				}
			})
		},
		initBar4:function(data){
			var self = this	;
			var unit = '';
			//延期数量
			var delayNumArr = new Array();
			//延期时间
			var delayTimeArr = new Array();
			for(var i=0;i<showPWArr.length;i++){
				unit = data[showPWArr[i]];
				if(null == unit || undefined == unit){
					delayNumArr.push(0);
					delayTimeArr.push(0);
				}else{
					if(unit.delayNum == null || unit.delayNum == undefined){
						delayNumArr.push(0);
					}else{
						delayNumArr.push(unit.delayNum);
					}
					if(unit.delayTime == null || unit.delayTime == undefined){
						delayTimeArr.push(0);
					}else{
						delayTimeArr.push(unit.delayTime);
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option4 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "配网工作票延期数量与延期时间统计",
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
				    	x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['延期数量','延期时间'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
				        textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : pwArr,
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
				        	name : '张',
				        	//min	 : 0,
				        	//max	 : 700
				        	splitLine:{
				            	show:false
							},
				        	axisLabel : {
				                fontSize:10
				            }
							
				        },
				        {
				        	type : 'value',
				        	name : '小时',
				        	//min	 : 0,
				        	//max	 : 100
				        	splitLine:{
				            	show:false
							},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'延期数量',
							data: delayNumArr,
							barWidth:20,
							type: 'bar',
							barGap:'-0%',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'延期时间',
							data: delayTimeArr,
							type: 'bar',
							barWidth:20,
							barGap:'-0%',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart4 = echarts.init(document.getElementById('bar_4'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart4.setOption(option4);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart4.resize();
	            },200)
	        });
	        myChart4.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getUnitDelayNumTime";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":showPWArr[dataIndex],"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		
		doSearchBar5:function(units,beginTime,endTime){
			var self = this;
			var myChart5 = echarts.init(document.getElementById('bar_5'));
			myChart5.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getUnitNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart5.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar5(data);
					}
				}
			})
		},
		initBar5:function(data){
			var self = this	;
			var unit = '';
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<qitaArr.length;i++){
				unit = data[qitaArr[i]];
				if(null == unit || undefined == unit){
					ticketTotal.push(0);
					ticketQualified.push(0);
					ticketStandard.push(0);
				}else{
					ticketTotal.push(unit.totalNum);
					if(unit.qualified == null || unit.qualified == undefined){
						ticketQualified.push(0);
					}else{
						ticketQualified.push(((unit.qualified/unit.totalNum)*100).toFixed(2));
					}
					if(unit.standard == null || unit.standard == undefined){
						ticketStandard.push(0);
					}else{
						ticketStandard.push(((unit.standard/unit.totalNum)*100).toFixed(2));
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option5 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "其他运行单位工作票数量以及合格率规范率",
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
				    	x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','工作票合格率','工作票规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
				        textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : qitaArr,
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
				        	name : '张',
				        	/*min	 : 0,
				        	max	 : 700*/
				        	splitLine:{
				            	show:false
							},
				        	axisLabel : {
				                fontSize:10
				            }
				        },
				        {
				        	type : 'value',
				        	name : '%',
				        	min	 : 0,
				        	max	 : 100,
				        	splitLine:{
				            	show:false
							},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: ticketTotal,
							barWidth:20,
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票合格率',
							data: ticketQualified,
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票规范率',
							data: ticketStandard,
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
	        var myChart5 = echarts.init(document.getElementById('bar_5'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart5.setOption(option5);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart5.resize();
	            },200)
	        });
	        myChart5.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getUnitNumAndCheck";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":qitaArr[dataIndex],"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		
		doSearchBar6:function(units,beginTime,endTime){
			var self = this;
			var myChart6 = echarts.init(document.getElementById('bar_6'));
			myChart6.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getUnitDelayNumTimeUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart6.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar6(data);
					}
				}
			})
		},
		initBar6:function(data){
			var self = this	;
			var unit = '';
			//延期数量
			var delayNumArr = new Array();
			//延期时间
			var delayTimeArr = new Array();
			for(var i=0;i<qitaArr.length;i++){
				unit = data[qitaArr[i]];
				if(null == unit || undefined == unit){
					delayNumArr.push(0);
					delayTimeArr.push(0);
				}else{
					if(unit.delayNum == null || unit.delayNum == undefined){
						delayNumArr.push(0);
					}else{
						delayNumArr.push(unit.delayNum);
					}
					if(unit.delayTime == null || unit.delayTime == undefined){
						delayTimeArr.push(0);
					}else{
						delayTimeArr.push(unit.delayTime);
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option6 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "其他运行单位工作票延期数量与延期时间统计",
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
				    	x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['延期数量','延期时间'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
				        textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : qitaArr,
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
				        	name : '张',
				        	//barCategoryGap:0,
				        	//min	 : 0,
				        	//max	 : 700
				        	splitLine:{
				            	show:false
							},
				        	axisLabel : {
				                fontSize:10
				            }
				        },
				        {
				        	type : 'value',
				        	name : '小时',
				        	//barCategoryGap:0,
				        	/*min	 : 0,
				        	max	 : 100*/
				        	splitLine:{
				            	show:false
							},
				        	axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'延期数量',
							data: delayNumArr,
							barWidth:20,
							type: 'bar',
							barGap:'-0%',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'延期时间',
							data: delayTimeArr,
							type: 'bar',
							barWidth:20,
							barGap:'-0%',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart6 = echarts.init(document.getElementById('bar_6'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart6.setOption(option6);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart6.resize();
	            },200)
	        });
	        myChart6.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getUnitDelayNumTime";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":qitaArr[dataIndex],"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		
		doSearchBar7:function(units,beginTime,endTime){
			var self = this;
			var myChart7 = echarts.init(document.getElementById('bar_7'));
			myChart7.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getKVLevelNumUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart7.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar7(data);
					}
				}
			})
		},
		initBar7:function(data){
			var self = this	;
			var unit = '';
			var levelArr = new Array();
			var showLevelArr = new Array();
			var totalNum = 0;
			//var kvLevel = unit.
			for(var i=0;i<KVLevelArr.length;i++){
				var levelNum = data[KVLevelArr[i]];
				if(levelNum == null || levelNum == undefined){
					levelArr.push(0);
				}else{
					totalNum = Number(totalNum) + Number(levelNum);
					levelArr.push(levelNum);
				}
			}
			for(var i=0;i<levelArr.length;i++){
				//levelArr.splice(i,1,levelArr[i]/totalNum * 100);//替换
				//{value:cntQualified, name:'合格'},
				var obj = {value:(levelArr[i]/totalNum * 100).toFixed(2),name:showKVLevelArr[i]};
				showLevelArr.push(obj);
			}
			var option7 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
				    title : {
				        text: '按电压等级统计工作票数量',
				        x:'center',
				        y:'15'
				    },
				    color:['#0070c0','#9483df','#78a0e5','#46a2a8','#87cb74','#00b050','#79bc32','#a4d86c'],
				    legend: {
				    	selectedMode:false,
				    	orient: 'horizontal',       // 'vertical'
				        x: 'center',                // 'center' | 'left' | {number},
				        y: '85%',                // 'center' | 'bottom' | {number}
				        data:showKVLevelArr,
				        textStyle:{
		                	fontSize:10
		                }
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {d}%"
				    },
				    calculable : true,
				    series : [
				        {
				            name:'按电压等级统计工作票数量',
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '50%'],
				            itemStyle: {
							    normal: {
							        label : {
							            show: true, 
							            position: 'outer',
							            formatter: '{b} ({d}%)'
							        }
							    }
							},
				            data:showLevelArr
				            	/*[
				                {value:cntQualified, name:'合格'},
				                {value:cntUnqualified, name:'不合格'},
				                //{value:cntException, name:'异常'}
				            ]*/
				        }
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
			var myChart7 = echarts.init(document.getElementById('bar_7'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart7.setOption(option7);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart7.resize();
	            },200)
	        });
	        myChart7.on('click', function (params) { 
	        	//console.log(params);
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getKVLevelNum";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":KVLevelArr[dataIndex]},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
			
		},
		doSearchBar8:function(units,beginTime,endTime){
			var self = this;
			var myChart8 = echarts.init(document.getElementById('bar_8'));
			myChart8.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getMajorNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart8.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar8(data);
					}
				}
			})
		},

		initBar8:function(data){
			var self = this	;
			var unit = '';
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<data.length;i++){
				//majorArr.push(data[i].name);
				ticketTotal.push(data[i].total);
				if(data[i].total == 0){
					ticketQualified.push(0);
					ticketStandard.push(0);
				}else{
					ticketQualified.push(((data[i].qualified/data[i].total)*100).toFixed(2));
					ticketStandard.push(((data[i].standard/data[i].total)*100).toFixed(2));
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option8 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "按专业统计工作票数量",
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
				    	x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','工作票合格率','工作票规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
				        textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : major,
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
				        	name : '张',
				        	/*min	 : 0,
				        	max	 : 700*/
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        },
				        {
				        	type : 'value',
				        	name : '%',
				        	min	 : 0,
				        	max	 : 100,
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: ticketTotal,
							barWidth:20,
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'工作票合格率',
							data: ticketQualified,
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票规范率',
							data: ticketStandard,
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
	        var myChart8 = echarts.init(document.getElementById('bar_8'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart8.setOption(option8);
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart8.resize();
	            },200)
	        });
	        myChart8.on('click', function (params) { 
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getMajorNumAndCheck";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":majorArr[dataIndex],"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		doSearchBar9:function(units,beginTime,endTime){
			var self = this;
			var myChart9 = echarts.init(document.getElementById('bar_9'));
			myChart9.showLoading({text:'正在加载数据'});
			$.ajax({
				url:getDeviceNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime,"units":units},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart9.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						if(null == data || undefined == data){
							//$.messager.alert("提示","查无数据！","warning");
							return false;
						}
						self.initBar9(data);
					}
				}
			})
		},
		initBar9:function(data){
			var self = this	;
			var unit = '';
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<deviceArr.length;i++){
				unit = data[deviceArr[i]];
				if(null == unit || undefined == unit){
					ticketTotal.push(0);
					ticketQualified.push(0);
					ticketStandard.push(0);
				}else{
					ticketTotal.push(unit.totalNum);
					if(unit.qualified == null || unit.qualified == undefined){
						ticketQualified.push(0);
					}else{
						ticketQualified.push(((unit.qualified/unit.totalNum)*100).toFixed(2));
					}
					if(unit.standard == null || unit.standard == undefined){
						ticketStandard.push(0);
					}else{
						ticketStandard.push(((unit.standard/unit.totalNum)*100).toFixed(2));
					}
				}
			}
			if(null != data){
				console.log("有数据");
			}
			var option9 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "主要设备类型统计工作票数量以及合格率规范率",
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
		                x: '20',
		                y: '70',
		                x2: '20',
		                y2: '15%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','工作票合格率','工作票规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: '90%', // 'center' | 'bottom' | {number}
		                textStyle:{
		                	fontSize:10
		                }
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : deviceArr,
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
				        	name : '张',
				        	/*min	 : 0,
				        	max	 : 700*/
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        
				        },
				        {
				        	type : 'value',
				        	name : '%',
				        	min	 : 0,
				        	max	 : 100,
				        	splitLine:{
				            	show:false
							},
							axisLabel : {
				                fontSize:10
				            }
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: ticketTotal,
							barWidth:20,
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票合格率',
							data: ticketQualified,
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
				        	name:'工作票规范率',
							data: ticketStandard,
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top',
				            			fontsize:10,
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
	        var myChart9 = echarts.init(document.getElementById('bar_9'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart9.setOption(option9);
	        
	        window.addEventListener("resize", function () { 
	            window.setTimeout(function(){
	             myChart9.resize();
	            },200)
	        });
	        
	        myChart9.on('click', function (params) { 
	        	console.log(params);
	        	var unitName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/getDeviceNumAndCheck";
				var seriesIndex = params.seriesIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"group":unitName,"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		getFormData:function(){
			var result = {};
			$.each($('#myForm').serializeArray(), function(index) {  
			    if(result[this['name']]) {
			        result[this['name']] = result[this['name']] + "," + this['value'];  
			    } else {  
			        result[this['name']] = this['value'];
			    }
			}); 
			/*result['checkResult'] = parent.$('#type').val();
			 
			result['p_ticketType'] = parent.$('#p_ticketType').val();
			result['p_batch'] = parent.$('#p_batchTime').val();
			result['p_workPrincipal'] = parent.$('#p_workPrincipal').val();
			result['p_ticketSigner'] = parent.$('#p_ticketSigner').val();
			result['p_workLicensor'] = parent.$('#p_workLicensor').val();
			result['p_department'] = parent.$('#p_department').val();
			result['p_ticketEndTime1'] = parent.$('#p_ticketEndTime1').val();
			result['p_ticketEndTime2'] = parent.$('#p_ticketEndTime2').val();*/
			 
			return result;
		}
	}
	
	module.exports = new TicketNumCharts();
})