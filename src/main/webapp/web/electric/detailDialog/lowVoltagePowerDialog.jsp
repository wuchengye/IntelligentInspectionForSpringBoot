<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 低压配电网工作票详情弹框 -->

<style>
 	div.panel-title { 
 		text-align:center;
 	} 	
 	#detailPageForm table{
 		border-collapse: collapse;
 		width:90%;
	}
	#detailPageForm table tr td{
 		border:1px solid #000; 
 		padding:5px;
	}
	input.detail_inp{
  		font-family: -webkit-pictograph;
	    font-weight: 600;
	    border: 0px;
   		border-bottom: 1px solid #444;
   		background-color:transparent;
	}
	input.detail_inp:focus{
	    outline: none;
	}
	#detailPageForm table tr td textarea{
  		font-family: -webkit-pictograph;
	    color: #000;
	    font-weight: 600;
	    width:90%;
	    resize: none;
	    height:29px;
	    background-color:transparent;
	    border:0;
	}
	#detailPageForm table tr td textarea:focus{
	    outline: none;
	}
	h2{
		font-weight: inherit;
	}
	#detailPageForm table tr td{
		font-size:14px;
	}

	#detailPageForm table tr td input:-webkit-calendar-picker-indicator{
		display: none;
		-webkit-appearance: none;

	}
	.panel-body-noheader{
		height:250px;
	}
	.checkRs{
		color: red !important;
	}

</style>

