<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
 	.rightText{
 	    text-align: right;
 	}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		
		<div data-options="region:'north',border:false" style="height: 100%;">
		    <table id="data_grid"></table>
		    <div id="myDialog"></div>
		    <div id="myToolBar">
		        <form action="" method="post" id="myForm">
		            <table class="formTable"  cellpadding="0" cellspacing="0" 
				       style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
				        <tr>
				            <td>工作票票号：</td>
						    <td class="leftItem">
						        <input id="ticketNo" name="ticketNo" style="width:146px">
						    </td>
				            <td>工作票种类：</td>
				            <td class="leftItem">
				                <input id="ticketType" name="ticketType" style="width:150px;">
						        <!-- <input class="easyui-combobox" id="ticketType" name="ticketType" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getTicketType'"> -->
						    </td>
			                <td>单位与班组：</td>
						    <td class="leftItem">
						        <input name="department" style="width: 150px" id="department">
						        <!-- <input class="easyui-combobox" id="department" name="department" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,
						                         valueField:'value',textField:'text',url:'electric/ticketCheckRes/getClassCombobox'"> -->
						    </td>
					    </tr>
					    <tr>
						    <td>工作负责人：</td>
						    <td class="leftItem">
						        <input  id="workPrincipal" name="workPrincipal" style="width:146px;">
						    <!-- 
						        <input class="easyui-combobox" id="workPrincipal" name="workPrincipal" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getPersonName',queryParams:{type:'负责人'}">
						    -->
						    </td>
			                <td>工作票签发人：</td>
						    <td class="leftItem">
						        <input  id="ticketSigner" name="ticketSigner" style="width:146px;">
						    <!--  
						        <input class="easyui-combobox" id="ticketSigner" name="ticketSigner" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getPersonName',queryParams:{type:'签发人'}">
						    -->
						    </td>
						    <td>工作许可人：</td>
						    <td class="leftItem">
						       	<input  id="workLicensor" name="workLicensor" style="width:146px;">
						    <!-- 
						        <input class="easyui-combobox" id="workLicensor" name="workLicensor" style="width:150px;"
						           data-options="panelHeight:'auto',panelMaxHeight:250,editable:true,valueField:'value',textField:'text',url:'electric/ticketCheckRes/getPersonName',queryParams:{type:'许可人'}">
						    -->
						    </td>
						    <!-- <td>归档时间：</td>
						    <td class="leftItem">
						        <input class="easyui-textbox" name="workLicensor" style="width: 150px">
						    </td>
						    <td>~：</td>
						    <td class="leftItem">
						        <input class="easyui-textbox" name="workLicensor" style="width: 150px">
						    </td>
						    <td>任务类别：</td>
						    <td class="leftItem">
						        <select id="checkType" name="checkType" style="width: 150px"></select>
						    </td> -->
					    </tr>
					    <tr>
					        <td colspan="6" style="text-align: center;">
					            <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-query" iconCls="icon-search" plain="true" >查询</a>
					            <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-reset" iconCls="icon-remove" plain="true" >重置</a>
					        </td>
					    </tr>
		            </table>
		        </form>
		    </div>
		</div>
		
		<!-- <div data-options="region:'south',border:false" style="height: 70%;">
		    <div style="display:block;width: 95%;height: 47%;float: left;padding-top: 20px">
				<div id="pie" style="display:inline-block;width: 50%;height:90%;float: left;">
				    <div id="pie"></div>
	            </div>
				<div style="display:inline-block;width: 50%;height:90%;float: left;">
				    <div id="bar_1" style="height: 100%"></div>
	            </div>
            </div>
            <div style="clear: both;"></div>
			<div style="width: 95%;height: 47%;margin: 15px auto;">
			    <div id="bar_2" style="height: 90%"></div>
			</div>
		</div> -->
		
    </div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/ticketCheckResult.js?v=20190701' ], function(t) {
			t.init();
		});		
		
		seajs.use([ 'module/commonUtil/initdate.js' ], function(initDate) {
			initDate.initDate();
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