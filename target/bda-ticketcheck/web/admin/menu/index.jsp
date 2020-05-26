<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body>
	<table id="menu_grid"></table>
	<div id="win"></div>
	<shiro:hasPermission name="Menu:edit:${menuId }">
		<div id="toolbarsFlag"></div>	<!-- 在org.js判断是否显示操作按钮的标记 -->
	</shiro:hasPermission>
<script type="text/javascript">
	$(function(){
		seajs.use(['module/admin/menu.js' ], function(menu) {
			menu.index();
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