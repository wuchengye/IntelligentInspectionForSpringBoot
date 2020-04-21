<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body class="easyui-layout">
	<input type="hidden" name="role" value="${role.roleId}">
	<table id="userRoleGrid"></table>
	<div id="userRoleGridBar">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="padding-left:2px">
					<label>用户：</label>
					<select class="easyui-combobox" name="filter" style="width:100px;" data-options="panelHeight:'auto'">
						 <option value="all">所有用户</option>
						 <option value="assigned">已分配用户</option>
						 <option value="unassigned">未分配用户</option>
					</select>
					<input id="userRoleSearch" class="js-searchbox" style="width:150px;"></input>
				</td>
			</tr>
		</table>
	</div>
	<script>
		$(function() {
			seajs.use([ 'module/admin/role.js' ], function(role) {
				role.editUserRole();
			});	
		});
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>