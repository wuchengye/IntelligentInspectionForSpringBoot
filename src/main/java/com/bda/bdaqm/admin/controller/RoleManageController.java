package com.bda.bdaqm.admin.controller;

import java.util.*;

import com.bda.admin.model.Resource;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.service.UserOprService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bda.admin.model.view.VRoleUser;
import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.MyRolePermissionService;
import com.bda.bdaqm.admin.service.MyRoleService;
import com.bda.bdaqm.admin.service.MyUserRoleService;
import com.bda.bdaqm.util.DateUtils;
import com.bda.common.bean.OperaterResult;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping({"rolemanage/role"})
public class RoleManageController {

	@Autowired
	private MyRoleService roleService;
	@Autowired
	private MyRolePermissionService rolePermissionService;
	@Autowired
	private MyUserRoleService userRoleService;
	@Autowired
	private UserOprService userService;

	@ResponseBody
	@RequestMapping("/getRoles")
	public Result getRoles(Page page/*, @RequestParam(required = false) String keyword*/){
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		//查询当前用户是否创建过角色
		List<Role> createList = roleService.getRolesByAccount(user.getAccount());
		//返回result
		List<Role> result = new ArrayList<>();
		if(createList != null){
			result.addAll(createList);
			//获取表中所有角色和用户
			List<Role> allRoles = roleService.getAllRole();
			List<User> allUsers = userService.getAllUsers();
			//被创建角色都有哪些用户
			for (Role role : createList){
				List<String> userIdList = userRoleService.getUserIdByRoleId(role.getRoleId());
				if(userIdList.size() == 0){
					continue;
				}
				result.addAll(selectAllRolesByUser(allRoles,allUsers,userIdList));
			}
		}
		List<Role> resList = new ArrayList<Role>();
		if( result!=null && !result.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > result.size() ) {
				max = result.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(result.get(i));
			}
		}
		PageInfo<Role> pager = new PageInfo<Role>(result);
		return Result.success(new DataGrid(resList, pager.getTotal()));

