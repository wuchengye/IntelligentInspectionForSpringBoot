package com.bda.bdaqm.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.admin.model.UserRole;

import tk.mybatis.mapper.common.Mapper;


public interface MyUserRoleMapper extends Mapper<UserRole>{
	int deleteUserRoleByRoleId(@Param("roleId") String arg0);

	List<UserRole> selectUserRole(@Param("params")UserRole userRole);

	int deleteUserRole(@Param("params")UserRole userRole);

	int insertUserRole(@Param("userRole")UserRole ur);

	List<UserRole> selectAllByEqField(@Param("field")String string, @Param("param")String param);
	int update(@Param("userRole")UserRole ur);
}
