package com.anrong.user.mappers;

import com.anrong.user.po.SysRoleApp;
import com.anrong.user.po.SysRoleAppEx;
import com.anrong.user.po.SysRoleAppExample;
import java.util.List;

import com.anrong.user.po.SysRoleMoudleEx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuxun
 */
@Mapper
public interface SysRoleAppMapper {
    List<SysRoleAppEx> selectLeftJoinApp(@Param("roleId") Integer arg0);

    int countByExample(SysRoleAppExample example);

    int deleteByExample(SysRoleAppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleApp record);

    int insertSelective(SysRoleApp record);

    List<SysRoleApp> selectByExample(SysRoleAppExample example);

    SysRoleApp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysRoleApp record, @Param("example") SysRoleAppExample example);

    int updateByExample(@Param("record") SysRoleApp record, @Param("example") SysRoleAppExample example);

    int updateByPrimaryKeySelective(SysRoleApp record);

    int updateByPrimaryKey(SysRoleApp record);
}