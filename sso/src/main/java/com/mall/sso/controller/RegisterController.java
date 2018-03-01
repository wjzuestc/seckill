package com.mall.sso.controller;

import com.mall.sso.dto.MallResult;
import com.mall.sso.pojo.User;
import com.mall.sso.service.RegisterService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 参数检查及用户注册controller类
 * @author: Jingzeng Wang
 * @date: created in 2017/10/22 16:32
 * @modified By:
 */
@Controller
@RequestMapping("/user")
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    /**
     * 检查参数是否可用
     *
     * @param param    参数值
     * @param type     参数类型 1 username 2 phone 3 email
     * @param callback 支持jsonp调用
     * @return http状态码+是否可用
     */
    @RequestMapping("/check/{param}/{type}")
    public ResponseEntity<Boolean> checkParam(@PathVariable String param,
                                              @PathVariable Integer type, String callback) {
        try {
            Boolean checkResult = registerService.checkParam(param, type);
            // 判断返回值null，说明请求参数不合法
            if (checkResult == null) {
                LOGGER.error("请求参数不合法：" + "参数值：" + param + "参数类型：" + type);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(checkResult);
        } catch (Exception e) {
            LOGGER.error("参数检查出错：" + ExceptionUtils.getMessage(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 用户注册  前端以post方式提交表单数据
     *
     * @param user 表单提交的参数
     * @return MallResult json格式
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public MallResult register(User user) {
        try {
            return registerService.register(user);
        } catch (Exception e) {
            LOGGER.error("注册用户出错" + ExceptionUtils.getMessage(e));
            return MallResult.build(400, "用户注册出错，请检查参数！");
        }
    }
}
