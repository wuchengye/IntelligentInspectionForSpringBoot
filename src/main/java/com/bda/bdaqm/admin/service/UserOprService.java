package com.bda.bdaqm.admin.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.model.UserRole;
import com.bda.bdaqm.util.DateUtils;
import com.bda.common.service.AbstractService;
import com.bda.bdaqm.admin.mapper.MyUserRoleMapper;
import com.bda.bdaqm.admin.mapper.UserOprMapper;

@Service
public class UserOprService extends AbstractService<User>{

	//b2d0a88d799f91ecd97b31ad09c7093cae459dfbb33081a6b08eb5b1f59053b6  gzj@123
	private final static String DEFAULT_PWD = "4a6297761baff57f294a95aa9e4ef30ea2d4dec4ca920dac51fb1804eb0c7e21";//明文：sjs@1234
	private final static String DEFAULT_STATUS = "1";
	
	@Autowired
	private UserOprMapper userMapper;
	@Autowired
	private MyUserRoleMapper userRoleMapper;
	
	/**
	 * 根据账号获取User
	 * @param account
	 * @return
	 */
	public User getUserByAccount(String account) {
		if (account == null) {
			return null;
		} else {
			User user = new User();
			user.setAccount(account);
			return this.userMapper.getUserByAccount(user).size() > 0 
					? this.userMapper.getUserByAccount(user).get(0) : null ;
		}
	}
	
	/**
	 * 获取用户列表(除admin)
	 * @return
	 */
	public List<User> getUsers(String arg0){
		return this.userMapper.getUserByEqField(arg0, null, null);
	}
	
	/**
	 * 根据userId获取账号
	 * @param userId
	 * @return
	 */
	public User selectByPrimaryKey(String userId) {
		User user = new User();
		user.setUserId(Long.parseLong(userId));
		return this.userMapper.getUserByAccount(user).size() > 0 
			?this.userMapper.getUserByAccount(user).get(0) : null;
	}

	public User save(User user, List<String> roleIds) {
		Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal(); //当前用户
		String curAccount = currentUser.getAccount();//当前登陆账号
		int ret1;
		if (StringUtils.isEmpty(user.getUserId())) {
			user.setPassword(DEFAULT_PWD);
			user.setStatus(DEFAULT_STATUS);
			user.setCreateTime(DateUtils.getChinaTime());
			user.setCreateUserAccount(curAccount);
			ret1 = userMapper.addSelective(user);
		} else {
			user.setUpdateTime(DateUtils.getChinaTime());
			user.setUpdateUserAccount(curAccount);
			ret1 = this.updateByPrimaryKeySelective(user);
			this.deleteUserRoleByUserId(user.getUserId());
		}

		if (roleIds != null) {
			Iterator<String> arg3 = roleIds.iterator();

			while (arg3.hasNext()) {
				String roleId = (String) arg3.next();
				String userId = user.getUserId();
				this.addUserRole(roleId, userId);
			}
		}

		return user;
	}

	public void addUserRole(String roleId, String userId) {
		UserRole uRole = new UserRole();
		uRole.setRoleId(roleId);
		uRole.setUserId(userId);
		userRoleMapper.insertUserRole(uRole);
	}
	
	public int deleteByPrimaryKeys(String userId) {
		return this.userMapper.deleteByPrimaryKeys(userId);
	}
	
	public int deleteUserRoleByUserId(String userId) {
		return this.userRoleMapper.deleteUserRole(new UserRole(null,userId));
	}

	public int editUserPas(String userId, String newPas) {
		return userMapper.editUserPas(userId, newPas);
	}
	
	public int resetUserPasswordByUserIds(List<String> ids){
		
		return userMapper.resetUserPasswordByUserIds(ids, DEFAULT_PWD);
	}

	/*新增*/
	public int update(User user,String roleIds){
		Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal(); //当前用户
		String curAccount = currentUser.getAccount();//当前登陆账号
		user.setUpdateTime(DateUtils.getChinaTime());
		user.setUpdateUserAccount(curAccount);
		if(userMapper.update(user) == 1){
			return this.updateRole(user,roleIds);
		}
		return 0;
	}
	public int updateRole(User user,String roleIds){
		return userMapper.updateRole(user.getUserId(),roleIds);
	}

}
