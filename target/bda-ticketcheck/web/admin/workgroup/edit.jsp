<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body>
	<form action="admin/workgroup/save"  method="post" id="groupForm">		
		<input type="hidden" name="groupId" value="${group.groupId}" id="groupId">
		<input type="hidden" name="tmpOrgId" value="${group.orgId}" id="tmpOrgId">
		<table class="formTable" style="text-align:left">
			
			<tr>
				<td>班组名称：</td>
				<td><input name="groupName" type="text" style="width:200px;" value="${group.groupName}" class="f1 easyui-textbox" data-options="required:true"/></td>
			</tr>
			<c:if test="${orgLev == 1 }">
			<tr>
				<td>所属区域：</td>
				<td><select name="orgId"  style="width:200px;" id="orgId" /></td>
			</tr>
			</c:if>
			<tr>
				<td>班组状态：</td>
				<td align="left">
                    <label><input type="radio" name="status" value="1" checked/>有效</label>
                    <label><input type="radio" name="status" value="0" <c:if test="${group.status == 0}">checked</c:if>/>无效</label>
                </td>
			</tr>
			
		</table>
	</form>
	<script>
		$(function(){
			seajs.use([ 'module/admin/workgroup.js' ], function(workgroup) {
				workgroup.edit();
			});		
		})
		
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>