package com.mall.sso.controller;

import com.mall.sso.dto.MallResult;
import com.mall.sso.service.UserQueryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 用户查询controller
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 18:11
 * @modified By:
 */
@Controller
public class UserQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserQueryController.class);

    @Autowired
    private UserQueryService userQueryService;

    /**
     * 用户是否登录查询服务
     *
     * @param token    登录凭证
     * @param callback 支持jsonp调用
     * @return MallResult-json 或 jsonp调用结果
     */
    @RequestMapping("/user/{token}")
    @ResponseBody
    public Object userQuery(@PathVariable String token, String callback) {
        try {
            MallResult result = userQueryService.userQuery(token);
            if (StringUtils.isNotBlank(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("查询出现异常" + ExceptionUtils.getMessage(e));
            return MallResult.build(500, "查询异常！");
        }
    }
}
