<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- 批次管理弹框 -->
<body style="text-align: center;">

    <div data-options="region:'center'" style="height: 100%;">
		<div id="batch_manage_dialog_bar">
		    <a href="javascript:void(0);" class="easyui-linkbutton bda-btn-deleteBatch" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<table id="batch_manage_grid"></table>
	</div>
	
	<script>
		$(function(){
			seajs.use([ 'module/electric/ticketCheckResult.js' ], function(t) {
				t.batchManage();
			});			
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>