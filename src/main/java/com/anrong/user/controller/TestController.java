package com.anrong.user.controller;

import com.anrong.user.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @apiNote 测试控制器
 *  @author liuxun
 */

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public  Object getUsers(){

        return testService.getAllUsers();
    }
}
