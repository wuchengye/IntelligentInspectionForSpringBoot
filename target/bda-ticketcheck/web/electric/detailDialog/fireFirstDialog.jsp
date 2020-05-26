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
					</span>一级动火工作票</h2>
				<div style='display: inline-block;width: 100%'>
					<span style="float: right;">
					        编号：<input class="detail_inp" value="${TicketCheck.ticketNo}" />
				    </span>&nbsp;&nbsp;
				</div>
			</div>
			
			<table>
				<tr>
					<td >动火工作负责人</td>
					<td><input class="detail_inp" type="text" value="${TicketCheck.workPrincipal}(${TicketCheck.workPrincipalTel})" /></td>
					<td>对应工作票编号</td>
					<td colspan="2"><input class="detail_inp" type="text" value="${TicketCheck.ticketNo}" /></td>
				</tr>
				<tr>
				<td>动火部门</td>
				<td>
					<input class="detail_inp department_oid" type='text' style='width:100%;' value="${TicketCheck.department}" />
				</td>
				<td >班组</td>
				<td colspan="2">
					 <input class="detail_inp work_member_count" type='text' style='width:100%;text-align: center;' value="${TicketCheck.department}"/>
				</td>
					
				</tr>
				<tr>
				<td colspan="1">
				 动火地点及设备名称
				</td>
				<td colspan="4">
				<input style="width:80%" class="detail_inp"  value="${TicketCheck.workPlace }" />
				</td>
				</tr>
				
				<tr>
				<td colspan="5">
				 <label>动火工作任务(附示意图))：<input style="width:80%" class="detail_inp" type='text'  value="${TicketCheck.workTask }" /></label>
				</td>
				</tr>
				
				<tr>
				 <td colspan="1">动火计划工作时间</td>
				 <td colspan="4" >
						自<input style="width:80%" class="detail_inp plan_start_time" style="" value="${TicketCheck.planStartTime }"/><br/><br/>
						至<input style="width:80%" class="detail_inp plan_end_time" style="" value="${TicketCheck.planEndTime}" title=""/>
				</td>
				</tr>
				
				<tr>
				 <td colspan="5">
				 <label>动火区域所在单位应采取的安全措施：</label><br/>
				 <textarea  style='background-color:transparent;border:0;'>${TicketCheck.saftyAndCare}
				 </textarea>
				</tr>
				
				<tr>
				<td colspan="5">
				 <label>动火作业单位应采取的安全措施：</label><br/>
				  <textarea style='background-color:transparent;border:0;'>${TicketCheck.saftyAndCare}</textarea>
				</td>
				</tr>
				
				<tr>
				 <td style="text-align: center;width:30%">动火工作签发人</td>
				 <td rowspan="2" style="text-align:center;">审批人签章</td>
				 <td style="text-align: center;">消防部门负责人</td>
				 <td style="text-align: center;">安全监管部门负责人</td>
				 <td style="text-align: center;">厂(局)负责人</td>
				</tr>
				
				<tr>
				<td >
				 <span class="ticket_sign_uid">签发人：</span>
						<input class="detail_inp ticket_sign_uid" type='text' value="${TicketCheck.ticketSigner }"/>
						<br/>
				<span class="ticket_counter_sign_uid">会签人：</span>
						<input class="detail_inp ticket_counter_sign_uid" type='text' value="${TicketCheck.ticketCounterSigner}"/>
				</td>
				
				<td>
				   <input class="detail_inp " type='text' value="${TicketCheck.protectionStartUname}"/>
				</td>
				<td>
				   <input class="detail_inp " type='text' value="${TicketCheck.saftyStartUname }"/>
				</td>
				<td>
				   <input class="detail_inp " type='text' value="${TicketCheck.bureauStartUname}"/>
				</td>
				
			    </tr>
			    
			    <tr>
			    <td colspan="2" style="text-align:center;">
			                        动火工作票接收时间
			    </td>
			    <td colspan="3">
			     <input style="width:80%" class="detail_inp"  value="${TicketCheck.receiveTime }"/>
			    </td>
			    </tr>
			    
			    <tr>
			    <td colspan="5">
			     <label>动火区域所在单位应采取的安全措施已做完，动火作业单位应采取的安全措施已做完。</label><br/>
			     <span class ="work_permission_uid">工作许可人签名：</span><input class="detail_inp work_permission_uid" type="text" value="${TicketCheck.workLicensor}"/>&nbsp;&nbsp;
			     <span >动火工作负责人签名：</span><input class="detail_inp " type="text" value="${TicketCheck.workPrincipal }"/>&nbsp;&nbsp;
			     <span class ="permission_time">时间：</span><input class="detail_inp" type="text" value="${TicketCheck.workPermissionTime}"/>
			    </td>
			    </tr>
			    
			    <tr>
			    <td colspan="5">
			    <span>应确认消防设施和消防措施已符合要求。可燃性、易爆气体含量或粉尘浓度测定合格。</span><br/>
			    <span >动火工作监护人签名：</span><input class="detail_inp" type="text" value="${TicketCheck.workGuardianUname }"/>
			    </td>
			    </tr>
			    
			    <tr>
			    <td colspan="1">首次开火作业开工前</td>
			    <td colspan="4">
			    <span>动火工作负责人签名:</span><input class="detail_inp" type="text" value="${TicketCheck.workPrincipal }"/><br/>
			     <span>动火执行人签名:</span><input class="detail_inp" type="text" value="${TicketCheck.ticketExecuteUname }"/><br/>
			      <span>动火部门负责人签名</span><input class="detail_inp" type="text" value="${TicketCheck.workPermissionTime}"/><br/>
			       <span>消防部门负责人签名:</span><input class="detail_inp" type="text" value="${TicketCheck.protectionStartUname}"/><br/>
			        <span>安全监管部门负责人签名:</span><input class="detail_inp" type="text" value="${TicketCheck.saftyStartUname }"/><br/>
			         <span>厂（局）负责人签名:</span><input class="detail_inp" type="text" value="${TicketCheck.bureauStartUname}"/><br/>
			          <span>时间:</span><input class="detail_inp" type="text" value="${TicketCheck.workPermissionTime}"/><br/>
			    
			    
			    </td>
			    
			    <tr>
			    <td colspan="5">
			                                    动火工作于<input class="detail_inp work_end_time" value="${TicketCheck.workEndTime }"/>
			                                    结束。材料、工具已清理完毕，现场确无残留火种，参与现场动火工作的有关人员已全部撤离，动火工作已结束。<br/>
			      <span>动火工作执行人签名：</span><input class="detail_inp" type="text" value="${TicketCheck.ticketExecuteUname }"/>&nbsp;&nbsp;
			      <span>动火工作监护人签名：</span><input class="detail_inp" type="text" value="${TicketCheck.workGuardianUname }"/>&nbsp;&nbsp;
			      <span>动火工作负责人签名：</span><input class="detail_inp" type="text" value="${TicketCheck.workPrincipal }"/><br/>
			       <span>工作许可人签名：</span><input class="detail_inp" type="text" value="${TicketCheck.workLicensor}"/>
			      
			      
			      
			              
			    </td>
			    </tr>
			    <tr>
			    <td colspan="5">
					备注：<br/><br/>
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

