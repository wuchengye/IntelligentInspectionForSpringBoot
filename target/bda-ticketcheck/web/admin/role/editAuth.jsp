<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body class="easyui-layout">
		<input type="hidden" name="role" value="${role.roleId}">
		<div  class="easyui-tabs js-tabs" id="authTab" data-options="region:'center',fit:'true',border:'false' ">
			<div title="主菜单权限" id="Menu" style="overflow: auto;" data-options="">
				<table id="menuAuthGrid"></table>
			</div>
<!-- 			<div title="机构权限" id="orgIndex" style="overflow: auto;" data-options=""> -->
<!-- 				<table id="orgAuthGrid"></table>				 -->
<!-- 			</div> -->
		</div>
	<script type="text/javascript">
		$(function() {
			seajs.use([ 'module/admin/role.js' ], function(role) {
				role.editAuth();
			});	
		});
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>