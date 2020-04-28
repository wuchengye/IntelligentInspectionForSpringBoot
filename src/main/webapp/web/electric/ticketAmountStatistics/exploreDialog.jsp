<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	<!-- <form  method="post" id="exploreForm"> -->
		<!--  
		<div style="margin-bottom:20px">
			<form id="upload_statement_form" action="keywords/statement/ImportFile" method="post" enctype="multipart/form-data">
	       		<div>
	       		<span style="float:left;width:30px">日期:</span><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:81px;" data-options="prompt:'开始日期'"/>~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:81px;" data-options="prompt:'结束日期'"/>
				</div>
				<div style="margin-top:10px">
				<span style="float:left;width:30px">单位:</span><input type="text" id="data_query" class="selectorMy" style="width:170px;"/>
	        	</div>
	        </form>
	    </div>
	    <div>
	        <a href="javascript:void(0)" class="easyui-linkbutton import-btn" style="width:200px;">查询</a>
	    </div>
	    -->
	    <input type="hidden" id="beginTimeDio" name="beginTime" value="${beginTime }">
		<input type="hidden" id="endTimeDio" name="endTime" value="${endTime }">
		<input type="hidden" id="unitDio" name="unit" value="${unit }">
		<input type="hidden" id="urlDio" name="url" value="${url }">
		<input type="hidden" id="seriesIndex" name="seriesIndex" value="${seriesIndex }">
		<input type="hidden" id="qcCenter" name="qcCenter" value="${qcCenter }">
		<input type="hidden" id="group" name="group" value="${group }">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="dialog_grid"></table>
			<div id="dialog_win"></div>
			<div id="dialog_bar">
			</div>
		</div>
		<!-- </form> -->
	<script>
		$(function(){
			seajs.use([ 'module/electric/ticketAmountStatistics/ticketNumChartsDialog.js?v=20190531' ], function(kwm) {
				kwm.dialog();
			});			
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>