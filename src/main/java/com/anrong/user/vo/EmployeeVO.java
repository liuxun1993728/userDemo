package com.anrong.user.vo;

import com.anrong.user.po.SysEmployee;
import com.anrong.user.po.SysEnterprise;

/**
 * @apiNote 企业信息的封装类
 */
public class EmployeeVO extends SysEmployee{

    private String deptName; // 部门名称
    private String enterpriseName; // 企业名称

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void copyPropertiesFromEmployee(SysEmployee sysEmployee){
        this.setCreatetime(sysEmployee.getCreatetime());
        this.setDeleteflag(sysEmployee.getDeleteflag());
        this.setDepartmentid(sysEmployee.getDepartmentid());
        this.setEmail(sysEmployee.getEmail());
        this.setEnterpriseid(sysEmployee.getEnterpriseid());
        this.setId(sysEmployee.getId());
        this.setMobile(sysEmployee.getMobile());
        this.setName(sysEmployee.getName());
        this.setUpdatetime(sysEmployee.getUpdatetime());
        this.setUserid(sysEmployee.getUserid());
    }

}
