package com.bda.bdaqm.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bda.common.bean.OperaterResult;
import com.bda.common.service.AbstractService;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;

public class BaseController<T> extends com.bda.common.controller.BaseController{

	@Autowired
	protected AbstractService<T> baseService;
	
	@ResponseBody
	@RequestMapping("get")
	public Object get(Page page, @RequestParam(value="keys[]", required = false)List<String> keys
			, @RequestParam(value="values[]", required = false)List<Object> values) {
		List<T> list;
		if(keys == null || keys.isEmpty() || values == null || values.isEmpty()){
			list = baseService.selectPage(page.getPageNum(), page.getPageSize());
		} else {
			list = baseService.selectByField(keys.get(0), (String)values.get(0), page.getPageNum(), page.getPageSize());
		}
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return new DataGrid(list, pageInfo.getTotal());
	}
	
	@RequestMapping("add")
	public ModelAndView add() {
		String ctxPath = request.getContextPath();
		String uri = request.getRequestURI();
		uri = uri.substring(ctxPath.length(), uri.length()-3);
		if (!uri.endsWith("/")) {
			uri += "/";
		}
		uri += "edit";
		return new ModelAndView(uri);
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(String id) {
		ModelAndView mv = new ModelAndView();
		T entity = baseService.selectByPrimaryKey(id);
		mv.addObject("Entity", entity);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("insert")
	public Object insert(T entity) {
		int res = baseService.addSelective(entity);
		return new OperaterResult<>(res > 0);
	}
	
	@ResponseBody
	@RequestMapping("update")
	public Object update(T entity) {
		int res = baseService.updateByPrimaryKeySelective(entity);
		return new OperaterResult<>(res > 0);
	}
	
	@ResponseBody
	@RequestMapping("del")
	public Object del(@RequestParam("ids[]") List<String> ids) {
		int res = baseService.deleteByPrimaryKeys(ids);
		return new OperaterResult<>(res > 0);
	}
}
