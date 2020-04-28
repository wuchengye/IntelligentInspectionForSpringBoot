<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- datamanage/business 业务数据管理-->
<body>
	<div style="text-align:center;margin-top:20px;width: 100%">
		<form id="import_userDetail_form" action="datamanage/userDetail/import" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td align="right" ><span style="text-indent:2em;margin-bottom:10px">请选择文件，仅支持xls和xlsx格式：</span></td>
					<td><input name="file" class="easyui-filebox" style="width:100%;clear:both" data-options="required:true,prompt:'请选择上传的文件',buttonText:' 浏 览 '"></td>
				</tr>
				<tr>
					<td align="right" ><span style="text-indent:2em;margin-bottom:10px">默认角色：</span></td>
					<td><input name="roleIds" class="easyui-combogrid" style="width:100%;clear:both" id="roleTypeGrid" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td align="right" ><span style="text-indent:2em;margin-bottom:10px">导入模板：</span></td>
					<td><a href="upload/dataManage/template/用户信息批量导入或修改模板.xlsx" class="template">用户信息批量导入或修改模板.xlsx</a></td>
				</tr>
			</table>
		</form>
	</div>
	<script>
		/* $(function(){
			seajs.use([ 'module/datamanage/business.js' ], function(business) {
				business.importWord();
			});			
		}) */
		seajs.use([ 'module/admin/user.js' ], function(user) {
			user.importInit();
		});	
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>