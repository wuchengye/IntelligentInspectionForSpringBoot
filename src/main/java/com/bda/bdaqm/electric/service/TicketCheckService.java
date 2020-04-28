package com.bda.bdaqm.electric.service;

import java.util.List;

import com.bda.bdaqm.electric.model.QueryParams;

public interface TicketCheckService {
	
	public Object ticketCheck(QueryParams check,List<String> list);

}
