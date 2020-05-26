

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	require('library/echarts4/echarts.min.js');
	
//	var getwrongwords = require("module/commonUtil/getwrongwords.js");
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/';//模块界面根目录
	
	var width='500px';
	var height='400px';
	
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
			self.initBar1(null);
			self.initBar2(null);
			self.initBar3(null);
			self.initBar4(null);
			self.initBar5(null);
			self.initBar6(null);
			self.initBar7(null);
		},
		initBar1:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "变电站对应工作票数量TOP10，以及月度合格率规范率",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '10%',
		                right: '6%',
		                bottom: '5%',
		                containLabel: true
		            },
		            legend: {
		                data:['持票数','月度合格率','月度规范率'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["七星变电站.10kV IIBM(从)","花城变电站.10kV IIBM(从)","松仔变电站.10kV IIBM(从)","黄陂变电站.10kV IIBM(从)","云平变电站.10kV IIBM(从)","增边变电站.10kV IIBM(从)","沦头变电站.10kV IIBM(从)","松仔变电站.10kV IIBM(主)","曾边变电站.10kV IIBM(主)","凌云变电站.10kV IIBM(从)"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 12
				        },
				        {
				        	type : 'value',
				        	name : '月合格率',
				        	min	 : 91.00,
				        	max	 : 99.00
				        }
				    ],
				    series : [
				        {
				        	name:'持票数',
							data: [10,9,9,8,8,8,7,7,6,6],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'月度合格率',
							data: [97.64,96.66,95.59,96.05,96.90,93.72,95.62,94.82,93.88,96.89],
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#F70F46'
					            }
					        }
				    	},
				    	{
				        	name:'月度规范率',
							data: [98.67,98.59,98.58,98.57,98.57,98.57,98.57,98.57,98.57,98.57],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#9BBB59'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_1'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
		initBar2:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "输电线路工作票数量top10,以及月度合格率规范率",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '8%',
		                right: '6%',
		                bottom: '5%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','月度合格率','月度规范率'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["220kV北涌乙线","220kV赤瑞乙线","220kV芳村站#2主变变高电缆","220kV芳村站#1主变变高电缆","110kV富浦甲线","110kV白泥线","110kV富浦乙线","110kV芳联福飘线","管道类型关缆","220kV恒增甲线"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 30
				        },
				        {
				        	type : 'value',
				        	name : '月合格率',
				        	min	 : 94.00,
				        	max	 : 99.00
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: [10,27,9,13,15,14,21,17,16,14],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'月度合格率',
							data: [97.64,96.66,95.59,96.05,96.90,93.72,95.62,94.82,93.88,96.89],
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#F70F46'
					            }
					        }
				    	},
				    	{
				        	name:'月度规范率',
							data: [98.67,98.59,98.58,98.57,98.57,98.57,98.57,98.57,98.57,98.57],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#9BBB59'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_2'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
		initBar3:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "巡检中心工作票数量以及月度合格率规范率",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '8%',
		                right: '6%',
		                bottom: '5%',
		                containLabel: true
		            },
		            legend: {
		                data:['巡检中心','月度合格率','月度规范率'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["A巡检中心","B巡检中心","C巡检中心","D巡检中心","E巡检中心","F巡检中心","G巡检中心","H巡检中心","I巡检中心","J巡检中心"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 12
				        },
				        {
				        	type : 'value',
				        	name : '月合格率',
				        	min	 : 91.00,
				        	max	 : 99.00
				        }
				    ],
				    series : [
				        {
				        	name:'巡检中心',
							data: [10,9,9,8,8,8,7,7,8,8],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'月度合格率',
							data: [97.64,96.66,95.59,96.05,96.90,93.72,95.62,94.82,93.88,96.89],
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#F70F46'
					            }
					        }
				    	},
				    	{
				        	name:'月度规范率',
							data: [98.67,98.59,98.58,98.57,98.57,98.57,98.57,98.57,98.57,98.57],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#9BBB59'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_3'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
		initBar4:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "各班组工作票数量以及月度合格率规范率",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '8%',
		                right: '6%',
		                bottom: '5%',
		                containLabel: true
		            },
		            legend: {
		                data:['持票数','月度合格率','月度规范率'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["维护一班","维护二班","维护三班","维护四班"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 30
				        },
				        {
				        	type : 'value',
				        	name : '月合格率',
				        	min	 : 94.00,
				        	max	 : 99.00
				        }
				    ],
				    series : [
				        {
				        	name:'持票数',
							data: [10,27,9,13],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'月度合格率',
							data: [97.64,96.66,95.59,96.05],
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#F70F46'
					            }
					        }
				    	},
				    	{
				        	name:'月度规范率',
							data: [98.67,98.59,98.58,98.57],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#9BBB59'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_4'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
		initBar5:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "按专业类型统计工作票数量",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '8%',
		                right: '6%',
		                containLabel: true
		            },
		            legend: {
		                data:['合格票数量','不合格数量','不合格占用比'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["检修","试验","运行","二次"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 160
				        },
				        {
				        	type : 'value',
				        	name : '月合格率',
				        	min	 : 0,
				        	max	 : 60.00
				        }
				    ],
				    series : [
				        {
				        	name:'合格票数量',
							data: [100,99,30,45],
							type: 'bar',
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'不合格数量',
							data: [50,20,5,20],
							type: 'bar',
							stack:'数量',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#92D050'
					            }
					        }
				    	},
				    	{
				        	name:'不合格占用比',
							data: [50.00,20.20,16.67,44.44],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#8064A2'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_5'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
		initBar6:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "工作任务类型工作票数量以及月度合格率规范率",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '8%',
		                right: '6%',
		                bottom: '5%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','月度合格率','月度规范率'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["PT检修","开关检修","母线检修","主变本体检修","电容器组检修","更换开关柜","配变检修","保护定检","开关预试","站用变预试"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 30
				        },
				        {
				        	type : 'value',
				        	name : '月合格率',
				        	min	 : 94.00,
				        	max	 : 99.00
				        }
				    ],
				    series : [
				        {
				        	name:'持票数',
							data: [10,27,9,13,15,14,21,17,16,14],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'月度合格率',
							data: [97.64,96.66,95.59,96.05,96.90,93.72,95.62,94.82,93.88,96.89],
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#F70F46'
					            }
					        }
				    	},
				    	{
				        	name:'月度规范率',
							data: [98.67,98.59,98.58,98.57,98.57,98.57,98.57,98.57,98.57,98.57],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#9BBB59'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_6'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
		initBar7:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option2 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "按设备类型统计工作票数量",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '8%',
		                right: '6%',
		                containLabel: true
		            },
		            legend: {
		                data:['合格票数量','不合格数量','不合格占用比'],
		                top:'95%'
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["变压器","组合电器","开关房","电房","断路器","隔离开关","母线","PT"],
				        	axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"0"
				            }
				        }
				    ],
				    yAxis : [
				        {
				        	type : 'value',
				        	name : '数量',
				        	min	 : 0,
				        	max	 : 160
				        }
				    ],
				    series : [
				        {
				        	name:'合格票数量',
							data: [50,20,10,30,60,80,75,56],
							type: 'bar',
							stack:'数量',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'不合格数量',
							data: [65,87,22,35,48,61,74,87],
							type: 'bar',
							stack:'数量',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true
				            		},
					            	color:'#92D050'
					            }
					        }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart2 = echarts.init(document.getElementById('bar_7'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart2.setOption(option2);
		},
	}
	
	module.exports = new TicketNumCharts();
})