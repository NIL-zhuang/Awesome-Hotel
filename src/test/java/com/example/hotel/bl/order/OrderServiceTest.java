package com.example.hotel.bl.order;

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
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    public void addOrder() {
    }

    @Test
    @Transactional
    public void getAllOrders() {
    }

    @Test
    @Transactional
    public void getHotelOrders() {
    }

    @Test
    @Transactional
    public void getUserOrders() {
    }

    @Test
    @Transactional
    public void annulOrder() {
    }

    @Test
    @Transactional
    public void finishOrder() {
    }

    @Test
    @Transactional
    public void getComment() {
    }

    @Test
    @Transactional
    public void annulComment() {
    }

    @Test
    @Transactional
    public void getHotelComment() {
    }

    @Test
    @Transactional
    public void addComment() {
    }

    @Test
    @Transactional
    public void getUserComments() {
    }
}