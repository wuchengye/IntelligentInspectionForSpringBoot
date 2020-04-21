package com.bda.bdaqm.electric.model;

public class unitVo {

	private String month;
	private String departmentOname;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDepartmentOname() {
		return departmentOname;
	}
	public void setDepartmentOname(String departmentOname) {
		this.departmentOname = departmentOname;
	}
	
	
	public unitVo(String month, String departmentOname) {
		super();
		this.month = month;
		this.departmentOname = departmentOname;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departmentOname == null) ? 0 : departmentOname.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		unitVo other = (unitVo) obj;
		if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
		if(obj instanceof unitVo) {
			if(this.month.equals(other.month)&&this.departmentOname.equals(other.departmentOname)) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	
}
