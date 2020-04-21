package com.bda.bdaqm.admin.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.admin.util.PermissionChecker;
import com.bda.bdaqm.admin.mapper.MyRolePermissionMapper;
import com.bda.bdaqm.admin.model.RolePermission;
import com.bda.common.service.AbstractService;

@Service
public class MyRolePermissionService extends AbstractService<RolePermission>{

	public static final String BEAN_ID = "rolePermissionService";
	@Autowired
	MyRolePermissionMapper rolePermissionMapper;

	public int addPermission(String roleId, String action, String permission, List<String> ids) {
		int ret = 0;

		RolePermission rp;
		for (Iterator<String> arg5 = ids.iterator(); arg5.hasNext(); ret += this.addSelective(rp)) {
			String id = (String) arg5.next();
			rp = new RolePermission(roleId, action, permission, id);
		}

		return ret;
	}

	public int addPermission(Collection<RolePermission> rps) {
		int ret = 0;

		RolePermission rp;
		for (Iterator<RolePermission> arg2 = rps.iterator(); arg2.hasNext(); ret += this.addSelective(rp)) {
			rp = (RolePermission) arg2.next();
		}

		return ret;
	}

	public int clonePermission(String name, String source, String dest) {
		int ret = this.rolePermissionMapper.cloneRolePermission(name, source, dest);
		if (ret > 0) {
			PermissionChecker.clearPermissionCache();
		}

		return ret;
	}

	public int removePermission(String roleId, String action, String permission, List<String> deletes) {
		int ret = 0;

		String id;
		for (Iterator<String> arg5 = deletes.iterator(); arg5
				.hasNext(); ret += this.delete(new RolePermission(roleId, action, permission, id))) {
			id = (String) arg5.next();
		}

		if (ret > 0) {
			PermissionChecker.clearPermissionCache();
		}

		return ret;
	}

	public int removePermission(Collection<RolePermission> rps) {
		int ret = 0;

		RolePermission rp;
		for (Iterator<RolePermission> arg2 = rps.iterator(); arg2.hasNext(); ret += this.delete(rp)) {
			rp = (RolePermission) arg2.next();
		}

		if (ret > 0) {
			PermissionChecker.clearPermissionCache();
		}

		return ret;
	}

	public List<RolePermission> getRolePermissions(String action, String name, String primkey) {
		RolePermission rp = new RolePermission();
		rp.setAction(action);
		rp.setName(name);
		rp.setPrimkey(primkey);
		return this.rolePermissionMapper.select(rp);
	}

	public int deleteRolePermissionByRoleId(String roleId) {
		return this.rolePermissionMapper.deleteRolePermissionByRoleId(roleId);
	}
	
}
