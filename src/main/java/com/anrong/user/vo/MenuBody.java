package com.anrong.user.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @apiNote  整个菜单体
 */
public class MenuBody {
    @JSONField(name = "text")
    private String bodyName; // 根菜单名称

    // 主菜单包含多个模块
    @JSONField(name = "children")
    private List<MenuModule> modules;

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    public List<MenuModule> getModules() {
        return modules;
    }

    public void setModules(List<MenuModule> modules) {
        this.modules = modules;
    }
}
