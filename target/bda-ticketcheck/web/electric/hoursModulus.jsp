<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<!-- 工作负责人工时统计系数 -->
<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="hours_modulu_grid"></table>
			<div id="hours_modulu_win"></div>
			<div id="hours_modulu_bar">
				<table cellpadding="0" cellspacing="0" style="width:100%">
						<tr>
							<td style="padding-left:5px;">
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-update" iconCls="icon-edit" plain="true">修改</a>
							</td>
						</tr>
				</table>
			</div>
			
		    <div id="modify_dialog" style=" padding: 10px 10px; display: none;">
  					<form id="modify_form" action="#" method="post" >
	  					<table width="100%"  cellpadding="5px" cellspacing="0px">
							<tr style="display:none"><td align="right">系数表id</td><td><input type="text" id="configId" name="configId" ></td></tr>
							<tr><td align="right">资格人员类型</td><td><input class="wid" type="text"name='type' id='type' disabled="disabled"></td></tr>
							<tr><td align="right">带电作业工作票系数</td><td><input class="Wdate" type="text"name='liveWorking' id='liveWorking'></td></tr>
							<tr><td align="right">低压配网工作票系数</td><td><input class="wid"  type='text' name='lowVoltage' id='lowVoltage'></td></tr>
							<tr><td align="right">紧急抢修工作票系数</td><td><input class="wid" type='text' name='urgentRepairs' id='urgentRepairs'></td></tr>
							<tr><td align="right">书面形式布置及记录系数</td><td><input class="wid" type='text' name='writtenForm' id='writtenForm'></td></tr>
							<tr><td align="right">厂站第一种工作票系数</td><td><input type='text'  class="wid"  name='stationOne' id='stationOne'></td></tr>
							<tr><td align="right">厂站第二种工作票系数</td><td><input type='text'  class="wid"  name='stationTwo' id='stationTwo'></td></tr>
							<tr><td align="right">厂站第三种工作票系数</td><td><input type='text'  class="wid"  name='stationThree' id='stationThree'></td></tr>
							<tr><td align="right">线路第一种工作票系数</td><td><input   class="wid" type='text' name='lineOne' id='lineOne'></td></tr>
							<tr><td align="right">线路第二种工作票系数</td><td><input  class="wid" type='text' name='lineTwo' id='lineTwo'></td></tr>
							<tr><td align="right">动火第一种工作票系数</td><td><input  class="wid" type='text' name='fireOne' id='fireOne'></td></tr>
							<tr><td align="right">动火第二种工作票系数</td><td><input  class="wid" type='text' name='fireTwo' id='fireTwo'></td></tr>
							<tr><td align="right">特殊工作票系数</td><td><input  class="wid" type='text' name='special' id='special'></td></tr>
	   					</table>
    				</form>
   				<div style="height: 10px"></div>
   			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/hoursModulus.js?v=20190529' ], function(kwm) {
				kwm.init();
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