package com.anrong.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anrong.user.service.MenuService;
import com.anrong.user.serviceImpl.TokenSerevice;
import com.anrong.user.vo.ActiveUserVO;
import com.anrong.user.vo.MenuBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @apiNote  菜单控制器
 * @author liuxun
 */

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private TokenSerevice tokenSerevice;

    @Autowired
    private MenuService menuService;

    @Cacheable(value = "/getMenus")
    @RequestMapping(value = "/getMenus",method = {RequestMethod.GET})
    public Object getMenusByToken(@RequestHeader String token){
        Map<String,Object> resultMap = new HashMap<>();
        String tokenInfo = tokenSerevice.deCodeToken(token, "user");
        if (tokenInfo == null){
            resultMap.put("errorMsg","Token无效");
            resultMap.put("errorCode","Error001");
            resultMap.put("success",false);
            return resultMap;
        }
        ActiveUserVO activeUserVO =  JSON.parseObject(tokenInfo, new TypeReference<ActiveUserVO>() {});
        Integer employeeId = activeUserVO.getEmployeeId();
        if(employeeId == null){
            resultMap.put("errorMsg","Token错误");
            resultMap.put("errorCode","Error001");
            resultMap.put("success",false);
            return resultMap;
        }
        // 根据员工ID 查询菜单数据
        MenuBody menuBody = menuService.getMenuBodyByEmployeeId(employeeId);
        if (menuBody == null){
            resultMap.put("errorMsg","获取菜单失败，请检查后重试");
            resultMap.put("errorCode","Error001");
            resultMap.put("success",false);
            return resultMap;
        }

        ArrayList<MenuBody> menuBodies = new ArrayList<>();
        menuBodies.add(menuBody);
        resultMap.put("menu",menuBodies);
        resultMap.put("success",true);

        return resultMap;
    }

//    @RequestMapping(value = "/testMenus",method = {RequestMethod.GET})
//    public Object getMenusByToken(){
//        return menuService.getMenuBodyByEmployeeId(2);
//    }


}
