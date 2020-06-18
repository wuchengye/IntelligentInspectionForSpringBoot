package com.bda.bdaqm.risk.controller;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.UserOprService;
import com.bda.bdaqm.risk.model.RequestModel;
import com.bda.bdaqm.risk.model.SessionDetail;
import com.bda.bdaqm.risk.service.AllService;
import com.bda.bdaqm.util.PropertyMgr;
import com.bda.common.bean.OperaterResult;
import com.bda.common.controller.BaseController;
import com.bda.easyui.bean.DataGrid;
import com.bda.easyui.bean.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"risk/all"})
public class AllController extends BaseController {
    @Autowired
    private AllService allService;
    @Autowired
    private UserOprService userService;


    @RequestMapping("/getJudgeDetail")
    public Result getJugeDetail(Page page, RequestModel requestModel){
        if(requestModel != null && requestModel.getType() != null) {
            List<String> allUserIds = new ArrayList<>();
            if(requestModel.getMissionId() == null) {
                //获取用户信息
                Subject subject = SecurityUtils.getSubject();
                User user = (User) subject.getPrincipal();
                List<Map<String, Object>> resList = new ArrayList<>();
                resList.addAll(haveSonUsers(user.getAccount()));
                for (Map<String, Object> map : resList) {
                    allUserIds.add(map.get("userId").toString());
                }
                allUserIds.add(user.getUserId());
            }
            List list = allService.getJugeDetail(page,requestModel,allUserIds);
            PageInfo pageInfo = new PageInfo(list);
            return Result.success(new DataGrid(pageInfo.getList(), pageInfo.getTotal()));
        }
        else {
            return Result.failure();
        }
    }

    @RequestMapping("/submitCheck")
    public Result submitCheck(RequestModel params){
        if (params.getType() != null) {
            try {
                //更新结果表数据
                allService.updateComplaintCheckResult(params);
                //将核查结果插入历史表
                allService.insertCheckHistory(params);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure(ResultCode.SUBMIT_FAILURE);
            }
            return Result.success();
        }else {
            return Result.failure(ResultCode.SUBMIT_FAILURE);
        }
    }

    @RequestMapping("/getSessionDetail")
    public Result getSessionDetail(String sessionId,int isQuestion){
        List<SessionDetail> list = new ArrayList<SessionDetail>();
        list = allService.selectSessionDetail(sessionId,isQuestion);
        return Result.success(new DataGrid(list,list.size()));
    }

    @RequestMapping("/exportJudgeDetail")
    public Result exportJudgeDetail(RequestModel requestModel){
        String downloadFileName = "";
        String downloadPath = "";
        if(requestModel != null && requestModel.getType() != null) {
            List<String> allUserIds = new ArrayList<>();
            if (requestModel.getMissionId() == null) {
                //获取用户信息
                Subject subject = SecurityUtils.getSubject();
                User user = (User) subject.getPrincipal();
                List<Map<String, Object>> resList = new ArrayList<>();
                resList.addAll(haveSonUsers(user.getAccount()));
                for (Map<String, Object> map : resList) {
                    allUserIds.add(map.get("userId").toString());
                }
                allUserIds.add(user.getUserId());
            }
            List list = allService.getJugeDetail(requestModel,allUserIds);
            try {
                if (requestModel.getType() == 0) {
                    String templatePath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/hotlineExpTemplate") + "/非可疑数据导出模板.xlsx";
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    downloadFileName = df.format(new Date()) + "-exportDubious.xlsx";
                    downloadPath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/") + downloadFileName;
                    allService.makeNotDubiousExcel(list, templatePath, downloadPath);
                }
                if (requestModel.getType() == 1) {
                    String templatePath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/hotlineExpTemplate") + "/可疑投诉风险监控会话数据导出模板.xlsx";
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    downloadFileName = df.format(new Date()) + "-exportComplaint.xlsx";
                    downloadPath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/") + downloadFileName;
                    allService.makeComplaintExportExcel(list, templatePath, downloadPath);
                }
                if (requestModel.getType() == 2) {
                    String templatePath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/hotlineExpTemplate") + "/可疑使用禁语会话数据导出模板.xlsx";
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    downloadFileName = df.format(new Date()) + "-exportTaboo.xlsx";
                    downloadPath = request.getSession().getServletContext().getRealPath("/upload/qualityJudge/") + downloadFileName;
                    allService.makeTabooExportExcel(list, templatePath, downloadPath);
                }
            }catch (Exception e){
                logger.error("程序异常：" + e);
                e.printStackTrace();
                return Result.failure();
            }
            return Result.success();
        }else {
            return Result.failure();
        }
    }

    @RequestMapping("/playV3")
    public Result playV3(@RequestParam(required=true)String filePath, String fileName) throws Exception{

        String ftpDebugModel = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.FTP_DEBUG_MODEL_KEY);
        if(ftpDebugModel.equals("1")){
            //return new OperaterResult<>(true, "", "/bdaqm/assets/audio/test.mp3");
            return Result.success(new OperaterResult<>(true, "", "/bdaqm/assets/audio/test.mp3"));
        }else{
            //本地V3文件相对路径
            /*String localRelateV3path = "/upload/dataManage/v3/";*/
            //本地WAV文件相对路径
            //File endDirection=new File("E:/path2");

            //本地V3文件相对路径
            //本地WAV文件相对路径
            String localRelateWAVpath = "/upload/dataManage/wav/";
            //根据相对路径获取绝对路径
            //根据相对路径获取绝对路径
            String localWAVpath = request.getSession().getServletContext().getRealPath(localRelateWAVpath);

            String ftpPath = PropertyMgr.getPropertyByKey(PropertyMgr.FTP_CONFIG_PROP, PropertyMgr.SFTP_PATH);
            if(!ftpPath.endsWith("/"))ftpPath += "/";
            if((!localWAVpath.endsWith("/")) && (!localWAVpath.endsWith("\\")))localWAVpath += "/";
            //打印本地地址
            //System.out.println("wcy=======" + localWAVpath);

            String v3Path = allService.getV3File(localWAVpath,ftpPath,filePath);
            if(StringUtils.isBlank(v3Path) || v3Path == null){
                //throw new RuntimeException("下载失败");
                return Result.failure(ResultCode.DOWNLOAD_FAILURE);
            }
            //return new OperaterResult<>(true, "", "/bdasjs/upload/dataManage/wav/"+fileName);
            return Result.success(new OperaterResult<>(true, "", localRelateWAVpath + fileName));
        }

    }

    //获取任务列表用；查询当前用户所创建的用户
    private List<Map<String,Object>> haveSonUsers(String create){
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> users = userService.selectUsersAndRoleByCreate(create,"");
        if(users != null && users.size() > 0){
            for(Map map : users){
                //向下递归，查找当前用户所创建的用户及其子用户
                if(map.get("account") != null && !map.get("account").toString().equals("")){
                    result.addAll(haveSonUsers(map.get("account").toString()));
                }
            }
            result.addAll(users);
        }
        return result;
    }
}
