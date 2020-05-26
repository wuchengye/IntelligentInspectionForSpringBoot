<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<link type="text/css" rel="stylesheet" href="assets/css/dict/importTemplate.css" />
<!-- risk/usedtaboo 使用服务禁语-->
<style type="text/css">
.layout-split-west {
	border-right: 1px solid #3a82bd;
}
td	{
	white-space: nowrap;
}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
	    <div data-options="region:'center',title:'数据管理',border:false" style="height: 100%; width: 100%;">
	    	<table id="business_grid"></table>
			<div id="business_edit_window"></div>
			<div id="business_grid_bar">
			<form  method="POST" id="business_form">
			<table class="formTable" cellpadding="0" cellspacing="0" style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
				<tr>
					<td style="text-align: center;" colspan="6">
						<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-exp" iconCls="icon-save" plain="true" style = "float:left">导出</a>                              
					</td>
				</tr>
			</table>
			</form>
			</div>
	    </div>
	</div>
<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/risk/hotlineindata.js' ], function(hotlineindata) {
			hotlineindata.init();
		});		
		seajs.use([ 'module/commonUtil/initdate.js' ], function(initDate) {
			initDate.initDate();
		});
	});
</script>
</body>
<%@ include file="/web/include/ft.jsp"%>