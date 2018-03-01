package com.mall.sso.service.impl;

import com.mall.sso.dto.MallResult;
import com.mall.sso.mapper.UserMapper;
import com.mall.sso.pojo.User;
import com.mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * @description: 参数检查和用户注册实现类
 * @author: Jingzeng Wang
 * @date: created in 2017/10/22 16:43
 * @modified By:
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检查参数是否可用 唯一性
     *
     * @param param 参数值
     * @param type  参数类型：1 username 2 phone 3 email
     * @return true:可用 false:不可用
     */
    @Override
    public Boolean checkParam(String param, Integer type) {
        // 判断参数是否合法
        if (StringUtils.isBlank(param) || type < 1 || type > 3) {
            return null;
        }
        User record = new User();
        // 判断type类型
        switch (type) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                break;
        }
        // 查询数据库数据是否可用
        User user = userMapper.selectOne(record);
        if (user == null) {
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     * 因为只涉及一条sql，是不需要手动事务保证的
     * mysql默认增删改是一个事务
     *
     * @param user 用户信息
     * @return 数据传输对象
     */
    @Override
    public MallResult register(User user) {
        // TODO {参数检查}
        // md5对密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        // 设置时间
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        userMapper.insert(user);
        return MallResult.build(200, "注册成功！");
    }
}
