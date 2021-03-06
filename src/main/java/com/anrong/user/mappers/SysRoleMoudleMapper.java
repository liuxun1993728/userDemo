package com.anrong.user.mappers;

import com.anrong.user.po.SysRoleMoudle;
import com.anrong.user.po.SysRoleMoudleEx;
import com.anrong.user.po.SysRoleMoudleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMoudleMapper {
    List<SysRoleMoudleEx> selectLeftJoinModule(@Param("roleId") Integer arg0);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    long countByExample(SysRoleMoudleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int deleteByExample(SysRoleMoudleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int insert(SysRoleMoudle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int insertSelective(SysRoleMoudle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    List<SysRoleMoudle> selectByExample(SysRoleMoudleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    SysRoleMoudle selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysRoleMoudle record, @Param("example") SysRoleMoudleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int updateByExample(@Param("record") SysRoleMoudle record, @Param("example") SysRoleMoudleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int updateByPrimaryKeySelective(SysRoleMoudle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_moudle
     *
     * @mbg.generated Thu Feb 08 15:24:08 CST 2018
     */
    int updateByPrimaryKey(SysRoleMoudle record);
}