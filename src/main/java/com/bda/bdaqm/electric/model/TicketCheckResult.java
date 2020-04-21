package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.Table;
@Table(name="bda_ticket_check_result")
public class TicketCheckResult implements Serializable{

	private static final long serialVersionUID = 1028141666354576597L;
	
	
	private String ticketId;				//工作票ID
	private String ticketType;				//工作票类型
	private String classes;					//班组
	private String ticketFinalTime;			//工作票终结时间
	private String workPrincipal;			//负责人
	private String ticketSigner;			//签发人
	private String licensor;				//许可人
	private String checkResult;				//校验结果
	private String misuseTicket;			//错用工作票
	private String beyondPlan;				//计划工作时间超出计划停电时间或已过期。
	private String keywordError;			//工作票关键词字迹不清、错漏。
	private String workMemberCount;			//工作票面工作班人名、人数与实际不符。
	private String fillinTaskError;			//工作任务、停电线路名称（包括电压等级及名称）、工作地段、设备双重编号填写不明确、错漏，工作内容与实际不符。
	private String fillinSafeError;			//安全措施不完备或填写不正确。
	private String doubleIssueError;		//应“双签发”的工作票没有“双签发”
	private String emptyPermissTime;		//工作许可时间未填写
	private String handleChange;			//工作负责人有变更时未办理变更手续。
	private String handleDelay;				//应办理延期的工作未按要求办理工作延期手续，需办理工作间断手续的未办理或记录不全。
	private String finalContentError;		//工作终结栏的内容应填写的无填写
	private String licensorNoSign;			//工作过程中需要变更安全措施时，未经许可人签名同意
	private String keepMultiple;			//一人多票
	private String signError;				//工作票中工作票‘三种人’签发人（包括会签人）、工作负责人、工作许可人 不具备资格的，或冒签名、漏签名、签名不全。
	private String standardResult;			//规范校验结果
	private String noSave;					//应与工作票 一同保存的 安全技术交底单、二次设备及回路工作安全技术措施单、附页等，未与工作票一同保存。
	private String nonstandardWord;			//工作票上使用的技术术语不规范。
	private String batchTime;				//批次时间
	//结果字符串
	private String checkString;
	private String standardString;
	
