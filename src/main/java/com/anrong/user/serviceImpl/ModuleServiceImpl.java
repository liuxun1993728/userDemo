package com.anrong.user.serviceImpl;

import com.anrong.user.mappers.SysAppMapper;
import com.anrong.user.mappers.SysEmployeeMapper;
import com.anrong.user.mappers.SysEnterpriseMapper;
import com.anrong.user.mappers.SysModuleMapper;
import com.anrong.user.po.*;
import com.anrong.user.service.ModuleService;
import com.anrong.user.vo.SysModuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysAppMapper sysAppMapper;

    @Autowired
    private SysEnterpriseMapper sysEnterpriseMapper;

    @Override
    public Integer getEnterpriseIdByEmployeeId(Integer employeeId) {
        SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(employeeId);
        if (sysEmployee == null || sysEmployee.getEnterpriseid() == null) {
            return null;
        }

        return sysEmployee.getEnterpriseid();
    }

    @Override
    public Boolean addModuleByModuleInfo(SysModule sysModule) {
        int i = sysModuleMapper.insertSelective(sysModule);
        return i > 0;
    }

    @Override
    public Boolean saveModule(SysModule sysModule) {
        int i = sysModuleMapper.updateByPrimaryKeySelective(sysModule);
        return i > 0;
    }

    @Override
    public Boolean isAppRelativedToModule(SysModule sysModule) {
        Integer sysModuleId = sysModule.getId();
        SysAppExample example = new SysAppExample();
        example.createCriteria().andModuleidEqualTo(sysModuleId);
        int i = sysAppMapper.countByExample(example);

        return i > 0;
    }

    @Override
    public Boolean deleteModule(SysModule sysModule) {
        int i = sysModuleMapper.deleteByPrimaryKey(sysModule.getId());
        return i > 0;
    }

    @Override
    public List<SysModuleVO> getModuleVOsofPageByCriteria(SysModuleVO sysModuleQueryVO, Integer page, Integer results) {
        SysModuleExample example = new SysModuleExample();
        example.setCount(results);
        example.setLimitStart((page - 1) * results);
        if (sysModuleQueryVO.getEnterpriseId() != null) {
            example.createCriteria().andEnterpriseidEqualTo(sysModuleQueryVO.getEnterpriseId());
        }
        List<SysModule> sysModules = sysModuleMapper.selectByExample(example);
        List<SysModuleVO> sysModuleVOS = new ArrayList<>();
        for (SysModule sysModule : sysModules) {
            Integer creater = sysModule.getCreater();
            Integer enterpriseid = sysModule.getEnterpriseid();
            SysModuleVO sysModuleVO = new SysModuleVO();
            sysModuleVO.copyPropertiesFromSysModule(sysModule);
            if (creater != null) {
                SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(creater);
                sysModuleVO.setSysEmployee(sysEmployee);
            } else {
                sysModuleVO.setSysEmployee(null);
            }

            if (enterpriseid != null) {
                SysEnterprise sysEnterprise = sysEnterpriseMapper.selectByPrimaryKey(enterpriseid);
                sysModuleVO.setSysEnterprise(sysEnterprise);
            } else {
                sysModuleVO.setSysEnterprise(null);
            }
            sysModuleVOS.add(sysModuleVO);
        }

        return sysModuleVOS;
    }

    @Override
    public List<SysApp> getAppsOfModule(Integer id) {
        SysAppExample example = new SysAppExample();
        example.createCriteria().andModuleidEqualTo(id);
        List<SysApp> sysApps = sysAppMapper.selectByExample(example);
        return sysApps;
    }

    @Override
    public Boolean isModuleNameValidated(SysModule sysModule) {
        Integer enterpriseid = sysModule.getEnterpriseid();
        String moduleName = sysModule.getName().trim();
        SysModuleExample example = new SysModuleExample();
        example.createCriteria().andEnterpriseidEqualTo(enterpriseid).andNameEqualTo(moduleName);
        int i = sysModuleMapper.countByExample(example);
        return i <= 0;
    }
}
