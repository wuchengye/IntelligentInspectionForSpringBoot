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
					<span>广州供电局</span>
					<span style='position:relative;'>
						<input class="detail_inp function_location_name" style='font-size:20px;font-weight: normal;' value="${TicketCheck.stationLineName}"/>
					</span>第一种工作票
				</h2>
				<div style='display: inline-block;width: 100%'>
					<span class="" style="float: right;">
					        编号：<input class="detail_inp" value="${TicketCheck.ticketNo}" />
				    </span>&nbsp;&nbsp;
				</div>
			</div>
			
			<table>
				<tr>
					<td style="width: 50%;">
						<label class="">工作负责人（监护人）：<input class="detail_inp" style="width: 50%;" value="${TicketCheck.workPrincipal}" /></label>	
						<br/><br/>
						<label>单位和班组：<input class="detail_inp department_oid" type='text' style='width:50%;' value="${TicketCheck.department}" /></label>
						<br/><br/>
						<label class="work_member_count">工作负责人及工作班人员总人数共 <input class="detail_inp work_member_count" type='text' style='width:50px;text-align: center;' value="${TicketCheck.numOfWork}"/> 人</label>
					</td>
					<td>计划工作时间</td>
					<td class='dateCls' class="plan_start_time plan_end_time">
						自<input class="detail_inp plan_start_time" style="" value="${TicketCheck.planStartTime}"/><br/><br/>
						至<input class="detail_inp plan_end_time" style="" value="${TicketCheck.planEndTime}" title=""/>
					</td>
				</tr>
				<tr>
					<td colspan="3">工作班人员（不包括工作负责人）：
						<input class="detail_inp" type = 'text' style='width:60%;height:35px;border:0;' value="${TicketCheck.workMemberUname}"/>
					</td>
				</tr>
				<tr>
					<td colspan="3">工作任务：
						<input class="detail_inp work_task" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workTask}"/>
					</td>
	
				</tr>
				<tr>
					<td colspan="3" class="work_place">工作地点：
						<input class="detail_inp work_place" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workPlace}"/>
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td rowspan="9" class='noTopBorder td1'><span>工作要求的安全措施</span></td>
				</tr>
				<tr>
					<td colspan="2" class='noTopBorder'>应拉开的断路器（开关）和隔离开关（刀闸）（双重名称或编号）</td>
				</tr>
				<tr>
					<td style='border-width:1px;'>
						<span class="pull_breaker">断路器（开关）：</span>
						<textarea class="detail_inp pull_breaker" style='background-color:transparent;border:0;'>${TicketCheck.pullBreaker}</textarea>
					</td>
					<td>隔离开关（刀闸）：<textarea class="detail_inp " style='background-color:transparent;border:0;'>${TicketCheck.pullSwitch}</textarea>
						
					</td>
				</tr>
				<tr>
					<td colspan="2" class='noTopBorder dcpower_lowp_circle' >应投切的相关直流电源（空气开关、熔断器、连接片）、低压及二次回路：
					<br/><textarea class="detail_inp dcpower_lowp_circle" style='height:49px;border:0;background-color:transparent;'>${TicketCheck.dcPowerLowpCircle}</textarea>
				</tr>
				<tr>
					<td colspan="2" class="switch_earthwire_insulation">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板：
					<br/><textarea class="detail_inp switch_earthwire_insulation" style='height:39px;background-color:transparent;border:0;'>${TicketCheck.switchEarthwireInsulation}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					    <span class="billboard">应设遮拦、应挂标识牌（注明位置）：</span>
					    <br/>
					    <textarea class="detail_inp billboard" style='height:49px;background-color:transparent;border:0;' >${TicketCheck.billboard}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">是否需线路对侧接地：
					    <input type="radio" name="isLanding" value="1" style='width:50px;' onclick="return false;" 
					           <c:if test="${TicketCheck.whetherLineGrounding == '1' }">checked="checked"</c:if> />是
					    <input type="radio" name="isLanding" value="0" style='width:50px;' onclick="return false;" 
					           <c:if test="${TicketCheck.whetherLineGrounding == null || TicketCheck.whetherLineGrounding == '' || TicketCheck.whetherLineGrounding == '2' }">checked="checked"</c:if>/>否
					</td>
				</tr>
				<tr>
					<td colspan="2"><span class="whether_secondbill">是否需办理二次设备及回路工作安全技术措施单：</span>
						<input type="radio" value="1" name="needSecondProcess" onclick="return false;"
						    <c:if test="${TicketCheck.isHandleSecondMeasures == '1'}">
						        checked="checked"
						    </c:if>
						/>是，共
						<input class="detail_inp secondbill_count"  type="text" style='width:39px;' value="${TicketCheck.numSecondMeasure}"/>张。
						<input type="radio" value="0" name="needSecondProcess" onclick="return false;"
						    <c:if test="${TicketCheck.isHandleSecondMeasures == null || TicketCheck.isHandleSecondMeasures == '' || TicketCheck.isHandleSecondMeasures == '2' }">
						        checked="checked"
						    </c:if>
						/>否
					</td>
				</tr>
				<tr>
					<td colspan="2" class="other_care">其他安全措施和注意事项：
						<textarea class="other_care" style='background-color:transparent;border:0;'><%-- ${TicketCheck.otherCare } --%>无。</textarea>
					</td>
				</tr>
				
			</table>
			
			<table>
				<tr>
					<td class='noTopBorder td1'>签发</td>
					<td class='noTopBorder' title="">
						<span class="ticket_sign_uid">工作票签发人签名：</span>
						<input class="detail_inp ticket_sign_uid" 
						       type='text' value="${TicketCheck.ticketSigner}" />
						<span class='dateCls float-r ticket_sign_time' >时间：
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
					<td>
						值班负责人签名：<input class="detail_inp" type='text' style="margin-left:13px" value=""/>
						<span class='dateCls float-r'>时间：
						    <input class="detail_inp" style="width:105px;"/>
						</span>
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td  rowspan="8" class='noTopBorder td1'><span>工作许可</span></td>
				</tr>
				<tr>
					<td colspan="2" class='noTopBorder'>
						安全措施是否满足工作要求： 
						<input class="detail_inp" type="radio" onclick="return false;" checked="checked" name="roundNew"  style='width:50px;'/>是
						<input class="detail_inp" type="radio" onclick="return false;" style='width:50px;'/>否
						<br/>
						需补充或调整的安全措施：<input class="detail_inp" type='text' style='width:50%' />
						<br/>
						是否需以手触试：
						<input class="detail_inp" type="radio" onclick="return false;" style='width:50px;' 
						        <c:if test="${TicketCheck.isTouch == '1'}">checked="checked"</c:if> />是
						<input class="detail_inp" type="radio" onclick="return false;" style='width:50px;' 
						       <c:if test="${TicketCheck.isTouch == null ||TicketCheck.isTouch == '' || TicketCheck.isTouch == '2'}">checked="checked"</c:if>/>否
						<br/>
						以手触试的具体部位：<input class="detail_inp" type='text' style='width:50%' value="${TicketCheck.touchPosition }"/>
					</td>
				</tr>
				<tr>
				    <td colspan="2">
					线路对侧安全措施：经值班调度员（配电网运维人员）（姓名）<input class="detail_inp" type='text' value="${TicketCheck.lineGroundingUname }"/>
					<br/>
					确认线路对侧已按要求执行。
					</td>	
				</tr>
				<tr>
					<td style="width:160px;">工作地点保留的带电部位</td>
					<td>
						带电的母线、导线：<input class="detail_inp" type='text' value="${TicketCheck.eleGeneratrixWire }"/>
						<br/><br/>
						带电的隔离开关（刀闸）：<input class="detail_inp" type='text' value="${TicketCheck.eleSwitch }"/>
						<br/><br/>
						其他：<input class="detail_inp" type='text' style="width: 70%" value="${TicketCheck.elePart }"/>
						<br/><br/>
						其他安全注意事项：<input class="detail_inp" type='text' style="width: 70%" value="${TicketCheck.otherCare }"/>
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.workLicensor}"/> 
						<br/><br/>
						<span class="permission_work_principal">工作负责人签名：</span><input class="detail_inp" type='text' value="${TicketCheck.permisstionPrincipal}"/> 
						<br/><br/>
						<span class="permission_time">时间：</span>
						<input class="detail_inp permission_time" style="width:105px;" value="${TicketCheck.workPermissionTime}"/>
					</td>
				</tr>
				
			</table>
			
			<table>
				<tr>
					<td class='noTopBorder'>指定
						<input class="detail_inp" type='text' value="${TicketCheck.guardianName }"/>为专责监护人 &nbsp;&nbsp;&nbsp;&nbsp;
						<span style="margin-left:100px">专责监护人签名：</span>
						<input class="detail_inp" type='text' 
						       <c:if test="${TicketCheck.whetherGuardianSign == '1' }">value="${TicketCheck.guardianName }	</c:if> />
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td class='noTopBorder td1'>安全交代</td>
					<td class='noTopBorder'>工作班人员确认工作负责人所交代布置的工作任务、安全措施和作业安全注意事项。
						<br/><br/>
						工作班人员签名：<input class="detail_inp" type='text' value="${Ticket.safeAccountUname }"/>
						<br/><br/>
						时间：<input class="detail_inp" style="width:105px;" value="${Ticket.safeAccountTime }"/>
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
				    <td rowspan="5" class='noTopBorder td1' style='width:65px'>工作间断</td>
				</tr>
				<tr class='noTopBorder'>
					<td>工作间断时间</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
					<td>工作开工时间</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
				</tr>
				<tr class="">
					<td class='dateCls'><input class="detail_inp gap_time" style="width:105px;" value="${TicketCheck.workBreakTime}"/></td>
					<td><input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workBreakLicensor}"/></td>
					<td><input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workBreakPrincipal}"/></td>
					<td class='dateCls'><input class="detail_inp gap_start_time" name="beginDate" style="width:105px;" value="${TicketCheck.workStartTime}"/></td>
					<td><input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workStartLicensor}"/></td>
					<td><input class="detail_inp" type='text' style='width:80px' value=""/></td>
				</tr>
				<tr class="">
					<td class='dateCls'>
					<input class="detail_inp" name="beginDate" style="width:105px;"/>
					</td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
					<td class='dateCls'><input class="detail_inp" name="beginDate"style="width:105px;"/></td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
				</tr>
				<tr class="">
					<td class='dateCls'>
					<input class="detail_inp" name="beginDate" style="width:105px;"/>
					</td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
					<td class='dateCls'><input class="detail_inp" name="beginDate"style="width:105px;"/></td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
					<td><input class="detail_inp" type='text' style='width:80px'/></td>
				</tr>
			</table>
			<table>
				<tr><td rowspan="8" class='noTopBorder td1'>工作变更</td></tr>
				<tr>
				    <td class='noTopBorder'>工作任务</td>
					<td colspan="4" class='noTopBorder'>
						不需变更安全措施下增加的工作内容：<input class="detail_inp" style="width: 60%" type='text' value="${Ticket.addContentDetail }"/>
						<br/><br/>
						工作负责人签名：<input class="detail_inp" type='text' /> 
						工作许可人签名：<input class="detail_inp" type='text' />
						<br/><br/>
						工作票签发人签名：<input class="detail_inp" type='text' /> 
						时间：<input class="detail_inp" name="beginDate" style="width:105px;"/>
					</td>
				</tr>
				<tr>
				    <td>工作负责人</td>
					<td colspan="4">
						工作票签发人签名：<input class="detail_inp" type='text' /> 
						<span class="change_principal_uid">原工作负责人签名：</span><input class="detail_inp change_principal_uid" type='text' />
						<br/><br/>
						<span class="change_new_principal_uid">现工作负责人签名：</span><input class="detail_inp change_new_principal_uid" type='text' /> 
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text' /> 
						<span class="change_time">时间：</span><input class="detail_inp change_time" style="width:105px;"/>
					</td>
				</tr>
				<tr>
					<td rowspan="5">工作班人员</td>
				</tr>
				<tr>
					<td>变更情况</td>
					<td>工作票签发人/工作许可人</td>
					<td>工作负责人</td>
					<td>变更时间</td>
				</tr>
				<tr>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" name="beginDate" style="width:105px;"/></td>
				</tr>
				<tr>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" name="beginDate" style="width:105px;" /></td>
				</tr>
				<tr>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" name="beginDate" style="width:105px;" /></td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td class='noTopBorder'>工作延期</td>
					<td class='noTopBorder'>
						<span class="delay_time">有效期延长到：</span>
						<input class="detail_inp delay_time" style="width:105px;" value="${TicketCheck.workDelayTime }"
						       title=""/>
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayLicensor }"/> 
						工作负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayPrincipal }"/>
						<br/><br/>
						<span class="delay_fill_time">时间：</span><input class="detail_inp delay_fill_time" name="beginDate" style="width:105px;" value="${TicketCheck.workDelayApplyTime }"/>
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td rowspan="4" class='noTopBorder td1' style='width:98px'>工作票的终结</td>
				</tr>
				<tr>
					<td class='noTopBorder'>作业终结</td>
					<td class='noTopBorder'>  
					           全部作业于<input class="detail_inp work_end_time" style="width:105px;" value="${TicketCheck.workEndTime }"/>
					           结束，检修临时安全措施已拆除，已恢复作业开始前状态，作业人员已全部撤离，材料工具已清理完毕。
						<br/><br/>
						<span class="work_end_principal_uid">工作负责人签名：</span><input class="detail_inp work_end_principal_uid" type='text' value="${TicketCheck.workEndPrincipal }"/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.ticketEndLicensor}"/>
						<br/><br/>
						时间：<input class="detail_inp" name="beginDate" style="width:105px;" value="${TicketCheck.ticketEndTime}" title=""/>
					</td>
				</tr>
				<tr title="">
					<td style='width:100px' class="">许可人措施终结</td>
					<td>
						<br/>
						<span class = "measure_end_per_uid">工作许可人签名：</span><input class="detail_inp" type='text' value="${TicketCheck.measureEndPerUname}"/> 
						<span class = "measure_end_fill_time">时间：</span><input class="detail_inp measure_end_fill_time" style="width:105px;" value="${TicketCheck.measureEndFillTime}"/>
				    </td>
				</tr>
				<tr title="">
					<td>汇报调度</td>
					<td class="">
					    <span class="dispatch_switch_code">未拉开接地刀闸双重名称或编号：</span> <input class="detail_inp dispatch_switch_code" type='text' value="${TicketCheck.unopenedGroundKnife}"/>
						<br/>
						<span class = "dispatch_switch_count">共</span><input class="detail_inp dispatch_switch_count" type='text' value="${TicketCheck.numUnopenedGroundKnife}"/><span class = "dispatch_switch_count">把</span>
						<br/><br/>
						<span class="dispatch_earthwire_code">未拆除接地线装设地点及编号：</span><input class="detail_inp dispatch_earthwire_code" type='text' value="${TicketCheck.unremovedGroundWire}"/>
						<br/>
						<span class = "dispatch_earthwire_count">共</span><input class="detail_inp dispatch_earthwire_count" type='text' value="${TicketCheck.numUnremovedGroundWire}"/><span class = "dispatch_earthwire_count">把</span>
						<br/><br/>
						值班负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.dispatchPriUname}"/> 
						值班调度员（姓名）：<input class="detail_inp" type='text' value="${TicketCheck.dispatchUname}"/>
						<br/><br/>
						时间：<input class="detail_inp" style="width:105px;" value="${Ticket.dispatchFillTime}"/>
					</td>
				</tr>
				<tr>
					<td colspan="3" title="">备注（工作转移、安全交代补充签名等）：
					    <textarea class="detail_inp" style='background-color:transparent;border:0;' type='text' >${TicketCheck.remark}	</textarea>
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

