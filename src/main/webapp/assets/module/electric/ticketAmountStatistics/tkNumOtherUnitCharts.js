

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	require('library/echarts4/echarts.min.js');
	
//	var getwrongwords = require("module/commonUtil/getwrongwords.js");
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/';//模块界面根目录
	
	var moduleRootUrl = 'electric/ticketCheck/';
	
	var otherDeptNumAndCheckUrl = moduleRootUrl + 'otherDeptNumAndCheck';
	
	var otherPersonNumAndCheckUrl = moduleRootUrl + 'otherPersonNumAndCheck';
	
	var openDialogUrl = moduleRootUrl +'openDialog';
	
	var width='500px';
	var height='400px';
	
	var begin = '';
	var end = '';
	var unit = '';
	
	/*var zwArr = ['电力调度控制中心','输电管理一所','输电管理二所','变电管理一所','变电管理二所','变电管理三所'];
	
	var pwArr = ['越秀','荔湾','海珠','天河','黄埔','白云','南沙','番禺','花都','增城','从化 '];
	
	var showPWArr = ['广州越秀供电局','广州荔湾供电局','广州海珠供电局','广州天河供电局','广州黄埔供电局',
	                 '广州白云供电局','广州南沙供电局','广州番禺供电局','广州花都供电局','广州增城供电局','广州从化供电局'];
	
	var qitaArr = ['计量中心','电力试验研究院','通信中心'];
	
	var showKVLevelArr = ["500kV","220kV","110kV","66kV","35kV","10kV","0.4kV"];
	
	var KVLevelArr = ["500KV","220KV","110KV","66KV","35KV","10KV","380V"];*/
	
	var otherArr = ['电力调度控制中心','输电管理一所','输电管理二所','变电管理一所','变电管理二所','变电管理三所',
	                    '广州越秀供电局','广州荔湾供电局','广州海珠供电局','广州天河供电局','广州黄埔供电局',
		                '广州白云供电局','广州南沙供电局','广州番禺供电局','广州花都供电局','广州增城供电局','广州从化供电局',
		                '计量中心','电力试验研究院','通信中心' ]
	var showOtherArr = ['调控中心','输电一所','输电二所','变电一所','变电二所','变电三所','越秀','荔湾','海珠','天河','黄埔',
	                '白云','南沙','番禺','花都','增城','从化 ','计量中心','试研院','通信中心']
	
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
				//var unit = $("#data_query").combobox("getValue")
				self.doSearchBar1(beginTime,endTime);
				self.doSearchBar3(beginTime,endTime);
				/*self.doSearchBar4(showPWArr,beginTime,endTime);
				self.doSearchBar5(qitaArr,beginTime,endTime);
				self.doSearchBar6(qitaArr,beginTime,endTime);
				self.doSearchBar7(null,beginTime,endTime);*/
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
		doSearchBar1:function(beginTime,endTime){
			var self = this;
			var myChart1 = echarts.init(document.getElementById('bar_1'));
			myChart1.showLoading({text:'正在加载数据'});
			$.ajax({
				url:otherDeptNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart1.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						//var otherUnits = resp.data.arr;
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
			//var qcCenter = new Array();
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<otherArr.length;i++){
				//qcCenter.push(item);
				unit = data[otherArr[i]];
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
				        text: "外委单位工作票数量以及合格率规范率",
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
				    	x: '10',
		                y: '80',
		                x2: '10',
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
				        	data : showOtherArr,
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
				            			fontsize:10
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
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				var findUrl = "electric/ticketExplore/otherDeptNumAndCheck";
				var seriesIndex = params.seriesIndex;
				var dataIndex = params.dataIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":otherArr[dataIndex],"beginTime":beginTime,"endTime":endTime,"url":findUrl,"seriesIndex":seriesIndex},
					//buttons:footButtons,
					minimizable:true,
					maximizable:true
				});
	        	$('#batch_manage_dialog').window('center');
	        });
		},
		doSearchBar3:function(beginTime,endTime){
			var self = this;
			var myChart3 = echarts.init(document.getElementById('bar_3'));
			myChart3.showLoading({text:'正在加载数据'});
			$.ajax({
				url:otherPersonNumAndCheckUrl,
				type:"post",
				data:{"beginTime":beginTime,"endTime":endTime},
				cache:false,
				dataType:"json",
				success:function(resp){
					myChart3.hideLoading();
					if(resp.meta.success){
						data = resp.data.unit;
						//var otherUnits = resp.data.arr;
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
			var otherPerson = new Array();
			//总量
			var ticketTotal = new Array();
			//合格
			var ticketQualified = new Array();
			//规范
			var ticketStandard = new Array();
			for(var i=0;i<data.length;i++){
				otherPerson.push(data[i].name);
				ticketTotal.push(data[i].total);
				if(data[i].total == 0){
					ticketQualified.push(0);
					ticketStandard.push(0);
				}else{
					ticketQualified.push(((data[i].qualified/data[i].total)*100).toFixed(2));
					ticketStandard.push(((data[i].standard/data[i].total)*100).toFixed(2));
				}
			}
			/*for(var i=0;i<otherArr.length;i++){
				//qcCenter.push(item);
				unit = data[otherArr[i]];
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
			}*/
			if(null != data){
				console.log("有数据");
			}
			var option3 = {
					textStyle:{
						fontFamily:'Microsoft YaHei',
					},
					title:{
				        text: "外委人员TOP10持工作票数量以及合格率规范率",
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
				        	data : otherPerson,
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
				            			fontsize:10
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
				            			fontsize:10
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
	        	var userName = params.name;
	        	var beginTime = $("#beginTime").datetimebox("getValue");
				var endTime = $("#endTime").datetimebox("getValue");
				//var unit = $("#data_query").combobox("getValue");
				var findUrl = "electric/ticketExplore/otherPersonNumAndCheck";
				var seriesIndex = params.seriesIndex;
	        	$("#batch_manage_dialog").dialog({
					title:'数据展示',
					width:'85%',
					height:'80%',
					modal:true,
					href:openDialogUrl,
					queryParams:{"unit":userName,"beginTime":beginTime,"endTime":endTime,
						"url":findUrl,"seriesIndex":seriesIndex},
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