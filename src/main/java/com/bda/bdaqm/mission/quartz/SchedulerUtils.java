package com.bda.bdaqm.mission.quartz;

import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.model.QuartzTestModel;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

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
        if(! scheduler.isStarted()){
            scheduler.start();
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(missionId,"group1");
        JobKey jobKey = JobKey.jobKey(missionId,"group1");
        System.out.println("单次暂停前");
        scheduler.pauseTrigger(triggerKey);
        System.out.println("单次暂停后，unshedulejob前");
        scheduler.unscheduleJob(triggerKey);
        System.out.println("unshedulejob后，delete前");
        scheduler.deleteJob(jobKey);
        System.out.println("delete后");
    }

    public static void removeCommonJob(String missionId) throws SchedulerException {
        if(! scheduler.isStarted()){
            scheduler.start();
        }
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

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(mission.getMissionCycle() + " *");
/*
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("15 * * ? * 2,3,4,5,6,7,1 *");
*/
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
                                        .startAt(date)
                                        /*.startNow()*/
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

    //获取scheduler中的任务
    public static List<QuartzTestModel> getListJobsInScheduler(){
        List<QuartzTestModel> list = new ArrayList<>();
        try {
            //获取所有的group
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for(String name : triggerGroupNames){
                //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
                GroupMatcher groupMatcher = GroupMatcher.groupEquals(name);
                //获取所有的triggerKey
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
                for (TriggerKey triggerKey : triggerKeySet) {
                    //通过triggerKey在scheduler中获取trigger对象
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    //获取trigger拥有的Job
                    JobKey jobKey = trigger.getJobKey();
                    JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);

                    QuartzTestModel quartzTestModel = new QuartzTestModel();
                    quartzTestModel.setGroupName(name);
                    quartzTestModel.setJobDetailName(jobDetail.getName());
                    quartzTestModel.setJobCronExpression(trigger.getCronExpression());
                    quartzTestModel.setTimeZone(trigger.getTimeZone().getID());
                    list.add(quartzTestModel);
                }
            }
        }catch (Exception e){
            System.out.println("获取定时器中的任务失败：" + e.getMessage());
        }
        return list;
    }

}
