package com.bda.bdaqm.mission.controller;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.DepartmentModel;
import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.DepartmentService;
import com.bda.bdaqm.admin.service.MyRoleService;
import com.bda.bdaqm.admin.service.UserOprService;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.model.QuartzTestModel;
import com.bda.bdaqm.mission.quartz.SchedulerUtils;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.bda.bdaqm.rabbitmq.RabbitmqProducer;
import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.risk.service.ComplaintService;
import com.bda.bdaqm.risk.service.UsedTabooService;
import com.bda.bdaqm.util.FileUtils;
import com.bda.bdaqm.util.FtpUtil;
import com.bda.bdaqm.util.SFTPUtil3;
import com.bda.bdaqm.util.UZipFile;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping(value = "/mission/manager", method = RequestMethod.POST)
public class MissionManagerController {

    @Value("#{ftpconfig.uploadFilePath}")
    private String uploadFilePath;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionJobDetailService missionJobDetailService;

    @Autowired
    private MyRoleService myRoleService;

    @Autowired
    private UserOprService userService;

    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @Autowired
    private DepartmentService departmentService;

    @Value("#{mqconfig.mq_ready_queue}")
    private String readyQueueId;
    @Value("#{mqconfig.mq_update_queue}")
    private String updateQueueId;
    @Value("#{mqconfig.mq_check_queue}")
    private String checkQueueId;


