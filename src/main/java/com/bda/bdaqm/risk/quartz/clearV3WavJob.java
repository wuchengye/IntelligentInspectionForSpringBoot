package com.bda.bdaqm.risk.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bda.bdaqm.risk.controller.UsedTabooController;

@Component
public class clearV3WavJob {
	@Autowired
	UsedTabooController usedTabooController;
	
	@Scheduled(cron = "0 0 3 * * ?")
	public Object execCleanRecordDir(){
		return usedTabooController.cleanRecordDir();
	}
}
