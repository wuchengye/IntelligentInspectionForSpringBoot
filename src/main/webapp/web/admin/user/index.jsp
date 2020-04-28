<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<link type="text/css" rel="stylesheet" href="assets/css/dict/importTemplate.css" />
<body>
	<table id="userGrid"></table>
	<div id="userEditWindow"></div>
	<div id="userGridBar">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				
				<td style="padding-left:2px">
					<shiro:hasPermission name="Opr:edit:user">
						<a href="#" class="easyui-linkbutton bda-btn-add" iconCls="icon-add" plain="true">新增</a>
					</shiro:hasPermission>
					<a href="#" class="easyui-linkbutton bda-btn-edit" iconCls="icon-edit" plain="true">编辑</a>
					<shiro:hasPermission name="Opr:edit:user">
						<a href="#" class="easyui-linkbutton bda-btn-del" iconCls="icon-remove" plain="true">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="Opr:edit:user">
					<a href="#" class="easyui-linkbutton bda-btn-editPas" iconCls="icon-edit" plain="true">重置密码</a>
					</shiro:hasPermission>
					<%-- <shiro:hasPermission name="Opr:edit:user">
					<a href="javascript:void(0);" class="easyui-linkbutton bda-btn-imp" iconCls="icon-save" plain="true">用户信息批量导入/修改</a>
					</shiro:hasPermission> --%>
				</td>
							
				<td style="text-align:right;padding-right:2px">
					<input class="js-searchbox" style="width:150px"></input>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/admin/user.js' ], function(user) {
			user.init();
		});		
	});
	
	function resizeDT() {
        $(".datagrid-f").each(function (i, x) {
            try {
                $("#" + $(x).prop("id")).datagrid("resize");
            } catch (e) {

            }
        })
        return true;
    }
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>