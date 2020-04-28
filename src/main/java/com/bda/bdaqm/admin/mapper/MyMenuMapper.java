package com.bda.bdaqm.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.admin.bean.PMenu;
import com.bda.bdaqm.admin.model.Menu;

import tk.mybatis.mapper.common.Mapper;

public interface MyMenuMapper extends Mapper<Menu>{

	List<Menu> selectAllRecursiveByParentId(String parentId);

	List<PMenu> selectMenusForPerms(@Param("parentId")String parentId, @Param("roleId")String roleId);
	
	List<String> selectChildsMenuIdByParentId(@Param("parentId") String arg0);
	
	int addSelective(@Param("menu") Menu menu);

	/*新增*/
	List<Menu> selectAllMenu();
	Menu selectMenuById(@Param("menuId") String menuId);
}
