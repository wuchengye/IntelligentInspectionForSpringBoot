<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body>
	<form action="datamanage/userDetail/save"  method="post" id="staffForm">		
		<input type="hidden" name="tmpGroup" value="${staff.staffGroup}" id="tmpGroup">
		<input type="hidden" name="tmpOrgId" value="${staff.orgId}" id="tmpOrgId">
		<input type="hidden" name="pkid" value="${staff.pkid}" id="pkid">
		<table class="formTable" style="text-align:left">
			<tr>
				<td>员工工号：</td>
				<td><input id="staffId" style="width:200px;" name="staffId" type="text" value="${staff.staffId}" class="f1 easyui-textbox" data-options="required:true"/></td>
			</tr>
			<tr>
				<td>员工姓名：</td>
				<td><input name="staffName" style="width:200px;" type="text" value="${staff.staffName}" class="f1 easyui-textbox" data-options="required:true"/></td>
			</tr>
			<tr>
				<td>员工所属科室：</td>
				<td><input name="staffRoom" style="width:200px;" type="text" value="${staff.staffRoom}" class="f1 easyui-textbox" /></td>
			</tr>
			<tr>
				<td>员工所属班组：</td>
				<td><input name="staffGroup" style="width:200px;" type="text" id="staffGroup" value="${staff.staffGroup}" class="f1 easyui-textbox" /></td>
			</tr>
			<tr>
				<td>所属区域：</td>
				<td><select name="orgId"  style="width:200px;" class="easyui-combobox" id="orgId" 
					data-options="required:true,panelHeight:'auto',panelMaxHeight:250,editable:false,valueField:'value',textField:'text',
					url:'datamanage/org/getAreaCombo',queryParams:{isall:'0'}"  /></td>
			</tr>
            <tr <c:if test="${dataType=='Online'}">hidden</c:if>>
                <td>服务类型：</td>
                <td>
                    <select name="serviceType"  style="width:200px;" class="easyui-combobox" id="serviceType" <c:if test="${dataType=='Online'}">disabled</c:if>
                            data-options="required:true,panelHeight:'auto',panelMaxHeight:250,editable:false,valueField:'value',textField:'text'" >
                         <option selected="selected" disabled="disabled" value="" style="display: none;"></option>
                         <option value="在线服务" <c:if test="${staff.serviceType=='在线服务'}">selected</c:if>>在线服务</option>
                         <option value="呼入服务" <c:if test="${staff.serviceType=='呼入服务'}">selected</c:if>>呼入服务</option>
                         <option value="呼出营销" <c:if test="${staff.serviceType=='呼出营销'}">selected</c:if>>呼出营销</option>
                         <option value="投诉处理" <c:if test="${staff.serviceType=='投诉处理'}">selected</c:if>>投诉处理</option>
                         <option value="服务质检" <c:if test="${staff.serviceType=='服务质检'}">selected</c:if>>服务质检</option>
                         <option value="其他" <c:if test="${staff.serviceType=='其他'}">selected</c:if>>其他</option>
                    </select>
                </td>
            </tr>
            <tr <c:if test="${dataType=='Online'}">hidden</c:if>>
                <td>人员类型：</td>
                <td>
                    <select name="personType"  style="width:200px;" class="easyui-combobox" id="personType" <c:if test="${dataType=='Online'}">disabled</c:if>
                            data-options="required:true,panelHeight:'auto',panelMaxHeight:250,editable:false,valueField:'value',textField:'text'" >
                         <option selected="selected" disabled="disabled" value="" style="display: none;"></option>
                         <option value="自营" <c:if test="${staff.personType=='自营'}">selected</c:if>>自营</option>
                         <option value="众包" <c:if test="${staff.personType=='众包'}">selected</c:if>>众包</option>
                         <option value="地市公司" <c:if test="${staff.personType=='地市公司'}">selected</c:if>>地市公司</option>
                         <option value="合作商" <c:if test="${staff.personType=='合作商'}">selected</c:if>>合作商</option>
                         <option value="其他" <c:if test="${staff.personType=='其他'}">selected</c:if>>其他</option>
                    </select>
              </td>
            </tr>
            <tr <c:if test="${dataType=='Online'}">hidden</c:if>>
                <td>合作商名称：</td>
                <td>
                    <input name="cooperatorName" id="cooperatorName" style="width:200px;" type="text" value="${staff.cooperatorName}" class="f1 easyui-textbox" <c:if test="${dataType=='Online'}">disabled</c:if>
                           data-options="required:true,prompt:'不加后缀,不使用点动(XX中心)形式'"/>
                </td>
            </tr>
			<%-- <tr>
				<td>所属年月：</td>
				<td>
					<input id="yearmonth" name="yearmonth" style="width:200px;" type="text" value="${staff.yearmonth}" class="f1 easyui-textbox" data-options="required:true,validType:'length[6,6]'"/>
				</td>
			</tr> --%>
		</table>
	</form>
	<script>
		$(function(){
			seajs.use([ 'module/admin/staff.js' ], function(staff) {
				staff.edit();
				
			});	 
		})
	</script>
</body>
<%@ include file="/web/include/ft.jsp"%>