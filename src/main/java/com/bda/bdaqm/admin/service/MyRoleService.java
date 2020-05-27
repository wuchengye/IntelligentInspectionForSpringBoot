package com.bda.bdaqm.admin.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.admin.model.Resource;
import com.bda.admin.model.RolePermission;
import com.bda.bdaqm.admin.model.UserRole;
import com.bda.admin.model.view.VRoleUser;
import com.bda.bdaqm.admin.mapper.MyRoleMapper;
import com.bda.bdaqm.admin.mapper.MyUserRoleMapper;
import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.User;
import com.bda.common.bean.OperaterResult;
import com.bda.common.service.AbstractService;
import com.bda.common.util.StringUtil;
import com.github.pagehelper.PageHelper;

@Service
public class MyRoleService  extends AbstractService<Role>{

	@Autowired
	private MyRoleMapper roleMapper;
	@Autowired
	private MyUserRoleMapper userRoleMapper;

	public List<Resource> getResources(String roleId, String name, String[] primkeys) {
		HashMap<String, Object> con = new HashMap<String, Object>();
		con.put("roleId", roleId);
		con.put("name", name);
		con.put("primkeys", primkeys);
		return this.roleMapper.getResources(con);
	}

	public OperaterResult<String> updateRolePermission(String roleId, String action, String name, String primkey,
			String checked) {
		int resInt1 = this.roleMapper.updateRolePermission(new RolePermission(roleId, action, name, primkey));
		return new OperaterResult<String>(resInt1 > 0, "完成操作");
	}

	public int deleteRolePermission(String roleId, String name, String primkey) {
		int resInt1 = this.roleMapper.deleteRolePermission(roleId, name, primkey);
		return resInt1;
	}
	
	public int addRolePermission(RolePermission rolePermission) {
		int resInt1 = this.roleMapper.addRolePermission(rolePermission);
		return resInt1;
	}

	public List<VRoleUser> searchUsers(String roleId, String keyword, String filter, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if (StringUtils.isEmpty(keyword)) {
			keyword = StringUtils.trimToNull(keyword);
		} else {
			keyword = StringUtil.keywords(keyword);
		}

		return this.roleMapper.searchUsersByRoleIdKeywordFilter(roleId, keyword, filter);
	}

	public List<Role> getRolesByUserId(String userId) {
		return this.roleMapper.getRolesByUserId(userId);
	}
	
	public List<Role> selectRoleByUser( User currentUser) {
		String currentUserRole = this.judgeCurrentUserRoleByUserId(currentUser.getUserId());
		
		if(currentUserRole.equals("1")){
			//系统管理员,查询所有角色
			return this.selectAllRole(null);
		}else if(currentUserRole.equals("2")){
			//只能看到自己的角色
			return this.selectSelfRoleByUserId(currentUser.getUserId());
		}else if(currentUserRole.equals("3")){
			//获取其他角色除了系统管理员
			return this.selectRoleExceoptAdmin("y",null);
			
		}
		//否则没有角色,看不到角色
		return null;
	}
	
	
	public List<Role> selectRoleExceoptAdmin(String exceoptAdmin, String arg) {
		return roleMapper.getAllRoles(exceoptAdmin, arg);
	}

	public List<Role> selectSelfRoleByUserId(String userId) {
		return roleMapper.getRolesByUserId(userId);
	}

	public List<Role> selectAllRole(String roleName) {
		return roleMapper.getAllRoles(null, roleName);
	}

	/**
	 * 判断当前用户的所属角色
	 * @param userId
	 * @return
	 * 	1,则说明是超级管理员<br>
	 * 	2,则说明是低权限角色<br>
	 * 	3,则说明是其他角色 <br>
	 * 	"",则说明没有角色
	 */
	public String judgeCurrentUserRoleByUserId(String userId) {
		//判断当前用户的角色
		List<UserRole> userRoleList = userRoleMapper.selectAllByEqField("userId",userId);
		String currentUserRole = "";
		if(userRoleList!=null && userRoleList.size()>0){
			for (UserRole userRole : userRoleList) {
				String roleId = userRole.getRoleId();
				if("10001".equals(roleId)){
					currentUserRole = "1";//只要有一个角色是1,则说明是系统管理员,直接返回
					return currentUserRole;
				}
			}
			if("".equals(currentUserRole)){
				//currentUserRole还是""说明不是系统管理员
				for (UserRole userRole : userRoleList) {
					String roleId = userRole.getRoleId();
					if("2".equals(roleId)){
						currentUserRole = "2";//只要有一个角色是2,则说明是低权限,直接返回
						return currentUserRole;
					}
				}
			}
			currentUserRole = "3";//既不是系统管理员,又不是低权限,属于系统管理员创建的角色,,是其他,直接返回
			return currentUserRole;
		}else{
			currentUserRole = "";//返回空,用户没有角色
			return currentUserRole;
		}
	}

	public List<String> getRoleIdsByUserId(String userId) {
		return roleMapper.getRoleIdsByUserId(userId);
	}
	
	public int deleteUsers(String roleId, List<String> userIds) {
		int ret = 0;
		UserRole ur = new UserRole(roleId, (String) null);

		for (Iterator<String> arg4 = userIds.iterator(); arg4.hasNext(); ret += this.userRoleMapper.delete(ur)) {
			String userId = (String) arg4.next();
			ur.setUserId(userId);
		}

		return ret;
	}
	
	public int addUsers(String roleId, List<String> userIds) {
		int ret = 0;
		UserRole ur = new UserRole(roleId, (String) null);

		for (Iterator<String> arg4 = userIds.iterator(); arg4.hasNext(); ) {
			String userId = (String) arg4.next();
			ur.setUserId(userId);
			if(userRoleMapper.selectUserRole(new UserRole(null,userId)).size() == 0){
				ret += this.userRoleMapper.insert(ur);
			}else {
				ret += userRoleMapper.update(ur);
			}
		}
		return ret;
	}

	/*新增*/
	public List<Role> getRolesByAccount(String accout){
		return roleMapper.getRolesByAccount(accout);
	}

	public List<Role> getAllRole(){
		return roleMapper.getAllRole();
	}

	public int addRole(Role role){
		return roleMapper.addRole(role.getRoleName(),role.getDescription(),role.getCreateUserAccount()
		,role.getCreateTime());
	}

	public int updateRole(Role role){
		return roleMapper.updateRole(role.getRoleId(),role.getRoleName(),role.getDescription(),role.getUpdateUserAccount(),
				role.getUpdateTime());
	}

	public String getRoleNameByUserId(String userId){
		return roleMapper.getRoleNameByUserId(userId);
	}
}
