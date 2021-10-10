package com.example.hotel.data.user;

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

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    private final User user = new User() {{
        setEmail("theshy@qq.com");
        setPassword("xiafan");
        setUserName("TheShy");
        setPhoneNumber("12312312312");
        setCredit(-100);
        setUserType(UserType.Client);
    }};

    @Test
    @Transactional
    public void createNewAccount() {
        accountMapper.createNewAccount(user);
        Assert.assertThat(
                accountMapper.getAccountByEmail("theshy@qq.com").getUserName(),
                is("TheShy"));
    }

    @Test
    @Transactional
    public void getAccountByEmail() {
        User user = accountMapper.getAccountByEmail("123@qq.com");
        Assert.assertThat(user.getUserName(), is("测试二号"));
    }

    @Test
    @Transactional
    public void getAccountById() {
        User user = accountMapper.getAccountById(4);
        Assert.assertThat(user.getEmail(), is("1012681@qq.com"));
    }

    @Test
    @Transactional
    public void updateAccount() {
        accountMapper.updateAccount(4, "testone", "12312312312", "12312312312");
        Assert.assertThat(accountMapper.getAccountById(4).getUserName(), is("testone"));
    }

    @Test
    @Transactional
    public void updateBirthday() {
        accountMapper.updateBirthday(4, "1989-06-04");
        Assert.assertThat(accountMapper.getAccountById(4).getBirthday(), is("1989-06-04"));
    }

    @Test
    @Transactional
    public void updateCorporate() {
        accountMapper.updateCorporate(4, "Nanjing University");
        Assert.assertThat(accountMapper.getAccountById(4).getCorporation(), is("Nanjing University"));
    }

    @Test
    @Transactional
    public void updatePassword() {
        accountMapper.updatePassword(4, "123456");
    }

    @Test
    @Transactional
    public void minAnnulTime() {
        accountMapper.minAnnulTime(4);
        Assert.assertThat(accountMapper.getAccountById(4).getAnnulTime(), is(2));
    }

    @Test
    @Transactional
    public void updatePortrait() {
        accountMapper.updatePortrait(4, "www.baidu.com");
        Assert.assertThat(accountMapper.getAccountById(4).getPortrait(), is("www.baidu.com"));
    }
}