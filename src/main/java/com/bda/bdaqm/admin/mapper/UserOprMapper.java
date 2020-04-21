package com.bda.bdaqm.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.admin.model.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserOprMapper extends Mapper<User>{

	List<User> getUserByAccount(@Param("user")User user);

	/**
	 * 获取用户列表（除admin）
	 * @param arg0  模糊查询条件
	 * @param arg1  账号
	 * @param arg2  用户名
	 * @return
	 */
	List<User> getUserByEqField(@Param("param")String arg0,@Param("account")String arg1,@Param("userName")String arg2);

	int editUserPas(@Param("userId")String userId, @Param("newPas")String newPas);
	
	int resetUserPasswordByUserIds(@Param("ids")List<String> ids,@Param("psw")String psw);
	
	int addSelective(@Param("user")User user);
	
	int deleteByPrimaryKeys(@Param("userId") String arg0);

	/*新增*/
	int update(@Param("user") User user);

	int updateRole(@Param("userId")String userId,@Param("roleId")String roleId);
}
