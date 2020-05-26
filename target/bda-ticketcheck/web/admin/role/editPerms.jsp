
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body>
<div style="height: 100%;">
		<input type="hidden" name="roleId" value="${roleId}">
		<div  class="easyui-tabs js-tabs" id="authTab" data-options="fit:'true',border:'false' ">
			<div title="主菜单权限" id="menuauth" style="overflow: auto;" data-options="">
				<div style="overflow: hidden;height:100%;" >
				    <iframe frameborder="0" src="admin/menu/config?roleId=${roleId}" width="100%" height="100%"></iframe>
				</div>				
			</div>
			<%-- <div title="操作权限" id="sysauth" style="overflow: auto;" data-options="">
				<div style="overflow: hidden;height:100%;" ><iframe frameborder="0" src="admin/permission/editPermission?roleId=${roleId}" width="100%" height="100%"></iframe></div>	
				
			</div> --%>
		</div>
	<script type="text/javascript">
		$(function() {
			seajs.use([ 'module/admin/role.js' ], function(role) {
				role.editPerms();
			});	
		});
	</script>
</div>
</body>
<%@ include file="/web/include/ft.jsp"%>
