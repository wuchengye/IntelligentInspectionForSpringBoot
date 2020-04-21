<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/web/include/hd.jsp"%>
<style>
 	div.panel-title { 
 		text-align:center;
 	} 
 	.dialog-button { 
    	padding: 5px; 
    	text-align: center; 
    } 
</style>
<meta http-equiv=“X-UA-Compatible” content=“chrome=1″/>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		
		<div data-options="region:'north',border:false" style="height: 100%;">
		    <div id="batch_manage_dialog"></div>
		    <div id="chartToolBar" style="border-bottom: 1px #dedede solid">
		        <form action="" method="post" id="myForm">
		            <table class="1formTable"  cellpadding="0" cellspacing="0" 
				       style="width:100%;padding:0% 2%;border-collapse:separate; border-spacing:0px 5px;">
						<tr>
							<td style="padding-left:5px;">
						   日期：<input id="begin_date_batch" name="beginDate" class="easyui-datebox"   style="width:105px;" data-options="prompt:'开始日期',editable:false" required="required"/>
							~ <input id="end_date_batch" name="endDate" class="easyui-datebox" style="width:105px;" data-options="prompt:'结束日期',editable:false" required="required"/>
							<span>单位:</span>
						    <input type="text" id="data_query" class="easyui-combobox selectorMy"  autocomplete="off"
						           data-options="panelHeight:'auto',panelMaxHeight:250,panelWidth:280,editable:true,valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/assets/audio/unitName.json'"/>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-hitlsn-query" iconCls="icon-search" plain="true">查询</a>
							<a href="javascript:void(0)" class="easyui-linkbutton bda-btn-reset" iconCls="icon-remove" plain="true" >重置</a>
						</td>
						</tr>
		            </table>
		        </form>
		    </div>
		    
		    <div style="width: 100%;height: 55%;margin: 15px auto;">
		        <div style="display:inline-block;width: 47%;height:100%;margin-left:2%;border: 1px #dedede solid;">
				    <div id="bar_1" style="height: 100%;"></div>
	            </div>
	            <div style="display:inline-block;width: 47%;height:100%;margin-left:20px;border: 1px #dedede solid;">
				    <div id="bar_2" style="height: 100%;"></div>
	            </div>
		    </div>
		    
			<div style="width: 100%;height: 55%;margin: 15px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:2%;border: 1px #dedede solid; ">
				    <div id="bar_3" style="height: 100%;width: 100%;"></div>
			    </div>
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_4" style="height: 100%;width: 100%;"></div>
			    </div>
			</div>
			<div style="width: 100%;height: 55%;margin: 15px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:2%;border: 1px #dedede solid; ">
				    <div id="bar_5" style="height: 100%;width: 100%;"></div>
			    </div>
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_6" style="height: 100%;width: 100%;"></div>
			    </div>
			</div>
			<div style="width: 100%;height: 55%;margin: 15px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:2%;border: 1px #dedede solid; ">
				    <div id="bar_7" style="height: 100%;width: 100%;"></div>
			    </div>
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_8" style="height: 100%;width: 100%;"></div>
			    </div>
			</div>
			<div style="width: 100%;height: 55%;margin: 15px auto;">
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:2%;border: 1px #dedede solid; ">
				    <div id="bar_9" style="height: 100%;width: 100%;"></div>
			    </div>
			    <div style="height: 100%;width:47% ;display: inline-block;margin-left:20px;border: 1px #dedede solid; ">
				    <div id="bar_10" style="height: 100%;width: 100%;"></div>
			    </div>
			</div>
		</div>
		
    </div>
	<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/ticketAmountStatistics/ticketTypeCharts.js' ], function(t) {
			t.init();
		});

	

		
		 $('#begin_date_batch,#end_date_batch').datebox({
             onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                 span.trigger('click'); //触发click事件弹出月份层
                 span1.trigger('click');
                 if (!tds)
                     setTimeout(function() { //延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                         tds = p.find('div.calendar-menu-month-inner td');
                         tds1 = p1.find('div.calendar-menu-month-inner td');
                         tds.click(function(e) {
                             e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                             var year = /\d{4}/.exec(span.html())[0] //得到年份
                                 ,month = parseInt($(this).attr('abbr'), 10); //月份 之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1; 
                             $('#begin_date_batch').datebox('hidePanel') //隐藏日期对象
                                 .datebox('setValue', year + '-' + month); //设置日期的值
                         });
                         tds1.click(function(e) {
                             e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                             var year = /\d{4}/.exec(span1.html())[0] //得到年份
                                 ,month = parseInt($(this).attr('abbr'), 10); //月份 之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1; 
                             $('#end_date_batch').datebox('hidePanel') //隐藏日期对象
                                 .datebox('setValue', year + '-' + month); //设置日期的值
                         });
                     }, 0);
             },
             parser: function (s) {//配置parser，返回选择的日期
                 if (!s) return new Date();
                 var arr = s.split('-');
                 return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
             },
             formatter: function (d) { 
            	 var currentMonth = (d.getMonth()+1);
                 var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
                 return d.getFullYear() + '-' + currentMonthStr;  
             }
         });

		var p = $('#begin_date_batch').datebox('panel'); //日期选择对象
		var p1 = $('#end_date_batch').datebox('panel'); //日期选择对象
		tds = false, //日期选择对象中月份
		span1 = p1.find('span.calendar-text'); //显示月份层的触发控件
		span = p.find('span.calendar-text'); //显示月份层的触发控件
		//var curr_time = new Date();
		//$("#begin_date_batch,#end_date_batch").datebox("setValue", myformatter(curr_time));//设置前当月
		

		//格式化日期
		function myformatter(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1+"";
			console.log(m.length);
			if(m.length<2){
				m="0"+m;
			}
			console.log(m);
			var ss=y + '-' + m
			return ss;
		}
		
	})
	</script>

</body>
<%@ include file="/web/include/ft.jsp"%>