package com.anrong.user.vo;

import com.anrong.user.core.utils.MD5Tools;
import com.anrong.user.po.SysApp;

import java.util.List;

/**
 * @author liuxun
 * @apiNote 模块的封装类
 */
public class ModuleVO {
    private String name; // 模块的名称
    private List<SysApp> sysApps; // 模块下的所有app

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SysApp> getSysApps() {
        return sysApps;
    }

    public void setSysApps(List<SysApp> sysApps) {
        this.sysApps = sysApps;
    }

    public static void main(String args[]) {
        System.out.println(MD5Tools.MD5("123456"));
    }
}
