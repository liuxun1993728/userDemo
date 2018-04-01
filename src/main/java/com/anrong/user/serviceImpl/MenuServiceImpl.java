package com.anrong.user.serviceImpl;

import com.anrong.user.mappers.*;
//import com.anrong.user.mappers.SysRoleEmployeeMapper;
import com.anrong.user.po.*;
import com.anrong.user.service.MenuService;
import com.anrong.user.vo.MenuApp;
import com.anrong.user.vo.MenuBody;
import com.anrong.user.vo.MenuModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysAppMapper sysAppMapper;

//    @Autowired
//    private SysRoleEmployeeMapper sysRoleEmployeeMapper;

    @Autowired
    private SysRoleAppMapper sysRoleAppMapper;

    @Autowired
    SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    SysRoleMoudleMapper sysRoleMoudleMapper;

    @Override
    public MenuBody getMenuBodyByEmployeeId(Integer employeeId) {
        System.out.println("employeeId: " + employeeId);
        MenuBody menuBody = new MenuBody();
        menuBody.setBodyName("主导航");

        SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(employeeId);
        if(sysEmployee != null) {
            List<MenuModule> modules = new ArrayList<>();

            List<SysRoleMoudleEx> listMoudleEx = sysRoleMoudleMapper.selectLeftJoinModule(sysEmployee.getRoleid());
            for (SysRoleMoudleEx sysRoleMoudleEx : listMoudleEx
                 ) {
                // 菜单第一级
                MenuModule menuModule = new MenuModule();
                menuModule.setModuleName(sysRoleMoudleEx.getMoudleName());
                menuModule.setIcon(sysRoleMoudleEx.getMoudleIcon());

                // 第二级
                List<MenuApp> apps = new ArrayList<MenuApp>();

                List<SysRoleAppEx> listRoleAppEx = sysRoleAppMapper.selectLeftJoinApp(sysEmployee.getRoleid());
                for (SysRoleAppEx sysRoleAppEx: listRoleAppEx
                     ) {
                    MenuApp menuApp = new MenuApp();
                    menuApp.setAppName(sysRoleAppEx.getAppName());
                    menuApp.setIcon(sysRoleAppEx.getAppIcon());
                    menuApp.setLink(sysRoleAppEx.getAppUrl());

                    if(sysRoleMoudleEx.getMoudleid().equals(sysRoleAppEx.getAppMoudleId()))
                        apps.add(menuApp);
                }

                menuModule.setApps(apps);
                modules.add(menuModule);
            }

            menuBody.setModules(modules);

        }

        // 第一步：根据员工的ID查询所有的菜单MenuModules
//        Map<Integer,SysModule> moduleMap = new HashMap<>();

//        Map<Integer,SysApp> appMap = new HashMap<>();

//        SysRoleEmployeeExample example1 = new SysRoleEmployeeExample();
//        example1.createCriteria().andEmployeeidEqualTo(employeeId);
        /*
        List<SysRoleEmployee> sysRoleEmployees = sysRoleEmployeeMapper.selectByExample(example1);
        for (SysRoleEmployee sysRoleEmployee:sysRoleEmployees){
            Integer roleid = sysRoleEmployee.getRoleid();
            SysRoleAppExample example2 = new SysRoleAppExample();
            example2.createCriteria().andRoleidEqualTo(roleid);
            List<SysRoleApp> sysRoleApps = sysRoleAppMapper.selectByExample(example2);
            for(SysRoleApp sysRoleApp:sysRoleApps){
                Integer appid = sysRoleApp.getAppid();
                SysApp sysApp = sysAppMapper.selectByPrimaryKey(appid);
                SysModule sysModule = sysModuleMapper.selectByPrimaryKey(sysApp.getModuleid());
                moduleMap.put(sysModule.getId(),sysModule);
                appMap.put(sysApp.getId(),sysApp);
            }
        }


        for (SysModule m:moduleMap.values()){
            MenuModule menuModule = new MenuModule();
            menuModule.setId(m.getId());
            menuModule.setModuleName(m.getName());
            menuModule.setIcon(m.getIcon());

            // 根据ModuleID查询 一级菜单
            SysAppExample example3 = new SysAppExample();
            example3.createCriteria().andModuleidEqualTo(menuModule.getId()).andParentidEqualTo(0);
            List<SysApp> sysApps = sysAppMapper.selectByExample(example3);


            if (sysApps.size()>0){
                List<MenuApp> menuApps = new ArrayList<>();
                for (SysApp sApp:sysApps){
                    // 对app进行筛选 筛选出属于员工的
                    if (appMap.containsKey(sApp.getId())){
                        MenuApp menuApp = new MenuApp();
                        menuApp.setId(sApp.getId());
                        menuApp.setAppName(sApp.getName());
                        menuApp.setIcon(sApp.getIcon());
                        menuApp.setLink(sApp.getUrl());
                        // 为app设置子app

                        menuApps.add(menuApp);
                    }
                }
                menuModule.setApps(menuApps);
            }
            modules.add(menuModule);
        }
        menuBody.setModules(modules);
        */


        return menuBody;
    }

    private void setChildRenAppForApp(MenuApp menuApp){

    }
}
