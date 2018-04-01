package com.anrong.user.mappers;

import com.anrong.user.po.SysApp;
import com.anrong.user.po.SysAppExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysAppMapper {
    int countByExample(SysAppExample example);

    int deleteByExample(SysAppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysApp record);

    int insertSelective(SysApp record);

    List<SysApp> selectByExample(SysAppExample example);

    SysApp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysApp record, @Param("example") SysAppExample example);

    int updateByExample(@Param("record") SysApp record, @Param("example") SysAppExample example);

    int updateByPrimaryKeySelective(SysApp record);

    int updateByPrimaryKey(SysApp record);
}