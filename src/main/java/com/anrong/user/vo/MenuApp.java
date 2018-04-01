package com.anrong.user.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @apiNote 每个模块下的app菜单
 */
public class MenuApp {
    @JSONField(name = "text")
    private String appName; // 每个app的名称
    private String link;
    private String icon;

    @JSONField(serialize = false)
    private Integer id; // app的主键

    @JSONField(name = "children")
    private List<MenuApp> menuApps;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public List<MenuApp> getMenuApps() {
        return menuApps;
    }

    public void setMenuApps(List<MenuApp> menuApps) {
        this.menuApps = menuApps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
