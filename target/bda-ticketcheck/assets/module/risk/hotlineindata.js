/**
 * 数据管理
 */

define(function(require, exports, module) {

	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js"); //扩展方法
	var utils = require("module/base/utils.js"); // 工具
	var showdetail = require("module/commonUtil/showdetail.js");
	var dialogUrl = "risk/dataManage/getDataManage";
	var getLatestDateUrl = "monitor/hotlineinconversationreview/getLatestDate";
	//连接配置
	var width='500px';
	var height='580px';

	var HotlineData =function(){
		this.$grid;
		this.$dialog;
		this.$form;
		this.$toolbar;
		
		this.$taboo;
		this.$ismute;
	}
	HotlineData.prototype = {
		constructor: HotlineData,
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
				{field: 'dataDate',title: '日期',width: 100,align: 'center'},
			    {field: 'recordCompression',title: '录音压缩量',width: 150,align: 'center'},
			    {field: 'recordVolume',title: '录音量',width: 150,align: 'center'},
			    {field: 'qualityStartTime',title: '质检开始时间',width: 150,align: 'center'},
			]];
			
			self.$grid.datagrid({
				url:dialogUrl,
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
                //queryParams: self.getFormData(),
                onLoadSuccess : function(data) {
                	showdetail.showDetail();
                	return false;
                }
			});
			self.initEleEvent();
		},
		initEleEvent:function(){
			var self = this;
			
			/**
			 * 点击导出非可疑结果事件
			 */
			self.$toolbar.on("click",".bda-btn-exp",function(){
				parent.$.messager.progress({
					title : '提示',
					text : '导出中，请稍后....'
				});
				
				$.ajax({
					url:"risk/dataManage/exportDataManage",
					type:"post",
					//data:self.getFormData(),
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
	}
	module.exports = new HotlineData();
})