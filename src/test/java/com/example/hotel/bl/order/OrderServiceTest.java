package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
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
    public void addOrder1() {
        OrderVO orderVO1 = new OrderVO(){{
            setCheckInDate("2020-07-01");
            setCheckOutDate("2020-07-05");
            setCreateDate("2020-06-17");
            setHotelId(1);
            setOrderState("未入住");
            setPrice(399.0);
            setRoomNum(1);
            setUserId(1);
            setHaveChild(false);
            setHotelName("汉庭酒店");
            setHotelPhoneNum("123456777");
            setPeopleNum(2);
            setRoomType("大床房");
            setId(4);
        }};
        ResponseVO responseVO = orderService.addOrder(orderVO1);
        Assert.assertThat(responseVO.getContent(),is(true));
    }

    @Test
    @Transactional
    public void addOrder2() {
        OrderVO orderVO2 = new OrderVO(){{
            setCheckInDate("2020-06-05");
            setCheckOutDate("2020-07-01");
            setCreateDate("2020-06-17");
            setHotelId(1);
            setOrderState("未入住");
            setPrice(399.0);
            setRoomNum(1);
            setUserId(1);
            setHaveChild(false);
            setHotelName("汉庭酒店");
            setHotelPhoneNum("123456777");
            setPeopleNum(2);
            setRoomType("大床房");
            setId(4);
        }};
        ResponseVO responseVO = orderService.addOrder(orderVO2);
        Assert.assertThat(responseVO.getMessage(),is("预订时间错误"));
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
        List<Order> orders = orderService.getUserOrders(1);
        Assert.assertThat(orders.size(),is(1));
    }

    @Test
    @Transactional
    public void annulOrder() {
        ResponseVO responseVO = orderService.annulOrder(100);
        Assert.assertThat(responseVO.getMessage(),is("请输入正确的订单号"));
    }

    @Test
    @Transactional
    public void checkIn() {
        ResponseVO responseVO = orderService.checkIn(100);
        Assert.assertThat(responseVO.getContent().toString(),is("办理入住成功"));
    }

    @Test
    @Transactional
    public void probableAbnormalOrder() {
        List<Order> orders = orderService.probableAbnormalOrder(1);
        Assert.assertThat(orders.size(),is(1));
    }

    @Test
    @Transactional
    public void abnormalOrder() {
        ResponseVO responseVO = orderService.abnormalOrder(1,0.5);
        Assert.assertThat(responseVO,is("已标记为异常订单"));
    }

    @Test
    @Transactional
    public void finishOrder() {
        ResponseVO responseVO = orderService.finishOrder(1);
        Assert.assertThat(responseVO.getContent().toString(),is("退房成功"));
    }

    @Test
    @Transactional
    public void getComment() {
        CommentVO commentVO = orderService.getComment(1);
        Assert.assertThat(commentVO.getComment(),is("你的野区我养猪"));
    }

    @Test
    @Transactional
    public void annulComment() {
        ResponseVO responseVO = orderService.annulComment(1);
        Assert.assertThat(responseVO.getContent().toString(), is("1"));
    }

    @Test
    @Transactional
    public void getHotelComment() {
        List<CommentVO> commentVOS = orderService.getHotelComment(1);
        Assert.assertThat(commentVOS.size(),is(1));
    }

    @Test
    @Transactional
    public void addComment() {
        CommentVO commentVO = new CommentVO(){{
            setUserId(1);
            setOrderId(1);
            setComment("Just so so ~ ");
            setEnvironment(4);
            setEquipment(4);
            setPoints(5);
            setSanitation(5);
            setService(4);
        }};
        ResponseVO responseVO = orderService.addComment(commentVO);
        Assert.assertThat(responseVO.getContent(),is(1));
    }

    @Test
    @Transactional
    public void getUserComments() {
        ResponseVO responseVO = orderService.getUserComments(1);
        Assert.assertThat(((List<CommentVO>)responseVO.getContent()).size(),is(1));
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