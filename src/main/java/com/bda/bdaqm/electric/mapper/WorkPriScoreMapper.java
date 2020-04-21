package com.bda.bdaqm.electric.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.electric.model.Modulu;
import com.bda.bdaqm.electric.model.MonthReward;
import com.bda.bdaqm.electric.model.ScoreSummary;
import com.bda.bdaqm.electric.model.YearReward;
import com.bda.bdaqm.util.ComboBoxItem;

import tk.mybatis.mapper.common.Mapper;

public interface WorkPriScoreMapper extends Mapper<ScoreSummary>{
	
    List<ScoreSummary> getWorkPriScore(Map<String,Object>params);
    
    //获取单位信息
    List<ComboBoxItem> getUnitCombobox();
    
    //获取专业信息
    List<ComboBoxItem> getSpecialtyCombobox();
    
    //获取班组信息
    List<ComboBoxItem> getClassCombobox(@Param("unitName")String unitName);
	
    //获取月度奖励分数
    List<MonthReward> getMonthReward();
    List<MonthReward> getMonthVerify(@Param("type")String type);
    
    //修改月度奖励分数
    void updateMonth(@Param("monthReward")MonthReward monthReward);
    
    //获取年度奖励分数
    List<YearReward> getYearReward();
    List<YearReward> getYearVerify(@Param("type")String type);
    List<Modulu> getModulusByType(@Param("type")String type);
    
    //修改年度奖励分数
    void updateYear(@Param("yearReward")YearReward yearReward);
    
    //获取系数配置数据
    List<Modulu> getModulus();
    //修改系数
    void updateModulus(@Param("modulus")Modulu modulus);
}
