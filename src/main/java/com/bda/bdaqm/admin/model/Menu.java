package com.bda.bdaqm.admin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/**
 * @author mars
 */
@Table(name="bda_menu")
public class Menu implements Serializable{

	private static final long serialVersionUID = 2657151704705316308L;
	public static final String FIELD_MENU_NAME = "menuName";
	public static final String FIELD_MENU_ID = "menuId";
	public static final String FIELD_PARENT_ID = "parentId";
	public static final String TYPE_PARENT = "1";
	public static final String TYPE_LEAF = "0";
	public static final String ROOT_ID = "0";
	@Id
	private String menuId;
	private String icon;
	private String menuName;
	@Transient
	private String description;
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String menuUrl = "";
	private String parentId = "0";
	private String menuOrder = "";
	private String type = "0";
	@Transient
	private String status;
	@Transient
	@ColumnType(jdbcType = JdbcType.TIME)
	private Date created;
	@Transient
	@ColumnType(jdbcType = JdbcType.TIME)
	private Date modified;
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String menuLevel = "";
	@Transient
	private List<Menu> subMenu;

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public List<Menu> getSubMenu() {
		return this.subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuOrder() {
		return this.menuOrder;
	}

	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuLevel() {
		return this.menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	
}
