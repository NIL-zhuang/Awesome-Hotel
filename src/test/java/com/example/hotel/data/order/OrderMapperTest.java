package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    private final Order order = new Order() {{
        setOrderState("未入住");
        setCheckInDate("2020-06-01");
        setCheckOutDate("2020-06-02");
        setClientName("clearlove7");
        setCreateDate("2020-06-01");
        setHotelId(2);
        setHotelName("野区");
        setHaveChild(true);
        setHotelPhoneNum("123456");
        setPeopleNum(7);
        setPrice(777.0);
        setUserId(1);
        setRoomNum(2);
        setId(1);
    }};

    @Test
    public void addOrder() {
        orderMapper.addOrder(order);
    }

    @Test
    public void getAllOrders() {
    }

    @Test
    public void getUserOrders() {
    }

    @Test
    public void annulOrder() {
    }

    @Test
    public void finishOrder() {
    }

    @Test
    public void getOrderById() {
    }

    @Test
    public void updateComment() {
    }

    @Test
    public void getComment() {
    }

    @Test
    public void getAllComment() {
    }

    @Test
    public void getUserComment() {
    }

    @Test
    public void getHotelComment() {
    }

    @Test
    public void annulComment() {
    }
}