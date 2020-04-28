<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- 三种人持票情况统计表 -->
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
 	.right{
 	    text-align: right;
 	    width: 200px;
 	}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'center',border:false" style="height: 100%; width: 100%;">
			<table id="news_grid"></table>
			<div id="news_win"></div>
			<div id="news_bar" >
			    <form id="tpsForm">
				<table cellpadding="0" cellspacing="0" style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;margin: 0 auto;">
					<tr>
						<td style="text-align: center;">
						   日期：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期',editable:false" required="required"/>
							~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期',editable:false" required="required"/>
							<span style="margin-left: 10px;">单位：</span>
						    <input id="unitName" class="selectorMy" style="text-align: left" 
						           data-options=""/><!-- url:'electric/workpriscore/getUnitCombobox' -->
						    <span style="margin-left: 10px;">专业：</span>
						    <input class="easyui-combobox" id="specialty" name="specialty" style="width:105px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,
						                         valueField:'value',textField:'text',
						                         url:'electric/workpriscore/getSpecialtyCombobox'">
						    <span style="margin-left: 10px;">姓名：</span>
						    <input id="personName" class="easyui-textbox" style="width:105px;" 
						           data-options="prompt:'（可模糊查询）'"/>
						    <span>“三种人”类型：</span>
						    <select id="threeType" class="selectorMy easyui-combobox" 
						            data-options="panelHeight:100,editable:false">
						        <option value="">--请选择--</option>
						    	<option value="工作票签发人">工作票签发人</option>
						    	<option value="工作负责人">工作负责人</option>
						    	<option value="工作许可人">工作许可人</option>
						    </select>
						</td>
					</tr>
					<tr>
					    <td style="text-align: center;">
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-query" iconCls="icon-search" plain="true">查询</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-export" iconCls="icon-print" plain="true">导出</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-reset" iconCls="icon-reset" plain="true">重置</a>
					    </td>
					</tr>
					<tr>
						<td style="color: red;padding-left: 10px;">
						最后一次持工作票信息：在查询时间范围内，未持有工作票的人在最近一次开票的票号，
						双击票号可查看具体票面信息
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
		<div id="amount_of_type" style="display: none;padding: 20px;">
		    <table cellpadding="1" cellspacing="0" style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;margin: 0 auto;">
		        <tr>
		            <td class="right">厂站第一种工作票：</td><td id="stationOne">0</td>
		        </tr>
		        <tr>
		            <td class="right">厂站第二种工作票：</td><td id="stationTwo">0</td>
		        </tr>
		        <tr>
		            <td class="right">厂站第三种工作票：</td><td id="stationThree">0</td>
		        </tr>
		        <tr>
		        	<td class="right">线路第一种工作票：</td><td id="lineOne">0</td>
		        </tr>
		        <tr>
		            <td class="right">线路第一种工作票：</td><td id="lineTwo">0</td>
		        </tr>
		        <tr>
		            <td class="right">一级动火工作票：</td><td id="fireOne">0</td>
		        </tr>
		        <tr>
		        	<td class="right">二级动火工作票：</td><td id="fireTwo">0</td>
		        </tr>
		        <tr>
		            <td class="right">配电第一种工作票：</td><td id="pdOne">0</td>
		        </tr>
		        <tr>
		            <td class="right">配电第二种工作票：</td><td id="pdTwo">0</td>
		        </tr>
		        <tr>
		        	<td class="right">带电作业工作票：</td><td id="ddzy">0</td>
		        </tr>
		        <tr>
		            <td class="right">低压配电网工作票：</td><td id="dypdw">0</td>
		        </tr>
		        <tr>
		            <td class="right">紧急抢修工作票：</td><td id="jjqx">0</td>
		        </tr>
		        <tr>
		            <td class="right">安全技术交底单：</td><td id="safty">0</td>
		        </tr>
		        <tr>
		            <td class="right">二次措施单：</td><td id="twice">0</td>
		        </tr>
		        <tr>
		            <td class="right">书面布置和记录：</td><td id="writtenForm">0</td>
		        </tr>
		        <tr>
		            <td class="right">现场勘察记录：</td><td id="xckcjl">0</td>
		        </tr>
		        <tr>
		            <td class="right">未标注工作票类型：</td><td id="isEmpty">0</td>
		        </tr>
		    </table>
		</div>
	</div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/threePerson2.js?v=20190605' ], function(kwa) {
			kwa.threePerson();
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