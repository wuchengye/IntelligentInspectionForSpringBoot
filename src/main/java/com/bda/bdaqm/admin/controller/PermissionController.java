package com.bda.bdaqm.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bda.admin.model.Permission;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.admin.model.Menu;
import com.bda.bdaqm.admin.service.MyMenuService;
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
	@Autowired
	private MyMenuService myMenuService;
	
	@RequestMapping("/editPermission")
	@ResponseBody
	public Result editPermission(@RequestParam(value = "roleId", required = false) String roleId){
		//ModelAndView mav = new ModelAndView("admin/permission/index");
		//mav.addObject("roleId", roleId);
		return Result.success(new HashMap<String,String>().put("roleId",roleId));
	}
	
	@RequestMapping("/getPermission")
	@ResponseBody
	public Result getPermission(@RequestParam(value = "roleId", required = false) String roleId,
								@RequestParam(value = "userRoleId")String userRoleId){
 		//List<RolePermission> result = permisService.getPermisByRoleId(roleId,userRoleId);
		Map<String,List<RolePermission>> map = permisService.getPermisByRoleId(roleId,userRoleId);
		List<Menu> menus = new ArrayList<>();
		for (RolePermission rolePer : map.get("userRole")){
			menus.add(myMenuService.selectMenuById(rolePer.getPermission()));
		}
		return Result.success(map.get("role"),menus);
	}
	
	@RequestMapping("/savePermission")
	@ResponseBody
	public Result savePermission(@RequestParam(value = "roleId")String roleId,
			@RequestParam(value = "permis")List<String> permis
			/*@RequestParam PermissionBean per*/){
		/*int result =  permisService.savePermission(per);
		return Result.success(result);
		//return new OperaterResult<>(result >= 0);*/
		int result = permisService.savePermission(roleId,permis);
		return result > 0 ? Result.success() : Result.failure();
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(@RequestParam(value = "name",required = true)String name){
		return name;
	}
}
