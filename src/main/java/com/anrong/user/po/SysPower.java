package com.anrong.user.po;

public class SysPower {
    private Integer id;

    private String name;

    private String code;

    private Integer enterpriseid;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getEnterpriseid() {
        return enterpriseid;
    }

    public void setEnterpriseid(Integer enterpriseid) {
        this.enterpriseid = enterpriseid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}