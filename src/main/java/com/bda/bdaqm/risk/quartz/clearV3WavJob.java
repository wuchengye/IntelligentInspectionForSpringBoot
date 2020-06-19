package com.bda.bdaqm.risk.quartz;

import com.bda.bdaqm.risk.controller.AllController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class clearV3WavJob {
	@Autowired
	AllController allController;
	
	@Scheduled(cron = "0 0 3 * * ?")
	public Object execCleanRecordDir(){
		return allController.cleanRecordDir();
	}
}
