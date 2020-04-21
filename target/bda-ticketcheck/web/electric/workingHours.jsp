<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- 三种人工时统计表 -->
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'center'" style="height: 100%;">
			<table id="hours_grid"></table>
			<div id="hours_win"></div>
			
			<div id="hours_bar">
				<form id="workHoursQueryForm" action="">
					<table cellpadding="0" cellspacing="0" style="width:100%">
						<tr>
							<td style="padding-left:10px;">
								<span>单位:</span>
							    <input type="text" id="data_query" class="selectorMy" style="text-align: left" autocomplete="off"
							           data-options=""/><!-- ,url:'electric/workingHours/getUnitCombobox' -->
								统计时间：
								<input id="begin_date_batch" name="beginDate" class="easyui-datebox"  style="width:105px;" data-options="prompt:'开始日期',editable:false"/>
								~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期',editable:false"/>
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-count" iconCls="icon-sum" plain="true">统计</a>
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-export" iconCls="icon-print" plain="true" >导出</a>
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-reset" iconCls="icon-remove" plain="true" >重置</a>
							</td>
						</tr>
						<tr>
						    <td style="color: red;padding-left: 10px;" >
						                   特殊工作票类型：
						       <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;蓄电池电压测量、主变铁芯及夹件接地电流测量、小母线接地电流测量、全站消防设备维护、全站环境卫生清洁绿化维护、
						       <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;变电站红外线防盗系统维护、变电站空调维护、全站防小动物检查维护、开关柜加热器及柜门密封维护。
						    </td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/workingHours.js?v=20190617' ], function(kwa) {
			kwa.workingHours();
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