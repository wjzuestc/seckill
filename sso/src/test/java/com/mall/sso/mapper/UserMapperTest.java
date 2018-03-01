package com.mall.sso.mapper;

import com.mall.sso.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by wjz on 2017/10/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml"})
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void mapperTest() {
/*        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123");
        user.setEmail("12");
        user.setPhone("156156");
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        userMapper.insert(user);*/
        User user = userMapper.selectByPrimaryKey(100);
        System.out.println(user);
    }
}
