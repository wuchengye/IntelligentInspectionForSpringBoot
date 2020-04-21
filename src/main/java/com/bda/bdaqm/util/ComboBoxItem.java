package com.bda.bdaqm.util;

public class ComboBoxItem {
	private String value;
	private String text;
	private boolean selected;
	
	public ComboBoxItem(){
		this.selected = false;
	}
	public ComboBoxItem(String text,String value){
		this.value = value;
		this.text = text;
		this.selected = false;
	}
	public ComboBoxItem(String text,String value,boolean selected){
		this.value = value;
		this.text = text;
		this.selected = selected;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
