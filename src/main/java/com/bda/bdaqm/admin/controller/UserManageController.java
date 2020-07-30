package com.bda.bdaqm.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.DepartmentModel;
import com.bda.bdaqm.admin.model.Menu;
import com.bda.bdaqm.admin.service.*;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.User;
import com.bda.common.bean.OperaterResult;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping({"usermanage/user"})
@Api(tags = {"用户管理"})
public class UserManageController {

	@Autowired
	private UserOprService userService;
	@Autowired
	private MyRoleService roleService;
	@Autowired
	private MyMenuService myMenuService;
	@Autowired
	private PermisService permisService;
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/getUsers")
	public Result getUsers(Page page,
						   @RequestParam(value = "keyword",defaultValue = "",required = false)String keyword,
						   @RequestParam(value = "departmentId",defaultValue = "",required = false)String departmentId){
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		//结果集
		List<Map<String,Object>> resList = new ArrayList<>();
		//判断keyword
		if(keyword.equals("")){
			if(departmentId.equals("")){
				return Result.failure();
			}
			resList = userService.selectUsersByDepartmentIdOrName(departmentId,"");
		}else{
			List<Map<String,Object>> keywords = userService.selectUsersByDepartmentIdOrName("",keyword);
			if(keywords.size() > 0){
				Role role = roleService.selectRoleByUserId(user.getUserId());
				if (role != null) {
					List<DepartmentModel> departments = new ArrayList<>();
					if(role.getAbility() == 3) {
						departments = departmentService.getDepartmentAndChildren(user.getDepartmentId());
					}
					if(role.getAbility() == 2){
						departments.add(departmentService.selectDepartmentById(user.getDepartmentId()));
					}
					for (Map map : keywords) {
						for (DepartmentModel d : departments) {
							if ((int) map.get("departmentId") == d.getDepartmentId()) {
								resList.add(map);
								break;
							}
						}
					}
				}
			}
		}
		List<Map<String,Object>> results = new ArrayList<>();
		if( resList!=null && !resList.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > resList.size() ) {
				max = resList.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				results.add(resList.get(i));
			}
		}
		return Result.success(results,resList.size());
	}

	/**
	 * 重写user的controller方法
	 * 修改用户,增加员工科室和班组的信息回显
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(@RequestParam(required = false) String userId,
							 @RequestParam(required = false) String editPasFlag,
							 @RequestParam(required = false) String indexEditPasFlag) {
		Map<String,Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(userId)) {
			User user = userService.selectByPrimaryKey(userId);
			map.put("user",user);
			if (!StringUtils.isEmpty(editPasFlag)) {
				map.put("edit","editPas");
			}
		}else if(!StringUtils.isEmpty(indexEditPasFlag)){
			map.put("edit","editPas");
		}
		return Result.success(map);
	}
	
	/**
	 * 重写user的controller方法
	 * 新增用户,增加员工科室和班组的信息填写
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Result save(User user,
						@RequestParam(name = "roleIds", required = false) List<String> roleIds) {
		// 在新增或保存前检查用户是否已经存在
		User userCheck = userService.getUserByAccount(user.getAccount());
		if (userCheck != null) {
			if(user.getStatus().equals("3")){
				int flag = userService.deleteByPrimaryKeys(user.getUserId());
				userService.deleteUserRoleByUserId(user.getUserId());
				return flag > 0 ? Result.success() : Result.failure();
			}
			if(user.getUserId() == null){
				return Result.failure(ResultCode.HAVE_ACCOUNT);
			}else {
				int i = userService.update(user,roleIds.get(0));
				return i == 1 ? Result.success() : Result.failure();
			}
		}
		user = userService.save(user, roleIds);
		return user != null ? Result.success() : Result.failure();
	}
	
	@ResponseBody
	@RequestMapping("/getUserRole")
	public Result getUserRole(@RequestParam(required = true) String userId) {
		Map<String, Object> resp = new HashMap<String, Object>();
		//获取根据当前用户获取角色
		Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal();
		List<Role> list = roleService.selectRoleByUser(currentUser);
		List<String> roldIdList = roleService.getRoleIdsByUserId(userId);
		PageInfo<Role> pages = new PageInfo<Role>(list);
		resp.put("rows", list);
		resp.put("total", pages.getTotal());
		resp.put("userRoleIds", roldIdList);
		//return resp;
		return Result.success(resp);
	}
	
	/**
	 * 重写user的controller方法
	 * 删除用户同时需要删除用户详细信息数据(科室,班组)
	 */
	@ResponseBody
	@RequestMapping("/del")
	public Result del(@RequestParam("ids[]") List<String> ids,
						@RequestParam("accounts[]") List<String> accounts) {
		int ret = 0;
		for (int i = 0; i < ids.size(); i++) {
			ret += userService.deleteByPrimaryKeys(ids.get(i));
			userService.deleteUserRoleByUserId(ids.get(i));
		}
		return Result.success(new OperaterResult<String>(ret > 0));
	}
	
	@ResponseBody
	@RequestMapping("/editPas")
	public Result editPas(	@RequestParam("id") String userId,
							@RequestParam("newPas") String newPas,
							@RequestParam(required = false) String indexEditPasFlag,
							String oldPas
							) {
		int ret = 0;
		if(!StringUtils.isEmpty(indexEditPasFlag) && (userId==null || "".equals(userId))){
			Subject subject = SecurityUtils.getSubject();
			User currentUser = (User) subject.getPrincipal();
			if(oldPas!=null && oldPas.equals(currentUser.getPassword())){
				ret = userService.editUserPas(currentUser.getUserId()+"", newPas);
				return ret > 0 ? Result.success() : Result.failure();
			}else{
				return Result.failure();
			}
		}else{
			User targetUser = userService.selectByPrimaryKey( userId);
			if(targetUser!=null && oldPas.equals(targetUser.getPassword())){
				ret = userService.editUserPas(userId, newPas);
			}
		}
		return ret > 0 ? Result.success() : Result.failure(ResultCode.WRONG_PASSWORD);
	}
	
	/**
	 * 用户管理重置密码
	 * @param ids
	 * @return
	 */
	@RequestMapping("/resetUser")
	@ResponseBody
	public Result resetUser(@RequestParam("ids[]") List<String> ids){
		int result = userService.resetUserPasswordByUserIds(ids);
		return Result.success(new OperaterResult<>(result>0,"info","msg"));
	}

	@ResponseBody
	@RequestMapping("/login")
	public Result login(){
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		if(user == null){
			return Result.failure(ResultCode.OVERRIDE_NOT_LOGIN_CODE);
		}else {
			return Result.failure(ResultCode.OVERRIDE_LOGIN_CODE);
		}
	}

	@ResponseBody
	@RequestMapping("/loginSuccess")
	public Result loginSuccess(){
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		//角色、角色权限和菜单

			List<String> list = roleService.getRoleIdsByUserId(user.getUserId());
			String roleId;
			if(list == null || list.size() == 0){
				roleId = "";
			}else {
				roleId = list.get(0);
			}
			map.put("roleId",roleId);
			if (!roleId.equals("")){
				List<String> permissions = permisService.getPermissionByUserId(user.getUserId());
				map.put("permission",permissions);
				List<Menu> menus = new ArrayList<>();
				if(permissions.size() != 0){
					List<Menu> allMenus = myMenuService.selectAllMenu();
					for (String permis : permissions){
						for (Menu menu : allMenus){
							if(permis.equals(menu.getMenuId())){
								menus.add(menu);
								break;
							}
						}
					}
				}
				map.put("menu",menus);
			}

		return Result.success(user,map);
	}

	@ResponseBody
	@RequestMapping("/logout")
	public Result logout(){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
			return Result.success();
		}
		return Result.failure();
	}
	
}
