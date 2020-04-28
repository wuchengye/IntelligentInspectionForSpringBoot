package com.bda.bdaqm.electric.model;

/**
 * 
 * @author 徐玮南
 * 定义单位名的顺序
 */
public class UnitOrder {
  private int orderId;
  private String unitname;
  
  public int getOrderId() {
	  return orderId;
  }
  public void setOrderId(int orderId) {
	  this.orderId=orderId;
  }
  public String getUnitname() {
	  return unitname;
  }
  public void setUnitname(String unitname) {
	  this.unitname=unitname;
  }
}
