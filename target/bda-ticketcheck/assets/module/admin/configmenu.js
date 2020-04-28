define(function(require, exports, module) {
	var url="admin/qmauth/loadPerms";
	var saveUrl="admin/qmauth/savePerms";
	
	function getPostData(target){
		var grid=target.grid;
		var gridData=grid.getData();
		var data=new Array();
		for(key in gridData){
			var o={
					roleId:target.roleId,
					action:key
				  };
			o.adds=gridData[key].adds;
			o.deletes=gridData[key].deletes;
			data.push(o);
		}
		return data;
		
	}
	function bindEvent(target){
		$(".js-btn-save").on("click",function(){
			parent.$.messager.progress({
			    title : '提示',
			    text : '保存中，请稍后....'
			});
			$.ajax({
				url:saveUrl,
				type : 'post',
				data : JSON.stringify(getPostData(target)),
				dataType : 'json',
				contentType : 'application/json',
				success:function(resp){
					if(resp.meta.success){
						parent.$.messager.progress('close');
						$.messager.alert("提示","保存成功","info");
					}else{
						parent.$.messager.progress('close');
						$.messager.alert("提示",resp.meta.code,"warning");
					}
					target.grid.clearLocalData();
				}
			});
		});
		$(".js-btn-cancel").on("click",function(){
			window.parent.$('#permsWin').dialog('close');
		});
	}
	var Main = function() {
		this.$grid;
		this.roleId;
		this.views;
	};
	Main.prototype = {
		constructor : Main,
		init : function() {
			var self = this;
			self.grid = $("#grid");
			self.roleId=$("input[name=roleId]").val();
			var Treegrid=require("module/base/treegrid.js");
			self.grid=new Treegrid({grid:self.grid,url:url,params:{roleId:self.roleId},title:"菜单"});
			bindEvent(self);
		}
	};
	
	module.exports = new Main();
});