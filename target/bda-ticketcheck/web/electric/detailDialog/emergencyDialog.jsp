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
					<span class="tst">广州供电局线路紧急抢修工作票</span>
					</h2>
				<div style='display: inline-block;width: 100%'>
					<span style="float: right;">
					        编号：<input class="detail_inp" value="${TicketCheck.ticketNo}" />
				    </span>&nbsp;&nbsp;
				</div>
			</div>
			
			<table>
				<tr>
					<td rowspan="3">
						启动抢修
					<td colspan="2"><label class="">工作负责人（监护人）：<input class="detail_inp" style="width: 50%;" value="${TicketCheck.workPrincipal}" /></label>
					      <br/><br/>
					      <label>单位和班组：<input class="detail_inp department_oid" type='text' style='width:50%;' value="${TicketCheck.department}" /></label>
					      <br/><br/>
					      <label>负责人及工作班人员总人数共 <input class="detail_inp work_member_count" type='text' style='width:50px;text-align: center;' value="${TicketCheck.numOfWork}"/> 人</label>
					</td>
				</tr>
					<tr>
					<td colspan="2" >
						<label>抢修任务（抢修地点和抢修内容）：<input class="detail_inp work_task" type = 'text' style='width:80%;height:35px;border:0;' value="${TicketCheck.workTask}"/></label>
					</td>
					</tr>
					<tr>
					<td colspan="2">
					  <label>安全措施及注意事项：<input class="detail_inp other_care" type='text' style='width:50%;' value="${TicketCheck.otherCare}"/></label>
					  
					</td>
					</tr>
				
				<tr>
				    <td>布置抢修</td>
				    <td><label>本项工作及主要安全事项根据抢修任务布置人<input class="detail_inp" type='text' style='width:50%;' value="${TicketCheck.repairControlUname}"/>安排填写</label>
				</tr>
				<tr>
					<td >抢修许可：</td>
					<td><label>经核实确认或需补充调整的安全措施：</label><br/>
						<input class="detail_inp" type = 'text' style='width:60%;height:35px;border:0;' value="${TicketCheck.permissionOtherCare}"/><br/>
						<label>工作许可人签名：<input class="detail_inp work_permission_uid" type="text" value="${TicketCheck.workLicensor}"/></label>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label>工作负责人签名：<input class="detail_inp permission_work_principal" type="text" value="${TicketCheck.permisstionPrincipal}"/></label><br/>
						<span class ="permission_time">时间：</span><input class="detail_inp permission_time" type="text" value="${TicketCheck.workPermissionTime}"/>
					</td>
				</tr>
				<tr>
					<td >抢修结束或转移工作票：</td>
					<td>
					   <label>抢修结束或转移工作票时间：</label><br/>
					   <input class="detail_inp work_end_time" type = 'text' style='width:80%;height:35px;border:0;' value="现场全部接地线已拆除，抢修人员已撤离，${TicketCheck.workEndTime}抢修工作结束."/><br/>
					   <label>现场设备状况及保留安全措施<input class="detail_inp" type='text' style='width:90%;' value="${TicketCheck.liveSafeMeasure}"/></label><br/><br/>
					   
					   <label>工作负责人签名:<input class="detail_inp work_end_principal_uid" type='text' value="${TicketCheck.workEndPrincipal }"/></label>
					    &nbsp;&nbsp;&nbsp;
					    <label>工作负责人签名:<input class="detail_inp" type='text' value="${TicketCheck.ticketEndLicensor}"/></label><br/>
						时间：<input class="detail_inp" name="beginDate" style="width:125px;" value="${TicketCheck.ticketEndTime}" title=""/>
					</td>
				</tr>
				<tr>
				    <td colspan="3">备注：
					    
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

