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
					<span>广州供电局</span>
					<span style='position:relative;'>
						<input class="detail_inp " style='font-size:20px;font-weight: normal;' value="${TicketCheck.stationLineName}"/>
					</span>第三种工作票
				</h2>
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
						<label>工作负责人及工作班人员总人数共 <input class="detail_inp" type='text' style='width:50px;text-align: center;' value="${TicketCheck.numOfWork}"/> 人</label>
					</td>
					<td >计划工作时间</td>
					<td colspan="3" >
						自<input class="detail_inp plan_start_time" style="" value="${TicketCheck.planStartTime}"/><br/><br/>
						至<input class="detail_inp plan_end_time" style="" value="${TicketCheck.planEndTime}" title=""/>
					</td>
				</tr>
				<tr>
					<td colspan="10">工作班人员（不包括工作负责人）：
						<input class="detail_inp work_member_count" type = 'text' style='width:60%;height:35px;border:0;' value="${TicketCheck.workMemberUname}"/>
					</td>
				</tr>
				<tr>
					<td colspan="10">工作任务：
						<input class="detail_inp work_task" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workTask}"/>
					</td>
				</tr>
				<tr>
					<td colspan="10">&nbsp;工作地点：
						<input class="detail_inp work_place" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workPlace}"/>
					</td>
				</tr>
			
				<tr>
					<td colspan="2" class='noTopBorder td1'><span>工作要求的安全措施:</span></td>
					<td colspan="8" class='noTopBorder td1'><textarea>${TicketCheck.requiredSafty }</textarea></td>
				</tr>
				
				
				
				
				
				<tr>
					<td colspan="2" class='td1'>接收</td>
					<td colspan="8">
						值班负责人签名：<input class="detail_inp" type='text' style="margin-left:13px" value=""/>
						<span class='dateCls float-r'>时间：
						    <input class="detail_inp" style="width:105px;"/>
						</span>
					</td>
				</tr>
			
				<tr>
					<td rowspan="3" colspan="2" class='noTopBorder td1'><span>工作许可</span></td>
					<td colspan="8" class='noTopBorder'>
						安全措施是否满足工作要求： 
						<input class="detail_inp" type="radio" onclick="return false;" checked="checked" name="roundNew"  style='width:50px;'/>是
						<input class="detail_inp" type="radio" onclick="return false;" style='width:50px;'/>否
						<br/>
						需补充或调整的安全措施：<input class="detail_inp" type='text' style='width:50%' />
					</td>
				</tr>
				<tr>
				    <td colspan="2">工作地点保留的带电部位</td>
				    <td colspan="6">
				                     带电的母线、导线：
				        <input class="detail_inp" type="text" ><br>
				                     带电的隔离开关：
				        <input class="detail_inp" type="text" ><br>
				                     其他：
				        <input class="detail_inp" type="text"/>
				    </td>
				</tr>
				<tr>
				    <td colspan="8">
				        <span class ="permission_other_care" >其他安全注意事项：</span>
				        <input class="detail_inp" type="text" value="${TicketCheck.permissionOtherCare}"><br>
				                     工作许可人签名：
				        <input class="detail_inp" type="text" value="${TicketCheck.workLicensor}">
				        <span class = "permission_work_principal">工作负责人签名：</span>
				        <input class="detail_inp permission_work_principal" type="text" value="${TicketCheck.permisstionPrincipal}">
				        <br>
				        <span class = "permission_time">时间：</span> 
				        <input class="detail_inp permission_time" type="text" value="${TicketCheck.workPermissionTime}">
				    </td>
				</tr>
			
				<tr>
					<td colspan="2">安全交代</td>
					<td colspan="8">工作班人员确认工作负责人所交代布置的工作任务、安全措施和作业安全注意事项。
						<br/>
						工作班人员签名：<input class="detail_inp" type="text" value="${Ticket.safeAccountUname }"/>
						<br/>
						时间：
						<input class="detail_inp" style="width:105px;" value="${Ticket.safeAccountTime }"/>
					</td>
				</tr>
			
				<tr>
				    <td rowspan="4" colspan="2">工作间断</td>
					<td colspan="2">工作间断时间</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
					<td colspan="2">工作开工时间</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input class="detail_inp gap_time" name="beginDate" style="width:105px;" value="${TicketCheck.workBreakTime}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workBreakLicensor}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workBreakPrincipal}"/>
					</td>
					<td colspan="2">
					    <input class="detail_inp gap_start_time" name="beginDate" style="width:105px;" value="${TicketCheck.workStartTime}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value="${TicketCheck.workStartLicensor}"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px' value=""/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					    <input class="detail_inp" style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td colspan="2">
					    <input class="detail_inp" name="beginDate"style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					    <input class="detail_inp" name="beginDate" style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td colspan="2">
					    <input class="detail_inp" name="beginDate"style="width:105px;"/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
					<td>
					    <input class="detail_inp" type='text' style='width:80px'/>
					</td>
				</tr>
				
				<tr>
				    <td rowspan="4">工作变更</td>
				    <td>工作负责人</td>
					<td colspan="8">
						工作票签发人签名：<input class="detail_inp" type='text' value="${TicketCheck.ticketSigner}"/> 
						<span class="change_principal_uid">原工作负责人签名：</span><input class="detail_inp" type='text' value="${TicketCheck.changePrincipal}"/>
						<br/><br/>
						<span class="change_new_principal_uid">现工作负责人签名：</span><input class="detail_inp" type='text' value="${TicketCheck.changeNewPrincipal}" /> 
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.workLicensor}"/> 
						<span class="change_time">时间：</span><input class="detail_inp" name="beginDate" style="width:105px;"/>
					</td>
				    
				</tr>
				
				
				<tr>
					<td rowspan="3">工作班人员</td>
					<td colspan="4">变更情况</td>
					<td>工作许可人</td>
					<td>工作负责人</td>
					<td colspan="2">变更时间</td>
				</tr>
				<tr>
					<td colspan="4"><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" name="beginDate" style="width:105px;"/></td>
				</tr>
				<tr>
					<td colspan="4"><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td><input class="detail_inp" type='text' /></td>
					<td colspan="2"><input class="detail_inp" name="beginDate" style="width:105px;" /></td>
				</tr>
				
				
				
				<tr>
					<td colspan="2">工作延期</td>
					<td colspan="8">
						<span class="delay_time">有效期延长到：</span>
						<input class="detail_inp delay_time" name="beginDate" style="width:105px;" title="" value="${TicketCheck.workDelayTime}"/>
						<br/><br/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayLicensor }"/> 
						工作负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.workDelayPrincipal }"/>
						<br/><br/>
						时间：<input class="detail_inp delay_fill_time" name="beginDate" style="width:105px;" value="${TicketCheck.workDelayApplyTime }"/>
					</td>
				</tr>
			
				<tr>
					<td colspan="2" style='width:98px'>工作票的终结</td>
					<td colspan="8">  
					           全部作业于<input class="detail_inp work_end_time" name="beginDate" style="width:105px;" />
					           结束，检修临时安全措施已拆除，已恢复作业开始前状态，作业人员已全部撤离，材料工具已清理完毕。
						<br/><br/>
						工作负责人签名：<input class="detail_inp" type='text' value="${TicketCheck.workEndPrincipal }"/>
						工作许可人签名：<input class="detail_inp" type='text' value="${TicketCheck.ticketEndLicensor}"/>
						<br/><br/>
						时间：<input class="detail_inp" name="beginDate" style="width:105px;" value="${TicketCheck.ticketEndTime}" title=""/>
					</td>
				</tr>
				<tr>
					<td colspan="10">备注（工作转移、安全交代补充签名等）：
					    <textarea rows="" cols="">${TicketCheck.remark}</textarea>
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

