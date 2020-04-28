package com.bda.bdaqm.electric.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ScoreSummary implements Serializable{
	
	private static final long serialVersionUID = 673960240569546685L;
	
	private String reportType;//报表类型
	private String unitRanking;//单位排名
	private String unitName;//单位名称
	private String deptmentName;
	private String groupName;
	private String personName;//人员姓名
	private String ticketSign;
	private String workPrincipal;
	private String workPermission;
	private String specialty;//所属专业
	private String classes;//所属班组
	private String one;//1月得分
	private String two;//2月得分
	private String three;//3月得分
	private String four;//4月得分
	private String five;//5月得分
	private String six;//6月得分
	private String seven;//7月得分
	private String eight;//8月得分
	private String nine;//9月得分
	private String ten;//10月得分
	private String eleven;//11月得分
	private String twelve;//12月得分
	private String total;//汇总
	private String monthName;//姓名
	private String monthScore;//得分
	private String monthQualifications;//三种人资格
	private String monthMajor;//专业
	private String monthLevel;//奖励级别
	private String monthMoney;//奖励金额（元）
	private String yearUnitName;//单位名称
	private String yearName;//姓名
	private String yearScore;//得分
	private String yearQualifications;//三种人资格
	private String yearMajor;//专业
	private String yearLevel;//奖励级别
	private String yearMoney;//奖励金额（元）
	
	///////////
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getUnitName() {
		return unitName;
	}
	public String getUnitRanking() {
		return unitRanking;
	}
	public void setUnitRanking(String unitRanking) {
		this.unitRanking = unitRanking;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
		setYearUnitName(unitName);
	}
	public String getDeptmentName() {
		return deptmentName;
	}
	public void setDeptmentName(String deptmentName) {
		this.deptmentName = deptmentName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
		setMonthName(personName);
		setYearName(personName);
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
		setMonthMajor(specialty);
		setYearMajor(specialty);
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getOne() {
		return one;
	}
	public void setOne(String one) {
		if(null == one) one = "0";
		this.one = ".5".equals(one)?"0.5":one;
	}
	public String getTwo() {
		return two;
	}
	public void setTwo(String two) {
		if(null == two) two = "0";
		this.two = ".5".equals(two)?"0.5":two;
	}
	public String getThree() {
		return three;
	}
	public void setThree(String three) {
		if(null == three) three = "0";
		this.three = ".5".equals(three)?"0.5":three;
	}
	public String getFour() {
		return four;
	}
	public void setFour(String four) {
		if(null == four) four = "0";
		this.four = ".5".equals(four)?"0.5":four;
	}
	public String getFive() {
		return five;
	}
	public void setFive(String five) {
		if(null == five) five = "0";
		this.five = ".5".equals(five)?"0.5":five;
	}
	public String getSix() {
		return six;
	}
	public void setSix(String six) {
		if(null == six) six = "0";
		this.six = ".5".equals(six)?"0.5":six;
	}
	public String getSeven() {
		return seven;
	}
	public void setSeven(String seven) {
		if(null == seven) seven = "0";
		this.seven = ".5".equals(seven)?"0.5":seven;
	}
	public String getEight() {
		return eight;
	}
	public void setEight(String eight) {
		if(null == eight) eight = "0";
		this.eight = ".5".equals(eight)?"0.5":eight;
	}
	public String getNine() {
		return nine;
	}
	public void setNine(String nine) {
		if(null == nine) nine = "0";
		this.nine = ".5".equals(nine)?"0.5":nine;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		if(null == ten) ten = "0";
		this.ten = ".5".equals(ten)?"0.5":ten;
	}
	public String getEleven() {
		return eleven;
	}
	public void setEleven(String eleven) {
		if(null == eleven) eleven = "0";
		this.eleven = ".5".equals(eleven)?"0.5":eleven;
	}
	public String getTwelve() {
		return twelve;
	}
	public void setTwelve(String twelve) {
		if(null == twelve) twelve = "0";
		this.twelve = ".5".equals(twelve)?"0.5":twelve;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		if(null == total) total = "0";
		this.total = ".5".equals(total)?"0.5":total;
		setMonthScore(total);
		setYearScore(total);
		//setLevelAndMoney();
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getMonthScore() {
		return monthScore;
	}
	public void setMonthScore(String monthScore) {
		this.monthScore = monthScore;
	}
	public String getMonthQualifications() {
		return monthQualifications;
	}
	public void setMonthQualifications(String monthQualifications) {
		this.monthQualifications = monthQualifications;
	}
	public String getMonthMajor() {
		return monthMajor;
	}
	public void setMonthMajor(String monthMajor) {
		this.monthMajor = monthMajor;
	}
	public String getMonthLevel() {
		return monthLevel;
	}
	public void setMonthLevel(String monthLevel) {
		this.monthLevel = monthLevel;
	}
	public String getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(String monthMoney) {
		this.monthMoney = monthMoney;
	}
	public String getYearUnitName() {
		return yearUnitName;
	}
	public void setYearUnitName(String yearUnitName) {
		this.yearUnitName = yearUnitName;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getYearScore() {
		return yearScore;
	}
	public void setYearScore(String yearScore) {
		this.yearScore = yearScore;
	}
	public String getYearQualifications() {
		return yearQualifications;
	}
	public void setYearQualifications(String yearQualifications) {
		this.yearQualifications = yearQualifications;
	}
	public String getYearMajor() {
		return yearMajor;
	}
	public void setYearMajor(String yearMajor) {
		this.yearMajor = yearMajor;
	}
	public String getYearLevel() {
		return yearLevel;
	}
	public void setYearLevel(String yearLevel) {
		this.yearLevel = yearLevel;
	}
	public String getYearMoney() {
		return yearMoney;
	}
	public void setYearMoney(String yearMoney) {
		this.yearMoney = yearMoney;
	}
	public String getTicketSign() {
		return ticketSign;
	}
	public void setTicketSign(String ticketSign) {
		this.ticketSign = ticketSign;
	}
	public String getWorkPrincipal() {
		return workPrincipal;
	}
	public void setWorkPrincipal(String workPrincipal) {
		this.workPrincipal = workPrincipal;
	}
	public String getWorkPermission() {
		return workPermission;
	}
	public void setWorkPermission(String workPermission) {
		this.workPermission = workPermission;
	}
	
	public void setLevelAndMoney(List<MonthReward> monthRwList, List<YearReward> yearRwList){
		if("工作负责人".equals(this.reportType)){
			setPriParams(monthRwList, yearRwList);
		}else if("工作许可人".equals(this.reportType)){
			setLicenorParams(monthRwList, yearRwList);
		}else if("工作票签发人".equals(this.reportType)){
			setLssueParams(monthRwList, yearRwList);
		}
	}
	
	//设置负责人奖励等级/金额
	public void setPriParams(List<MonthReward> monthRwList, List<YearReward> yearRwList){
		//月累计
		//if( "输电".equals(this.monthMajor) ){
		if(Arrays.asList(StringArrayEntity.transmission).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthTrans())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		//}else if("变电二次".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeTwice).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthTwice())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		//}else if("变电检修试验".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRepair).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthInspection())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}else if(Arrays.asList(StringArrayEntity.changeRun).contains(this.unitName)){//变电运行
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthOperation())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}else if(this.unitName.indexOf("供电局") > -1 
				|| Arrays.asList(StringArrayEntity.distributing).contains(this.unitName)){//配电
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthPower())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}
		
		//年累计
		//if( "输电".equals(this.monthMajor)){
		if(Arrays.asList(StringArrayEntity.transmission).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearTrans())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电运行".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRun).contains(this.unitName)){	
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearOperation())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电二次".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeTwice).contains(this.unitName)){	
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearTwice())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电检修试验".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRepair).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearInspection())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("配电".equals(this.monthMajor)){
		}else if(this.unitName.indexOf("供电局") > -1 || Arrays.asList(StringArrayEntity.distributing).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearPower())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		}
	}
	
	//设置许可人奖励等级/金额
	public void setLicenorParams(List<MonthReward> monthRwList, List<YearReward> yearRwList){
		//月累计
		//if( "输电".equals(this.monthMajor)){
		if(Arrays.asList(StringArrayEntity.transmission).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthTrans())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		//}else if("变电运行".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRun).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthOperation())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		//}else if("配电".equals(this.monthMajor)){
		}else if(this.unitName.indexOf("供电局") > -1 || Arrays.asList(StringArrayEntity.distributing).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthPower())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}
		
		//年累计
		//if("输电".equals(this.monthMajor)){
		if(Arrays.asList(StringArrayEntity.transmission).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearTrans())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRun).contains(this.unitName) || Arrays.asList(StringArrayEntity.changeTwice).contains(this.unitName) || Arrays.asList(StringArrayEntity.changeTwice).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearTwice())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("配电".equals(this.monthMajor)){
		}else if(this.unitName.indexOf("供电局") > -1 || Arrays.asList(StringArrayEntity.distributing).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearPower())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		}
	}
	
	//设置签发人奖励等级/金额
	public void setLssueParams(List<MonthReward> monthRwList, List<YearReward> yearRwList){
		//月累计
		//if( "输电".equals(this.monthMajor) ){
		if(Arrays.asList(StringArrayEntity.transmission).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthTrans())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		//}else if("变电二次".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeTwice).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthTwice())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		//}else if("变电检修试验".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRepair).contains(this.unitName)){
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthInspection())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}else if(Arrays.asList(StringArrayEntity.changeRun).contains(this.unitName)){//变电运行
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthOperation())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}else if(this.unitName.indexOf("供电局") > -1 
				|| Arrays.asList(StringArrayEntity.distributing).contains(this.unitName)){//配电
			for (MonthReward m : monthRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(m.getMonthPower())){
					this.setMonthLevel( m.getMonthGrade() );// eg:五星工作负责人
					this.setMonthMoney( m.getLevelMoneyMonth() );//eg:20000元
					break;
				}
			}
		}

		//年累计
		//if( "输电".equals(this.monthMajor)){
		if(Arrays.asList(StringArrayEntity.transmission).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearTrans())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电运行".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRun).contains(this.unitName)){	
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearOperation())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电二次".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeTwice).contains(this.unitName)){	
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearTwice())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("变电检修试验".equals(this.monthMajor)){
		}else if(Arrays.asList(StringArrayEntity.changeRepair).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearInspection())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		//}else if("配电".equals(this.monthMajor)){
		}else if(this.unitName.indexOf("供电局") > -1 || Arrays.asList(StringArrayEntity.distributing).contains(this.unitName)){
			for (YearReward y : yearRwList) {
				if(Float.parseFloat(this.total) >= Integer.parseInt(y.getYearPower())){
					this.setYearLevel( y.getYearGrade() );// eg:一等奖
					this.setYearMoney( y.getLevelMoneyYear() );//eg:5000
					break;
				}
			}
		}
	}
	
	//计算总分
	
	public void calculateTotalScore(){
		float total = changeStr2Float(one) + changeStr2Float(two) + changeStr2Float(three) + changeStr2Float(four) + 
				      changeStr2Float(five) + changeStr2Float(six) + changeStr2Float(seven) + changeStr2Float(eight) +
				      changeStr2Float(nine) + changeStr2Float(ten) + changeStr2Float(eleven) + changeStr2Float(twelve) ; 
		setTotal(total+"");
	}
	
	//将数字字符转为浮点数
	private float changeStr2Float(String str){
		if(!("").equals(str) && str.length() > 0){
			return Float.parseFloat(str);
		}
		return 0;
	}
}

class StringArrayEntity{

	//输电
	public static String[] transmission = {"输电管理所","输电管理二所","输电管理一所","项目管理中心"};
	//变电运行
	public static  String[] changeRun = {"变电管理一所","变电管理二所","变电管理三所","电力调度控制中心","调控中心","变电三所"};
	//变电二次
	public static  String[] changeTwice = {"计量中心","通信中心"};
	//变电检修
	public static  String[] changeRepair = {"电力试验研究院"};
	//配电
	public static  String[] distributing = {"区局","广州城市用电服务有限公司","路灯管理所"};
}