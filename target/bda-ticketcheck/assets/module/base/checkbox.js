define(function(require, exports, module) {
	var compatible = require("library/compatible/compatible.js");
	
	module.exports.init=function(){
		
	}
	module.exports.checkbox=function($grid,target,name){
		compatible.indexOf(); //解决IE8不支持indexOf的问题
		target.data[name]={adds:[],deletes:[]};
		var result=target.data[name];
		$grid.treegrid("getPanel").on("click","input[name="+name+"]",function(event){
    		var checked=$(this).attr("checked");
    		var row=$grid.treegrid('getSelected');
    		changeState($(this),checked,row);
    	});
		
		$grid.treegrid("getPanel").on("change","input[name="+name+"]",function(event){
			var checked=$(this).attr("checked");
    		if(name=='edit' && checked){
    			$view = $(this).closest('td').prev('td').find('input[name=view]');
    			//veiw是未勾选状态
    			if(!($view.attr("checked"))){
    				$view.click();
    			}
    		}else if(name=='view' && !checked){
    			$edit = $(this).closest('td').next('td').find('input[name=edit]');
    			//edit是勾选状态
    			if($edit.attr("checked")){
    				$edit.click();
    			}
    		}
    	});
		
		
		/**
		 * 改变checkbox的状态
		 * [勾选/不勾选]
		 * 
		 * @param $checkbox 当前checkbox对象
		 * @param checked checkbox状态
		 * @param row 当前checkbox所在的行对象数据
		 * @param gothrough 是否跳过（设置这个flag的目的在于避免对parent节点的children节点进行遍历）
		 * 
		 * @createdBy jianwei.li
		 * @createdDate 2016/11/23
		 * 
		 * @note 迁移或者修改需要注意上面result的值
		 * 
		 */
		function changeState($checkbox,checked,row,gothrough){
			
			var id=row.id;
			
			if(row.actions.indexOf(name)!=-1){//本来就有name权限
				var index=result.deletes.indexOf(id);
				if(index!=-1){
					result.deletes.splice(index,1);
				}
				if(!checked){//未勾选，即是取消权限
					result.deletes.push(id);
				}
			}else{//本身并无name权限
				var index=result.adds.indexOf(id);
				if(index!=-1){
					result.adds.splice(index,1);
				}
				if(checked){//勾选，即是添加授权
					result.adds.push(id);
				}
			}
			
			//寻找子节点
			var childs = $grid.treegrid('getChildren',id);
			
			//当前是未选中状态
			//并且有子节点
			if(!checked){
				//如果子节点不为空
				if(gothrough === undefined && childs != null){
					for(var i in childs){
						//找到当前子节点，取消勾选并继续向下查找
						var $current = $checkbox.closest('tr').next('tr.treegrid-tr-tree').find('input[name=' + name + ']').eq(i);
						$current.prop('checked',false);
						changeState($current,undefined,childs[i]);
					}
				}
				
				//检查兄弟节点是否全部是未选中状态，如果是取消父节点选中状态
				var brothers = $checkbox.closest('tbody').children('tr.datagrid-row').find('input[name=' + name + ']');
				if(brothers.length > 0){
					
						for(var counter = 0;counter < brothers.length; counter++){
							if(brothers.eq(counter).attr('checked')){
								return;
							}
						}
					
						//寻找父节点
						var parent = $grid.treegrid('getParent',id);
						if(parent != null){
							//找到当前父节点，勾选并继续向上查找
							var $current = $checkbox.closest('tr.treegrid-tr-tree').prev('tr').find('input[name=' + name + ']');
							$current.prop('checked',false);
							changeState($current,undefined,parent,true);
						}
					}
				
				
			}
			
			//当前是选中状态
			if(checked){
				//如果子节点不为空
				if(gothrough === undefined && childs != null){
					for(var i in childs){
						//找到当前子节点，勾选并继续向下查找
						var $current = $checkbox.closest('tr').next('tr.treegrid-tr-tree').find('input[name=' + name + ']').eq(i);
						$current.prop('checked',true);
						changeState($current,'checked',childs[i]);
					}
				}
				
				//寻找父节点
				var parent = $grid.treegrid('getParent',id);
				if(parent != null){
					//找到当前父节点，勾选并继续向上查找
					var $current = $checkbox.closest('tr.treegrid-tr-tree').prev('tr').find('input[name=' + name + ']');
					$current.prop('checked',true);
					changeState($current,'checked',parent,true);
				}
			}
		}
	}
});