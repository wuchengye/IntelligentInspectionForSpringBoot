package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.Table;
@Table(name="bda_ticket_check_result_children")
public class CriterionChildrenResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7664112101068984318L;
	
	private String ticketId;
	private String criterion;
	private String criterionChildren;
	private String description;
	private String field;
	private String batchTime;
	
	
	
	public String getBatchTime() {
		return batchTime;
	}
	public void setBatchTime(String batchTime) {
		this.batchTime = batchTime;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getCriterion() {
		return criterion;
	}
	public void setCriterion(String criterion) {
		this.criterion = criterion;
	}
	public String getCriterionChildren() {
		return criterionChildren;
	}
	public void setCriterionChildren(String criterionChildren) {
		this.criterionChildren = criterionChildren;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	
}
