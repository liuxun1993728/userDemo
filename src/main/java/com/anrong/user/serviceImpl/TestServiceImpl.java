package com.anrong.user.serviceImpl;

import com.anrong.user.mappers.SysUserMapper;
import com.anrong.user.po.SysUser;
import com.anrong.user.po.SysUserExample;
import com.anrong.user.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAllUsers() {
        SysUserExample example = new SysUserExample();
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        return sysUsers;
    }

}
