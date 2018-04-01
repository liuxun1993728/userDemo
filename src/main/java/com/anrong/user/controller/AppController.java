package com.anrong.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anrong.user.mappers.SysAppMapper;
import com.anrong.user.po.SysApp;
import com.anrong.user.po.SysEnterprise;
import com.anrong.user.po.SysModule;
import com.anrong.user.po.SysUser;
import com.anrong.user.service.AppService;
import com.anrong.user.serviceImpl.TokenSerevice;
import com.anrong.user.vo.ActiveUserVO;
import com.anrong.user.vo.EmployeeVO;
import com.anrong.user.vo.SysAppVO;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxun
 * @apiNote app处理
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private TokenSerevice tokenSerevice;

    @Autowired
    SysAppMapper sysAppMapper;

    @GetMapping("unBindApp")
    public Object unBindApp(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);

        SysApp sysApp = sysAppMapper.selectByPrimaryKey(id);
        if(sysApp != null) {
            sysApp.setModuleid(null);
            if(sysAppMapper.updateByPrimaryKey(sysApp) > 0)
                map.put("success", true);
        }

        return map;
    }

    @Cacheable(cacheNames = "allEnterprises")
    @RequestMapping(value = "/getAllEnterprises", method = {RequestMethod.GET})
    public Object getEnterprises(@RequestHeader String token) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "无效Token");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
