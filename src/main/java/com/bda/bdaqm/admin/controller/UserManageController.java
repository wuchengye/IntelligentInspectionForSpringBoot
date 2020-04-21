package com.bda.bdaqm.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import io.swagger.annotations.Api;
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

import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.MyRoleService;
import com.bda.bdaqm.admin.service.UserOprService;
import com.bda.common.bean.OperaterResult;
import com.bda.easyui.bean.DataGrid;
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
	
	@ResponseBody
	@RequestMapping("/getUsers")
	public Result getUsers(Page page,@RequestParam(required = false) String keyword){
		List<User> data = this.userService.getUsers(keyword);
		List<User> resList = new ArrayList<User>();
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
		PageInfo<User> pager = new PageInfo<User>(data);
		//return new DataGrid(resList, pager.getTotal());
		return Result.success(new DataGrid(resList, pager.getTotal()));
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
		//ModelAndView mav = new ModelAndView("admin/user/edit");
		Map<String,Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(userId)) {
			User user = userService.selectByPrimaryKey(userId);
			//UserDetail userDetail = userDetailService.selectOneByEqField("userAccount", user.getAccount());
			//mav.addObject("userDetail", userDetail);
			//mav.addObject("user", user);
			map.put("user",user);
			if (!StringUtils.isEmpty(editPasFlag)) {
				//mav.setViewName("admin/user/editPas");
				map.put("edit","editPas");
			}
		}else if(!StringUtils.isEmpty(indexEditPasFlag)){
			//mav.setViewName("admin/user/editPas");	//由主页面直接进来的修改密码的
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
			//return new OperaterResult<String>(false, "账户已存在");
			if(user.getUserId() == null){
				return Result.failure(ResultCode.HAVE_ACCOUNT);
			}else {
				int i = userService.update(user,roleIds.get(0));
				return i == 1 ? Result.success() : Result.failure();
			}
		}
		
		user = userService.save(user, roleIds);
		//return new OperaterResult<String>(user != null);
		return user != null ? Result.success() : Result.failure();
	}
	
	@ResponseBody
	@RequestMapping("/getUserRole")
	public Result getUserRole(@RequestParam(required = true) String userId, Page page) {
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
		//return new OperaterResult<String>(ret > 0);
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
			//主页面进来修改密码的所以需要获取当前登录用户
			Subject subject = SecurityUtils.getSubject();
			User currentUser = (User) subject.getPrincipal();
			//User User = userService.selectOneByEqField("USER_ID",currentUser.getUserId());
			if(oldPas!=null && oldPas.equals(currentUser.getPassword())){
				ret = userService.editUserPas(currentUser.getUserId()+"", newPas);
				//return new OperaterResult<String>(ret>0);
				return ret > 0 ? Result.success() : Result.failure();
			}else{
				//return new OperaterResult<String>(false);
				return Result.failure();
			}
		}else{
//			newPas = UserUtil.Sha256(newPas);
			User targetUser = userService.selectOneByEqField("userId", userId);
			if(targetUser!=null && oldPas.equals(targetUser.getPassword())){
				ret = userService.editUserPas(userId, newPas);
			}
			
		}
		//return new OperaterResult<String>(ret > 0);
		return ret > 0 ? Result.success() : Result.failure();
	}
	
	/**
	 * 用户管理重置密码
	 * @param ids
	 * @return
	 */
	@RequestMapping("/resetUser")
	@ResponseBody
	public Result resetUser(@RequestParam("ids[]") List<String> ids){
		//userService.update(entity)
		int result = userService.resetUserPasswordByUserIds(ids);
		//return new OperaterResult<>(result>0,"info","msg");
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
		return Result.success(user);
	}
	
}
