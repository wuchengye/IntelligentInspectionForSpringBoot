<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body >
	<div class="easyui-layout" fit="true" >
		<input type="hidden" name="roleId" value="${roleId}">
		<div data-options="region:'center',border:'false'">
			<table id="permissionGrid"></table>
		</div>
		<div data-options="region:'south',height:'50'" >
			<div style="text-align: right;padding: 10px;">
				<a class="easyui-linkbutton bda-btn-save" href="javascript:void(0)" style="width:80px">保存</a>
				<a class="easyui-linkbutton bda-btn-cancel" href="javascript:void(0)" style="width:80px">取消</a>
			</div>
		</div>
	
	</div>
	<script type="text/javascript">
		$(function() {
			seajs.use([ 'module/admin/permission.js' ], function(permission) {
				permission.init();
			});		
		});
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>