//        ActiveUserVO activeUserVO = JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {});
//        SysUser sysUser = activeUserVO.getSysUser();
//        if (sysUser == null){
//            resultMap.put("errorMsg", "Token信息缺失");
//            resultMap.put("errorCode", "Error001");
//            resultMap.put("success", false);
//            return resultMap;
//        }

        // 根据UserID 查询所有的Enterprises
        List<SysEnterprise> enterprises = appService.getAllEnterprises();
        resultMap.put("enterprises", enterprises);
        resultMap.put("success", true);
        return resultMap;
    }

    @Cacheable(cacheNames = "modulesOfEnterprise")
    @RequestMapping(value = "/getModulesOfEnterprise", method = {RequestMethod.GET})
    public Object getModulesOfEnterprise(@RequestHeader String token, Integer enterpriseId) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "Token无法解析");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (enterpriseId == null) {
            resultMap.put("errorMsg", "参数非法");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        List<SysModule> sysModules = appService.getModulesOfEnterpriseByEnterpriseId(enterpriseId);
        resultMap.put("sysModules", sysModules);
        resultMap.put("success", true);
        return null;
    }


    @CacheEvict(cacheNames = "appsOfPage")
    @RequestMapping(value = "/addApp", method = {RequestMethod.POST})
    public Object addApp(@RequestHeader String token, @RequestBody SysApp sysApp) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "无效的Token");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        ActiveUserVO activeUserVO = JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {
        });
        EmployeeVO employeeVO = activeUserVO.getEmployeeVO();
        if (employeeVO == null || employeeVO.getId() == null || employeeVO.getEnterpriseid() == null) {
            resultMap.put("errorMsg", "Token信息错误");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysApp == null) {
            resultMap.put("errorMsg", "APP数据不能为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
//        if (sysApp.getModuleid() == null) {
//            resultMap.put("errorMsg", "APP关联的ModuleID为空");
//            resultMap.put("errorCode", "Error001");
//            resultMap.put("success", false);
//            return resultMap;
//        }

        if (sysApp.getEnterpriseid() == null) {
            resultMap.put("errorMsg", "enterpriseId不可为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }


        if (sysApp.getParentid() == null) {
            sysApp.setParentid(0);
        }

        if (sysApp.getParentid() != null && sysApp.getParentid() != 0) {
            // 验证父ID是否合法
            Boolean isValidate = appService.isValidateParentId(sysApp.getEnterpriseid(), sysApp.getParentid());
            if (!isValidate) {
                resultMap.put("errorMsg", "parentid不合法");
                resultMap.put("errorCode", "Error001");
                resultMap.put("success", false);
                return resultMap;
            }
        }

        // 判断enterpriseID是否合法
        SysEnterprise sysEnterprise = appService.getEnterpriseByEnterpriseId(sysApp.getEnterpriseid());
        if (sysEnterprise == null) {
            resultMap.put("errorMsg", "enterpriseId不存在");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        // 判断ModuleID是否合法  因为更改需求  放在Module端进行绑定APP
//        Boolean isModuleValidated = appService.getModuleByTwoId(sysApp.getModuleid(),sysApp.getModuleid());
//        if (!isModuleValidated){
//            resultMap.put("errorMsg", "ModuleId不合法");
//            resultMap.put("errorCode", "Error001");
//            resultMap.put("success", false);
//            return resultMap;
//        }


        sysApp.setCreater(employeeVO.getId());
        // 添加保存新增的模块App
        Boolean isSuccess = appService.addAppByAppInfo(sysApp);
        if (!isSuccess) {
            resultMap.put("errorMsg", "保存APP失败，请联系管理员");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("app", sysApp);
        return resultMap;
    }

    @CacheEvict(cacheNames = "appsOfPage")
    @RequestMapping(value = "/updateApp", method = {RequestMethod.POST})
    public Object updateApp(@RequestHeader String token, @RequestBody SysApp sysApp) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "token无法解析");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        ActiveUserVO activeUserVO = JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {
        });
        EmployeeVO employeeVO = activeUserVO.getEmployeeVO();

        if (sysApp == null || sysApp.getId() == null) {
            resultMap.put("errorMsg", "app数据及其主键不能为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }


        Boolean isSuccess = appService.saveApp(sysApp);
        if (!isSuccess) {
            resultMap.put("errorMsg", "服务器异常,轻稍后重试");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("app", sysApp);
        return resultMap;
    }

    @CacheEvict(cacheNames = "appsOfPage")
    @RequestMapping(value = "/deleteApp", method = {RequestMethod.POST})
    public Object deleteApp(@RequestHeader String token, @RequestBody SysApp sysApp) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "token出现异常");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysApp == null || sysApp.getId() == null) {
            resultMap.put("errorMsg", "app参数非法");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        // 首先查询是否有app关联此App，如果有关联则不可删除
        Boolean isAppRelativedToApp = appService.isAppRelativedToApp(sysApp);
        if (isAppRelativedToApp) {
            resultMap.put("errorMsg", "有app关联此App 不可删除");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        Boolean isSuccess = appService.deleteApp(sysApp);
        if (!isSuccess) {
            resultMap.put("errorMsg", "服务器出现异常");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("app", sysApp);
        return resultMap;
    }


    @Cacheable(cacheNames = "appsOfPage")
    @RequestMapping(value = "/selectApp", method = {RequestMethod.GET})
    public Object selectAppByPage(@RequestHeader String token, String sysAppVOStr, Integer page, Integer results) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "toke解析异常");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysAppVOStr == null){
            resultMap.put("errorMsg", "查询条件不可为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

//        String s = sysAppVOStr.replaceAll("\\\\", "");
//        String queryStr = s.substring(1,s.length()-1);
        SysAppVO sysAppVO = JSON.parseObject(sysAppVOStr, new TypeReference<SysAppVO>() {});
        if (sysAppVO == null){
            resultMap.put("errorMsg", "jsonStr有误");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }


        // 如果缺乏缺参数 则给出默认值  默认为我第一页 每页10条
        page = (page == null || page <= 0) ? 1 : page;
        results = (results == null || results <= 0) ? 10 : results;


        // 根据页码 条数 以及员工所在公司的ID进行查询
        List<SysAppVO> sysAppVOList = appService.getAppsofPageByCriteria(sysAppVO, page, results);
        if (sysAppVOList == null) {
            resultMap.put("errorMsg", "获取app信息异常，请稍后重试");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("results", sysAppVOList);
        resultMap.put("success", true);
        return resultMap;
    }


}
