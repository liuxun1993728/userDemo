package com.anrong.user.service;

import com.anrong.user.po.SysApp;
import com.anrong.user.po.SysModule;
import com.anrong.user.vo.SysModuleVO;

import java.util.List;

public interface ModuleService {
    Integer getEnterpriseIdByEmployeeId(Integer employeeId);

    Boolean addModuleByModuleInfo(SysModule sysModule);

    Boolean saveModule(SysModule sysModule);

    Boolean isAppRelativedToModule(SysModule sysModule);

    Boolean deleteModule(SysModule sysModule);

    List<SysModuleVO> getModuleVOsofPageByCriteria(SysModuleVO sysModuleVO, Integer page, Integer results);

    List<SysApp> getAppsOfModule(Integer id);

    Boolean isModuleNameValidated(SysModule sysModule);
}
