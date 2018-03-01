package com.mall.sso.service;

import com.mall.sso.dto.MallResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录/登出接口
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 9:17
 * @modified By:
 */
public interface LoginService {

    /**
     * 登录操作
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    public MallResult login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户安全退出：删除redis的用户session信息
     *
     * @param token
     * @return
     */
    public MallResult logout(String token);
}