		/*List<Role> data = this.roleService.selectRoleExceoptAdmin("y", keyword);
		List<Role> resList = new ArrayList<Role>();
		if( data!=null && !data.isEmpty() ) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo-1) * pageSize;
			int max = 0;
			if( start+pageSize > data.size() ) {
				max = data.size();
			} else {
				max = start+pageSize;
			}
			for(int i=start; i<max; i++) {
				resList.add(data.get(i));
			}
		}
		PageInfo<Role> pager = new PageInfo<Role>(data);
		//return new DataGrid(resList, pager.getTotal());
		return Result.success(new DataGrid(resList, pager.getTotal()));*/
	}
	//递归用户创建的查询角色
	private List<Role> selectAllRolesByUser(List<Role> allRoles, List<User> allUsers,List<String> userIds){
		List<Role> result = new ArrayList<>();
		for(String uId : userIds){
			for(User u : allUsers){
				if(uId.equals(u.getUserId())){
					for(Role role : allRoles){
						if(role.getCreateUserAccount()!= null && u.getAccount().equals(role.getCreateUserAccount())){
							List<String> userIdList = userRoleService.getUserIdByRoleId(role.getRoleId());
							if (userIdList.size() != 0){
								result.addAll(selectAllRolesByUser(allRoles,allUsers,userIdList));
							}
							result.add(role);
						}
					}
					break;
				}
			}
		}
		return result;
	}



	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(@RequestParam(required = false) String roleId,
			@RequestParam(required = false) String name) {
		//ModelAndView mav = new ModelAndView("admin/role/edit");
		Map<String,Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(roleId)) {
			Role role = (Role) this.roleService.selectByPrimaryKey(roleId);
			//mav.addObject("role", role);
			map.put("role",role);
			if (!StringUtils.isEmpty(name)) {
				if (name.equals("editAuth")) {
					//mav.setViewName("admin/role/editAuth");
					map.put("name","editAuth");
				} else if (name.equals("editUserRole")) {
					//mav.setViewName("admin/role/editUserRole");
					map.put("name","editUserRole");
				}
			}
		}

		return Result.success(map);
	}
	
	@RequestMapping("/editPerms")
	@ResponseBody
	public Result editPerms(String roleId) {
		ModelAndView mav = new ModelAndView("admin/role/editPerms");
		mav.addObject("roleId", roleId);
		//return mav;
		return Result.success(new HashMap<String,String>().put("roleId",roleId));
	}

	@RequestMapping("/save")
	@ResponseBody
	public Result save(Role role) {
		Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal(); //当前用户
		String curAccount = currentUser.getAccount();//当前登陆账号
		int ret1;
		if (StringUtils.isEmpty(role.getRoleId())) {
			role.setCreateTime(DateUtils.getChinaTime());
			role.setCreateUserAccount(curAccount);
			ret1 = this.roleService.addRole(role);
		} else {
			role.setUpdateTime(DateUtils.getChinaTime());
			role.setUpdateUserAccount(curAccount);
			ret1 = this.roleService.updateRole(role);
		}

		//return new OperaterResult<Object>(ret1 > 0, "");
		return ret1 > 0 ? Result.success() : Result.failure();
	}

	@RequestMapping("/del")
	@ResponseBody
	public Result del(@RequestParam("ids[]") List<String> ids) {
		int ret = 0;

		try {
			ret = this.roleService.deleteByPrimaryKeys(ids);
			Iterator<String> e = ids.iterator();

			while (e.hasNext()) {
				String roleId = (String) e.next();
				this.rolePermissionService.deleteRolePermissionByRoleId(roleId);
				this.userRoleService.deleteUserRoleByRoleId(roleId);
			}
		} catch (Exception arg4) {
			arg4.printStackTrace();
		}

		//return new OperaterResult<Object>(ret > 0, "");
		return ret > 0 ? Result.success() : Result.failure();
	}

	@RequestMapping("/getResources")
	@ResponseBody
	public Result getResources(String roleId, String name, String[] primkeys) {
		//return new OperaterResult<>(true, "获取权限资源列表", this.roleService.getResources(roleId, name, primkeys));
		List<Resource> list = this.roleService.getResources(roleId, name, primkeys);
		if(list.size() == 0){
			return Result.failure(ResultCode.GET_ROLE_RESOURCE_FAILURE);
		}
		return Result.success(list);
	}

	/*@ResponseBody
	@RequestMapping("/searchUsers")
	public Result searchUsers(String roleId, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "all") String filter, Page page) {
		List<VRoleUser> list = this.roleService.searchUsers(roleId, keyword, filter, page.getPageNum(), page.getPageSize());
		PageInfo<VRoleUser> pageInfo = new PageInfo<VRoleUser>(list);
		//return new DataGrid(list, pageInfo.getTotal());
		return Result.success(new DataGrid(list,pageInfo.getTotal()));
	}*/
	@ResponseBody
	@RequestMapping("/searchUsers")
	public Result searchUsers(@RequestParam(value = "keyword" ,defaultValue = "",required = false)String keyword,
							  @RequestParam(value = "select" ,defaultValue = "",required = false)String select,
							  @RequestParam(value = "roleId",required = true)String roleId,
							  Page page){
		//查找角色对应的用户
		List<String> userIds = userRoleService.getUserIdByRoleId(roleId);
		//结果集
		List<Map<String,Object>> result = new ArrayList<>();
		switch (select){
			case "isRole"://已分配
				List<User> list = userService.getUserWithUserRole(keyword);
				if(list == null || list.size() == 0){
					return Result.success(result);
				}
				for(User user : list){
					if(user.getUserId().equals("10001")){
						continue;
					}
					Map<String,Object> map = new HashMap<>();
					map.put("isCurrentRole",false);
					for (String role : userIds){
						if(user.getUserId().equals(role)){
							map.put("isCurrentRole",true);
							break;
						}
					}
					map.put("userId",user.getUserId());
					map.put("userName",user.getUserName());
					result.add(map);
				}
				return Result.success(result);
			case "noRole"://未分配
				List<User> list1 = userService.getUserWithoutUserRole(keyword);
				if(list1 == null || list1.size() == 0){
					return Result.success(result);
				}
				for(User user : list1) {
					if(user.getUserId().equals("10001")){
						continue;
					}
					Map<String,Object> map = new HashMap<>();
					map.put("userId",user.getUserId());
					map.put("userName",user.getUserName());
					map.put("isCurrentRole",null);
					result.add(map);
				}
				return Result.success(result);
			default://全部
				List<Map<String,Object>> listAll = userService.getUserAndUserRole(keyword);
				for (Map<String,Object> map : listAll){
					if(map.get("userId").toString().equals("10001")){
						continue;
					}
					if (map.get("roleId") == null){
						map.put("isCurrentRole",null);
					}else {
						if(map.get("roleId").toString().equals(roleId)){
							map.put("isCurrentRole",true);
						}else {
							map.put("isCurrentRole",false);
						}
					}
					map.remove("roleId");
					result.add(map);
				}
				List<Map<String,Object>> resList = new ArrayList<>();
				if( result!=null && !result.isEmpty() ) {
					int pageSize = page.getPageSize();
					int pageNo = page.getPageNum();
					int start = (pageNo-1) * pageSize;
					int max = 0;
					if( start+pageSize > result.size() ) {
						max = result.size();
					} else {
						max = start+pageSize;
					}
					for(int i=start; i<max; i++) {
						resList.add(result.get(i));
					}
				}
				return Result.success(resList,result.size());
		}
	}

	@ResponseBody
	@RequestMapping("/updateUsers")
	public Result updateUsers(@Param(value = "roleId") String roleId,
			@RequestParam(value = "cancel", defaultValue = "",required = false) List<String> cancel,
			@RequestParam(value = "confirm", defaultValue = "",required = false) List<String> confirm) {
		int ret = 0;
		if (cancel.isEmpty() && confirm.isEmpty()) {
			//return new OperaterResult<Object>(true, "没有任何更新");
			return Result.failure(ResultCode.NO_UPDATE);
		} else {
			if (!cancel.isEmpty()) {
				ret += this.roleService.deleteUsers(roleId, cancel);
			}

			if (!confirm.isEmpty()) {
				ret += this.roleService.addUsers(roleId, confirm);
			}

			//return new OperaterResult<Object>(ret > 0, "完成更新");
			return ret > 0 ? Result.success(ret) : Result.failure(ResultCode.UPDATE_FAILURE);
		}
	}
	
}
