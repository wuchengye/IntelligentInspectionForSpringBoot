package com.bda.bdaqm.admin.controller;

import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import com.bda.bdaqm.admin.model.DepartmentModel;
import com.bda.bdaqm.admin.model.Role;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.DepartmentService;
import com.bda.bdaqm.admin.service.MyRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"department/manage"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MyRoleService roleService;

    @RequestMapping("/insertDepartment")
    public Result insertDepartment(int id, DepartmentModel newOne){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Role role  = roleService.selectRoleByUserId(user.getUserId());
        if(role == null){
            return Result.failure(ResultCode.DEPARTMENT_NO_ROLE);
        }
        if(role.getAbility() < 3){
            return Result.failure(ResultCode.DEPARTMENT_NO_PERMISSION);
        }
        newOne.setUpdateUserName(user.getAccount());
        newOne = departmentService.insertDepartment(id,newOne);
        if(newOne.getDepartmentId() == null){
            return Result.failure(ResultCode.DEPARTMENT_INSERT_ERROR);
        }else {
            return Result.success(newOne);
        }
    }

    @RequestMapping("/deleteDepartment")
    public Result deleteDepartment(int id){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Role role  = roleService.selectRoleByUserId(user.getUserId());
        if(role == null){
            return Result.failure(ResultCode.DEPARTMENT_NO_ROLE);
        }
        if(role.getAbility() < 3){
            return Result.failure(ResultCode.DEPARTMENT_NO_PERMISSION);
        }
        try {
            departmentService.deleteDepartment(id);
            return Result.success();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.failure(ResultCode.DEPARTMENT_DELETE_ERROR);
        }
    }

    @RequestMapping("/getDepartment")
    public Result getDepartment(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Role myRole = roleService.selectRoleByUserId(user.getUserId());
        if(myRole != null){
            if(myRole.getAbility() == 2){
                List<DepartmentModel> list = new ArrayList<>();
                list.add(departmentService.selectDepartmentById(user.getDepartmentId()));
                return Result.success(list);
            }
            if(myRole.getAbility() == 3){
                return Result.success(departmentService.getDepartmentAndChildren(user.getDepartmentId()));
            }else {
                return Result.failure(ResultCode.DEPARTMENT_NO_PERMISSION);
            }
        }else {
            return Result.failure(ResultCode.DEPARTMENT_NO_ROLE);
        }
    }

}
