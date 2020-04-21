/**
 * Admin页面控制，后台管理的入口页面，提供父Frame的脚本
 */
define(function(require, exports, module) {
	var base = require("module/base/main.js");
	var extend = require("module/base/extend.js");
	//var menuUrl = "admin/getMenu";
	var menuUrl = "admin/qmauth/getMenu";
	var getDataTypeUrl = "admin/permission/getDataType";
	var setDataTypeUrl = "admin/permission/setDataType";
	
	var curTop=35;
	var h=70;
	var curPos=0;
	var $navSlider=$("#nav_slider");
	var dataType="";
	var _self;
	
	var cancelClk = true;
	
	var AdminMain = function() {
		this.$tabs;
	};
	
	
	AdminMain.prototype = {
		constructor : AdminMain,
		init:function(){
			_self=$(this);
			_self.$tabs = $(".js-tabs");
			render(_self);
		},
		
	};
	module.exports = new AdminMain();
	
	
	function render(target){
		//初始化读取数据类型
		//loadDataType();
		//初始化菜单
		loadMenus(target);
		$("#nav_menu").on("mouseenter mouseleave",".b-menu-item ",function(e){
			var self=$(this);
			var i=self.index();
			var length=self.siblings(".b-menu-item").length;
			var toTop=curTop+i*h;
			
			if(e.type === "mouseenter"){
				var t=self.offset();
				var allH=t.top+self.find(".sub-menu").height();
				var windowH=$(window).height();
				$navSlider.animate({top:toTop},80,function(){
					if(allH>windowH){
						self.find(".sub-menu").css("bottom",h*(length-i));
					}
					self.siblings(".b-menu-item.menu-hover").removeClass("menu-hover");
					self.addClass("menu-hover");				
				}).clearQueue();
			}else{
				self.removeClass("menu-hover");
				$navSlider.animate({top:curTop+curPos*h},80,function(){
					self.removeClass("menu-hover");
					self.find(".sub-menu").css("bottom","auto");
				}).clearQueue();
			}	
			
		});

		$("#nav_menu").on("click",".sub-menu",function(){
			var self=$(this);
			self.parent(".b-menu-item").removeClass("menu-hover");
			curPos=self.parent(".b-menu-item").index();
		});
		
		//数据源切换按钮事件
		$(".btnDataSorce").click(function(){
			parent.$.messager.progress({
			    title : '提示',
			    text : '数据源切换中，请稍候....'
			});
			var id = $(this).attr("id");
			$.post(setDataTypeUrl,{dataType:id},function(data,status){
				parent.$.messager.progress('close');
				//重新设置数据类型
				dataType = id;
				//重新初始化菜单
				loadMenus(_self);
			});
			var tabs = $('#centerTab').tabs('tabs');
			var tlength = tabs.length;
			for(var i=0;i<tlength;i++){
				var url =$(tabs[0].panel('options').content).find('iframe').attr('src');
				var title = tabs[0].panel('options').title;
				var tabid = tabs[0].panel('options').id.substring(4);
				//关闭最前面的tab，后面的tab会移动到前面，所以tabs下标值固定取0
				$('#centerTab').tabs('close', title);
				
				//base.openAjaxTab($('#centerTab'), tabid, title, url);
			}
			
		});
		
		$('#menubutton').click(function(){
			var leftvar = $(".login-info")[0].getBoundingClientRect().left;
	  		var topvar = $(".login-info")[0].getBoundingClientRect().top;
	  		$('#menuitem').menu('show', {    
	  			  left: leftvar,    
	  			  top: topvar+30    
	  		}); 
		});
		
	}
	
	/**
	 * 加载菜单
	 */
	function loadMenus(target){
		var tplLi="<li class='b-menu-item'>" +
					"<a href='javascript:void(0);'>" +
						"<img src='assets/images/admin/menu_1.png'>" +
						"<span></span>" +
					"</a>" +
				   "</li>";
		var tplSub="<div class='sub-menu'><ul></ul></div>";
		var tplSubLi="<li><a href='javascript:void(0);'><span></span></a></li>";
		$.getJSON(menuUrl,function(resp){
			//首先清空原来的菜单
			$("#nav_menu").empty();
			for(var i=0;i<resp.length;i++){
				var m=resp[i];
				var $li=$(tplLi);
				//debugger;
				$li.find("a").data("node",m);
				$li.find("span").html(m.text);
				//$li.find("img").attr("src","assets/images/admin/menu_"+(i%6+1)+".png");
				
				if(null==m.icon || ''==m.icon){
					$li.find("img").attr("src","assets/images/admin/"+'menu_1.png');
				}else{
					var data_icon_arr = m.icon.split(',');
					//如果包含当前数据类型的菜单才渲染到界面
					//if($.inArray(dataType,data_icon_arr)>=0){
						var icon = data_icon_arr[0];
						$li.find("img").attr("src","assets/images/admin/"+icon);
						if(m.children != null && m.children.length > 0){
							var $sub=$(tplSub);
							$li.append($sub);
							//渲染子菜单
							for(var k=0;k<m.children.length;k++){
								var sm=m.children[k];
								/*if(null!=sm.icon && ''!=sm.icon){//不渲染icon为空的菜单
									var sm_data_icon_arr = sm.icon.split(',');
									if($.inArray(dataType,sm_data_icon_arr)>=0){*/
										var $subLi=$(tplSubLi);
										$subLi.find("a").data("node",sm);
										$subLi.find("a").addClass('activable-menu');//点击打开tab页功能
										$subLi.find("span").html(sm.text);
										$sub.find("ul").append($subLi);
									//}
								//}
							}
						} else if(m.url != null && m.url.length > 0){
							$li.find("a").addClass('activable-menu');
						}
						$("#nav_menu").append($li);
						$(".sub-menu").mCustomScrollbar({//渲染滚动条插件
							theme:"minimal-dark"
						});
					//}
				}
			}
		});
		$("#nav_menu").on("click",".activable-menu",function(e){
			var self=$(this);
			var node=self.data("node");
			base.openAjaxTab(target.$tabs, node.id, node.text, node.url);
		});
		
		$('#menuitem').menu({    
            onClick:function(item){    
                if(item.id == 'editPsd'){
                	var editPasFootBtn = [{
    					text:'保存',
    					iconCls:'icon-ok',
    					handler:function(){
    						var $oldPas = $('#oldPas');
    						var $firstPas = $('#firstPas');
    						var $newPas = $('#newPas');
    						//var rgx = /^(?:(?=.*[0-9].*)(?=.*[A-Za-z].*)(?=.*[,\.#%@\~\='"\+\*\-:;^_`\?\!\$&\|\(\)\[\]\{\}<>\/\\].*))[?=.*[,\.#%@\~\='"\+\*\-:;^_`\?\!\$&\|\(\)\[\]\{\}<>\/\\0-9A-Za-z]{8,100}$/;
    						//if(rgx.test($newPas.val())){
	    						if($firstPas.val() == $newPas.val()){
	    							
	    							$oldPas.textbox('setValue',$.sha256($oldPas.val()));
	    							var psd = $.sha256($firstPas.val());
	    							$firstPas.textbox('setValue',psd);
	    							$newPas.textbox('setValue',psd);
	    							$('#userPasForm').form({
	    								queryParams : {indexEditPasFlag:"yes"},
	    								
	    				                success:function(resp){
	    				                	resp=$.parseJSON(resp);
	    				                	if(resp.meta.success){
	    					                    $.messager.alert('提示', "保存成功", 'info',function(){
	    					                    	$("#editPsdDialog").dialog("close");
	    					                    	$("#editPsdDialog").datagrid('reload');
	    					                    })
	    				                	}else{
	    				                		 $.messager.alert('错误', "保存失败", 'error');
	    				                		 	$oldPas.textbox('setValue','');
	    		    								$firstPas.textbox('setValue','');
	    		    								$newPas.textbox('setValue','');
	    		    								$firstPas.focus();
	    				                	}
	    				                }
	    							});
	    							$('#userPasForm').submit();
	    						}else{
	    							$.messager.alert('提示','两次输入不一致,请重新输入','warming',function(){
	    								$oldPas.textbox('setValue','');
	    								$firstPas.textbox('setValue','');
	    								$newPas.textbox('setValue','');
	    								$firstPas.focus();										
	    							});
	    						}
    						//}else{
    						//	return;
    						//}
    					}
    				},
    				{
    					text:'取消',
    					handler:function(){
    						if(cancelClk){
    							$("#editPsdDialog").dialog("close");
    						}else{
    							$(".btn-logout")[0].click();
    						}
    					}
    				
    				}];
                	
                	$('#editPsdDialog').dialog({    
                	    title: '修改用户密码',    
                	    width: 350,    
                	    height: 160,    
                	    href: "usermanage/user/edit", 
    					queryParams:{editPasFlag:"yes",indexEditPasFlag:"yes"},
    					buttons:editPasFootBtn,
    					minimizable:true,
    					maximizable:true,
    					resizable:true
                	});
                }
            }    
        }); 
		
	}
	
	/**
	 * 读取数据类型
	 */
	function loadDataType(){
		$.getJSON(getDataTypeUrl,function(resp){
			$("#"+resp.dataType).linkbutton('select');
			dataType = resp.dataType;
		})
	}
	
});