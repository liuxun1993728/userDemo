package com.anrong.user.service;


import com.anrong.user.po.SysApp;
import com.anrong.user.po.SysEnterprise;
import com.anrong.user.po.SysModule;
import com.anrong.user.vo.SysAppVO;

import java.util.List;

public interface AppService {
    Boolean addAppByAppInfo(SysApp sysApp);

    Boolean isValidateParentId(Integer enterpriseid, Integer parentid);

    Boolean saveApp(SysApp sysApp);

    Boolean isAppRelativedToApp(SysApp sysApp);

    Boolean deleteApp(SysApp sysApp);

    List<SysAppVO> getAppsofPageByCriteria(SysAppVO sysAppVO, Integer page, Integer results);

    SysEnterprise getEnterpriseByEnterpriseId(Integer enterpriseId);

    Boolean  getModuleByTwoId(Integer enterpriseId, Integer moduleid);

    List<SysEnterprise> getEnterpriseByUserId(Integer id);

    List<SysEnterprise> getAllEnterprises();

    List<SysModule> getModulesOfEnterpriseByEnterpriseId(Integer enterpriseId);
}
