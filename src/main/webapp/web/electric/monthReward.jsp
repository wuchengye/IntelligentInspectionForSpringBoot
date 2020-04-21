<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="month_reward_grid"></table>
			<div id="month_reward_win"></div>
			<div id="month_reward_bar">
			<table cellpadding="0" cellspacing="0" style="width:100%">
					<tr>
						<td style="padding-left:5px;">
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-update" iconCls="icon-edit" >修改</a>
						</td>
					</tr>
			</table>
			</div>
			
		    <div id="month_reward_panel" style=" padding: 10px 10px; display: none;">
  				<form id="month_reward_form" action="#" method="post" >
  					<table width="100%"  cellpadding="5px" cellspacing="0px">
						<tr style="display:none"><td align="right">月度奖励分数表id</td><td><input type="text" id="configMonthId" name="configMonthId" ></td></tr>
						<tr><td align="right">资格人员类型</td><td><input class="wid" type="text"name='type' id='mtype' disabled="disabled"></td></tr>
						<tr><td align="right">奖励等级</td><td><input class="Wdate" type="text"name='monthGrade' id='monthGrade' disabled="disabled"></td></tr>
						<tr><td align="right">输电</td><td><input class="wid"  type='text' name='monthTrans' id=monthTrans></td></tr>
						<tr><td align="right">变电运行</td><td><input class="wid" type='text' name='monthOperation' id='monthOperation'></td></tr>
						<tr><td align="right">变电二次（含计量、通信）</td><td><input class="wid" type='text' name='monthTwice' id='monthTwice'></td></tr>
						<tr><td align="right">变电检修试验（含试研院）</td><td><input type='text'  class="wid"  name='monthInspection' id='monthInspection'></td></tr>
						<tr><td align="right">配电（含路灯所）</td><td><input type='text'  class="wid"  name='monthPower' id='monthPower'></td></tr>
						<tr><td align="right">奖励金额</td><td><input type='text'  class="wid"  name='levelMoneyMonth' id='levelMoneyMonth'></td></tr>
   					</table>
      			</form>
   				<div style="height: 10px"></div>
   			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/score.js' ], function(kwm) {
				kwm.monthReward();
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