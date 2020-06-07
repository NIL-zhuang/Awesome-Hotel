package com.example.hotel.bl.user;

import com.example.hotel.data.user.AccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

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
    }

    @Test
    @Transactional
    public void login() {
    }

    @Test
    @Transactional
    public void getUserInfo() {
    }

    @Test
    @Transactional
    public void getUserInfoByEmail() {
    }

    @Test
    @Transactional
    public void updateUserInfo() {
    }

    @Test
    @Transactional
    public void updateCredit() {
    }

    @Test
    @Transactional
    public void personalVIP() {
    }

    @Test
    @Transactional
    public void corporateVIP() {
    }

    @Test
    @Transactional
    public void normalUser() {
    }

    @Test
    @Transactional
    public void updateBirthday() {
    }

    @Test
    @Transactional
    public void registerAsVIP() {
    }

    @Test
    @Transactional
    public void updatePortrait() {
    }

    @Test
    @Transactional
    public void getManagerTelephone() {
    }
}