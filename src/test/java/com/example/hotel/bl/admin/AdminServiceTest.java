package com.example.hotel.bl.admin;

import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.vo.UserForm;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;

import com.example.hotel.bl.admin.AdminService;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/25 23:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminServiceImpl adminService;

    public void testAddManager() {
        Assert.assertThat(1, is(1));
    }

    public void testGetAllManagers() {
    }
}