    @RequestMapping("/uploadFile")
    @ResponseBody
    /*上传文件，接收临时文件夹id*/
    public Result uploadFile(@RequestParam("file") CommonsMultipartFile file){

        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        //配置路径
        String tempDirName = user.getUserId() + "-" + System.currentTimeMillis();
        String tempPath = uploadFilePath + tempDirName + "/"; //临时文件夹路径
        String zipPath = tempPath + "zip/"; //zip保存路径
        String unZipPath = tempPath + "unZip/"; //解压路径
        //生成临时文件夹
        File tempFoldor = new File(tempPath);
        File zipFoldor = new File(zipPath);
        if(tempFoldor.exists()){
            System.out.println("临时文件夹已存在");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }
        if (!tempFoldor.mkdirs() || !zipFoldor.mkdirs()) {
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        //保存zip文件
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        File tempFile = new File(zipPath + fileName);
        if (type.equals(".zip")) {
            try {
                file.transferTo(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
                FileUtils.delDir(tempPath);
                return Result.failure();
            }
        } else {
            System.out.println("上传文件不是zip格式");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        //解压文件
        try {
            UZipFile.unZipFiles(tempFile, unZipPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解压失败");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }

        //遍历mp3，检查格式和大小
        if (!checkMp3Files(new File(unZipPath))) {
            System.out.println("解压后的单个文件超过5M，或格式不是mp3");
            FileUtils.delDir(tempPath);
            return Result.failure();
        }
        //计数器
        int totalNum = totalNum(unZipPath);
        return Result.success(tempDirName,totalNum);
    }

    @RequestMapping("/createSingleMission")
    @ResponseBody
    public Result createMission(@RequestParam(value = "missId",required = false,defaultValue = "")String missId,
                                @RequestParam("missionName")String missionName,
                                @RequestParam("missionBegintime")String missionBegintime,
                                @RequestParam("missionLevel")int missionLevel,
                                @RequestParam("missionIstransfer")int missionIstransfer,
                                @RequestParam("missionIsinspection")int missionIsinspection,
                                @RequestParam("missionIstaboo")int missionIstaboo,
                                @RequestParam("missionIsrisk")int missionIsrisk,
                                @RequestParam("missionIsnodubious")int missionIsnodubious,
                                @RequestParam("missionDescribe")String missionDescribe,
                                @RequestParam("dirName")String dirName,
                                @RequestParam("missionTotalNum")int missionTotalNum
    ){
        if(missId.equals("")){
            //获取用户信息
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            String missionCreaterRole = departmentService.selectDepartmentById(user.getDepartmentId()).getDepartmentName();

            InspectionMission missionModel = new InspectionMission();
            missionModel.setMissionName(missionName);
            //任务开始时间不能早于现在
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            long m2 = 120000;
            try {
                Date begin = df.parse(missionBegintime);
                //开始时间比现在小于两分钟
                if(begin.getTime() - (new Date().getTime()) < m2){
                    //两分钟后开始
                    date = new Date(new Date().getTime() + m2);
                }else {
                    date = begin;
                }
            } catch (ParseException e) {
                date = new Date(new Date().getTime() + m2);
            }
            missionModel.setMissionBegintime(df.format(date));

            missionModel.setMissionLevel(missionLevel);
            missionModel.setMissionIstransfer(missionIstransfer);
            missionModel.setMissionIsinspection(missionIsinspection);
            missionModel.setMissionIstaboo(missionIstaboo);
            missionModel.setMissionIsrisk(missionIsrisk);
            missionModel.setMissionIsnodubious(missionIsnodubious);
            missionModel.setMissionDescribe(missionDescribe);
            //语音文件路径,任务创建时，文件在本机上
            missionModel.setMissionFilepath(uploadFilePath + dirName + "/" + "unZip" + "/");
            missionModel.setMissionCreaterid(Integer.valueOf(user.getUserId()));
            missionModel.setMissionCreaterRole(missionCreaterRole);
            //文件总数
            missionModel.setMissionTotalNum(missionTotalNum);

            //创建任务时，任务相关状态
            missionModel.setMissionStatus(0);
            missionModel.setMissionUploadStatus(0);
            missionModel.setMissionTransferStatus(0);
            missionModel.setMissionInspectionStatus(0);
            missionModel.setMissionType(1);

            if(isMissionNameRepeat(missionModel)){
                return Result.failure(ResultCode.CREATE_MISSION_REPEAT_NAME);
            }

            int missionId = missionService.createMission(missionModel);
            if(missionId > 0 ){
                try {
                    missionService.addSingleJob(missionModel);
                }catch (SchedulerException e){
                    return Result.failure();
                }
                return Result.success();
            }else {
                return Result.failure();
            }
        }else {
            int missionId = 0;
            try {
                missionId = Integer.valueOf(missId);
            }catch (Exception e){
                return Result.failure();
            }
            //old
            InspectionMission inspectionMission = missionService.getMissionByMissionId(missionId);
            if(inspectionMission == null){
                return Result.failure();
            }
            if(inspectionMission.getMissionStatus()!= 0){
                return Result.failure();
            }
            if(missionService.isCurrentlyExe(missId)){
                return Result.failure();
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            long m2 = 120000;
            try {
                Date begin = df.parse(missionBegintime);
                //开始时间比现在小于两分钟
                if(begin.getTime() - (new Date().getTime()) < m2){
                    //两分钟后开始
                    date = new Date(new Date().getTime() + m2);
                }else {
                    date = begin;
                }
            } catch (ParseException e) {
                date = new Date(new Date().getTime() + m2);
            }
            inspectionMission.setMissionBegintime(df.format(date));

            inspectionMission.setMissionLevel(missionLevel);
            inspectionMission.setMissionIstransfer(missionIstransfer);
            inspectionMission.setMissionIsinspection(missionIsinspection);
            inspectionMission.setMissionIstaboo(missionIstaboo);
            inspectionMission.setMissionIsrisk(missionIsrisk);
            inspectionMission.setMissionIsnodubious(missionIsnodubious);
            inspectionMission.setMissionDescribe(missionDescribe);
            //语音文件路径,任务创建时，文件在本机上
            if(!inspectionMission.getMissionFilepath().equals(dirName)) {
                inspectionMission.setMissionFilepath(uploadFilePath + dirName + "/"+ "unZip" + "/");
            }
            //文件总数
            inspectionMission.setMissionTotalNum(missionTotalNum);

            if(!inspectionMission.getMissionName().equals(missionName)){
                inspectionMission.setMissionName(missionName);
                if(isMissionNameRepeat(inspectionMission)){
                    return Result.failure(ResultCode.CREATE_MISSION_REPEAT_NAME);
                }
            }

            //移除定时任务
            try {
                missionService.removeSingleJob(String.valueOf(missionId));
            }catch (SchedulerException e){
                return Result.failure();
            }
            //更新数据库
            if(missionService.updateSingleMission(inspectionMission) > 0){
                try {
                    missionService.addSingleJob(inspectionMission);
                }catch (SchedulerException e){
                    return Result.failure();
                }
                return Result.success();
            }else {
                return Result.failure();
            }
        }
    }


    /*常规任务的创建*/
    @RequestMapping("/createCommonMission")
    public Result createCommonMission(
            @RequestParam(value = "missId",required = false,defaultValue = "")String missId,
            @RequestParam("missionName")String missionName,
            @RequestParam("missionBegintime")String missionBegintime,
            @RequestParam("missionIstransfer")int missionIstransfer,
            @RequestParam("missionIsinspection")int missionIsinspection,
            @RequestParam("missionIstaboo")int missionIstaboo,
            @RequestParam("missionIsrisk")int missionIsrisk,
            @RequestParam("missionIsnodubious")int missionIsnodubious,
            @RequestParam("missionDescribe")String missionDescribe,
            @RequestParam("ftpPath")String ftpPath,
            @RequestParam("scanTime")String scanTime,
            @RequestParam("scanDay")List<String> scanDays
            ){
        if(missId.equals("")){
            //获取用户信息
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            String missionCreaterRole = departmentService.selectDepartmentById(user.getDepartmentId()).getDepartmentName();

            List<InspectionMission> list = missionService.isCreateCommonMission(user.getUserId(),"0");
            if(list != null && list.size() > 0){
                return Result.failure(ResultCode.MISSION_EXIST);
            }

            InspectionMission inspectionMission = new InspectionMission();
            inspectionMission.setMissionName(missionName);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            long m2 = 120000;
            try {
                Date begin = df.parse(missionBegintime);
                //开始时间比现在小于两分钟
                if(begin.getTime() - (new Date().getTime()) < m2){
                    //两分钟后开始
                    date = new Date(new Date().getTime() + m2);
                }else {
                    date = begin;
                }
            } catch (ParseException e) {
                date = new Date(new Date().getTime() + m2);
            }
            inspectionMission.setMissionBegintime(df.format(date));
            inspectionMission.setMissionIstransfer(missionIstransfer);
            inspectionMission.setMissionIsinspection(missionIsinspection);
            inspectionMission.setMissionIstaboo(missionIstaboo);
            inspectionMission.setMissionIsrisk(missionIsrisk);
            inspectionMission.setMissionIsnodubious(missionIsnodubious);
            inspectionMission.setMissionDescribe(missionDescribe);
            //等级最高 ： 10
            inspectionMission.setMissionLevel(10);
            inspectionMission.setMissionCreaterid(Integer.valueOf(user.getUserId()));
            inspectionMission.setMissionCreaterRole(missionCreaterRole);
            //创建任务时，任务相关状态
            inspectionMission.setMissionStatus(0);
            inspectionMission.setMissionUploadStatus(0);
            inspectionMission.setMissionTransferStatus(0);
            inspectionMission.setMissionInspectionStatus(0);
            //标识常规任务
            inspectionMission.setMissionType(0);

            inspectionMission.setMissionFtp(ftpPath);
            String[] scantimes = scanTime.split(":");
            StringBuffer cycle = new StringBuffer();
            for(int i = scantimes.length-1;i>=0;i--){
                cycle.append(scantimes[i]);
                cycle.append(" ");
            }
            cycle.append("? * ");
            for (String scanDay : scanDays){
                cycle.append(scanDay + ",");
            }
            cycle.deleteCharAt(cycle.length() - 1);

            inspectionMission.setMissionCycle(cycle.toString());

            if(isMissionNameRepeat(inspectionMission)){
                return Result.failure(ResultCode.CREATE_MISSION_REPEAT_NAME);
            }

            int missionId = missionService.createMission(inspectionMission);
            if(missionId > 0){
                //添加定时任务
                try {
                    missionService.addCommonJob(inspectionMission);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                return Result.success();
            }
            return Result.failure();
        }else{
            int missionId = 0;
            try {
                missionId = Integer.valueOf(missId);
            }catch (Exception e){
                return Result.failure();
            }
            //old
            InspectionMission inspectionMission = missionService.getMissionByMissionId(missionId);
            if(inspectionMission == null){
                return Result.failure();
            }
            //写入对象中
            inspectionMission.setMissionIstransfer(missionIstransfer);
            inspectionMission.setMissionIsinspection(missionIsinspection);
            inspectionMission.setMissionIstaboo(missionIstaboo);
            inspectionMission.setMissionIsrisk(missionIsrisk);
            inspectionMission.setMissionIsnodubious(missionIsnodubious);
            inspectionMission.setMissionDescribe(missionDescribe);
            inspectionMission.setMissionFtp(ftpPath);
            String[] scantimes = scanTime.split(":");
            StringBuffer cycle = new StringBuffer();
            for(int i = scantimes.length-1;i>=0;i--){
                cycle.append(scantimes[i]);
                cycle.append(" ");
            }
            cycle.append("? * ");
            for (String scanDay : scanDays){
                cycle.append(scanDay + ",");
            }
            cycle.deleteCharAt(cycle.length() - 1);

            if (!inspectionMission.getMissionName().equals(missionName)){
                inspectionMission.setMissionName(missionName);
                if(isMissionNameRepeat(inspectionMission)){
                    return Result.failure(ResultCode.CREATE_MISSION_REPEAT_NAME);
                }
            }

            if(!inspectionMission.getMissionBegintime().equals(missionBegintime) ||
                !inspectionMission.getMissionCycle().equals(cycle.toString())){
                if(missionService.isCurrentlyExe(missId)){
                    return Result.failure();
                }
                try {
                    missionService.removeCommonJob(missId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                inspectionMission.setMissionBegintime(missionBegintime);
                inspectionMission.setMissionCycle(cycle.toString());
                if(missionService.updateCommonMission(inspectionMission) > 0){
                    try {
                        missionService.addCommonJob(inspectionMission);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                    return Result.success();
                }
                return Result.failure();
            }else {
                if(missionService.updateCommonMission(inspectionMission) > 0){
                    return Result.success();
                }else {
                    return Result.failure();
                }
            }
        }
    }

    //判断任务名称是否重名
    private boolean isMissionNameRepeat(InspectionMission mission){
        List<InspectionMission> list = missionService.getMissionByCreaterId(mission.getMissionCreaterid());
        if(list.size() == 0){
            return false;
        }
        for (InspectionMission name : list){
            if (name.getMissionName().equals(mission.getMissionName())){
                return true;
            }
        }
        return false;
    }

    //创建任务前调用
    @RequestMapping("/isCreateCommonMission")
    public Result isCreateCommonMission(){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        String userId = user.getUserId();
        String missionType = "0";
        List<InspectionMission> list = missionService.isCreateCommonMission(userId,missionType);
        if(list != null && list.size() > 0){
            return Result.failure();
        }
        return Result.success();
    }


    @RequestMapping("/getQuartz")
    public void getQuartz(){
        try {
            SchedulerUtils.getSingleJob();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }


    @RequestMapping("isAttach")
    public Result isAttach(@RequestParam(value = "ftpPath") String ftpPath){
            if(ftpPath == null || ftpPath.equals("")){
                return Result.failure(ResultCode.FTP_PATH_NULL);
            }
            String[] ftps = ftpPath.split("&|:|\\?");
            String path = "";
            String account = "";
            String pwd = "";
            int i = 0;
            for(String ftp : ftps){
                i++;
                switch (i){
                    case 2:
                        path = ftp;
                        break;
                    case 3:
                        account = ftp.substring(ftp.indexOf("=") + 1);
                        break;
                    case 4:
                        pwd = ftp.substring(ftp.indexOf("=") + 1);
                        break;
                }
            }
            if(ftps[0].equals("ftp")){
                FtpUtil ftpUtil = new FtpUtil(path,21,account,pwd);
                try {
                    ftpUtil.connect();
                    ftpUtil.disconnect();
                }catch (Exception e){
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
                return Result.success();
            }else {
                SFTPUtil3 sftpUtil3 = new SFTPUtil3(path,account,pwd);
                if(!sftpUtil3.connect()){
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
                if(sftpUtil3.getSftp().isConnected()){
                    return Result.success();
                }else {
                    return Result.failure(ResultCode.FTP_CONNECT_FAILURE);
                }
            }
    }

    @RequestMapping("/queryMissonList")
    public Result queryMissonList(Page page,
                                  @RequestParam(value = "companyName",required = false)String companyName,
                                  @RequestParam(value = "planName",required = false)String planName,
                                  @RequestParam(value = "recordStartDate",required = false)String recordStartDate,
                                  @RequestParam(value = "recordEndDate",required = false)String recordEndDate){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        List<String> tempList = haveSonUsers(user);
        List<String> allUserIds = new ArrayList<>();
        if (tempList == null){
            return Result.failure();
        }
        allUserIds.addAll(tempList);
        List<InspectionMission> listMission = missionService.getListMission(page,allUserIds,companyName,planName,recordStartDate,recordEndDate);
        PageInfo<InspectionMission> pageInfo = new PageInfo<>(listMission);
        return Result.success(new DataGrid(listMission, pageInfo.getTotal()));

    }

    @RequestMapping("/missionPause")
    public Result missionPause(@RequestParam("missionId")int missionId){
        InspectionMission mission = missionService.getMissionByMissionId(missionId);
        if (mission.getMissionStatus() == 2) {
            return Result.failure(ResultCode.MISSION_HAS_COMPLETED);
        }

        //更新任务状态-暂停，刷新消息队列
        missionService.updateMissionStatus(missionId, 3);
        updateReadyQueue();
        return Result.success();
    }

    @RequestMapping("/missionStart")
    public Result missionStart(@RequestParam("missionId")int missionId){
        InspectionMission mission = missionService.getMissionByMissionId(missionId);
        if (mission.getMissionStatus() == 2) {
            return Result.failure(ResultCode.MISSION_HAS_COMPLETED);
        }

        //更新任务状态-进行中，刷新消息队列
        missionService.updateMissionStatus(missionId, 1);
        updateReadyQueue();
        return Result.success();
    }



    @RequestMapping("/missionCancel")
    public Result missionCancel(@RequestParam("dirName")String dirName){
        deleteTemp(dirName);
        return Result.success();
    }

    /**
     * 根据任务id查找任务，用于任务详情页面
     * */
    @RequestMapping("/getMissionDetail")
    public Result getMissionDetail(@RequestParam("missionId")String missionId){
        InspectionMission mission = missionService.getMissionByMissionId(Integer.valueOf(missionId));
        if(mission == null){
            return Result.failure();
        }
        if(mission.getMissionStatus() == 2 && mission.getMissionCompletetime() != null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begin = df.parse(mission.getMissionBegintime());
                Date end = df.parse(mission.getMissionCompletetime());
                long time = end.getTime() - begin.getTime();
                long day=time/(24*60*60*1000);
                long hour=(time/(60*60*1000)-day*24);
                long min=((time/(60*1000))-day*24*60-hour*60);
                long s=(time/1000-day*24*60*60-hour*60*60-min*60);
                mission.setMissionCompletetime(day+"天"+hour+"小时"+min+"分"+s+"秒");
            }catch (Exception e){
                e.printStackTrace();
            }
            List<InspectionMissionJobDetail> listMissionJob = missionJobDetailService.getListByMissionId(mission.getMissionId());
            Map<String,String> map = new HashMap<>();
            int suc = 0;
            int fal = 0;
            for (InspectionMissionJobDetail job : listMissionJob){
                if(job.getFileStatus() == 0){
                    fal++;
                }
                if(job.getFileStatus() == 5){
                    suc++;
                }
            }
            map.put("InspectionSuccess",String.valueOf(suc));
            map.put("InspectionFailure",String.valueOf(fal));
            return Result.success(mission,map);
        }
        return Result.success(mission);
    }

    /**
     * 失败文件列表下载
     * */
    @RequestMapping("downloadFailureList")
    public void downloadFailureList(@RequestParam("missionId")String missionId){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpServletRequest request = requestAttributes.getRequest();
        List<InspectionMissionJobDetail> list = missionJobDetailService.getListByMissionId(Integer.valueOf(missionId));
        String fileName = "失败列表" + "_" + new Date().toString() + ".xls";
        String path = request.getSession().getServletContext().getRealPath("") + "/" + fileName;
        File excel = new File(path);
        try {
            // 创建写工作簿对象
            WritableWorkbook workbook = Workbook.createWorkbook(excel);
            // 工作表
            WritableSheet sheet = workbook.createSheet("失败列表", 0);
            // 设置字体;
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat cellFormat = new WritableCellFormat(font);
            // 设置背景颜色;
            cellFormat.setBackground(Colour.WHITE);
            // 设置边框;
            cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
            // 设置文字居中对齐方式;
            cellFormat.setAlignment(Alignment.CENTRE);
            // 设置垂直居中;
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            sheet.getSettings().setDefaultColumnWidth(20);
            cellFormat.setWrap(true);
            Label label0 = new Label(0, 0, "ID", cellFormat);
            Label label1 = new Label(1, 0, "文件名", cellFormat);
            Label label2 = new Label(2, 0, "文件路径", cellFormat);
            Label label3 = new Label(3, 0, "失败原因", cellFormat);
            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            // 给第二行设置背景、字体颜色、对齐方式等等;
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            // 设置文字居中对齐方式;
            cellFormat2.setAlignment(Alignment.CENTRE);
            // 设置垂直居中;
            cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat2.setBackground(Colour.WHITE);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(true);

            // 记录行数
            int n = 1;

            for (InspectionMissionJobDetail jobDetail : list){
                if(jobDetail.getFileStatus() == 0){
                    Label lt0 = new Label(0, n, jobDetail.getJobId() + "", cellFormat2);
                    Label lt1 = new Label(1, n, jobDetail.getFileName(), cellFormat2);
                    Label lt2 = new Label(2, n, jobDetail.getFilePath(), cellFormat2);
                    Label lt3 = new Label(3, n, jobDetail.getFileStatusDescribe(), cellFormat2);
                    sheet.addCell(lt0);
                    sheet.addCell(lt1);
                    sheet.addCell(lt2);
                    sheet.addCell(lt3);
                    n++;
                }
            }
            //开始执行写入操作
            workbook.write();
            //关闭流
            workbook.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        // 第六步，下载excel
        OutputStream out = null;
        try {
            response.addHeader("content-disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(fileName, "utf-8"));
            // 2.下载
            out = response.getOutputStream();
            String path3 = request.getSession().getServletContext().getRealPath("") + "/" + fileName;
            // inputStream：读文件，前提是这个文件必须存在，要不就会报错
            InputStream is = new FileInputStream(path3);

            byte[] b = new byte[4096];
            int size = is.read(b);
            while (size > 0) {
                out.write(b, 0, size);
                size = is.read(b);
            }
            out.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/mqCheck")
    public String  mqPurge(String str){
        return "null";
    }

    @RequestMapping("/mqUpdate")
    public void mqUpdate(){
        try {
            updateReadyQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务
     * */
    @RequestMapping("/missionDelete")
    public Result missionDelete(String missionId){
        int Id = Integer.valueOf(missionId);
        InspectionMission inspectionMission = missionService.getMissionByMissionId(Id);
        if(inspectionMission == null){
            return Result.failure();
        }
        if(inspectionMission.getMissionStatus() ==  1){
            return Result.failure();
        }

        if(inspectionMission.getMissionType() == 0){
            //常规
            if(missionService.isCurrentlyExe(String.valueOf(inspectionMission.getMissionId()))){
                System.out.println("删除任务正在运行");
                return Result.failure(ResultCode.COMMONJOB_RUNNING);
            }
            try {
                System.out.println("删除任务正在移除");
                missionService.removeCommonJob(String.valueOf(inspectionMission.getMissionId()));
            }catch (Exception e){
                System.out.println("常规移除失败");
                return Result.failure(ResultCode.COMMONJOB_REMOVE_FAILURE);
            }
            missionService.deleteMissionById(inspectionMission.getMissionId());
            if(inspectionMission.getMissionStatus() != 0){
                missionJobDetailService.deleteByMissionId(inspectionMission.getMissionId());
            }
        }else{
            //单次
            if(inspectionMission.getMissionStatus() == 0){
                if(missionService.isCurrentlyExe(String.valueOf(inspectionMission.getMissionId()))){
                    System.out.println("单次正在执行");
                    return Result.failure();
                }
                try{
                    System.out.println("单次正在移除");
                    missionService.removeSingleJob(String.valueOf(inspectionMission.getMissionId()));
                }catch (Exception e){
                    System.out.println("单次移除失败");
                    return Result.failure();
                }
                missionService.deleteMissionById(inspectionMission.getMissionId());
            }
            else {
                missionService.deleteMissionById(inspectionMission.getMissionId());
                missionJobDetailService.deleteByMissionId(inspectionMission.getMissionId());
            }
        }
        return Result.success();
    }


    //更新ready队列
    private void updateReadyQueue() {
        rabbitmqProducer.sendQueue(updateQueueId+"_exchange", updateQueueId+"_patt", null);
    }

    private void deleteTemp(String dirName){
        String path = uploadFilePath + dirName + "/";
        File foldor = new File(path);
        if(foldor.exists()){
            if(foldor.listFiles().length > 0){
                for (File file : foldor.listFiles()){
                    deleteFile(file);
                }
            }
            foldor.delete();
        }
    }

    private void deleteFile(File file){
        if (file.isFile()){
            file.delete();
        }else {
            File[] chilFiles = file.listFiles();
            for (File file1 : chilFiles){
                deleteFile(file1);
            }
            file.delete();
        }
    }

    //先清空队列，然后查表根据任务状态添加任务至队列
    private void updateMQ() {

    }

    private boolean checkMp3Files(File foldor){
        File[] fs = foldor.listFiles();
        if (fs == null) {
            return false;
        }
        for(File f:fs){
            if(f.isDirectory() && !checkMp3Files(f))	//若是目录，则递归该目录下的文件
                return false;
            if(f.isFile() && !isMp3File(f))		//若是文件，直接判断
                return false;
        }
        return true;
    }

    private boolean isMp3File(File file) {
        Long size = file.length();
        Long maxSize = 5L * 1024 * 1024; //5MB
        String fileName = file.getName();
        System.out.println(fileName);
        String type = fileName.substring(fileName.lastIndexOf("."));
        if (type.equals(".mp3") && size <= maxSize) {
            return true;
        }
        return false;
    }

    private int totalNum(String path){
        int num = 0;
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                num = num + totalNum(file.getPath());
            }else {
                num++;
                System.out.println("???" + file.getPath());
            }
        }
        return num;
    }

    @RequestMapping("getTriggerState")
    public Result getTriggerState(String missionId){
        return Result.success(SchedulerUtils.getTriggerState(missionId));
    }

    //获取任务列表用；查询当前用户能浏览的权限
    private List<String> haveSonUsers(User user){
        List<String> results = new ArrayList<>();
        Role role = myRoleService.selectRoleByUserId(user.getUserId());
        List<DepartmentModel> departments = new ArrayList<>();
        if(role != null){
            if(role.getAbility() == 3){
                departments.addAll(departmentService.getDepartmentAndChildren(user.getDepartmentId()));
            }
            if(role.getAbility() == 2){
                departments.add(departmentService.selectDepartmentById(user.getDepartmentId()));
            }else {

            }
            if(departments.size() == 0){
                results.add(user.getUserId());
            }
            for (DepartmentModel d : departments){
                results.addAll(userService.selectUserIdsByDepartmentId(d.getDepartmentId()));
            }
            return results;
        }else {
            return null;
        }
    }

    @RequestMapping("/testGetJobs")
    public List<QuartzTestModel> testGetJobs(){
        return SchedulerUtils.getListJobsInScheduler();
    }


}