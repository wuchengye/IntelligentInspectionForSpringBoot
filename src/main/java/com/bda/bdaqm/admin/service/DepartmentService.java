package com.bda.bdaqm.admin.service;

import com.bda.bdaqm.admin.mapper.DepartmentMapper;
import com.bda.bdaqm.admin.model.DepartmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * @date 2020-06-23 16:06
     * 添加部门(传入父节点id),返回新部门信息
     */
    @Transactional
    public DepartmentModel insertDepartment(int parentId,DepartmentModel newOne){
        DepartmentModel departmentModel = selectDepartmentById(parentId);
        departmentMapper.updateRightWhenInsert(departmentModel.getRight());
        departmentMapper.updateLeftWhenInsert(departmentModel.getRight());
        newOne.setLeft(departmentModel.getRight());
        newOne.setRight(departmentModel.getRight() + 1);
        departmentMapper.insertDepartment(newOne);
        return newOne;
    }

    /**
     * @date 2020-06-23 19:53
     * 删除部门(传入待删除部门id)
     */
    @Transactional
    public void deleteDepartment(int id){
        DepartmentModel departmentModel = selectDepartmentById(id);
        departmentMapper.deleteDepartment(departmentModel);
        departmentMapper.updateLeftWhenDelete(departmentModel);
        departmentMapper.updateRightWhenDelete(departmentModel);
    }

    /**
     * @date 2020-06-23 16:10
     * 获取当前部门信息(传入id)
     */
    public DepartmentModel selectDepartmentById(int id){
        return departmentMapper.selectDepartmentById(id);
    }

    /**
     * @date 2020-06-23 16:26
     * 获取当前节点及其子节点(传入id)
     */
    public List<DepartmentModel> getDepartmentAndChildren(int id){
        DepartmentModel departmentModel = departmentMapper.selectDepartmentById(id);
        return departmentMapper.getDepartmentAndChildren(departmentModel);
    }
}
