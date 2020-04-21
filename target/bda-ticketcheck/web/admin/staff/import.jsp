<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- datamanage/business 业务数据管理-->
<body>
	<div style="text-align:center;margin-top:20px;width: 100%">
		<form id="import_staff_form" action="" method="post" enctype="multipart/form-data">
			<span style="float:left;text-indent:2em;margin-bottom:10px">请选择文件，仅支持xls和xlsx格式:</span>
			<input name="file" class="easyui-filebox" style="width:80%;clear:both" data-options="required:true,prompt:'请选择上传的文件',buttonText:' 浏 览 '">
		</form>
        <c:if test="${dataType=='Online'}">
    		<div style="text-indent:2em;float:left;margin-top:10px">导入模板：<a href="upload/admin/template/员工信息导入模板.xlsx" class="template">员工信息导入模板.xlsx</a></div>
        </c:if>
        <c:if test="${dataType!='Online'}">
            <div style="text-indent:2em;float:left;margin-top:10px">导入模板：<a href="upload/admin/template/热线员工信息导入模板.xlsx" class="template">热线员工信息导入模板.xlsx</a></div>
        </c:if>
		<div style="text-indent:2em;float:left;margin-top:10px;color:red;">可同时导入员工信息以及班组信息</div>
	</div>
	<script>
		/* $(function(){
			seajs.use([ 'module/datamanage/business.js' ], function(business) {
				business.importWord();
			});			
		}) 
		seajs.use([ 'module/admin/user.js' ], function(user) {
			user.importInit();
		});	*/
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>