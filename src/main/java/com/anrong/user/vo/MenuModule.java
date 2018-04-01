package com.anrong.user.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @apiNote 每个模块菜单
 */
public class MenuModule {

    @JSONField(name = "text")
    private String moduleName; // 模块名称
    private String link;
    private String icon;

    @JSONField(serialize = false)
    private Integer id; // Module的ID

    // 每个模块下有多个app
    @JSONField(name = "children")
    private List<MenuApp> apps;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuApp> getApps() {
        return apps;
    }

    public void setApps(List<MenuApp> apps) {
        this.apps = apps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
