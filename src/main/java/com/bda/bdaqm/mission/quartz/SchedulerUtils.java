package com.bda.bdaqm.mission.quartz;

import com.bda.bdaqm.mission.model.InspectionMission;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SchedulerUtils {
    private static StdScheduler scheduler = (StdScheduler) new ClassPathXmlApplicationContext("spring/applicationContext-quartz.xml")
            .getBean("startQuertz");

    public static void addSingleJob(InspectionMission mission) throws SchedulerException{

        //Job实例
        JobDetail jobDetail = JobBuilder.newJob(MissionJob.class)
                                        .withIdentity(mission.getMissionId().toString(),"group1")
                                        .usingJobData("missionId",mission.getMissionId().toString())
                                        .usingJobData("missionFilepath",mission.getMissionFilepath())
                                        .usingJobData("missionIstransfer",mission.getMissionIstransfer().toString())
                                        .usingJobData("missionIsinspection",mission.getMissionIsinspection().toString())
                                        .usingJobData("missionLevel",mission.getMissionLevel().toString())
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


    public static void removeSingleJob(String missionId) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(missionId,"group1");
        JobKey jobKey = JobKey.jobKey(missionId,"group1");
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
    }

    public static void removeCommonJob(String missionId) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(missionId,"group2");
        JobKey jobKey = JobKey.jobKey(missionId,"group2");
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
    }

    public static void getSingleJob()throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(MissionJob.class)
                .withIdentity("1","group1")
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("00 01 12 01 06 ? 2020");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("1","group1")
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,trigger);
        JobKey jobKey = JobKey.jobKey("1","group1");
        System.out.println("ooooooo"+scheduler.getJobDetail(jobKey));;
    }

    public static void addCommonJob(InspectionMission mission) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(MissionCommonJob.class)
                                        .withIdentity(mission.getMissionId().toString(),"group2")
                                        .usingJobData("missionId",mission.getMissionId().toString())
                                        .build();

/*
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(mission.getMissionCycle());
*/
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/15 * * * * ? *");
        String startTime = mission.getMissionBegintime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity(mission.getMissionId().toString(),"group2")
                                        /*.startAt(date)*/
                                        .startNow()
                                        .withSchedule(cronScheduleBuilder)
                                        .build();

        if(! scheduler.isStarted()){
            scheduler.start();
        }
        scheduler.scheduleJob(jobDetail,trigger);
    }

    public static Trigger.TriggerState getTriggerState(String missionId){
        TriggerKey triggerKey = TriggerKey.triggerKey(missionId,"group1");
        try {
            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            return state;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    //是否正在运行
    public static boolean isCurrentlyExe(String missionId){
        List<JobExecutionContext> list = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext job : list){
            if(missionId.equals(job.getTrigger().getJobKey().getName())){
                return true;
            }
        }
        return false;
    }


}
