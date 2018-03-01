package com.mall.sso.service.impl;

import com.mall.sso.dto.MallResult;
import com.mall.sso.redis.JedisClient;
import com.mall.sso.service.UserQueryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 18:12
 * @modified By:
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private JedisClient jedisClient;

    /**
     * 用户Session的前缀key，为了操作方便统一格式
     */
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;

    /**
     * session过期时间  30分钟
     */
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    /**
     * 用户信息查询接口
     * 查询用户是否已登录
     *
     * @param token 用户登录凭证
     * @return
     */
    @Override
    public MallResult userQuery(String token) {
        if (StringUtils.isBlank(token)) {
            return MallResult.build(400, "token为空！");
        }
        String key = REDIS_SESSION_KEY + ":" + token;
        String userJson = jedisClient.get(key);
        if (StringUtils.isBlank(userJson)) {
            return MallResult.build(400, "未登录或登录已过期！");
        }
        // 刷新过期时间
        jedisClient.expire(key, SESSION_EXPIRE);
        MallResult ok = MallResult.build(200, "ok");
        ok.setData(userJson);
        return ok;
    }
}
