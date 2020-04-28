<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<style>
 	div.panel-title { 
 		text-align:center;
 	} 	
 	table{
 		border-collapse: collapse;
 		width:90%;
	}
	#pageForm table tr td{
 		border:1px solid #000; 
 		padding:5px;
	}
	input{
  		font-family: -webkit-pictograph;
	    font-weight: 600;
	    border: 0px;
   		border-bottom: 1px solid #444;
   		background-color:transparent;
	}
	input:focus{
	    outline: none;
	}
	textarea{
  		font-family: -webkit-pictograph;
	    color: #000;
	    font-weight: 600;
	    width:90%;
	    resize: none;
	    height:29px;
	}
	textarea:focus{
	    outline: none;
	}
	h2{
		font-weight: inherit;
	}
	td{
		font-size:14px;
	}

	input::-webkit-calendar-picker-indicator{
	display: none;
	-webkit-appearance: none;

	}
	#dateCls{
		height:250px;
	}
	.code{
	    color:red;
	}
	.textbox{
		border: 0;
		background-color:transparent;
	}
	.panel-body-noheader{
		height:250px;
	}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;border:none;">
		<div data-options="region:'center'" style="height: 100%;margin-left:70px;border:none;">
			<form id="pageForm" action="">
			<div style ="text-align:center;margin-right:11%;margin-bottom:5px">
			<div id="news_bar" style='text-align:right;margin-top:5px;margin-right:4px;'>
				<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-genarate" iconCls="icon-ok" >生成</a>
				<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-save" iconCls="icon-save" >保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-reset" iconCls="icon-cancel" >撤销</a>
			</div>
				<h2>
				<span class="code">*</span><span>广州供电局</span>
				<span style='position:relative;'>
						<!-- <input type='text' id='stname' onclick='test()'  list='cars' style='font-size:20px;font-weight: normal;' onblur='noTest()' /> -->
						<input class="easyui-combobox" name="title"
						       data-options="required:'true',panelHeight:'auto',panelMaxHeight:220,editable:true,valueField:'value',textField:'text'"
						       validType="checkValue['#stname']"
							   id='stname' style='font-size:20px;font-weight: normal;' />
						<span class='slt' id='slt' tabindex='-1'>
						</span>
					</span>第一种工作票
				</h2>
				<div style='display: inline-block;width: 100%'><span style="float: left;">(<span class="code">*</span>为必填项)</span><span style="float: right;"><span class="code">*</span>编号：<input class='center easyui-validatebox' name="serialNo" required="required" autocomplete="off" /></span>&nbsp;&nbsp;</div>
			</div>
			<table>
			<tr>
				<td>
					<label><span class="code">*</span>工作负责人（监护人）：<input class="easyui-validatebox" type='text' name="charger" required="required" autocomplete="off" style="width: 70%;" /></label>	
					<br/><br/>
					<label><span class="code">*</span>单位和班组：<input class="easyui-validatebox" type='text' name="unit" style='width:360px;' required="required" autocomplete="off" /></label>
					<br/><br/>
					<label><span class="code">*</span>工作负责人及工作班人员总人数共 <input class='center easyui-numberbox' type='text' name="totalNum" style='width:50px;' required="required"/> 人</label>
				</td>
				<td><span class="code">*</span>计划工作时间</td>
				<td class='dateCls'>
					<input id="planStartTime" name="planStartTime" class="easyui-datetimebox" showSeconds='false' style="width:120px;" data-options="prompt:'开始日期',editable:false,required:'true'"/>
					~ <input id="planEndTime" name="planEndTime" class="easyui-datetimebox"  showSeconds='false' style="width:120px;" data-options="prompt:'结束日期',editable:false,required:'true'"/>
				</td>
			</tr>
			<tr>
				<td colspan="3"><span class="code">*</span>工作班人员（不包括工作负责人）：
					<input class="easyui-validatebox" type = 'text' name="worker" style='width:60%;height:35px;border:0;' required="required" autocomplete="off"/>
				</td>
			</tr>
			<tr>
				<td colspan="3"><span class="code">*</span>工作任务：
					<input type = 'text' id="task" name="task" style='width:80%;height:35px;border:0;' 
					       required="required" validType="checkValue['#task']" autocomplete="off"/>
				</td>

			</tr>
			<tr>
				<td colspan="3">&nbsp;工作地点：
					<input type = 'text' class="easyui-textbox" id="workPlace" name="workPlace" required="required" style='width:70%;height:35px;border:0;'/>
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
					<span>断路器（开关）：</span>
					<textarea class="easyui-validatebox" name="breaker" id='roadiBreak' required="required" style='background-color:transparent;border:0;'></textarea>
				</td>
				<td>隔离开关（刀闸）：<textarea class="easyui-validatebox" name="disconecter"  id='disconecterIsBreak' required="required" style='background-color:transparent;border:0;'></textarea>
					
				</td>
			</tr>
			<tr>
				<td colspan="2" class='noTopBorder' >应投切的相关直流电源（空气开关、熔断器、连接片）、低压及二次回路：
				<br/><textarea class="easyui-validatebox" name="content_1" id="content_1" style='height:49px;border:0;background-color:transparent;' required="required"></textarea>
			</tr>
			<tr>
				<td colspan="2">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板：
				<br/><textarea class="easyui-validatebox" name="content_2" id="content_2" style='height:39px;background-color:transparent;border:0;' required="required" ></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">应设遮拦、应挂标识牌（注明位置）：
				<br/><textarea class="easyui-validatebox" name="needSetSign" id="needSetSign" style='height:49px;background-color:transparent;border:0;' required="required" ></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">是否需线路对侧接地：
				    <input class='center' type="radio" id='text3' name="isLanding" value="1" style='width:50px;' />是
				    <input class='center' type="radio" id='text3' name="isLanding" value="0" style='width:50px;' checked="checked"/>否
				</td>
			</tr>
			<tr>
				<td colspan="2">是否需办理二次设备及回路工作安全技术措施单：
					<input class='center' type="radio" id='needSecondProcess' name="needSecondProcess" value="1"/>是，共
					<input class='center easyui-numberbox' type="text" id='measureOrder' name="measureOrder" style='width:39px;' 
					       data-options="disabled:'true'"/>张。
					<input class='center' type="radio" id='needSecondProcess' name="needSecondProcess" value="0" checked="checked"/>否
				</td>
			</tr>
			<tr>
				<td colspan="2">其他安全措施和注意事项：
					<textarea class="easyui-validatebox" name="otherAttention" id='text6' required="required" style='background-color:transparent;border:0;'>无。</textarea>
				</td>
			</tr>
			
		</table>
		
		<table>
			<tr>
				<td class='noTopBorder td1'>签发</td>
				<td class='noTopBorder'>
					工作票签发人签名：<input type='text' />
					<span class='dateCls float-r'>时间：<input id="begin_date_batch" name="beginDate" class="easyui-datetimebox" showSeconds='false' style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datetimebox" showSeconds='false' style="width:105px;" data-options="prompt:'结束日期'"/>
					</span>
					<br/><br/>工作票会签人签名：<input type='text' />
					<span class='dateCls float-r'>时间：<input id="begin_date_batch" name="beginDate" class="easyui-datetimebox" showSeconds='false' style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datetimebox" showSeconds='false' style="width:105px;" data-options="prompt:'结束日期'"/>
					</span>
				</td>
			</tr>
			
			<tr>
				<td class='td1'>接收</td>
				<td>
					值班负责人签名：<input type='text' style="margin-left:13px"/>
					<span class='dateCls float-r'>时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
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
					安全措施是否满足工作要求： <input type="radio" id='text3' checked="checked" name="roundNew" class='center' style='width:50px;'/>是<input type="radio" id='text3' name="roundNew" class='center' style='width:50px;'/>否
					<br/>需补充或调整的安全措施：<input type='text' style='width:50%' />
					<br/>是否需以收触试：<input type="radio" id='text3' checked="checked" name="roundOld" class='center' style='width:50px;'/>是<input type="radio" id='text3' name="roundOld" class='center' style='width:50px;'/>否
					<br/>以手触试的具体部位：<input type='text' style='width:50%' />
				</td>
			</tr>
			<tr><td colspan="2">
				线路对侧安全措施：经值班调度员（配电网运维人员）（姓名）<input type='text' />
				<br/>确认线路对侧已按要求执行。
				</td>	
			</tr>
			<tr>
				<td style="width:160px;">工作地点保留的带点部位</td>
				<td>
					带电的母线、导线：<input type='text' />
					<br/><br/>带电的隔离开关（刀闸）：<input type='text' />
					<br/><br/>其他：<input type='text' />
					<br/><br/>其他安全注意事项：<input type='text' />
					<br/><br/>工作许可人签名：<input type='text' /> 
					<br/><br/>工作负责人签名：<input type='text' /> 
					<br/><br/>时间：<span class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
						</span>
				</td>
			</tr>
			
		</table>
		
		<table>
			<tr>
				<td class='noTopBorder'>指定<input type='text' />为专责监护人 &nbsp;&nbsp;&nbsp;&nbsp;<span style="margin-left:100px">专责监护人签名：</span><input type='text' /></td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td class='noTopBorder td1'>安全交代</td>
				<td class='noTopBorder'>工作班人员确认工作负责人所交代布置的工作任务、安全措施和作业安全注意事项。
					<br/><br/>工作班人员签名：<input type='text' />
					<br/><br/>时间：<span class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
							 </span>
				</td>
			</tr>
		</table>
		
		<table>
			<tr><td rowspan="5" class='noTopBorder td1' style='width:65px'>工作间断</td>
			</tr>
			<tr class='noTopBorder'>
				<td>工作间断时间</td>
				<td>工作许可人</td>
				<td>工作负责人</td>
				<td>工作开工时间</td>
				<td>工作许可人</td>
				<td>工作负责人</td>
			</tr>
			<tr>
				<td class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
				<td><input type='text' style='width:80px'/></td>
				<td><input type='text' style='width:80px'/></td>
				<td class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
				<td><input type='text' style='width:80px'/></td>
				<td><input type='text' style='width:80px'/></td>
			</tr>
			<tr>
				<td class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
				<td><input type='text' style='width:80px'/></td>
				<td><input type='text' style='width:80px'/></td>
				<td class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
				<td><input type='text' style='width:80px'/></td>
				<td><input type='text' style='width:80px'/></td>
			</tr>
			<tr>
				<td class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
				<td><input type='text' style='width:80px'/></td>
				<td><input type='text' style='width:80px'/></td>
				<td class='dateCls'><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
				<td><input type='text' style='width:80px'/></td>
				<td><input type='text' style='width:80px'/></td>
			</tr>
		</table>
		<table>
			<tr><td rowspan="8" class='noTopBorder td1'>工作变更</td></tr>
			<tr><td class='noTopBorder'>工作任务</td>
				<td colspan="4" class='noTopBorder'>
					不需变更安全措施下增加的工作内容：<input type='text' />
					<br/><br/>工作负责人签名：<input type='text' /> 工作许可人签名：<input type='text' />
					<br/><br/>工作票签发人签名：<input type='text' /> 时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
				</td>
			</tr>
			<tr><td>工作负责人</td>
				<td colspan="4">
					工作票签发人签名：<input type='text' /> 原工作负责人签名：<input type='text' />
					<br/><br/>现工作负责人签名：<input type='text' /> 
					<br/><br/>工作许可人签名：<input type='text' /> 时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
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
				<td><input type='text' /></td>
				<td><input type='text' /></td>
				<td><input type='text' /></td>
				<td><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
			</tr>
			<tr>
				<td><input type='text' /></td>
				<td><input type='text' /></td>
				<td><input type='text' /></td>
				<td><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
			</tr>
			<tr>
				<td><input type='text' /></td>
				<td><input type='text' /></td>
				<td><input type='text' /></td>
				<td><input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/></td>
			</tr>
			
		</table>
		
		<table>
			<tr>
				<td class='noTopBorder'>工作延期</td>
				<td class='noTopBorder'>
					有效期延长到：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
					<br/><br/>工作许可人签名：<input type='text' /> 工作负责人签名：<input type='text' />
					<br/><br/>时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td rowspan="4" class='noTopBorder td1' style='width:98px'>工作票的终结</td>
			</tr>
			<tr>
				<td class='noTopBorder'>作业终结</td>
				<td class='noTopBorder'>  全部作业于<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>结束，检修临时安全措施已拆除，已恢复作业开始前状态，作业人员已全部撤离，材料工具已清理完毕。
					<br/><br/>工作负责人签名：<input type='text' />工作许可人签名：<input type='text' />_
					<br/><br/>时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
				</td>
			</tr>
			<tr>
				<td style='width:100px'>许可人措施终结</td>
				<td>临时遮拦已拆除，标示牌已去下，常设遮拦已恢复等。
					<br/>工作许可人签名：<input type='text' /> 时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
			</tr>
			<tr>
				<td>汇报调度</td>
				<td>未拉开接地刀闸双重名称或编号： <input type='text' />
					<br/>共<input type='text' />把
					<br/><br/>未拆除接地线装设地点及编号：<input type='text' />
					<br/>共<input type='text' />把
					<br/><br/>值班负责人签名：<input type='text' />_ 值班调度员（姓名）：<input type='text' />
					<br/><br/>时间：<input id="begin_date_batch" name="beginDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'开始日期'"/>
					~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期'"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">备注（工作转移、安全交代补充签名等）：<input type='text' /></td>
			</tr>
		</table>
		</form>
		</div>
	</div>	

