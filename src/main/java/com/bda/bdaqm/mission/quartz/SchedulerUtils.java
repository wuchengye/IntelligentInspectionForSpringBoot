package com.bda.bdaqm.mission.quartz;

import com.bda.bdaqm.mission.model.InspectionMission;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SchedulerUtils {
    private static StdScheduler scheduler = (StdScheduler) new ClassPathXmlApplicationContext("spring/applicationContext-quartz.xml")
            .getBean("startQuertz");

    public static void startScheduler(InspectionMission mission) throws SchedulerException{

        //Job实例
        JobDetail jobDetail = JobBuilder.newJob(MissionJob.class)
                                        .withIdentity(mission.getMissionId().toString(),"group1")
                                        .usingJobData("missionId",mission.getMissionId().toString())
                                        .usingJobData("missionName",mission.getMissionName())
                                        .usingJobData("missionFilepath",mission.getMissionFilepath())
                                        .usingJobData("missionTotalNum",mission.getMissionTotalNum().toString())
                                        .build();

        //时间格式转换
        String startTime = mission.getMissionBegintime();
        String cron = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(startTime);
            SimpleDateFormat cronFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
            cron = cronFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        Trigger trigger = TriggerBuilder.newTrigger()
                                    .withIdentity(mission.getMissionId().toString(),"group1")
                                    .startNow()
                                    .withSchedule(cronScheduleBuilder)
                                    .build();


        if(! scheduler.isStarted()){
            scheduler.start();
        }
        scheduler.scheduleJob(jobDetail,trigger);

    }

}
