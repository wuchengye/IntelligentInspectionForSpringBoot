package com.bda.bdaqm.shiro.realm;


import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.bda.bdaqm.admin.service.PermisService;
import com.bda.bdaqm.admin.service.MyRoleService;
import com.bda.bdaqm.admin.service.UserOprService;
import com.bda.bdaqm.admin.model.User;

public class MyRealm extends AuthorizingRealm {
	public static final String CACHE_NAME_1 = "com.bda.shiro.realms.Realm.authenticationCache";
	public static final String CACHE_NAME_2 = "com.bda.shiro.realms.Realm.authorizationCache";
	@Autowired
	private UserOprService userOprService;
	@Autowired
	private MyRoleService roleService;
	@Autowired
	private PermisService permissionService;
	
	public MyRealm() {
		this.setName(this.getClass().getName());
	}
	
    @Autowired
    public void setSecurityService(UserOprService userService) {
        this.userOprService = userService;
    }

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.fromRealm(this.getName()).iterator().next();
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<String> roleIds = this.roleService.getRoleIdsByUserId(user.getUserId());
			info.addRoles(roleIds);
			List<String> permissions = this.permissionService.getPermissionByUserId(user.getUserId());
			info.addStringPermissions(permissions);
			return info;
		} else {
			return null;
		}
//		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		System.out.println("login流程");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authcToken;
        String username = usernamePasswordToken.getUsername();
		System.out.println("login流程用户名" + username);
		StringBuffer pwd = new StringBuffer();
		for(char i : usernamePasswordToken.getPassword()){
			pwd.append(i);
		}
		System.out.println("login流程密码" + pwd);
        User user = userOprService.getUserByAccount(username);
        // 4、若用户不存在，则抛出UnknownAccountException异常
        if (null == username){
			System.out.println("login流程异常");
            throw new UnknownAccountException("用户不存在");
        }
		return (user != null && user.getStatus().equals("1")) ? new SimpleAuthenticationInfo(user, user.getPassword(), this.getName()) : null;
	}

	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		//User user = (User) principals.getPrimaryPrincipal();
		return "shiro.auth.User:" + principals.getPrimaryPrincipal();
	}

}
