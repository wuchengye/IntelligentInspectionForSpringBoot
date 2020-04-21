package com.bda.bdaqm.admin.controller;

import java.util.HashMap;
import java.util.List;

import com.bda.bdaqm.RESTful.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bda.bdaqm.admin.model.RolePermission;
import com.bda.bdaqm.admin.bean.PermissionBean;
import com.bda.bdaqm.admin.service.PermisService;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;

@RestController
@RequestMapping("admin/permission")
public class PermissionController extends BaseController {
	@Autowired
	private PermisService permisService;
	
	@RequestMapping("/editPermission")
	@ResponseBody
	public Result editPermission(@RequestParam(value = "roleId", required = false) String roleId){
		//ModelAndView mav = new ModelAndView("admin/permission/index");
		//mav.addObject("roleId", roleId);
		return Result.success(new HashMap<String,String>().put("roleId",roleId));
	}
	
	@RequestMapping("getPermission")
	@ResponseBody
	public Result getPermission(@RequestParam(value = "roleId", required = false) String roleId){
 		List<RolePermission> result = permisService.getPermisByRoleId(roleId);
		return Result.success(result);
	}
	
	@RequestMapping("savePermission")
	@ResponseBody
	public Result savePermission(
			@RequestBody PermissionBean per
			){
		int result =  permisService.savePermission(per);
		return Result.success(result);
		//return new OperaterResult<>(result >= 0);
	}
	
	@RequestMapping("test")
	@ResponseBody
	public String test(@RequestParam(value = "name",required = true)String name){
		return name;
	}
}
