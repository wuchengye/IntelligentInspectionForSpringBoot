<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
 	.dialog-button { 
    	padding: 5px; 
    	text-align: center; 
    } 
</style>
<meta http-equiv=“X-UA-Compatible” content=“chrome=1″/>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		
		<div data-options="region:'north',border:false" style="height: 100%;">
		    <div id="batch_manage_dialog"></div>
		    <div id="chartToolBar" style="border-bottom: 1px #dedede solid">
		        <form action="" method="post" id="myForm">
		            <table class="1formTable"  cellpadding="0" cellspacing="0" 
				       style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
						<tr>
							<td style="padding-left:5px;">
						   日期：<input id="begin_date_batch" name="beginDate" class="easyui-datebox"   style="width:105px;" data-options="prompt:'开始日期',editable:false"/>
							~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期',editable:false"/>
							<span>单位:</span>
						    <input type="text" id="data_query" class="easyui-combotree selectorMy"  autocomplete="off"
						           data-options="panelHeight:'auto',panelMaxHeight:250,panelWidth:280,editable:true,valueField:'value',textField:'text',url:''"/>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-query" iconCls="icon-search" plain="true">查询</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-reset" iconCls="icon-remove" plain="true" >重置</a>
						</td>
						</tr>
		            </table>
		        </form>
		    </div>
		    
		    <div style="width: 100%;height: 81%;margin: 15px auto;">
		        <div style="display:inline-block;width: 47%;height:90%;float: left;margin-left:10px;border: 1px #dedede solid;">
				    <div id="bar_1" style="height: 100%;"></div>
	            </div>
	            <div style="display:inline-block;width: 47%;height:90%;float: left;margin-left:20px;border: 1px #dedede solid;">
				    <div id="bar_2" style="height: 100%;"></div>
	            </div>
		    </div>
		    
			<div style="width: 100%;height: 81%;margin: 15px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:10px;border: 1px #dedede solid; ">
				    <div id="bar_3" style="height: 90%;width: 100%;"></div>
			    </div>
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_4" style="height: 90%;width: 100%;"></div>
			    </div>
			</div>
			<div style="width: 100%;height: 81%;margin: 15px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:10px;border: 1px #dedede solid; ">
				    <div id="bar_5" style="height: 90%;width: 100%;"></div>
			    </div>
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_6" style="height: 90%;width: 100%;"></div>
			    </div>
			</div>
			<div style="clear: both;"></div>
			<div style="width: 100%;height: 81%;margin: 40px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:10px;border: 1px #dedede solid; ">
				    <div id="bar_7" style="height: 90%;width: 100%;"></div>
			    </div>
			</div>
		</div>
		
    </div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/ticketAmountStatistics/ticketNumCharts.js' ], function(t) {
			t.init();
		});		
		
	})
	</script>

</body>
<%@ include file="/web/include/ft.jsp"%>