package com.example.hotel.bl.user;

import com.example.hotel.enums.UserType;
import com.example.hotel.enums.VIPType;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    @Transactional
    public void registerAccount() {
        UserVO userVO = new UserVO(){{
            setEmail("12345@qq.com");
            setPassword("123456");
            setUserName("测测");
            setPhoneNumber("1234567");
            setCredit(100.0);
            setUserType(UserType.Client);
        }};
        accountService.registerAccount(userVO);
        UserVO userVO1 = accountService.getUserInfoByEmail("12345@qq.com");
        Assert.assertThat(userVO1.getUserName(), is("测测"));
    }

    @Test
    @Transactional
    public void login() {
        UserForm userForm = new UserForm(){{
           setEmail("555@qq.com");
           setPassword("123456");
        }};
        UserVO userVO = accountService.login(userForm);
        Assert.assertThat(userVO.getUserName(), is("管理人员1"));
    }

    @Test
    @Transactional
    public void getUserInfo() {
        UserVO userVO = accountService.getUserInfo(6);
        Assert.assertThat(userVO.getEmail(), is("333@qq.com"));
    }

    @Test
    @Transactional
    public void getUserInfoByEmail() {
        UserVO userVO = accountService.getUserInfoByEmail("333@qq.com");
        Assert.assertThat(userVO.getId(), is(6));
    }

    @Test
    @Transactional
    public void updateUserInfo() {
        accountService.updateUserInfo(6,"测试","12345678910","123qwe");
        UserVO userVO = accountService.getUserInfo(6);
        Assert.assertThat(userVO.getUserName(), is("测试"));
    }

    @Test
    @Transactional
    public void corporateVIP() {
        accountService.updateCorporation(6,"test");
        UserVO userVO = accountService.getUserInfo(6);
        Assert.assertThat(userVO.getVipType(), is(VIPType.Corporation));
    }

    @Test
    @Transactional
    public void updateBirthday() {
        accountService.updateBirthday(6,"2000-01-01");
        UserVO userVO = accountService.getUserInfo(6);
        Assert.assertThat(userVO.getBirthday(), is("2000-01-01"));
    }

    @Test
    @Transactional
    public void registerAsVIP() {
        accountService.registerAsVIP(6);
        UserVO userVO = accountService.getUserInfo(6);
        Assert.assertThat(userVO.getVipType(), is(VIPType.Client));
    }

    @Test
    @Transactional
    public void updatePortrait() {
        accountService.updatePortrait(6,"www.xxhub.com");
        UserVO userVO = accountService.getUserInfo(6);
        Assert.assertThat(userVO.getPortrait(), is("www.xxhub.com"));
    }

    @Test
    @Transactional
    public void getAllPhoneNumOfSalesPerson() {
        accountService.updateUserInfo(7,"营销人员1","13345626722","南京大学");
        List<String> phoneNums = accountService.getAllPhoneNumOfSalesPerson();
        Assert.assertThat(phoneNums.size(), is(1));
        Assert.assertThat(phoneNums.get(0), is("13345626722"));
    }
}