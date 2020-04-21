<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body>
	<form action="usermanage/user/save"  method="post" id="userForm">		
		<input type="hidden" name="userId" value="${user.userId}" id="userId">
		<table class="formTable" style="text-align:left">
			<tr>
				<td>用户账号：<td>  
				<td><input id="weaccount" name="account" type="text" value="${user.account }" class="f1 easyui-textbox " data-options="required:true,disabled:true" />
				<td>
			</tr>
			<tr>
				<td>用户姓名：<td>
				<td><input name="userName" type="text" value="${user.userName }" class="f1 easyui-textbox" data-options="required:true"/><td>
			</tr>
            <c:if test="${user.userId != null}">
  			<tr>
  				<td>用户状态：<td>
  				<td><input type="text" name="status" id="status" value="${user.status}" data-options="panelHeight:'auto',panelMaxHeight:250,required:true,editable:false"/> </td>
  			</tr>
            </c:if>
			<tr>
				<td>角色：<td>
				<td><input name="roleIds" style="width:320px" id="roleTypeGrid" data-options="required:true"></input><td>
			</tr>
			<%-- <tr>
				<td>用户所属单位：<td>
				<td><input name="orgId" style="width:450px" id="orgId" data-options="required:true" value="${user.orgId}" ></input><td>
			</tr> --%>
			<%-- <tr>
				<td>用户所属科室：<td>
				<td><input name="userRoom" type="text" value="${userDetail.userRoom }" class="f1 easyui-textbox" data-options="required:true"/><td>
			</tr>
			<tr>
				<td>用户所属班组：<td>
				<td><input name="userGroup" type="text" value="${userDetail.userGroup }" class="f1 easyui-textbox" data-options="required:true"/><td>
			</tr> --%>
			
		</table>
	</form>
	<script>
		$(function(){
			seajs.use([ 'module/admin/user.js' ], function(user) {
				//alert("我是用户编辑页面");
				user.edit();
			});			
			seajs.use([ 'module/commonUtil/checkinput.js' ], function(checkinput) {
				checkinput.checkinput();
			});			
		})
		
		/* $("body").on("keydown","input",function(e){
			if(e.keyCode==32){ //处理空格,不让输入
				return false;
			}
		}); */
		
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>