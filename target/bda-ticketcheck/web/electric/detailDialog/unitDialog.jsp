<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="unitDialog_grid"></table>
			<div id="unitDialog_win"></div>
			<div id="unitDialog_bar">
			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/statistics.js?v=20190531' ], function(kwm) {
				kwm.unitDialog();
			});			
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>