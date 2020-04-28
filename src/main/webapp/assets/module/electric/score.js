/**
 * 工作负责人得分汇总
 */
define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/workpriscore/';//模块界面根目录
	var getHitListenDataUrl = moduleRequestRootUrl + 'getWorkPriScore';
	var getMonthRewardUrl = moduleRequestRootUrl + 'getMonthReward';
	var getYearRewardUrl = moduleRequestRootUrl + 'getYearReward';
	var getMonthVerifyUrl = moduleRequestRootUrl + 'getMonthVerify';
	var getYearVerifyUrl = moduleRequestRootUrl + 'getYearVerify';
	var updateMonthUrl = moduleRequestRootUrl + 'updateMonth';
	var updateYearUrl = moduleRequestRootUrl + 'updateYear';
	var getModulusUrl = moduleRequestRootUrl + 'getModulus';
	var updateModulusUrl = moduleRequestRootUrl + 'updateModulus';
	var uploadWebUrl = moduleWebRootUrl +'upload.jsp';
	var moduluWebUrl = moduleWebRootUrl +'modulu.jsp';
	var monthRewardWebUrl = moduleWebRootUrl +'monthReward.jsp';
	var yearRewardWebUrl = moduleWebRootUrl +'yearReward.jsp';
	var exportUrl = moduleRequestRootUrl +'export';
	var width='500px';
	var height='400px';
	
	var year;
	
	var score = function(){
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
		this.$reportType;
	}
	score.prototype = {
			constructor: score,
			score:function() {
				extend.queryCombotree("#unitName");
				var self = this;
				
				self.$grid = $('#score_grid');
				
				year = $("#myYear").combobox('getValue');
				self.$reportType = $("#reportType").combobox("getValue");
				var newColumns = [[
					{field:'unitName',title:'单位名称',width:100,align:'center'},
					{field:'deptmentName',title:'部门',width:100,align:'center'},
					{field:'groupName',title:'班组',width:100,align:'center'},
					{field:'personName',title:'人员姓名',width:100,align:'center'},
					{field:'specialty',title:'所属专业',width:150,align:'center'}/*,
					{field:'classes',title:'所属班组',width:100,align:'center'}*/
				]]
				var Columns = [[
						{field:'b',title: year + "年" + self.$reportType + "得分汇总",align:'center',colspan:13},
						{field:'c',title:'月度累计优秀奖',align:'center',colspan:6},
						{field:'d',title:'年度累计优秀奖',align:'center',colspan:7},
						],
						[{field:'one',title:'1月得分',width:100,align:'center',formatter:formatValue},
						{field:'two',title:'2月得分',width:100,align:'center',formatter:formatValue},
						{field:'three',title:'3月得分',width:100,align:'center',formatter:formatValue},
						{field:'four',title:'4月得分',width:100,align:'center',formatter:formatValue},
						{field:'five',title:'5月得分',width:100,align:'center',formatter:formatValue},
						{field:'six',title:'6月得分',width:100,align:'center',formatter:formatValue},
						{field:'seven',title:'7月得分',width:100,align:'center',formatter:formatValue},
						{field:'eight',title:'8月得分',width:100,align:'center',formatter:formatValue},
						{field:'nine',title:'9月得分',width:100,align:'center',formatter:formatValue},
						{field:'ten',title:'10月得分',width:100,align:'center',formatter:formatValue},
						{field:'eleven',title:'11月得分',width:100,align:'center',formatter:formatValue},
						{field:'twelve',title:'12月得分',width:100,align:'center',formatter:formatValue},
						{field:'total',title:'汇总',width:100,align:'center',formatter:formatValue},
						{field:'monthName',title:'姓名',width:100,align:'center'},
						{field:'monthScore',title:'得分',width:100,align:'center',formatter:formatValue},
						{field:'monthQualifications',title:'三种人资格',width:100,align:'center'},
						{field:'monthMajor',title:'专业',width:100,align:'center'},
						{field:'monthLevel',title:'奖励级别',width:100,align:'center'/*,
							formatter: function(value,row,index){
								if(row.monthMajor == '输电' || row.monthMajor == '变电运行' || row.monthMajor == '配电'){
									if(row.monthScore >= 250) return '五星工作负责人';
									if(row.monthScore >= 200) return '四星工作负责人';
									if(row.monthScore >= 150) return '三星工作负责人';
									if(row.monthScore >= 100) return '二星工作负责人';
									if(row.monthScore >= 50) return '一星工作负责人';
								}else if(row.monthMajor == '变电二次'){
									if(row.monthScore >= 500) return '五星工作负责人';
									if(row.monthScore >= 400) return '四星工作负责人';
									if(row.monthScore >= 300) return '三星工作负责人';
									if(row.monthScore >= 200) return '二星工作负责人';
									if(row.monthScore >= 100) return '一星工作负责人';
								}else if(row.monthMajor == '变电检修试验'){
									if(row.monthScore >= 650) return '五星工作负责人';
									if(row.monthScore >= 520) return '四星工作负责人';
									if(row.monthScore >= 390) return '三星工作负责人';
									if(row.monthScore >= 260) return '二星工作负责人';
									if(row.monthScore >= 130) return '一星工作负责人';
								}
							}*/
						},
						{field:'monthMoney',title:'奖励金额（元）',width:100,align:'center'/*,
							formatter: function(value,row,index){
								if(row.monthMajor == '输电' || row.monthMajor == '变电运行' || row.monthMajor == '配电'){
									if(row.monthScore >= 250) return '20000元';
									if(row.monthScore >= 200) return '10000元';
									if(row.monthScore >= 150) return '5000元';
									if(row.monthScore >= 100) return '3000元';
									if(row.monthScore >= 50) return '2000元';
								}else if(row.monthMajor == '变电二次'){
									if(row.monthScore >= 500) return '20000元';
									if(row.monthScore >= 400) return '10000元';
									if(row.monthScore >= 300) return '5000元';
									if(row.monthScore >= 200) return '3000元';
									if(row.monthScore >= 100) return '2000元';
								}else if(row.monthMajor == '变电检修试验'){
									if(row.monthScore >= 650) return '20000元';
									if(row.monthScore >= 520) return '10000元';
									if(row.monthScore >= 390) return '5000元';
									if(row.monthScore >= 260) return '3000元';
									if(row.monthScore >= 130) return '2000元';
								}
							}*/
						},
						{field:'yearUnitName',title:'单位名称',width:100,align:'center'},
						{field:'yearName',title:'姓名',width:100,align:'center'},
						{field:'yearScore',title:'得分',width:100,align:'center',formatter:formatValue},
						{field:'yearQualifications',title:'三种人资格',width:100,align:'center'},
						{field:'yearMajor',title:'专业',width:100,align:'center'},
						{field:'yearLevel',title:'奖励级别',width:100,align:'center'/*,
							formatter: function(value,row,index){
								if(row.monthMajor == '输电'){
									if(row.monthScore >= 30) return '一等奖';
									if(row.monthScore >= 20) return '二等奖';
									if(row.monthScore >= 15) return '三等奖';
									if(row.monthScore >= 10) return '四等奖';
									if(row.unitRanking <= 8) return '鼓励奖';
								}else if(row.monthMajor == '变电运行'){
									if(row.monthScore >= 50) return '一等奖';
									if(row.monthScore >= 30) return '二等奖';
									if(row.monthScore >= 20) return '三等奖';
									if(row.monthScore >= 10) return '四等奖';
									if(row.unitRanking <= 8) return '鼓励奖';
								}else if(row.monthMajor == '变电二次'){
									if(row.monthScore >= 80) return '一等奖';
									if(row.monthScore >= 60) return '二等奖';
									if(row.monthScore >= 40) return '三等奖';
									if(row.monthScore >= 30) return '四等奖';
									if(row.unitRanking <= 8) return '鼓励奖';
								}else if(row.monthMajor == '变电检修试验'){
									if(row.monthScore >= 80) return '一等奖';
									if(row.monthScore >= 60) return '二等奖';
									if(row.monthScore >= 50) return '三等奖';
									if(row.monthScore >= 40) return '四等奖';
									if(row.unitRanking <= 8) return '鼓励奖';
								}else if(row.monthMajor == '配电'){
									if(row.monthScore >= 40) return '一等奖';
									if(row.monthScore >= 30) return '二等奖';
									if(row.monthScore >= 20) return '三等奖';
									if(row.monthScore >= 10) return '四等奖';
									if(row.unitRanking <= 8) return '鼓励奖';
								}
							}*/
						},
						{field:'yearMoney',title:'奖励金额（元）',width:100,align:'center'/*,
							formatter: function(value,row,index){
								if(row.monthMajor == '输电'){
									if(row.monthScore >= 30) return '5000';
									if(row.monthScore >= 20) return '4000';
									if(row.monthScore >= 15) return '3000';
									if(row.monthScore >= 10) return '2000';
									if(row.unitRanking <= 8) return '1500';
								}else if(row.monthMajor == '变电运行'){
									if(row.monthScore >= 50) return '5000';
									if(row.monthScore >= 30) return '4000';
									if(row.monthScore >= 20) return '3000';
									if(row.monthScore >= 10) return '2000';
									if(row.unitRanking <= 8) return '1500';
								}else if(row.monthMajor == '变电二次'){
									if(row.monthScore >= 80) return '5000';
									if(row.monthScore >= 60) return '4000';
									if(row.monthScore >= 40) return '3000';
									if(row.monthScore >= 30) return '2000';
									if(row.unitRanking <= 8) return '1500';
								}else if(row.monthMajor == '变电检修试验'){
									if(row.monthScore >= 80) return '5000';
									if(row.monthScore >= 60) return '4000';
									if(row.monthScore >= 50) return '3000';
									if(row.monthScore >= 40) return '2000';
									if(row.unitRanking <= 8) return '1500';
								}else if(row.monthMajor == '配电'){
									if(row.monthScore >= 40) return '5000';
									if(row.monthScore >= 30) return '4000';
									if(row.monthScore >= 20) return '3000';
									if(row.monthScore >= 10) return '2000';
									if(row.unitRanking <= 8) return '1500';
								}
							}*/
						},
						]]
				
				function formatValue(value){
					if(value.indexOf(".0") > -1){
						return value.replace(".0","");
					}
					if(value.indexOf(".") == 0){
						return "0"+value;
					}else{
						return value;
					}
				}
				
				self.$grid.datagrid({
					//url: getHitListenDataUrl,
					queryParams: {status: '1'},
					frozenColumns:newColumns,
					columns: Columns,
					toolbar : $('#score_bar'),
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
						var span = $($("td[field='b']").find("div")[0]).find("span")[0];
						$(span).text( year + "年" + self.$reportType + "得分汇总" );
						//$('table#news_grid').parent().siblings('.datagrid-pager').find('table').remove();
//						var pageInfo = $('table#news_grid').parent().siblings('div.datagrid-pager').find('div.pagination-info').text();
//						pageInfo = pageInfo.substring(pageInfo.indexOf(',')+1, pageInfo.length);
//						var sum = pageInfo.replace(/[^0-9]/ig,"");
					},
//					data:[{'unitName':'变电一所','personName':'张三','specialty':'输电','classes':'巡视','one':'43','two':'23','three':'123','four':'232','five':'53','six':'54','seven':'34','eight':'565','nine':'53','ten':'45','eleven':'43','twelve':'234','total':'2442','monthName':'张三','monthScore':'576','monthQualifications':'工作许可人','monthMajor':'输电','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'变电一所','yearName':'张三','yearScore':'425','yearQualifications':'工作许可人','yearMajor':'输电','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'变电二所','personName':'李四','specialty':'变电运行','classes':'巡视','one':'23','two':'3','three':'22','four':'23','five':'34','six':'34','seven':'45','eight':'32','nine':'34','ten':'54','eleven':'23','twelve':'34','total':'3125','monthName':'李四','monthScore':'345','monthQualifications':'工作负责人','monthMajor':'变电运行','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'变电二所','yearName':'李四','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'变电运行','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'变电三所','personName':'王五','specialty':'变电二次（含计量、通信）','classes':'巡视','one':'1','two':'34','three':'454','four':'32','five':'33','six':'34','seven':'53','eight':'43','nine':'52','ten':'54','eleven':'56','twelve':'45','total':'3707','monthName':'王五','monthScore':'453','monthQualifications':'工作票签发人','monthMajor':'变电二次（含计量、通信）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'变电三所','yearName':'王五','yearScore':'234','yearQualifications':'工作许可人','yearMajor':'变电二次（含计量、通信）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'输电一所','personName':'赵六','specialty':'变电检修试验（含试研院）','classes':'巡视','one':'3','two':'54','three':'11','four':'432','five':'342','six':'34','seven':'34','eight':'234','nine':'234','ten':'54','eleven':'765','twelve':'654','total':'2342','monthName':'赵六','monthScore':'634','monthQualifications':'工作许可人','monthMajor':'变电检修试验（含试研院）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'输电一所','yearName':'赵六','yearScore':'432','yearQualifications':'工作许可人','yearMajor':'变电检修试验（含试研院）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'输电二所','personName':'廖七','specialty':'配电（含路灯所）','classes':'巡视','one':'23','two':'45','three':'34','four':'32','five':'23','six':'345','seven':'54','eight':'34','nine':'43','ten':'543','eleven':'765','twelve':'56','total':'2325','monthName':'廖七','monthScore':'345','monthQualifications':'工作负责人','monthMajor':'配电（含路灯所）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'输电二所','yearName':'廖七','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'配电（含路灯所）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'试验院','personName':'罗八','specialty':'输电','classes':'巡视','one':'3','two':'34','three':'22','four':'','five':'665','six':'34','seven':'534','eight':'543','nine':'234','ten':'3445','eleven':'67','twelve':'65','total':'4565','monthName':'罗八','monthScore':'634','monthQualifications':'工作票签发人','monthMajor':'输电','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'试验院','yearName':'罗八','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'输电','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'越秀供电局','personName':'钟九','specialty':'变电运行','classes':'巡视','one':'2','two':'223','three':'32','four':'54','five':'43','six':'43','seven':'234','eight':'345','nine':'45','ten':'43','eleven':'75','twelve':'654','total':'65','monthName':'钟九','monthScore':'345','monthQualifications':'工作许可人','monthMajor':'变电运行','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'越秀','yearName':'钟九','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'变电运行','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'荔湾供电局','personName':'曹十','specialty':'变电二次（含计量、通信）','classes':'巡视','one':'23','two':'','three':'43','four':'65','five':'23','six':'54','seven':'453','eight':'34','nine':'654','ten':'55','eleven':'56','twelve':'34','total':'2345','monthName':'曹十','monthScore':'643','monthQualifications':'工作负责人','monthMajor':'变电二次（含计量、通信）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'荔湾','yearName':'曹十','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'变电二次（含计量、通信）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'海珠供电局','personName':'曾十一','specialty':'变电检修试验（含试研院）','classes':'巡视','one':'23','two':'','three':'32','four':'44','five':'363','six':'34','seven':'53','eight':'54','nine':'45','ten':'568','eleven':'76','twelve':'543','total':'5234','monthName':'曾十一','monthScore':'346','monthQualifications':'工作票签发人','monthMajor':'变电检修试验（含试研院）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'海珠','yearName':'曾十一','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'变电检修试验（含试研院）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'天河供电局','personName':'雷十二','specialty':'配电（含路灯所）','classes':'巡视','one':'123','two':'45','three':'333','four':'5','five':'345','six':'23','seven':'432','eight':'44','nine':'43','ten':'67','eleven':'76','twelve':'543','total':'4235','monthName':'雷十二','monthScore':'673','monthQualifications':'工作许可人','monthMajor':'配电（含路灯所）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'天河','yearName':'雷十二','yearScore':'432','yearQualifications':'工作许可人','yearMajor':'配电（含路灯所）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'黄埔供电局','personName':'常十三','specialty':'输电','classes':'巡视','one':'23','two':'43','three':'86','four':'46','five':'54','six':'234','seven':'234','eight':'656','nine':'23','ten':'44','eleven':'','twelve':'66','total':'5460','monthName':'常十三','monthScore':'635','monthQualifications':'工作负责人','monthMajor':'输电','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'黄埔','yearName':'常十三','yearScore':'234','yearQualifications':'工作许可人','yearMajor':'输电','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'白云供电局','personName':'谢十四','specialty':'变电运行','classes':'巡视','one':'3','two':'32','three':'67','four':'43','five':'34','six':'345','seven':'321','eight':'777','nine':'53','ten':'34','eleven':'','twelve':'87','total':'5354','monthName':'谢十四','monthScore':'346','monthQualifications':'工作票签发人','monthMajor':'变电运行','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'白云','yearName':'谢十四','yearScore':'423','yearQualifications':'工作许可人','yearMajor':'变电运行','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'南沙供电局','personName':'黎十五','specialty':'变电二次（含计量、通信）','classes':'巡视','one':'23','two':'55','three':'45','four':'23','five':'665','six':'34','seven':'333','eight':'234','nine':'23','ten':'543','eleven':'66','twelve':'534','total':'3423','monthName':'黎十五','monthScore':'345','monthQualifications':'工作许可人','monthMajor':'变电二次（含计量、通信）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'南沙','yearName':'黎十五','yearScore':'234','yearQualifications':'工作许可人','yearMajor':'变电二次（含计量、通信）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'番禺供电局','personName':'姚磊','specialty':'变电检修试验（含试研院）','classes':'巡视','one':'23','two':'56','three':'32','four':'43','five':'34','six':'54','seven':'534','eight':'345','nine':'42','ten':'66','eleven':'678','twelve':'34','total':'3427','monthName':'姚磊','monthScore':'634','monthQualifications':'工作负责人','monthMajor':'变电检修试验（含试研院）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'番禺','yearName':'姚磊','yearScore':'345','yearQualifications':'工作许可人','yearMajor':'变电检修试验（含试研院）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'花都供电局','personName':'左十六','specialty':'配电（含路灯所）','classes':'巡视','one':'23','two':'76','three':'79','four':'53','five':'63','six':'345','seven':'234','eight':'33','nine':'43','ten':'76','eleven':'78','twelve':'54','total':'5345','monthName':'左十六','monthScore':'534','monthQualifications':'工作票签发人','monthMajor':'配电（含路灯所）','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'花都','yearName':'左十六','yearScore':'564','yearQualifications':'工作许可人','yearMajor':'配电（含路灯所）','yearLevel':'四星工作许可人','yearMoney':'5000'},
//						{'unitName':'增城供电局','personName':'苗十七','specialty':'输电','classes':'巡视','one':'12','two':'88','three':'70','four':'45','five':'66','six':'54','seven':'655','eight':'45','nine':'34','ten':'876','eleven':'66','twelve':'43','total':'5543','monthName':'苗十七','monthScore':'532','monthQualifications':'工作许可人','monthMajor':'输电','monthLevel':'五星工作负责人','monthMoney':'2000','yearUnitName':'增城','yearName':'苗十七','yearScore':'456','yearQualifications':'工作许可人','yearMajor':'输电','yearLevel':'四星工作许可人','yearMoney':'5000'},]
				});
				self.initEleEvent();

			},
			initEleEvent:function(){
				var self = this	;
				
				self.$dialog = $('#score_win');
				//点击配置系数事件
				$('a.bda-btn-hitlsn-modulu').bind('click', function() {
					var importButtons=[{
						text:'关闭',
						handler:function(){
							self.$dialog.dialog("destroy");
							self.$grid.datagrid("reload");
						}
					}];
					
					self.$dialog.dialog({
						title:'配置系数',
						width: '700px',
						height: '400px',
						modal:true,
						href:moduluWebUrl,
						queryParams:{},
						buttons:importButtons,
//						minimizable:true,
						maximizable:true,
						resizable:true,
						onClose:function(){
							self.$grid.datagrid("reload");
						}
					}); 
					return false;
				});
				
				//点击配置月度奖励分数事件
				$('a.bda-btn-hitlsn-month').bind('click', function() {
					var importButtons=[{
						text:'关闭',
						handler:function(){
							self.$dialog.dialog("destroy");
							self.$grid.datagrid("reload");
						}
					}];
					
					self.$dialog.dialog({
						title:'配置月度奖励分数',
						width: '700px',
						height: '400px',
						modal:true,
						href:monthRewardWebUrl,
						queryParams:{},
						buttons:importButtons,
//						minimizable:true,
						maximizable:true,
						resizable:true,
						onClose:function(){
							self.$dialog.dialog("destroy");
							self.$grid.datagrid("reload");
						}
					}); 
					return false;
				});
				
				//点击配置年度奖励分数事件
				$('a.bda-btn-hitlsn-year').bind('click', function() {
					var importButtons=[{
						text:'关闭',
						handler:function(){
							self.$dialog.dialog("destroy");
							self.$grid.datagrid("reload");
						}
					}];
					
					self.$dialog.dialog({
						title:'配置年度奖励分数',
						width: '700px',
						height: '400px',
						modal:true,
						href:yearRewardWebUrl,
						queryParams:{},
						buttons:importButtons,
//						minimizable:true,
						maximizable:true,
						resizable:true,
						onClose:function(){
							self.$dialog.dialog("destroy");
							self.$grid.datagrid("reload");
						}
					}); 
					return false;
				});
				
				//点击查询事件
				$('a.bda-btn-hitlsn-query').bind('click', function() {
					self.doSearch('#unitName', '#personName');
				});
				//重置
				$('#score_bar').on('click','.bda-btn-hitlsn-reset',function(){
					var myDate= new Date();
					$("#scoreForm").form('reset');
					$("#myYear").combobox("select",myDate.getFullYear());
					return false;
				})
				//点击导出事件
				.on('click','.bda-btn-hitlsn-export',function(){
					
					year = $("#myYear").combobox('getValue');
					self.$reportType = $("#reportType").combobox('getValue');
					
					if(null == year || "" == year){
						$.messager.alert("提示","请选择年份！","info");
						return false;
					}
					
					if(null == self.$reportType || "" == self.$reportType){
						$.messager.alert("提示","请选择报表类型！","info");
						return false;
					}
					var unitName = $("#unitName").combotree("getText")
			    	//var unitName = $("#unitName").combobox('getValue');
			    	var personName = $("#personName").textbox('getValue');
			    	var specialty = $("#specialty").combobox('getValue');
			    	var t = $("#unitName").combotree('tree');  
			    	var x = null;
			    	var level = null;
			    	if(unitName != null && unitName != ""){
			    		x = t.tree("getSelected").id;
				    	level = t.tree("getSelected").level;
			    	}
			    	
			    	//var classes = $("#classes").combobox('getValue');
					var params = {
							'reportType':self.$reportType,
							'year':year,
							'unitName': unitName,
							'personName': personName,
							'specialty':specialty,
							'orgId':x,
							'level':level,
							//'classes':classes
					};
					
				    $.messager.confirm('请确认','导出工作票得分统计表吗？',function(r){
					    if (r){
					    	parent.$.messager.progress({
								title : '提示',
								text : '导出中，请稍后....'
							});
					    	
//					    	var $form = $('<form></form>')
//					    	$form.attr('action', exportUrl);
//					    	$form.attr('method', 'post');
//							$(document.body).append($form);
//					    	$form.submit();
//					    	$form.remove();
//					    	self.exportKeyword();
					    	
					    	$.ajax({
								url: exportUrl,
								type: "post",
								data: params,
								cache: false,
								dataType: "json",
								success: function(data){
									parent.$.messager.progress('close');
									if(data.meta.success){
										var $form = $('<form></form>')
								    	$form.attr('method', 'post');
										$(document.body).append($form);
										$form.form('submit', {
											url: "common/downloadFile/downloadReport",
											queryParams: {
												fileName: data.meta.code
											}
										});
								    	$form.remove();
									}else{
										$.messager.alert('错误', "导出失败", 'error');
									}
								}
							});
					    }
					});
				})
				//得分统计公式
				.on("click",".bda-btn-hitlsn-score",function(){
					$("#score_statictis").show();
					$("#score_statictis").dialog({
						title:"得分统计公式",
						width: 500,
						height: 280,
						resizable:true
					})
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
			doSearch:function(unitNameSelector, personNameSelector){
				var self = this;
				year = $("#myYear").combobox('getValue');
				self.$reportType = $("#reportType").combobox('getValue');
				
				if(null == year || "" == year){
					$.messager.alert("提示","请选择年份！","info");
					return false;
				}
				
				if(null == self.$reportType || "" == self.$reportType){
					$.messager.alert("提示","请选择报表类型！","info");
					return false;
				}
				
				var span = $($("td[field='b']").find("div")[0]).find("span")[0];
				$(span).text( year + "年" + self.$reportType + "得分汇总" );
				
				var unitName = $(unitNameSelector).combotree("getText");
		    	//var unitName = $(unitNameSelector).combobox('getValue');
		    	var personName = $(personNameSelector).textbox('getValue');
		    	var specialty = $("#specialty").combobox('getValue');
		    	var x = null;
		    	var level = null;
		    	if(unitName != null && unitName != ""){
			    	x = $(unitNameSelector).combotree("tree").tree("getSelected").id;
			    	level = $(unitNameSelector).combotree("tree").tree("getSelected").level;
		    	}
		    	//var classes = $("#classes").combobox('getValue');
				var params = {
						'reportType':self.$reportType,
						'year':year,
						'unitName': unitName,
						'personName': personName,
						'specialty':specialty,
						'orgId':x,
						'level':level,
						//'classes':classes
				};
				$('#score_grid').datagrid({url:getHitListenDataUrl, queryParams:params});
			},
			modulu : function() {
				function showAllvalue(value){
					if(value==null){
						return null;
					}else{
						return '<span title='+value+'>'+value+'</span>';
					}
				}
				var self = this;
				self.$grid = $('#score_grid');
				self.$dialog = $('#score_win');
				self.$toolbar = $("#score_bar");
				self.$modulu = $("#modify_panel");
				//var selectedRow = $('#score_grid').datagrid('getSelected');
				//var unit = selectedRow.unit;
				//var month = selectedRow.month;
				var Columns = [[
                    {field:'type',title:'资格人员类型',width:150,align:'center',formatter:showAllvalue}            
				]];
				
				var newComluns = [[
					
					{field:'liveWorking',title:'带电作业工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'lowVoltage',title:'低压配网工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'urgentRepairs',title:'紧急抢修工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'writtenForm',title:'书面形式布置及记录系数',width:150,align:'center',formatter:showAllvalue},
					{field:'stationOne',title:'厂站第一种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'stationTwo',title:'厂站第二种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'stationThree',title:'厂站第三种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'lineOne',title:'线路第一种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'lineTwo',title:'线路第二种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'fireOne',title:'动火第一种工作票系数',width:150,align:'center',formatter:showAllvalue},
					{field:'fireTwo',title:'动火第二种工作票系数',width:150,align:'center',formatter:showAllvalue}
				]]
				$('#modulu_grid').datagrid({
					url: getModulusUrl,
					//queryParams: {month:month,unit:unit,type:paramType,field:paramFiled},
					frozenColumns:Columns,
					columns: newComluns,
					toolbar : $('#modulu_bar'),
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
				
				$('#modulu_bar').on('click','.bda-btn-update',function(){
					var rows = $('#modulu_grid').datagrid('getSelected');
					if(!rows){
						$.messager.alert('提示', "请至少选中一行", 'error');
			    		return false;
					}
					var importButtons=[
						{
						  text:'确认',
						  handler:function(){
							  var type = $('#type').val();
							  var liveWorking = $('#liveWorking').val();
							  var lowVoltage = $('#lowVoltage').val();
							  var urgentRepairs = $('#urgentRepairs').val();
							  var writtenForm = $('#writtenForm').val();
							  var stationOne = $('#stationOne').val();
							  var stationTwo = $('#stationTwo').val();
							  var stationThree = $('#stationThree').val();
							  var lineOne = $('#lineOne').val();
							  var lineTwo = $('#lineTwo').val();
							  var fireOne = $('#fireOne').val();
							  var fireTwo = $('#fireTwo').val();
							  
							  if(liveWorking==""||lowVoltage==""||urgentRepairs==""||writtenForm==""||stationOne==""||stationTwo==""||stationThree==""||lineOne==""||lineTwo==""||fireOne==""||fireTwo==""){
								  $.messager.alert('提示', "不能为空", 'error');
								  return false;
							  }
								  
							  var reg = /^[0-9]+\.?[0-9]*$/;
							  if(!reg.test(liveWorking)||!reg.test(lowVoltage)||!reg.test(urgentRepairs)||!reg.test(writtenForm)||!reg.test(stationOne)||!reg.test(stationTwo)||!reg.test(stationThree)||!reg.test(lineOne)||!reg.test(lineTwo)||!reg.test(fireOne)||!reg.test(fireTwo)){
								  $.messager.alert('提示', "只能输入正整数", 'error');
								  return false;
							  }
							  $.ajax({
									url: updateModulusUrl,
									type: "post",
									data: {
										type:type,
										liveWorking:liveWorking,
										lowVoltage:lowVoltage,
										urgentRepairs:urgentRepairs,
										writtenForm:writtenForm,
										stationOne:stationOne,
										stationTwo:stationTwo,
										stationThree:stationThree,
										lineOne:lineOne,
										lineTwo:lineTwo,
										fireOne:fireOne,
										fireTwo:fireTwo,
									},
									cache: false,
									dataType: "json",
									success: function(data){
										if(data['meta']['success']==true){
											$.messager.alert('提示', "修改成功", 'info');
											$('#modulu_grid').datagrid('reload');
										}
									}
								});
							  self.$modulu.dialog("close");
						  }
						},{
						  text:'取消',
						  handler:function(){self.$modulu.dialog("close");}
					}];
					
					$('#type').val(rows.type);
					$('#liveWorking').val(rows.liveWorking);
					$('#lowVoltage').val(rows.lowVoltage);
					$('#urgentRepairs').val(rows.urgentRepairs);
					$('#writtenForm').val(rows.writtenForm);
					$('#stationOne').val(rows.stationOne);
					$('#stationTwo').val(rows.stationTwo);
					$('#stationThree').val(rows.stationThree);
					$('#lineOne').val(rows.lineOne);
					$('#lineTwo').val(rows.lineTwo);
					$('#fireOne').val(rows.fireOne);
					$('#fireTwo').val(rows.fireTwo);
					
					self.$modulu.show();
					self.$modulu.dialog({
						title:'修改系数',
						width: '500px',
						height: '400px',
						modal:true,
						//href:moduluWebUrl,
						queryParams:{},
						buttons:importButtons,
//						minimizable:true,
						maximizable:true,
						resizable:true,
						onClose:function(){
							$("#modify_form").form("reset");
						}
					}); 
					return false;
				});
			},
			monthReward : function() {
				function showAllvalue(value){
					if(value==null){
						return null;
					}else{
						return '<span title='+value+'>'+value+'</span>';
					}
				}
				var self = this;
				
				self.$reward = $("#month_reward_panel");
				
				//var selectedRow = $('#score_grid').datagrid('getSelected');
				//var unit = selectedRow.unit;
				//var month = selectedRow.month;
				var newComluns = [[
					{field:'monthTrans',title:'输电',width:100,align:'center'},
					{field:'monthOperation',title:'变电运行',width:150,align:'center'},
					{field:'monthTwice',title:'变电二次（含计量、通信）',width:150,align:'center'},
					{field:'monthInspection',title:'变电检修试验（含试研院）',width:150,align:'center'},
					{field:'monthPower',title:'配电（含路灯所）',width:130,align:'center'},
					{field:'levelMoneyMonth',title:'奖励金额',width:100,align:'center'},
				]]
				
				var Columns =[[
					{field:'type',title:'资格人员类型',width:100,align:'center'},
					{field:'monthGrade',title:'奖励等级',width:100,align:'center'},
				]]
				$('#month_reward_grid').datagrid({
					url: getMonthRewardUrl,
					//queryParams: {month:month,unit:unit,type:paramType,field:paramFiled},
					frozenColumns:Columns,
					columns: newComluns,
					toolbar : $('#month_reward_bar'),
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
					onLoadSuccess:function(data){
						var mark =1;
						for(var i=1;i<data.rows.length;i++){
							if (data.rows[i]['type'] == data.rows[i-1]['type']){
								mark += 1;  
								$(this).datagrid('mergeCells',{
									index: i+1-mark,
									field: 'type',
									rowspan:mark
								});
							}else{
								mark=1;
							}
//							if(data.rows[i-1]['type']=='工作许可人'){
//								if(i==14){
//									i+=1;
//								}
//								$(this).datagrid('mergeCells',{
//									index: i-1,
//									field: 'monthOperation',
//									colspan:3
//								});
//							}
						}
					}	
				});
				
				$('#month_reward_bar').on('click','.bda-btn-update',function(){
					var rows = $('#month_reward_grid').datagrid('getSelections');
					if(rows.length==0){
						$.messager.alert('提示', "请至少选中一行", 'error');
			    		return false;
					}
					var importButtons=[
						{
						  text:'确认',
						  handler:function(){
							  var type = $('#mtype').val();
							  var monthGrade = $('#monthGrade').val();
							  var monthTrans = $('#monthTrans').val();
							  var monthOperation = $('#monthOperation').val();
							  var monthTwice = $('#monthTwice').val();
							  var monthInspection = $('#monthInspection').val();
							  var monthPower = $('#monthPower').val();
							  var levelMoney = $('#levelMoneyMonth').val();
							  
							  if(monthTrans==""||monthOperation==""||monthTwice==""||monthInspection==""||monthPower==""||levelMoney==""){
								  $.messager.alert('提示', "不能为空", 'error');
								  return false;
							  }
							  var reg = /^[1-9][0-9]*$/;
							  if(type=='工作许可人'){
								  if(!reg.test(monthPower)||!reg.test(monthTrans)||!reg.test(monthOperation)||!reg.test(levelMoney)){
									  $.messager.alert('提示', "只能输入正整数", 'error');
									  return false;
								  }
							  }else{
								  if(!reg.test(monthPower)||!reg.test(monthTrans)||!reg.test(monthOperation)||!reg.test(monthTwice)||!reg.test(monthInspection)||!reg.test(levelMoney)){
									  $.messager.alert('提示', "只能输入正整数", 'error');
									  return false;
								  }
							  }	  
							  var str = self.verify('month',type,monthGrade,monthTrans,monthOperation,monthTwice,monthInspection,monthPower,levelMoney);
							  if(str=='trans'){
								  $.messager.alert('提示', "输电分数不能小于上一等级或大于下一等级", 'error');
								  return false;
							  }else if(str=='operation'){
								  $.messager.alert('提示', "变电运行分数不能小于上一等级或大于下一等级", 'error');
								  return false;
							  }else if(str=='twice'){
								  $.messager.alert('提示', "变电二次分数不能小于上一等级或大于下一等级", 'error');
								  return false;
							  }else if(str=='inspection'){
								  $.messager.alert('提示', "变电检修分数不能小于上一等级或大于下一等级", 'error');
								  return false;
							  }else if(str=='power'){
								  $.messager.alert('提示', "配电分数不能小于上一等级或大于下一等级", 'error');
								  return false;
							  }else if(str=='money'){
								  $.messager.alert('提示', "奖励金额不能小于上一等级或大于下一等级", 'error');
								  return false;
							  }else{
								  $.ajax({
										url: updateMonthUrl,
										type: "post",
										data: {type:type,monthGrade:monthGrade,monthTrans:monthTrans,monthOperation:monthOperation,monthTwice:monthTwice,monthPower:monthPower,monthInspection:monthInspection,levelMoneyMonth:levelMoney},
										cache: false,
										dataType: "json",
										success: function(data){
											if(data['meta']['success']==true){
												$.messager.alert('提示', "修改成功", 'info');
												$('#month_reward_grid').datagrid('reload');
											}
										}
									});
							  }
							  self.$reward.dialog("close");
						  }
						},{
						  text:'取消',
						  handler:function(){
							  self.$reward.dialog("close");
						  }
					}];
					self.$reward.show();
					if(rows[0].type=='工作许可人'){
						$('#monthTwice').attr("disabled","disabled");
						$('#monthInspection').attr("disabled","disabled");
					}else{
						$('#monthTwice').removeAttr("disabled");
						$('#monthInspection').removeAttr("disabled");
					}
					
					$('#mtype').val(rows[0].type);
					$('#monthGrade').val(rows[0].monthGrade);
					$('#monthTrans').val(rows[0].monthTrans);
					$('#monthOperation').val(rows[0].monthOperation);
					$('#monthTwice').val(rows[0].monthTwice);
					$('#monthInspection').val(rows[0].monthInspection);
					$('#monthPower').val(rows[0].monthPower);
					$('#levelMoneyMonth').val(rows[0].levelMoneyMonth);
					self.$reward.dialog({
						title:'修改月度奖励分数',
						width: '500px',
						height: '400px',
						modal:true,
						//href:moduluWebUrl,
						queryParams:{},
						buttons:importButtons,
//						minimizable:true,
						maximizable:true,
						resizable:true,
						onClose:function(){
							$("#month_reward_form").form("reset");
						}
					}); 
					return false;
				});
			},
			yearReward : function() {
				function showAllvalue(value){
					if(value==null){
						return null;
					}else{
						return '<span title='+value+'>'+value+'</span>';
					}
				}
				var self = this;
				
				self.$reward = $("#year_reward_panel");

				//var selectedRow = $('#score_grid').datagrid('getSelected');
				//var unit = selectedRow.unit;
				//var month = selectedRow.month;
				var newComluns = [[
					{field:'yearTrans',title:'输电',width:100,align:'center'},
					{field:'yearOperation',title:'变电运行',width:100,align:'center'},
					{field:'yearTwice',title:'变电二次（含计量、通信）',width:150,align:'center'},
					{field:'yearInspection',title:'变电检修试验（含试研院）',width:150,align:'center'},
					{field:'yearPower',title:'配电（含路灯所）',width:130,align:'center'},
					{field:'levelMoneyYear',title:'奖励金额',width:100,align:'center'},
				]]
				var Columns =[[
					{field:'type',title:'资格人员类型',width:100,align:'center'},
					{field:'yearGrade',title:'奖励等级',width:100,align:'center'},
				]]
				$('#year_reward_grid').datagrid({
					url: getYearRewardUrl,
					//queryParams: {month:month,unit:unit,type:paramType,field:paramFiled},
					frozenColumns:Columns,
					columns: newComluns,
					toolbar : $('#year_reward_bar'),
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
					onLoadSuccess:function(data){
						var mark =1;
						for(var i=1;i<data.rows.length;i++){
							if (data.rows[i]['type'] == data.rows[i-1]['type']){
								mark += 1;  
								$(this).datagrid('mergeCells',{
									index: i+1-mark,
									field: 'type',
									rowspan:mark
								});
							}else{
								mark=1;
							}
//							if(data.rows[i-1]['type']=='工作许可人'){
//
//								$(this).datagrid('mergeCells',{
//									index: i-1,
//									field: 'yearOperation',
//									colspan:3
//								});
//							}
						}	
					}
				});
				
				$('#year_reward_bar').on('click','.bda-btn-update',function(){
					var rows = $('#year_reward_grid').datagrid('getSelections');
					if(rows.length==0){
						$.messager.alert('提示', "请至少选中一行", 'error');
			    		return false;
					}
					var importButtons=[
						{
						  text:'确认',
						  handler:function(){
							  var type = $('#ytype').val();
							  var yearGrade = $('#yearGrade').val();
							  var yearTrans = $('#yearTrans').val();
							  var yearOperation = $('#yearOperation').val();
							  var yearTwice = $('#yearTwice').val();
							  var yearInspection = $('#yearInspection').val();
							  var yearPower = $('#yearPower').val();
							  var levelMoney = $('#levelMoneyYear').val();
							  if(yearTrans==""||yearOperation==""||yearTwice==""||yearInspection==""||yearPower==""||levelMoney==""){
								  $.messager.alert('提示', "不能为空", 'error');
								  return false;
							  }
							  var reg = /^[1-9][0-9]*$/;
							  if(type=='工作许可人'){
								  if(!reg.test(yearPower)||!reg.test(yearTrans)||!reg.test(yearTwice)||!reg.test(levelMoney)){
									  $.messager.alert('提示', "只能输入正整数", 'error');
									  return false;
								  }
							  }else{
								  if(!reg.test(yearPower)||!reg.test(yearTrans)||!reg.test(yearOperation)||!reg.test(yearTwice)||!reg.test(yearInspection)||!reg.test(levelMoney)){
									  $.messager.alert('提示', "只能输入正整数", 'error');
									  return false;
								  }
							  } 
							  var str = self.verify('year',type,yearGrade,yearTrans,yearOperation,yearTwice,yearInspection,yearPower,levelMoney);
							  if(str=='trans'){
								  $.messager.alert('提示', "输电分数不能大于上一等级或小于下一等级", 'error');
								  return false;
							  }else if(str=='operation'){
								  $.messager.alert('提示', "变电运行分数不能大于上一等级或小于下一等级", 'error');
								  return false;
							  }else if(str=='twice'){
								  $.messager.alert('提示', "变电二次分数不能大于上一等级或小于下一等级", 'error');
								  return false;
							  }else if(str=='inspection'){
								  $.messager.alert('提示', "变电检修分数不能大于上一等级或小于下一等级", 'error');
								  return false;
							  }else if(str=='power'){
								  $.messager.alert('提示', "配电分数不能大于上一等级或小于下一等级", 'error');
								  return false;
							  }else if(str=='money'){
								  $.messager.alert('提示', "奖励金额不能大于上一等级或小于下一等级", 'error');
								  return false;
							  }else{
								  $.ajax({
										url: updateYearUrl,
										type: "post",
										data: {type:type,yearGrade:yearGrade,yearTrans:yearTrans,yearOperation:yearOperation,yearTwice:yearTwice,yearPower:yearPower,yearInspection:yearInspection,levelMoneyYear:levelMoney},
										cache: false,
										dataType: "json",
										success: function(data){
											if(data['meta']['success']==true){
												$.messager.alert('提示', "修改成功", 'info');
												$('#year_reward_grid').datagrid('reload');
											}
										}
									});
							  }
							 self.$reward.dialog("close");
						  }
						},{
						  text:'取消',
						  handler:function(){
							  self.$reward.dialog("close");
						  }
					}];
					self.$reward.show();
					if(rows[0].type=='工作许可人'){
						$('#yearOperation').attr("disabled","disabled");
						$('#yearInspection').attr("disabled","disabled");
					}else{
						$('#yearOperation').removeAttr("disabled");
						$('#yearInspection').removeAttr("disabled");
					}
					$('#ytype').val(rows[0].type);
					$('#yearGrade').val(rows[0].yearGrade);
					$('#yearTrans').val(rows[0].yearTrans);
					$('#yearOperation').val(rows[0].yearOperation);
					$('#yearTwice').val(rows[0].yearTwice);
					$('#yearInspection').val(rows[0].yearInspection);
					$('#yearPower').val(rows[0].yearPower);
					$('#levelMoneyYear').val(rows[0].levelMoneyYear);
					self.$reward.dialog({
						title:'修改年度奖励分数',
						width: '500px',
						height: '400px',
						modal:true,
						//href:moduluWebUrl,
						queryParams:{},
						buttons:importButtons,
//						minimizable:true,
						maximizable:true,
						resizable:true
					}); 
					return false;
				});
			},
			verify:function(source,type,grade,trans,operation,twice,inspection,power,money){
				if(source=='month'){
					var sonMap ={
							type:type,
							monthGrade:grade,
							monthInspection:inspection,
							monthOperation:operation,
							monthPower:power,
							monthTrans:trans,
							monthTwice:twice,
							levelMoneyMonth:money
					};
					var list = [];
		    		$.ajax({
						url: getMonthVerifyUrl,
						type: "post",
						data: {type:type},
						async: false,
						cache: false,
						dataType: "json",
						success: function(data){
							for(var i=0;i<data.length;i++){
								list.push(data[i]);
							}
						}
					});
					var t = 0;
		    		for(var i=0;i<list.length;i++){
		    			if(grade==list[i]['monthGrade']){
		    				t=i;
		    				list.splice(i,1,sonMap);
						}
		    		}
					if(t!=0&&t!=list.length-1){
						if(parseInt(list[t]['monthTrans'])<parseInt(list[t-1]['monthTrans'])||parseInt(list[t]['monthTrans'])>parseInt(list[t+1]['monthTrans'])){
							return 'trans';
						}else if(parseInt(list[t]['monthOperation'])<parseInt(list[t-1]['monthOperation'])||parseInt(list[t]['monthOperation'])>parseInt(list[t+1]['monthOperation'])){
							return 'operation';
						}else if(parseInt(list[t]['monthTwice'])<parseInt(list[t-1]['monthTwice'])||parseInt(list[t]['monthTwice'])>parseInt(list[t+1]['monthTwice'])){
							return 'twice';
						}else if(parseInt(list[t]['monthPower'])<parseInt(list[t-1]['monthPower'])||parseInt(list[t]['monthPower'])>parseInt(list[t+1]['monthPower'])){
							return 'power';
						}else if(parseInt(list[t]['monthInspection'])<parseInt(list[t-1]['monthInspection'])||parseInt(list[t]['monthInspection'])>parseInt(list[t+1]['monthInspection'])){
							return 'inspection';
						}else if(parseInt(list[t]['levelMoneyMonth'])<parseInt(list[t-1]['levelMoneyMonth'])||parseInt(list[t]['levelMoneyMonth'])>parseInt(list[t+1]['levelMoneyMonth'])){
							return 'money';
						}else{
							return 'true';
						}
					}else if(t==0){
						if(parseInt(list[t]['monthTrans'])>parseInt(list[t+1]['monthTrans'])){
							return 'trans';
						}else if(parseInt(list[t]['monthOperation'])>parseInt(list[t+1]['monthOperation'])){
							return 'operation';
						}else if(parseInt(list[t]['monthTwice'])>parseInt(list[t+1]['monthTwice'])){
							return 'twice';
						}else if(parseInt(list[t]['monthPower'])>parseInt(list[t+1]['monthPower'])){
							return 'power';
						}else if(parseInt(list[t]['monthInspection'])>parseInt(list[t+1]['monthInspection'])){
							return 'inspection';
						}else if(parseInt(list[t]['levelMoneyMonth'])>parseInt(list[t+1]['levelMoneyMonth'])){
							return 'money';
						}else{
							return 'true';
						}
					}else if(t==list.length-1){
						if(parseInt(list[t]['monthTrans'])<parseInt(list[t-1]['monthTrans'])){
							return 'trans';
						}else if(parseInt(list[t]['monthOperation'])<parseInt(list[t-1]['monthOperation'])){
							return 'operation';
						}else if(parseInt(list[t]['monthTwice'])<parseInt(list[t-1]['monthTwice'])){
							return 'twice';
						}else if(parseInt(list[t]['monthPower'])<parseInt(list[t-1]['monthPower'])){
							return 'power';
						}else if(parseInt(list[t]['monthInspection'])<parseInt(list[t-1]['monthInspection'])){
							return 'inspection';
						}else if(parseInt(list[t]['levelMoneyMonth'])<parseInt(list[t-1]['levelMoneyMonth'])){
							return 'money';
						}else{
							return 'true';
						}
					}
		    	}else if(source=='year'){
					var sonMap ={
							type:type,
							yearGrade:grade,
							yearInspection:inspection,
							yearOperation:operation,
							yearPower:power,
							yearTrans:trans,
							yearTwice:twice,
							levelMoneyYear:money
					};
					var list = [];
		    		$.ajax({
						url: getYearVerifyUrl,
						type: "post",
						data: {type:type},
						async: false,
						cache: false,
						dataType: "json",
						success: function(data){
							for(var i=0;i<data.length;i++){
								list.push(data[i]);
							}
						}
					});
					var t = 0;
		    		for(var i=0;i<list.length;i++){
		    			if(grade==list[i]['yearGrade']){
		    				t=i;
		    				list.splice(i,1,sonMap);
						}
		    		}
					if(t!=0&&t!=list.length-1){
						if(parseInt(list[t]['yearTrans'])>parseInt(list[t-1]['yearTrans'])||parseInt(list[t]['yearTrans'])<parseInt(list[t+1]['yearTrans'])){
							return 'trans';
						}else if(parseInt(list[t]['yearOperation'])>parseInt(list[t-1]['yearOperation'])||parseInt(list[t]['yearOperation'])<parseInt(list[t+1]['yearOperation'])){
							return 'operation';
						}else if(parseInt(list[t]['yearTwice'])>parseInt(list[t-1]['yearTwice'])||parseInt(list[t]['yearTwice'])<parseInt(list[t+1]['yearTwice'])){
							return 'twice';
						}else if(parseInt(list[t]['yearPower'])>parseInt(list[t-1]['yearPower'])||parseInt(list[t]['yearPower'])<parseInt(list[t+1]['yearPower'])){
							return 'power';
						}else if(parseInt(list[t]['yearInspection'])>parseInt(list[t-1]['yearInspection'])||parseInt(list[t]['yearInspection'])<parseInt(list[t+1]['yearInspection'])){
							return 'inspection';
						}else if(parseInt(list[t]['levelMoneyYear'])>parseInt(list[t-1]['levelMoneyYear'])||parseInt(list[t]['levelMoneyYear'])<parseInt(list[t+1]['levelMoneyYear'])){
							return 'money';
						}else{
							return 'true';
						}
					}else if(t==0){
						if(parseInt(list[t]['yearTrans'])<parseInt(list[t+1]['yearTrans'])){
							return 'trans';
						}else if(parseInt(list[t]['yearOperation'])<parseInt(list[t+1]['yearOperation'])){
							return 'operation';
						}else if(parseInt(list[t]['yearTwice'])<parseInt(list[t+1]['yearTwice'])){
							return 'twice';
						}else if(parseInt(list[t]['yearPower'])<parseInt(list[t+1]['yearPower'])){
							return 'power';
						}else if(parseInt(list[t]['yearInspection'])<parseInt(list[t+1]['yearInspection'])){
							return 'inspection';
						}else if(parseInt(list[t]['levelMoneyYear'])<parseInt(list[t+1]['levelMoneyYear'])){
							return 'money';
						}else{
							return 'true';
						}
					}else if(t==list.length-1){
						if(parseInt(list[t]['yearTrans'])>parseInt(list[t-1]['yearTrans'])){
							return 'trans';
						}else if(parseInt(list[t]['yearOperation'])>parseInt(list[t-1]['yearOperation'])){
							return 'operation';
						}else if(parseInt(list[t]['yearTwice'])>parseInt(list[t-1]['yearTwice'])){
							return 'twice';
						}else if(parseInt(list[t]['yearPower'])>parseInt(list[t-1]['yearPower'])){
							return 'power';
						}else if(parseInt(list[t]['yearInspection'])>parseInt(list[t-1]['yearInspection'])){
							return 'inspection';
						}else if(parseInt(list[t]['levelMoneyYear'])>parseInt(list[t-1]['levelMoneyYear'])){
							return 'money';
						}else{
							return 'true';
						}
					}
		    	}
				
			}
	}
	
	module.exports = new score();
})