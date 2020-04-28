package com.bda.bdaqm.electric.model;

public class ComboxTree {
	
	private String childrenId;
	private String fatherId;
	private String name;
	private String level;
	public String getChildrenId() {
		return childrenId;
	}
	public void setChildrenId(String childrenId) {
		this.childrenId = childrenId;
	}
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
