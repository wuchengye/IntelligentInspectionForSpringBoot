package com.bda.bdaqm.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.admin.model.RolePermission;

public interface PermisMapper {
	List<String> getPermissionByRole(String arg0);

	List<String> getPermissionByUserId(@Param("userId")String arg0);

	List<String> getPermissionByBtn(String arg0);
	
	/**
	 * 根据角色ID获取权限
	 * @param roleId
	 * @return
	 */
	public List<RolePermission> getPermisByRoleId(String roleId);
	
	/**
	 * 批量新增权限
	 */
	public int insertPerByBatch(@Param("roleId")String roleId, @Param("permis")String per);
	
	/**
	 * 批量删除权限
	 */
	public int deletePerByBatch(@Param("roleId")String roleId);

	int addOnePermis(@Param("permiss")String permiss,@Param("roleId")String roleId);

	int deleteMenus(@Param("permis")String per);
}
