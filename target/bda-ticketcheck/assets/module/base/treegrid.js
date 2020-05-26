define(function(require, exports, module) {
	var checkboxUtil=require("module/base/checkbox.js");
	var compatible = require("library/compatible/compatible.js");
	compatible.indexOf(); //解决IE8不支持indexOf的问题
	var tplCheckbox='<input type="checkbox">';
	
	var defaults = {
		grid:null,
		url : "",
		params:{},
		title : "",
		rootId : 0
	}
	function getColumns(target) {
		return [ [ {
			title : target.opt.title,
			field : 'text',
			width : '150px'
		}, {
			title : '可见',
			field : 'view',
			width : '50px',
			height : '25px',
			formatter : function(value, row, index) {
				return formatCheckbox("view", row);
			}
		}/*, {
			title : '编辑',
			field : 'edit',
			width : '50px',
			height : '25px',
			formatter : function(value, row, index) {
				return formatCheckbox("edit", row);
			}
		}*/ ] ];
	}
	function formatCheckbox(name,row){
		var i= row.actions.indexOf(name);
		var $checkbox=$(tplCheckbox).attr("name",name);
		$checkbox.attr('id',name+"_"+row.id);
		if(i != -1){
			return $checkbox.attr('checked','checked')[0].outerHTML;
		}
		return $checkbox[0].outerHTML;;
	}
	
	function initLocalData(target){
		target.data['view']=new Object();
		target.data['edit']=new Object();
	}
	function clearLocalData(target){
		target.data['view'].adds=[];
		target.data['view'].deletes=[];
		target.data['edit'].adds=[];
		target.data['edit'].deletes=[];
	}
	function createPanel(target){
		var opt=target.opt;
		opt.grid.treegrid({
		    url:opt.url,
		    rownumbers: false,
		    fit: true,
		    pagination:true,
		    pageList: [10,30,50,100],
		    idField : "id",
		    treeField : "text",
		    columns: getColumns(target),
		    animate:true,
		    ctrlSelect:true,
            singleSelect:false,
		    queryParams:target.opt.params,
		    onBeforeLoad: function(row,param){
                if (!row) {    // load top level rows
                	param.id = target.opt.params.rootId;    // set id=0, indicate to load new page rows
                }
            }
		
		//勾选然后再点击展开应该和勾选不展开后台统一进行了处理
//		,onExpand:function(row){
//            		//实际上触发了两次 有点问题
//            		$('tr.datagrid-row-selected').find('input[name=view]').click();
//            		$('tr.datagrid-row-selected').find('input[name=edit]').click();
//            }
            
		});
		checkboxUtil.checkbox(opt.grid,target,"view");
		checkboxUtil.checkbox(opt.grid,target,"edit");
	}
	function getData(target){
		var data={};
		data.view=target.data['view'];
		data.edit=target.data['edit'];
		return data;
	}
	var Treegrid=function(options){
		this.opt=$.extend({},defaults, options);
		this.data=new Object();
		this._init();
	}
	Treegrid.prototype={
			_init:function(){
				var self = this;
				initLocalData(this);
				createPanel(this);
			},
			clearLocalData:function(){
				clearLocalData(this);
			},
			getData:function(){
				return getData(this);
			}
	}
	module.exports=Treegrid;

});