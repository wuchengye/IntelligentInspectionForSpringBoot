package com.bda.bdaqm.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.PermisService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bda.admin.bean.PMenu;
import com.bda.admin.bean.ResourceAction;
import com.bda.admin.permissionhelper.PermissionPriority;
import com.bda.bdaqm.admin.model.Menu;
import com.bda.bdaqm.admin.model.RolePermission;
import com.bda.bdaqm.admin.service.MyMenuService;
import com.bda.bdaqm.admin.service.MyRolePermissionService;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.MenuTreeNode;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("admin/qmauth")
public class QmAuthController extends BaseController{
	@Autowired
	private MyMenuService myMenuService;
	@Autowired
	MyRolePermissionService rolePermissionService;
	@Autowired
	private PermisService permisService;
	
	/*@RequestMapping("/getMenu")
	@ResponseBody
	public Result getMenu() {
		List<Menu> menus = myMenuService.selectAllRecursiveByParentId("0");
		List<Menu> menus2 = setSubMenus(menus);
//		List<Menu> menus = menuService.selectAllRecursiveByParentId("0");
		//return filterPermissionfromMenu(menus2);
		return Result.success(menus2);
		//return adminController.getMenu();
	}
	
	public static List<Menu> setSubMenus(List<Menu> menus) {
		List<Menu> rootMenu = new ArrayList<Menu>();
		for(Menu menu:menus){
			if("0".equals(menu.getParentId())){
				rootMenu.add(menu);
			}
		}
		for(Menu menu:rootMenu){
			List<Menu> child = getChild(menu.getMenuId(),menus);
			menu.setSubMenu(child);
		}
		return rootMenu;
	}
	
	public static List<Menu> getChild(String menuId,List<Menu> menus){
		List<Menu> childMenu = new ArrayList<Menu>();
		for(Menu menu:menus){
			if(menuId.equals(menu.getParentId())){
				childMenu.add(menu);
			}
		}
		for(Menu menu:childMenu){
			menu.setSubMenu(getChild(menu.getMenuId(),menus));
		}
		if(childMenu.size()==0){
			return new ArrayList<Menu>();
		}
		return childMenu;
	}
	
	public static List<MenuTreeNode> filterPermissionfromMenu(List<Menu> menus) {
		ArrayList<MenuTreeNode> nodes = new ArrayList<MenuTreeNode>();
		Subject subject = SecurityUtils.getSubject();
		String permission = "Menu:view:";
		Iterator<Menu> arg3 = menus.iterator();

		while (arg3.hasNext()) {
			Menu menu = (Menu) arg3.next();
			String p = permission + menu.getMenuId();
			if (subject.isPermitted(p)) {
				MenuTreeNode node = new MenuTreeNode();
				node.setId(menu.getMenuId());
				node.setText(menu.getMenuName());
				node.setUrl(menu.getMenuUrl());
				node.setIcon(menu.getIcon());
//				if(menu.getSubMenu() == null){
//					menu.setSubMenu(new ArrayList<>());
//				}
				if (!menu.getSubMenu().isEmpty()) {
					node.setChildren(filterPermissionfromMenu(menu.getSubMenu()));
				}

				nodes.add(node);
			}
		}

		return nodes;
	}*/
	
	@RequestMapping("/config")
	@ResponseBody
	public Result config(String roleId) {
		//ModelAndView mav = new ModelAndView("admin/menu/config");
		//mav.addObject("roleId", roleId);
		//return mav;
		return Result.success(new HashMap<String,String>().put("roleId",roleId));
	}
	
	@RequestMapping("/loadPerms")
	@ResponseBody
	public Result loadPerms(@RequestParam(defaultValue = "0") String id,
			@RequestParam(value = "roleId", required = false) String roleId, Page page) {
		List<PMenu> list = null;
		if (!StringUtils.equalsIgnoreCase(id, "0")) {
			list = this.myMenuService.selectMenusForPerms(id, roleId, 0, 0);
			//return list;
			return Result.success(list);
		} else {
			list = this.myMenuService.selectMenusForPerms(id, roleId, page.getPageNum(), page.getPageSize());
			PageInfo<PMenu> pageInfo = new PageInfo<PMenu>(list);
			//return new DataGrid(list, pageInfo.getTotal());
			return Result.success(new DataGrid(list, pageInfo.getTotal()));
		}
	}
	
