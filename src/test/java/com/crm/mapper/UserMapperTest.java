package com.crm.mapper;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.crm.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName UserMapperTest
 * @Description 一句话描述此类或方法的作用
 * @Author Liang Yansheng
 * @Date 2020/12/19 11:55
 * @Version 1.0
 */

public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityMapper activityMapper;

    @Test
    public void testSelectUserById(){
        User user = userMapper.selectByPrimaryKey("06f5fc056eac41558a964f96daa7f27c");

        System.out.println(user.getName());
    }


}
