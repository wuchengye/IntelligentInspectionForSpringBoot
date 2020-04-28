package com.bda.bdaqm.electric.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Table;

@Table(name="sp_pd_home_page")
public class TicketCheck implements Serializable{

	private static final long serialVersionUID = 1160583379903802076L;
	
	private String ticketId;				//工作票ID
	private String ticketType;				//工作票类型
	private String stationLineName;			//变电站站名/线路
	private String workPrincipal;			//工作负责人
	private String department;				//单位和班组
	private String numOfWork;				//工作班人数
	private String workMemberUname;         //工作班人员
	private String planStartTime;			//计划开始时间
	private String planEndTime;				//计划结束时间
	private String workTask;				//工作任务
	private String workPlace;				//工作地点
	private String powerCutLine;			//停电线路名称
	private String pullBreaker;				//断路器（开关）
	private String pullSwitch;				//隔离开关
	private String pullBreakerSwitch;		//应拉断路器（开关）和隔离开关（刀闸）
	private String dcPowerLowpCircle;		//应投切的相关直流电源（空气开关、熔断器、连接片）、低压及二次回路
	private String switchEarthwireInsulation;//应合上的接地刀闸 （双重名称或编号）、装设的接地线（装设地点）、应设绝 缘挡板
	private String billboard;				//应设遮栏、应挂标示牌（位置）
	private String isHandleSecondMeasures;	//是否需办理二次设备及回路工作安全技术措施单
	private String numSecondMeasure;		//二次设备及回路工作安全技术措施单数量
	
	private String otherSafeCares;			//其他安全措施和注意事项
	private String isOuterDept;				//是否外来单位 
	private String ticketSigner;			//工作票签发人
	private String ticketSignTime;			//工作票签发时间
	private String ticketCounterSigner; 	//工作票会签人
	private String ticketCounterSignTime;  	//工作票会签时间
	private String watchName;               //变电、线路、配电工作票业务表单-值班负责人
	private String workLicensor;			//工作许可人
	private String workPermissionTime;		//工作许可时间
	private String workBreakTime;			//工作间隔时间
	private String workBreakLicensor;		//工作间断_工作许可人
	private String workBreakPrincipal;		//工作间断_工作负责人
	private String workStartTime;			//工作开工时间
	private String workStartLicensor;		//工作开工许可人
	private String workDelayTime;			//工作延期时间
	private String workDelayApplyTime;		//工作延期申请时间
	private String workDelayLicensor;		//工作延期_工作许可人
	private String workDelayPrincipal;		//工作延期_工作负责人
	private String workEndContent;			//工作终结内容
	private String ticketEndLicensor;		//工作票终结许可人
	private String workEndTime;             //工作终结时间
	private String ticketEndTime;			//工作票终结时间
	private String licensorMeasureEnd;		//许可人措施终结内容
	private String measureEndPerUname;      //许可人措施终结（工作许可人）
	private String measureEndFillTime;      //许可人措施终结填写时间
	private String unopenedGroundKnife;		//汇报调度_未拉开接地刀闸
	private String numUnopenedGroundKnife;	//汇报调度_未拉开接地刀闸数量
	private String unremovedGroundWire;		//汇报调度_未拆除接地线
	private String numUnremovedGroundWire;	//汇报调度_未拆除接地线数量
	private String dispatchPriUname;        //汇报调度_值班负责人
	private String dispatchUname;           //汇报调度_值班负责人
	private String dispatchFillTime;        //汇报调度_填写时间
	private String remark;					//备注
	
	private String ticketNo;                //工作票票号
	private String whetherLineGrounding;    //是否要求线路对侧接地(枚举：1-是； 2-否)
	private String guardianName;            //专责监护人
	private String whetherGuardianSign;     //专责监护人是否已签字(枚举：1-是； 2-否)
	private String safeAccountUname;        //安全交代工作成员名称
	private String safeAccountTime;         //安全交代时间
	private String ticketReceiveUname;      //工作票接收人
	private String addContentDetail;        //增加工作内容（不需变更安全措施）
	private String isTouch;                 //是否以手触试 1.是；2.否
	private String touchPosition;           //以手触试的位置
	private String lineGroundingUname;      //线路对侧安全措施调度员
	private String eleGeneratrixWire;       //带电的母线、导线
	private String eleSwitch;               //带电的隔离开关（刀闸）
	private String elePart;                 //其他保留的带电部位
	private String otherCare;               //其他安全注意事项
	private String eleLineDevicePer;        //保留的带电线路或带电设备
	
