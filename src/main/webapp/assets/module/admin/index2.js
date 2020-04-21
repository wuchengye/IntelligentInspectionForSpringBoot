/**
 * Admin页面控制，后台管理的入口页面，提供父Frame的脚本
 */
define(function(require, exports, module) {
	var base = require("module/base/main.js");
	var extend = require("module/base/extend.js");
	var ask = require("module/chat/ask.js");
	
	var menuUrl = "admin/getMenu";

	var AdminMain = function() {
		this.$tree;
		this.$menuPanel;
		this.$tabs;
		this.$menu;
		this.$toolbar;
		this.$templateWindow;
	};

	AdminMain.prototype = {
		constructor : AdminMain,
		/**
		 * 初始化函数
		 */
		index : function() {
			extend.init();
			var self = this;
			self.$toolbar=$(".js-toolbar");
			self.$menuPanel = $(".js-menu-panel");
			self.$menu = $("#nav_menu");
			self.$tabs = $(".js-tabs");
			self.$templateWindow = $("#templateWindow");
			
//			初始化工具栏
			self.initToolbar();
         // 初始化菜单树
			self.initMenu();
		},
		initToolbar:function(){
			var self=this;
			self.$toolbar.on("click",".js-btn-setup",function(event){
				base.openAjaxTab(self.$tabs, "config", "设置", $(this).find('a').attr('href'));
				return false;
			});
		},
		/**
		 * 初始化Tree组件
		 * 
		 * @return {[type]} [description]
		 */
		initMenu: function() {
			var self = this;
			self.$menu.accordion({
				width:'100%',
				multiple:true
			});
			$.ajaxSetup({
				cache :false
			});
			$.getJSON(menuUrl,function(data){
				for(var i=0;i<data.length;i++){
					var menu=data[i];
					var $subMenu=$("<ul class='js-sub-menu' ></ul>");
					if(menu.children){
						for(var k=0;k<menu.children.length;k++){
							var m=menu.children[k];
							var li=$("<li></li").append($('<a href="#" class="easyui-linkbutton c9" style="width:100%" ></a>').attr('href',m.url).html(m.text));
							li.data('node',m);
							$subMenu.append(li);
						}
						self.$menu.accordion('add', {
							title: menu.text,
							content:$subMenu
						});
					}else{
						self.$menu.accordion('add', {
							title: menu.text,
							collapsed:false,
							collapsible:false,
							content:$subMenu
						});
					}
				}
//				onclick
				self.$menu.on("click","li",function(event){
					var url=$(this).find("a").attr("href");
					var node=$(this).data("node");
					base.openAjaxTab(self.$tabs, node.id, node.text, node.url);
					//选项卡关闭前提示是否关闭，如果是，则清理会话
					if(node.text == '提问界面') {//修复添加关闭标签页监听事件后打开新标签也无法聚焦的bug
						self.$tabs.tabs({//还是存在bug，且出现的几率较大，需要删除再添加
							onBeforeClose : function(title, index)	{
								if(title == '提问界面') {
									var $askFrame = $(".askFrame");
									if($askFrame.contents().find("#bda_chat_chatIdSync").val()) {
										var flag = window.confirm("关闭标签页后聊天会话将会结束，确定关闭？");
										if(flag) {//当title为“提问界面”时，iframe的id为askFrame(见main.js
											ask.exitChatIfExist($askFrame);//将获取到的iframe元素传递给该方法，以便在方法里面查找iframe里的元素
											return true;
										}
										return false;
									}
								}
								return true;
							}
						});
					}
					return false;
				});
			});
		},
		treeNodeOnClick: function(node) {
			var self = this;
			if (node.children) {
				//展开目录
				if (node.state == "closed") {
					self.$tree.tree("expand", node.target);
				} else {
					self.$tree.tree("collapse", node.target);
				}
			} else {
				var url = node.url;
				base.openAjaxTab(self.$tabs, node.id, node.text, url);
			}
		}
	};
	module.exports = new AdminMain();
})