<script type="text/javascript">
	$(function() {
		
		seajs.use([ 'module/electric/workTicket.js?v=20190308' ], function(kwa) {
			kwa.init();
		});		
	});
	
	function task(){
		var element = $("#workTask").val();
		var elementOld = $("#stname").val();
		var str = Trim(element,"g");
		//var city = elementOld.substring(5,7);
		if(elementOld=='110kV九佛站'){
			if(str!='10kV#1A电容器检修'){
				alert("输入的内容有误");
			}
		}else if(elementOld=='220kV碧山站'){
			if(str!='10kV#1AA电容器检修'){
				alert("输入的内容有误");
			}
		}else if(elementOld=='500kV增城站'){
			if(str!='35kV#1变低#1电容器检修'){
				alert("输入的内容有误");
			}
		}else if(elementOld=='500kV木棉站'){
			if(str!='35kV1M母线#1电容器组检修'){
				alert("输入的内容有误");
			}
		}
	}
	 function Trim(str,is_global){
	   var result;
	   result = str.replace(/(^\s+)|(\s+$)/g,"");
	   if(is_global.toLowerCase()=="g"){
	    	result = result.replace(/\s/g,"");
	   }
	   return result;
	}
</script>

</body>
<%@ include file="/web/include/ft.jsp"%>