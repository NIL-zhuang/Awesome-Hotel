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
    private final Credit credit1 = new Credit(1, 8, 10.2, "测试");
    private final String argue = "cnm老子不改了";

    @Test
    @Transactional
    public void AddCredit() {
        int val = creditMapper.addCredit(credit);
        Assert.assertEquals(val, 1);
    }

    @Test
    @Transactional
    public void GetUserCredit() {
        int val = creditMapper.addCredit(credit);
        creditMapper.addCredit(credit1);
        Assert.assertEquals(val, 1);
        List<Credit> creditList = creditMapper.getUserCredit(1);
        Assert.assertEquals(creditList.size(), 2);
        Assert.assertEquals(creditList.get(0).getNow(), 2.2, 0.1);
    }

    @Test
    @Transactional
    public void GetAllCredit() {
        creditMapper.addCredit(credit1);
        creditMapper.addCredit(credit);
        List<Credit> credits = creditMapper.getAllCredit();
        Assert.assertEquals(credits.size(), 2);
    }

    @Test
    @Transactional
    public void getCredit() {
        creditMapper.addCredit(credit);
        List<Credit> credits = creditMapper.getUserCredit(1);
        Credit testCredit = credits.get(0);
        assertEquals(testCredit.getReason(), "test");

    }

    @Test
    @Transactional
    public void argueCredit() {
        creditMapper.addCredit(credit);
        List<Credit> credits = creditMapper.getUserCredit(1);
        Credit testCredit = credits.get(0);
        int id = testCredit.getId();
        creditMapper.argueCredit(id, argue);
        testCredit = creditMapper.getCredit(id);
        assertEquals(testCredit.getArgue(), argue);
        assertEquals(testCredit.getStatus(), 1);

    }

    @Test
    @Transactional
    public void getArgueCredits() {
        creditMapper.addCredit(credit);
        List<Credit> credits = creditMapper.getUserCredit(1);
        Credit testCredit = credits.get(0);
        int id = testCredit.getId();
        creditMapper.argueCredit(id, argue);
        testCredit = creditMapper.getCredit(id);
        assertEquals(testCredit.getStatus(), 1);
        credits = creditMapper.getArgueCredits();
        assertEquals(credits.size(), 1);
    }

    @Test
    @Transactional
    public void handleArgue() {
        creditMapper.addCredit(credit);
        List<Credit> credits = creditMapper.getUserCredit(1);
        Credit testCredit = credits.get(0);
        int id = testCredit.getId();
        creditMapper.argueCredit(id, argue);
        testCredit = creditMapper.getCredit(id);
        assertEquals(testCredit.getStatus(), 1);
        creditMapper.handleArgue(id, -1);
        testCredit = creditMapper.getCredit(id);
        assertEquals(testCredit.getStatus(), -1);
    }
}