<div class="easyui-layout" style="width: 100%; height: 100%;border:none;">
	<div data-options="region:'center'" style="height: 100%;margin-left:70px;border:none;">
		<form id="detailPageForm" action="">
		    <input id="childLs" type="hidden" value='${childLs}'>
		    <div style="margin-top: 5px;margin-bottom: 5px;">
		        <h3>${result.checkString}</h3>
		        <h3>${result.standardString}</h3>
		    </div>
		    <!-- 标题部分 -->
			<div style ="text-align:center;margin-right:11%;margin-bottom:5px">
				<h2>
					<span>广州供电局低压配电网工作票</span>
				</h2>
				<div style='display: inline-block;width: 100%'>
					<span style="float: right;">
					        编号：<input class="detail_inp" value="${TicketCheck.ticketNo}" />
				    </span>&nbsp;&nbsp;
				</div>
			</div>
			
			<table>
				<tr>
					<td colspan="4" style="width: 50%;">
						<label class="">工作负责人（监护人）：<input class="detail_inp" style="width: 50%;" value="${TicketCheck.workPrincipal} ${TicketCheck.workPrincipalTel}" /></label>	
						<br/><br/>
						<label>单位和班组：<input class="detail_inp department_oid" type='text' style='width:50%;' value="${TicketCheck.department}" /></label>
						<br/><br/>
						<label>工作负责人及工作班人员总人数共 <input class="detail_inp" type='text' style='width:50px;text-align: center;' value="${TicketCheck.numOfWork}"/> 人</label>
					</td>
					<td >计划工作时间</td>
					<td colspan="3" >
						自<input class="detail_inp plan_start_time" style="" value="${TicketCheck.planStartTime}"/><br/><br/>
						至<input class="detail_inp plan_end_time" style="" value="${TicketCheck.planEndTime}" title=""/>
					</td>
				</tr>
				<tr>
					<td colspan="8">工作班人员（不包括工作负责人）：
						<input class="detail_inp work_member_count" type = 'text' style='width:60%;height:35px;border:0;' value="${TicketCheck.workMemberUname}"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">工作任务：
						<input class="detail_inp work_task" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workTask}"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">停电线路名称：
						<input class="detail_inp work_task" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workTask}"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">&nbsp;工作地段（可附页绘图）：
						<input class="detail_inp work_place" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workPlace}"/>
					</td>
				</tr>
			
				<tr>
					<td rowspan="2" style="width:20%;">工作要求 的安全措施（可附页绘图）</td>
					<td colspan="7">工作条件和应采取的安全措施（停电、接地、隔离和装设的安全遮栏、围栏、标示牌等）
					    <input class="detail_inp" type="text" style="width:60%;height:35px;border:0;"value="${TicketCheck.highpDeviceState}">
					</td>
				</tr>
				<tr>
				   <td colspan="7">保留的带电部位：
				       <input class="detail_inp" type="text" style="width:60%;height:35px;border:0;" value="${TicketCheck.powerCircleState}">
				   </td>
				</tr>
				
				<tr>
				    <td rowspan="2">应装设的接地线</td>
				    <td style="width:11%;">线路名称或位置</td>
				    <td style="width:11%;"></td>
				    <td style="width:11%;"></td>
				    <td style="width:11%;"></td>
				    <td style="width:11%;"></td>
				    <td style="width:11%;"></td>
				    <td style="width:11%;"></td>
				</tr>
				
				<tr>
				    <td>接地线编号</td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td></td>
				</tr>
				
				<tr>
					<td class='noTopBorder td1'>签发</td>
					<td colspan="7" class='noTopBorder' title="">
						<span class="ticket_sign_uid">工作票签发人签名：</span>
						<input class="detail_inp ticket_sign_uid" 
						       type='text' value="${TicketCheck.ticketSigner}" />
						<span class='dateCls float-r ticket_sign_time'>时间：
						    <input class="detail_inp ticket_sign_time" 
						           style="width:105px;" value="${TicketCheck.ticketSignTime}"/>
						</span>
						<br/><br/>
						<span class="ticket_counter_sign_uid">工作票会签人签名：</span>
						<input class="detail_inp ticket_counter_sign_uid" type='text' value="${TicketCheck.ticketCounterSigner}"/>
						<span class='dateCls float-r ticket_counter_sign_time'>时间：
						    <input class="detail_inp ticket_counter_sign_time" 
						           style="width:105px;" value="${TicketCheck.ticketCounterSignTime}"/>
						</span>
					</td>
				</tr>
				
				<tr>
					<td class='td1'>接收</td>
					<td colspan="7">
						值班负责人签名：<input class="detail_inp" type='text' style="margin-left:13px" value="${TicketCheck.watchUname }"/>
						<span class='dateCls float-r'>时间：
						    <input class="detail_inp" style="width:105px;" value="${TicketType.receiveTime }"/>
						</span>
					</td>
				</tr>
			
				<tr>
					<td class='noTopBorder td1'><span>工作许可</span></td>
					<td colspan="7" class='noTopBorder'>
					    <input type="checkbox" onclick="return false;">工作许可人负责的本工作票 “工作要求的安全措施”栏所述措施已经落实。
					    <br/> 
					           保留或邻近的带电线路、设备：<input class="detail_inp" type="text" value="${TicketCheck.eleLineDevice}"/>
						<br/>
						<span class ="permission_other_care" >其他补充安全注意事项：
						    <b style="padding-bottom: 1px;border-bottom: 1px solid;">${TicketCheck.permissionOtherCare}（根据现场实际需要填写）</b>
						</span>
						<%-- <input class="detail_inp" type="text" value="${TicketCheck.permissionOtherCare}"> --%>
				        <br/>
						工作许可人签名：
				        <input class="detail_inp" type="text" value="${TicketCheck.workLicensor}">
				        <span class = "permission_work_principal">工作负责人签名：</span>
				        <input class="detail_inp permission_work_principal" type="text" value="${TicketCheck.permisstionPrincipal}">
				        <br/>
				                     许可方式：<input class="detail_inp"/>
				        <span class = "permission_time">时间：</span> 
				        <input class="detail_inp permission_time" type="text" value="${TicketCheck.workPermissionTime}">
						
					</td>
				</tr>
				
				<tr>
					<td >安全交代</td>
					<td colspan="7">工作班人员确认工作负责人所交代布置的工作任务、安全措施和作业安全注意事项。
						<br/>
						工作班人员（分组负责人）签名：<input class="detail_inp" type="text" value="${Ticket.safeAccountUname }"/>
						<br/>
						时间：
						<input class="detail_inp" style="width:105px;" value="${Ticket.safeAccountTime }"/>
					</td>
				</tr>
				
				<tr>
				    <td >增加工作任务</td>
					<td colspan="7">
						不需变更安全措施下增加的工作内容：<input class="detail_inp" type="text" value="${Ticket.addContentDetail }"/>
						<br/><br/>
						工作票签发人签名：<input class="detail_inp" type="text" value="${TicketCheck.ticketSigner}"/> 
						工作负责人签名：<input class="detail_inp" type="text" value="${TicketCheck.workPrincipal}"/> 
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type="text" value="${TicketCheck.workLicensor}"/>
						时间：<input class="detail_inp" style="width:105px;"/>
					</td>
				</tr>
				
				<tr>
					<td >工作延期</td>
					<td colspan="7">
						<span class="delay_time">有效期延长到：</span>
						<input class="detail_inp delay_time" name="beginDate" style="width:105px;" title="" value="${TicketCheck.workDelayTime}"/>
						<br/><br/>
						工作负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayPrincipal }"/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayLicensor }"/> 
						<br/><br/>
						时间：<input class="detail_inp delay_fill_time" name="beginDate" style="width:105px;" value="${TicketCheck.workDelayApplyTime }"/>
					</td>
				</tr>
			
				<tr>
					<td >工作票的终结</td>
					<td colspan="7">  
					           全部作业于<input class="detail_inp work_end_time" name="beginDate" style="width:105px;" />
					           结束，线路（或配电设备）上所装设的接地线共（  <input class="detail_inp" type="text" style="width:10px;" value="${TicketCheck.workEndEarthwireCount }"/> ）组已全部拆除，工作人员已全部撤离，材料工具已清理完毕，已恢复工作开始前状态。
						<br/><br/>
						工作负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.workEndPrincipal }"/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.ticketEndLicensor}"/>
						<br/><br/>
						终结方式：<input class="detail_inp" type="text" value="${TicketCheck.workEndReportWay }"/>
						时间：<input class="detail_inp" name="beginDate" style="width:105px;" value="${TicketCheck.ticketEndTime}" title=""/>
					</td>
				</tr>
				<tr>
					<td colspan="8">备注（工作间断、变更、补充措施等））：
					    <textarea class="detail_inp" rows="" cols="">${TicketCheck.remark}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>	

<script type="text/javascript">
    $(function(){
        $(".detail_inp").attr("readonly","readonly");
        
        seajs.use([ 'module/commonUtil/showChildrenDetail.js' ], function(show) {
			show.init();
		});
    })
</script>

