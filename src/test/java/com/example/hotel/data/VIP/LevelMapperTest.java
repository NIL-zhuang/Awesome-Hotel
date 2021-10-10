package com.example.hotel.data.VIP;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelMapperTest {
    @Autowired
    private LevelMapper levelMapper;

    @Test
    @Transactional
    public void formulateVIPLevel() {
        levelMapper.formulateVIPLevel(8, "Corporation",20, 0.01);
        Assert.assertThat(levelMapper.getTheRequestOfLevel(8, "Corporation"), is(20));
    }

    @Test
    @Transactional
    public void changeLevel() {
        levelMapper.formulateVIPLevel(8, "Corporation",20, 0.01);
        levelMapper.changeLevel(8, "Corporation",17, 0.01);
        Assert.assertThat(levelMapper.getTheRequestOfLevel(8, "Corporation"), is(17));
    }

    @Test
    @Transactional
    public void getTheRequestOfLevel() {
        // the same to formulateVIPLevel
    }
}