	private String permissionOtherCare;		//工作许可栏-其他安全注意事项
	private String permisstionPrincipal;	//工作许可栏-工作负责人
	private String changePrincipal;			//原工作负责人
	private String changeNewPrincipal;		//现工作负责人
	private String highpDeviceState;		//相关高压设备状态
	private String powerCircleState;		//相关直流低压及二次回路状态
	private String workStartPrincipal;		//工作开工时间-工作负责人
	private String workEndPrincipal;
	
	private String requiredSafty; //工作要求的安全措施：变电第三种工作票业务表单-工作要求的安全措施-字符型，工作条件及运行方式要求： 配电带电作业工作票
	private String saftyAndCare; //应采取的安全措施及安全注意事项：线2、配2、带电作业工作票业务表单-应采取的安全措施及安全注意事项-字符型
	private BigDecimal isLineSwitchRecove; //相关线路重合闸装置、再启动功能可以恢复 1.可以；2.不可以（新安规添加）
	private String repairControlUname;//抢修任务布置人
	private String liveSafeMeasure;//现场设备状况及保留的安全措施 紧急抢修单（新安规）
	private String saftyStartUname;//安全监察部门负责人（首次开工）（新安规）
	private String receiveTime;//收到工作票时间(新安规添加)
	private String workGuardianUname;//动火监护人ID
	private String ticketExecuteUname;//动火执行人（作业开工）(新安规添加)
	private String workPrincipalTel;//负责人电话：工作票业务表单-工作负责人手机号码-字符型
	private String protectionStartUname;//消防部门负责人（会签点）（新安规）
	private String bureauStartUname;//厂（局）负责人（首次开工）（新安规）
	private String watchUname;//值班负责人ID：变电、线路、配电工作票业务表单-值班负责人-字符型
	private String eleLineDevice;//保留的带电线路或带电设备：线1、配1工作票业务表单-保留的带电线路或带电设备-字符型
	private String workEndEarthwireCount;//工作终结（工作负责人汇报-地线拆除组数）：变1、配1工作业务表单-工作票终结（接地线拆除组数）-数字型
	private String workEndReportWay;//工作终结（终结的报告方式-（枚举：1-当面； 2-电话））：线1、配1工作票业务表单-工作终结（终结的报告方式）-枚举型 当面-1，电话-2
	
