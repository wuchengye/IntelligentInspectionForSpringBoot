<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="year_reward_grid"></table>
			<div id="year_reward_win"></div>
			<div id="year_reward_bar">
			<table cellpadding="0" cellspacing="0" style="width:100%">
					<tr>
						<td style="padding-left:5px;">
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-update" iconCls="icon-edit" >修改</a>
						</td>
					</tr>
			</table>
			</div>
			
			    <div id="year_reward_panel" style=" padding: 10px 10px; display: none;">
   					<table width="100%"  cellpadding="5px" cellspacing="0px">
   						<form id="year_reward_form" action="#" method="post" >
							<tr style="display:none"><td align="right">年度奖励分数表id</td><td><input type="text" id="configYearId" name="configYearId" ></td></tr>
							<tr><td align="right">资格人员类型</td><td><input class="wid" type="text"name='type' id='ytype' disabled="disabled"></td></tr>
							<tr><td align="right">奖励等级</td><td><input class="Wdate" type="text"name='yearGrade' id='yearGrade' disabled="disabled"></td></tr>
							<tr><td align="right">输电</td><td><input class="wid"  type='text' name='yearTrans' id=yearTrans></td></tr>
							<tr><td align="right">变电运行</td><td><input class="wid" type='text' name='yearOperation' id='yearOperation'></td></tr>
							<tr><td align="right">变电二次（含计量、通信）</td><td><input class="wid" type='text' name='yearTwice' id='yearTwice'></td></tr>
							<tr><td align="right">变电检修试验（含试研院）</td><td><input type='text'  class="wid"  name='yearInspection' id='yearInspection'></td></tr>
							<tr><td align="right">配电（含路灯所）</td><td><input type='text'  class="wid"  name='yearPower' id='yearPower'></td></tr>
							<tr><td align="right">奖励金额</td><td><input type='text'  class="wid"  name='levelMoneyYear' id='levelMoneyYear'></td></tr>
       					</form>
    				</table>
    				<div style="height: 10px"></div>
    			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/score.js' ], function(kwm) {
				kwm.yearReward();
			});			
		})
		
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