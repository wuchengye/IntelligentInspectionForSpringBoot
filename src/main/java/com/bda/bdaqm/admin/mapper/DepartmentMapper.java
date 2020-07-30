package com.bda.bdaqm.admin.mapper;

import com.bda.bdaqm.admin.model.DepartmentModel;

import java.util.List;

public interface DepartmentMapper {
    DepartmentModel selectDepartmentById(int id);
    List<DepartmentModel> getDepartmentAndChildren(DepartmentModel departmentModel);
    //插入系列操作
    void updateRightWhenInsert(int right);
    void updateLeftWhenInsert(int right);
    int insertDepartment(DepartmentModel newOne);
    //删除系列操作
    void deleteDepartment(DepartmentModel departmentModel);
    void updateLeftWhenDelete(DepartmentModel departmentModel);
    void updateRightWhenDelete(DepartmentModel departmentModel);
}