	public String getWorkEndPrincipal() {
		return workEndPrincipal;
	}
	public void setWorkEndPrincipal(String workEndPrincipal) {
		this.workEndPrincipal = workEndPrincipal;
	}
	public String getWorkStartPrincipal() {
		return workStartPrincipal;
	}
	public void setWorkStartPrincipal(String workStartPrincipal) {
		this.workStartPrincipal = workStartPrincipal;
	}
	public String getChangePrincipal() {
		return changePrincipal;
	}
	public void setChangePrincipal(String changePrincipal) {
		this.changePrincipal = changePrincipal;
	}
	public String getChangeNewPrincipal() {
		return changeNewPrincipal;
	}
	public void setChangeNewPrincipal(String changeNewPrincipal) {
		this.changeNewPrincipal = changeNewPrincipal;
	}
	public String getHighpDeviceState() {
		return highpDeviceState;
	}
	public void setHighpDeviceState(String highpDeviceState) {
		this.highpDeviceState = highpDeviceState;
	}
	public String getPowerCircleState() {
		return powerCircleState;
	}
	public void setPowerCircleState(String powerCircleState) {
		this.powerCircleState = powerCircleState;
	}
	public String getBillboard() {
		return billboard;
	}
	public void setBillboard(String billboard) {
		this.billboard = billboard;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLicensorMeasureEnd() {
		return licensorMeasureEnd;
	}
	public void setLicensorMeasureEnd(String licensorMeasureEnd) {
		this.licensorMeasureEnd = licensorMeasureEnd;
	}
	public String getUnopenedGroundKnife() {
		return unopenedGroundKnife;
	}
	public void setUnopenedGroundKnife(String unopenedGroundKnife) {
		this.unopenedGroundKnife = unopenedGroundKnife;
	}
	public String getNumUnopenedGroundKnife() {
		return numUnopenedGroundKnife;
	}
	public void setNumUnopenedGroundKnife(String numUnopenedGroundKnife) {
		this.numUnopenedGroundKnife = numUnopenedGroundKnife;
	}
	public String getUnremovedGroundWire() {
		return unremovedGroundWire;
	}
	public void setUnremovedGroundWire(String unremovedGroundWire) {
		this.unremovedGroundWire = unremovedGroundWire;
	}
	public String getNumUnremovedGroundWire() {
		return numUnremovedGroundWire;
	}
	public void setNumUnremovedGroundWire(String numUnremovedGroundWire) {
		this.numUnremovedGroundWire = numUnremovedGroundWire;
	}
	public String getWorkBreakTime() {
		return workBreakTime;
	}
	public void setWorkBreakTime(String workBreakTime) {
		this.workBreakTime = workBreakTime;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getStationLineName() {
		return stationLineName;
	}
	public void setStationLineName(String stationLineName) {
		this.stationLineName = stationLineName;
	}
	public String getWorkTask() {
		return workTask;
	}
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	public String getWorkPrincipal() {
		return workPrincipal;
	}
	public void setWorkPrincipal(String workPrincipal) {
		this.workPrincipal = workPrincipal;
	}
	public String getTicketSigner() {
		return ticketSigner;
	}
	public void setTicketSigner(String ticketSigner) {
		this.ticketSigner = ticketSigner;
	}
	public String getWorkLicensor() {
		return workLicensor;
	}
	public void setWorkLicensor(String workLicensor) {
		this.workLicensor = workLicensor;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}
	public String getTicketEndTime() {
		return ticketEndTime;
	}
	public void setTicketEndTime(String ticketEndTime) {
		this.ticketEndTime = ticketEndTime;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getWorkPermissionTime() {
		return workPermissionTime;
	}
	public void setWorkPermissionTime(String workPermissionTime) {
		this.workPermissionTime = workPermissionTime;
	}
	public String getIsOuterDept() {
		return isOuterDept;
	}
	public void setIsOuterDept(String isOuterDept) {
		this.isOuterDept = isOuterDept;
	}
	public String getTicketCounterSigner() {
		return ticketCounterSigner;
	}
	public void setTicketCounterSigner(String ticketCounterSigner) {
		this.ticketCounterSigner = ticketCounterSigner;
	}
	public String getTicketSignTime() {
		return ticketSignTime;
	}
	public void setTicketSignTime(String ticketSignTime) {
		this.ticketSignTime = ticketSignTime;
	}
	public String getTicketCounterSignTime() {
		return ticketCounterSignTime;
	}
	public void setTicketCounterSignTime(String ticketCounterSignTime) {
		this.ticketCounterSignTime = ticketCounterSignTime;
	}
	public String getWorkDelayTime() {
		return workDelayTime;
	}
	public void setWorkDelayTime(String workDelayTime) {
		this.workDelayTime = workDelayTime;
	}
	public String getNumOfWork() {
		return numOfWork;
	}
	public void setNumOfWork(String numOfWork) {
		this.numOfWork = numOfWork;
	}
	public String getWorkMemberUname() {
		return workMemberUname;
	}
	public void setWorkMemberUname(String workMemberUname) {
		this.workMemberUname = workMemberUname;
	}
	public String getPowerCutLine() {
		return powerCutLine;
	}
	public void setPowerCutLine(String powerCutLine) {
		this.powerCutLine = powerCutLine;
	}
	public String getPullBreaker() {
		return pullBreaker;
	}
	public void setPullBreaker(String pullBreaker) {
		this.pullBreaker = pullBreaker;
	}
	public String getPullSwitch() {
		return pullSwitch;
	}
	public void setPullSwitch(String pullSwitch) {
		this.pullSwitch = pullSwitch;
	}
	public String getPullBreakerSwitch() {
		return pullBreakerSwitch;
	}
	public void setPullBreakerSwitch(String pullBreakerSwitch) {
		this.pullBreakerSwitch = pullBreakerSwitch;
	}
	public String getDcPowerLowpCircle() {
		return dcPowerLowpCircle;
	}
	public void setDcPowerLowpCircle(String dcPowerLowpCircle) {
		this.dcPowerLowpCircle = dcPowerLowpCircle;
	}
	public String getSwitchEarthwireInsulation() {
		return switchEarthwireInsulation;
	}
	public void setSwitchEarthwireInsulation(String switchEarthwireInsulation) {
		this.switchEarthwireInsulation = switchEarthwireInsulation;
	}
	public String getIsHandleSecondMeasures() {
		return isHandleSecondMeasures;
	}
	public void setIsHandleSecondMeasures(String isHandleSecondMeasures) {
		this.isHandleSecondMeasures = isHandleSecondMeasures;
	}
	public String getNumSecondMeasure() {
		return numSecondMeasure;
	}
	public void setNumSecondMeasure(String numSecondMeasure) {
		this.numSecondMeasure = numSecondMeasure;
	}
	public String getOtherSafeCares() {
		return otherSafeCares;
	}
	public void setOtherSafeCares(String otherSafeCares) {
		this.otherSafeCares = otherSafeCares;
	}
	public String getWorkBreakLicensor() {
		return workBreakLicensor;
	}
	public void setWorkBreakLicensor(String workBreakLicensor) {
		this.workBreakLicensor = workBreakLicensor;
	}
	public String getWorkBreakPrincipal() {
		return workBreakPrincipal;
	}
	public void setWorkBreakPrincipal(String workBreakPrincipal) {
		this.workBreakPrincipal = workBreakPrincipal;
	}
	public String getWorkStartTime() {
		return workStartTime;
	}
	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}
	public String getWorkStartLicensor() {
		return workStartLicensor;
	}
	public void setWorkStartLicensor(String workStartLicensor) {
		this.workStartLicensor = workStartLicensor;
	}
	public String getWorkDelayLicensor() {
		return workDelayLicensor;
	}
	public void setWorkDelayLicensor(String workDelayLicensor) {
		this.workDelayLicensor = workDelayLicensor;
	}
	public String getWorkDelayPrincipal() {
		return workDelayPrincipal;
	}
	public void setWorkDelayPrincipal(String workDelayPrincipal) {
		this.workDelayPrincipal = workDelayPrincipal;
	}
	public String getWorkEndContent() {
		return workEndContent;
	}
	public void setWorkEndContent(String workEndContent) {
		this.workEndContent = workEndContent;
	}
	public String getTicketEndLicensor() {
		return ticketEndLicensor;
	}
	public void setTicketEndLicensor(String ticketEndLicensor) {
		this.ticketEndLicensor = ticketEndLicensor;
	}
	public String getWorkDelayApplyTime() {
		return workDelayApplyTime;
	}
	public void setWorkDelayApplyTime(String workDelayApplyTime) {
		this.workDelayApplyTime = workDelayApplyTime;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getWhetherLineGrounding() {
		return whetherLineGrounding;
	}
	public void setWhetherLineGrounding(String whetherLineGrounding) {
		this.whetherLineGrounding = whetherLineGrounding;
	}
	public String getOtherCare() {
		return otherCare;
	}
	public void setOtherCare(String otherCare) {
		this.otherCare = otherCare;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getWhetherGuardianSign() {
		return whetherGuardianSign;
	}
	public void setWhetherGuardianSign(String whetherGuardianSign) {
		this.whetherGuardianSign = whetherGuardianSign;
	}
	public String getSafeAccountUname() {
		return safeAccountUname;
	}
	public void setSafeAccountUname(String safeAccountUname) {
		this.safeAccountUname = safeAccountUname;
	}
	public String getSafeAccountTime() {
		return safeAccountTime;
	}
	public void setSafeAccountTime(String safeAccountTime) {
		this.safeAccountTime = safeAccountTime;
	}
	public String getTicketReceiveUname() {
		return ticketReceiveUname;
	}
	public void setTicketReceiveUname(String ticketReceiveUname) {
		this.ticketReceiveUname = ticketReceiveUname;
	}
	public String getAddContentDetail() {
		return addContentDetail;
	}
	public void setAddContentDetail(String addContentDetail) {
		this.addContentDetail = addContentDetail;
	}
	public String getTouchPosition() {
		return touchPosition;
	}
	public void setTouchPosition(String touchPosition) {
		this.touchPosition = touchPosition;
	}
	public String getIsTouch() {
		return isTouch;
	}
	public void setIsTouch(String isTouch) {
		this.isTouch = isTouch;
	}
	public String getLineGroundingUname() {
		return lineGroundingUname;
	}
	public void setLineGroundingUname(String lineGroundingUname) {
		this.lineGroundingUname = lineGroundingUname;
	}
	public String getEleGeneratrixWire() {
		return eleGeneratrixWire;
	}
	public void setEleGeneratrixWire(String eleGeneratrixWire) {
		this.eleGeneratrixWire = eleGeneratrixWire;
	}
	public String getEleSwitch() {
		return eleSwitch;
	}
	public void setEleSwitch(String eleSwitch) {
		this.eleSwitch = eleSwitch;
	}
	public String getElePart() {
		return elePart;
	}
	public void setElePart(String elePart) {
		this.elePart = elePart;
	}
	public String getWorkEndTime() {
		return workEndTime;
	}
	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}
	public String getMeasureEndFillTime() {
		return measureEndFillTime;
	}
	public void setMeasureEndFillTime(String measureEndFillTime) {
		this.measureEndFillTime = measureEndFillTime;
	}
	public String getDispatchPriUname() {
		return dispatchPriUname;
	}
	public void setDispatchPriUname(String dispatchPriUname) {
		this.dispatchPriUname = dispatchPriUname;
	}
	public String getDispatchUname() {
		return dispatchUname;
	}
	public void setDispatchUname(String dispatchUname) {
		this.dispatchUname = dispatchUname;
	}
	public String getMeasureEndPerUname() {
		return measureEndPerUname;
	}
	public void setMeasureEndPerUname(String measureEndPerUname) {
		this.measureEndPerUname = measureEndPerUname;
	}
	public String getDispatchFillTime() {
		return dispatchFillTime;
	}
	public void setDispatchFillTime(String dispatchFillTime) {
		this.dispatchFillTime = dispatchFillTime;
	}
	public String getPermissionOtherCare() {
		return permissionOtherCare;
	}
	public void setPermissionOtherCare(String permissionOtherCare) {
		this.permissionOtherCare = permissionOtherCare;
	}
	public String getPermisstionPrincipal() {
		return permisstionPrincipal;
	}
	public void setPermisstionPrincipal(String permisstionPrincipal) {
		this.permisstionPrincipal = permisstionPrincipal;
	}
	public String getRequiredSafty() {
		return requiredSafty;
	}
	public void setRequiredSafty(String requiredSafty) {
		this.requiredSafty = requiredSafty;
	}
	public String getSaftyAndCare() {
		return saftyAndCare;
	}
	public void setSaftyAndCare(String saftyAndCare) {
		this.saftyAndCare = saftyAndCare;
	}
	public BigDecimal getIsLineSwitchRecove() {
		return isLineSwitchRecove;
	}
	public void setIsLineSwitchRecove(BigDecimal isLineSwitchRecove) {
		this.isLineSwitchRecove = isLineSwitchRecove;
	}
	public String getRepairControlUname() {
		return repairControlUname;
	}
	public void setRepairControlUname(String repairControlUname) {
		this.repairControlUname = repairControlUname;
	}
	public String getLiveSafeMeasure() {
		return liveSafeMeasure;
	}
	public void setLiveSafeMeasure(String liveSafeMeasure) {
		this.liveSafeMeasure = liveSafeMeasure;
	}
	public String getSaftyStartUname() {
		return saftyStartUname;
	}
	public void setSaftyStartUname(String saftyStartUname) {
		this.saftyStartUname = saftyStartUname;
	}
	public String getWorkGuardianUname() {
		return workGuardianUname;
	}
	public void setWorkGuardianUname(String workGuardianUname) {
		this.workGuardianUname = workGuardianUname;
	}
	public String getTicketExecuteUname() {
		return ticketExecuteUname;
	}
	public void setTicketExecuteUname(String ticketExecuteUname) {
		this.ticketExecuteUname = ticketExecuteUname;
	}
	public String getWorkPrincipalTel() {
		return workPrincipalTel;
	}
	public void setWorkPrincipalTel(String workPrincipalTel) {
		this.workPrincipalTel = workPrincipalTel;
	}
	public String getProtectionStartUname() {
		return protectionStartUname;
	}
	public void setProtectionStartUname(String protectionStartUname) {
		this.protectionStartUname = protectionStartUname;
	}
	public String getBureauStartUname() {
		return bureauStartUname;
	}
	public void setBureauStartUname(String bureauStartUname) {
		this.bureauStartUname = bureauStartUname;
	}
	public String getWatchUname() {
		return watchUname;
	}
	public void setWatchUname(String watchUname) {
		this.watchUname = watchUname;
	}
	public String getEleLineDevice() {
		return eleLineDevice;
	}
	public void setEleLineDevice(String eleLineDevice) {
		this.eleLineDevice = eleLineDevice;
	}
	public String getWorkEndEarthwireCount() {
		return workEndEarthwireCount;
	}
	public void setWorkEndEarthwireCount(String workEndEarthwireCount) {
		this.workEndEarthwireCount = workEndEarthwireCount;
	}
	public String getWorkEndReportWay() {
		return workEndReportWay;
	}
	public void setWorkEndReportWay(String workEndReportWay) {
		this.workEndReportWay = workEndReportWay;
	}
	public String getWatchName() {
		return watchName;
	}
	public void setWatchName(String watchName) {
		this.watchName = watchName;
	}
	public String getEleLineDevicePer() {
		return eleLineDevicePer;
	}
	public void setEleLineDevicePer(String eleLineDevicePer) {
		this.eleLineDevicePer = eleLineDevicePer;
	}

}
