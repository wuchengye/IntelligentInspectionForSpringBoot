

define(function(require, exports, module) {
	var base = require("module/base/main.js"); // 基本页面工具调用
	var extend = require("module/base/extend.js")
	var utils = require("module/base/utils.js"); // 工具
	
//	var getwrongwords = require("module/commonUtil/getwrongwords.js");
	
	var moduleWebRootUrl = 'web/electric/';//模块界面根目录
	var moduleRequestRootUrl = 'electric/';//模块界面根目录
	
	var saveTicketUrl = moduleRequestRootUrl + "workticket/saveTicket"
	var genarateUrl = moduleRequestRootUrl + "workticket/genarate";
	var width='500px';
	var height='400px';
	
	var WorkTicket = function(){
		this.width = '655px';
		this.height = '350px';
		this.$form;
		
	}
	
	WorkTicket.prototype = {
		constructor: WorkTicket,
		
		init:function(){
			var self = this;
			self.$form = $("#pageForm");
			self.initEvent();
//			var wrongwords = getwrongwords.getWrongWords();
//			console.log(wrongwords);
		},
	
		
		initEvent:function(){
			var self = this;
			
			var data = [
			    {text:"110kV九佛站",value:"110kV九佛站"},
			    {text:"220kV碧山站",value:"220kV碧山站"},
			    {text:"500kV增城站",value:"500kV增城站"},
			    {text:"500kV木棉站",value:"500kV木棉站"}
			];
			
			var $task = $("input[name='task']");
			$("#stname").combobox({
				url:'electric/workticket/getTitles',
				limitToList:true,
				//data:data,
				onSelect:function(val){//点击选择  or 填写完整 可触发
					//self.resetFunc();
					
					//alert(val)
//					$task.combobox({
//						url:'electric/workticket/getTaskByTitle',
//						queryParams:{title:val.value},
//						panelHeight:'auto',
//						panelMaxHeight:250,
//						editable:true,
//						valueField:'value',
//						textField:'text',
//						limitToList:true,
//						hasDownArrow:false,//是否显示向下箭头按钮
						/*onSelect:function(val){
							$(this).combobox("setValue",val.value)
						}*/
//					})
					
				}
			})
			
			$task.bind("blur",function(){
				var task = $(this).val().replace(/\s*/g,"");
				var isTask = false;
				if(task == ""){
					
				}else if(task != "") { 
					isTask = /(d{1,3}kV)?(.*)\d*(开关B修|开关检修|电容器组检修|PT检修|CT检修|开关防拒动检查|开关投产前试验|主变本体检修|保护及(测控|测控装置)定检|保护定检)$/i.test(task)?true:false;
					if(!isTask){
						$.messager.alert("提示","请正确填写工作任务再进行生成！","info");
						return false;
					}
					$(this).val(task);
				}
			});
			
			//点击生成按钮事件
			$('#news_bar').on('click','.bda-btn-hitlsn-genarate',function(){
				var stname = $("#stname").combobox("getValue");//当前填写的站名

				var task = $task.val().replace(/\s*/g,"");//当前填写的工作任务（去除空白字符）
				var stnames = $("#stname").combobox("getData");//所有站名
				
				var isStname = false;
				if(stname == ""){
					$.messager.alert("提示","站名不能为空！","info");
					return false;
				}else{ 
					for(var i = 0; i < stnames.length; i++){
						if(stname == stnames[i].text){
							isStname = true;
							break;
						}
					}
					if(!isStname){
						$.messager.alert("提示","请选择正确站名再进行生成！","info");
						return false;
					}
				}
				
				var isTask = false;
				if(task == ""){
					$.messager.alert("提示","工作任务不能为空！","info");
					return false;
				}else if(task != "") { 
					isTask = /(\d{1,3}kV)?(.*)\d*(开关B修|开关检修|电容器组检修|PT检修|CT检修|开关防拒动检查|开关投产前试验|主变本体检修|保护及(测控|测控装置)定检|保护定检)$/i.test(task)?true:false;
					if(!isTask){
						$.messager.alert("提示","请正确填写工作任务再进行生成！","info");
						return false;
					}
				}
				$.messager.confirm('请确认','确定填充工作地点和工作要求的安全措施内容吗？',function(r){
					    if (r){
							parent.$.messager.progress({
								title : '提示',
								text : '生成中，请稍后....'
							});
							
							$.ajax({
								url:genarateUrl,
								type:"post",
								data:{title:stname,task:task},
								cache:false,
								dataType:"json",
								success:function(resp){
									parent.$.messager.progress('close');
									if(resp.meta.success){
										if(resp.meta.code != "OK"){
											$.messager.alert('提示',resp.meta.code,'warning',function(){
												//self.resetFunc();
											});
											return false;
										}
										
										if(task.indexOf("定检") > -1 ){
											task = "";
										}
										$('#workPlace').textbox("setValue",
												task.replace("检修","位置").replace("B修","位置").replace("投产前试验","位置"));
										var data = resp.data;
										
										var roadiBreak;//断路器
										var disconecterIsBreak = "";//隔离开关
										var content_1;
										var content_2;
										var needSetSign;
										
										roadiBreak = data[1].brks;
										var temp = data[1].dis.split("、");
										if(temp.length > 0 ){
											var _t = "";
											for(var i = 0; i < temp.length;i++ ){
												if(temp[i].indexOf("DZ") > -1 ){
													temp[i] = temp[i].replace("DZ","");
													if(roadiBreak.indexOf(temp[i]) > -1){
														_t.length > 0 ? _t += "、" + temp[i] : _t += temp[i];
													}
													if(i < temp.length -1){
														continue;
													}
												}
												if(i ==  temp.length -1 && _t.length > 0 ){
													disconecterIsBreak.length > 0 ? disconecterIsBreak += ("、将" + _t + "开关小车拉至试验位置") : disconecterIsBreak += ("将" + _t + "开关小车拉至试验位置") ;
												}else{
													disconecterIsBreak.length > 0 ? disconecterIsBreak += ("、" + temp[i]) : disconecterIsBreak += temp[i] ;
												}
											}
										}
										content_1 = data[0].CONTENT_1;
										content_2 = data[0].CONTENT_2;
										needSetSign = data[0].NEED_SET_SIGN;
										
										$('#roadiBreak').val(roadiBreak);
										$('#disconecterIsBreak').val(disconecterIsBreak);
										$('#content_1').val(content_1);
										$('#content_2').val(content_2);
										$('#needSetSign').val(needSetSign);
										$.messager.alert('提示','生成成功！','info');
									}else{
										$.messager.alert('提示','生成失败！','info');
									}
								}
							})
					    }
					});
			})
			//点击保存按钮事件
			.on('click','.bda-btn-hitlsn-save',function(){
				//console.log(self.getFormData());
				var beginTime = $('#planStartTime').datetimebox('getValue');
				var endTime = $('#planEndTime').datetimebox('getValue');
				if(beginTime>endTime){
					$.messager.alert('错误','开始时间不能大于结束时间！','error');
					return false;
				}
				if(!self.$form.form("validate")){
					$.messager.alert('错误','请正确填写必填项！','error');
					return false;
				}
				parent.$.messager.progress({
					title : '提示',
					text : '保存中，请稍后....'
				});
				$.ajax({
					url:saveTicketUrl,
					type:"post",
					data:self.getFormData(),
					cache:false,
					dataType:"json",
					success:function(resp){
						parent.$.messager.progress('close');
						if(resp.meta.success){
							$.messager.alert('提示','保存成功！','info');
						}else{
							$.messager.alert('提示','保存失败！','info');
						}
					}
				})
				
			})
		    //点击撤销按钮事件
		    .on('click','.bda-btn-hitlsn-reset',function(){
		    	$.messager.confirm("提示","确定是否撤销？",function(r){
		    		if(r){
		    			$("#pageForm").form("reset");
		    		}
		    	})
		    });

			//监听 “否需办理二次设备及回路工作安全技术措施单” 点击事件
			$("input[name='needSecondProcess']").on("click",function(){
				if($(this).val() == "1"){//如果是（1），则为必填
					$("#measureOrder").numberbox('enable');
				}else{//为否（0）则禁用
					//$("#measureOrder").numberbox('clear');
					$("#measureOrder").numberbox('disable');//禁用后不会传到后台
				}
			})
			
			//校验日期
			$("#planStartTime").datetimebox({
				onSelect:function(date){
//					var beginTime= date.getFullYear()+":"+(date.getMonth()+1)+":"+date.getDate()+' '+date.getHours()+":"+date.getMinutes();
//					var beginTime = $('#planStartTime').datetimebox('getValue');
//					var endTime = $('#planEndTime').datetimebox('getValue')
//					if(beginTime>endTime){
//						alert("开始时间不能大于结束时间")
//						return false;
//					}
				}
			})
			$("#planEndTime").datetimebox({
				onSelect:function(date){
//					var beginTime = $('#planStartTime').datetimebox('getValue');
//					var endTime= date.getFullYear()+":"+(date.getMonth()+1)+":"+date.getDate()+' '+date.getHours()+":"+date.getMinutes();
//					if(endTime<beginTime){
//						alert("开始时间不能大于结束时间")
//						return false;
//					}
				}
			})
			
			//校验拓展
			$.extend($.fn.validatebox.defaults.rules, {
		        checkValue: {
		            validator: function(value,param){
		            	var opts = $(param[0]).combobox("getData");
		            	for(var i in opts){
	            		   if(opts[i].value==value){
						       return true;
						   }
		            	}
						return false;
		            },
		            message: '请选择正确的值！'
		        }
		    });
		},
		
		getFormData:function(){
			var result = {};
			 $.each($('#pageForm').serializeArray(), function(index) {  
			        if(result[this['name']]) {
			        	result[this['name']] = result[this['name']] + "," + this['value'];  
			        } else {  
			        	result[this['name']] = this['value'];
			        }
			    }); 
			 return result;
		},
		
		resetFunc: function(){
			$("#task").val("");
			$("#workPlace").textbox("reset");
			$("#roadiBreak").val("");
			$("#disconecterIsBreak").val("");
			$("#content_1").val("");
			$("#content_2").val("");
			$("#needSetSign").val("");
			$("input[name='isLanding']")[1].checked = true;
			$("input[name='needSecondProcess']")[1].checked = true;
		}
	
	}
	
	module.exports = new WorkTicket();
})