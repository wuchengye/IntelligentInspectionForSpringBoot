<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- 两票校验 -->
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
	    
		<div data-options="region:'center'" style="height: 100%;width:100%">
		    <div id="checked_dialog" style="display: none;">
		        <table id="checkResult">
		        </table>
		    </div>
			<table id="check_grid"></table>
			<div id="check_bar">
			    <form action="POST" id="conditionForm">
				<table class="formTable" cellpadding="0" cellspacing="0" 
				       style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
					<tr>
					    <td>工作票票号:</td>
					    <td class="leftItem">
					        <input name="ticketNo" class="easyui-textbox" style="width:150px">
					    </td>
					    <td>工作票类型:</td>
					    <td class="leftItem">
					        <input name="ticketType" style="width: 150px">
					    </td>
					    <td>站名/线路:</td>
					    <td class="leftItem">
					        <input class="easyui-textbox" name="stationLineName" style="width: 150px">
					    </td>
					    <td>工作负责人:</td>
					    <td class="leftItem">
					        <input class="easyui-textbox" name="workPrincipal" style="width: 150px">
					    </td>
					</tr>
					<tr>
					    <td>工作票签发人:</td>
					    <td class="leftItem">
					        <input class="easyui-textbox" name="ticketSigner" style="width: 150px">
					    </td>
					    <td>工作许可人:</td>
					    <td class="leftItem">
					        <input class="easyui-textbox" name="workLicensor" style="width: 150px">
					    </td>
					    <td>工作终结许可人:</td>
					    <td class="leftItem">
					        <input class="easyui-textbox" name="workEndLicensor" style="width: 150px">
					    </td>
					    <td>单位和班组:</td>
					    <td class="leftItem">
					        <input name="department" style="width: 150px" id="department">
					        <!-- <input class="easyui-combobox" name="department" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,
						                         valueField:'value',textField:'text',url:'electric/ticketCheckRes/getClassCombobox'"> -->
					    </td>
					</tr>
					<tr>
					    <!-- <td>计划开始时间:</td>
					    <td class="leftItem">
					        <input id="startTime" name="planStartTime" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    </td>
					    <td>计划结束时间:</td>
					    <td class="leftItem">
					        <input id="endTime" name="planEndTime" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    </td> -->
					    <td>工作许可时间:</td>
					    <td class="leftItem">
					        <input id="startTime" name="permissionTime1" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    </td>
					    <td>至:</td>
					    <td class="leftItem">
					        <input id="endTime" name="permissionTime2" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    </td>
					    <td>外来单位:</td>
					    <td class="leftItem">
					        <select class="easyui-combobox" name="isOuterDept" style="width: 150px" data-options="panelHeight:80,editable:false">
					            <option value="" selected="selected">-</option>
					            <option value="1">是</option>
					            <option value="2">否</option>
					        <</select>
					    </td>
					    <td>工作票会签人:</td>
					    <td class="leftItem">
					        <input class="easyui-textbox" name="ticketCounterSigner" style="width: 150px">
					    </td>
					</tr>
					<tr>
					    <td>工作终结时间:</td>
					    <td class="leftItem">
					        <input id="startTime" name="ticketEndTime1" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    </td>
					    <td>至:</td>
					    <td class="leftItem">
					        <input id="endTime" name="ticketEndTime2" class="easyui-datetimebox" style="width:150px" data-options="editable:false">
					    </td>
					</tr>
					<tr>
					    <td style="text-align: center;" colspan="2">
					    </td>
					    <td style="text-align: center;" colspan="4">
					        <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-query" iconCls="icon-search" plain="true" >查询</a>
					        <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-reset" iconCls="icon-remove" plain="true" >重置</a>
					        <input type="radio" style="display: none;" name="conditionType" value="0" checked><span style="display: none;">按ID校验</span>
					        <input type="radio" style="display: none;" name="conditionType" value="1"><span style="display: none;">按条件校验</span>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-check" iconCls="icon-ok" plain="true" title="点击开始校验" >校验</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-check-all" iconCls="icon-sum" plain="true" title="点击开始校验" >全部校验</a>
					    </td>
					    <td></td>
					    <td></td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/ticketCheck.js?v=20190628' ], function(ticketCheck) {
			ticketCheck.init();
		});	
		
		seajs.use([ 'module/commonUtil/initdate.js' ], function(initDate) {
			initDate.initDate();
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