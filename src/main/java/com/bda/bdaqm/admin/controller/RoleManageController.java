package com.bda.bdaqm.admin.controller;

import java.util.*;

import com.bda.admin.model.Resource;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.DepartmentModel;
import com.bda.bdaqm.admin.service.*;
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
	@Autowired
	private DepartmentService departmentService;

	@ResponseBody
	@RequestMapping("/getRoles")
	public Result getRoles(Page page,
						   @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
						   @RequestParam(value = "departmentId",defaultValue = "",required = false)String departmentId) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		//返回result
		List<Role> result = new ArrayList<>();
		if (keyword.equals("")) {
			if(departmentId.equals("")){
				return Result.failure();
			}
			result = roleService.getRolesByDepartmentIdOrName(departmentId,"");
		}else {
			List<Role> keywords = roleService.getRolesByDepartmentIdOrName("", keyword);
			if (keywords.size() > 0) {
				Role role = roleService.selectRoleByUserId(user.getUserId());
				if (role != null) {
					List<DepartmentModel> departments = new ArrayList<>();
					if (role.getAbility() == 3) {
						departments = departmentService.getDepartmentAndChildren(user.getDepartmentId());
					}
					if (role.getAbility() == 2) {
						departments.add(departmentService.selectDepartmentById(user.getDepartmentId()));
					}
					for (Role role1 : keywords) {
						for (DepartmentModel d : departments) {
							if (role1.getDepartmentId() == d.getDepartmentId()) {
								result.add(role1);
								break;
							}
						}
					}
				}
			}
		}
		List<Role> resList = new ArrayList<Role>();
		if (result != null && !result.isEmpty()) {
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNum();
			int start = (pageNo - 1) * pageSize;
			int max = 0;
			if (start + pageSize > result.size()) {
				max = result.size();
			} else {
				max = start + pageSize;
			}
			for (int i = start; i < max; i++) {
				resList.add(result.get(i));
			}
		}
		PageInfo<Role> pager = new PageInfo<Role>(result);
		return Result.success(new DataGrid(resList, pager.getTotal()));
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

	@ResponseBody
	@RequestMapping("/searchUsers")
	public Result searchUsers(@RequestParam(value = "keyword" ,defaultValue = "",required = false)String keyword,
							  @RequestParam(value = "select" ,defaultValue = "",required = false)String select,
							  @RequestParam(value = "roleId",required = true)String roleId,
							  Page page){
		//确定角色详情，获取部门id,查找该部门下的用户
		Role role = roleService.getRoleById(roleId);
		List<Map<String,Object>> users = userService.selectUsersByDepartmentIdOrName(String.valueOf(role.getDepartmentId()),"");
		//查找角色对应的用户
		List<String> userIds = userRoleService.getUserIdByRoleId(roleId);
		//结果集
		List<Map<String,Object>> result = new ArrayList<>();
		switch (select) {
			case "isRole"://已分配
				for (Map u : users) {
					if (((String) u.get("roleId")) != null && !((String) u.get("roleId")).equals("")) {
						Map<String, Object> map = new HashMap<>();
						map.put("isCurrentRole", false);
						map.put("userId", u.get("userId"));
						map.put("userName", u.get("userName"));
						if (((String) u.get("roleId")).equals(roleId)) {
							map.put("isCurrentRole", true);
						}
						result.add(map);
					}
				}
				break;
			case "noRole"://未分配
				for (Map u : users) {
					if (((String) u.get("roleId")) == null || ((String) u.get("roleId")).equals("")) {
						Map<String, Object> map = new HashMap<>();
						map.put("isCurrentRole", null);
						map.put("userId", u.get("userId"));
						map.put("userName", u.get("userName"));
						result.add(map);
					}
				}
				break;
			default://全部
				for (Map u : users) {
					Map<String, Object> map = new HashMap<>();
					map.put("userId", u.get("userId"));
					map.put("userName", u.get("userName"));
					if (((String) u.get("roleId")) != null && !((String) u.get("roleId")).equals("")) {
						map.put("isCurrentRole", false);
						if (((String) u.get("roleId")).equals(roleId)) {
							map.put("isCurrentRole", true);
						}
					} else {
						map.put("isCurrentRole", null);
					}
					result.add(map);
				}
		}
		if(!keyword.equals("")){
			List<Map<String,Object>> tempMap = new ArrayList<>();
			for(Map m : result){
				if(((String)m.get("userName")).equals(keyword)){
					tempMap.add(m);
				}
			}
			result.clear();
			result.addAll(tempMap);
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

	@ResponseBody
	@RequestMapping("/updateUsers")
	public Result updateUsers(@Param(value = "roleId") String roleId,
			@RequestParam(value = "cancel", defaultValue = "",required = false) List<String> cancel,
			@RequestParam(value = "confirm", defaultValue = "",required = false) List<String> confirm) {
		int ret = 0;
		if (cancel.isEmpty() && confirm.isEmpty()) {
			return Result.failure(ResultCode.NO_UPDATE);
		} else {
			if (!cancel.isEmpty()) {
				ret += this.roleService.deleteUsers(roleId, cancel);
			}

			if (!confirm.isEmpty()) {
				ret += this.roleService.addUsers(roleId, confirm);
			}
			return ret > 0 ? Result.success(ret) : Result.failure(ResultCode.UPDATE_FAILURE);
		}
	}
	
}
