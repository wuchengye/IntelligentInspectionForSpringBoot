<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body>
	<form action="usermanage/user/editPas"  method="post" id="userPasForm">		
		<input type="hidden" name="id" value="${user.userId}">
		<table class=formTable>
			<tr>
				<td>旧密码：</td>
				<td><input id="oldPas" name="oldPas" class="easyui-textbox" type="password" ></td>
			</tr>
			<tr>
				<td>新密码：</td>
				<td><input id="firstPas" type="password" class="easyui-textbox" ></td><!-- data-options="validType:['unsignPsw']" -->
			</tr>
			<tr>
				<td>重复新密码：</td>
				<td><input id="newPas" name="newPas" type="password" class="easyui-textbox" ></td><!-- data-options="validType:['unsignPsw']" -->
			</tr>
		</table>
	</form>
	<script>
		$(function(){
			seajs.use([ 'module/admin/user.js' ], function(user) {
				//alert("我是用户编辑页面");
				user.editPas();
			});
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>