package com.bda.bdaqm.admin.controller;

import java.util.*;

import com.bda.admin.model.Resource;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import org.apache.commons.lang.StringUtils;
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
	
	@ResponseBody
	@RequestMapping("/getRoles")
	public Result getRoles(Page page, @RequestParam(required = false) String keyword){
		List<Role> data = this.roleService.selectRoleExceoptAdmin("y", keyword);
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
			ret1 = this.roleService.addSelective(role);
		} else {
			role.setUpdateTime(DateUtils.getChinaTime());
			role.setUpdateUserAccount(curAccount);
			ret1 = this.roleService.updateByPrimaryKeySelective(role);
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

	@ResponseBody
	@RequestMapping("/searchUsers")
	public Result searchUsers(String roleId, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "all") String filter, Page page) {
		List<VRoleUser> list = this.roleService.searchUsers(roleId, keyword, filter, page.getPageNum(), page.getPageSize());
		PageInfo<VRoleUser> pageInfo = new PageInfo<VRoleUser>(list);
		//return new DataGrid(list, pageInfo.getTotal());
		return Result.success(new DataGrid(list,pageInfo.getTotal()));
	}

	@ResponseBody
	@RequestMapping("/updateUsers")
	public Result updateUsers(String roleId,
			@RequestParam(value = "addUserIds[]", defaultValue = "") List<String> addUserIds,
			@RequestParam(value = "delUserIds[]", defaultValue = "") List<String> delUserIds) {
		int ret = 0;
		if (delUserIds.isEmpty() && addUserIds.isEmpty()) {
			//return new OperaterResult<Object>(true, "没有任何更新");
			return Result.failure(ResultCode.NO_UPDATE);
		} else {
			if (!delUserIds.isEmpty()) {
				ret += this.roleService.deleteUsers(roleId, delUserIds);
			}

			if (!addUserIds.isEmpty()) {
				ret += this.roleService.addUsers(roleId, addUserIds);
			}

			//return new OperaterResult<Object>(ret > 0, "完成更新");
			return ret > 0 ? Result.success() : Result.failure(ResultCode.UPDATE_FAILURE);
		}
	}
	
}
