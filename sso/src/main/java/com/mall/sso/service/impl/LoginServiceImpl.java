package com.mall.sso.service.impl;

import com.mall.sso.dto.MallResult;
import com.mall.sso.mapper.UserMapper;
import com.mall.sso.pojo.User;
import com.mall.sso.redis.JedisClient;
import com.mall.sso.service.LoginService;
import com.mall.sso.utils.CookieUtils;
import com.mall.sso.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @description:
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 9:19
 * @modified By:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

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
     * 登录操作
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @Override
    public MallResult login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return MallResult.build(400, "用户名或密码为空，请再次输入！");
        }
        User user = new User();
        user.setUsername(username);
        User userResult = userMapper.selectOne(user);
        if (userResult == null) {
            return MallResult.build(400, "用户不存在!");
        }
        boolean bool = DigestUtils.md5DigestAsHex(password.getBytes()).equals(userResult.getPassword());
        // 如果密码不相等，登录失败
        if (!bool) {
            return MallResult.build(400, "密码错误!");
        }
        // 生成唯一token  使用uuid生成
        String token = UUID.randomUUID().toString();
        // 保证信息安全，将密码设为null
        userResult.setPassword(null);
        // 将用户信息保存到redis中 设置过期时间
        String key = REDIS_SESSION_KEY + ":" + token;
        jedisClient.set(key, JsonUtils.objectToJson(user));
        jedisClient.expire(key, SESSION_EXPIRE);
        // 将token保存到cookie中，返回到客户端  关闭浏览器 cookie失效
        CookieUtils.setCookie(request, response, "USER_SESSION", token);
        MallResult ok = MallResult.build(200, "ok");
        ok.setData(token);
        return ok;
    }

    /**
     * 用户安全退出：删除redis的用户session信息
     *
     * @param token 用户token
     * @return
     */
    @Override
    public MallResult logout(String token) {
        if (StringUtils.isBlank(token)) {
            return MallResult.build(400, "用户token为空！");
        }
        String key = REDIS_SESSION_KEY + ":" + token;
        long del = jedisClient.del(key);
        // 若redis中没有此token。可能刚过期。
        if (del == 0) {
            return MallResult.build(400, "退出失败！");
        }
        return MallResult.build(200, "ok");
    }
}
