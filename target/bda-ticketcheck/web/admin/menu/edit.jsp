<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="padding:10px">
	<form id="menu_form" action="admin/qmauth/save" method="post" >
    	<input type="hidden" name="menuId" value="${menu.menuId}">
            <table>
                <tr>
                    <td>菜单:</td>
                    <td><input name="menuName" class="f1 easyui-textbox" value="${menu.menuName}" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>父菜单:</td>
                    <td><input name="parentId" type="text" value="${menu==null ? parentId:menu.parentId}" /></td>
                </tr>
                <tr>
                    <td>URL:</td>
                    <td><input name="menuUrl" class="f1 easyui-textbox" value="${menu.menuUrl}"></input></td>
                </tr>
                <tr>
                    <td>菜单类型:</td>
                    <td>
                    	<select class="easyui-combobox" name="type" data-options="required:true,panelHeight:'auto',editable:false">
						    <option value="1" <c:if test="${menu.type == 1 }">selected</c:if>  >菜单目录</option>
						    <option value="0" <c:if test="${menu.type == 0 }">selected</c:if>  >页面</option>
						</select>
					</td>
                </tr>
                <tr>
                    <td>排序</td>
                    <td>
                    	 <input name="menuOrder" value="${menu.menuOrder}" class="easyui-numberspinner" style="width:80px;"  data-options="min:0" />
					</td>
                </tr>
            </table>
        </form>
    <script type="text/javascript">
	$(function() {
		seajs.use([ 'module/admin/menu.js' ], function(menu) {
			menu.edit();
		});		
		$("body").on("keydown","input",function(e){
			if(e.keyCode==32){ //处理空格,不让输入
				return false;
			}
			
		});
	});
</script>
</body>
<%@ include file="/web/include/ft.jsp"%>