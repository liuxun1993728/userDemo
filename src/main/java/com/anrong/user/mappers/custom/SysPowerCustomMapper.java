package com.anrong.user.mappers.custom;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @apiNote 自定义 权限查询mapper
 * @author liuxun
 */

@Mapper
public interface SysPowerCustomMapper {
    @Select(
   "SELECT DISTINCT sp.url FROM sys_power sp,sys_app_power sap,sys_role_app sra,sys_role_employee sre,sys_employee se,sys_enterprise sep,sys_user su WHERE sap.powerId=sp.id AND sap.appId=sra.appId AND sep.id=sp.enterpriseId AND sra.roleId=sre.roleId AND sre.employeeId=se.id AND se.userId=su.id AND se.enterpriseId=sep.id AND sep.deleteFlag=0 AND se.deleteFlag=0 AND su.deleteFlag=0 AND su.id=#{userId};"
    )
    public List<String> getAllPowerUrlByUserId(Integer userId);
}
