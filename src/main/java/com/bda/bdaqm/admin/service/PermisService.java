package com.bda.bdaqm.admin.service;

import java.util.List;

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
	public List<RolePermission> getPermisByRoleId(String roleId){
		return permisMapper.getPermisByRoleId(roleId);
	}
	
	/**
	 * 更改权限
	 * @param per
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int savePermission(PermissionBean per){
		int result = 0;
		List<RolePermission> addList = per.getAddList();
		List<RolePermission> delList = per.getDelList();
		for(RolePermission add:addList){
			add.setRoleId(per.getRoleId());
		}
		for(RolePermission del:delList){
			del.setRoleId(per.getRoleId());
		}
		if(addList.size()>0){
			result += permisMapper.insertPerByBatch(addList);	
		}
		if(delList.size()>0){
			result += permisMapper.deletePerByBatch(delList);
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
	
}
