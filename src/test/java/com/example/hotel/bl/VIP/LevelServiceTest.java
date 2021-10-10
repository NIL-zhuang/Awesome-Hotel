package com.example.hotel.bl.VIP;

import com.example.hotel.vo.LevelVO;
import com.example.hotel.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/9 9:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelServiceTest {

    @Autowired
    private LevelService levelService;

    @Test
    @Transactional
    public void formulateVIPLevel() {
        levelService.formulateVIPLevel(8, "Client", 200, 0.01);
        ResponseVO responseVO = levelService.getTheRequestOfLevel(8, "Client");
        Assert.assertEquals(((LevelVO) responseVO.getContent()).getRequest(), 200);
    }

    @Test
    @Transactional
    public void getTheRequestOfLevel() {
        // same as formulate VIP level
    }
}