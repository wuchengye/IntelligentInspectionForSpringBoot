define(function(require, exports, module) {
	
	var ShowDetail = function(){}
	
	ShowDetail.prototype = {
			constructor:ShowDetail,
			
			format: function(row){
				if(null == row.staffId || undefined == row.staffId || '' == row.staffId){
					row.staffId = '-';
				}
				if(null == row.staffName || undefined == row.staffName || '' == row.staffName){
					row.staffName = '-';
				}
				if(null == row.workGroupName || undefined == row.workGroupName || '' == row.workGroupName){
					row.workGroupName = '-';
				}
				if(null == row.staffRoom || undefined == row.staffRoom || '' == row.staffRoom){
					row.staffRoom = '-';
				}
				var template = "<a class='staffId1' style='color:#0AA1ED;'  href='javascript:void(0)' " +
		   		"title='<b >员工工号：</b>" + row.staffId + 
		   		"<br><b >员工姓名：</b>"+row.staffName +
		   		"<br><b >所属班组：</b>"+row.workGroupName +
		   		"<br><b >所属科室：</b>"+row.staffRoom+"' >"
		   		+ row.staffId + "<a/>";
				return template;
			},
			
			showDetail: function() {
				$('.staffId1').tooltip({
					onShow : function(e) {
						$(this).tooltip('tip').css({
							backgroundColor : '#fff',
							borderColor : '#3a82bd'
						});
					}
				});
			}
	}
	
	module.exports = new ShowDetail();
})