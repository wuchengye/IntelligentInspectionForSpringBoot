package com.bda.bdaqm.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bda.bdaqm.util.ComboBoxItem;

public interface QmAuthMapper {
	public List<ComboBoxItem> getGroupCombo(@Param("orgId") String orgId,@Param("yearmonth") String yearmonth);
}
