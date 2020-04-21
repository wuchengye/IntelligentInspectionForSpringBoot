<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="typeChartsDialog_grid"></table>
			<div id="typeChartsDialog_win"></div>
			<div id="typeChartsDialog_bar">
			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/ticketAmountStatistics/ticketTypeCharts.js' ], function(kwm) {
				kwm.typeChartsDialog();
			});			
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>