	@RequestMapping("savePerms")
	@ResponseBody
	public Result savePerms(@RequestBody List<ResourceAction> list) {
		byte ret = 0;
		HashSet<RolePermission> addRps = new HashSet<RolePermission>();
		HashSet<RolePermission> deleteRps = new HashSet<RolePermission>();
		Iterator<ResourceAction> arg7 = list.iterator();

		while (arg7.hasNext()) {
			ResourceAction rc = (ResourceAction) arg7.next();
			List<String> adds = this.myMenuService.getFullMenusId(rc.getAdds());
			Iterator<String> arg9 = adds.iterator();

			List<String> actions;
			String id;
			Iterator<String> arg11;
			String action;
			while (arg9.hasNext()) {
				id = (String) arg9.next();
				actions = PermissionPriority.getMaxPermissionActions(rc.getAction());
				arg11 = actions.iterator();

				while (arg11.hasNext()) {
					action = (String) arg11.next();
					addRps.add(new RolePermission(rc.getRoleId(), action, "Menu", id));
				}
			}

			List<String> deletes = this.myMenuService.getFullMenusId(rc.getDeletes());
			arg9 = deletes.iterator();

			while (arg9.hasNext()) {
				id = (String) arg9.next();
				actions = PermissionPriority.getMinPermissionActions(rc.getAction());
				arg11 = actions.iterator();

				while (arg11.hasNext()) {
					action = (String) arg11.next();
					deleteRps.add(new RolePermission(rc.getRoleId(), action, "Menu", id));
				}
			}
		}

		int ret1 = ret + this.rolePermissionService.addPermission(addRps);
		ret1 += this.rolePermissionService.removePermission(deleteRps);
		//return new OperaterResult<Object>(ret1 > 0, ret1 > 0 ? "" : "没有添加或者删除任何权限");
		return ret1 > 0 ? Result.success() : Result.failure(ResultCode.NO_PREMISSION_TO_OPRATION);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(@RequestParam(required = false) String menuId,
			@RequestParam(defaultValue = "0") String parentId) {
		//ModelAndView mav = new ModelAndView("admin/menu/edit");
		Map<String,Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(menuId)) {
			Menu menu = this.myMenuService.selectByPrimaryKey(menuId);
			parentId = menu.getParentId();
			//mav.addObject("menu", menu);
			map.put("menu",menu);
		}
		//mav.addObject("parentId", parentId);
		map.put("parentId",parentId);
		return Result.success(map);
	}
	
	@RequestMapping("/searchMenus")
	@ResponseBody
	public Result searchMenus() {
		List<Menu> menus = myMenuService.selectAllMenu();
		return Result.success(menus);
	}
	
	private static List<Map<String, Object>> covert2NodefromMenu(List<Menu> menus) {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		HashMap<String, Object> map;
		for (Iterator<Menu> arg1 = menus.iterator(); arg1.hasNext(); list.add(map)) {
			Menu menu = (Menu) arg1.next();
			map = new HashMap<String, Object>();
			map.put("menuId", menu.getMenuId());
			map.put("menuName", menu.getMenuName());
			map.put("menuUrl", menu.getMenuUrl());
			map.put("parentId", menu.getParentId());
			if ("1".equalsIgnoreCase(menu.getType())) {
				map.put("state", "closed");
			}
		}

		return list;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Result save(@RequestParam(value = "menu") Menu menu,
					   @RequestParam(value = "roleId")String roleId) {
		int ret = 0;

		try {
			if (StringUtils.isEmpty(menu.getParentId())) {
				menu.setParentId("0");
			}

			if (StringUtils.isEmpty(menu.getMenuId())) {
				ret = this.myMenuService.addSelective(menu);
				if(ret > 0){
					permisService.addOnePermis(roleId,String.valueOf(ret));
				}
			} else {
				ret = this.myMenuService.updateByPrimaryKeySelective(menu);
			}
		} catch (Exception arg3) {
			this.logger.error(arg3.getLocalizedMessage());
		}
		return Result.success(new OperaterResult<Object>(ret > 0));
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public Result del(@RequestParam("ids[]") List<String> ids) {
		this.myMenuService.deleteByPrimaryKeys(ids);
		permisService.deleteMenus(ids);
		return Result.success(new OperaterResult<Object>(true, "删除成功"));
	}
	
}
