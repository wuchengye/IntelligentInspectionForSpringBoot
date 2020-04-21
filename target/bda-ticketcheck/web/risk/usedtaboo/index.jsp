<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<link type="text/css" rel="stylesheet" href="assets/css/dict/importTemplate.css" />
<!-- risk/usedtaboo 使用服务禁语-->
<style type="text/css">
.layout-split-west {
	border-right: 1px solid #3a82bd;
}
td	{
	white-space: nowrap;
}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
  	    <%-- <div data-options="region:'west',title:'批次选择',collapsible:true,collapsed:false,split:true,border:false,
   	    		collapsedContent:'<b>点<br/>击<br/>可<br/>选<br/>择<br/>批<br/>次<br/>！</b>',
   	    		hideCollapsedContent:false" style="height: 100%; width: 16%;">
 	    	<div id="batch_grid" style="height: 100%; width: 100%;">
	            <table class="list_grid" class='easyui-datagrid' ></table>
	        </div>
	    </div> --%>
	    <div data-options="region:'center',title:'查询会话',border:false" style="height: 100%; width: 100%;">
	    	<table id="business_grid"></table>
			<div id="business_edit_window"></div>
			<div id="business_grid_bar">
				<form  method="POST" id="business_form">	
				<input type="hidden" name="sessionBatchId" id="sessionBatchId" >
				<input type="hidden" id="yearmonth" name="yearmonth" />
				<table class="formTable" cellpadding="0" cellspacing="0" style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
						<tr>
							<td>录音日期：</td>
							<td class="leftItem">
								<input id="recordStartDate" name="recordStartDate" class="easyui-datebox" style="width:150px" data-options="required:true,editable:false">
							</td>
							<td>~到：</td>
							<td class="leftItem">
								<input id="recordEndDate" name="recordEndDate" class="easyui-datebox" style="width:150px" data-options="required:true,editable:false">
							</td>
                            <td>录音文件名：</td>
                            <td class="leftItem">
                            	<input name="fileName" type="text" style="width:150px;" class="easyui-textbox" data-options="validType:'length[1,240]',prompt:'（可模糊查询）'"/>
                            </td>
                            
						</tr>
						<tr>
							<td>复核状态：</td>
							<td class="leftItem">
								<select name="checkStatus" class="easyui-combobox" style="width:150px;" data-options="panelHeight:'auto',editable:false">
	                                <option value="">--全部--</option>
	                                <option value="未复核">未复核</option>
	                                <option value="已复核">已复核</option>
								</select>
							</td>
							<td>人员是否有问题：</td>
							<td class="leftItem">
								<select name="personIsProblem" class="easyui-combobox" style="width:150px;" data-options="panelHeight:'auto',editable:false">
	                                <option value="">--全部--</option>
	                                <option value="是">是</option>
	                                <option value="否">否</option>
								</select>
							</td>
						</tr>
						
						<tr>
							<td style="text-align: center;" colspan="6">
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-start" iconCls="icon-search" plain="true" >查看可疑数据</a>
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-del" iconCls="icon-remove" plain="true" >清空条件</a>
								<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-exp" iconCls="icon-save" plain="true">导出可疑结果</a>
                                    <!-- <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-expnum" iconCls="icon-add" plain="true">导出号码包</a> -->
							</td>
						</tr>
					</table>
				</form>
			</div>
	    </div>
	</div>
<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/risk/hotlineintaboo.js' ], function(hotlineintaboo) {
			hotlineintaboo.init();
		});		
		seajs.use([ 'module/commonUtil/initdate.js' ], function(initDate) {
			initDate.initDate();
		});
	});
</script>
</body>
<%@ include file="/web/include/ft.jsp"%>