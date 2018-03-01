package com.mall.sso.service;

import com.mall.sso.dto.MallResult;

/**
 * @description:
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 18:12
 * @modified By:
 */
public interface UserQueryService {

    /**
     * 用户信息查询接口
     * 查询用户是否已登录
     *
     * @param token 用户登录凭证
     * @return
     */
    MallResult userQuery(String token);
}
