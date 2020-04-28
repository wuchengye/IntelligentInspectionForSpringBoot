

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
			var option1 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "运行单位工作票数量以及对应变电站和输电线路数",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '0%',
		                right: '0%',
		                bottom: '8%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','变电站数','输电线路数'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: 'bottom', // 'center' | 'bottom' | {number}
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["越秀","荔湾","海珠","天河","黄埔","白云","南沙","番禺","花都","增城","从化","输一","输二","变一","变二","变三"],
				        	/*axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }*/
				        }
				    ],
				    yAxis : [
				        {type : 'value'}
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: [15, 16, 9, 12, 17, 18, 14, 20, 17, 9, 8, 10, 9, 15, 16, 23],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'变电站数',
							data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 16, 20],
							type: 'bar',
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
					            	color:'#92D050'
					            }
					        }
				    	},
				    	{
				        	name:'输电线路数',
							data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 2, 0, 0, 0],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
				            		color:'#DC853D'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart1 = echarts.init(document.getElementById('bar_1'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart1.setOption(option1);
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
				        text: "工作票三种人数，人均开票数",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '0%',
		                right: '0%',
		                bottom: '8%',
		                containLabel: true
		            },
		            legend: {
		                data:['三种人数','人均开票数'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: 'bottom', // 'center' | 'bottom' | {number}
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["越秀","荔湾","海珠","天河","黄埔","白云","南沙","番禺","花都","增城","从化","输一","输二","变一","变二","变三"],
				        	/*axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }*/
				        }
				    ],
				    yAxis : [
				        {type : 'value'}
				    ],
				    series : [
				        {
				        	name:'三种人数',
							data: [5, 8, 3, 4, 9, 6, 7, 4, 6, 3, 4, 7, 8, 3, 4, 12],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'人均开票数',
							data: [3, 2, 3, 3, 2, 3, 2, 5, 3, 3, 2, 4, 2, 5, 4, 2],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
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
		},
		
		initBar3:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option3 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "各单位工作票月度合格率规范率",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
		                left: '0%',
		                right: '0%',
		                bottom: '8%',
		                containLabel: true
		            },
		            legend: {
		                data:['合格率','规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: 'bottom', // 'center' | 'bottom' | {number}
		            },
				    xAxis : [
				        {
				        	type : 'category',
				        	data : ["越秀","荔湾","海珠","天河","黄埔","白云","南沙","番禺","花都","增城","从化","输一","输二","变一","变二","变三"],
				        	/*axisLabel : {//坐标轴刻度标签的相关设置。
				        		interval:0,
				                rotate:"45"
				            }*/
				        }
				    ],
				    yAxis : [
				        {type : 'value'}
				    ],
				    series : [
				        {
				        	name:'合格率',
							data: [54, 76, 56, 64, 65, 45, 67, 83, 69, 47, 71, 10, 8, 43, 37, 47],
							type: 'line',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
				            		color:'#92D050'
				            	}
				            }
				    	},
				    	{
				        	name:'规范率',
							data: [34, 56, 36, 44, 45, 25, 47, 63, 49, 27, 51, 7, 8, 23, 17, 27],
							type: 'line',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
				            		color:'#8064A2'
				            	}
				            }
				    	}
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart3 = echarts.init(document.getElementById('bar_3'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart3.setOption(option3);
		},
		
		initBar4:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option4 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "工作票延期时间与延期数量统计",
				        x:'center'
				    },
		            legend: {
		            	icon: "line", //circle，rect ，roundRect，triangle，diamond，pin，arrow，none
		                data: ['工作票延期数量','工作票延期时间'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: 'bottom', // 'center' | 'bottom' | {number}
		            },
		            tooltip:{
		            	enterable: true
		            },
		            grid: {
		                left: '0%',
		                right: '0%',
		                bottom: '8%',
		                containLabel: true
		            },
		            radar:{
		            	shape: 'circle',
		                splitNumber: -1,     // 雷达图圈数设置
		                //设置雷达图中间射线的颜色
		                axisLine: {
		                    lineStyle: {
		                        color: 'rgba(250,192,144)',
		                        },
		                },
		            	indicator: [
                            { text: '越秀' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '荔湾' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '海珠' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '天河' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '黄埔' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '白云' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '南沙' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '番禺' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '花都' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '增城' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '从化' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '输一' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '输二' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '变一' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '变二' , max: 25, color: "black", fontFamily:'Microsoft YaHei'},
                            { text: '变三' , max: 25, color: "black", fontFamily:'Microsoft YaHei'}
                        ],
		            	
                        splitArea : {
                        	show : false,
                        	areaStyle : {
                        		color: 'rgba(255,0,0,0)', // 图表背景的颜色
                        	},
                        }
		            },
				    series : [
						{
						    name: '雷达图',
						    type: 'radar',
						    data: [
						        {
						            value: [15, 16, 9, 12, 17, 18, 14, 20, 17, 9, 8, 10, 9, 15, 16, 23],
						            name: '工作票延期数量',
						            lineStyle: {
						                normal: {
						                    color: '#92D050'
						                }
						            }
						        },
						        {
						            value: [5, 10, 8, 9, 17, 18, 7, 6, 5, 4, 3, 7, 8, 2, 1, 20],
						            name: '工作票延期时间',
						            lineStyle: {
						                normal: {
						                    color: '#4F80BD'
						                }
						            }
						        }
						    ]
						},
				    ]
				};
			
			// 基于准备好的dom，初始化echarts实例
	        var myChart4 = echarts.init(document.getElementById('bar_4'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart4.setOption(option4);
		},
		
		initBar5:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option5 = {
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
		                left: '0%',
		                right: '0%',
		                bottom: '8%',
		                containLabel: true
		            },
		            legend: {
		                data:['工作票数量','月度合格率','月度规范率'],
		                orient: 'horizontal', // 'vertical'
				        x: 'center', // 'center' | 'left' | {number},
				        y: 'bottom', // 'center' | 'bottom' | {number}
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
				        	//name : '数量',
				        	min	 : 0,
				        	max	 : 30
				        },
				        {
				        	type : 'value',
				        	//name : '月合格率',
				        	min	 : 91.00,
				        	max	 : 99.00
				        }
				    ],
				    series : [
				        {
				        	name:'工作票数量',
							data: [10, 27, 9, 13, 15, 14, 21, 17, 16, 14],
							type: 'bar',
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
				            		color:'#4F80BD'
				            	}
				            }
				    	},
				    	{
				        	name:'月度合格率',
							data: [97.64, 96.66, 95.59, 96.05, 97.64, 96.66, 95.59, 96.05, 95.59, 96.05],
							type: 'line',
							yAxisIndex: 1,
					        itemStyle:{
					           normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
				            		},
					            	color:'#DC853D'
					            }
					        }
				    	},
				    	{
				        	name:'月度规范率',
							data: [98.67, 98.59, 98.58, 98.57, 98.67, 98.59, 98.58, 98.57, 98.58, 98.57],
							type: 'line',
							yAxisIndex: 1,
				            itemStyle:{
				            	normal:{
				            		label:{
				            			show: true,
				            			position: 'top'
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
		},
		
		initBar6:function(data){
			var self = this	;
			
			if(null != data){
				console.log("有数据");
			}
			var option6 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "按专业统计工作票数量",
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    grid: {
				    	left: '0%',
		                right: '0%',
		                bottom: '8%',
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
							barWidth: 30,
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
							barWidth: 30,
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
	        var myChart6 = echarts.init(document.getElementById('bar_6'));
	        // 使用刚指定的配置项和数据显示图表。
	        myChart6.setOption(option6);
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
				        	data : ["变压器","组合电器","开关房","电房","断路器","隔离开关","母线","PT","CT","其它"],
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
							data: [50,20,10,30,60,80,75,56,30,56],
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
							data: [65,87,22,35,48,61,74,87,35,87],
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