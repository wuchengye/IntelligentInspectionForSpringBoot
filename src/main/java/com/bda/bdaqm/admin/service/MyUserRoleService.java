package com.bda.bdaqm.admin.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.admin.mapper.MyUserRoleMapper;
import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.UserRole;
import com.bda.common.service.AbstractService;

@Service
public class MyUserRoleService extends AbstractService<UserRole>{

	@Autowired
	private MyUserRoleMapper userRoleMapper;
	
	public int addUsers(String roleId, List<String> userIds) {
		int ret = 0;
		UserRole ur = new UserRole(roleId, (String) null);

		for (Iterator<String> arg4 = userIds.iterator(); arg4.hasNext(); ret += this.userRoleMapper.insertUserRole(ur)) {
			String userId = (String) arg4.next();
			ur.setUserId(userId);
		}

		return ret;
	}

	public int deleteUserRoleByRoleId(String roleId) {
		return this.userRoleMapper.deleteUserRole(new UserRole(roleId, (String) null));
	}

	public int deleteUsers(String roleId, List<String> userIds) {
		int ret = 0;
		UserRole ur = new UserRole(roleId, (String) null);

		for (Iterator<String> arg4 = userIds.iterator(); arg4.hasNext(); ret += this.userRoleMapper.deleteUserRole(ur)) {
			String userId = (String) arg4.next();
			ur.setUserId(userId);
		}

		return ret;
	}

	public int deleteUserRole(String roleId, String userId) {
		return this.userRoleMapper.deleteUserRole(new UserRole(roleId, userId));
	}

	public List<String> getRoleIdsByUserId(String userId) {
		ArrayList<String> ids = new ArrayList<String>();
		List<UserRole> userRoles = this.userRoleMapper.selectUserRole(new UserRole((String) null, userId));
		Iterator<UserRole> arg3 = userRoles.iterator();

		while (arg3.hasNext()) {
			UserRole ur = (UserRole) arg3.next();
			ids.add(ur.getRoleId());
		}

		return ids;
	}

	public Class<?> getEntityClass() {
		return Role.class;
	}

	/*新增*/
	public List<String> getUserIdByRoleId(String roleId){
		ArrayList<String> ids = new ArrayList<String>();
		List<UserRole> userRoles = this.userRoleMapper.selectUserRole(new UserRole((String) roleId, null));
		Iterator<UserRole> arg3 = userRoles.iterator();

		while (arg3.hasNext()) {
			UserRole ur = (UserRole) arg3.next();
			ids.add(ur.getUserId());
		}

		return ids;
	}


}
