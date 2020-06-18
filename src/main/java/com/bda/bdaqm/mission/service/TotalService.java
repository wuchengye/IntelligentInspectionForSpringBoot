package com.bda.bdaqm.mission.service;

import com.bda.bdaqm.mission.mapper.SessionJobMapper;
import com.bda.bdaqm.mission.mapper.TotalMapper;
import com.bda.bdaqm.mission.model.BdaqmTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalService {
    @Autowired
    private TotalMapper totalMapper;

    public int insertTotal(BdaqmTotal params){
        return totalMapper.insertTotal(params);
    }
}
