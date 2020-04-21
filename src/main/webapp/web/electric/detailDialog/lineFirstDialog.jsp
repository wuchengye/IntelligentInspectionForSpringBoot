<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
					<span class="tst">广州供电局</span>
					<span style='position:relative;'>
						<input class="detail_inp" style='font-size:20px;font-weight: normal;' value="${TicketCheck.stationLineName}"/>
					</span>第一种工作票</h2>
				<div style='display: inline-block;width: 100%'>
					<span style="float: right;">
					        编号：<input class="detail_inp" value="${TicketCheck.ticketNo}" />
				    </span>&nbsp;&nbsp;
				</div>
			</div>
			
			<table>
				<tr>
					<td colspan="6" style="width: 50%;">
						<label class="">工作负责人（监护人）：<input class="detail_inp" style="width: 50%;" value="${TicketCheck.workPrincipal}" /></label>	
						<br/><br/>
						<label>单位和班组：<input class="detail_inp department_oid" type='text' style='width:50%;' value="${TicketCheck.department}" /></label>
						<br/><br/>
						<label>工作负责人及工作班人员总人数共 <input class="detail_inp work_member_count" type='text' style='width:50px;text-align: center;' value="${TicketCheck.numOfWork}"/> 人</label>
					</td>
					<td >计划工作时间</td>
					<td colspan="3" >
						自<input class="detail_inp plan_start_time" style="" value="${TicketCheck.planStartTime}"/><br/><br/>
						至<input class="detail_inp plan_end_time" style="" value="${TicketCheck.planEndTime}" title=""/>
					</td>
				</tr>
				<tr>
				    <td colspan="10">是否办理分组工作派工单：
				        <input type="radio" value="1" name="needSecondProcess" onclick="return false;"
						    <c:if test="${TicketCheck.isHandleSecondMeasures == '是'}">
						        checked="checked"
						    </c:if>
						/>是，共
						<input class="detail_inp secondbill_count"  type="text" style='width:39px;' value="${TicketCheck.numSecondMeasure}"/>张。
						<input type="radio" value="0" name="needSecondProcess" onclick="return false;"
						    <c:if test="${TicketCheck.isHandleSecondMeasures == null || TicketCheck.isHandleSecondMeasures == '' || TicketCheck.isHandleSecondMeasures == '否' }">
						        checked="checked"
						    </c:if>
						/>否
				    </td>
				</tr>
				<tr>
					<td colspan="10">工作班人员（不包括工作负责人）：
						<input class="detail_inp" type = 'text' style='width:60%;height:35px;border:0;' value="${TicketCheck.workMemberUname}"/>
					</td>
				</tr>
				<tr>
					<td colspan="10">工作任务：
						<input class="detail_inp work_task" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workTask}"/>
					</td>
				</tr>
				<tr>
					<td colspan="10">停电线路名称：
						<input class="detail_inp function_location_name" type = 'text' style='width:70%;height:35px;border:0;' value="${TicketCheck.stationLineName}"/>
					</td>
				</tr>
			    <tr>
					<td colspan="10">工作地段：
						<input class="detail_inp work_place" type = 'text' style='width:70%;height:35px;border:0;' value="${TicketCheck.workPlace}"/>
					</td>
				</tr>
				
				<tr>
					<td rowspan="4" colspan="2">工作要求 的安全措施（必要时可附页绘图说明）</td>
					<td colspan="8">应拉断路器（开关）和隔离开关（刀闸）（厂站名及双重名称或编号）：
					    <textarea  class="pull_breaker_switch" rows="" cols="">${TicketCheck.pullBreakerSwitch}</textarea>
					</td>
				</tr>
				<tr>
				   <td colspan="8">应合的接地刀闸（注明双重名称或编号）或应装的接地线（装设地点）：
				       <textarea  class="switch_earthwire_insulation" rows="" cols="">${TicketCheck.switchEarthwireInsulation}</textarea>
				   </td>
				</tr>
				<tr>
					<td colspan="8" class='noTopBorder'>应设遮栏、应挂标示牌（位置）:
					    <textarea class="billboard" rows="" cols="">${TicketCheck.billboard}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="8">其他安全措施和注意事项：
						<textarea  class="other_care" style='background-color:transparent;border:0;'>${TicketCheck.otherCare}</textarea>
					</td>
				</tr>
				
				<tr>
					<td rowspan="2" colspan="2">应装设 的接地线</td>
					<td colspan="2">线路名称及杆号</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">接地线编号</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				
				<tr>
					<td colspan="2" class='noTopBorder td1'>签发</td>
					<td colspan="8" class='noTopBorder' title="">
						<span class="ticket_sign_uid">工作票签发人签名：</span>
						<input class="detail_inp ticket_sign_uid" type='text' value="${TicketCheck.ticketSigner}"/>
						<span class='dateCls float-r ticket_sign_time'>时间：
						    <input class="detail_inp ticket_sign_time" 
						           style="width:125px;" value="${TicketCheck.ticketSignTime}"/>
						</span>
						<br/><br/>
						<span class="ticket_counter_sign_uid">工作票会签人签名：</span>
						<input class="detail_inp ticket_counter_sign_uid" type='text' value="${TicketCheck.ticketCounterSigner}"/>
						<span class='dateCls float-r ticket_counter_sign_time'>时间：
						    <input class="detail_inp ticket_counter_sign_time" 
						           style="width:125px;" value="${TicketCheck.ticketCounterSignTime}"/>
						</span>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" class='td1'>接收</td>
					<td colspan="8">
						值班负责人签名：<input class="detail_inp" type='text' style="margin-left:13px" value="${TicketCheck.watchUname }"/>
						<span class='dateCls float-r'>时间：
						    <input class="detail_inp" style="width:105px;"/>
						</span>
					</td>
				</tr>
			
				<tr>
					<td colspan="2">工作许可</td>
					<td colspan="8">
						工作许可人负责的本工作票 “工作要求的安全措施”栏所述措施已经落实。<br>
						保留或邻近的带电线路、设备：<input class="detail_inp" /><br><br>
						<span class ="permission_other_care" >其他安全注意事项：</span><input class="detail_inp" value="${TicketCheck.permissionOtherCare}"/><br><br>
						<span class ="work_permission_uid">工作许可人签名：</span><input class="detail_inp work_permission_uid" type="text" value="${TicketCheck.workLicensor}"/>
						<span class =permission_work_principal>工作负责人签名：</span><input class="detail_inp permission_work_principal" type="text" value="${TicketCheck.permisstionPrincipal}"/><br>
						许可方式：<input class="detail_inp" type="text"/>
						<span class ="permission_time">时间：</span><input class="detail_inp permission_time" type="text" value="${TicketCheck.workPermissionTime}"/>
					</td>
				</tr>
				<tr>
				    <td colspan="10">
				                   指定<input class="detail_inp" type="text" value="${TicketCheck.guardianName}"/>为专责监护人。          
				                   专责监护人签名：<input class="detail_inp" type="text" <c:if test="${TicketCheck.whetherGuardianSign == '1' }">value="${TicketCheck.guardianName }	</c:if>/>
				    </td>
				</tr>
			
				<tr>
					<td colspan="2">安全交代</td>
					<td colspan="8">工作班人员确认工作负责人所交代布置的工作任务、安全措施和作业安全注意事项。
						<br/>
						工作班人员（分组负责人）签名：<input class="detail_inp" type="text" value="${Ticket.safeAccountUname }"/>
						<br/>
						时间：
						<input class="detail_inp" style="width:105px;" value="${Ticket.safeAccountTime }"/>
					</td>
				</tr>
			
				<tr>
				    <td rowspan="4" colspan="2">工作间断</td>
					<td >工作间断时间</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
					<td>方式</td>
					<td >工作开工时间</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
					<td>方式</td>
				</tr>
				
				<tr>
					<td>
						<input class="detail_inp gap_time" name="beginDate" style="width:105px;" value="${TicketCheck.workBreakTime}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workBreakLicensor}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workBreakPrincipal}"/>
					</td>
					<td>
					    <input class="detail_inp " name="beginDate" style="width:105px;" value=""/>
					</td>
					<td>
					    <input class="detail_inp gap_start_time" type='text' style='width:80px' value="${TicketCheck.workStartTime}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workStartLicensor}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workStartPrincipal}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value=""/>
					</td>
				</tr>
				<tr>
					<td>
					    <input class="detail_inp" style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" name="beginDate"style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value=""/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value=""/>
					</td>
				</tr>
				<tr>
					<td>
					    <input class="detail_inp" name="beginDate" style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" name="beginDate"style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value=""/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value=""/>
					</td>
				</tr>
				
				<tr>
				    <td rowspan="6">工作变更</td>
				    <td>工作任务</td>
					<td colspan="8">
						不需变更安全措施下增加的工作内容：<input class="detail_inp" type="text" value="${Ticket.addContentDetail }"/>
						<br/><br/>
						工作签发人签名：<input class="detail_inp" type="text" value="${TicketCheck.ticketSigner}"/> 
						工作负责人签名：<input class="detail_inp" type="text" value="${TicketCheck.workPrincipal}"/> 
						工作许可人签名：<input class="detail_inp" type="text" value="${ticketCheck.workLicensor}"/>
						<br/><br/>
						时间：<input class="detail_inp" style="width:105px;"/>
					</td>
				</tr>
				<tr>
				    <td>工作负责人</td>
					<td colspan="8">
						工作票签发人签名：<input class="detail_inp" type='text' value="${TicketCheck.ticketSigner}"/> 
						<span class ="change_principal_uid">原工作负责人签名：</span><input class="detail_inp change_principal_uid" type='text' value="${TicketCheck.changePrincipal}"/>
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text'value="${TicketCheck.workLicensor}" /> 
						<span class ="change_time">时间：</span><input class="detail_inp change_time" name="beginDate" style="width:105px;"/>
						<br/><br/>
						<span class ="change_new_principal_uid">现工作负责人签名：</span><input class="detail_inp change_new_principal_uid" type='text'value="${TicketCheck.changeNewPrincipal}" /> 
					</td>
				</tr>
				
				<tr>
					<td rowspan="4">工作班人员</td>
					<td colspan="2">变更情况</td>
					<td colspan="2">工作签发人/许可人</td>
					<td colspan="2">工作负责人</td>
					<td colspan="2">变更时间</td>
				</tr>
				<tr>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" style="width:105px;"/></td>
				</tr>
				<tr>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" style="width:105px;" /></td>
				</tr>
				<tr>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" style="width:105px;" /></td>
				</tr>
				
				<tr>
					<td colspan="2">工作延期</td>
					<td colspan="8">
						<span class="delay_time" title="">有效期延长到：</span>
						<input class="detail_inp delay_time" name="beginDate" style="width:105px;" title="" value="${TicketCheck.workDelayTime}"/>
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayLicensor }"/> 
						工作负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayPrincipal }"/>
						<br/><br/>
						申请方式：  
						<br/><br/>
						<span class ="delay_fill_time">时间：</span><input class="detail_inp delay_fill_time" name="beginDate" style="width:105px;" value="${TicketCheck.workDelayApplyTime }"/>
					</td>
				</tr>
			
				<tr>
				    <td rowspan="3">工作票的终结</td>
					<td>作业终结</td>
					<td colspan="8">  
					           全部作业于<input class="detail_inp work_end_time" value="${TicketCheck.workEndTime }"/>
					           结束，线路（或配电设备）上所装设的接地线共<input class="detail_inp"/>
					           组和使用的个人保安线已全部拆除，作业人员已全部撤离，材料工具已清理完毕，已恢复作业开始前状态。
						<br/>
						工作负责人签名：<input class="detail_inp work_end_principal_uid" type='text' value="${TicketCheck.workEndPrincipal }"/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.ticketEndLicensor}"/>
						<br/>
						终结方式：<input class="detail_inp" type='text' />
						时间：<input class="detail_inp" name="beginDate" style="width:125px;" value="${TicketCheck.ticketEndTime}" title=""/>
					</td>
				</tr>
				<tr title="">
					<td style='width:100px'>许可人措施终结</td>
					<td colspan="8">
					    <span>临时遮栏已拆除，标示牌已取下，常设遮栏已恢复等。</span>
						<br/>
						<span class = "measure_end_per_uid">工作许可人签名：</span><input class="detail_inp measure_end_per_uid" type='text'  value="${TicketCheck.measureEndPerUname}"/> 
						<span class ="measure_end_fill_time">时间：</span><input class="detail_inp measure_end_fill_time" name="beginDate" style="width:105px;" value="${TicketCheck.measureEndFillTime}"/>
				    </td>
				</tr>
				<tr title="">
					<td>汇报调度</td>
					<td  colspan="8" class="">
					          <span class = "dispatch_switch_code">未拉开接地刀闸双重名称或编号： </span><input class="detail_inp dispatch_switch_code" type='text' value="${TicketCheck.unopenedGroundKnife}"/>
						<br/>
						<span class = "dispatch_switch_count">共</span><input class="detail_inp dispatch_switch_count" type='text' value="${TicketCheck.numUnopenedGroundKnife}"/><span class = "dispatch_switch_count">把</span>
						<br/><br/>
						<span class = "dispatch_earthwire_code">未拆除接地线装设地点及编号：</span><input class="detail_inp dispatch_earthwire_code" class="detail_inp" type='text' value="${TicketCheck.unremovedGroundWire}"/>
						<br/>
						<span class = "dispatch_earthwire_count">共</span><input class="detail_inp dispatch_earthwire_count" class="detail_inp" type='text' value="${TicketCheck.numUnremovedGroundWire}"/><span class = "dispatch_earthwire_count">把</span>
						<br/><br/>
						值班负责人签名：<input class="detail_inp" class="detail_inp" type='text'value="${TicketCheck.dispatchPriUname}" /> 值班调度员（姓名）：<input class="detail_inp" type='text' value="${TicketCheck.dispatchUname}"/>
						<br/><br/>
						时间：<input class="detail_inp" class="detail_inp" name="beginDate" style="width:105px;" value="${Ticket.dispatchFillTime}" />
					</td>
				</tr>
				
				<tr>
				    <td colspan="10">备注（工作转移、安全交代补充签名等）：
					    <textarea rows="" cols="" >${TicketCheck.remark}</textarea>
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

