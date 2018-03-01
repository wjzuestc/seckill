package com.mall.sso.service;

import com.mall.sso.dto.MallResult;
import com.mall.sso.pojo.User;

/**
 * @description: 参数检查和用户注册service接口
 * @author: Jingzeng Wang
 * @date: created in 2017/10/22 16:35
 * @modified By:
 */
public interface RegisterService {
    /**
     * 检查参数是否可用
     *
     * @param param 参数值
     * @param type 参数类型
     * @return
     */
    Boolean checkParam(String param, Integer type);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    MallResult register(User user);
}
