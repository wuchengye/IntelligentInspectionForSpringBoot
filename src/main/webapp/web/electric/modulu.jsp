<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>

<body style="text-align: center;">
	    <div data-options="region:'center'" style="height: 100%;">
			<table id="modulu_grid"></table>
			<div id="modulu_win"></div>
			<div id="modulu_bar">
			<table cellpadding="0" cellspacing="0" style="width:100%">
					<tr>
						<td style="padding-left:5px;">
							<a href="javascript:void(0)" style="" class="easyui-linkbutton bda-btn-update" iconCls="icon-edit" >修改</a>
						</td>
					</tr>
			</table>
			</div>
			
			    <div id="modify_panel" style=" padding: 10px 10px; display: none;">
   					<table width="100%"  cellpadding="5px" cellspacing="0px">
   						<form id="modify_form" action="#" method="post" >
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
       					</form>
    				</table>
    				<div style="height: 10px"></div>
    			</div>
		</div>
	<script>
		$(function(){
			seajs.use([ 'module/electric/score.js' ], function(kwm) {
				kwm.modulu();
			});	
			//$(".easyui-linkbutton").css("backgroud-color","red");
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