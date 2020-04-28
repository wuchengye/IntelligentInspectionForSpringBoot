package com.bda.bdaqm.electric.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bda.bdaqm.electric.controller.TicketCheckController;
import com.bda.bdaqm.electric.controller.TicketNumChartsConroller;

@Component
public class CheckQualifiedStandard {
	
	@Autowired
	TicketCheckController ticketCheckController;
	@Autowired
	TicketNumChartsConroller ticketNumChartsConroller;
	/*@Autowired
	TicketBaseTempController ticketBaseTempController;*/
	
	/**
	 * base及动火票插入中间表
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 0 5 * * ?")
	public void insertTicketBaseTemp() throws InterruptedException{
		ticketBaseTempController.insertTicketBase(null, null);
	}*/
	/**
	 * 数据校验
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 30 5 * * ?")
	public void checkQualifiedStandard() throws InterruptedException{
		//数据校验
		ticketCheckController.quartzCheckTicket(null,null);
		//数据插入图表使用中间表
		ticketNumChartsConroller.insertDataToTemp(null,null);
	}*/
	/**
	 * 数据插入中间表
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 30 6 * * ?")
	public void insertBaseDataToTemp() throws InterruptedException{
		
	}*/
	/**
	 * 工作位置字段分析入库
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 0 7 * * ?")
	public void analysisFunctionName() throws InterruptedException{
		ticketNumChartsConroller.analysisFunctionName(null,null);
	}*/
	
	
}
