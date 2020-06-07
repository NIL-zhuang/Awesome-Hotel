package com.example.hotel.bl.admin;

import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    private final UserForm form = new UserForm() {{
        setEmail("777@qq.com");
        setPassword("clearlove7");
        setHotelId(1);
        setPhoneNumber("77777777777");
        setUserName("4396");
    }};

    @Test
    @Transactional
    public void addManagerTest() {
        ResponseVO responseVO = adminService.addManager(form);
        assertNull(responseVO.getMessage());
    }

    @Test
    @Transactional
    public void addSalesPersonTest() {

    }

    @Test
    @Transactional
    public void getAllManagersTest() {

    }
}
