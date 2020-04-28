package com.bda.bdaqm.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bda.bdaqm.admin.model.RolePermission;
import com.bda.bdaqm.admin.bean.PermissionBean;
import com.bda.bdaqm.admin.mapper.PermisMapper;

@Service
public class PermisService {

	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	private PermisMapper permisMapper;
	
	/**
	 * 根据角色ID获取权限
	 * @param roleId
	 * @return
	 */
	public Map<String,List<RolePermission>> getPermisByRoleId(String roleId,String userRoleId){
		Map<String,List<RolePermission>> map = new HashMap<>();
		map.put("role",permisMapper.getPermisByRoleId(roleId));
		map.put("userRole",permisMapper.getPermisByRoleId(userRoleId));
		return map;
	}
	
	/**
	 * 更改权限
	 */
	@Transactional(rollbackFor=Exception.class)
	public int savePermission(String roleId,
			List<String> permis){
		int result = 0;
		if(permisMapper.deletePerByBatch(roleId) >= 0){
			for (String per : permis){
				result += permisMapper.insertPerByBatch(roleId,per);
			}
		}
		return result;
	}

	public List<String> getPermissionByRole(String roleId) {
		return this.permisMapper.getPermissionByRole(roleId);
	}

	public List<String> getPermissionByUserId(String userId) {
		return this.permisMapper.getPermissionByUserId(userId);
	}

	public List<String> getPermissionByBtn(String roleId) {
		return this.permisMapper.getPermissionByBtn(roleId);
	}

	public int addOnePermis(String roleId,String permiss){
		return this.permisMapper.insertPerByBatch(roleId,permiss);
	}

	public int deleteMenus(List<String> ids){
		int ret = 0;
		for (String id : ids){
			ret += permisMapper.deleteMenus(id);
		}
		return ret;
	}
}
