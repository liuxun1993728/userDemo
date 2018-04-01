package com.anrong.user.po;

/**
 * Created by liudh on 2018/2/8.
 */
public class SysRoleAppEx extends SysRoleApp {
    String appName;
    String appIcon;
    String appUrl;
    Integer appMoudleId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getAppMoudleId() {
        return appMoudleId;
    }

    public void setAppMoudleId(Integer appMoudleId) {
        this.appMoudleId = appMoudleId;
    }
}