	private String ticketNo;				//工作票编号
	
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getBatchTime() {
		return batchTime;
	}
	public void setBatchTime(String batchTime) {
		this.batchTime = batchTime;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getTicketFinalTime() {
		return ticketFinalTime;
	}
	public void setTicketFinalTime(String ticketFinalTime) {
		this.ticketFinalTime = ticketFinalTime;
	}
	public String getLicensor() {
		return licensor;
	}
	public void setLicensor(String licensor) {
		this.licensor = licensor;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getMisuseTicket() {
		return misuseTicket;
	}
	public void setMisuseTicket(String misuseTicket) {
		this.misuseTicket = misuseTicket;
	}
	public String getBeyondPlan() {
		return beyondPlan;
	}
	public void setBeyondPlan(String beyondPlan) {
		this.beyondPlan = beyondPlan;
	}
	public String getKeywordError() {
		return keywordError;
	}
	public void setKeywordError(String keywordError) {
		this.keywordError = keywordError;
	}
	public String getWorkMemberCount() {
		return workMemberCount;
	}
	public void setWorkMemberCount(String workMemberCount) {
		this.workMemberCount = workMemberCount;
	}
	public String getFillinTaskError() {
		return fillinTaskError;
	}
	public void setFillinTaskError(String fillinTaskError) {
		this.fillinTaskError = fillinTaskError;
	}
	public String getFillinSafeError() {
		return fillinSafeError;
	}
	public void setFillinSafeError(String fillinSafeError) {
		this.fillinSafeError = fillinSafeError;
	}
	public String getDoubleIssueError() {
		return doubleIssueError;
	}
	public void setDoubleIssueError(String doubleIssueError) {
		this.doubleIssueError = doubleIssueError;
	}
	public String getEmptyPermissTime() {
		return emptyPermissTime;
	}
	public void setEmptyPermissTime(String emptyPermissTime) {
		this.emptyPermissTime = emptyPermissTime;
	}
	public String getHandleChange() {
		return handleChange;
	}
	public void setHandleChange(String handleChange) {
		this.handleChange = handleChange;
	}
	public String getHandleDelay() {
		return handleDelay;
	}
	public void setHandleDelay(String handleDelay) {
		this.handleDelay = handleDelay;
	}
	public String getFinalContentError() {
		return finalContentError;
	}
	public void setFinalContentError(String finalContentError) {
		this.finalContentError = finalContentError;
	}
	public String getLicensorNoSign() {
		return licensorNoSign;
	}
	public void setLicensorNoSign(String licensorNoSign) {
		this.licensorNoSign = licensorNoSign;
	}
	public String getKeepMultiple() {
		return keepMultiple;
	}
	public void setKeepMultiple(String keepMultiple) {
		this.keepMultiple = keepMultiple;
	}
	public String getSignError() {
		return signError;
	}
	public void setSignError(String signError) {
		this.signError = signError;
	}
	public String getStandardResult() {
		return standardResult;
	}
	public void setStandardResult(String standardResult) {
		this.standardResult = standardResult;
	}
	public String getNoSave() {
		return noSave;
	}
	public void setNoSave(String noSave) {
		this.noSave = noSave;
	}
	public String getNonstandardWord() {
		return nonstandardWord;
	}
	public void setNonstandardWord(String nonstandardWord) {
		this.nonstandardWord = nonstandardWord;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
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
	
	
	public String getCheckString() {
		return checkString;
	}
	public void setCheckString() {
		String checkString = "";
		if("0".equals(this.checkResult)){
			checkString += "<span>合格性校验结果：</span><div style='color: red;width:80px;display:inline-block'>合格。</div>";
		}else if("1".equals(this.checkResult) || "2".equals(this.checkResult)){
			checkString += "<span>合格性校验结果：</span><div style='color: red;width:80px;display:inline-block'>不合格。</div><span>违反规则：</span><span style='color: red;'>";
			checkString += ("1".equals(this.misuseTicket)||"2".equals(this.misuseTicket))?"判据1":"";
			checkString += ("1".equals(this.beyondPlan)||"2".equals(this.beyondPlan))?(checkString.indexOf("判据") > -1 ? "、判据2" : "判据2"):"";
			checkString += ("1".equals(this.keywordError)||"2".equals(this.keywordError))?(checkString.indexOf("判据") > -1 ? "、判据3" : "判据3"):"";
			checkString += ("1".equals(this.workMemberCount)||"2".equals(this.workMemberCount))?(checkString.indexOf("判据") > -1 ? "、判据5" : "判据5"):"";
			checkString += ("1".equals(this.fillinTaskError)||"2".equals(this.fillinTaskError))?(checkString.indexOf("判据") > -1 ? "、判据6" : "判据6"):"";
			checkString += ("1".equals(this.fillinSafeError)||"2".equals(this.fillinSafeError))?(checkString.indexOf("判据") > -1 ? "、判据7" : "判据7"):"";
			checkString += ("1".equals(this.doubleIssueError)||"2".equals(this.doubleIssueError))?(checkString.indexOf("判据") > -1 ? "、判据8" : "判据8"):"";
			checkString += ("1".equals(this.emptyPermissTime)||"2".equals(this.emptyPermissTime))?(checkString.indexOf("判据") > -1 ? "、判据9" : "判据9"):"";
			checkString += ("1".equals(this.handleChange)||"2".equals(this.handleChange))?(checkString.indexOf("判据") > -1 ? "、判据10" : "判据10"):"";
			checkString += ("1".equals(this.handleDelay)||"2".equals(this.handleDelay))?(checkString.indexOf("判据") > -1 ? "、判据11" : "判据11"):"";
			checkString += ("1".equals(this.finalContentError)||"2".equals(this.finalContentError))?(checkString.indexOf("判据") > -1 ? "、判据13" : "判据13"):"";
			checkString += ("1".equals(this.licensorNoSign)||"2".equals(this.licensorNoSign))?(checkString.indexOf("判据") > -1 ? "、判据14" : "判据14"):"";
			checkString += ("1".equals(this.keepMultiple)||"2".equals(this.keepMultiple))?(checkString.indexOf("判据") > -1 ? "、判据15" : "判据15"):"";
			checkString += ("1".equals(this.signError)||"2".equals(this.signError))?(checkString.indexOf("判据") > -1 ? "、判据16" : "判据16"):"";
			checkString += "。</span>";
		}/*else if("2".equals(this.checkResult)){
			checkString += "<span>合格性校验结果：</span><div style='color: red;width:80px;display:inline-block'>异常。</div><span>违反规则：</span><span style='color: red;'>";
			checkString += ("1".equals(this.misuseTicket)||"2".equals(this.misuseTicket))?"判据1":"";
			checkString += ("1".equals(this.beyondPlan)||"2".equals(this.beyondPlan))?(checkString.indexOf("判据") > -1 ? "、判据2" : "判据2"):"";
			checkString += ("1".equals(this.keywordError)||"2".equals(this.keywordError))?(checkString.indexOf("判据") > -1 ? "、判据3" : "判据3"):"";
			checkString += ("1".equals(this.workMemberCount)||"2".equals(this.workMemberCount))?(checkString.indexOf("判据") > -1 ? "、判据5" : "判据5"):"";
			checkString += ("1".equals(this.fillinTaskError)||"2".equals(this.fillinTaskError))?(checkString.indexOf("判据") > -1 ? "、判据6" : "判据6"):"";
			checkString += ("1".equals(this.fillinSafeError)||"2".equals(this.fillinSafeError))?(checkString.indexOf("判据") > -1 ? "、判据7" : "判据7"):"";
			checkString += ("1".equals(this.doubleIssueError)||"2".equals(this.doubleIssueError))?(checkString.indexOf("判据") > -1 ? "、判据8" : "判据8"):"";
			checkString += ("1".equals(this.emptyPermissTime)||"2".equals(this.emptyPermissTime))?(checkString.indexOf("判据") > -1 ? "、判据9" : "判据9"):"";
			checkString += ("1".equals(this.handleChange)||"2".equals(this.handleChange))?(checkString.indexOf("判据") > -1 ? "、判据10" : "判据10"):"";
			checkString += ("1".equals(this.handleDelay)||"2".equals(this.handleDelay))?(checkString.indexOf("判据") > -1 ? "、判据11" : "判据11"):"";
			checkString += ("1".equals(this.finalContentError)||"2".equals(this.finalContentError))?(checkString.indexOf("判据") > -1 ? "、判据13" : "判据13"):"";
			checkString += ("1".equals(this.licensorNoSign)||"2".equals(this.licensorNoSign))?(checkString.indexOf("判据") > -1 ? "、判据14" : "判据14"):"";
			checkString += ("1".equals(this.keepMultiple)||"2".equals(this.keepMultiple))?(checkString.indexOf("判据") > -1 ? "、判据15" : "判据15"):"";
			checkString += ("1".equals(this.signError)||"2".equals(this.signError))?(checkString.indexOf("判据") > -1 ? "、判据16" : "判据16"):"";
			checkString += "。</span>";
		}*/
		
		this.checkString = checkString;
	}
	public String getStandardString() {
		return standardString;
	}
	public void setStandardString() {
		String standardString = "";
		if("0".equals(this.standardResult)){
			standardString += "<span>规范性校验结果：</span><div style='color: red;width:80px;display:inline-block'>规范。</div>";
		}else if("1".equals(this.standardResult) || "2".equals(this.standardResult)){
			standardString += "<span>规范性校验结果：</span><div style='color: red;width:80px;display:inline-block'>不规范。</div><span>违反规则：</span><span style='color: red;'>";
			standardString += ("1".equals(this.noSave)||"2".equals(this.noSave))?"判据4":"";
			standardString += ("1".equals(this.nonstandardWord)||"2".equals(this.nonstandardWord))?(standardString.indexOf("判据") > -1 ? "、判据5" : "判据5"):"";
			standardString += "。</span>";
		}/*else if("2".equals(this.standardResult)){
			standardString += "<span>规范性校验结果：</span><div style='color: red;width:80px;display:inline-block'>异常。</div><span>违反规则：</span><span style='color: red;'>";
			standardString += ("1".equals(this.noSave)||"2".equals(this.noSave))?"判据4":"";
			standardString += ("1".equals(this.nonstandardWord)||"2".equals(this.nonstandardWord))?(standardString.indexOf("判据") > -1 ? "、判据5" : "判据5"):"";
			standardString += "。</span>";
		}*/
		this.standardString = standardString;
	}
	
	
	
	
}
