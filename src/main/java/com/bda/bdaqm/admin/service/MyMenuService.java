package com.bda.bdaqm.admin.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bda.bdaqm.admin.model.Menu;
import com.bda.common.service.AbstractService;
import com.bda.common.util.StringUtil;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

import com.bda.admin.bean.PMenu;
import com.bda.bdaqm.admin.mapper.MyMenuMapper;

@Service("myMenuService")
public class MyMenuService extends AbstractService<Menu>{

	@Autowired
	private MyMenuMapper mapper;
	
	public int addSelective(Menu menu){
		return mapper.addSelective(menu);
	}
	
	public List<Menu> selectAllRecursiveByParentId(String parentId) {
		
		return mapper.selectAllRecursiveByParentId(parentId);
	}
	
	public List<PMenu> selectMenusForPerms(String parentId, String roleId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.mapper.selectMenusForPerms(parentId, roleId);
	}
	
	public List<String> getFullMenusId(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		} else {
			for (int parentCounter = 0; parentCounter < ids.size(); ++parentCounter) {
				List<String> childsMenuId = this.mapper.selectChildsMenuIdByParentId((String) ids.get(parentCounter));
				if (!CollectionUtils.isEmpty(childsMenuId)) {
					int childCounter;
					for (childCounter = 0; childCounter < childsMenuId.size()
							&& !ids.contains(childsMenuId.get(childCounter)); ++childCounter) {
						;
					}

					if (childCounter == childsMenuId.size()) {
						ids.addAll(childsMenuId);
					}
				}
			}

			return ids;
		}
	}

	public List<Menu> selectByParentId(String keyword, String parentId, int pageNum, int pageSize) {
		Example example = new Example(Menu.class);
		if (!StringUtils.isEmpty(keyword)) {
			keyword = StringUtil.keywords(keyword);
			example.createCriteria().andLike("menuName", keyword);
		}

		if (!StringUtils.isEmpty(parentId)) {
			example.createCriteria().andEqualTo("parentId", parentId);
		}

		PageHelper.orderBy("menu_order");
		PageHelper.startPage(pageNum, pageSize);
		return this.mapper.selectByExample(example);
	}

	/*新增*/
	public List<Menu> selectAllMenu(){
		return mapper.selectAllMenu();
	}

	public Menu selectMenuById(String menuId){
		return mapper.selectMenuById(menuId);
	}
}
