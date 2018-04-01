package com.anrong.user.vo;

import com.anrong.user.po.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuxun
 * @apiNote 用于存入Token的包装类信息
 */
public class ActiveUserVO {
    private List<String> permissons; // 指的是所有当前用户在所有公司的权限URL
    private SysUser sysUser; // 当前登录的用户信息
    // 当前最近登录的员工(根据更新时间)
    private Integer employeeId;

    // 当前最近登录的员工信息
    EmployeeVO employeeVO;

    public List<String> getPermissons() {
        return permissons;
    }

    public void setPermissons(List<String> permissons) {
        this.permissons = permissons;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeVO getEmployeeVO() {
        return employeeVO;
    }

    public void setEmployeeVO(EmployeeVO employeeVO) {
        this.employeeVO = employeeVO;
    }
}
