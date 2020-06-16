package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
        List<Order> orders = orderService.getAllOrders();
        int num = orders.size();
        Assert.assertThat(num,is(1));
    }

    @Test
    @Transactional
    public void getHotelOrders() {
        List<Order> orders = orderService.getHotelOrders(2);
        int num = orders.size();
        Assert.assertThat(num,is(0));
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

    @Test
    @Transactional
    public void getOrdersInMonthOfHotel() {
        ResponseVO responseVO = orderService.getOrdersInMonthOfHotel(1);
        List<Order> orders = (List<Order>) responseVO.getContent();
        int num = orders.size();
        Assert.assertThat(num,is(1));
    }

    @Test
    @Transactional
    public void getOrdersInMonthOfAll() {
        ResponseVO responseVO = orderService.getOrdersInMonthOfAll();
        List<Order> orders = (List<Order>) responseVO.getContent();
        int num = orders.size();
        Assert.assertThat(num,is(1));
    }

    @Test
    @Transactional
    public void filterOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<Order> ordersOf1 = orderService.getHotelOrders(1);
        String beginTime = "2020-06-01";
        String endTime = "2020-06-03";
        orders= orderService.filterOrders(orders,beginTime,endTime);
        ordersOf1 = orderService.filterOrders(ordersOf1,beginTime,endTime);
        Assert.assertThat(1,is(orders.size()));
        Assert.assertThat(1,is(ordersOf1.size()));
    }
}