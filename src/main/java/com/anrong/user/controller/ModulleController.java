package com.anrong.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anrong.user.po.SysApp;
import com.anrong.user.po.SysModule;
import com.anrong.user.service.AppService;
import com.anrong.user.service.ModuleService;
import com.anrong.user.serviceImpl.TokenSerevice;
import com.anrong.user.vo.ActiveUserVO;
import com.anrong.user.vo.SysModuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxun
 */

@RestController
@RequestMapping("/module")
public class ModulleController {
    @Autowired
    ModuleService moduleService;

    @Autowired
    TokenSerevice tokenSerevice;

    @CacheEvict(cacheNames = "modulesOfPage")
    @RequestMapping(value = "/addModule", method = {RequestMethod.POST})
    public Object addModule(@RequestHeader String token, @RequestBody SysModule sysModule) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "无效的");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        ActiveUserVO activeUserVO = JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {
        });
        Integer employeeId = activeUserVO.getEmployeeId();
        if (employeeId == null) {
            resultMap.put("errorMsg", "Token信息错误");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysModule == null) {
            resultMap.put("errorMsg", "数据不能为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysModule.getEnterpriseid() == null) {
            resultMap.put("errorMsg", "enterpriseId不能为空");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        // 判断添加的此模块在当前公司是否重复
        Boolean isNameValidated = moduleService.isModuleNameValidated(sysModule);
        if (!isNameValidated){
            resultMap.put("errorMsg", "模块名称已经存在");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        // 添加保存新增的模块Module
        sysModule.setCreater(employeeId);
        Boolean isSuccess = moduleService.addModuleByModuleInfo(sysModule);
        if (!isSuccess) {
            resultMap.put("errorMsg", "保存失败，请联系管理员");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("module", sysModule);
        return resultMap;
    }

    @CacheEvict(cacheNames = "modulesOfPage")
    @RequestMapping(value = "/updateModule", method = {RequestMethod.POST})
    public Object updateModule(@RequestHeader String token, @RequestBody SysModule sysModule) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "无效的token");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        ActiveUserVO activeUserVO = JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {
        });
        Integer employeeId = activeUserVO.getEmployeeId();
        if (employeeId == null) {
            resultMap.put("errorMsg", "token信息错误");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysModule == null || sysModule.getId() == null) {
            resultMap.put("errorMsg", "数据及其主键不能为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        // sysModule.setCreater(employeeId);
        Boolean isSuccess = moduleService.saveModule(sysModule);
        if (!isSuccess) {
            resultMap.put("errorMsg", "服务器繁忙,轻稍后重试");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("module", sysModule);
        return resultMap;
    }

    @CacheEvict(cacheNames = "modulesOfPage")
    @RequestMapping(value = "/deleteModule", method = {RequestMethod.POST})
    public Object deleteModule(@RequestHeader String token, @RequestBody SysModule sysModule) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "token异常");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysModule == null || sysModule.getId() == null) {
            resultMap.put("errorMsg", "参数非法");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        // 首先查询是否有app关联此Module，如果有关联则不可删除
        Boolean isAppRelativedToModule = moduleService.isAppRelativedToModule(sysModule);
        if (isAppRelativedToModule) {
            resultMap.put("errorMsg", "有app关联此Module 不可删除");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        Boolean isSuccess = moduleService.deleteModule(sysModule);
        if (!isSuccess) {
            resultMap.put("errorMsg", "服务器繁忙");
            resultMap.put("errorCode", "Error003");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("module", sysModule);
        return resultMap;
    }

    @Cacheable(cacheNames = "modulesOfPage")
    @RequestMapping(value = "/selectModule", method = {RequestMethod.GET})
    public Object selectModuleByPage(@RequestHeader String token,String sysModuleVOStr, Integer page, Integer results) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "toke无法解析");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        ActiveUserVO activeUserVO = JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {
        });
        Integer employeeId = activeUserVO.getEmployeeId();
        if (employeeId == null) {
            resultMap.put("errorMsg", "Token信息不完整");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        if (sysModuleVOStr == null){
            resultMap.put("errorMsg", "缺少JSON Str");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

//        String s = sysModuleVOStr.replaceAll("\\\\", "");
//        String queryStr = s.substring(1,s.length()-1);
        SysModuleVO sysModuleVO = JSON.parseObject(sysModuleVOStr, new TypeReference<SysModuleVO>() {});
        if (sysModuleVO == null){
            resultMap.put("errorMsg", "jsonStr解析错误");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        // 如果缺乏缺参数 则给出默认值  默认为我第一页 每页10条
        page = (page == null || page <= 0) ? 1 : page;
        results = (results == null || results <= 0) ? 10 : results;

        // 查询员工公司的ID
        // Integer enterpriseId = moduleService.getEnterpriseIdByEmployeeId(employeeId);

        // 根据页码 条数 以及员工所在公司的ID进行查询
        List<SysModuleVO> sysModuleList = moduleService.getModuleVOsofPageByCriteria(sysModuleVO, page, results);
        if (sysModuleList == null) {
            resultMap.put("errorMsg", "获取列表异常，请稍后重试");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        resultMap.put("results", sysModuleList);
        resultMap.put("success", true);
        return resultMap;
    }

    @RequestMapping(value = "/appsOfModule", method = {RequestMethod.GET})
    public Object appsOfModule(@RequestHeader String token, Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null) {
            resultMap.put("errorMsg", "toke解析失败");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }
        if (id == null){
            resultMap.put("errorMsg", "moduleId不能为空");
            resultMap.put("errorCode", "Error001");
            resultMap.put("success", false);
            return resultMap;
        }

        List<SysApp> sysApps = moduleService.getAppsOfModule(id);
        resultMap.put("sysApps",sysApps);
        resultMap.put("success", true);
        return resultMap;

    }

}
