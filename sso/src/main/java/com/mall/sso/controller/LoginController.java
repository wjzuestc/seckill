package com.mall.sso.controller;

import com.mall.sso.dto.MallResult;
import com.mall.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 用户登录/登出逻辑
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 9:16
 * @modified By:
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录 controller
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 支持jsonp调用
     * @return MallResult-json  或 jsonp调用结果
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username, String password, String callback,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            MallResult result = loginService.login(username, password, request, response);
            //支持jsonp调用
            if (StringUtils.isNotBlank(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("登录异常！" + ExceptionUtils.getMessage(e));
            return MallResult.build(500, ExceptionUtils.getMessage(e));
        }
    }

    /**
     * 用户登出
     *
     * @param token    用户存在cookie中的token信息
     * @param callback 支持jsonp调用
     * @return MallResult-json  或  jsonp结果
     */
    @RequestMapping("/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback) {
        try {
            MallResult result = loginService.logout(token);
            if (StringUtils.isNotBlank(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("用户登出异常！" + ExceptionUtils.getMessage(e));
            return MallResult.build(500, "用户登出异常！");
        }
    }

}
