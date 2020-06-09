package com.example.hotel.data.admin;

import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

    private final User manager = new User() {{
        setUserName("clearlove7");
        setPassword("4396");
        setHotelID(7);
        setEmail("clearlove7@qq.com");
        setUserType(UserType.HotelManager);
    }};

    private final User salesPerson = new User() {{
        setUserName("UZI");
        setUserType(UserType.SalesPerson);
        setEmail("uzi@qq.com");
        setPassword("306306");
    }};

    @Test
    @Transactional
    public void addManager() {
        int val = adminMapper.addManager(manager);
        Assert.assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void addSalesPerson() {
        int val = adminMapper.addSalesPerson(salesPerson);
        Assert.assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void getAllManagers() {
        List<User> managers = adminMapper.getAllManagers();
        Assert.assertThat(managers.size(), is(1));
        Assert.assertThat(managers.get(0).getEmail(), is("333@qq.com"));
    }

    @Test
    @Transactional
    public void getHotelManagers() {
        List<User> hotelManagers = adminMapper.getHotelManagers(1);
        Assert.assertThat(hotelManagers.size(), is(1));
        Assert.assertThat(hotelManagers.get(0).getEmail(), is("333@qq.com"));
    }

    @Test
    @Transactional
    public void getAllSalesPerson() {
        List<User> sales = adminMapper.getAllSalesPerson();
        Assert.assertThat(sales.size(), is(1));
        Assert.assertThat(sales.get(0).getEmail(), is("444@qq.com"));
    }

    @Test
    @Transactional
    public void deleteManager() {
        int val = adminMapper.deleteManager(6);
        Assert.assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void deleteSalesPerson() {
        int val = adminMapper.deleteSalesPerson(7);
        Assert.assertThat(val, is(1));
    }
}