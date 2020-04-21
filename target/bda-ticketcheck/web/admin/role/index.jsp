<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body>
<table id="roleGrid"></table>
<div id="editWindow"></div>
<div id="permsWin"></div>
<div id="roleGridBar">
	<table cellpadding="0" cellspacing="0" style="width:100%">
		<tr>
			<td style="padding-left:2px">
				<a href="#" class="easyui-linkbutton bda-btn-add" iconCls="icon-add" plain="true">新增</a>
				<a href="#" class="easyui-linkbutton bda-btn-edit" iconCls="icon-edit" plain="true">编辑</a>
				<a href="#" class="easyui-linkbutton bda-btn-del" iconCls="icon-remove" plain="true">删除</a>
				<a href="#" class="easyui-linkbutton bda-btn-editAuth" iconCls="icon-edit" plain="true">分配页面</a>
				<!-- <a href="#" class="easyui-linkbutton bda-btn-editPermis" iconCls="icon-edit" plain="true">修改权限</a> -->
				<a href="#" class="easyui-linkbutton bda-btn-editUserRold" iconCls="icon-edit" plain="true">分配用户</a>
			</td>
			<td style="text-align:right;padding-right:2px">
				<input id="roleSearch" class="js-searchbox" style="width:150px"></input>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/admin/role.js' ], function(role) {
			role.index();
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