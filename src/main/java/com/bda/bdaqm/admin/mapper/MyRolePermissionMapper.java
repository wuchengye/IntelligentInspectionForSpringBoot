package com.bda.bdaqm.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.admin.model.Resource;
import com.bda.bdaqm.admin.model.RolePermission;

import tk.mybatis.mapper.common.Mapper;

public interface MyRolePermissionMapper extends Mapper<RolePermission>{

	int cloneRolePermission(@Param("name") String arg0, @Param("source") String arg1, @Param("dest") String arg2);

	int deleteRolePermissionByRoleId(@Param("roleId") String arg0);

	List<Resource> selectByRoleId(Map<String, String> arg0);
	
}
