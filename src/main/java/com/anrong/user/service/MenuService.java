package com.anrong.user.service;

import com.anrong.user.vo.MenuBody;

/**
 * @apiNote 菜单业务类接口
 * @author liuxun
 */
public interface MenuService {

    MenuBody getMenuBodyByEmployeeId(Integer employeeId);
}
