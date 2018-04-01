package com.anrong.user.vo;

import com.anrong.user.po.SysApp;
import com.anrong.user.po.SysEmployee;
import com.anrong.user.po.SysEnterprise;

/**
 * @apiNote 用于封装查询条件 以及返回的数据类型
 */
public class SysAppVO extends SysApp {

    // 此处封装的是返回的数据
    private SysEmployee sysEmployee;
    private SysEnterprise sysEnterprise;

    // 此处封装查询条件字段
    private Integer enterpriseId;

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public SysEmployee getSysEmployee() {
        return sysEmployee;
    }

    public void setSysEmployee(SysEmployee sysEmployee) {
        this.sysEmployee = sysEmployee;
    }

    public SysEnterprise getSysEnterprise() {
        return sysEnterprise;
    }

    public void setSysEnterprise(SysEnterprise sysEnterprise) {
        this.sysEnterprise = sysEnterprise;
    }

    public void  copyPropertiesFromSysApp(SysApp sysApp){
        this.setDescription(sysApp.getDescription());
        this.setEnterpriseid(sysApp.getEnterpriseid());
        this.setIcon(sysApp.getIcon());
        this.setId(sysApp.getId());
        this.setModuleid(sysApp.getModuleid());
        this.setName(sysApp.getName());
        this.setParentid(sysApp.getParentid());
        this.setSequence(sysApp.getSequence());
        this.setUrl(sysApp.getUrl());
    }
}
