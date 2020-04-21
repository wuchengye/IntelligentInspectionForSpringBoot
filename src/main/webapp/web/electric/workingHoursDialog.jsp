<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
	        <input type="hidden" id="unitName_d" value="${unitName}">
	        <input type="hidden" id="personName_d" value="${personName}">
	        <input type="hidden" id="ticketType_d" value="${ticketType}">
	        <input type="hidden" id="startTime_d" value="${startTime}">
	        <input type="hidden" id="endTime_d" value="${endTime}">
			<table id="dialog_grid"></table>
			<div id="dialog_win"></div>
			<div id="dialog_bar">
			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/workingHours.js?v=20190617' ], function(kwm) {
				kwm.dialog();
			});			
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>