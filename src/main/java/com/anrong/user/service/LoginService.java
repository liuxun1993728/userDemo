package com.anrong.user.service;

import com.anrong.user.po.SysUser;
import com.anrong.user.vo.EmployeeVO;

import java.util.List;

/**
 * @apiNote 实现登录业务类
 * @author liuxun
 */
public interface LoginService {
    public SysUser getUserByAccountAndPassword(String userName,String password,Integer type);

    List<String> getAllPowersByUserId(Integer id);

    List<EmployeeVO> getEnterpriseAndEmployeesByUserId(Integer id);

    void updateTimeToEmployeeById(Integer employeeId);
}
