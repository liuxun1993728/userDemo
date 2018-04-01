package com.anrong.user.serviceImpl;

import com.anrong.user.mappers.SysAppMapper;
import com.anrong.user.mappers.SysEmployeeMapper;
import com.anrong.user.mappers.SysEnterpriseMapper;
import com.anrong.user.mappers.SysModuleMapper;
import com.anrong.user.po.*;
import com.anrong.user.service.AppService;
import com.anrong.user.vo.SysAppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppServiceImpl implements AppService {

    @Autowired
    private SysAppMapper sysAppMapper;

    @Autowired
    private SysEnterpriseMapper sysEnterpriseMapper;

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Override
    public Boolean addAppByAppInfo(SysApp sysApp) {
        int i = sysAppMapper.insertSelective(sysApp);
        return i > 0;
    }

    @Override
    public Boolean isValidateParentId(Integer enterpriseid, Integer parentid) {
        if (enterpriseid == null || parentid == null) {
            return false;
        }

        SysApp sysApp = sysAppMapper.selectByPrimaryKey(parentid);
        if (sysApp == null || !sysApp.getEnterpriseid().equals(enterpriseid)) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean saveApp(SysApp sysApp) {
        int i = sysAppMapper.updateByPrimaryKeySelective(sysApp);
        return i > 0;
    }

    @Override
    public Boolean isAppRelativedToApp(SysApp sysApp) {
        SysAppExample example = new SysAppExample();
        example.createCriteria().andParentidEqualTo(sysApp.getId());
        int i = sysAppMapper.countByExample(example);
        return i > 0;
    }

    @Override
    public Boolean deleteApp(SysApp sysApp) {
        int i = sysAppMapper.deleteByPrimaryKey(sysApp.getId());
        return i > 0;
    }

    @Override
    public List<SysAppVO> getAppsofPageByCriteria(SysAppVO sysAppVO, Integer page, Integer results) {
        SysAppExample example = new SysAppExample();
        example.setCount(results);
        example.setLimitStart((page - 1) * results);
        if (sysAppVO.getEnterpriseId() != null) {
            example.createCriteria().andEnterpriseidEqualTo(sysAppVO.getEnterpriseId());
        }
        List<SysApp> sysApps = sysAppMapper.selectByExample(example);
        List<SysAppVO> sysAppVOS = new ArrayList<>();
        for(SysApp sysApp:sysApps){
            Integer enterpriseid = sysApp.getEnterpriseid();
            Integer creater = sysApp.getCreater();
            SysAppVO appVO = new SysAppVO();
            appVO.copyPropertiesFromSysApp(sysApp);
            if (enterpriseid!=null){
                SysEnterprise sysEnterprise = sysEnterpriseMapper.selectByPrimaryKey(enterpriseid);
                appVO.setSysEnterprise(sysEnterprise);
            }else{
                appVO.setSysEnterprise(null);
            }

            if (creater!=null){
                SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(creater);
                appVO.setSysEmployee(sysEmployee);
            }else{
                appVO.setSysEmployee(null);
            }
            sysAppVOS.add(appVO);
        }

        return sysAppVOS;
    }

    @Override
    public SysEnterprise getEnterpriseByEnterpriseId(Integer enterpriseId) {
        return sysEnterpriseMapper.selectByPrimaryKey(enterpriseId);
    }

    @Override
    public Boolean getModuleByTwoId(Integer enterpriseId, Integer moduleid) {
        SysModuleExample example = new SysModuleExample();
        example.createCriteria().andIdEqualTo(moduleid).andEnterpriseidEqualTo(enterpriseId);
        int i = sysModuleMapper.countByExample(example);
        return i > 0;
    }

    @Override
    public List<SysEnterprise> getEnterpriseByUserId(Integer id) {
        SysEmployeeExample example = new SysEmployeeExample();
        example.createCriteria().andUseridEqualTo(id);
        List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(example);
        List<SysEnterprise> sysEnterprises = new ArrayList<>();
        for (SysEmployee sysEmployee : sysEmployees) {
            SysEnterprise sysEnterprise = sysEnterpriseMapper.selectByPrimaryKey(sysEmployee.getEnterpriseid());
            sysEnterprises.add(sysEnterprise);
        }
        return sysEnterprises;
    }

    @Override
    public List<SysEnterprise> getAllEnterprises() {
        SysEnterpriseExample example = new SysEnterpriseExample();
        List<SysEnterprise> sysEnterprises = sysEnterpriseMapper.selectByExample(example);
        return sysEnterprises;
    }

    @Override
    public List<SysModule> getModulesOfEnterpriseByEnterpriseId(Integer enterpriseId) {
        SysModuleExample example = new SysModuleExample();
        example.createCriteria().andEnterpriseidEqualTo(enterpriseId);
        List<SysModule> sysModules = sysModuleMapper.selectByExample(example);
        return sysModules;
    }
}
