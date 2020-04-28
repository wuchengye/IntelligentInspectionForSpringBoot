<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body>
	<form action="rolemanage/role/save"  method="post" id="authRoleForm" >		
		<input type="hidden" name="roleId" value="${role.roleId}">
		<table style="padding: 10px;" >
			<tr>
				<td>角色名称:<td>
				<td><input name="roleName" type="text" value="${role.roleName }" class="f1 easyui-textbox" data-options="required:true"/><td>
			</tr>
			<tr>
				<td>角色描述:<td>
				<td><input name="description" type="text" value="${role.description }" class="f1 easyui-textbox" data-options="multiline:true" style="height:60px;width:300px;"/><td>
			</tr>
		</table>
	</form>
	<script>
		seajs.use([ 'module/admin/role.js' ], function(role) {
			//alert("我是角色编辑页面");
			role.editRole();
		});
		$("body").on("keydown","input",function(e){
			if(e.keyCode==32){ //处理空格,不让输入
				return false;
			}
			
		});
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>