<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
 	.rightText{
 	    text-align: right;
 	}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		
		<div data-options="region:'north',border:false" style="height: 100%;">
		    <div id="chartToolBar" style="border-bottom: 1px #dedede solid;" hidden>
		        <form action="" method="post" id="myForm">
		            <table class="formTable"  cellpadding="0" cellspacing="0" 
				       style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
				        <tr>
				            <td>校验批次：</td>
				            <td class="leftItem">
				                <select class="easyui-combobox" id="batch" name="batch" style="width:150px"
				                       data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,required:true,
				                       valueField:'value',textField:'text',url:'electric/ticketCheckRes/getAllBatch',validType:'comboxRequired'"></select>
				            </td>
				            <td>工作票种类：</td>
				            <td class="leftItem">
				                <input id="ticketType" name="ticketType" style="width:150px;">
						        <!-- <input class="easyui-combobox" id="ticketType" name="ticketType" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getTicketType'"> -->
						    </td>
			                <td>单位与班组：</td>
						    <td class="leftItem">
						        <input name="department" style="width: 150px" id="department">
						        <!-- <input class="easyui-combobox" id="department" name="department" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,
						                         valueField:'value',textField:'text',url:'electric/ticketCheckRes/getClassCombobox'"> -->
						    </td>
					    </tr>
					    <tr>
						    <td>工作负责人：</td>
						    <td class="leftItem">
						    	<input class="easyui-textbox" id="workPrincipal" name="workPrincipal" style="width:150px;">
						    <!-- 
						        <input class="easyui-combobox" id="workPrincipal" name="workPrincipal" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getPersonName',queryParams:{type:'负责人'}">
						    -->
						    </td>
			                <td>工作票签发人：</td>
						    <td class="leftItem">
						    	<input class="easyui-textbox" id="ticketSigner" name="ticketSigner" style="width:150px;">
						    <!--  
						        <input class="easyui-combobox" id="ticketSigner" name="ticketSigner" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getPersonName',queryParams:{type:'签发人'}">
						    -->
						    </td>
						    <td>工作许可人：</td>
						    <td class="leftItem">
						    	<input class="easyui-textbox" id="workLicensor" name="workLicensor" style="width:150px;">
						    <!-- 
						        <input class="easyui-combobox" id="workLicensor" name="workLicensor" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getPersonName',queryParams:{type:'许可人'}">
						    -->
						    </td>
					    </tr>
					    <tr>
					        <td>工作票终结时间：</td>
						    <td class="leftItem">
						        <input id="startTime" name="ticketEndTime1" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
						    </td>
						    <td>至：</td>
						    <td class="leftItem">
					        	<input id="endTime" name="ticketEndTime2" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    	</td>
					    </tr>
					    <tr>
					        <td colspan="6" style="text-align: center;">
					            <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-query" iconCls="icon-search" plain="true" >查询</a>
					            <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-reset" iconCls="icon-remove" plain="true" >重置</a>
					        </td>
					    </tr>
		            </table>
		        </form>
		    </div>
		    
		    <div style="width: 100%;height: 45%;margin: 15px auto;">
		        <div style="display:inline-block;width: 55%;height:90%;float: left;margin-left:10px;border: 1px #dedede solid;">
				    <div id="pie_1" style="height: 100%;"></div>
	            </div>
	            <div style="display:inline-block;width: 40%;height:90%;float: left;margin-left:20px;border: 1px #dedede solid;">
				    <div id="pie_2" style="height: 100%;"></div>
	            </div>
		    </div>
		    
		    <div style="width: 100%;height: 71%;margin: 15px auto;">
			    <div style="height: 100%;width:55% ;display: inline-block;margin-left:10px;border: 1px #dedede solid; ">
				    <div id="bar_2" style="height: 90%;width: 100%;"></div>
					<div style="clear: both;"></div>
					<div style="width: 100%;height: 10%;margin: 0 auto;text-align: center;display: inline-block;float: left;">
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #F2707A;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">无需求</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #00B050;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">未完成</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #3078CA;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">已完成</span>
					</div>
			    </div>
			    <div style="height: 100%;width:40% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_3" style="height: 90%;width: 100%;"></div>
			    	<div style="clear: both;"></div>
					<div style="width: 100%;height: 10%;margin: 0 auto;text-align: center;display: inline-block;float: left;">
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #F2707A;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">无需求</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #00B050;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">未完成</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #FFBA56;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">已完成</span>
					</div>
			    </div>
			</div>
			<!-- <div style="clear: both;"></div>
			<div style="width: 100%;height: 71%;margin: 40px auto;">
			    <div style="height: 100%;width:55% ;display: inline-block;margin-left:10px;border: 1px #dedede solid; ">
				    <div id="bar_4" style="height: 90%;width: 100%;"></div>
					<div style="clear: both;"></div>
					<div style="width: 100%;height: 10%;margin: 0 auto;text-align: center;display: inline-block;float: left;">
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #F2707A;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">无需求</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #00B050;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">未完成</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #3078CA;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">已完成</span>
					</div>
			    </div>
			    <div style="height: 100%;width:40% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_5" style="height: 90%;width: 100%;"></div>
			    	<div style="clear: both;"></div>
					<div style="width: 100%;height: 10%;margin: 0 auto;text-align: center;display: inline-block;float: left;">
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #F2707A;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">无需求</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #00B050;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">未完成</span>
					    <div style="display: inline-block;border: 0px;height: 12px;width: 20px;background-color: #FFBA56;margin-left: 10px;border-radius:3px;"></div>
					    <span style="font-size: 12px;font-family: 微软雅黑;">已完成</span>
					</div>
			    </div>
			</div> -->
		</div>
		
    </div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/ticketCheckResult.js?v=20190701' ], function(t) {
			t.getChartData();
		});		
		
		seajs.use([ 'module/commonUtil/initdate.js' ], function(initDate) {
			initDate.initDate();
		});
		
		$("#ticketType").combobox({
			valueField:'value',
			textField:'text',
			panelHeight:'auto',
			panelMaxHeight:250,
			editable:true,
			data:[
			      {value:'',text:'--请选择--',selected:true},
			      {value:'线路第一种工作票',text:'线路第一种工作票'},{value:'线路第二种工作票',text:'线路第二种工作票'},
			      {value:'厂站第一种工作票',text:'厂站第一种工作票'},{value:'厂站第二种工作票',text:'厂站第二种工作票'},
			      {value:'厂站第三种工作票',text:'厂站第三种工作票'},
			      {value:'一级动火工作票',text:'一级动火工作票'},{value:'二级动火工作票',text:'二级动火工作票'},
			      {value:'二次措施单',text:'二次措施单'},
			      {value:'紧急抢修工作票',text:'紧急抢修工作票'},
			      {value:'带电作业工作票',text:'带电作业工作票'},
			      {value:'低压配电网工作票',text:'低压配电网工作票'},
			      {value:'书面布置和记录',text:'书面布置和记录'},
			      {value:'现场勘察记录',text:'现场勘察记录'},
			      {value:'安全技术交底单',text:'安全技术交底单'}
			]
		})
		
		$.extend($.fn.validatebox.defaults.rules, {
		    comboxRequired: {
		        validator: function(value){
		            return value!= "--请选择--" && value != "";
		        },
		        message: '该项为必填项'
		    }
		});
		
	})
	
	function resizeEs(){
		var myChart1 = echarts.init(document.getElementById('pie_1'));
		var myChart2 = echarts.init(document.getElementById('pie_2'));
		var myChart3 = echarts.init(document.getElementById('bar_2'));
		var myChart4 = echarts.init(document.getElementById('bar_3'));
		var myChart5 = echarts.init(document.getElementById('bar_4'));
		var myChart6 = echarts.init(document.getElementById('bar_5'));
		myChart1.resize();
		myChart2.resize();
		myChart3.resize();
		myChart4.resize();
		myChart5.resize();
		myChart6.resize();
	}
		
	</script>

</body>
<%@ include file="/web/include/ft.jsp"%>