package com.anrong.user.vo;

import com.anrong.user.po.SysEmployee;
import com.anrong.user.po.SysEnterprise;
import com.anrong.user.po.SysModule;

public class SysModuleVO extends SysModule{

    // 此处封装的是返回的数据
    private SysEmployee sysEmployee;
    private SysEnterprise sysEnterprise;

    // 此处封装查询条件相关的字段
    private Integer enterpriseId;

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

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public void copyPropertiesFromSysModule(SysModule sysModule){
        this.setCreater(sysModule.getCreater());
        this.setDescription(sysModule.getDescription());
        this.setEnterpriseid(sysModule.getEnterpriseid());
        this.setIcon(sysModule.getIcon());
        this.setId(sysModule.getId());
        this.setName(sysModule.getName());
    }
}
