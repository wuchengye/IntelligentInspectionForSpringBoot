<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
 	.rightText{
 	    text-align: right;
 	}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'center'" style="height: 100%;">
			<table id="score_grid"></table>
			<div id="score_win"></div>
			<div id="score_statictis" style="display: none;">
			    <p>（1）工作票负责人个人总得分   = ∑（合格工作票数量 × 工作票种类系数 × 工作时间系数）</p>
			    <p>（2）工作票签发人个人总得分  = ∑（合格工作票数量 × 工作票种类系数）</p>
			    <p>（3）工作票许可人个人总得分  = ∑（合格工作票数量 × 工作票种类系数）</p>
			    <p>（4）班站长总得分 = ∑工作票负责人个人总得分</p>
			    <p>（5）工作时间系数 = 0.2 × ∑（作业终结时间  - 工作许可时间  - 工作间断时间）</p>
			</div>
			<div id="score_bar">
			    <form id="scoreForm">
				<table cellpadding="0" cellspacing="0" 
				       style="width:90%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;margin: 0 auto;">
					<tr>
						<td class="rightText" style="padding-left:5px;">报表类型：</td>
						<td>
						    <select class="easyui-combobox" id="reportType" name="reportType" style="width: 180px;" 
				                   data-options="panelHeight:'auto'">
				               <option value="工作许可人">工作许可人得分汇总</option>
				               <option value="工作负责人" selected="selected">工作负责人得分汇总</option>
				               <option value="工作票签发人">工作票签发人得分汇总</option>
				           </select>
						</td>
						<td class="rightText">年份：</td>
						<td><select class="easyui-combobox" id="myYear" style="width: 180px;" 
						            data-options="valueField:'year',textField:'text',panelHeight:'auto',panelMaxHeight:220,editable:false"></select>
						</td>
						<td class="rightText">所属专业：</td>
						<td>
						    <input class="easyui-combobox" id="specialty" name="specialty" style="width:180px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/workpriscore/getSpecialtyCombobox'">
						</td>
					</tr>
					<tr>
					    <td class="rightText">单位名称：</td>
					    <td>
					        <!-- <input class="easyui-combotree" id="unitName" name="unitName" style="width:180px;" 
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,panelWidth:280,
							                         valueField:'value',textField:'text',url:'electric/statistics/getComboxTree'"/> --><!-- url:'electric/workpriscore/getUnitCombobox' -->
							                         
							<input type="text" id="unitName" name="unitName" class="selectorMy" style="width:180px;" data-options=""/>
						</td>
						<!-- <td class="rightText">所属班组：</td>
						<td>
						    <input class="easyui-combobox" id="classes" name="classes" style="width:180px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,
						                         editable:true,valueField:'value',textField:'text',">url:'electric/workpriscore/getClassCombobox'
						</td> -->
						<td class="rightText">人员姓名：</td>
						<td>
						    <input class="easyui-textbox" id="personName" name="personName" style="width:180px;" data-options="prompt:'(可模糊查询)'"/>
						</td>
					</tr>
					<tr>
					    <td colspan="6" style="text-align: center;">
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-query" iconCls="icon-search" plain="true">查询</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-export" iconCls="icon-print" plain="true">导出</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-reset" iconCls="icon-reset" plain="true">重置</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-score" iconCls="icon-sum" plain="true">得分统计公式</a>
							<!-- <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-modulu" iconCls="icon-tip" plain="true">配置系数</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-month" iconCls="icon-tip" plain="true">配置月度奖励分数</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-year" iconCls="icon-tip" plain="true">配置年度奖励分数</a> -->
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
    </div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/score.js?v=20190523' ], function(kwa) {
			kwa.score();
		});		
		
		//设置年份的选择 
		var myDate= new Date(); 
		var startYear=myDate.getFullYear()-2;//起始年份 
		var endYear=myDate.getFullYear()+30;//结束年份 
		
		var years = new Array();
		for (var i=startYear;i<=endYear;i++) {
			var t = {"text":i+'',"year":i+''};
			if(i == myDate.getFullYear()){
				t = {"text":i+'',"year":i+'',selected:true};
			}
			years.push(t);
		}
		
		$("#myYear").combobox("loadData",years);
		
		$("#reportType").combobox({
			onSelect:function(){
				$("#myYear").combobox("select",myDate.getFullYear());
				$("#unitName").combobox("reset");
				$("#specialty").combobox("reset");
				//$("#classes").combobox("reset");
				$("#personName").textbox("reset");
			}
		});
		
		/* $("#unitName").combobox({
			onSelect:function(){
				var unitName = $(this).combobox("getValue");
				$("#classes").combobox({
					url:'electric/workpriscore/getClassCombobox',
					queryParams:{
						unitName:unitName
					}
				})
			}
		}) */
		
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