<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="typeAllChartsDialog_grid"></table>
			<div id="typeAllChartsDialog_win"></div>
			<div id="typeAllChartsDialog_bar">
			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/ticketAmountStatistics/ticketTypeAllUnitCharts.js' ], function(kwm) {
				kwm.typeAllChartsDialog();
			});			
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>