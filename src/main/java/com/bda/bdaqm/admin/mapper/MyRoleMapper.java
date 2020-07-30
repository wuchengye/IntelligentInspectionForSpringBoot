package com.bda.bdaqm.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.admin.model.Resource;
import com.bda.admin.model.RolePermission;
import com.bda.admin.model.view.VRoleUser;
import com.bda.bdaqm.admin.model.Role;

import tk.mybatis.mapper.common.Mapper;

public interface MyRoleMapper extends Mapper<Role>{
	
	List<Role> getAllRoles(@Param("exceoptAdmin")String arg0, @Param("param")String arg1);
	
	List<Role> getRolesByUserId(@Param("userId")String arg0);
	
	List<String> getRoleIdsByUserId(@Param("userId") String arg0);
	
	List<VRoleUser> searchUsersByRoleIdKeywordFilter(@Param("roleId") String arg0, @Param("keyword") String arg1,
			@Param("filter") String arg2);

	List<Map<String, Object>> getAuthRole(Map<String, Object> arg0);

	List<Resource> getResources(Map<String, Object> arg0);

	int updateAuthRole(Map<String, Object> arg0);

	int updateRolePermission(RolePermission arg0);

	int addRolePermission(RolePermission arg0);

	void deleteAuthRole(List<String> arg0);

	int deleteRolePermission(@Param("roleId") String arg0, @Param("name") String arg1, @Param("primkey") String arg2);

	int delRoleAuths(@Param("roleId") String arg0);

	int delUsersByRoleId(@Param("roleId") String arg0);

	int deleteUserRoleByRoleId(@Param("roleId") String arg0);

	/*新增*/
	List<Role> getRolesByAccount(@Param("createUserAccount")String account);
	List<Role> getAllRole();
	int addRole(@Param("roleName") String name,@Param("roleDescription")String desctiption,
					 @Param("createUserAccount") String account,@Param("createTime") String time,
				@Param("departmentId") int departmentId,@Param("ability") int ability);
	int updateRole(@Param("roleId")String id,@Param("roleName") String name,@Param("roleDescription")String desctiption,
				   @Param("updateUserAccount") String account,@Param("updateTime") String time,
				   @Param("ability") int ability);

	String getRoleNameByUserId(@Param("userId")String userId);

	Role selectRoleByUserId(@Param("userId") String userId);
	List<Role> getRolesByDepartmentIdOrName(@Param("departmentId")String departmentId,@Param("keyword")String keyword);
	Role getRoleById (String roleId);
}
