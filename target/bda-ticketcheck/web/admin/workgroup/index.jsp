<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<link type="text/css" rel="stylesheet" href="assets/css/dict/importTemplate.css" />
<body>
	<table id="workgroupGrid"></table>
	<div id="workgroupEditWindow"></div>
	<div id="workgroupGridBar">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="padding-left:2px">
					<a href="#" class="easyui-linkbutton bda-btn-add" iconCls="icon-add" plain="true">新增</a>
					<a href="#" class="easyui-linkbutton bda-btn-edit" iconCls="icon-edit" plain="true">编辑</a>
					<a href="#" class="easyui-linkbutton bda-btn-del" iconCls="icon-remove" plain="true">删除</a>
					<!-- <a href="javascript:void(0);" class="easyui-linkbutton bda-btn-imp" iconCls="icon-save" plain="true">班组信息批量导入/修改</a> -->
				</td>
				<td style="text-align:right;padding-right:2px">
					<input class="js-searchbox" style="width:150px" data-options="prompt:'班组名称'"></input>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/admin/workgroup.js' ], function(workgroup) {
			workgroup.init();
		});		
	});
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>