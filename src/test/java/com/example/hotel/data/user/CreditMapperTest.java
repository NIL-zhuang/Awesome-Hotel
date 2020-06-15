package com.example.hotel.data.user;

import com.example.hotel.po.Credit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditMapperTest {

    @Autowired
    private CreditMapper creditMapper;

    private final Credit credit = new Credit(1, 5, 2.2, "test");

    @Test
    public void testAddCredit() {
    }

    @Test
    @Transactional
    public void testGetUserCredit() {
        int val = creditMapper.addCredit(credit);
        Assert.assertEquals(val, 1);
        List<Credit> creditList = creditMapper.getUserCredit(1);
        Assert.assertEquals(creditList.size(), 1);
        Assert.assertEquals(creditList.get(0).getNow(), 2.2, 0.1);
    }

    @Test
    public void testGetAllCredit() {
    }
}