package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
        List<User> managers = adminService.getAllManagers();
        int size = managers.size();
        User manager = managers.get(size-1);
        Assert.assertThat(manager.getEmail(), is("777@qq.com"));
    }

    @Test
    @Transactional
    public void addSalesPersonTest() {
        adminService.addSalesPerson(form);
        List<User> salesMan = adminService.getAllSalesPerson();
        int size = salesMan.size();
        User user = salesMan.get(size-1);
        Assert.assertThat(user.getUserName(), is("4396"));
    }

    @Test
    @Transactional
    public void getAllManagersTest() {
        List<User> managers = adminService.getAllManagers();
        Assert.assertThat(managers.size(), is(1));
        Assert.assertThat(managers.get(0).getId(), is(6));
    }

    @Test
    @Transactional
    public void getAllSalesPerson() {
        List<User> salesPerson = adminService.getAllSalesPerson();
        Assert.assertThat(salesPerson.size(), is(1));
        Assert.assertThat(salesPerson.get(0).getId(), is(7));
    }

    @Test
    @Transactional
    public void deleteManagersTest() {
        List<User> managers = adminService.getAllManagers();
        Assert.assertThat(managers.size(), is(1));
        adminService.deleteManager(managers.get(0).getId());
        managers = adminService.getAllManagers();
        Assert.assertThat(managers.size(), is(0));
    }

    @Test
    @Transactional
    public void deleteSalesPerson() {
        List<User> salesPerson = adminService.getAllSalesPerson();
        Assert.assertThat(salesPerson.size(), is(1));
        adminService.deleteManager(salesPerson.get(0).getId());
        salesPerson = adminService.getAllSalesPerson();
        Assert.assertThat(salesPerson.size(), is(0));
    }
}
