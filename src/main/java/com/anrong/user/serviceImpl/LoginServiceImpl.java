package com.anrong.user.serviceImpl;

import com.anrong.user.core.utils.MD5Tools;
import com.anrong.user.mappers.SysDepartmentMapper;
import com.anrong.user.mappers.SysEmployeeMapper;
import com.anrong.user.mappers.SysEnterpriseMapper;
import com.anrong.user.mappers.SysUserMapper;
import com.anrong.user.mappers.custom.SysPowerCustomMapper;
import com.anrong.user.po.*;
import com.anrong.user.service.LoginService;
import com.anrong.user.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liuxun
 * @apiNote 登录的业务实现类
 */

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private SysEnterpriseMapper sysEnterpriseMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Override
    public SysUser getUserByAccountAndPassword(String userName, String password,Integer type) {
        String pass = MD5Tools.MD5(password);
        SysUserExample example = new SysUserExample();
//        example.setCount(1);
        SysUserExample.Criteria criteria = example.createCriteria();
        if (type==0){
            criteria.andLogincodeEqualTo(userName);
        }
        if (type==1){
            criteria.andMobileEqualTo(userName);
        }

        criteria.andPasswordEqualTo(pass).andDeleteflagEqualTo(0);

        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        return sysUsers.size() > 0 ? sysUsers.get(0) : null;
    }

    @Override
    public List<String> getAllPowersByUserId(Integer userID) {

        return null;
    }

    @Override
    public List<EmployeeVO> getEnterpriseAndEmployeesByUserId(Integer id) {

        List<EmployeeVO> employeeVOS = new ArrayList<>();
        SysEmployeeExample exampleE= new SysEmployeeExample();
        exampleE.createCriteria().andUseridEqualTo(id).andDeleteflagEqualTo(0);
        exampleE.setOrderByClause("updateTime desc");
        List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(exampleE);

        for (SysEmployee sysEmployee:sysEmployees){
            Integer enterpriseid = sysEmployee.getEnterpriseid();
            Integer departmentid = sysEmployee.getDepartmentid();

            SysEnterprise sysEnterprise = sysEnterpriseMapper.selectByPrimaryKey(enterpriseid);
            SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentid);

            if (sysEnterprise!=null && sysEnterprise.getDeleteflag()==0){// 首选确保企业没有被逻辑删除
                EmployeeVO employeeVO = new EmployeeVO();
                employeeVO.copyPropertiesFromEmployee(sysEmployee);
                employeeVO.setEnterpriseName(sysEnterprise.getName());
                employeeVO.setDeptName(sysDepartment==null?null:sysDepartment.getName());

                employeeVOS.add(employeeVO);
            }
        }

//        Collections.sort(employeeVOS, new Comparator<EmployeeVO>(){
//
//            @Override
//            public int compare(EmployeeVO o1, EmployeeVO o2) {
//                return o1.getUpdatetime().compareTo(o2.getUpdatetime());
//            }
//        });

        return employeeVOS;
    }

    @Override
    public void updateTimeToEmployeeById(Integer employeeId) {
        SysEmployee sysEmployee = new SysEmployee();
        sysEmployee.setUpdatetime(new Date());
        sysEmployee.setId(employeeId);
        sysEmployeeMapper.updateByPrimaryKeySelective(sysEmployee);
    }
}
